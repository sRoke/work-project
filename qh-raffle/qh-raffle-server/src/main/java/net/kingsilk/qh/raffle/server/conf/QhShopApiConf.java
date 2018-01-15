package net.kingsilk.qh.raffle.server.conf;

import net.kingsilk.qh.raffle.QhRaffleProperties;
import net.kingsilk.qh.shop.api.brandApp.shop.ShopApi;
import net.kingsilk.qh.shop.client.impl.ShopApiImpl;
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
            QhRaffleProperties qhRaffleProperties
    ) {
        return new ShopApiImpl(restOperations, qhRaffleProperties.getShopUt().getBasePath());
    }
}
