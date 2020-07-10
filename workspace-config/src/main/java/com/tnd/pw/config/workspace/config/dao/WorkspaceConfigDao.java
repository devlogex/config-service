package com.tnd.pw.config.workspace.config.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.workspace.config.entity.WorkspaceConfigEntity;
import com.tnd.pw.config.workspace.config.exception.WorkspaceConfigNotFoundException;

import java.io.IOException;
import java.util.List;

public interface WorkspaceConfigDao {
    void create(WorkspaceConfigEntity entity) throws IOException, DBServiceException;
    List<WorkspaceConfigEntity> get(WorkspaceConfigEntity entity) throws IOException, DBServiceException, WorkspaceConfigNotFoundException;
    void update(WorkspaceConfigEntity entity) throws IOException, DBServiceException;
}
