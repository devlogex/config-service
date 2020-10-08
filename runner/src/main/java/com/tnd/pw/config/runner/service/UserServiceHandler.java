package com.tnd.pw.config.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.common.representations.CsUserRepresentation;
import com.tnd.pw.config.common.representations.UserRepresentation;
import com.tnd.pw.config.common.requests.AnonymousRequest;
import com.tnd.pw.config.common.requests.UserRequest;
import com.tnd.pw.config.user.exception.UserConfigNotFoundException;
import com.tnd.pw.config.user.exception.UserProfileNotFoundException;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public interface UserServiceHandler {

    UserRepresentation loginWorkspace(UserRequest request) throws DBServiceException, UserProfileNotFoundException, IOException, LoginException;

    UserRepresentation login(AnonymousRequest request) throws IOException, DBServiceException, LoginException;

    UserRepresentation createUser(AnonymousRequest request) throws IOException, DBServiceException;

    UserRepresentation getCurrentUser(UserRequest request) throws DBServiceException, UserProfileNotFoundException, IOException;

    UserRepresentation updateUserInfo(UserRequest request) throws DBServiceException, UserProfileNotFoundException, IOException;

    CsUserRepresentation getUserProfile(UserRequest request) throws DBServiceException, UserProfileNotFoundException, IOException;

    CsUserRepresentation getUserOfProduct(UserRequest request) throws DBServiceException, UserConfigNotFoundException, IOException;
}
