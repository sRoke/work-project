package net.kingsilk.qh.oauth.core.wap.conf;

import org.apache.http.client.*;
import org.apache.http.conn.*;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.*;
import org.springframework.context.annotation.*;
import org.springframework.http.client.*;

//import net.kingsilk.qh.agency.wap.resource.item.ItemResource

@Configuration
public class HttpClientConf {

    @Bean
    public HttpClientConnectionManager httpClientConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setValidateAfterInactivity(100);
        connectionManager.setMaxTotal(100);

        return connectionManager;
    }

    @Bean
    public HttpClient httpClient(
            HttpClientConnectionManager httpClientConnectionManager
    ) {
        return HttpClientBuilder.create()
                .setConnectionManager(httpClientConnectionManager)
                .build();
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(
            HttpClient httpClient
    ) {
        // HttpComponentsClientHttpRequestFactory 与日志级别 "logging.level.org.apache.http: DEBUG" 配合
        // 以方便调试 RestTemplate 请求头、响应头
        ClientHttpRequestFactory fac = new HttpComponentsClientHttpRequestFactory(httpClient);
        return fac;
    }

}
