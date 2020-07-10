package com.tnd.pw.config.common.representations;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class CsProductRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("list_products")
    private Node<ProductRepresentation> productReps;

    public CsProductRepresentation(Node<ProductRepresentation> productReps) {
        this.productReps = productReps;
    }
}
