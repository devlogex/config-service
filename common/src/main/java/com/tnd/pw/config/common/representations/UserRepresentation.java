package com.tnd.pw.config.common.representations;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class UserRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("token")
    private String token;
}
