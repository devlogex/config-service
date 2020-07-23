package com.tnd.pw.config.user.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserConfigEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("user_id")
    private Long userId;
    @SerializedName("workspace_id")
    private Long workspaceId;
    @SerializedName("workspace_permissions")
    private String workspacePermissions;
    @SerializedName("product_permissions")
    private String productPermissions;
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
