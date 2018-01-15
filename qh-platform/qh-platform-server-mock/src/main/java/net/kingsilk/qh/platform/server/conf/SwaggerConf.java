package net.kingsilk.qh.platform.server.conf;

import io.swagger.jaxrs.config.*;
import org.springframework.context.annotation.*;

@Configuration
class SwaggerConf {


    @Bean
    BeanConfig swaggerBeanConfig() {
        String[] schemes = {"https", "http"};
        BeanConfig config = new BeanConfig();
        config.setTitle("Swagger sample app");
        config.setVersion("1.0.0");
        config.setBasePath("/api");
        config.setSchemes(schemes);
        config.setResourcePackage("net.kingsilk.qh.platform.server.resource");
        config.setScan(true);
        return config;
    }
}