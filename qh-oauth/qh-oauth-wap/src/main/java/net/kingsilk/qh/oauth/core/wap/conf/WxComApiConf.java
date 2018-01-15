package net.kingsilk.qh.oauth.core.wap.conf;

import net.kingsilk.wx4j.client.com.api.app.*;
import net.kingsilk.wx4j.client.com.api.impl.app.*;
import net.kingsilk.wx4j.client.com.api.impl.quota.*;
import net.kingsilk.wx4j.client.com.api.quota.*;
import org.springframework.context.annotation.*;
import org.springframework.web.client.*;

/**
 *
 */
@Configuration
public class WxComApiConf {

    @Bean
    public QuotaApi snsUserApi(RestOperations restTemplate) {
        return new QuotaApiImpl(restTemplate);
    }

    @Bean
    public AppApi appApi(RestOperations restTemplate) {
        return new AppApiImpl(restTemplate);
    }


}
