package net.kingsilk.qh.oauth.core.wap;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication(scanBasePackages = "net.kingsilk.qh.oauth")
public class QhOAuthWapApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(QhOAuthWapApp.class, args);
    }

}
