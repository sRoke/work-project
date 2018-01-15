package net.kingsilk.qh.oauth.core.wap.conf;

import net.kingsilk.wx4j.client.com.api.comAt.*;
import net.kingsilk.wx4j.client.com.api.impl.comAt.*;
import net.kingsilk.wx4j.client.mp.api.appAt.*;
import net.kingsilk.wx4j.client.mp.api.impl.appAt.*;
import net.kingsilk.wx4j.client.mp.api.impl.jsApiTicket.*;
import net.kingsilk.wx4j.client.mp.api.impl.qrCode.*;
import net.kingsilk.wx4j.client.mp.api.impl.snsUser.*;
import net.kingsilk.wx4j.client.mp.api.impl.user.*;
import net.kingsilk.wx4j.client.mp.api.impl.userAt.*;
import net.kingsilk.wx4j.client.mp.api.jsApiTicket.*;
import net.kingsilk.wx4j.client.mp.api.qrCode.*;
import net.kingsilk.wx4j.client.mp.api.snsUser.*;
import net.kingsilk.wx4j.client.mp.api.user.*;
import net.kingsilk.wx4j.client.mp.api.userAt.*;
import org.springframework.context.annotation.*;
import org.springframework.web.client.*;

/**
 *
 */
@Configuration
public class WxMpApiConf {
    @Bean
    public AppAtApi appAtApiImpl(RestOperations restTemplate) {
        return new AppAtApiImpl(restTemplate);
    }

    @Bean
    public JsApiTicketApi jsApiTicketApi(RestOperations restTemplate) {
        return new JsApiTicketApiImpl(restTemplate);
    }

    @Bean
    public SnsUserApi snsUserApi(RestOperations restTemplate) {
        return new SnsUserApiImpl(restTemplate);
    }

    @Bean
    public UserAtApi userAtApi(RestOperations restTemplate) {
        return new UserAtApiImpl(restTemplate);
    }

    @Bean
    public QrCodeApi qrCodeApi(RestOperations restTemplate) {
        return new QrCodeApiImpl(restTemplate);
    }

    @Bean
    public UserApi userApi(RestOperations restTemplate) {
        return new UserApiImpl(restTemplate);
    }

    @Bean
    public ComAtApi comAtApi(RestOperations restTemplate) {
        return new ComAtApiImpl(restTemplate);
    }

}
