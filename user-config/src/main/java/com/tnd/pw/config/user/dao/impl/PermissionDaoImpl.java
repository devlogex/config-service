package com.tnd.pw.config.user.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.dbservice.DataHelper;
import com.tnd.pw.config.user.dao.PermissionDao;
import com.tnd.pw.config.user.entity.PermissionEntity;
import com.tnd.pw.config.user.exception.PermissionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class PermissionDaoImpl implements PermissionDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO permission(id, name, permissions) " +
                    "values(%d, '%s', '%s')";
    private static final String SQL_UPDATE =
            "UPDATE permission SET permissions = '%s', " +
                    "name = '%s' WHERE id = %d";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM permission WHERE id = %d";

    @Override
    public void create(PermissionEntity entity) throws DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getName(),
                entity.getPermissions());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<PermissionEntity> get(PermissionEntity entity) throws DBServiceException, PermissionNotFoundException {
        String query = String.format(SQL_SELECT_BY_ID, entity.getId());
        List<PermissionEntity> entities = dataHelper.querySQL(query, PermissionEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new PermissionNotFoundException();
        }
        return entities;
    }

    @Override
    public void update(PermissionEntity entity) throws DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getPermissions(),
                entity.getName(), entity.getId());
        dataHelper.executeSQL(query);
    }
}
