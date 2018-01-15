package net.kingsilk.qh.shop.server.conf;

import io.swagger.jaxrs.config.BeanConfig;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SwaggerConf {


    @Bean
    BeanConfig swaggerBeanConfig(
            BuildProperties buildProperties
    ) {
        String[] schemes = {"https", "http"};
        BeanConfig config = new BeanConfig();
        config.setTitle(buildProperties.getName());
        config.setVersion(buildProperties.getVersion());
        config.setBasePath("/api");
        config.setSchemes(schemes);
        config.setResourcePackage("net.kingsilk.qh.shop.api");
        config.setScan(true);
        return config;
    }
}