package com.yexuejc.vertx.simple.ctrl;

import com.yexuejc.vertx.simple.service.IndexSrv;
import com.yexuejc.vertx.web.base.annotations.RouteHandler;
import com.yexuejc.vertx.web.base.annotations.RouteMapping;
import com.yexuejc.vertx.web.base.annotations.RouteMethod;
import com.yexuejc.vertx.web.base.http.SenderInvokeHandler;
import com.yexuejc.vertx.web.base.result.ResultOb;
import com.yexuejc.vertx.web.base.util.ParamUtil;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @PackageName: com.yexuejc.vertx.simple.ctrl
 * @Description:
 * @author: maxf
 * @date: 2018/4/8 14:39
 */
@RouteHandler("/as")
public class IndexCtrl extends SenderInvokeHandler {
    Logger logger = LoggerFactory.getLogger(IndexCtrl.class.getName());

    @RouteMapping(value = "/test", method = RouteMethod.GET)
    public Handler<RoutingContext> doTest() {
        return ctx -> {
            logger.debug("参数:" + ctx.request().params());
            ctx.response().setStatusCode(200);
            ctx.response().end(ResultOb.build().setMsg("Hello，欢迎使用测试地址.....").toString());
        };
    }

    @RouteMapping(value = "/index", method = RouteMethod.GET)
    public Handler<RoutingContext> doIndex() {
        return ctx -> {
            JsonObject param = ParamUtil.getRequestParams(ctx);
            logger.debug("参数:" + param);
            //对应    IndexSrv->index()
            sendProcess(ctx, IndexSrv.class.getName(), "index", param);
        };
    }

}
