package com.tnd.pw.config.common.requests;

import com.google.gson.annotations.SerializedName;
import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.authens.TokenRequest;
import com.tnd.common.api.common.base.authens.UserTokenRequest;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

@Getter
@Setter
public class ConfigRequest extends UserTokenRequest {
    private static final long serialVersionUID = 1L;

    @SerializedName("name")
    private String name;
    @SerializedName("state")
    private String state;
    @SerializedName("type")
    private String type;
    @SerializedName("config_id")
    private Long configId;
    @SerializedName("parent_id")
    private Long parentId;
    @SerializedName("owner_id")
    private String ownerId;
    @SerializedName("domain")
    private String domain;
    @SerializedName("package_id")
    private Long packageId;
    @SerializedName("code")
    private Long code;
    @SerializedName("email")
    private String email;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("permission")
    private HashMap<Long, String> permission;
}
