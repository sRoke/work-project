package net.kingsilk.qh.shop.server.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.transform.CompileStatic;
import groovy.transform.TypeCheckingMode;
import net.kingsilk.qh.shop.service.QhShopProperties;
import net.kingsilk.qh.shop.service.service.HttpRequestInterceptorService;
import net.kingsilk.qh.shop.service.util.PlainConnectionSocketFactoryEx;
import net.kingsilk.qh.shop.service.util.SSLConnectionSocketFactoryEx;
import net.kingsilk.qh.shop.service.util.errorHandler.Oauth2ResponseErrorHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


@Configuration
class RestTemplateConf {

    // ========================================================================== 内网

    @Bean
    @CompileStatic(TypeCheckingMode.SKIP)
    HttpMessageConverters httpMessageConverters(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter(objectMapper);

        // 微信部分API返回的content-type 不正确，是 text/plain
        jsonConverter.setSupportedMediaTypes(new ArrayList<>(
                Arrays.asList(
                        MediaType.APPLICATION_JSON,
                        new MediaType("application", "*+json"),
                        MediaType.TEXT_PLAIN)
        ));


        return new HttpMessageConverters(true, Arrays.asList(

                // 启用 Jaxb 注解
                jsonConverter,

                // String 类型 ： ISO-8859-1 -> UTF-8
                new StringHttpMessageConverter(Charset.forName("UTF-8"))
        ));
    }

//    @Bean
//    ClientHttpRequestFactory clientHttpRequestFactory() {
//        HttpClient httpClient = HttpClientBuilder.create()
//                .build();
//
//        // HttpComponentsClientHttpRequestFactory 与日志级别 "logging.level.org.apache.http: DEBUG" 配合
//        // 以方便调试 RestTemplate 请求头、响应头
//
//        ClientHttpRequestFactory fac = new HttpComponentsClientHttpRequestFactory(httpClient);
//        return fac;
//    }

//    @Bean
//    ClientHttpRequestFactory clientHttpRequestFactory(){
//        return new OkHttpClientHttpRequestFactory();
//    }

    /**
     * 方便打印日志
     */
    @Bean
    RestTemplateCustomizer myRestTemplateCustomizer(
            ClientHttpRequestFactory clientHttpRequestFactory

    ) {
        return (RestTemplate restTemplate) -> {

//                HttpClient httpClient = HttpClientBuilder.create()
//                        .build();

            // HttpComponentsClientHttpRequestFactory 与日志级别 "logging.level.org.apache.http: DEBUG" 配合
            // 以方便调试 RestTemplate 请求头、响应头
//                ClientHttpRequestFactory fac = new HttpComponentsClientHttpRequestFactory(httpClient)
            restTemplate.setRequestFactory(clientHttpRequestFactory);

        };
    }

//    @Bean
//    RestTemplateBuilder restTemplateBuilder() {
//        return new RestTemplateBuilder();
//    }

    /**
     * 访问内网的 RestTemplate
     *
     * @return
     */

    @Bean
    @Scope("prototype")
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    // ========================================================================== 外网 + 代理
//    @Bean
//    QhShopProperties prop() {
//        return new QhShopProperties();
//    }

    /**
     * 要访问外网的 HttpClient
     */
    @Bean
    HttpClient wwwHttpClient(QhShopProperties qhShopProperties) throws KeyManagementException, NoSuchAlgorithmException {

        PlainConnectionSocketFactoryEx httpConnFac = new PlainConnectionSocketFactoryEx();
        SSLContext sslcontext = SSLContexts.custom().build();
        SSLConnectionSocketFactoryEx sslConnFac = new SSLConnectionSocketFactoryEx(sslcontext);
        if (qhShopProperties.getHttp().getProxy().isEnabled()) {
            SocketAddress address = new InetSocketAddress(
                    qhShopProperties.getHttp().getProxy().getHost(),
                    qhShopProperties.getHttp().getProxy().getPort());
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, address);
            httpConnFac.setProxy(proxy);
            sslConnFac.setProxy(proxy);
        }

        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", httpConnFac)
                .register("https", sslConnFac)
                .build();
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(reg);


        HttpClientBuilder hcb = HttpClientBuilder.create()
                .setConnectionManager(connMgr);
        return hcb.build();
    }

    /**
     * 访问外网的 RestTemplate
     *
     * @return
     */
    @Bean
    @Scope("prototype")
    RestTemplate wwwRestTemplate(
            RestTemplateBuilder restTemplateBuilder,
            HttpClient wwwHttpClient
    ) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(wwwHttpClient));
        return restTemplate;
    }

    @Bean
    @Scope("prototype")
    RestTemplate agencyRestTemplate(
            RestTemplateBuilder restTemplateBuilder
    ) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        List<ClientHttpRequestInterceptor> newInterceptors = new LinkedList<>(restTemplate.getInterceptors());
        newInterceptors.add(new HttpRequestInterceptorService());
        restTemplate.setInterceptors(newInterceptors);
        HttpClient httpClient = HttpClientBuilder.create().build();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        return restTemplate;
    }


    @Bean
    @Scope("prototype")
    RestTemplate oauthRestTemplate(
            RestTemplateBuilder restTemplateBuilder
    ) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        List<ClientHttpRequestInterceptor> newInterceptors = new LinkedList<>(restTemplate.getInterceptors());
        newInterceptors.add(new HttpRequestInterceptorService());
        restTemplate.setInterceptors(newInterceptors);
        HttpClient httpClient = HttpClientBuilder.create().build();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        restTemplate.setErrorHandler(new Oauth2ResponseErrorHandler());
        return restTemplate;
    }

}
