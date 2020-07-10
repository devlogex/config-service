package com.tnd.pw.config.runner.service.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.common.representations.CsProductRepresentation;
import com.tnd.pw.config.common.representations.ProductRepresentation;
import com.tnd.pw.config.common.requests.ConfigRequest;
import com.tnd.pw.config.common.utils.RepresentationBuilder;
import com.tnd.pw.config.product.entity.ProductEntity;
import com.tnd.pw.config.product.enums.ProductType;
import com.tnd.pw.config.product.exception.ProductNotFoundException;
import com.tnd.pw.config.product.service.ProductService;
import com.tnd.pw.config.runner.service.ProductServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class ProductServiceHandlerImpl implements ProductServiceHandler {
    @Autowired
    private ProductService productService;

    @Override
    public CsProductRepresentation addProduct(ConfigRequest request) throws IOException, DBServiceException, ProductNotFoundException {
//        ProductEntity productEntity = productService.create(
//                ProductEntity.builder()
//                        .type(ProductType.valueOf(request.getType()).ordinal())
//                        .parent(request.getParentId())
//                        .workspaceId(request.getWorkspaceId())
//                        .name(request.getName())
//                        .createdBy(1L)
//                        .build()
//        );
//        List<ProductEntity> productEntities = productService.get(ProductEntity.builder().workspaceId(productEntity.getWorkspaceId()).build());
//        return RepresentationBuilder.buildListProductRep(productEntities);
    }

    @Override
    public CsProductRepresentation getProduct(ConfigRequest request) throws DBServiceException, IOException, ProductNotFoundException {
        List<ProductEntity> productEntities = productService.get(
                ProductEntity.builder()
                        .id(request.getId())
                        .workspaceId(request.getWorkspaceId())
                        .build()
        );
        return RepresentationBuilder.buildListProductRep(productEntities);
    }

    @Override
    public CsProductRepresentation removeProduct(ConfigRequest request) throws IOException, DBServiceException, ProductNotFoundException {
//        ProductEntity productEntity = productService.get(ProductEntity.builder().id(request.getId()).build()).get(0);
//        productService.remove(productEntity);
//        List<ProductEntity> productEntities = productService.get(ProductEntity.builder().workspaceId(productEntity.getWorkspaceId()).build());
//        return RepresentationBuilder.buildListProductRep(productEntities);
    }
}
