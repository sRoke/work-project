package net.kingsilk.qh.agency.server

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 *
 */
@SpringBootApplication(scanBasePackages = ["net.kingsilk.qh.agency","net.kingsilk.qh.oauth.client.utils"])
@CompileStatic
class QhAgencyServerApp {
    static void main(String[] args) throws Exception {
        SpringApplication.run(QhAgencyServerApp.class, args)
    }
}
