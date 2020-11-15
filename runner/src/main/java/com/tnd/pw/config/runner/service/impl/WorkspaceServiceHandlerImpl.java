package com.tnd.pw.config.runner.service.impl;


import com.google.common.reflect.TypeToken;
import com.tnd.common.api.common.Utils.GenUID;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.common.representations.CsWorkspaceRepresentation;
import com.tnd.pw.config.common.representations.WorkspaceRepresentation;
import com.tnd.pw.config.common.requests.AdminRequest;
import com.tnd.pw.config.common.requests.UserRequest;
import com.tnd.pw.config.common.requests.WorkspaceRequest;
import com.tnd.pw.config.common.utils.GsonUtils;
import com.tnd.pw.config.common.utils.RepresentationBuilder;
import com.tnd.pw.config.packages.entity.PackageCodeEntity;
import com.tnd.pw.config.packages.entity.PackageEntity;
import com.tnd.pw.config.packages.enums.PackageCodeState;
import com.tnd.pw.config.packages.enums.PackageState;
import com.tnd.pw.config.packages.exception.InconsistentStateException;
import com.tnd.pw.config.packages.exception.PackageCodeNotFoundException;
import com.tnd.pw.config.packages.exception.PackageNotFoundException;
import com.tnd.pw.config.packages.service.PackageService;
import com.tnd.pw.config.product.entity.ProductEntity;
import com.tnd.pw.config.product.exception.ProductNotFoundException;
import com.tnd.pw.config.product.service.ProductService;
import com.tnd.pw.config.runner.exception.InvalidDataException;
import com.tnd.pw.config.runner.exception.PackageCodeExpiredException;
import com.tnd.pw.config.runner.exception.PackageInactiveException;
import com.tnd.pw.config.runner.service.WorkspaceServiceHandler;
import com.tnd.pw.config.user.constants.AccountType;
import com.tnd.pw.config.user.constants.UserPermissions;
import com.tnd.pw.config.user.constants.UserState;
import com.tnd.pw.config.user.entity.UserConfigEntity;
import com.tnd.pw.config.user.entity.UserProfileEntity;
import com.tnd.pw.config.user.exception.PermissionNotFoundException;
import com.tnd.pw.config.user.exception.UserConfigNotFoundException;
import com.tnd.pw.config.user.exception.UserProfileNotFoundException;
import com.tnd.pw.config.user.service.UserService;
import com.tnd.pw.config.workspace.config.entity.WorkspaceConfigEntity;
import com.tnd.pw.config.workspace.config.exception.WorkspaceConfigNotFoundException;
import com.tnd.pw.config.workspace.config.service.WorkspaceConfigService;
import com.tnd.pw.config.workspace.entity.WorkspaceEntity;
import com.tnd.pw.config.workspace.enums.WorkspaceState;
import com.tnd.pw.config.workspace.exception.WorkspaceNotFoundException;
import com.tnd.pw.config.workspace.service.WorkspaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorkspaceServiceHandlerImpl implements WorkspaceServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkspaceServiceHandlerImpl.class);
    private static final String DefaultPassword = "password";

    @Autowired
    private PackageService packageService;
    @Autowired
    private WorkspaceService workspaceService;
    @Autowired
    private WorkspaceConfigService workspaceConfigService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @Override
    public WorkspaceRepresentation addWorkspace(UserRequest request) throws DBServiceException, PackageCodeNotFoundException, PackageCodeExpiredException, PackageNotFoundException, PackageInactiveException, InconsistentStateException {
        PackageCodeEntity packageCodeEntity = packageService.getPackageCode(PackageCodeEntity.builder().id(request.getCode()).build()).get(0);
        if(packageCodeEntity.getState() == PackageCodeState.INACTIVE.ordinal()) {
            throw new PackageCodeExpiredException();
        }
        PackageEntity packageEntity = packageService.getPackage(PackageEntity.builder().id(packageCodeEntity.getPackageId()).build()).get(0);
        if(packageEntity.getState() == PackageState.INACTIVE.ordinal()) {
            throw new PackageInactiveException();
        }

        WorkspaceEntity workspaceEntity = workspaceService.create(
                WorkspaceEntity.builder()
                        .domain(request.getPayload().getDomain())
                        .state(WorkspaceState.ACTIVE.ordinal())
                        .ownerId(request.getPayload().getUserId())
                        .createdBy(request.getPayload().getUserId())
                        .build()
        );

        WorkspaceConfigEntity workspaceConfigEntity = workspaceConfigService.create(
                WorkspaceConfigEntity.builder()
                        .workspaceId(workspaceEntity.getId())
                        .packageId(packageEntity.getId())
                        .config(GsonUtils.convertToString(packageEntity))
                        .state(WorkspaceState.ACTIVE.ordinal())
                        .build()
        );

        workspaceEntity.setConfigId(workspaceConfigEntity.getId());
        workspaceService.update(workspaceEntity);

        packageCodeEntity.setState(PackageCodeState.INACTIVE.ordinal());
        packageService.updatePackageCode(packageCodeEntity);

        userService.createUserConfig(
                UserConfigEntity.builder()
                        .userId(request.getPayload().getUserId())
                        .workspaceId(workspaceEntity.getId())
                        .workspacePermissions(UserPermissions.OWNER)
                        .productPermissions(null)
                        .createdBy(request.getPayload().getUserId())
                        .build());
        return RepresentationBuilder.buildWorkspaceRepresentation(workspaceEntity, GsonUtils.convertToString(packageEntity));
    }

    @Override
    public CsWorkspaceRepresentation getWorkspace(AdminRequest request) throws DBServiceException, WorkspaceNotFoundException, WorkspaceConfigNotFoundException {
        List<WorkspaceEntity> workspaceEntities = workspaceService.get(
                WorkspaceEntity.builder()
                        .id(request.getId())
                        .state(request.getState() != null ? WorkspaceState.valueOf(request.getState()).ordinal() : null)
                        .build()
        );
        List<WorkspaceRepresentation> list = new ArrayList<>();
        for (WorkspaceEntity workspaceEntity : workspaceEntities) {
            WorkspaceConfigEntity workspaceConfigEntity = workspaceConfigService.get(
                    WorkspaceConfigEntity.builder()
                            .workspaceId(workspaceEntity.getId())
                            .state(WorkspaceState.ACTIVE.ordinal())
                            .build()
            ).get(0);
            list.add(RepresentationBuilder.buildWorkspaceRepresentation(workspaceEntity, workspaceConfigEntity.getConfig()));
        }
        return new CsWorkspaceRepresentation(list);
    }

    @Override
    public WorkspaceRepresentation updateWorkspace(WorkspaceRequest request) throws DBServiceException, WorkspaceNotFoundException, WorkspaceConfigNotFoundException {
        WorkspaceEntity workspaceEntity = workspaceService.get(WorkspaceEntity.builder().id(request.getId()).build()).get(0);
        WorkspaceConfigEntity workspaceConfigEntity = workspaceConfigService.get(WorkspaceConfigEntity.builder().id(workspaceEntity.getConfigId()).build()).get(0);
        workspaceEntity.setState(WorkspaceState.valueOf(request.getState()).ordinal());
        workspaceEntity.setUpdatedBy(request.getPayload().getUserId());
        workspaceConfigEntity.setState(WorkspaceState.valueOf(request.getState()).ordinal());
        workspaceConfigEntity.setUpdatedBy(request.getPayload().getUserId());
        workspaceService.update(workspaceEntity);
        workspaceConfigService.update(workspaceConfigEntity);
        return RepresentationBuilder.buildWorkspaceRepresentation(workspaceEntity, workspaceConfigEntity.getConfig());
    }

    @Override
    public WorkspaceRepresentation upgradeWorkspace(WorkspaceRequest request) throws DBServiceException, PackageCodeNotFoundException, PackageCodeExpiredException, PackageNotFoundException, PackageInactiveException, WorkspaceNotFoundException, WorkspaceConfigNotFoundException {
        PackageCodeEntity packageCodeEntity = packageService.getPackageCode(PackageCodeEntity.builder().id(request.getCode()).build()).get(0);
        if(packageCodeEntity.getState() == PackageCodeState.INACTIVE.ordinal()) {
            throw new PackageCodeExpiredException();
        }
        PackageEntity packageEntity = packageService.getPackage(PackageEntity.builder().id(packageCodeEntity.getPackageId()).build()).get(0);
        if(packageEntity.getState() == PackageState.INACTIVE.ordinal()) {
            throw new PackageInactiveException();
        }

        WorkspaceEntity workspaceEntity = workspaceService.get(
                WorkspaceEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);

        updateOldConfig(workspaceEntity.getConfigId());

        WorkspaceConfigEntity workspaceConfigEntity = workspaceConfigService.create(
                WorkspaceConfigEntity.builder()
                        .workspaceId(workspaceEntity.getId())
                        .packageId(packageEntity.getId())
                        .config(GsonUtils.convertToString(packageEntity))
                        .state(WorkspaceState.ACTIVE.ordinal())
                        .updatedBy(request.getPayload().getUserId())
                        .build()
        );

        workspaceEntity.setState(WorkspaceState.ACTIVE.ordinal());
        workspaceEntity.setConfigId(workspaceConfigEntity.getId());
        workspaceService.update(workspaceEntity);
        return RepresentationBuilder.buildWorkspaceRepresentation(workspaceEntity, workspaceConfigEntity.getConfig());
    }

    @Override
    public CsWorkspaceRepresentation addUser(WorkspaceRequest request) throws DBServiceException, PermissionNotFoundException, ProductNotFoundException, InvalidDataException {
        Long workspaceId = request.getPayload().getWorkspaceId();
        HashMap<Long, String> productPermission = new HashMap<>();
        try {
            checkInputData(request);
            UserProfileEntity userProfileEntity = userService.getUserProfile(
                    UserProfileEntity.builder()
                            .email(request.getEmail())
                            .password(DefaultPassword)
                            .build()
            ).get(0);
            try {
                UserConfigEntity userConfigEntity = userService.getUserConfig(
                        UserConfigEntity.builder()
                                .userId(userProfileEntity.getId())
                                .workspaceId(workspaceId)
                                .build()
                ).get(0);
                if(userConfigEntity.getState() == UserState.ACTIVE.ordinal()) {
                    HashMap<Long, String> permissions = GsonUtils.getGson().fromJson(userConfigEntity.getProductPermissions(),
                            new TypeToken<HashMap<Long, String>>() {
                            }.getType());
                    for (Long productId : request.getPermission().keySet()) {
                        if (!permissions.containsKey(productId)) {
                            permissions.put(productId, request.getPermission().get(productId));
                        }
                    }
                    if (request.getPermission().containsValue(UserPermissions.OWNER)) {
                        for (Long productId : permissions.keySet()) {
                            permissions.replace(productId, UserPermissions.OWNER);
                        }
                        userConfigEntity.setWorkspacePermissions(UserPermissions.OWNER);
                    }
                    userConfigEntity.setProductPermissions(GsonUtils.convertToString(permissions));
                } else {
                    if(request.getPermission().values().contains(UserPermissions.OWNER)) {
                        List<ProductEntity> productEntities = productService.get(ProductEntity.builder().workspaceId(workspaceId).build());
                        for(ProductEntity productEntity: productEntities) {
                            productPermission.put(productEntity.getId(), UserPermissions.OWNER);
                        }
                        userConfigEntity.setWorkspacePermissions(UserPermissions.OWNER);
                    } else {
                        for(Long productId: request.getPermission().keySet()) {
                            if(request.getPermission().get(productId).equals(UserPermissions.OWNER)) {
                                throw new InvalidDataException("Permission OWNER appear in list permissions , this have others permissions !");
                            }
                            productPermission.put(productId, request.getPermission().get(productId));
                        }
                        userConfigEntity.setWorkspacePermissions("");
                    }
                    userConfigEntity.setState(UserState.ACTIVE.ordinal());
                    userConfigEntity.setProductPermissions(GsonUtils.convertToString(productPermission));
                }
                userService.updateUserConfig(userConfigEntity);
            } catch (UserConfigNotFoundException e) {
                if(request.getPermission().values().contains(UserPermissions.OWNER)) {
                    List<ProductEntity> productEntities = productService.get(ProductEntity.builder().workspaceId(workspaceId).build());
                    for(ProductEntity productEntity: productEntities) {
                        productPermission.put(productEntity.getId(), UserPermissions.OWNER);
                    }
                    userService.createUserConfig(
                            UserConfigEntity.builder()
                                    .userId(userProfileEntity.getId())
                                    .workspaceId(workspaceId)
                                    .workspacePermissions(UserPermissions.OWNER)
                                    .productPermissions(GsonUtils.convertToString(productPermission))
                                    .createdAt(request.getPayload().getUserId())
                                    .build());
                } else {
                    initUserPermission(request, workspaceId, userProfileEntity, productPermission);
                }
            }
        } catch (UserProfileNotFoundException e) {
            UserProfileEntity userProfileEntity = userService.createUserProfile(
                    UserProfileEntity.builder()
                            .email(request.getEmail())
                            .firstName(request.getFirstName())
                            .lastName(request.getLastName())
                            .role(AccountType.USER)
                            .createdBy(request.getPayload().getUserId())
                            .build()
            );
            initUserPermission(request, workspaceId, userProfileEntity, productPermission);
        }

        return null;
    }

    private void checkInputData(WorkspaceRequest request) throws InvalidDataException {
        for(Long productId: request.getPermission().keySet()) {
            if(!GenUID.isProductId(productId)) {
                throw new InvalidDataException(String.format("This id: %d is not product_id !", productId));
            }
        }
    }

    @Override
    public CsWorkspaceRepresentation getWorkspaceOfUser(UserRequest request) throws DBServiceException, WorkspaceNotFoundException, WorkspaceConfigNotFoundException {
        List<UserConfigEntity> userConfigs = null;
        try {
            userConfigs = userService.getUserConfig(
                    UserConfigEntity.builder()
                            .userId(request.getPayload().getUserId())
                            .state(UserState.ACTIVE.ordinal())
                            .build());
        } catch (UserConfigNotFoundException e) {
            LOGGER.error("UserConfigNotFoundException with user_id: {}", request.getPayload().getUserId());
            return new CsWorkspaceRepresentation(new ArrayList<>());
        }
        List<WorkspaceRepresentation> list = new ArrayList<>();
        for(UserConfigEntity userConfigEntity: userConfigs) {
            WorkspaceEntity workspaceEntity = workspaceService.get(
                    WorkspaceEntity.builder()
                            .id(userConfigEntity.getWorkspaceId())
                            .build()
            ).get(0);
            WorkspaceConfigEntity workspaceConfigEntity = workspaceConfigService.get(
                    WorkspaceConfigEntity.builder()
                            .workspaceId(workspaceEntity.getId())
                            .state(WorkspaceState.ACTIVE.ordinal())
                            .build()
            ).get(0);
            list.add(RepresentationBuilder.buildWorkspaceRepresentation(workspaceEntity, workspaceConfigEntity.getConfig()));
        }
        return new CsWorkspaceRepresentation(list);
    }

    @Override
    public CsWorkspaceRepresentation updateUser(WorkspaceRequest request) throws InvalidDataException, DBServiceException, UserConfigNotFoundException {
        checkInputData(request);
        UserConfigEntity userConfigEntity = userService.getUserConfig(
                UserConfigEntity.builder()
                        .userId(request.getUserId())
                        .workspaceId(request.getPayload().getWorkspaceId())
                        .state(UserState.ACTIVE.ordinal())
                        .build()
        ).get(0);
        HashMap<Long, String> permissions = GsonUtils.getGson().fromJson(userConfigEntity.getProductPermissions(),
                new TypeToken<HashMap<Long, String>>() {}.getType());
        if(request.getIsDemote() != null) {
            if(request.getPermission().containsValue(UserPermissions.OWNER)) {
                throw new InvalidDataException("Data error ! Not OWNER when demoting !");
            }
            for(Long productId: permissions.keySet()) {
                if(!request.getPermission().containsKey(productId)) {
                    throw new InvalidDataException(String.format("Lack of data %s when demoting !", productId));
                }
                permissions.replace(productId, request.getPermission().get(productId));
            }
            userConfigEntity.setWorkspacePermissions("");
        } else {
            if(request.getPermission().containsValue(UserPermissions.OWNER)) {
                if(!permissions.containsValue(UserPermissions.OWNER)) {
                    for (Long key : permissions.keySet()) {
                        permissions.replace(key, UserPermissions.OWNER);
                    }
                    permissions.putAll(request.getPermission());
                    userConfigEntity.setWorkspacePermissions(UserPermissions.OWNER);
                }
            } else {
                if(permissions.containsValue(UserPermissions.OWNER)) {
                    throw new InvalidDataException("User was Owner !");
                }
                permissions.putAll(request.getPermission());
            }
        }
        userConfigEntity.setProductPermissions(GsonUtils.convertToString(permissions));
        userService.updateUserConfig(userConfigEntity);
        return null;
    }

    @Override
    public CsWorkspaceRepresentation removeUser(WorkspaceRequest request) throws DBServiceException, UserConfigNotFoundException, InvalidDataException {
        UserConfigEntity userConfigEntity = userService.getUserConfig(
                UserConfigEntity.builder()
                        .userId(request.getUserId())
                        .workspaceId(request.getPayload().getWorkspaceId())
                        .state(UserState.ACTIVE.ordinal())
                        .build()
        ).get(0);
        if(request.getPermission() != null) {
            checkInputData(request);
            if(userConfigEntity.getWorkspacePermissions().equals(UserPermissions.OWNER)) {
                throw new InvalidDataException("User was Owner ! Can't remove product of user !");
            }
            HashMap<Long, String> permissions = GsonUtils.getGson().fromJson(userConfigEntity.getProductPermissions(),
                    new TypeToken<HashMap<Long, String>>() {}.getType());
            for(Long productId: request.getPermission().keySet()) {
                permissions.remove(productId);
            }
            userConfigEntity.setProductPermissions(GsonUtils.convertToString(permissions));
        } else {
            userConfigEntity.setState(UserState.INACTIVE.ordinal());
        }
        userService.updateUserConfig(userConfigEntity);
        return null;
    }

    private void initUserPermission(WorkspaceRequest request, Long workspaceId, UserProfileEntity userProfileEntity, HashMap<Long, String> productPermission) throws InvalidDataException, DBServiceException {
        for(Long productId: request.getPermission().keySet()) {
            if(request.getPermission().get(productId).equals(UserPermissions.OWNER)) {
                throw new InvalidDataException("Permission OWNER appear in list permissions , this have others permissions !");
            }
            productPermission.put(productId, request.getPermission().get(productId));
        }
        userService.createUserConfig(
                UserConfigEntity.builder()
                        .userId(userProfileEntity.getId())
                        .workspaceId(workspaceId)
                        .workspacePermissions(null)
                        .productPermissions(GsonUtils.convertToString(productPermission))
                        .createdAt(request.getPayload().getUserId())
                        .build());
    }

    private void updateOldConfig(Long configId) throws DBServiceException, WorkspaceConfigNotFoundException {
        WorkspaceConfigEntity workspaceConfigEntity = workspaceConfigService.get(WorkspaceConfigEntity.builder().id(configId).build()).get(0);
        workspaceConfigEntity.setState(WorkspaceState.INACTIVE.ordinal());
        workspaceConfigService.update(workspaceConfigEntity);
    }
}
