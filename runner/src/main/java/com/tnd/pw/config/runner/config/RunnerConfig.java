package com.tnd.pw.config.runner.config;

import com.tnd.com.notification.NotificationService;
import com.tnd.com.notification.email.EmailService;
import com.tnd.common.cache.redis.CacheHelper;
import com.tnd.dbservice.sdk.api.DBServiceSdkClient;
import com.tnd.dbservice.sdk.api.impl.DBServiceSdkClientImpl;
import com.tnd.pw.config.dbservice.DBServiceApiClient;
import com.tnd.pw.config.dbservice.DataHelper;
import com.tnd.pw.config.packages.dao.PackageCodeDao;
import com.tnd.pw.config.packages.dao.PackageDao;
import com.tnd.pw.config.packages.dao.impl.PackageCodeDaoImpl;
import com.tnd.pw.config.packages.dao.impl.PackageDaoImpl;
import com.tnd.pw.config.packages.service.PackageService;
import com.tnd.pw.config.packages.service.impl.PackageServiceImpl;
import com.tnd.pw.config.product.dao.ProductDao;
import com.tnd.pw.config.product.dao.impl.ProductDaoImpl;
import com.tnd.pw.config.product.service.ProductService;
import com.tnd.pw.config.product.service.impl.ProductServiceImpl;
import com.tnd.pw.config.runner.handler.*;
import com.tnd.pw.config.runner.service.PackageServiceHandler;
import com.tnd.pw.config.runner.service.ProductServiceHandler;
import com.tnd.pw.config.runner.service.UserServiceHandler;
import com.tnd.pw.config.runner.service.WorkspaceServiceHandler;
import com.tnd.pw.config.runner.service.impl.PackageServiceHandlerImpl;
import com.tnd.pw.config.runner.service.impl.ProductServiceHandlerImpl;
import com.tnd.pw.config.runner.service.impl.UserServiceHandlerImpl;
import com.tnd.pw.config.runner.service.impl.WorkspaceServiceHandlerImpl;
import com.tnd.pw.config.user.dao.PermissionDao;
import com.tnd.pw.config.user.dao.UserConfigDao;
import com.tnd.pw.config.user.dao.UserProfileDao;
import com.tnd.pw.config.user.dao.impl.PermissionDaoImpl;
import com.tnd.pw.config.user.dao.impl.UserConfigDaoImpl;
import com.tnd.pw.config.user.dao.impl.UserProfileDaoImpl;
import com.tnd.pw.config.user.service.UserService;
import com.tnd.pw.config.user.service.impl.UserServiceImpl;
import com.tnd.pw.config.workspace.config.dao.WorkspaceConfigDao;
import com.tnd.pw.config.workspace.config.dao.impl.WorkspaceConfigDaoImpl;
import com.tnd.pw.config.workspace.config.service.WorkspaceConfigService;
import com.tnd.pw.config.workspace.config.service.impl.WorkspaceConfigServiceImpl;
import com.tnd.pw.config.workspace.dao.WorkspaceDao;
import com.tnd.pw.config.workspace.dao.impl.WorkspaceDaoImpl;
import com.tnd.pw.config.workspace.service.WorkspaceService;
import com.tnd.pw.config.workspace.service.impl.WorkspaceServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class RunnerConfig {
    @Value("${db.host}")
    private String db_host;
    @Value("${db.port}")
    private String db_port;
    @Value("${redis.url}")
    private String redis_url;
    @Value("${redis.password}")
    private String redis_password;
    @Value("${notification.sendAddress}")
    private String sendAddress;
    @Value("${notification.auth}")
    private String auth;

    @Bean
    public DBServiceSdkClient dbServiceSdkClient() {
        return new DBServiceSdkClientImpl(db_host,Integer.parseInt(db_port), 1);
    }

    @Bean
    public DataHelper dataHelper(DBServiceSdkClient dbServiceSdkClient) {
        return new DataHelper(dbServiceSdkClient);
    }

    @Bean
    public NotificationService notificationService() {
        return new EmailService(sendAddress, auth);
    }

    @Bean
    public CacheHelper cacheHelper() {
        return new CacheHelper(redis_url, redis_password);
    }

    @Bean
    public UserProfileDao userProfileDao() {
        return new UserProfileDaoImpl();
    }

    @Bean
    public UserConfigDao userConfigDao() {
        return new UserConfigDaoImpl();
    }

    @Bean
    public PermissionDao permissionDao() {
        return new PermissionDaoImpl();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }

    @Bean
    public PackageDao packageDao(){
        return new PackageDaoImpl();
    }

    @Bean
    public PackageCodeDao packageCodeDao(){
        return new PackageCodeDaoImpl();
    }

    @Bean
    public PackageService packageService() {
        return new PackageServiceImpl();
    }

    @Bean
    public PackageServiceHandler packageServiceHandler() {
        return new PackageServiceHandlerImpl();
    }

    @Bean
    public PackageHandler packageHandler() {
        return new PackageHandler();
    }

    @Bean
    public WorkspaceDao workspaceDao(){
        return new WorkspaceDaoImpl();
    }

    @Bean
    public WorkspaceService workspaceService() {
        return new WorkspaceServiceImpl();
    }

    @Bean
    public WorkspaceConfigDao workspaceConfigDao() {
        return new WorkspaceConfigDaoImpl();
    }

    @Bean
    public WorkspaceConfigService workspaceConfigService(){
        return new WorkspaceConfigServiceImpl();
    }

    @Bean
    public WorkspaceServiceHandler workspaceServiceHandler() {
        return new WorkspaceServiceHandlerImpl();
    }

    @Bean
    public WorkspaceHandler workspaceHandler() {
        return new WorkspaceHandler();
    }

    @Bean
    public ProductDao productDao(){
        return new ProductDaoImpl();
    }

    @Bean
    public ProductService productService() {
        return new ProductServiceImpl();
    }

    @Bean
    public ProductServiceHandler productServiceHandler() {
        return new ProductServiceHandlerImpl();
    }

    @Bean
    public ProductHandler productHandler() {
        return new ProductHandler();
    }

    @Bean
    public UserServiceHandler userServiceHandler() {
        return new UserServiceHandlerImpl();
    }

    @Bean
    public UserHandler userHandler() {
        return new UserHandler();
    }

    @Bean
    public ConfigHandler configHandler() {
        return new ConfigHandler();
    }
}
