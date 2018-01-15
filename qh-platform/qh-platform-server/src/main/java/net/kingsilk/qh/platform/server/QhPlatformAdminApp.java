package net.kingsilk.qh.platform.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@SpringBootApplication(scanBasePackages = {"net.kingsilk.qh.platform"})
public class QhPlatformAdminApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(QhPlatformAdminApp.class, args);
    }

}
