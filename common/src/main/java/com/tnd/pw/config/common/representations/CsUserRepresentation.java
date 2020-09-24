package com.tnd.pw.config.common.representations;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class CsUserRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("list_user_profile")
    private List<UserRepresentation> userProfiles;

    public CsUserRepresentation() {
    }

    public CsUserRepresentation(List<UserRepresentation> userProfiles) {
        this.userProfiles = userProfiles;
    }
}
