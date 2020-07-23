package com.tnd.pw.config.runner.handler;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.common.representations.CsWorkspaceRepresentation;
import com.tnd.pw.config.common.representations.WorkspaceRepresentation;
import com.tnd.pw.config.common.requests.ConfigRequest;
import com.tnd.pw.config.common.requests.UserRequest;
import com.tnd.pw.config.common.utils.GsonUtils;
import com.tnd.pw.config.packages.exception.InconsistentStateException;
import com.tnd.pw.config.packages.exception.PackageCodeNotFoundException;
import com.tnd.pw.config.packages.exception.PackageNotFoundException;
import com.tnd.pw.config.product.exception.ProductNotFoundException;
import com.tnd.pw.config.runner.exception.InvalidDataException;
import com.tnd.pw.config.runner.exception.PackageCodeExpiredException;
import com.tnd.pw.config.runner.exception.PackageInactiveException;
import com.tnd.pw.config.runner.service.WorkspaceServiceHandler;
import com.tnd.pw.config.user.exception.PermissionNotFoundException;
import com.tnd.pw.config.user.exception.UserConfigNotFoundException;
import com.tnd.pw.config.user.exception.UserProfileNotFoundException;
import com.tnd.pw.config.workspace.config.exception.WorkspaceConfigNotFoundException;
import com.tnd.pw.config.workspace.exception.WorkspaceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@HandlerServiceClass
public class WorkspaceHandler implements BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkspaceHandler.class);

    @Autowired
    private WorkspaceServiceHandler workspaceServiceHandler;

    @HandlerService(path = "/config/workspace/add", protocol = "POST")
    public BaseResponse<WorkspaceRepresentation> addWorkspace(UserRequest request) throws PackageNotFoundException, IOException, InconsistentStateException, DBServiceException, PackageCodeNotFoundException, PackageCodeExpiredException, PackageInactiveException {
        LOGGER.info("[WorkspaceHandler] addWorkspace() - request: {}", GsonUtils.convertToString(request));
        WorkspaceRepresentation response = workspaceServiceHandler.addWorkspace(request);
        LOGGER.info("[WorkspaceHandler] addWorkspace() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/workspace/user", protocol = "GET")
    public BaseResponse<CsWorkspaceRepresentation> getWorkspaceOfUser(UserRequest request) throws WorkspaceConfigNotFoundException, WorkspaceNotFoundException, DBServiceException, IOException, UserConfigNotFoundException {
        LOGGER.info("[WorkspaceHandler] getWorkspaceOfUser() - request: {}", GsonUtils.convertToString(request));
        CsWorkspaceRepresentation response = workspaceServiceHandler.getWorkspaceOfUser(request);
        LOGGER.info("[WorkspaceHandler] getWorkspaceOfUser() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/workspace", protocol = "GET")
    public BaseResponse<CsWorkspaceRepresentation> getWorkspace(ConfigRequest request) throws WorkspaceConfigNotFoundException, WorkspaceNotFoundException, DBServiceException, IOException {
        LOGGER.info("[WorkspaceHandler] getWorkspace() - request: {}", GsonUtils.convertToString(request));
        CsWorkspaceRepresentation response = workspaceServiceHandler.getWorkspace(request);
        LOGGER.info("[WorkspaceHandler] getWorkspace() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/workspace/update", protocol = "POST")
    public BaseResponse<WorkspaceRepresentation> updateWorkspace(ConfigRequest request) throws WorkspaceConfigNotFoundException, WorkspaceNotFoundException, DBServiceException, IOException {
        LOGGER.info("[WorkspaceHandler] updateWorkspace() - request: {}", GsonUtils.convertToString(request));
        WorkspaceRepresentation response = workspaceServiceHandler.updateWorkspace(request);
        LOGGER.info("[WorkspaceHandler] updateWorkspace() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/workspace/upgrade", protocol = "POST")
    public BaseResponse<WorkspaceRepresentation> upgradeWorkspace(ConfigRequest request) throws WorkspaceConfigNotFoundException, WorkspaceNotFoundException, PackageCodeNotFoundException, IOException, DBServiceException, PackageCodeExpiredException, PackageNotFoundException, PackageInactiveException {
        LOGGER.info("[WorkspaceHandler] upgradeWorkspace() - request: {}", GsonUtils.convertToString(request));
        WorkspaceRepresentation response = workspaceServiceHandler.upgradeWorkspace(request);
        LOGGER.info("[WorkspaceHandler] upgradeWorkspace() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/workspace/add_user", protocol = "POST")
    public BaseResponse<CsWorkspaceRepresentation> addUserToWorkspace(ConfigRequest request) throws UserProfileNotFoundException, ProductNotFoundException, IOException, DBServiceException, PermissionNotFoundException, InvalidDataException {
        LOGGER.info("[WorkspaceHandler] addUserToWorkspace() - request: {}", GsonUtils.convertToString(request));
        CsWorkspaceRepresentation response = workspaceServiceHandler.addUser(request);
        LOGGER.info("[WorkspaceHandler] addUserToWorkspace() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
