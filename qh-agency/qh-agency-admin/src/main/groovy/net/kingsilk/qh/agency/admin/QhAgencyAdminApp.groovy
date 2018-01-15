package net.kingsilk.qh.agency.admin

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * Created by zll on 10/03/2017.
 */
@SpringBootApplication(scanBasePackages="net.kingsilk.qh.agency")
@CompileStatic
class QhAgencyAdminApp {

    static void main(String[] args) throws Exception {
        SpringApplication.run(QhAgencyAdminApp.class, args)
    }
}
