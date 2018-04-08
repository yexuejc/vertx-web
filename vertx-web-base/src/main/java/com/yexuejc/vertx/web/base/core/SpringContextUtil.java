package com.yexuejc.vertx.web.base.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring 获取上下文工具
 *
 * @ClassName: SpringContextUtil
 * @Description:
 * @author: maxf
 * @date: 2018/4/8 10:34
 */
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的回调方法
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取对象
     *
     * @return Object 一个以所给名字注册的bean的实例(必须遵循Spring的生成规则)
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    /**
     * 获取对象
     *
     * @return Object 一个以所给名字注册的bean的实例(必须遵循Spring的生成规则)
     */
    public static Object getBean(Class classObj) throws BeansException {
        return applicationContext.getBean(classObj);
    }
}