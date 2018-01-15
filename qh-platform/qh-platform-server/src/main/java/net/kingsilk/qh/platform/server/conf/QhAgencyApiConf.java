package net.kingsilk.qh.platform.server.conf;

import net.kingsilk.qh.agency.api.brandApp.authorities.AuthoritiesApi;
import net.kingsilk.qh.agency.client.AuthoritiesApiImpl;
import net.kingsilk.qh.platform.service.QhPlatformProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;


@Configuration
public class QhAgencyApiConf {


    @Bean
    public AuthoritiesApi qhAgencyAuthoritiesApi(
            @Qualifier("oauthRestTemplate")
                    RestOperations restOperations,
            QhPlatformProperties qhPlatformProperties
    ) {
        return new AuthoritiesApiImpl(restOperations, qhPlatformProperties.getAgencyUt().getBasePath());
    }
}
