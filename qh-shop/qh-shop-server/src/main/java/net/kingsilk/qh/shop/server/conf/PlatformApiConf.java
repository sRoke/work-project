package net.kingsilk.qh.shop.server.conf;

import net.kingsilk.qh.platform.api.brandApp.BrandAppApi;
import net.kingsilk.qh.platform.client.BrandAppApiImpl;
import net.kingsilk.qh.shop.service.QhShopProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;

@Configuration
public class PlatformApiConf {

    @Bean
    public BrandAppApi brandAppApi(
            @Qualifier("oauthRestTemplate")
                    RestOperations restOperations,
            QhShopProperties qhShopProperties

    ) {
        return new BrandAppApiImpl(restOperations, qhShopProperties.getPlatformUt().getBasePath());
    }

}
