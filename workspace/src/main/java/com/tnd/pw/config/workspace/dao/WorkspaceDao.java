package com.tnd.pw.config.workspace.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.workspace.entity.WorkspaceEntity;
import com.tnd.pw.config.workspace.exception.WorkspaceNotFoundException;

import java.io.IOException;
import java.util.List;

public interface WorkspaceDao {
    void create(WorkspaceEntity entity) throws IOException, DBServiceException;
    List<WorkspaceEntity> get(WorkspaceEntity entity) throws IOException, DBServiceException, WorkspaceNotFoundException;
    void update(WorkspaceEntity entity) throws IOException, DBServiceException;
}
