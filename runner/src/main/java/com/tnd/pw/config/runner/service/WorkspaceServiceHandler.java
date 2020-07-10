package com.tnd.pw.config.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.common.representations.CsWorkspaceRepresentation;
import com.tnd.pw.config.common.representations.WorkspaceRepresentation;
import com.tnd.pw.config.common.requests.ConfigRequest;
import com.tnd.pw.config.packages.exception.InconsistentStateException;
import com.tnd.pw.config.packages.exception.PackageCodeNotFoundException;
import com.tnd.pw.config.packages.exception.PackageNotFoundException;
import com.tnd.pw.config.runner.exception.PackageCodeExpiredException;
import com.tnd.pw.config.runner.exception.PackageInactiveException;
import com.tnd.pw.config.workspace.config.exception.WorkspaceConfigNotFoundException;
import com.tnd.pw.config.workspace.exception.WorkspaceNotFoundException;

import java.io.IOException;

public interface WorkspaceServiceHandler {
    WorkspaceRepresentation addWorkspace(ConfigRequest request) throws IOException, DBServiceException, PackageNotFoundException, PackageCodeNotFoundException, PackageCodeExpiredException, PackageInactiveException, InconsistentStateException;
    CsWorkspaceRepresentation getWorkspace(ConfigRequest request) throws DBServiceException, WorkspaceNotFoundException, IOException, WorkspaceConfigNotFoundException;
    WorkspaceRepresentation updateWorkspace(ConfigRequest request) throws DBServiceException, WorkspaceNotFoundException, IOException, WorkspaceConfigNotFoundException;
    WorkspaceRepresentation upgradeWorkspace(ConfigRequest request) throws DBServiceException, IOException, PackageCodeNotFoundException, PackageCodeExpiredException, PackageNotFoundException, PackageInactiveException, WorkspaceNotFoundException, WorkspaceConfigNotFoundException;
}
