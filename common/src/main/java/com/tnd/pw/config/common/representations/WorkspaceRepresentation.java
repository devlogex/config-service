package com.tnd.pw.config.common.representations;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class WorkspaceRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private String id;
    @SerializedName("state")
    private String state;
    @SerializedName("owner_id")
    private String ownerId;
    @SerializedName("domain")
    private String domain;
    @SerializedName("config")
    private String config;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("created_by")
    private String createdBy;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("updated_by")
    private String updatedBy;
}
