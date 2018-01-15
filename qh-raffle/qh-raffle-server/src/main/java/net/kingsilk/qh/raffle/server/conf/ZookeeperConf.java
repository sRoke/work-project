package net.kingsilk.qh.raffle.server.conf;

import net.kingsilk.qh.raffle.QhRaffleProperties;
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
            QhRaffleProperties qhRaffleProperties,
            RetryPolicy zkRetry
    ) {
        CuratorFramework zkClient = CuratorFrameworkFactory.newClient(
                qhRaffleProperties.getZk().getConnStr(),
                zkRetry
        );
        return zkClient;
    }

    @Bean
    public ZookeeperLockRegistry zkLockRegistry(
            CuratorFramework zkClient
    ) {
        return new ZookeeperLockRegistry(zkClient);
    }
}
