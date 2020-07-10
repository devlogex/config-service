package com.tnd.pw.config.runner;

import com.tnd.com.ioc.SpringApplicationContext;
import com.tnd.common.api.server.CommonServer;
import com.tnd.pw.config.runner.config.RunnerConfig;
import com.tnd.pw.config.runner.handler.PackageHandler;
import com.tnd.pw.config.runner.handler.ProductHandler;
import com.tnd.pw.config.runner.handler.WorkspaceHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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

        String port = System.getenv("PORT");
        if (port == null) {
            commonServer.initServlet(8003);
        } else {
            commonServer.initServlet(Integer.parseInt(port));
        }
        commonServer.startServer();
    }
}
