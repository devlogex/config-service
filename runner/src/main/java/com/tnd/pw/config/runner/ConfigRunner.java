package com.tnd.pw.config.runner;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tnd.com.ioc.SpringApplicationContext;
import com.tnd.common.api.common.base.authens.UserPermission;
import com.tnd.common.api.server.CommonServer;
import com.tnd.common.cache.redis.CacheHelper;
import com.tnd.pw.config.common.constants.CacheKeys;
import com.tnd.pw.config.common.utils.GsonUtils;
import com.tnd.pw.config.runner.config.RunnerConfig;
import com.tnd.pw.config.runner.handler.PackageHandler;
import com.tnd.pw.config.runner.handler.ProductHandler;
import com.tnd.pw.config.runner.handler.UserHandler;
import com.tnd.pw.config.runner.handler.WorkspaceHandler;
import com.tnd.pw.config.user.entity.PermissionEntity;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigRunner {

    public static void main(String args[]) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(RunnerConfig.class);
        context.refresh();
        SpringApplicationContext.setShareApplicationContext(context);

        CommonServer commonServer = new CommonServer();
        commonServer.register(SpringApplicationContext.getBean(WorkspaceHandler.class));
        commonServer.register(SpringApplicationContext.getBean(PackageHandler.class));
        commonServer.register(SpringApplicationContext.getBean(ProductHandler.class));
        commonServer.register(SpringApplicationContext.getBean(UserHandler.class));

        String port = System.getenv("PORT");
        if (port == null) {
            commonServer.initServlet(8003);
        } else {
            commonServer.initServlet(Integer.parseInt(port));
        }

        initPermission();
        commonServer.startServer();
    }

    private static void initPermission() throws FileNotFoundException {

        JsonElement json = JsonParser.parseReader(new FileReader(
                (new ConfigRunner()).getClass().getClassLoader().getResource("permissions.json").getFile()
        ));
        List<UserPermission> permissions = GsonUtils.getGson().fromJson(json,
                new TypeToken<ArrayList<UserPermission>>() {}.getType());
        CacheHelper cache = SpringApplicationContext.getBean(CacheHelper.class);
        for(UserPermission userPermission: permissions) {
            cache.setHashMap(CacheKeys.PERMISSIONS, userPermission.getName(), userPermission);
        }
    }
}
