package net.kingsilk.qh.shop.server.conf;

import net.kingsilk.qh.shop.service.QhShopProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
class CommonConf {

    @Bean
    QhShopProperties qhShopProperties() {
        return new QhShopProperties();
    }

}















