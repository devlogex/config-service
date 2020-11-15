package com.tnd.pw.config.sdk;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.pw.config.common.representations.CsUserRepresentation;

import java.util.List;

public interface ConfigServiceSdkClient {
    @Deprecated
    BaseResponse<CsUserRepresentation> getUserProfile(Long userId);

    BaseResponse<CsUserRepresentation> getUserProfiles(List<Long> userIds);
}
