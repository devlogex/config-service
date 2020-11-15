package com.tnd.pw.config.sdk.impl;

import com.tnd.common.api.client.service.AbstractService;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.pw.config.common.constants.Methods;
import com.tnd.pw.config.common.representations.CsUserRepresentation;
import com.tnd.pw.config.common.requests.UserRequest;
import com.tnd.pw.config.sdk.ConfigServiceSdkClient;

import java.util.List;

public class ConfigServiceSdkClientImpl extends AbstractService implements ConfigServiceSdkClient {

    public ConfigServiceSdkClientImpl(String host, int port, int appId) {
        super(host, port, appId);
    }

    @Override
    public BaseResponse<CsUserRepresentation> getUserProfile(Long id) {
        UserRequest request = new UserRequest();
        request.setId(id);
        return client.sendRequest(Methods.GET_USER_PROFILE, request);
    }

    @Override
    public BaseResponse<CsUserRepresentation> getUserProfiles(List<Long> userIds) {
        UserRequest request = new UserRequest();
        request.setUserIds(userIds);
        return client.sendRequest(Methods.GET_LIST_USER_PROFILE, request);
    }
}
