package net.kingsilk.qh.oauth.client.utils;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "ut")
@Component
public class BaseUrlProps {

    private String basePath;

    public String getBasePath() { return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
