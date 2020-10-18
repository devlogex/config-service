package com.tnd.pw.config.user.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.user.entity.PermissionEntity;
import com.tnd.pw.config.user.exception.PermissionNotFoundException;

import java.io.IOException;
import java.util.List;

public interface PermissionDao {
    void create(PermissionEntity entity) throws DBServiceException;
    List<PermissionEntity> get(PermissionEntity entity) throws DBServiceException, PermissionNotFoundException;
    void update(PermissionEntity entity) throws DBServiceException;
}
