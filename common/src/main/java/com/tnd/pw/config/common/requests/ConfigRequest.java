package com.tnd.pw.config.common.requests;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class ConfigRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("max_product")
    private Integer maxProduct;
    @SerializedName("max_member")
    private Integer maxMember;
    @SerializedName("price")
    private BigDecimal price;
    @SerializedName("state")
    private String state;
    @SerializedName("type")
    private String type;
    @SerializedName("config_id")
    private Long configId;
    @SerializedName("parent_id")
    private Long parentId;
    @SerializedName("workspace_id")
    private Long workspaceId;
    @SerializedName("owner_id")
    private String ownerId;
    @SerializedName("domain")
    private String domain;
    @SerializedName("package_id")
    private Long packageId;
    @SerializedName("period_validity")
    private Long periodValidity;
    @SerializedName("code")
    private Long code;
}
