package com.tnd.pw.config.product.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.product.entity.ProductEntity;
import com.tnd.pw.config.product.exception.ProductNotFoundException;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductEntity create(ProductEntity entity) throws IOException, DBServiceException;
    List<ProductEntity> get(ProductEntity entity) throws IOException, DBServiceException, ProductNotFoundException;
    void remove(ProductEntity entity) throws IOException, DBServiceException;
}
