package net.kingsilk.qh.raffle.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@SpringBootApplication(scanBasePackages = {"net.kingsilk.qh.raffle"})
public class QhRaffleServerApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(QhRaffleServerApp.class, args);
    }

}
