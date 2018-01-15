package net.kingsilk.qh.shop.server.conf;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class TaskConf {

    @Bean
    @Qualifier("initThreadPool")
    public ThreadPoolTaskScheduler initThreadPool() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(20);
        taskScheduler.initialize();
        return taskScheduler;
    }
}
