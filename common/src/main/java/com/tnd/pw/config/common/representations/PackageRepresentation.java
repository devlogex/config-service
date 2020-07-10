package com.tnd.pw.config.common.representations;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class PackageRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private String id;
    @SerializedName("max_product")
    private Integer maxProduct;
    @SerializedName("max_member")
    private Integer maxMember;
    @SerializedName("price")
    private BigDecimal price;
    @SerializedName("state")
    private String state;
    @SerializedName("period_validity")
    private String periodValidity;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("created_by")
    private String createdBy;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("updated_by")
    private String updatedBy;
}
