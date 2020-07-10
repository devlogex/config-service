package com.tnd.pw.config.common.representations;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class CsWorkspaceRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;

    public CsWorkspaceRepresentation(List<WorkspaceRepresentation> workspaceReps) {
        this.workspaceReps = workspaceReps;
    }

    @SerializedName("list_workspaces")
    private List<WorkspaceRepresentation> workspaceReps;
}
