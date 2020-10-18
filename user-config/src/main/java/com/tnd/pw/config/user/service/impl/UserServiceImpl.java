package com.tnd.pw.config.user.service.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.user.dao.PermissionDao;
import com.tnd.pw.config.user.dao.UserConfigDao;
import com.tnd.pw.config.user.dao.UserProfileDao;
import com.tnd.pw.config.user.entity.PermissionEntity;
import com.tnd.pw.config.user.entity.UserConfigEntity;
import com.tnd.pw.config.user.entity.UserProfileEntity;
import com.tnd.pw.config.user.constants.UserState;
import com.tnd.pw.config.user.exception.PermissionNotFoundException;
import com.tnd.pw.config.user.exception.UserConfigNotFoundException;
import com.tnd.pw.config.user.exception.UserProfileNotFoundException;
import com.tnd.pw.config.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserProfileDao userProfileDao;
    @Autowired
    private UserConfigDao userConfigDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public UserProfileEntity createUserProfile(UserProfileEntity entity) throws DBServiceException {
        entity.setId(System.currentTimeMillis());
        entity.setCreatedAt(System.currentTimeMillis());
        entity.setState(UserState.ACTIVE.ordinal());
        entity.setAvatar(entity.getAvatar() != null ? entity.getAvatar() : "");
        userProfileDao.create(entity);
        return entity;
    }

    @Override
    public List<UserProfileEntity> getUserProfile(UserProfileEntity entity) throws DBServiceException, UserProfileNotFoundException {
        return userProfileDao.get(entity);
    }

    @Override
    public List<UserProfileEntity> getUserProfile(List<Long> ids) throws DBServiceException, UserProfileNotFoundException {
        return userProfileDao.get(ids);
    }

    @Override
    public void updateUserProfile(UserProfileEntity entity) throws DBServiceException {
        userProfileDao.update(entity);
    }

    @Override
    public UserConfigEntity createUserConfig(UserConfigEntity entity) throws DBServiceException {
        entity.setId(System.currentTimeMillis());
        entity.setCreatedAt(System.currentTimeMillis());
        entity.setState(UserState.ACTIVE.ordinal());
        entity.setWorkspacePermissions(entity.getWorkspacePermissions() == null ? "" : entity.getWorkspacePermissions());
        userConfigDao.create(entity);
        return entity;
    }

    @Override
    public List<UserConfigEntity> getUserConfig(UserConfigEntity entity) throws DBServiceException, UserConfigNotFoundException {
        return userConfigDao.get(entity);
    }

    @Override
    public void updateUserConfig(UserConfigEntity entity) throws DBServiceException {
        userConfigDao.update(entity);
    }

    @Override
    public PermissionEntity createPermission(PermissionEntity entity) throws DBServiceException {
        entity.setId(System.currentTimeMillis());
        permissionDao.create(entity);
        return entity;
    }

    @Override
    public List<PermissionEntity> getPermission(PermissionEntity entity) throws DBServiceException, PermissionNotFoundException {
        return permissionDao.get(entity);
    }

    @Override
    public void updatePermission(PermissionEntity entity) throws DBServiceException {
        permissionDao.update(entity);
    }
}
