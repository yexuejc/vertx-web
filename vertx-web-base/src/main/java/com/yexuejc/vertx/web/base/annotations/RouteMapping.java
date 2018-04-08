package com.yexuejc.vertx.web.base.annotations;

import java.lang.annotation.*;

/**
 * Router API Mehtod 标识注解
 * @ClassName: RouteMapping
 * @Description:
 * @author: maxf
 * @date: 2018/4/8 14:17
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RouteMapping {

    String value() default "";
    /**** 是否覆盖 *****/
    boolean isCover() default true;
    /**** 使用http method *****/
    RouteMethod method() default RouteMethod.GET;
    /**** 接口描述 *****/
    String descript() default "";

}
