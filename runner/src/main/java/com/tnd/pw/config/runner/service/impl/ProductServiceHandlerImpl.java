package com.tnd.pw.config.runner.service.impl;

import com.google.common.reflect.TypeToken;
import com.tnd.common.api.common.Utils.TokenUtils;
import com.tnd.common.api.common.base.authens.Payload;
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
import com.tnd.pw.config.user.constants.UserState;
import com.tnd.pw.config.user.constants.UserWPRoles;
import com.tnd.pw.config.user.entity.PermissionEntity;
import com.tnd.pw.config.user.entity.UserConfigEntity;
import com.tnd.pw.config.user.entity.UserProfileEntity;
import com.tnd.pw.config.user.exception.PermissionNotFoundException;
import com.tnd.pw.config.user.exception.UserConfigNotFoundException;
import com.tnd.pw.config.user.exception.UserProfileNotFoundException;
import com.tnd.pw.config.user.service.UserService;
import com.tnd.pw.config.workspace.entity.WorkspaceEntity;
import com.tnd.pw.config.workspace.exception.WorkspaceNotFoundException;
import com.tnd.pw.config.workspace.service.WorkspaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductServiceHandlerImpl implements ProductServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceHandlerImpl.class);

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private CacheHelper cacheHelper;

    @Override
    public CsProductRepresentation addProduct(ConfigRequest request) throws IOException, DBServiceException, ProductNotFoundException, UserConfigNotFoundException, PermissionNotFoundException, WorkspaceNotFoundException, UserProfileNotFoundException {
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
        HashMap<Long, String> productMapping = GsonUtils.getGson().fromJson(userConfigEntity.getProductPermissions(),
                new TypeToken<HashMap<Long, String>>() {}.getType());
        if(productMapping == null) {
            productMapping = new HashMap<>();
        }
        productMapping.put(productEntity.getId(), UserPermissions.OWNER);
        userConfigEntity.setProductPermissions(GsonUtils.convertToString(productMapping));
        userService.updateUserConfig(userConfigEntity);

        List<ProductEntity> productEntities = productService.get(ProductEntity.builder().workspaceId(productEntity.getWorkspaceId()).build());

        UserProfileEntity userProfile = userService.getUserProfile(UserProfileEntity.builder().id(request.getPayload().getUserId()).build()).get(0);
        UserPermission workspacePermission = cacheHelper.getHashMap(CacheKeys.PERMISSIONS, userConfigEntity.getWorkspacePermissions());
        HashMap<Long, UserPermission> productPermissions;
        if(productMapping == null) {
            productPermissions = null;
        } else {
            productPermissions = new HashMap<>();
            for(Long productId: productMapping.keySet()) {
                UserPermission userPermission = cacheHelper.getHashMap(CacheKeys.PERMISSIONS, productMapping.get(productId));
                productPermissions.put(
                        productId,
                        userPermission
                );
            }
        }
        String token = TokenUtils.generateToken(
                new Payload(
                        userProfile.getId(),
                        userProfile.getDomain(),
                        userProfile.getEmail(),
                        productPermissions,
                        userProfile.getRole(),
                        userConfigEntity.getWorkspaceId(),
                        workspacePermission
                ));

        return RepresentationBuilder.buildListProductRep(productEntities, token, productMapping);
    }

    @Override
    public CsProductRepresentation getProduct(ConfigRequest request) throws DBServiceException, IOException {
        List<ProductEntity> productEntities = null;
        try {
            productEntities = productService.get(
                    ProductEntity.builder()
                            .id(request.getId())
                            .workspaceId(request.getPayload().getWorkspaceId())
                            .build()
            );
            return RepresentationBuilder.buildListProductRep(productEntities);
        } catch (ProductNotFoundException e) {
            LOGGER.error("ProductNotFoundException with request: {}", request);
            return RepresentationBuilder.buildListProductRep(new ArrayList<>());
        }
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
