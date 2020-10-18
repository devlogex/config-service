package com.tnd.pw.config.sdk;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.pw.config.common.representations.CsUserRepresentation;

public interface ConfigServiceSdkClient {
    BaseResponse<CsUserRepresentation> getUserProfile(Long userId);
}
