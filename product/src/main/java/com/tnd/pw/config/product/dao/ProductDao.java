package com.tnd.pw.config.product.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.product.entity.ProductEntity;
import com.tnd.pw.config.product.exception.ProductNotFoundException;

import java.io.IOException;
import java.util.List;

public interface ProductDao {
    void create(ProductEntity entity) throws DBServiceException;
    List<ProductEntity> get(ProductEntity entity) throws DBServiceException, ProductNotFoundException;
    void remove(ProductEntity entity) throws DBServiceException;
    void update(ProductEntity entity) throws DBServiceException;
}
