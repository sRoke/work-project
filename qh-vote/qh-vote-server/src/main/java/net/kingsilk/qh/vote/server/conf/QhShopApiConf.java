package net.kingsilk.qh.vote.server.conf;

import net.kingsilk.qh.shop.api.brandApp.shop.ShopApi;
import net.kingsilk.qh.shop.client.impl.ShopApiImpl;
import net.kingsilk.qh.vote.service.QhVoteProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;

@Configuration
public class QhShopApiConf {

    @Bean
    public ShopApi qhShopApi(

            @Qualifier("oauthRestTemplate")
                    RestOperations restOperations,
            QhVoteProperties qhVoteProperties
    ) {
        return new ShopApiImpl(restOperations, qhVoteProperties.getShopUt().getBasePath());
    }
}
