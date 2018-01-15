package net.kingsilk.qh.platform.server.conf;

import net.kingsilk.qh.platform.service.QhPlatformProperties;
import net.kingsilk.qh.shop.api.brandApp.authorities.AuthoritiesApi;
import net.kingsilk.qh.shop.client.impl.AuthoritiesApiImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;

@Configuration
public class QhShopApiConf {


    @Bean
    public AuthoritiesApi qhShopAuthoritiesApi(
            @Qualifier("oauthRestTemplate")
                    RestOperations restOperations,
            QhPlatformProperties qhPlatformProperties
    ) {
        return new AuthoritiesApiImpl(restOperations, qhPlatformProperties.getShopUt().getBasePath());
    }
}
