package net.kingsilk.qh.shop.server.conf;

import net.kingsilk.tb4j.api.alibaba.aliqin.fc.sms.num.query.QueryApi;
import net.kingsilk.tb4j.api.alibaba.aliqin.fc.sms.num.query.impl.QueryApiImpl;
import net.kingsilk.tb4j.api.alibaba.aliqin.fc.sms.num.send.SendApi;
import net.kingsilk.tb4j.api.alibaba.aliqin.fc.sms.num.send.impl.SendApiImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.client.RestOperations;

/**
 *
 */
@Configuration
public class TbSmsApiConf {

    @Bean(value = "tbSmsQueryApi")
    QueryApi queryApi(
            @Autowired
            @Qualifier("mvcConversionService")
                    ConversionService cs,
            RestOperations restTemplate
    ) {
        QueryApiImpl impl = new QueryApiImpl();

        impl.setConversionService(cs);
        impl.setRestTemplate(restTemplate);
        return impl;
    }


    @Bean(value = "tbSmsSendApi")
    SendApi sendApi(
            @Autowired
            @Qualifier("mvcConversionService")
                    ConversionService cs,
            RestOperations restTemplate
    ) {
        SendApiImpl impl = new SendApiImpl();

        impl.setConversionService(cs);
        impl.setRestTemplate(restTemplate);
        //impl.setApiUrl("http://gw.api.tbsandbox.com/router/rest");
        return impl;
    }


}
