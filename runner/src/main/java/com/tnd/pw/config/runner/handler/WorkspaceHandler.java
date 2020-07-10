package com.tnd.pw.config.runner.handler;

import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.common.representations.CsWorkspaceRepresentation;
import com.tnd.pw.config.common.representations.WorkspaceRepresentation;
import com.tnd.pw.config.common.requests.ConfigRequest;
import com.tnd.pw.config.common.utils.GsonUtils;
import com.tnd.pw.config.packages.exception.InconsistentStateException;
import com.tnd.pw.config.packages.exception.PackageCodeNotFoundException;
import com.tnd.pw.config.packages.exception.PackageNotFoundException;
import com.tnd.pw.config.runner.exception.PackageCodeExpiredException;
import com.tnd.pw.config.runner.exception.PackageInactiveException;
import com.tnd.pw.config.runner.service.WorkspaceServiceHandler;
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

    @HandlerService(path = "/config/workspace/add", protocol = "POST",
            dataRequestType = "com.tnd.pw.config.common.requests.ConfigRequest")
    public BaseResponse<WorkspaceRepresentation> addWorkspace(BaseRequest<ConfigRequest> request) throws PackageNotFoundException, IOException, InconsistentStateException, DBServiceException, PackageCodeNotFoundException, PackageCodeExpiredException, PackageInactiveException {
        LOGGER.info("[WorkspaceHandler] addWorkspace() - request: {}", GsonUtils.convertToString(request));
        WorkspaceRepresentation response = workspaceServiceHandler.addWorkspace(request.getData());
        LOGGER.info("[WorkspaceHandler] addWorkspace() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/workspace", protocol = "GET",
            dataRequestType = "com.tnd.pw.config.common.requests.ConfigRequest")
    public BaseResponse<CsWorkspaceRepresentation> getWorkspace(BaseRequest<ConfigRequest> request) throws WorkspaceConfigNotFoundException, WorkspaceNotFoundException, DBServiceException, IOException {
        LOGGER.info("[WorkspaceHandler] getWorkspace() - request: {}", GsonUtils.convertToString(request));
        CsWorkspaceRepresentation response = workspaceServiceHandler.getWorkspace(request.getData());
        LOGGER.info("[WorkspaceHandler] getWorkspace() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/workspace/update", protocol = "POST",
            dataRequestType = "com.tnd.pw.config.common.requests.ConfigRequest")
    public BaseResponse<WorkspaceRepresentation> updateWorkspace(BaseRequest<ConfigRequest> request) throws WorkspaceConfigNotFoundException, WorkspaceNotFoundException, DBServiceException, IOException {
        LOGGER.info("[WorkspaceHandler] updateWorkspace() - request: {}", GsonUtils.convertToString(request));
        WorkspaceRepresentation response = workspaceServiceHandler.updateWorkspace(request.getData());
        LOGGER.info("[WorkspaceHandler] updateWorkspace() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/workspace/upgrade", protocol = "POST",
            dataRequestType = "com.tnd.pw.config.common.requests.ConfigRequest")
    public BaseResponse<WorkspaceRepresentation> upgradeWorkspace(BaseRequest<ConfigRequest> request) throws WorkspaceConfigNotFoundException, WorkspaceNotFoundException, PackageCodeNotFoundException, IOException, DBServiceException, PackageCodeExpiredException, PackageNotFoundException, PackageInactiveException {
        LOGGER.info("[WorkspaceHandler] upgradeWorkspace() - request: {}", GsonUtils.convertToString(request));
        WorkspaceRepresentation response = workspaceServiceHandler.upgradeWorkspace(request.getData());
        LOGGER.info("[WorkspaceHandler] upgradeWorkspace() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
