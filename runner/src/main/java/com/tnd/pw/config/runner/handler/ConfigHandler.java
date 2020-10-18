package com.tnd.pw.config.runner.handler;

import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.pw.config.common.representations.CsPackageRepresentation;

@HandlerServiceClass
public class ConfigHandler  implements BaseHandler {

    @HandlerService(path = "/", protocol = "GET")
    public BaseResponse<CsPackageRepresentation> check(BaseRequest request) {
        return new BaseResponse<>(null);
    }
}
