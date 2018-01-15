package net.kingsilk.qh.platform.client.conf;


import net.kingsilk.qh.platform.api.brandCom.*;
import net.kingsilk.qh.platform.api.brand.*;
import net.kingsilk.qh.platform.client.*;
import org.springframework.context.annotation.*;
import org.springframework.web.client.*;

@Configuration
public class PlatformApiConf {

    @Bean
    BrandComApi brandComApiImpl(RestOperations restTemplate) {
        return new BrandComApiImpl(restTemplate);
    }

    @Bean
    BrandApi brandApiImpl(RestOperations restTemplate) {
        return new BrandApiImpl(restTemplate);
    }
}
