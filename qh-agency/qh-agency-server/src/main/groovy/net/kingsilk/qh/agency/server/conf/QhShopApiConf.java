package net.kingsilk.qh.agency.server.conf;

import groovy.transform.CompileStatic;
import net.kingsilk.qh.agency.QhAgencyProperties;
import net.kingsilk.qh.shop.api.brandApp.sync.SyncItemApi;
import net.kingsilk.qh.shop.client.impl.SyncItemImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;

/**
 *
 */
@Configuration
public class QhShopApiConf {

    @Bean
    public SyncItemApi syncItemApi(

            @Qualifier("oauthRestTemplate")
                    RestOperations restOperations,
            QhAgencyProperties qhAgencyProperties
    ) {
        return new SyncItemImpl(restOperations, qhAgencyProperties.getShopUt().getBasePath());
    }
}
