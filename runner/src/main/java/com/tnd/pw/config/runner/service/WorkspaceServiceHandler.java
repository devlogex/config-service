package com.tnd.pw.config.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.common.representations.CsWorkspaceRepresentation;
import com.tnd.pw.config.common.representations.WorkspaceRepresentation;
import com.tnd.pw.config.common.requests.AdminRequest;
import com.tnd.pw.config.common.requests.UserRequest;
import com.tnd.pw.config.common.requests.WorkspaceRequest;
import com.tnd.pw.config.packages.exception.InconsistentStateException;
import com.tnd.pw.config.packages.exception.PackageCodeNotFoundException;
import com.tnd.pw.config.packages.exception.PackageNotFoundException;
import com.tnd.pw.config.product.exception.ProductNotFoundException;
import com.tnd.pw.config.runner.exception.InvalidDataException;
import com.tnd.pw.config.runner.exception.PackageCodeExpiredException;
import com.tnd.pw.config.runner.exception.PackageInactiveException;
import com.tnd.pw.config.user.exception.PermissionNotFoundException;
import com.tnd.pw.config.user.exception.UserConfigNotFoundException;
import com.tnd.pw.config.user.exception.UserProfileNotFoundException;
import com.tnd.pw.config.workspace.config.exception.WorkspaceConfigNotFoundException;
import com.tnd.pw.config.workspace.exception.WorkspaceNotFoundException;

import java.io.IOException;

public interface WorkspaceServiceHandler {
    WorkspaceRepresentation addWorkspace(UserRequest request) throws DBServiceException, PackageNotFoundException, PackageCodeNotFoundException, PackageCodeExpiredException, PackageInactiveException, InconsistentStateException;
    CsWorkspaceRepresentation getWorkspace(AdminRequest request) throws DBServiceException, WorkspaceNotFoundException, WorkspaceConfigNotFoundException;
    WorkspaceRepresentation updateWorkspace(WorkspaceRequest request) throws DBServiceException, WorkspaceNotFoundException, WorkspaceConfigNotFoundException;
    WorkspaceRepresentation upgradeWorkspace(WorkspaceRequest request) throws DBServiceException, PackageCodeNotFoundException, PackageCodeExpiredException, PackageNotFoundException, PackageInactiveException, WorkspaceNotFoundException, WorkspaceConfigNotFoundException;

    CsWorkspaceRepresentation addUser(WorkspaceRequest request) throws DBServiceException, UserProfileNotFoundException, PermissionNotFoundException, ProductNotFoundException, InvalidDataException;

    CsWorkspaceRepresentation getWorkspaceOfUser(UserRequest request) throws DBServiceException, UserConfigNotFoundException, WorkspaceNotFoundException, WorkspaceConfigNotFoundException;

    CsWorkspaceRepresentation updateUser(WorkspaceRequest request) throws InvalidDataException, DBServiceException, UserConfigNotFoundException;

    CsWorkspaceRepresentation removeUser(WorkspaceRequest request) throws DBServiceException, UserConfigNotFoundException, InvalidDataException;
}
