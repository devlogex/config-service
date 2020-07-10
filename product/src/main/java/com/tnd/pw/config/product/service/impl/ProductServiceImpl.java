package com.tnd.pw.config.product.service.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.product.dao.ProductDao;
import com.tnd.pw.config.product.entity.ProductEntity;
import com.tnd.pw.config.product.exception.ProductNotFoundException;
import com.tnd.pw.config.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public ProductEntity create(ProductEntity entity) throws IOException, DBServiceException {
        entity.setId(System.currentTimeMillis());
        entity.setCreatedAt(System.currentTimeMillis());
        productDao.create(entity);
        return entity;
    }

    @Override
    public List<ProductEntity> get(ProductEntity entity) throws IOException, DBServiceException, ProductNotFoundException {
        return productDao.get(entity);
    }

    @Override
    public void remove(ProductEntity entity) throws IOException, DBServiceException {
        productDao.remove(entity);
    }
}
