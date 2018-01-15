package net.kingsilk.qh.activity.server.conf;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

@Configuration
public class HttpClientConf {


    @Bean
    HttpClientConnectionManager httpClientConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setValidateAfterInactivity(100);
        connectionManager.setMaxTotal(100);

        return connectionManager;
    }

    @Bean
    HttpClient httpClient(
            HttpClientConnectionManager httpClientConnectionManager
    ) {
        return HttpClientBuilder.create()
                .setConnectionManager(httpClientConnectionManager)
                .build();
    }

    @Bean
    ClientHttpRequestFactory clientHttpRequestFactory(
            HttpClient httpClient
    ) {
        // HttpComponentsClientHttpRequestFactory 与日志级别 "logging.level.org.apache.http: DEBUG" 配合
        // 以方便调试 RestTemplate 请求头、响应头
        ClientHttpRequestFactory fac = new HttpComponentsClientHttpRequestFactory(httpClient);
        return fac;
    }

}
