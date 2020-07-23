package com.tnd.pw.config.user.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserProfileEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("role")
    private String role;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("company_name")
    private String companyName;
    @SerializedName("domain")
    private String domain;
    @SerializedName("state")
    private Integer state;
    @SerializedName("created_at")
    private Long createdAt;
    @SerializedName("created_by")
    private Long createdBy;
    @SerializedName("updated_at")
    private Long updatedAt;
    @SerializedName("updated_by")
    private Long updatedBy;
}
