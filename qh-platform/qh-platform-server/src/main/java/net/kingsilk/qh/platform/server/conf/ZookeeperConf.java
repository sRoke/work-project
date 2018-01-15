package net.kingsilk.qh.platform.server.conf;

import net.kingsilk.qh.platform.service.QhPlatformProperties;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;

/**
 *
 */
@Configuration
public class ZookeeperConf {

    @Bean
    public RetryPolicy zkRetry() {
        return new ExponentialBackoffRetry(1000, 3);
    }

    @Bean(initMethod = "start", destroyMethod = "close")
    public CuratorFramework zkClient(
            QhPlatformProperties qhPlatformProperties,
            RetryPolicy zkRetry
    ) {
        return CuratorFrameworkFactory.newClient(
                qhPlatformProperties.getZk().getConnStr(),
                zkRetry
        );
    }

    @Bean
    public ZookeeperLockRegistry zkLockRegistry(
            CuratorFramework zkClient
    ) {
        return new ZookeeperLockRegistry(zkClient);
    }
}
