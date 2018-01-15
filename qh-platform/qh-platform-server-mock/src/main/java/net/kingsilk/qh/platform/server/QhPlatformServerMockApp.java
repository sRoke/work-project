package net.kingsilk.qh.platform.server;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

/**
 *
 */
@SpringBootApplication(scanBasePackages = "net.kingsilk.qh.platform")
public class QhPlatformServerMockApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(QhPlatformServerMockApp.class, args);
    }

}
