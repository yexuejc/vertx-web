package com.yexuejc.vertx.simple;

import com.yexuejc.vertx.web.base.VertxServer;
import com.yexuejc.vertx.web.base.core.VertxBean;
import com.yexuejc.vertx.web.base.verticle.RouterHandlerFactory;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * vertx 启动类
 *
 * @ClassName: StartRunner
 * @Description:
 * @author: maxf
 * @date: 2018/4/8 10:00
 */
public class StartRunner {
    public static void main(String[] args) throws IOException {
        Logger logger = LoggerFactory.getLogger(StartRunner.class.getClass());
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        logger.debug("=======================Runner  Deployment======================");
        VertxBean.getStandardVertx(Vertx.vertx(new VertxOptions()));
        // 设置扫描器 api、handler(service)
        VertxServer.startDeploy(
                new RouterHandlerFactory("com.yexuejc.vertx.simple.ctrl", "").createRouter(),
                "com.yexuejc.vertx.simple.service",
                8989);
    }
}
