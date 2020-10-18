package com.tnd.pw.config.user.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.user.entity.PermissionEntity;
import com.tnd.pw.config.user.entity.UserConfigEntity;
import com.tnd.pw.config.user.entity.UserProfileEntity;
import com.tnd.pw.config.user.exception.PermissionNotFoundException;
import com.tnd.pw.config.user.exception.UserConfigNotFoundException;
import com.tnd.pw.config.user.exception.UserProfileNotFoundException;

import java.io.IOException;
import java.util.List;

public interface UserService {
    UserProfileEntity createUserProfile(UserProfileEntity entity) throws DBServiceException;
    List<UserProfileEntity> getUserProfile(UserProfileEntity entity) throws DBServiceException, UserProfileNotFoundException;
    List<UserProfileEntity> getUserProfile(List<Long> ids) throws DBServiceException, UserProfileNotFoundException;
    void updateUserProfile(UserProfileEntity entity) throws DBServiceException;

    UserConfigEntity createUserConfig(UserConfigEntity entity) throws DBServiceException;
    List<UserConfigEntity> getUserConfig(UserConfigEntity entity) throws DBServiceException, UserConfigNotFoundException;
    void updateUserConfig(UserConfigEntity entity) throws DBServiceException;

    PermissionEntity createPermission(PermissionEntity entity) throws DBServiceException;
    List<PermissionEntity> getPermission(PermissionEntity entity) throws DBServiceException, PermissionNotFoundException;
    void updatePermission(PermissionEntity entity) throws DBServiceException;
}
