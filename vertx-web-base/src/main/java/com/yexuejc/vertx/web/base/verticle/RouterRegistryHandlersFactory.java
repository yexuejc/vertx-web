package com.yexuejc.vertx.web.base.verticle;

import com.yexuejc.vertx.web.base.core.SingleVertxRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * router 扫描注册器
 *
 * @ClassName: RouterRegistryHandlersFactory
 * @Description:
 * @author: maxf
 * @date: 2018/4/8 14:28
 */
public class RouterRegistryHandlersFactory extends AbstractVerticle {
    private static Logger logger = LoggerFactory.getLogger(RouterRegistryHandlersFactory.class.getName());
    protected Router router;
    HttpServer server;
    // 默认http server端口
    public static volatile int port = 8080;


    public RouterRegistryHandlersFactory(int port) {
        this.router = SingleVertxRouter.getRouter();
        if (port > 0) {
            this.port = port;
        }
    }

    public RouterRegistryHandlersFactory(Router router) {
        Objects.requireNonNull(router, "The router is empty.");
        this.router = router;
    }

    public RouterRegistryHandlersFactory(Router router, int port) {
        this.router = router;
        if (port > 0) {
            this.port = port;
        }
    }

    /**
     * 重写启动verticle
     *
     * @param future
     * @throws Exception
     */
    @Override
    public void start(Future<Void> future) throws Exception {
        logger.trace("To start listening to port {} ......", port);
        super.start();
        HttpServerOptions options = new HttpServerOptions().setMaxWebsocketFrameSize(1000000).setPort(port);
        server = vertx.createHttpServer(options);
        server.requestHandler(router::accept);
        server.listen(result -> {
            if (result.succeeded()) {
                future.complete();
            } else {
                future.fail(result.cause());
            }
        });
    }

    /**
     * 重写停止verticle
     *
     * @param future
     */
    @Override
    public void stop(Future<Void> future) {
        if (server == null) {
            future.complete();
            return;
        }
        server.close(result -> {
            if (result.failed()) {
                future.fail(result.cause());
            } else {
                future.complete();
            }
        });
    }

}
