package com.tnd.pw.config.packages.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.packages.entity.PackageEntity;
import com.tnd.pw.config.packages.exception.PackageNotFoundException;

import java.io.IOException;
import java.util.List;

public interface PackageDao {
    void create(PackageEntity entity) throws DBServiceException;
    List<PackageEntity> get(PackageEntity entity) throws DBServiceException, PackageNotFoundException;
    void update(PackageEntity entity) throws DBServiceException;
}
