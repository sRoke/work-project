package net.kingsilk.qh.vote.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@SpringBootApplication(scanBasePackages = {"net.kingsilk.qh.vote"})
public class QhVoteServerApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(QhVoteServerApp.class, args);
    }

}
