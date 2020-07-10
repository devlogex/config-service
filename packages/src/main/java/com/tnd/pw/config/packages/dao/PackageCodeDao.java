package com.tnd.pw.config.packages.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.packages.entity.PackageCodeEntity;
import com.tnd.pw.config.packages.entity.PackageEntity;
import com.tnd.pw.config.packages.exception.PackageCodeNotFoundException;
import com.tnd.pw.config.packages.exception.PackageNotFoundException;

import java.io.IOException;
import java.util.List;

public interface PackageCodeDao {
    void create(PackageCodeEntity entity) throws IOException, DBServiceException;
    List<PackageCodeEntity> get(PackageCodeEntity entity) throws IOException, DBServiceException, PackageCodeNotFoundException;
    void update(PackageCodeEntity entity) throws IOException, DBServiceException;
}
