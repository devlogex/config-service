package com.tnd.pw.config.runner.service.impl;

import com.google.common.reflect.TypeToken;
import com.tnd.common.api.common.base.authens.UserPermission;
import com.tnd.common.cache.redis.CacheHelper;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.common.constants.CacheKeys;
import com.tnd.pw.config.common.representations.CsProductRepresentation;
import com.tnd.pw.config.common.requests.ConfigRequest;
import com.tnd.pw.config.common.utils.GsonUtils;
import com.tnd.pw.config.common.utils.RepresentationBuilder;
import com.tnd.pw.config.product.entity.ProductEntity;
import com.tnd.pw.config.product.enums.ProductType;
import com.tnd.pw.config.product.exception.ProductNotFoundException;
import com.tnd.pw.config.product.service.ProductService;
import com.tnd.pw.config.runner.service.ProductServiceHandler;
import com.tnd.pw.config.user.constants.UserPermissions;
import com.tnd.pw.config.user.constants.UserWPRoles;
import com.tnd.pw.config.user.entity.PermissionEntity;
import com.tnd.pw.config.user.entity.UserConfigEntity;
import com.tnd.pw.config.user.exception.PermissionNotFoundException;
import com.tnd.pw.config.user.exception.UserConfigNotFoundException;
import com.tnd.pw.config.user.service.UserService;
import com.tnd.pw.config.workspace.entity.WorkspaceEntity;
import com.tnd.pw.config.workspace.exception.WorkspaceNotFoundException;
import com.tnd.pw.config.workspace.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ProductServiceHandlerImpl implements ProductServiceHandler {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @Override
    public CsProductRepresentation addProduct(ConfigRequest request) throws IOException, DBServiceException, ProductNotFoundException, UserConfigNotFoundException, PermissionNotFoundException, WorkspaceNotFoundException {
        Long userId = request.getPayload().getUserId();
        ProductEntity productEntity = productService.create(
                ProductEntity.builder()
                        .type(ProductType.valueOf(request.getType()).ordinal())
                        .parent(request.getParentId())
                        .workspaceId(request.getPayload().getWorkspaceId())
                        .name(request.getName())
                        .createdBy(userId)
                        .build()
        );

        UserConfigEntity userConfigEntity = userService.getUserConfig(
                UserConfigEntity.builder()
                        .userId(userId)
                        .workspaceId(request.getPayload().getWorkspaceId())
                        .build()
        ).get(0);
        HashMap<Long, String> product_mapping = GsonUtils.getGson().fromJson(userConfigEntity.getProductPermissions(),
                new TypeToken<HashMap<Long, String>>() {}.getType());
        if(product_mapping == null) {
            product_mapping = new HashMap<>();
        }
        product_mapping.put(productEntity.getId(), UserPermissions.OWNER);
        userConfigEntity.setProductPermissions(GsonUtils.convertToString(product_mapping));
        userService.updateUserConfig(userConfigEntity);

        List<ProductEntity> productEntities = productService.get(ProductEntity.builder().workspaceId(productEntity.getWorkspaceId()).build());
        return RepresentationBuilder.buildListProductRep(productEntities);
    }

    @Override
    public CsProductRepresentation getProduct(ConfigRequest request) throws DBServiceException, IOException, ProductNotFoundException {
        List<ProductEntity> productEntities = productService.get(
                ProductEntity.builder()
                        .id(request.getId())
                        .workspaceId(request.getPayload().getWorkspaceId())
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
        return null;
    }


}
