package com.tnd.pw.config.runner.service.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.packages.entity.PackageCodeEntity;
import com.tnd.pw.config.packages.enums.PackageCodeState;
import com.tnd.pw.config.packages.enums.PackageState;
import com.tnd.pw.config.common.representations.CsPackageRepresentation;
import com.tnd.pw.config.common.representations.PackageRepresentation;
import com.tnd.pw.config.common.requests.ConfigRequest;
import com.tnd.pw.config.common.utils.RepresentationBuilder;
import com.tnd.pw.config.packages.entity.PackageEntity;
import com.tnd.pw.config.packages.exception.PackageNotFoundException;
import com.tnd.pw.config.packages.service.PackageService;
import com.tnd.pw.config.runner.service.PackageServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class PackageServiceHandlerImpl implements PackageServiceHandler {
    @Autowired
    private PackageService packageService;

    @Override
    public CsPackageRepresentation addPackage(ConfigRequest request) throws IOException, DBServiceException, PackageNotFoundException {
        packageService.createPackage(PackageEntity.builder()
                .maxProduct(request.getMaxProduct())
                .maxMember(request.getMaxMember())
                .price(request.getPrice())
                .state(PackageState.ACTIVE.ordinal())
                .periodValidity(request.getPeriodValidity())
                .createdBy(1L)
                .build()
        );
        List<PackageEntity> entities = packageService.getPackage(PackageEntity.builder().state(PackageState.ACTIVE.ordinal()).build());
        return RepresentationBuilder.buildListPackageRep(entities);
    }

    @Override
    public CsPackageRepresentation getPackage(ConfigRequest request) throws IOException, DBServiceException, PackageNotFoundException {
        List<PackageEntity> entities = packageService.getPackage(
                PackageEntity.builder()
                        .id(request.getId())
                        .state(request.getState() != null ? PackageState.valueOf(request.getState()).ordinal() : null)
                        .build()
        );
        return RepresentationBuilder.buildListPackageRep(entities);
    }

    @Override
    public PackageRepresentation updatePackage(ConfigRequest request) throws IOException, DBServiceException, PackageNotFoundException {
        PackageEntity packageEntity = packageService.getPackage(PackageEntity.builder().id(request.getId()).build()).get(0);
        if(request.getState() != null) {
            packageEntity.setState(PackageState.valueOf(request.getState()).ordinal());
        }
        packageService.updatePackage(packageEntity);
        return RepresentationBuilder.buildPackageRepresentation(packageEntity);
    }

    @Override
    public CsPackageRepresentation registerPackage(ConfigRequest request) throws DBServiceException, IOException, PackageNotFoundException {
        PackageEntity packageEntity = packageService.getPackage(PackageEntity.builder().id(request.getId()).build()).get(0);
        PackageCodeEntity packageCode = packageService.createPackageCode(
                PackageCodeEntity.builder()
                        .packageId(packageEntity.getId())
                        .state(PackageCodeState.ACTIVE.ordinal())
                        .expireTime(System.currentTimeMillis() + packageEntity.getPeriodValidity())
                        .build()
        );
        return new CsPackageRepresentation(packageCode.getId().toString());
    }
}
