package com.yexuejc.vertx.web.base.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Router API类 标识注解
 * @ClassName: RouteHandler
 * @Description:
 * @author: maxf
 * @date: 2018/4/8 14:17
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RouteHandler {
    String value() default "";
    /*** 是否直接暴露(开放平台) ***/
    boolean isOpen() default false;
}
