package com.tnd.pw.config.common.requests;

import com.google.gson.annotations.SerializedName;
import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.authens.ProductTokenRequest;
import com.tnd.common.api.common.base.authens.TokenRequest;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

@Getter
@Setter
public class ProductRequest extends ProductTokenRequest {
    private static final long serialVersionUID = 1L;


}
