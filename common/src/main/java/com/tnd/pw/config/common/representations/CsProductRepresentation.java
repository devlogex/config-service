package com.tnd.pw.config.common.representations;

import com.google.gson.annotations.SerializedName;
import com.tnd.common.api.common.base.authens.UserPermission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class CsProductRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("list_products")
    private Node<ProductRepresentation> productReps;
    @SerializedName("new_token")
    private String token;
    @SerializedName("product_permissions")
    private HashMap<Long, String> productPermissions;
    @SerializedName("user_permissions")
    private HashMap<String, HashMap<String, String>> userPermissions;

    public CsProductRepresentation(Node<ProductRepresentation> productReps) {
        this.productReps = productReps;
    }

    public CsProductRepresentation(HashMap<String, HashMap<String, String>> userPermissions) {
        this.userPermissions = userPermissions;
    }

    public CsProductRepresentation(Node<ProductRepresentation> productReps, String token, HashMap<Long, String> productPermissions) {
        this.productReps = productReps;
        this.token = token;
        this.productPermissions = productPermissions;
    }
}
