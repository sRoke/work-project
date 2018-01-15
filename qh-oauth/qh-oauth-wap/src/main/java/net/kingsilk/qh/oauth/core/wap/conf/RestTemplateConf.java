package net.kingsilk.qh.oauth.core.wap.conf;

import net.kingsilk.qh.oauth.*;
import net.kingsilk.qh.oauth.util.*;
import org.apache.http.client.*;
import org.apache.http.config.*;
import org.apache.http.conn.socket.*;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.*;
import org.apache.http.ssl.*;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.boot.web.client.*;
import org.springframework.context.annotation.*;
import org.springframework.http.client.*;
import org.springframework.http.converter.*;
import org.springframework.web.client.*;

import javax.net.ssl.*;
import java.net.*;
import java.nio.charset.*;
import java.security.*;
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

//    @Bean
//    ClientHttpRequestFactory clientHttpRequestFactory (){
//        HttpClient httpClient = HttpClientBuilder.create()
//                .build();
//
//        // HttpComponentsClientHttpRequestFactory 与日志级别 "logging.level.org.apache.http: DEBUG" 配合
//        // 以方便调试 RestTemplate 请求头、响应头
//        ClientHttpRequestFactory fac = new HttpComponentsClientHttpRequestFactory(httpClient)
//        return fac
//    }


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

//                HttpClient httpClient = HttpClientBuilder.create()
//                        .build();

                // HttpComponentsClientHttpRequestFactory 与日志级别 "logging.level.org.apache.http: DEBUG" 配合
                // 以方便调试 RestTemplate 请求头、响应头
//                ClientHttpRequestFactory fac = new HttpComponentsClientHttpRequestFactory(httpClient)
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

    // ========================================================================== 外网 + 代理

    /**
     * 要访问外网的 HttpClient
     * @param prop
     * @return
     */
    @Bean
    public HttpClient wwwHttpClient(
            QhOAuthProperties prop
    ) throws KeyManagementException, NoSuchAlgorithmException {
        PlainConnectionSocketFactoryEx httpConnFac = new PlainConnectionSocketFactoryEx();
        SSLContext sslcontext = SSLContexts.custom().build();
        SSLConnectionSocketFactoryEx sslConnFac = new SSLConnectionSocketFactoryEx(sslcontext);
        if (prop.getHttp().getProxy().getEnabled()) {
            SocketAddress address = new InetSocketAddress(
                    prop.getHttp().getProxy().getHost(),
                    prop.getHttp().getProxy().getPort());
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, address);
            httpConnFac.setProxy(proxy);
            sslConnFac.setProxy(proxy);
        }

        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", httpConnFac)
                .register("https", sslConnFac)
                .build();
        BasicHttpClientConnectionManager connMgr = new BasicHttpClientConnectionManager(reg);


        HttpClientBuilder hcb = HttpClientBuilder.create()
                .setConnectionManager(connMgr);
        hcb.build();
        HttpClient httpClient = hcb.build();

        return httpClient;
    }

    /**
     * 访问外网的 RestTemplate
     * @param restTemplateBuilder
     * @return
     */
    @Bean
    @Scope("prototype")
    public RestTemplate wwwRestTemplate(
            RestTemplateBuilder restTemplateBuilder,
            HttpClient wwwHttpClient
    ) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(wwwHttpClient));
        return restTemplate;
    }
}
