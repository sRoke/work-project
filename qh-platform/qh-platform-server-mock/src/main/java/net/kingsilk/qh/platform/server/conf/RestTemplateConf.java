package net.kingsilk.qh.platform.server.conf;

import org.apache.http.client.*;
import org.apache.http.impl.client.*;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.boot.web.client.*;
import org.springframework.context.annotation.*;
import org.springframework.http.client.*;
import org.springframework.http.converter.*;
import org.springframework.web.client.*;

import java.nio.charset.*;
import java.util.*;

/**
 *
 */
@Configuration
class RestTemplateConf {


    @Bean
    HttpMessageConverters httpMessageConverters() {

        // String 类型 ： ISO-8859-1 -> UTF-8
        return new HttpMessageConverters(
                true,
                Arrays.asList(
                        new StringHttpMessageConverter(Charset.forName("UTF-8"))
                ));
    }

    @Bean
    ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpClient httpClient = HttpClientBuilder.create()
                .build();

        // HttpComponentsClientHttpRequestFactory 与日志级别 "logging.level.org.apache.http: DEBUG" 配合
        // 以方便调试 RestTemplate 请求头、响应头
        ClientHttpRequestFactory fac = new HttpComponentsClientHttpRequestFactory(httpClient);
        return fac;
    }

    /**
     * 方便打印日志
     */
    @Bean
    RestTemplateCustomizer myRestTemplateCustomizer() {
        return new RestTemplateCustomizer() {

            @Override
            public void customize(RestTemplate restTemplate) {

                HttpClient httpClient = HttpClientBuilder.create()
                        .build();

                // HttpComponentsClientHttpRequestFactory 与日志级别 "logging.level.org.apache.http: DEBUG" 配合
                // 以方便调试 RestTemplate 请求头、响应头
                ClientHttpRequestFactory fac = new HttpComponentsClientHttpRequestFactory(httpClient);
                restTemplate.setRequestFactory(fac);

            }
        };
    }

}
