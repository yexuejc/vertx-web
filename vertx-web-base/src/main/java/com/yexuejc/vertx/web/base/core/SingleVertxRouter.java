package com.yexuejc.vertx.web.base.core;

import io.vertx.ext.web.Router;

/**
 * 获得全局的Router对象
 *
 * @ClassName: SingleVertxRouter
 * @Description:
 * @author: maxf
 * @date: 2018/4/8 14:14
 */
public class SingleVertxRouter {
    private static Router router;
    private static SingleVertxRouter instance;

    public SingleVertxRouter() {
        router = Router.router(VertxBean.getStandardVertx());
    }

    public static SingleVertxRouter getInstance() {
        if (instance == null) {
            instance = new SingleVertxRouter();
        }
        return instance;
    }

    /**
     * 获得当前Router
     *
     * @return
     */
    public static Router getRouter() {
        if (instance == null) {
            instance = new SingleVertxRouter();
        }
        return router;
    }
}
