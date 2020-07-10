package com.tnd.pw.config.runner.handler;


import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.common.representations.CsProductRepresentation;
import com.tnd.pw.config.common.requests.ConfigRequest;
import com.tnd.pw.config.common.utils.GsonUtils;
import com.tnd.pw.config.product.exception.ProductNotFoundException;
import com.tnd.pw.config.runner.service.ProductServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@HandlerServiceClass
public class ProductHandler implements BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductHandler.class);

    @Autowired
    private ProductServiceHandler productServiceHandler;

    @HandlerService(path = "/config/product/add", protocol = "POST",
            dataRequestType = "com.tnd.pw.config.common.requests.ConfigRequest")
    public BaseResponse<CsProductRepresentation> addProduct(BaseRequest<ConfigRequest> request) throws DBServiceException, IOException, ProductNotFoundException {
        LOGGER.info("[ProductHandler] addProduct() - request: {}", GsonUtils.convertToString(request));
        CsProductRepresentation response = productServiceHandler.addProduct(request.getData());
        LOGGER.info("[ProductHandler] addProduct() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/product", protocol = "GET",
            dataRequestType = "com.tnd.pw.config.common.requests.ConfigRequest")
    public BaseResponse<CsProductRepresentation> getProduct(BaseRequest<ConfigRequest> request) throws DBServiceException, IOException, ProductNotFoundException {
        LOGGER.info("[ProductHandler] getProduct() - request: {}", GsonUtils.convertToString(request));
        CsProductRepresentation response = productServiceHandler.getProduct(request.getData());
        LOGGER.info("[ProductHandler] getProduct() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/product/remove", protocol = "POST",
            dataRequestType = "com.tnd.pw.config.common.requests.ConfigRequest")
    public BaseResponse<CsProductRepresentation> removeProduct(BaseRequest<ConfigRequest> request) throws DBServiceException, IOException, ProductNotFoundException {
        LOGGER.info("[ProductHandler] removeProduct() - request: {}", GsonUtils.convertToString(request));
        CsProductRepresentation response = productServiceHandler.removeProduct(request.getData());
        LOGGER.info("[ProductHandler] removeProduct() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
