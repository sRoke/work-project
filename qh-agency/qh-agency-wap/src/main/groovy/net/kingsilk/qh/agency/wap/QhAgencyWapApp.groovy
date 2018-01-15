package net.kingsilk.qh.agency.wap

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication(scanBasePackages="net.kingsilk.qh.agency")
@CompileStatic
class QhAgencyWapApp {

    static void main(String[] args) throws Exception {
        SpringApplication.run(QhAgencyWapApp.class, args)
    }
}
