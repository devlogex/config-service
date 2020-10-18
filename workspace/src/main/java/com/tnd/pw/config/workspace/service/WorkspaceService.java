package com.tnd.pw.config.workspace.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.workspace.entity.WorkspaceEntity;
import com.tnd.pw.config.workspace.exception.WorkspaceNotFoundException;

import java.io.IOException;
import java.util.List;

public interface WorkspaceService {
    WorkspaceEntity create(WorkspaceEntity entity) throws DBServiceException;
    List<WorkspaceEntity> get(WorkspaceEntity entity) throws DBServiceException, WorkspaceNotFoundException;
    void update(WorkspaceEntity entity) throws DBServiceException;
}
