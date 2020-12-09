package com.tnd.pw.config.packages.service.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.packages.dao.PackageCodeDao;
import com.tnd.pw.config.packages.dao.PackageDao;
import com.tnd.pw.config.packages.entity.PackageCodeEntity;
import com.tnd.pw.config.packages.entity.PackageEntity;
import com.tnd.pw.config.packages.exception.PackageCodeNotFoundException;
import com.tnd.pw.config.packages.exception.PackageNotFoundException;
import com.tnd.pw.config.packages.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class PackageServiceImpl implements PackageService {
    @Autowired
    private PackageDao packageDao;
    @Autowired
    private PackageCodeDao packageCodeDao;

    @Override
    public PackageEntity createPackage(PackageEntity entity) throws DBServiceException {
        entity.setId(System.currentTimeMillis());
        entity.setCreatedAt(System.currentTimeMillis());
        packageDao.create(entity);
        return entity;
    }

    @Override
    public List<PackageEntity> getPackage(PackageEntity entity) throws DBServiceException, PackageNotFoundException {
        return packageDao.get(entity);
    }

    @Override
    public void updatePackage(PackageEntity entity) throws DBServiceException {
        entity.setUpdatedAt(System.currentTimeMillis());
        packageDao.update(entity);
    }

    @Override
    public PackageCodeEntity createPackageCode(PackageCodeEntity entity) throws DBServiceException {
        entity.setId(System.currentTimeMillis());
        entity.setCreatedAt(System.currentTimeMillis());
        packageCodeDao.create(entity);
        return entity;
    }

    @Override
    public List<PackageCodeEntity> getPackageCode(PackageCodeEntity entity) throws DBServiceException, PackageCodeNotFoundException {
        return packageCodeDao.get(entity);
    }

    @Override
    public void updatePackageCode(PackageCodeEntity entity) throws DBServiceException {
        entity.setUpdatedAt(System.currentTimeMillis());
        packageCodeDao.update(entity);
    }

    @Override
    public List<PackageCodeEntity> getPackageCode(Long packageId, Long startTime, Long endTime) throws DBServiceException, PackageCodeNotFoundException {
        return packageCodeDao.get(packageId, startTime, endTime);
    }
}
