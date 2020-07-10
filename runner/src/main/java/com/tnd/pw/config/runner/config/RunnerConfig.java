package com.tnd.pw.config.runner.config;

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
import com.tnd.pw.config.runner.handler.PackageHandler;
import com.tnd.pw.config.runner.handler.ProductHandler;
import com.tnd.pw.config.runner.handler.WorkspaceHandler;
import com.tnd.pw.config.runner.service.PackageServiceHandler;
import com.tnd.pw.config.runner.service.ProductServiceHandler;
import com.tnd.pw.config.runner.service.WorkspaceServiceHandler;
import com.tnd.pw.config.runner.service.impl.PackageServiceHandlerImpl;
import com.tnd.pw.config.runner.service.impl.ProductServiceHandlerImpl;
import com.tnd.pw.config.runner.service.impl.WorkspaceServiceHandlerImpl;
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
    @Value("${db.url}")
    private String db_url;

    @Bean
    public DBServiceApiClient dbServiceApiClient() {
        return new DBServiceApiClient();
    }

    @Bean
    public DataHelper dataHelper(DBServiceApiClient dbServiceApiClient) {
        return new DataHelper(db_url, dbServiceApiClient);
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

    ////////////

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
}
