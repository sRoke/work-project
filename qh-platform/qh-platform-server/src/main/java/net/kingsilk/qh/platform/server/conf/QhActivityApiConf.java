package net.kingsilk.qh.platform.server.conf;

import net.kingsilk.qh.activity.api.brandApp.vote.authorities.AuthoritiesApi;
import net.kingsilk.qh.activity.client.impl.AuthoritiesApiImpl;
import net.kingsilk.qh.platform.service.QhPlatformProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;

@Configuration
public class QhActivityApiConf {

    @Bean
    public AuthoritiesApi qhActivityAuthoritiesApi(
            @Qualifier("oauthRestTemplate")
                    RestOperations restOperations,
            QhPlatformProperties qhPlatformProperties
    ) {
        return new AuthoritiesApiImpl(restOperations, qhPlatformProperties.getActivityUt().getBasePath());
    }
}
