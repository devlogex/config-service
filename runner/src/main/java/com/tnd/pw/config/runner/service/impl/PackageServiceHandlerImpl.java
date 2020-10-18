package com.tnd.pw.config.runner.service.impl;

import com.tnd.com.notification.NotificationService;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.common.requests.AdminRequest;
import com.tnd.pw.config.common.requests.AnonymousRequest;
import com.tnd.pw.config.common.requests.UserRequest;
import com.tnd.pw.config.packages.entity.PackageCodeEntity;
import com.tnd.pw.config.packages.enums.PackageCodeState;
import com.tnd.pw.config.packages.enums.PackageState;
import com.tnd.pw.config.common.representations.CsPackageRepresentation;
import com.tnd.pw.config.common.representations.PackageRepresentation;
import com.tnd.pw.config.common.utils.RepresentationBuilder;
import com.tnd.pw.config.packages.entity.PackageEntity;
import com.tnd.pw.config.packages.exception.PackageNotFoundException;
import com.tnd.pw.config.packages.service.PackageService;
import com.tnd.pw.config.runner.service.PackageServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class PackageServiceHandlerImpl implements PackageServiceHandler {
    @Autowired
    private PackageService packageService;
    @Autowired
    private NotificationService notificationService;

    @Override
    public CsPackageRepresentation addPackage(AdminRequest request) throws DBServiceException, PackageNotFoundException {
        packageService.createPackage(PackageEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
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
    public CsPackageRepresentation getPackage(AnonymousRequest request) throws DBServiceException, PackageNotFoundException {
        List<PackageEntity> entities = packageService.getPackage(
                PackageEntity.builder()
                        .id(request.getId())
                        .state(request.getState() != null ? PackageState.valueOf(request.getState()).ordinal() : null)
                        .build()
        );
        return RepresentationBuilder.buildListPackageRep(entities);
    }

    @Override
    public PackageRepresentation updatePackage(AdminRequest request) throws DBServiceException, PackageNotFoundException {
        PackageEntity packageEntity = packageService.getPackage(PackageEntity.builder().id(request.getId()).build()).get(0);
        if(request.getState() != null) {
            packageEntity.setState(PackageState.valueOf(request.getState()).ordinal());
        }
        packageService.updatePackage(packageEntity);
        return RepresentationBuilder.buildPackageRepresentation(packageEntity);
    }

    @Override
    public CsPackageRepresentation registerPackage(UserRequest request) throws DBServiceException, PackageNotFoundException, MessagingException {
        PackageEntity packageEntity = packageService.getPackage(PackageEntity.builder().id(request.getId()).build()).get(0);
        PackageCodeEntity packageCode = packageService.createPackageCode(
                PackageCodeEntity.builder()
                        .packageId(packageEntity.getId())
                        .state(PackageCodeState.ACTIVE.ordinal())
                        .expireTime(System.currentTimeMillis() + packageEntity.getPeriodValidity())
                        .build()
        );
//        notificationService.send(request.getPayload().getEmail(), "Package_Code", packageCode.getId().toString());
        return new CsPackageRepresentation(packageCode.getId().toString());
    }
}
