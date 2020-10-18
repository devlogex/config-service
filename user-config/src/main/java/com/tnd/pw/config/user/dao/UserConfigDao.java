package com.tnd.pw.config.user.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.user.entity.UserConfigEntity;
import com.tnd.pw.config.user.exception.UserConfigNotFoundException;
import com.tnd.pw.config.user.exception.UserProfileNotFoundException;

import java.io.IOException;
import java.util.List;

public interface UserConfigDao {
    void create(UserConfigEntity entity) throws DBServiceException;
    List<UserConfigEntity> get(UserConfigEntity entity) throws DBServiceException, UserConfigNotFoundException;
    void update(UserConfigEntity entity) throws DBServiceException;
}
