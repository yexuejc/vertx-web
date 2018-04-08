package com.yexuejc.vertx.web.base;

import com.yexuejc.vertx.web.base.core.VertxBean;
import com.yexuejc.vertx.web.base.verticle.RegistryHandlersFactory;
import com.yexuejc.vertx.web.base.verticle.RouterRegistryHandlersFactory;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 开始注册vertx相关服务
 *
 * @ClassName: VertxServer
 * @Description:
 * @author: maxf
 * @date: 2018/4/8 14:19
 */
public class VertxServer {
    private static Logger logger = LoggerFactory.getLogger(VertxServer.class.getName());

    public static void startDeploy(int port) throws IOException {
        logger.trace("Start Deploy....");
        VertxBean.getStandardVertx().deployVerticle(new RouterRegistryHandlersFactory(port));
    }

    public static void startDeploy(Router router, int port) throws IOException {
        logger.trace("Start Deploy....");
        VertxBean.getStandardVertx().deployVerticle(new RouterRegistryHandlersFactory(router, port));
    }

    public static void startDeploy(Router router, String handlerScan, int port) throws IOException {
        logger.trace("Start Deploy....");
        VertxBean.getStandardVertx().deployVerticle(new RouterRegistryHandlersFactory(router, port));
        logger.trace("Start registry handler....");
        new RegistryHandlersFactory(handlerScan).registerVerticle();
    }

    public static void startDeploy(Router router, String handlerScan, String appPrefix, int port) throws IOException {
        logger.trace("Start Deploy....");
        VertxBean.getStandardVertx().deployVerticle(new RouterRegistryHandlersFactory(router, port));
        logger.trace("Start registry handler....");
        new RegistryHandlersFactory(handlerScan, appPrefix).registerVerticle();
    }

    public static void startDeploy(String handlerScan, String appPrefix) throws IOException {
        logger.trace("Start registry handler....");
        new RegistryHandlersFactory(handlerScan, appPrefix).registerVerticle();
    }
}
