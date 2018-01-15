package net.kingsilk.qh.oauth.core.wap.conf;

import net.kingsilk.qh.oauth.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.http.client.*;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.resource.*;
import org.springframework.security.oauth2.client.token.*;
import org.springframework.security.oauth2.client.token.grant.client.*;
import org.springframework.security.oauth2.config.annotation.web.configuration.*;
import org.springframework.web.client.*;

import java.util.*;

/**
 * Oauth2 客户端配置,目前只用于连接 qh-common
 */
@Configuration
@EnableOAuth2Client
public class Oauth2ClientConf {

    ///////////////////client
    @Bean
    OAuth2ProtectedResourceDetails qhCommonAdminCRD(
            QhOAuthProperties props
    ) {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setId(props.getQhCommon().getAdmin().getResourceId()); // 要请求资源的ID
        details.setClientId(props.getQhOAuth().getWap().getClientId());
        details.setClientSecret(props.getQhOAuth().getWap().getClientSecret());
        details.setAccessTokenUri(props.getQhOAuth().getWap().getAccessTokenUrl());
        details.setScope(props.getQhOAuth().getWap().getScopes());
        //details.setGrantType("")
        return details;
    }

    @Bean
    AccessTokenProvider accessTokenProvider(RestTemplate restTemplate) {
        return new AccessTokenProviderChain(Arrays.asList(
                new ClientCredentialsAccessTokenProvider() {
                    @Override
                    protected RestOperations getRestTemplate() {
//                        RestTemplate restTemplate = wwwRestTemplate;
                        restTemplate.setErrorHandler(getResponseErrorHandler());
                        setMessageConverters(restTemplate.getMessageConverters());
                        return restTemplate;
                    }
                }
        ));
    }

    @Bean
    OAuth2RestTemplate qhCommonAdminCRT(
            OAuth2ClientContext clientContext,
            AccessTokenProvider accessTokenProvider,
            @Qualifier("qhCommonAdminCRD")
                    OAuth2ProtectedResourceDetails qhCommonAdminCRD,
            ClientHttpRequestFactory clientHttpRequestFactory
    ) {
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(qhCommonAdminCRD, clientContext);
        restTemplate.setAccessTokenProvider(accessTokenProvider);
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        return restTemplate;
    }
}
