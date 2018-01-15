package net.kingsilk.qh.activity.server.conf;

import net.kingsilk.qh.activity.service.QhActivityProperties;
import net.kingsilk.qh.platform.api.brandApp.BrandAppApi;
import net.kingsilk.qh.platform.client.BrandAppApiImpl;
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
            QhActivityProperties qhActivityProperties

    ) {
        return new BrandAppApiImpl(restOperations, qhActivityProperties.getPlatformUt().getBasePath());
    }

}
