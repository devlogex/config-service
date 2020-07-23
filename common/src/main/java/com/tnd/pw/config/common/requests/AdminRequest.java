package com.tnd.pw.config.common.requests;

import com.google.gson.annotations.SerializedName;
import com.tnd.common.api.common.base.authens.TokenRequest;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AdminRequest extends TokenRequest {
    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("max_product")
    private Integer maxProduct;
    @SerializedName("max_member")
    private Integer maxMember;
    @SerializedName("price")
    private BigDecimal price;
    @SerializedName("period_validity")
    private Long periodValidity;
    @SerializedName("state")
    private String state;
}
