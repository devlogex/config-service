package com.tnd.pw.config.user.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.user.entity.UserProfileEntity;
import com.tnd.pw.config.user.exception.UserProfileNotFoundException;

import java.io.IOException;
import java.util.List;

public interface UserProfileDao {
    void create(UserProfileEntity entity) throws IOException, DBServiceException;
    List<UserProfileEntity> get(UserProfileEntity entity) throws IOException, DBServiceException, UserProfileNotFoundException;
    void update(UserProfileEntity entity) throws IOException, DBServiceException;
}
