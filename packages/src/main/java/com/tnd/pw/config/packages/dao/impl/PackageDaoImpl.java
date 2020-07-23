package com.tnd.pw.config.packages.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.dbservice.DataHelper;
import com.tnd.pw.config.packages.dao.PackageDao;
import com.tnd.pw.config.packages.entity.PackageEntity;
import com.tnd.pw.config.packages.exception.PackageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class PackageDaoImpl implements PackageDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO package(id, name, description, max_product, max_member, price, period_validity, state, created_at, created_by) " +
                    "values(%d, '%s', '%s', %d, %d, %16.4f, %d, %d, %d, %d)";
    private static final String SQL_UPDATE =
            "UPDATE package SET state = %d, updated_at = %d, updated_by = %d WHERE id = %d";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM package WHERE id = %d ORDER BY created_at";
    private static final String SQL_SELECT_BY_STATE =
            "SELECT * FROM package WHERE state = %d ORDER BY created_at";
    private static final String SQL_SELECT =
            "SELECT * FROM package ORDER BY created_at";

    @Override
    public void create(PackageEntity entity) throws IOException, DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getName(),
                entity.getDescription(),entity.getMaxProduct(),
                entity.getMaxMember(), entity.getPrice(), entity.getPeriodValidity(),
                entity.getState(), entity.getCreatedAt(),entity.getCreatedBy());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<PackageEntity> get(PackageEntity entity) throws IOException, DBServiceException, PackageNotFoundException {
        String query;
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else if(entity.getState() != null) {
            query = String.format(SQL_SELECT_BY_STATE, entity.getState());
        }
        else {
            query = String.format(SQL_SELECT, entity.getState());
        }
        List<PackageEntity> entities = dataHelper.querySQL(query, PackageEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new PackageNotFoundException();
        }
        return entities;
    }

    @Override
    public void update(PackageEntity entity) throws IOException, DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getState(),
                entity.getUpdatedAt(), entity.getUpdatedBy(), entity.getId());
        dataHelper.executeSQL(query);
    }
}
