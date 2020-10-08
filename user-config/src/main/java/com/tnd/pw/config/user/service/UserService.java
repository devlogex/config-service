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
    UserProfileEntity createUserProfile(UserProfileEntity entity) throws IOException, DBServiceException;
    List<UserProfileEntity> getUserProfile(UserProfileEntity entity) throws IOException, DBServiceException, UserProfileNotFoundException;
    List<UserProfileEntity> getUserProfile(List<Long> ids) throws IOException, DBServiceException, UserProfileNotFoundException;
    void updateUserProfile(UserProfileEntity entity) throws IOException, DBServiceException;

    UserConfigEntity createUserConfig(UserConfigEntity entity) throws IOException, DBServiceException;
    List<UserConfigEntity> getUserConfig(UserConfigEntity entity) throws IOException, DBServiceException, UserConfigNotFoundException;
    void updateUserConfig(UserConfigEntity entity) throws IOException, DBServiceException;

    PermissionEntity createPermission(PermissionEntity entity) throws IOException, DBServiceException;
    List<PermissionEntity> getPermission(PermissionEntity entity) throws IOException, DBServiceException, PermissionNotFoundException;
    void updatePermission(PermissionEntity entity) throws IOException, DBServiceException;
}
