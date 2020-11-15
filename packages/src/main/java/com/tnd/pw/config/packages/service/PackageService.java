package com.tnd.pw.config.packages.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.packages.entity.PackageCodeEntity;
import com.tnd.pw.config.packages.entity.PackageEntity;
import com.tnd.pw.config.packages.exception.PackageCodeNotFoundException;
import com.tnd.pw.config.packages.exception.PackageNotFoundException;

import java.io.IOException;
import java.util.List;

public interface PackageService {
    PackageEntity createPackage(PackageEntity entity) throws DBServiceException;
    List<PackageEntity> getPackage(PackageEntity entity) throws DBServiceException, PackageNotFoundException;
    void updatePackage(PackageEntity entity) throws DBServiceException;

    PackageCodeEntity createPackageCode(PackageCodeEntity entity) throws DBServiceException;
    List<PackageCodeEntity> getPackageCode(PackageCodeEntity entity) throws DBServiceException, PackageCodeNotFoundException;
    void updatePackageCode(PackageCodeEntity entity) throws DBServiceException;
    List<PackageCodeEntity> getPackageCode(Long startTime, Long endTime) throws DBServiceException, PackageCodeNotFoundException;
}
