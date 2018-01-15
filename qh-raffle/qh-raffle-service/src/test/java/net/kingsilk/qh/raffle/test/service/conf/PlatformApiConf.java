package net.kingsilk.qh.raffle.test.service.conf;

import net.kingsilk.qh.platform.api.brandApp.BrandAppApi;
import net.kingsilk.qh.platform.client.BrandAppApiImpl;
import net.kingsilk.qh.raffle.QhRaffleProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;

@Configuration
public class PlatformApiConf {

    @Bean
    public BrandAppApi brandAppApi(
            @Qualifier("restTemplate")
                    RestOperations restOperations,
            QhRaffleProperties qhRaffleProperties

    ) {
        return new BrandAppApiImpl(restOperations, qhRaffleProperties.getPlatformUt().getBasePath());
    }

}
