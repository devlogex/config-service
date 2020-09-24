package com.tnd.pw.config.runner.handler;


import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.common.representations.CsProductRepresentation;
import com.tnd.pw.config.common.requests.WorkspaceRequest;
import com.tnd.pw.config.common.utils.GsonUtils;
import com.tnd.pw.config.product.exception.ProductNotFoundException;
import com.tnd.pw.config.runner.service.ProductServiceHandler;
import com.tnd.pw.config.user.exception.PermissionNotFoundException;
import com.tnd.pw.config.user.exception.UserConfigNotFoundException;
import com.tnd.pw.config.user.exception.UserProfileNotFoundException;
import com.tnd.pw.config.workspace.exception.WorkspaceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@HandlerServiceClass
public class ProductHandler implements BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductHandler.class);

    @Autowired
    private ProductServiceHandler productServiceHandler;

    @HandlerService(path = "/config/product/add", protocol = "POST")
    public BaseResponse<CsProductRepresentation> addProduct(WorkspaceRequest request) throws DBServiceException, IOException, ProductNotFoundException, PermissionNotFoundException, UserConfigNotFoundException, WorkspaceNotFoundException, UserProfileNotFoundException {
        LOGGER.info("[ProductHandler] addProduct() - request: {}", GsonUtils.convertToString(request));
        CsProductRepresentation response = productServiceHandler.addProduct(request);
        LOGGER.info("[ProductHandler] addProduct() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/product", protocol = "GET")
    public BaseResponse<CsProductRepresentation> getProduct(WorkspaceRequest request) throws DBServiceException, IOException, ProductNotFoundException {
        LOGGER.info("[ProductHandler] getProduct() - request: {}", GsonUtils.convertToString(request));
        CsProductRepresentation response = productServiceHandler.getProduct(request);
        LOGGER.info("[ProductHandler] getProduct() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/product/remove", protocol = "POST")
    public BaseResponse<CsProductRepresentation> removeProduct(WorkspaceRequest request) throws DBServiceException, IOException, ProductNotFoundException {
        LOGGER.info("[ProductHandler] removeProduct() - request: {}", GsonUtils.convertToString(request));
        CsProductRepresentation response = productServiceHandler.removeProduct(request);
        LOGGER.info("[ProductHandler] removeProduct() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/product/user", protocol = "GET")
    public BaseResponse<CsProductRepresentation> getUserInProduct(WorkspaceRequest request) throws DBServiceException, IOException, UserConfigNotFoundException, ProductNotFoundException, UserProfileNotFoundException {
        LOGGER.info("[ProductHandler] getUserInProduct() - request: {}", GsonUtils.convertToString(request));
        CsProductRepresentation response = productServiceHandler.getUserInProduct(request);
        LOGGER.info("[ProductHandler] getUserInProduct() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
