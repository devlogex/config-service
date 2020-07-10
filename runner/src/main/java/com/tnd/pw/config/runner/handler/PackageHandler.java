package com.tnd.pw.config.runner.handler;

import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.common.representations.CsPackageRepresentation;
import com.tnd.pw.config.common.representations.PackageRepresentation;
import com.tnd.pw.config.common.requests.ConfigRequest;
import com.tnd.pw.config.common.utils.GsonUtils;
import com.tnd.pw.config.packages.exception.PackageNotFoundException;
import com.tnd.pw.config.runner.service.PackageServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@HandlerServiceClass
public class PackageHandler implements BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PackageHandler.class);

    @Autowired
    private PackageServiceHandler packageServiceHandler;

    @HandlerService(path = "/config/package/add", protocol = "POST",
            dataRequestType = "com.tnd.pw.config.common.requests.ConfigRequest")
    public BaseResponse<CsPackageRepresentation> addPackage(BaseRequest<ConfigRequest> request) throws IOException, DBServiceException, PackageNotFoundException {
        LOGGER.info("[PackageHandler] addPackage() - request: {}", GsonUtils.convertToString(request));
        CsPackageRepresentation response = packageServiceHandler.addPackage(request.getData());
        LOGGER.info("[PackageHandler] addPackage() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/package/update", protocol = "POST",
            dataRequestType = "com.tnd.pw.config.common.requests.ConfigRequest")
    public BaseResponse<PackageRepresentation> updatePackage(BaseRequest<ConfigRequest> request) throws IOException, DBServiceException, PackageNotFoundException {
        LOGGER.info("[PackageHandler] updatePackage() - request: {}", GsonUtils.convertToString(request));
        PackageRepresentation response = packageServiceHandler.updatePackage(request.getData());
        LOGGER.info("[PackageHandler] updatePackage() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/package", protocol = "GET",
            dataRequestType = "com.tnd.pw.config.common.requests.ConfigRequest")
    public BaseResponse<CsPackageRepresentation> getPackage(BaseRequest<ConfigRequest> request) throws IOException, DBServiceException, PackageNotFoundException {
        LOGGER.info("[PackageHandler] getPackage() - request: {}", GsonUtils.convertToString(request));
        CsPackageRepresentation response = packageServiceHandler.getPackage(request.getData());
        LOGGER.info("[PackageHandler] getPackage() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/package/register", protocol = "POST",
            dataRequestType = "com.tnd.pw.config.common.requests.ConfigRequest")
    public BaseResponse<CsPackageRepresentation> registerPackage(BaseRequest<ConfigRequest> request) throws IOException, DBServiceException, PackageNotFoundException {
        LOGGER.info("[PackageHandler] registerPackage() - request: {}", GsonUtils.convertToString(request));
        CsPackageRepresentation response = packageServiceHandler.registerPackage(request.getData());
        LOGGER.info("[PackageHandler] registerPackage() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
