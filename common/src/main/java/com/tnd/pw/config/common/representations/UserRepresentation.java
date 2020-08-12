package com.tnd.pw.config.common.representations;

import com.google.gson.annotations.SerializedName;
import com.tnd.common.api.common.base.authens.UserPermission;
import com.tnd.pw.config.user.entity.UserProfileEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
public class UserRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("token")
    private String token;
    @SerializedName("id")
    private Long id;
    @SerializedName("email")
    private String email;
    @SerializedName("role")
    private String role;
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
    @SerializedName("product_permissions")
    private HashMap<Long, String> productPermissions;
}
