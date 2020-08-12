package com.tnd.pw.config.runner.handler;

import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.common.representations.CsPackageRepresentation;
import com.tnd.pw.config.common.representations.PackageRepresentation;
import com.tnd.pw.config.common.requests.AdminRequest;
import com.tnd.pw.config.common.requests.AnonymousRequest;
import com.tnd.pw.config.common.requests.ConfigRequest;
import com.tnd.pw.config.common.requests.UserRequest;
import com.tnd.pw.config.common.utils.GsonUtils;
import com.tnd.pw.config.packages.exception.PackageNotFoundException;
import com.tnd.pw.config.runner.service.PackageServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import java.io.IOException;

@HandlerServiceClass
public class PackageHandler implements BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PackageHandler.class);

    @Autowired
    private PackageServiceHandler packageServiceHandler;

    @HandlerService(path = "/config/package/add", protocol = "POST")
    public BaseResponse<CsPackageRepresentation> addPackage(AdminRequest request) throws IOException, DBServiceException, PackageNotFoundException {
        LOGGER.info("[PackageHandler] addPackage() - request: {}", GsonUtils.convertToString(request));
        CsPackageRepresentation response = packageServiceHandler.addPackage(request);
        LOGGER.info("[PackageHandler] addPackage() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/package/update", protocol = "POST")
    public BaseResponse<PackageRepresentation> updatePackage(AdminRequest request) throws IOException, DBServiceException, PackageNotFoundException {
        LOGGER.info("[PackageHandler] updatePackage() - request: {}", GsonUtils.convertToString(request));
        PackageRepresentation response = packageServiceHandler.updatePackage(request);
        LOGGER.info("[PackageHandler] updatePackage() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/package", protocol = "GET")
    public BaseResponse<CsPackageRepresentation> getPackage(AnonymousRequest request) throws IOException, DBServiceException, PackageNotFoundException {
        LOGGER.info("[PackageHandler] getPackage() - request: {}", GsonUtils.convertToString(request));
        CsPackageRepresentation response = packageServiceHandler.getPackage(request);
        LOGGER.info("[PackageHandler] getPackage() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/config/package/register", protocol = "POST")
    public BaseResponse<CsPackageRepresentation> registerPackage(UserRequest request) throws IOException, DBServiceException, PackageNotFoundException, MessagingException {
        LOGGER.info("[PackageHandler] registerPackage() - request: {}", GsonUtils.convertToString(request));
        CsPackageRepresentation response = packageServiceHandler.registerPackage(request);
        LOGGER.info("[PackageHandler] registerPackage() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
