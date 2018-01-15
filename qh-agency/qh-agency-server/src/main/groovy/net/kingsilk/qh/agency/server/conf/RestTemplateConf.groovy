package net.kingsilk.qh.agency.server.conf

import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode
import net.kingsilk.qh.agency.QhAgencyProperties
import net.kingsilk.qh.agency.service.HttpRequestInterceptorService
import net.kingsilk.qh.agency.util.PlainConnectionSocketFactoryEx
import net.kingsilk.qh.agency.util.SSLConnectionSocketFactoryEx
import net.kingsilk.qh.agency.util.errorHandler.Oauth2ResponseErrorHandler
import org.apache.http.client.HttpClient
import org.apache.http.config.Registry
import org.apache.http.config.RegistryBuilder
import org.apache.http.conn.socket.ConnectionSocketFactory
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.apache.http.ssl.SSLContexts
import org.springframework.boot.autoconfigure.web.HttpMessageConverters
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.client.RestTemplateCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.client.RestTemplate

import javax.net.ssl.SSLContext
import java.nio.charset.Charset

@Configuration
class RestTemplateConf {

    // ========================================================================== 内网

    @Bean
    @CompileStatic(TypeCheckingMode.SKIP)
    HttpMessageConverters httpMessageConverters() {

        // String 类型 ： ISO-8859-1 -> UTF-8
        return new HttpMessageConverters(true, [
                new StringHttpMessageConverter(Charset.forName("UTF-8"))
        ])
    }

//    @Bean
//    ClientHttpRequestFactory clientHttpRequestFactory() {
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
    RestTemplateCustomizer myRestTemplateCustomizer(
            ClientHttpRequestFactory clientHttpRequestFactory
    ) {
        return new RestTemplateCustomizer() {

            @Override
            void customize(RestTemplate restTemplate) {

//                HttpClient httpClient = HttpClientBuilder.create()
//                        .build();

                // HttpComponentsClientHttpRequestFactory 与日志级别 "logging.level.org.apache.http: DEBUG" 配合
                // 以方便调试 RestTemplate 请求头、响应头
//                ClientHttpRequestFactory fac = new HttpComponentsClientHttpRequestFactory(httpClient)
                restTemplate.setRequestFactory(clientHttpRequestFactory);

            }
        }
    }

    /**
     * 访问内网的 RestTemplate
     * @param restTemplateBuilder
     * @return
     */
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder.build()
        return restTemplate;
    }

    // ========================================================================== 外网 + 代理

    /**
     * 要访问外网的 HttpClient
     * @param prop
     * @return
     */
    @Bean
    HttpClient wwwHttpClient(QhAgencyProperties prop) {
        PlainConnectionSocketFactoryEx httpConnFac = new PlainConnectionSocketFactoryEx();
        SSLContext sslcontext = SSLContexts.custom().build();
        SSLConnectionSocketFactory sslConnFac = new SSLConnectionSocketFactoryEx(sslcontext);
        if (prop.http.proxy.enabled) {
            SocketAddress address = new InetSocketAddress(
                    prop.http.proxy.host,
                    prop.http.proxy.port);
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, address);
            httpConnFac.proxy = proxy
            sslConnFac.proxy = proxy
        }

        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("http", httpConnFac)
                .register("https", sslConnFac)
                .build();
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(reg);


        HttpClientBuilder hcb = HttpClientBuilder.create()
                .setConnectionManager(connMgr)
        hcb.build()
        HttpClient httpClient = hcb.build();

        return httpClient
    }

    /**
     * 访问外网的 RestTemplate
     * @param restTemplateBuilder
     * @return
     */
    @Bean
    RestTemplate wwwRestTemplate(
            RestTemplateBuilder restTemplateBuilder,
            HttpClient wwwHttpClient
    ) {
        RestTemplate restTemplate = restTemplateBuilder.build()
        restTemplate.requestFactory = new HttpComponentsClientHttpRequestFactory(wwwHttpClient)
        return restTemplate;
    }

    @Bean
    RestTemplate oauthRestTemplate(
            RestTemplateBuilder restTemplateBuilder
    ) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        List<ClientHttpRequestInterceptor> newInterceptors = new LinkedList<>(restTemplate.getInterceptors());
        newInterceptors.add(new HttpRequestInterceptorService());
        restTemplate.setInterceptors(newInterceptors);
        HttpClient httpClient = HttpClientBuilder.create().build();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        restTemplate.setErrorHandler(new Oauth2ResponseErrorHandler())
        return restTemplate;
    }
}
