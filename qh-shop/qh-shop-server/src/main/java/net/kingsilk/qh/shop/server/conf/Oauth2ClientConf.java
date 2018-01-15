package net.kingsilk.qh.shop.server.conf;

import net.kingsilk.qh.shop.service.QhShopProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Oauth2 客户端配置,目前只用于连接qh-pay-wap
 */
@Configuration
@EnableOAuth2Client
public class Oauth2ClientConf {

    //////////////accessTokenProvider
    @Bean
    public AccessTokenProvider accessTokenProvider(RestTemplate wwwRestTemplate) {
        return new AccessTokenProviderChain(Arrays.asList(
                new ClientCredentialsAccessTokenProvider() {
                    @Override
                    protected RestOperations getRestTemplate() {
                        RestTemplate restTemplate = wwwRestTemplate;
                        restTemplate.setErrorHandler(getResponseErrorHandler());
                        setMessageConverters(restTemplate.getMessageConverters());
                        return restTemplate;
                    }
                }
        ));
    }

    ///////////////////qh-pay-wap
    @Bean
    public OAuth2ProtectedResourceDetails o2QhPayRD(
            QhShopProperties props
    ) {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setId(props.getQhPay().getWap().getResourceId());          // 要请求资源的ID
        details.setClientId(props.getQhAgency().getServer().getClientId());
        details.setClientSecret(props.getQhAgency().getServer().getClientSecret());
        details.setAccessTokenUri(props.getQhOAuth().getWap().getAccessTokenUri());
        details.setScope(props.getQhAgency().getServer().getScopes());
        //details.setGrantType("")
        return details;
    }


    ///////////////////qh-agency-api/qh-agency-server
    @Bean
    public OAuth2ProtectedResourceDetails o2QhAgencyRD(
            QhShopProperties props
    ) {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setId(props.getQhAgency().getServer().getResourceId());          // 要请求资源的ID
        details.setClientId(props.getQhAgency().getServer().getClientId());
        details.setClientSecret(props.getQhAgency().getServer().getClientSecret());
        details.setAccessTokenUri(props.getQhOAuth().getWap().getAccessTokenUri());
        details.setScope(props.getQhAgency().getServer().getScopes());
        //details.setGrantType("")
        return details;
    }

    @Bean
    public OAuth2RestTemplate o2QhPayRT(
            OAuth2ClientContext clientContext,
            AccessTokenProvider accessTokenProvider,
            @Qualifier("o2QhPayRD")
                    OAuth2ProtectedResourceDetails oClientPayWapResourceDetails,
            ClientHttpRequestFactory clientHttpRequestFactory
    ) {
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(oClientPayWapResourceDetails, clientContext);
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        restTemplate.setAccessTokenProvider(accessTokenProvider);
        return restTemplate;
    }


    @Bean
    public OAuth2RestTemplate o2QhAgencyRT(
            OAuth2ClientContext clientContext,
            AccessTokenProvider accessTokenProvider,
            @Qualifier("o2QhAgencyRD")
                    OAuth2ProtectedResourceDetails oClientPayWapResourceDetails,
            ClientHttpRequestFactory clientHttpRequestFactory
    ) {
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(oClientPayWapResourceDetails, clientContext);
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        restTemplate.setAccessTokenProvider(accessTokenProvider);
        return restTemplate;
    }

}
