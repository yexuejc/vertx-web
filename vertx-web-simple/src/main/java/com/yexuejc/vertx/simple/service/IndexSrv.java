package com.yexuejc.vertx.simple.service;

import com.yexuejc.vertx.web.base.annotations.VerticleMapping;
import io.vertx.core.json.JsonObject;
import org.springframework.stereotype.Service;

/**
 * @PackageName: com.yexuejc.vertx.simple.service
 * @Description:
 * @author: maxf
 * @date: 2018/4/8 14:39
 */
@VerticleMapping
@Service
public class IndexSrv {
    public JsonObject index(JsonObject param) {
        JsonObject object = new JsonObject();
        object.put("参数",param);
        object.put("处理","成功");
        return object;
    }
}
