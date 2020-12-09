package com.tnd.pw.config.common.representations;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class StatisticalInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("month")
    private Integer month;
    @SerializedName("year")
    private Integer year;
    @SerializedName("count_sale")
    private Long countSale;
    @SerializedName("money_in")
    private BigDecimal moneyIn;
}