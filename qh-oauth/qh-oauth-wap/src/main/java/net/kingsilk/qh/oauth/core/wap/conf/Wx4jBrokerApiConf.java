package net.kingsilk.qh.oauth.core.wap.conf;

import net.kingsilk.qh.oauth.*;
import net.kingsilk.wx4j.broker.api.wxCom.mp.user.auth.*;
import net.kingsilk.wx4j.broker.api.wxMp.user.auth.*;
import net.kingsilk.wx4j.broker.client.wxCom.mp.user.auth.*;
import net.kingsilk.wx4j.broker.client.wxMp.user.auth.*;
import org.springframework.context.annotation.*;
import org.springframework.core.env.*;
import org.springframework.web.client.*;

@Configuration
public class Wx4jBrokerApiConf {

    @Bean
    public WxMpUserAuthApi wxMpUserAuthApi(
            RestOperations restTemplate,
            QhOAuthProperties props
    ) {
        WxMpUserAuthApiImpl impl = new WxMpUserAuthApiImpl();
        impl.setRestTemplate(restTemplate);
        impl.setBaseUri(props.getWx4j().getBroker().getApiBaseUri());
        return impl;
    }

    @Bean
    public WxComMpUserAuthApi wxComMpUserAuthApi(
            RestOperations restTemplate,
            QhOAuthProperties props
    ) {
        WxComMpUserAuthApiImpl impl = new WxComMpUserAuthApiImpl();
        impl.setRestTemplate(restTemplate);
        impl.setBaseUri(props.getWx4j().getBroker().getApiBaseUri());
        return impl;
    }

}
