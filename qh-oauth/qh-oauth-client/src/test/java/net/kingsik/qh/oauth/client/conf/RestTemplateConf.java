package net.kingsik.qh.oauth.client.conf;

import org.springframework.boot.autoconfigure.web.*;
import org.springframework.boot.web.client.*;
import org.springframework.context.annotation.*;
import org.springframework.http.client.*;
import org.springframework.http.converter.*;
import org.springframework.web.client.*;

import java.nio.charset.*;
import java.util.*;

@Configuration
public class RestTemplateConf {

    // ========================================================================== 内网

    @Bean
    public HttpMessageConverters httpMessageConverters() {

        // String 类型 ： ISO-8859-1 -> UTF-8
        return new HttpMessageConverters(true, Arrays.asList(
                new StringHttpMessageConverter(Charset.forName("UTF-8"))
        ));
    }


    /**
     * 方便打印日志
     */
    @Bean
    public RestTemplateCustomizer myRestTemplateCustomizer(
            ClientHttpRequestFactory clientHttpRequestFactory
    ) {
        return new RestTemplateCustomizer() {

            @Override
            public void customize(RestTemplate restTemplate) {

                // HttpComponentsClientHttpRequestFactory 与日志级别 "logging.level.org.apache.http: DEBUG" 配合
                // 以方便调试 RestTemplate 请求头、响应头
                restTemplate.setRequestFactory(clientHttpRequestFactory);

            }
        };
    }

    /**
     * 访问内网的 RestTemplate
     * @param restTemplateBuilder
     * @return
     */
    @Bean
//    @Scope("prototype")
    public RestTemplate restTemplate(
            RestTemplateBuilder restTemplateBuilder
    ) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate;
    }

}
