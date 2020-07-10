package com.tnd.pw.config.packages.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PackageEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("max_product")
    private Integer maxProduct;
    @SerializedName("max_member")
    private Integer maxMember;
    @SerializedName("price")
    private BigDecimal price;
    @SerializedName("state")
    private Integer state;
    @SerializedName("period_validity")
    private Long periodValidity;
    @SerializedName("created_at")
    private Long createdAt;
    @SerializedName("created_by")
    private Long createdBy;
    @SerializedName("updated_at")
    private Long updatedAt;
    @SerializedName("updated_by")
    private Long updatedBy;
}
