package com.tnd.pw.config.user.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PermissionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("permissions")
    private List<String> permissions;
}
