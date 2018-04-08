package com.yexuejc.vertx.web.base.http;

import com.yexuejc.vertx.web.base.core.EventBusAddress;
import com.yexuejc.vertx.web.base.core.VertxBean;
import com.yexuejc.vertx.web.base.result.ResultOb;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 事件发送调用处理器
 *
 * @ClassName: SenderInvokeHandler
 * @Description:
 * @author: maxf
 * @date: 2018/4/8 14:46
 */
public class SenderInvokeHandler {
    private static Logger logger = LoggerFactory.getLogger(SenderInvokeHandler.class.getName());
    private static final int TIME_OUT = 60000;
    private static final String DEFAULT_METHOD_COLUMN = "method";
    private static final String DEFAULT_METHOD = "execute";

    /**
     * 发送业务处理请求(适用于集群;本地vertx不推荐，需要使用反射)
     *
     * @param ctx
     * @param processor
     * @param params
     */
    public static void sendProcess(RoutingContext ctx, String processor, JsonObject params) {
        VertxBean.getStandardVertx().eventBus().<JsonObject>send(EventBusAddress.positiveFormate(processor), params, new DeliveryOptions().addHeader(DEFAULT_METHOD_COLUMN, DEFAULT_METHOD).setSendTimeout(TIME_OUT), resultBody -> {
            if (resultBody.failed()) {
                logger.error("Fail for the process.");
                ctx.fail(resultBody.cause());
                return;
            }
            JsonObject result = resultBody.result().body();
            if (result == null) {
                logger.error("Fail by result is null");
                ctx.fail(500);
                return;
            }
            ctx.response().setStatusCode(200);
            ctx.response().end(result.encode());
        });
    }

    /**
     * 发送业务处理请求(适用于集群、本地vertx)
     *
     * @param ctx
     * @param processor
     * @param method
     * @param params
     */
    public static void sendProcess(RoutingContext ctx, String processor, String method, JsonObject params) {
        VertxBean.getStandardVertx().eventBus().<JsonObject>send(EventBusAddress.positiveFormate(processor), params, new DeliveryOptions().addHeader(DEFAULT_METHOD_COLUMN, method).setSendTimeout(TIME_OUT), resultBody -> {
            if (resultBody.failed()) {
                logger.error("Fail for the process.");
                ctx.fail(resultBody.cause());
                return;
            }
            JsonObject result = resultBody.result().body();
            if (result == null) {
                logger.error("Fail by result is null");
                ctx.fail(500);
                return;
            }
            ctx.response().setStatusCode(200);
            ctx.response().end(result.encode());
        });
    }

    /**
     * 发送业务处理请求(适用于集群、本地vertx)
     * 可自定义回调函数(异步)
     *
     * @param ctx
     * @param processor
     * @param method
     * @param params
     * @param replyHandler
     */
    public static void sendProcess(RoutingContext ctx, String processor, String method, JsonObject params, Handler<AsyncResult<Message<JsonObject>>> replyHandler) {
        VertxBean.getStandardVertx().eventBus().<JsonObject>send(EventBusAddress.positiveFormate(processor), params, new DeliveryOptions().addHeader(DEFAULT_METHOD_COLUMN, method).setSendTimeout(TIME_OUT), replyHandler);
    }

    /**
     * 执行异步业务，无需等待结果(默认结果成功)
     *
     * @param ctx
     * @param processor
     * @param params
     */
    public static void sendNSyncProcess(RoutingContext ctx, String processor, JsonObject params) {
        VertxBean.getStandardVertx().eventBus().<JsonObject>send(EventBusAddress.positiveFormate(processor), params, new DeliveryOptions().addHeader(DEFAULT_METHOD_COLUMN, DEFAULT_METHOD).setSendTimeout(60000));
        ctx.response().setStatusCode(200);
        ctx.response().end(new ResultOb().toString());
    }

    /**
     * 执行异步业务，无需等待结果(默认结果成功)
     *
     * @param ctx
     * @param processor
     * @param method
     * @param params
     */
    public static void sendNSyncProcess(RoutingContext ctx, String processor, String method, JsonObject params) {
        VertxBean.getStandardVertx().eventBus().<JsonObject>send(EventBusAddress.positiveFormate(processor), params, new DeliveryOptions().addHeader(DEFAULT_METHOD_COLUMN, method).setSendTimeout(60000));
        ctx.response().setStatusCode(200);
        ctx.response().end(new ResultOb().toString());
    }
}
