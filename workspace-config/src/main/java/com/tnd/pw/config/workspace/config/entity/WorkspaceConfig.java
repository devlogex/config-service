package com.tnd.pw.config.workspace.config.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class WorkspaceConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("max_product")
    private Integer maxProduct;
    @SerializedName("max_member")
    private Integer maxMember;
    @SerializedName("price")
    private BigDecimal price;
    @SerializedName("state")
    private Integer state;
    @SerializedName("expire_time")
    private Long expireTime;
}
