package com.tnd.pw.config.runner.handler;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.common.constants.Methods;
import com.tnd.pw.config.common.representations.CsUserRepresentation;
import com.tnd.pw.config.common.representations.UserRepresentation;
import com.tnd.pw.config.common.requests.AnonymousRequest;
import com.tnd.pw.config.common.requests.UserRequest;
import com.tnd.pw.config.common.utils.GsonUtils;
import com.tnd.pw.config.runner.service.UserServiceHandler;
import com.tnd.pw.config.user.exception.UserConfigNotFoundException;
import com.tnd.pw.config.user.exception.UserProfileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.security.auth.login.LoginException;
import java.io.IOException;

@HandlerServiceClass
public class UserHandler implements BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserHandler.class);

    @Autowired
    private UserServiceHandler userServiceHandler;

    @HandlerService(path = "/config/user/login/workspace", protocol = "POST")
    public BaseResponse<UserRepresentation> loginWorkspace(UserRequest request) throws LoginException, UserProfileNotFoundException, DBServiceException {
        LOGGER.info("[UserHandler] loginWorkspace() - request: {}", GsonUtils.convertToString(request));
        UserRepresentation response = userServiceHandler.loginWorkspace(request);
        LOGGER.info("[UserHandler] loginWorkspace() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/user/login", protocol = "POST")
    public BaseResponse<UserRepresentation> login(AnonymousRequest request) throws DBServiceException, LoginException {
        LOGGER.info("[UserHandler] login() - request: {}", GsonUtils.convertToString(request));
        UserRepresentation response = userServiceHandler.login(request);
        LOGGER.info("[UserHandler] login() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/user/register", protocol = "POST")
    public BaseResponse<UserRepresentation> registerUser(AnonymousRequest request) throws DBServiceException {
        LOGGER.info("[UserHandler] registerUser() - request: {}", GsonUtils.convertToString(request));
        UserRepresentation response = userServiceHandler.createUser(request);
        LOGGER.info("[UserHandler] registerUser() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/user/current", protocol = "GET")
    public BaseResponse<UserRepresentation> getCurrentUser(UserRequest request) throws DBServiceException, UserProfileNotFoundException {
        LOGGER.info("[UserHandler] getCurrentUser() - request: {}", GsonUtils.convertToString(request));
        UserRepresentation response = userServiceHandler.getCurrentUser(request);
        LOGGER.info("[UserHandler] getCurrentUser() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/user", protocol = "POST")
    public BaseResponse<UserRepresentation> updateUserInfo(UserRequest request) throws DBServiceException, UserProfileNotFoundException {
        LOGGER.info("[UserHandler] updateUserInfo() - request: {}", GsonUtils.convertToString(request));
        UserRepresentation response = userServiceHandler.updateUserInfo(request);
        LOGGER.info("[UserHandler] updateUserInfo() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(method = Methods.GET_USER_PROFILE, path = "/config/user/profile", protocol = "GET")
    public BaseResponse<CsUserRepresentation> getUserProfile(UserRequest request) throws DBServiceException, UserProfileNotFoundException {
        LOGGER.info("[UserHandler] getUserProfile() - request: {}", GsonUtils.convertToString(request));
        CsUserRepresentation response = userServiceHandler.getUserProfile(request);
        LOGGER.info("[UserHandler] getUserProfile() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(method = Methods.GET_LIST_USER_PROFILE, path = "/config/user/profile", protocol = "GET")
    public BaseResponse<CsUserRepresentation> getUserProfiles(UserRequest request) throws DBServiceException {
        LOGGER.info("[UserHandler] getUserProfiles() - request: {}", GsonUtils.convertToString(request));
        CsUserRepresentation response = userServiceHandler.getUserProfiles(request);
        LOGGER.info("[UserHandler] getUserProfiles() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/user/product", protocol = "GET")
    public BaseResponse<CsUserRepresentation> getUserOfProduct(UserRequest request) throws DBServiceException, UserProfileNotFoundException, UserConfigNotFoundException {
        LOGGER.info("[UserHandler] getUserOfProduct() - request: {}", GsonUtils.convertToString(request));
        CsUserRepresentation response = userServiceHandler.getUserOfProduct(request);
        LOGGER.info("[UserHandler] getUserOfProduct() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
