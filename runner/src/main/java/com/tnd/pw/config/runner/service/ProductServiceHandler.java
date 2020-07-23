package com.tnd.pw.config.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.common.representations.CsProductRepresentation;
import com.tnd.pw.config.common.representations.ProductRepresentation;
import com.tnd.pw.config.common.requests.ConfigRequest;
import com.tnd.pw.config.product.exception.ProductNotFoundException;
import com.tnd.pw.config.user.exception.PermissionNotFoundException;
import com.tnd.pw.config.user.exception.UserConfigNotFoundException;
import com.tnd.pw.config.workspace.exception.WorkspaceNotFoundException;

import java.io.IOException;

public interface ProductServiceHandler {
    CsProductRepresentation addProduct(ConfigRequest request) throws IOException, DBServiceException, ProductNotFoundException, UserConfigNotFoundException, PermissionNotFoundException, WorkspaceNotFoundException;
    CsProductRepresentation getProduct(ConfigRequest request) throws DBServiceException, IOException, ProductNotFoundException;
    CsProductRepresentation removeProduct(ConfigRequest request) throws IOException, DBServiceException, ProductNotFoundException;
}
