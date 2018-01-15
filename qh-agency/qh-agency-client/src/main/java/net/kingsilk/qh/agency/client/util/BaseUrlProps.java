package net.kingsilk.qh.agency.client.util;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "agencyUt")
@Component(value = "agencyProps")
public class BaseUrlProps {

    private String basePath;

    public String getBasePath() { return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
