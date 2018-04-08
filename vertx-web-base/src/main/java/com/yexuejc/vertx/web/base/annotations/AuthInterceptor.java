package com.yexuejc.vertx.web.base.annotations;

import java.lang.annotation.*;

/**
 * 权限拦截器注解
 * @ClassName: AuthInterceptor
 * @Description:
 * @author: maxf
 * @date: 2018/4/8 14:17
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthInterceptor {
    boolean isVerify() default true;
}
