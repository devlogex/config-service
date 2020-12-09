package com.tnd.pw.config.workspace.config.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.dbservice.DataHelper;
import com.tnd.pw.config.workspace.config.dao.WorkspaceConfigDao;
import com.tnd.pw.config.workspace.config.entity.WorkspaceConfigEntity;
import com.tnd.pw.config.workspace.config.exception.WorkspaceConfigNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class WorkspaceConfigDaoImpl implements WorkspaceConfigDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO workspace_config(id, workspace_id, package_id, config, state, created_at, created_by) " +
                    "values(%d, %d, %d, '%s', %d, %d, %d)";
    private static final String SQL_UPDATE =
            "UPDATE workspace_config SET state = %d, updated_at = %d, updated_by = %d WHERE id = %d";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM workspace_config WHERE id = %d";
    private static final String SQL_SELECT_BY_WORKSPACE_ID_AND_STATE =
            "SELECT * FROM workspace_config WHERE workspace_id = %d AND state = %d";
    private static final String SQL_SELECT_BY_STATE =
            "SELECT * FROM workspace_config WHERE state = %d";
    private static final String SQL_SELECT =
            "SELECT * FROM workspace_config";

    @Override
    public void create(WorkspaceConfigEntity entity) throws DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getWorkspaceId(),
                entity.getPackageId(), entity.getConfig(), entity.getState(),
                entity.getCreatedAt(),entity.getCreatedBy());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<WorkspaceConfigEntity> get(WorkspaceConfigEntity entity) throws DBServiceException, WorkspaceConfigNotFoundException {
        String query ;
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else if(entity.getWorkspaceId() != null && entity.getState() != null) {
            query = String.format(SQL_SELECT_BY_WORKSPACE_ID_AND_STATE, entity.getWorkspaceId(), entity.getState());
        }
        else if(entity.getState() != null) {
            query = String.format(SQL_SELECT_BY_STATE, entity.getState());
        }
        else {
            query = String.format(SQL_SELECT);
        }
        List<WorkspaceConfigEntity> entities = dataHelper.querySQL(query, WorkspaceConfigEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new WorkspaceConfigNotFoundException();
        }
        return entities;
    }

    @Override
    public void update(WorkspaceConfigEntity entity) throws DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getState(),
                entity.getUpdatedAt(), entity.getUpdatedBy(), entity.getId());
        dataHelper.executeSQL(query);
    }
}
