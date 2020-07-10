package com.tnd.pw.config.workspace.service.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.workspace.dao.WorkspaceDao;
import com.tnd.pw.config.workspace.entity.WorkspaceEntity;
import com.tnd.pw.config.workspace.exception.WorkspaceNotFoundException;
import com.tnd.pw.config.workspace.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class WorkspaceServiceImpl implements WorkspaceService {
    @Autowired
    private WorkspaceDao workspaceDao;

    @Override
    public WorkspaceEntity create(WorkspaceEntity entity) throws IOException, DBServiceException {
        entity.setId(System.currentTimeMillis());
        entity.setCreatedAt(System.currentTimeMillis());
        workspaceDao.create(entity);
        return entity;
    }

    @Override
    public List<WorkspaceEntity> get(WorkspaceEntity entity) throws IOException, DBServiceException, WorkspaceNotFoundException {
        return workspaceDao.get(entity);
    }

    @Override
    public void update(WorkspaceEntity entity) throws IOException, DBServiceException {
        entity.setUpdatedAt(System.currentTimeMillis());
        workspaceDao.update(entity);
    }

}
