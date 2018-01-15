package net.kingsilk.qh.shop.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "net.kingsilk.qh.shop"
})
public class QhShopServerApp {
    public static void main(String[] args) {
        SpringApplication.run(QhShopServerApp.class, args);
    }

}
