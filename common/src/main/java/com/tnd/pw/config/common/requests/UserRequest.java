package com.tnd.pw.config.common.requests;

import com.google.gson.annotations.SerializedName;
import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.authens.TokenRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest extends TokenRequest {
    @SerializedName("workspace_id")
    private Long workspaceId;
    @SerializedName("code")
    private Long code;
    @SerializedName("id")
    private Long id;

    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("password")
    private String password;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("company_name")
    private String companyName;
}
