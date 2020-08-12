package com.tnd.pw.config.common.requests;

import com.google.gson.annotations.SerializedName;
import com.tnd.common.api.common.base.BaseRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnonymousRequest extends BaseRequest {

    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("name")
    private String name;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("company_name")
    private String companyName;
    @SerializedName("domain")
    private String domain;

    @SerializedName("id")
    private Long id;
    @SerializedName("state")
    private String state;
}
