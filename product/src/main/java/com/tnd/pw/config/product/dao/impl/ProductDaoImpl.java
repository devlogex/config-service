package com.tnd.pw.config.product.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.dbservice.DataHelper;
import com.tnd.pw.config.product.dao.ProductDao;
import com.tnd.pw.config.product.entity.ProductEntity;
import com.tnd.pw.config.product.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO product(id, name, type, parent, workspace_id, created_at, created_by) " +
                    "values(%d, '%s', %d, %d, %d, %d, %d)";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM product WHERE id = %d";
    private static final String SQL_SELECT_BY_WORKSPACE_ID =
            "SELECT * FROM product WHERE workspace_id = %d";
    private static final String SQL_DELETE =
            "DELETE FROM product WHERE id = %d";

    @Override
    public void create(ProductEntity entity) throws IOException, DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getName(), entity.getType(),
                entity.getParent(), entity.getWorkspaceId(), entity.getCreatedAt(),entity.getCreatedBy());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<ProductEntity> get(ProductEntity entity) throws IOException, DBServiceException, ProductNotFoundException {
        String query;
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else {
            query = String.format(SQL_SELECT_BY_WORKSPACE_ID, entity.getWorkspaceId());
        }
        List<ProductEntity> entities = dataHelper.querySQL(query, ProductEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new ProductNotFoundException();
        }
        return entities;
    }

    @Override
    public void remove(ProductEntity entity) throws IOException, DBServiceException {
        String query = String.format(SQL_DELETE, entity.getId());
        dataHelper.executeSQL(query);
    }
}
