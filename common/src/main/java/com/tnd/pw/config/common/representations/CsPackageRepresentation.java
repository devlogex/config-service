package com.tnd.pw.config.common.representations;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

@Getter
@Setter
public class CsPackageRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;
    public CsPackageRepresentation() {
    }
    public CsPackageRepresentation(List<PackageRepresentation> packageReps) {
        this.packageReps = packageReps;
    }

    public CsPackageRepresentation(String code) {
        this.code = code;
    }
    @SerializedName("list_packages")
    private List<PackageRepresentation> packageReps;

    @SerializedName("code")
    private String code;

    @SerializedName("statistical_monthly")
    private TreeMap<String, Long> statisticalMonthly;

    @SerializedName("statistical_quarterly")
    private TreeMap<String, Long> statisticalQuarterly;

}
