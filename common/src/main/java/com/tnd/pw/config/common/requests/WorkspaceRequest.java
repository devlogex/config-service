package com.tnd.pw.config.common.requests;

import com.google.gson.annotations.SerializedName;
import com.tnd.common.api.common.base.authens.WorkspaceTokenRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class WorkspaceRequest extends WorkspaceTokenRequest {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
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
    @SerializedName("is_demote")
    private String isDemote;
    @SerializedName("user_id")
    private Long userId;
}
