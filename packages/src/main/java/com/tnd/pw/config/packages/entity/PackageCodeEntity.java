package com.tnd.pw.config.packages.entity;

import com.google.gson.annotations.SerializedName;
import com.tnd.pw.config.packages.enums.PackageCodeState;
import com.tnd.pw.config.packages.exception.InconsistentStateException;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PackageCodeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Setter
    @SerializedName("id")
    private Long id;
    @Setter
    @SerializedName("package_id")
    private Long packageId;
    @Setter
    @SerializedName("expire_time")
    private Long expireTime;
    @SerializedName("state")
    private Integer state;
    @Setter
    @SerializedName("price")
    private BigDecimal price;
    @Setter
    @SerializedName("created_at")
    private Long createdAt;
    @Setter
    @SerializedName("created_by")
    private Long createdBy;
    @Setter
    @SerializedName("updated_at")
    private Long updatedAt;
    @Setter
    @SerializedName("updated_by")
    private Long updatedBy;

    public void setState(Integer state) throws InconsistentStateException {
        if(this.state == PackageCodeState.ACTIVE.ordinal() && state == PackageCodeState.INACTIVE.ordinal()) {
            this.state = state;
        }
        else {
            throw new InconsistentStateException();
        }
    }
}
