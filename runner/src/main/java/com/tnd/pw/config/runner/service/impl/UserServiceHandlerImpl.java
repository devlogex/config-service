package com.tnd.pw.config.runner.service.impl;

import com.google.common.reflect.TypeToken;
import com.tnd.common.api.common.Utils.TokenUtils;
import com.tnd.common.api.common.base.authens.Payload;
import com.tnd.common.api.common.base.authens.UserPermission;
import com.tnd.common.cache.redis.CacheHelper;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.common.constants.CacheKeys;
import com.tnd.pw.config.common.representations.UserRepresentation;
import com.tnd.pw.config.common.requests.AnonymousRequest;
import com.tnd.pw.config.common.requests.UserRequest;
import com.tnd.pw.config.common.utils.GsonUtils;
import com.tnd.pw.config.runner.service.UserServiceHandler;
import com.tnd.pw.config.user.constants.AccountType;
import com.tnd.pw.config.user.entity.PermissionEntity;
import com.tnd.pw.config.user.entity.UserConfigEntity;
import com.tnd.pw.config.user.entity.UserProfileEntity;
import com.tnd.pw.config.user.constants.UserState;
import com.tnd.pw.config.user.exception.UserConfigNotFoundException;
import com.tnd.pw.config.user.exception.UserProfileNotFoundException;
import com.tnd.pw.config.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.HashMap;

public class UserServiceHandlerImpl implements UserServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceHandlerImpl.class);

    @Autowired
    private UserService userService;
    @Autowired
    private CacheHelper cacheHelper;

    @Override
    public UserRepresentation loginWorkspace(UserRequest request) throws DBServiceException, UserProfileNotFoundException, IOException, LoginException {
        UserProfileEntity userProfile = null;
        try{
            userProfile = userService.getUserProfile(UserProfileEntity.builder().id(request.getPayload().getUserId()).build()).get(0);
            UserConfigEntity userConfigEntity = userService.getUserConfig(
                    UserConfigEntity.builder()
                            .userId(userProfile.getId())
                            .workspaceId(request.getWorkspaceId())
                            .state(UserState.ACTIVE.ordinal())
                            .build()
            ).get(0);

            UserPermission workspacePermission = cacheHelper.getHashMap(CacheKeys.PERMISSIONS, userConfigEntity.getWorkspacePermissions());

            HashMap<Long, String> productMapping = GsonUtils.getGson().fromJson(
                    userConfigEntity.getProductPermissions(),
                    new TypeToken<HashMap<Long, String>>() {}.getType()
            );
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
                            productPermissions,
                            userProfile.getRole(),
                            userConfigEntity.getWorkspaceId(),
                            workspacePermission
                    ));
            return new UserRepresentation(token);
        } catch (UserConfigNotFoundException e) {
            throw new LoginException(String.format("User id %d did not have permission on workspace %d", userProfile.getId(), request.getWorkspaceId()));
        }
    }

    @Override
    public UserRepresentation login(AnonymousRequest request) throws IOException, DBServiceException, LoginException {
        UserProfileEntity userProfile = null;
        try{
            userProfile = userService.getUserProfile(UserProfileEntity.builder().email(request.getEmail()).build()).get(0);
            if(!userProfile.getPassword().equals(request.getPassword())) {
                throw new LoginException("Wrong password !");
            }
            String token = TokenUtils.generateToken(
                    new Payload(
                            userProfile.getId(),
                            userProfile.getDomain(),
                            null,
                            userProfile.getRole(),
                            null,
                            null
                    ));
            return new UserRepresentation(token);
        } catch (UserProfileNotFoundException e) {
            throw new LoginException("Email not found !");
        }
    }

    @Override
    public UserRepresentation createUser(AnonymousRequest request) throws IOException, DBServiceException {
        UserProfileEntity userProfile = userService.createUserProfile(
                UserProfileEntity.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .email(request.getEmail())
                        .role(AccountType.USER)
                        .companyName(request.getCompanyName())
                        .domain(request.getDomain())
                        .password(request.getPassword())
                        .build()
        );
        String token = TokenUtils.generateToken(
                new Payload(
                        userProfile.getId(),
                        userProfile.getDomain(),
                        null,
                        userProfile.getRole(),
                        null,
                        null
                ));
        return new UserRepresentation(token);
    }
}
