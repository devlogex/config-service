package com.tnd.pw.config.packages.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.dbservice.DataHelper;
import com.tnd.pw.config.packages.dao.PackageCodeDao;
import com.tnd.pw.config.packages.entity.PackageCodeEntity;
import com.tnd.pw.config.packages.exception.PackageCodeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class PackageCodeDaoImpl implements PackageCodeDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO package_code(id, package_id, expire_time, state, price, created_at, created_by, updated_at, updated_by) " +
                    "values(%d, %d, %d, %d, %16.4f, %d, %d, %d, %d)";
    private static final String SQL_UPDATE =
            "UPDATE package_code SET state = %d, updated_at = %d, updated_by = %d WHERE id = %d";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM package_code WHERE id = %d";
    private static final String SQL_SELECT_BY_PACKAGE_ID_AND_DURATION_TIME =
            "SELECT * FROM package_code WHERE package_id = %d AND (created_at BETWEEN %d AND %d) ORDER BY created_at ASC";

    @Override
    public void create(PackageCodeEntity entity) throws DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getPackageId(),
                entity.getExpireTime(), entity.getState(), entity.getPrice(), entity.getCreatedAt(),entity.getCreatedBy(),
                entity.getUpdatedAt(), entity.getUpdatedBy());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<PackageCodeEntity> get(PackageCodeEntity entity) throws DBServiceException, PackageCodeNotFoundException {
        String query = String.format(SQL_SELECT_BY_ID, entity.getId());
        List<PackageCodeEntity> entities = dataHelper.querySQL(query, PackageCodeEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new PackageCodeNotFoundException();
        }
        return entities;
    }

    @Override
    public void update(PackageCodeEntity entity) throws DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getState(),
                entity.getUpdatedAt(), entity.getUpdatedBy(), entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<PackageCodeEntity> get(Long packageId, Long startTime, Long endTime) throws DBServiceException, PackageCodeNotFoundException {
        String query = String.format(SQL_SELECT_BY_PACKAGE_ID_AND_DURATION_TIME, packageId, startTime, endTime);
        List<PackageCodeEntity> entities = dataHelper.querySQL(query, PackageCodeEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new PackageCodeNotFoundException();
        }
        return entities;
    }
}
