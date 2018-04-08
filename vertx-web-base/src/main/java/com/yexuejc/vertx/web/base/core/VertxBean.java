package com.yexuejc.vertx.web.base.core;

import com.yexuejc.vertx.web.base.util.NetworkUtil;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * 标准的Vertx 对象
 *
 * @ClassName: VertxBean
 * @Description:
 * @author: maxf
 * @date: 2018/4/8 14:03
 */
public class VertxBean {

    private static Logger logger = LoggerFactory.getLogger(VertxBean.class);
    /**
     * 是否集群模式，默认否
     **/
    public static volatile boolean isCluster = false;
    /*** Vertx vertx 对象 **/
    private static Vertx standardVertx = null;
    private static Vertx localVertx = null;

    /**
     * 初始设置standardVertx对象，此方法为集群模式
     *
     * @param vertx     需要集群的vertx 对象
     * @param ipAddress 需要集群的宿主ip(避免多网卡集群失败)
     * @throws InterruptedException
     */
    private static void init(Vertx vertx, String ipAddress) throws InterruptedException {
        ClusterManager clusterManager = new ZookeeperClusterManager();
        if (ipAddress == null || "".equals(ipAddress)) {
            ipAddress = NetworkUtil.getIpAddress();
        }
        VertxOptions options = new VertxOptions().setClusterManager(clusterManager).setClusterHost(ipAddress);
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                standardVertx = (Vertx) res.result();
                localVertx = vertx;
            } else {
                logger.error("Cluster failed: " + res.cause() + ";Please wait for 30 seconds, auto retry connection......");
            }
        });
        Thread.sleep(3 * 1000);
    }

    /**
     * 初始设置standardVertx 对象，此方法为单例
     */
    private static void init(Vertx vertx) {
        Objects.requireNonNull(vertx, "The vertx object is empty.");
        standardVertx = vertx;
        localVertx = vertx;
    }

    /**
     * 设置vertx集群,默认读取网卡ip集群，不太推荐，如果只有一个网卡时可以
     */
    public static Vertx setClusterVertx(Vertx vertx) {
        Objects.requireNonNull(vertx, "Need to cluster the vertx object is empty.");
        String ipAddress = NetworkUtil.getIpAddress();
        try {
            setClusterVertx(vertx, ipAddress);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return standardVertx;
    }

    /**
     * 设置vertx集群,默认读取网卡ip集群
     */
    public static Vertx setClusterVertx(Vertx vertx, String ipAddress) {
        Objects.requireNonNull(vertx, "Need to cluster the vertx object is empty.");
        Objects.requireNonNull(ipAddress, "Need to cluster the host address is empty.");
        try {
            init(vertx, ipAddress);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return standardVertx;
    }

    /**
     * 获得标准的vertx 对象,集群不推荐使用
     */
    public static Vertx getStandardVertx(Vertx vertx) {
        if (!isCluster) {
            init(vertx);
        } else {
            try {
                setClusterVertx(vertx);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return standardVertx;
    }

    /**
     * 获得标准的vertx 对象,如果还未初始化设置，将错误提示
     *
     * @return io.vertx.core.Vertx
     * @throws NullPointerException
     * @method getStandardVertx
     * @author Neil.Zhou
     * @date 2017/9/20 17:52
     * @see #setClusterVertx(Vertx vertx)
     */
    public static Vertx getStandardVertx() {
        Objects.requireNonNull(standardVertx, "Please perform vertx initialization, otherwise will block the subsequent operations.");
        return standardVertx;
    }

    /**
     * 获得本地vertx
     * 在初始化vertx 对象时，默认保存一份本地vertx对象
     *
     * @return io.vertx.core.Vertx
     * @throws
     * @method getLocalVertx
     * @author Neil.Zhou
     * @date 2017/9/20 22:59
     */
    public static Vertx getLocalVertx() {
        Objects.requireNonNull(localVertx, "Please perform vertx initialization, otherwise will block the subsequent operations.");
        return localVertx;
    }
}
