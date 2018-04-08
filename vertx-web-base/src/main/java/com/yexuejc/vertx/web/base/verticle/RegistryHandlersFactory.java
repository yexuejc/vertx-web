package com.yexuejc.vertx.web.base.verticle;

import com.yexuejc.vertx.web.base.annotations.ServiceMethod;
import com.yexuejc.vertx.web.base.annotations.VerticleMapping;
import com.yexuejc.vertx.web.base.core.EventBusAddress;
import com.yexuejc.vertx.web.base.core.VertxBean;
import com.yexuejc.vertx.web.base.util.StringUtils;
import io.vertx.core.DeploymentOptions;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;

/**
 * 处理器注册工厂
 *
 * @ClassName: RegistryHandlersFactory
 * @Description:
 * @author: maxf
 * @date: 2018/4/8 14:20
 */
public class RegistryHandlersFactory {
    private static Logger logger = LoggerFactory.getLogger(RegistryHandlersFactory.class.getName());

    public static volatile String BASE_ROUTER = "/";
    // 需要扫描注册的Router路径
    private static volatile Reflections reflections = null;

    public RegistryHandlersFactory(String handlerScanAddress, String appPrefix) {
        Objects.requireNonNull(handlerScanAddress, "The router package address scan is empty.");
        reflections = new Reflections(handlerScanAddress);
        this.BASE_ROUTER = appPrefix;
    }

    public RegistryHandlersFactory(String handlerScanAddress) {
        Objects.requireNonNull(handlerScanAddress, "The router package address scan is empty.");
        reflections = new Reflections(handlerScanAddress);
    }

    /**
     * verticle 服务注册
     */
    public void registerVerticle() {
        logger.trace("Register Service Verticle...");
        Set<Class<?>> verticles = reflections.getTypesAnnotatedWith(VerticleMapping.class);
        String busAddressPrefix = "";
        for (Class<?> service : verticles) {
            try {
                if (service.isAnnotationPresent(VerticleMapping.class)) {
                    VerticleMapping routeHandler = service.getAnnotation(VerticleMapping.class);
                    if (StringUtils.isBlank(routeHandler.value())) {
                        busAddressPrefix = service.getName();
                    } else {
                        busAddressPrefix = routeHandler.value();
                    }
                    if (busAddressPrefix.startsWith("/")) {
                        busAddressPrefix = busAddressPrefix.substring(1, busAddressPrefix.length());
                    }
                    if (!BASE_ROUTER.endsWith("/")) {
                        busAddressPrefix = BASE_ROUTER + "/" + busAddressPrefix;
                    } else {
                        busAddressPrefix = BASE_ROUTER + busAddressPrefix;
                    }
                    if (busAddressPrefix.endsWith("/")) {
                        busAddressPrefix = busAddressPrefix.substring(0, busAddressPrefix.length() - 1);
                    }
                    if (busAddressPrefix.startsWith("/")) {
                        busAddressPrefix = busAddressPrefix.substring(1, busAddressPrefix.length());
                    }
                    if (BASE_ROUTER.equals(busAddressPrefix)) {
                        /***** 每一个方法都部署一个verticle *****/
                        Method[] methods = service.getDeclaredMethods();
                        for (Method method : methods) {
                            if (method.isAnnotationPresent(ServiceMethod.class)) {
                                ServiceMethod serviceMethod = method.getAnnotation(ServiceMethod.class);
                                String methodTarget = serviceMethod.value();
                                if (!methodTarget.startsWith("/")) {
                                    methodTarget = "/" + methodTarget;
                                }
                                logger.trace("[Method] The register processor address is {}", EventBusAddress.positiveFormate(busAddressPrefix + methodTarget));
                                VertxBean.getStandardVertx().deployVerticle(new VerticleHandlerFactory(toLowerCaseFirstOne(service.getSimpleName()), EventBusAddress.positiveFormate(busAddressPrefix.concat(methodTarget))), new DeploymentOptions());
                            }
                        }
                    } else {
                        logger.trace("The register processor address is {}", EventBusAddress.positiveFormate(busAddressPrefix));
                        VertxBean.getStandardVertx().deployVerticle(new VerticleHandlerFactory(toLowerCaseFirstOne(service.getSimpleName()), EventBusAddress.positiveFormate(busAddressPrefix)), new DeploymentOptions());
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), "The {} Verticle register Service is fail，{}", service, e.getMessage());
            }
        }
    }

    /**
     * 获得Spring bean name，交给Spring来提取容器中的bean
     *
     * @param serviceName
     * @return
     */
    private static String toLowerCaseFirstOne(String serviceName) {
        if (Character.isLowerCase(serviceName.charAt(0))) {
            return serviceName;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(serviceName.charAt(0))).append(serviceName.substring(1)).toString();
        }
    }
}
