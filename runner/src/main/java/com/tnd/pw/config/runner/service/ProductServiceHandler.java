package com.tnd.pw.config.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.common.representations.CsProductRepresentation;
import com.tnd.pw.config.common.representations.ProductRepresentation;
import com.tnd.pw.config.common.requests.ProductRequest;
import com.tnd.pw.config.common.requests.WorkspaceRequest;
import com.tnd.pw.config.product.exception.ProductNotFoundException;
import com.tnd.pw.config.user.exception.PermissionNotFoundException;
import com.tnd.pw.config.user.exception.UserConfigNotFoundException;
import com.tnd.pw.config.user.exception.UserProfileNotFoundException;
import com.tnd.pw.config.workspace.exception.WorkspaceNotFoundException;

import java.io.IOException;

public interface ProductServiceHandler {
    CsProductRepresentation addProduct(WorkspaceRequest request) throws DBServiceException, ProductNotFoundException, UserConfigNotFoundException, PermissionNotFoundException, WorkspaceNotFoundException, UserProfileNotFoundException;
    ProductRepresentation updateProduct(ProductRequest request) throws DBServiceException, ProductNotFoundException;
    CsProductRepresentation getProduct(WorkspaceRequest request) throws DBServiceException, ProductNotFoundException;
    CsProductRepresentation removeProduct(WorkspaceRequest request) throws DBServiceException, ProductNotFoundException, UserConfigNotFoundException;
    CsProductRepresentation getUserInProduct(WorkspaceRequest request) throws DBServiceException, UserConfigNotFoundException, UserProfileNotFoundException, ProductNotFoundException;
}
