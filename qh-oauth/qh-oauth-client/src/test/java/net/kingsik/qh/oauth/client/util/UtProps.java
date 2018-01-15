package net.kingsik.qh.oauth.client.util;


import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

@Configuration
@ConfigurationProperties(prefix = "ut")
@Component
public class UtProps {


    private String a;


    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }
}
