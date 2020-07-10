package com.tnd.pw.config.runner.service.impl;


import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.common.representations.CsWorkspaceRepresentation;
import com.tnd.pw.config.common.representations.WorkspaceRepresentation;
import com.tnd.pw.config.common.requests.ConfigRequest;
import com.tnd.pw.config.common.utils.GsonUtils;
import com.tnd.pw.config.common.utils.RepresentationBuilder;
import com.tnd.pw.config.packages.entity.PackageCodeEntity;
import com.tnd.pw.config.packages.entity.PackageEntity;
import com.tnd.pw.config.packages.enums.PackageCodeState;
import com.tnd.pw.config.packages.enums.PackageState;
import com.tnd.pw.config.packages.exception.InconsistentStateException;
import com.tnd.pw.config.packages.exception.PackageCodeNotFoundException;
import com.tnd.pw.config.packages.exception.PackageNotFoundException;
import com.tnd.pw.config.packages.service.PackageService;
import com.tnd.pw.config.runner.exception.PackageCodeExpiredException;
import com.tnd.pw.config.runner.exception.PackageInactiveException;
import com.tnd.pw.config.runner.service.WorkspaceServiceHandler;
import com.tnd.pw.config.workspace.config.entity.WorkspaceConfigEntity;
import com.tnd.pw.config.workspace.config.exception.WorkspaceConfigNotFoundException;
import com.tnd.pw.config.workspace.config.service.WorkspaceConfigService;
import com.tnd.pw.config.workspace.entity.WorkspaceEntity;
import com.tnd.pw.config.workspace.enums.WorkspaceState;
import com.tnd.pw.config.workspace.exception.WorkspaceNotFoundException;
import com.tnd.pw.config.workspace.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class WorkspaceServiceHandlerImpl implements WorkspaceServiceHandler {
    @Autowired
    private PackageService packageService;
    @Autowired
    private WorkspaceService workspaceService;
    @Autowired
    private WorkspaceConfigService workspaceConfigService;

    @Override
    public WorkspaceRepresentation addWorkspace(ConfigRequest request) throws IOException, DBServiceException, PackageCodeNotFoundException, PackageCodeExpiredException, PackageNotFoundException, PackageInactiveException, InconsistentStateException {
        PackageCodeEntity packageCodeEntity = packageService.getPackageCode(PackageCodeEntity.builder().id(request.getCode()).build()).get(0);
        if(packageCodeEntity.getState() == PackageCodeState.INACTIVE.ordinal()) {
            throw new PackageCodeExpiredException();
        }
        PackageEntity packageEntity = packageService.getPackage(PackageEntity.builder().id(packageCodeEntity.getPackageId()).build()).get(0);
        if(packageEntity.getState() == PackageState.INACTIVE.ordinal()) {
            throw new PackageInactiveException();
        }

        WorkspaceEntity workspaceEntity = workspaceService.create(
                WorkspaceEntity.builder()
                        .domain(request.getDomain())
                        .state(WorkspaceState.ACTIVE.ordinal())
                        .ownerId(1L)
                        .createdBy(1L)
                        .build()
        );

        WorkspaceConfigEntity workspaceConfigEntity = workspaceConfigService.create(
                WorkspaceConfigEntity.builder()
                        .workspaceId(workspaceEntity.getId())
                        .packageId(packageEntity.getId())
                        .config(GsonUtils.convertToString(packageEntity))
                        .state(WorkspaceState.ACTIVE.ordinal())
                        .build()
        );

        workspaceEntity.setConfigId(workspaceConfigEntity.getId());
        workspaceService.update(workspaceEntity);

        packageCodeEntity.setState(PackageCodeState.INACTIVE.ordinal());
        packageService.updatePackageCode(packageCodeEntity);
        return RepresentationBuilder.buildWorkspaceRepresentation(workspaceEntity, GsonUtils.convertToString(packageEntity));
    }

    @Override
    public CsWorkspaceRepresentation getWorkspace(ConfigRequest request) throws DBServiceException, WorkspaceNotFoundException, IOException, WorkspaceConfigNotFoundException {
        List<WorkspaceEntity> workspaceEntities = workspaceService.get(
                WorkspaceEntity.builder()
                        .id(request.getId())
                        .state(request.getState() != null ? WorkspaceState.valueOf(request.getState()).ordinal() : null)
                        .build()
        );
        List<WorkspaceConfigEntity> workspaceConfigEntities = workspaceConfigService.get(WorkspaceConfigEntity.builder().build());
        return RepresentationBuilder.buildListWorkspaceRep(workspaceEntities, workspaceConfigEntities);
    }

    @Override
    public WorkspaceRepresentation updateWorkspace(ConfigRequest request) throws DBServiceException, WorkspaceNotFoundException, IOException, WorkspaceConfigNotFoundException {
        WorkspaceEntity workspaceEntity = workspaceService.get(WorkspaceEntity.builder().id(request.getId()).build()).get(0);
        WorkspaceConfigEntity workspaceConfigEntity = workspaceConfigService.get(WorkspaceConfigEntity.builder().id(workspaceEntity.getConfigId()).build()).get(0);
        workspaceEntity.setState(WorkspaceState.valueOf(request.getState()).ordinal());
        workspaceConfigEntity.setState(WorkspaceState.valueOf(request.getState()).ordinal());
        workspaceService.update(workspaceEntity);
        workspaceConfigService.update(workspaceConfigEntity);
        return RepresentationBuilder.buildWorkspaceRepresentation(workspaceEntity, workspaceConfigEntity.getConfig());
    }

    @Override
    public WorkspaceRepresentation upgradeWorkspace(ConfigRequest request) throws DBServiceException, IOException, PackageCodeNotFoundException, PackageCodeExpiredException, PackageNotFoundException, PackageInactiveException, WorkspaceNotFoundException, WorkspaceConfigNotFoundException {
        PackageCodeEntity packageCodeEntity = packageService.getPackageCode(PackageCodeEntity.builder().id(request.getCode()).build()).get(0);
        if(packageCodeEntity.getState() == PackageCodeState.INACTIVE.ordinal()) {
            throw new PackageCodeExpiredException();
        }
        PackageEntity packageEntity = packageService.getPackage(PackageEntity.builder().id(packageCodeEntity.getPackageId()).build()).get(0);
        if(packageEntity.getState() == PackageState.INACTIVE.ordinal()) {
            throw new PackageInactiveException();
        }

        WorkspaceEntity workspaceEntity = workspaceService.get(
                WorkspaceEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);

        updateOldConfig(workspaceEntity.getConfigId());

        WorkspaceConfigEntity workspaceConfigEntity = workspaceConfigService.create(
                WorkspaceConfigEntity.builder()
                        .workspaceId(workspaceEntity.getId())
                        .packageId(packageEntity.getId())
                        .config(GsonUtils.convertToString(packageEntity))
                        .state(WorkspaceState.ACTIVE.ordinal())
                        .build()
        );

        workspaceEntity.setState(WorkspaceState.ACTIVE.ordinal());
        workspaceEntity.setConfigId(workspaceConfigEntity.getId());
        workspaceService.update(workspaceEntity);
        return RepresentationBuilder.buildWorkspaceRepresentation(workspaceEntity, workspaceConfigEntity.getConfig());
    }

    private void updateOldConfig(Long configId) throws DBServiceException, WorkspaceConfigNotFoundException, IOException {
        WorkspaceConfigEntity workspaceConfigEntity = workspaceConfigService.get(WorkspaceConfigEntity.builder().id(configId).build()).get(0);
        workspaceConfigEntity.setState(WorkspaceState.INACTIVE.ordinal());
        workspaceConfigService.update(workspaceConfigEntity);
    }
}
