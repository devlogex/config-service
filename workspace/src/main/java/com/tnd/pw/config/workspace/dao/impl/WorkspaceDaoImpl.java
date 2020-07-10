package com.tnd.pw.config.workspace.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.dbservice.DataHelper;
import com.tnd.pw.config.workspace.dao.WorkspaceDao;
import com.tnd.pw.config.workspace.entity.WorkspaceEntity;
import com.tnd.pw.config.workspace.exception.WorkspaceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class WorkspaceDaoImpl implements WorkspaceDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO workspace(id, config_id, state, owner_id, domain, created_at, created_by) " +
                    "values(%d, %d, %d, %d, '%s', %d, %d)";
    private static final String SQL_UPDATE =
            "UPDATE workspace SET state = %d, config_id = %d, updated_at = %d, updated_by = %d WHERE id = %d";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM workspace WHERE id = %d";
    private static final String SQL_SELECT_BY_STATE =
            "SELECT * FROM workspace WHERE state = %d";

    @Override
    public void create(WorkspaceEntity entity) throws IOException, DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getConfigId(), entity.getState(),
                entity.getOwnerId(), entity.getDomain(), entity.getCreatedAt(),entity.getCreatedBy());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<WorkspaceEntity> get(WorkspaceEntity entity) throws IOException, DBServiceException, WorkspaceNotFoundException {
        String query;
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else {
            query = String.format(SQL_SELECT_BY_STATE, entity.getState());
        }
        List<WorkspaceEntity> entities = dataHelper.querySQL(query, WorkspaceEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new WorkspaceNotFoundException();
        }
        return entities;
    }

    @Override
    public void update(WorkspaceEntity entity) throws IOException, DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getState(), entity.getConfigId(),
                entity.getUpdatedAt(), entity.getUpdatedBy(), entity.getId());
        dataHelper.executeSQL(query);
    }

}
