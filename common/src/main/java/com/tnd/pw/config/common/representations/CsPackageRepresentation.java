package com.tnd.pw.config.common.representations;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class CsPackageRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;

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
}
