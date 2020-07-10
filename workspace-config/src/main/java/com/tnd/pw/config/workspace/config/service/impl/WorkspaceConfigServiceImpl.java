package com.tnd.pw.config.workspace.config.service.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.workspace.config.dao.WorkspaceConfigDao;
import com.tnd.pw.config.workspace.config.entity.WorkspaceConfigEntity;
import com.tnd.pw.config.workspace.config.exception.WorkspaceConfigNotFoundException;
import com.tnd.pw.config.workspace.config.service.WorkspaceConfigService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class WorkspaceConfigServiceImpl implements WorkspaceConfigService {
    @Autowired
    private WorkspaceConfigDao workspaceConfigDao;

    @Override
    public WorkspaceConfigEntity create(WorkspaceConfigEntity entity) throws IOException, DBServiceException {
        entity.setId(System.currentTimeMillis());
        entity.setCreatedAt(System.currentTimeMillis());
        workspaceConfigDao.create(entity);
        return entity;
    }

    @Override
    public List<WorkspaceConfigEntity> get(WorkspaceConfigEntity entity) throws IOException, DBServiceException, WorkspaceConfigNotFoundException {
        return workspaceConfigDao.get(entity);
    }

    @Override
    public void update(WorkspaceConfigEntity entity) throws IOException, DBServiceException {
        entity.setUpdatedAt(System.currentTimeMillis());
        workspaceConfigDao.update(entity);
    }
}
