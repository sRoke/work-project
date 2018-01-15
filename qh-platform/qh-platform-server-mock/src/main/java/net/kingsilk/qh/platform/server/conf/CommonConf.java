package net.kingsilk.qh.platform.server.conf;

import org.springframework.context.annotation.*;

import java.util.*;

/**
 *
 */
@Configuration
public class CommonConf {

    @Bean
    public Map<String, String> mm() {
        Map<String, String> m = new LinkedHashMap<>();
        m.put("a", "aa");
        m.put("b", "bb");
        return m;
    }
}















