package net.kingsilk.qh.platform.client.util;


import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

@Configuration
@ConfigurationProperties(prefix = "ut")
@Component
public class UtProps {

    public String basePath;

}
