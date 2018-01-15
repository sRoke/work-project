package net.kingsilk.qh.activity.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@SpringBootApplication(scanBasePackages = {"net.kingsilk.qh.activity"})
public class QhActivityServerApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(QhActivityServerApp.class, args);
    }

}
