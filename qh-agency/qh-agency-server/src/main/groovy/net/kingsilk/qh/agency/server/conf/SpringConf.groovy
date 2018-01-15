package net.kingsilk.qh.agency.server.conf

import net.kingsilk.qh.agency.service.QiNiuSdk
import net.kingsilk.qh.agency.service.UploadService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

/**
 * Created by tpx on 17-3-15.
 */
@Configuration
class SpringConf {

    @Autowired
    private Environment env;

    @Bean
    QiNiuSdk qiNiuSdk() {
        return new QiNiuSdk(
                env.getProperty("qh.qiniu.accessKey"),
                env.getProperty("qh.qiniu.secretKey"),
                env.getProperty("qh.qiniu.defaultBucket")
        )
    }

    @Bean
    UploadService uploadService() {
        return new UploadService(
                env.getProperty("qh.qiniu.accessKey"),
                env.getProperty("qh.qiniu.secretKey"),
                env.getProperty("qh.qiniu.defaultBucket")
        )
    }
}
