package com.tnd.pw.config.user.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.dbservice.DataHelper;
import com.tnd.pw.config.user.dao.UserConfigDao;
import com.tnd.pw.config.user.entity.UserConfigEntity;
import com.tnd.pw.config.user.exception.UserConfigNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class UserConfigDaoImpl implements UserConfigDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO user_config(id, user_id, workspace_id, workspace_permissions, product_permissions, state, created_at, created_by) " +
                    "values(%d, %d, %d, '%s', '%s', %d, %d, %d)";
    private static final String SQL_UPDATE =
            "UPDATE user_config SET workspace_permissions = '%s', product_permissions = '%s', state = %d, " +
                    "updated_at = %d, updated_by = %d WHERE id = %d";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM user_config WHERE id = %d";
    private static final String SQL_SELECT_BY_USER_ID_AND_STATE =
            "SELECT * FROM user_config WHERE user_id = %d AND state = %d";
    private static final String SQL_SELECT_BY_WORKSPACE_ID_AND_STATE =
            "SELECT * FROM user_config WHERE workspace_id = %d AND state = %d";
    private static final String SQL_SELECT_BY_USER_ID_AND_WORKSPACE_ID_AND_STATE =
            "SELECT * FROM user_config WHERE user_id = %d AND workspace_id = %d AND state = %d";
    private static final String SQL_SELECT_BY_USER_ID =
            "SELECT * FROM user_config WHERE user_id = %d";
    private static final String SQL_SELECT_BY_WORKSPACE_ID =
            "SELECT * FROM user_config WHERE workspace_id = %d";
    private static final String SQL_SELECT_BY_USER_ID_AND_WORKSPACE_ID =
            "SELECT * FROM user_config WHERE user_id = %d AND workspace_id = %d";
    private static final String SQL_SELECT_BY_WORKSPACE_PERMISSION_AND_WORKSPACE_ID_AND_STATE =
            "SELECT * FROM user_config WHERE workspace_permissions LIKE '%s' AND workspace_id = %d AND STATE = %d";

    @Override
    public void create(UserConfigEntity entity) throws DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getUserId(),
                entity.getWorkspaceId(), entity.getWorkspacePermissions(), entity.getProductPermissions(),
                entity.getState(), entity.getCreatedAt(),entity.getCreatedBy());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<UserConfigEntity> get(UserConfigEntity entity) throws DBServiceException, UserConfigNotFoundException {
        String query = null;
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else if(entity.getState() != null) {
            if(entity.getWorkspacePermissions() != null && entity.getWorkspaceId() != null) {
                query = String.format(SQL_SELECT_BY_WORKSPACE_PERMISSION_AND_WORKSPACE_ID_AND_STATE, entity.getWorkspacePermissions(), entity.getWorkspaceId(), entity.getState());
            }
            else if(entity.getUserId() != null && entity.getWorkspaceId() != null) {
                query = String.format(SQL_SELECT_BY_USER_ID_AND_WORKSPACE_ID_AND_STATE, entity.getUserId(), entity.getWorkspaceId(), entity.getState());
            }
            else if(entity.getUserId() != null){
                query = String.format(SQL_SELECT_BY_USER_ID_AND_STATE, entity.getUserId(), entity.getState());
            }
            else if(entity.getWorkspaceId() != null) {
                query = String.format(SQL_SELECT_BY_WORKSPACE_ID_AND_STATE, entity.getWorkspaceId(), entity.getState());
            }
        }
        else {
            if(entity.getUserId() != null && entity.getWorkspaceId() != null) {
                query = String.format(SQL_SELECT_BY_USER_ID_AND_WORKSPACE_ID, entity.getUserId(), entity.getWorkspaceId());
            }
            else if(entity.getUserId() != null){
                query = String.format(SQL_SELECT_BY_USER_ID, entity.getUserId());
            }
            else if(entity.getWorkspaceId() != null) {
                query = String.format(SQL_SELECT_BY_WORKSPACE_ID, entity.getWorkspaceId());
            }
        }
        List<UserConfigEntity> entities = dataHelper.querySQL(query, UserConfigEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new UserConfigNotFoundException();
        }
        return entities;
    }

    @Override
    public void update(UserConfigEntity entity) throws DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getWorkspacePermissions(),
                entity.getProductPermissions(), entity.getState(),
                entity.getUpdatedAt(), entity.getUpdatedBy(), entity.getId());
        dataHelper.executeSQL(query);
    }
}
