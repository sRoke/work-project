package net.kingsilk.qh.agency.server.conf

import groovy.transform.CompileStatic
import net.kingsilk.qh.agency.QhAgencyProperties
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails
import org.springframework.security.oauth2.client.token.AccessTokenProvider
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.web.client.RestOperations
import org.springframework.web.client.RestTemplate

/**
 * Oauth2 客户端配置,目前只用于连接qh-pay-wap
 */
@Configuration
@EnableOAuth2Client
@CompileStatic
class Oauth2ClientConf {

    //////////////accessTokenProvider
    @Bean
    AccessTokenProvider accessTokenProvider(RestTemplate wwwRestTemplate) {
        return new AccessTokenProviderChain(Arrays.asList(
                new ClientCredentialsAccessTokenProvider() {
                    @Override
                    protected RestOperations getRestTemplate() {
                        RestTemplate restTemplate = wwwRestTemplate
                        restTemplate.setErrorHandler(getResponseErrorHandler())
                        setMessageConverters(restTemplate.getMessageConverters());
                        return restTemplate
                    }
                }
        ));
    }

    ///////////////////qh-pay-wap
    @Bean
    public OAuth2ProtectedResourceDetails oClientPayWapResourceDetails(
            QhAgencyProperties props
    ) {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setId(props.qhPay.wap.resourceId);          // 要请求资源的ID
        details.setClientId(props.qhAgency.server.clientId);
        details.setClientSecret(props.qhAgency.server.clientSecret);
        details.setAccessTokenUri(props.qhOAuth.wap.accessTokenUri);
        details.setScope(props.qhAgency.server.scopes);
        //details.setGrantType("")
        return details;
    }

    @Bean
    public OAuth2RestTemplate oClientPayWapRestTemplate(
            OAuth2ClientContext clientContext,
            AccessTokenProvider accessTokenProvider,
            @Qualifier("oClientPayWapResourceDetails") OAuth2ProtectedResourceDetails oClientPayWapResourceDetails,
            ClientHttpRequestFactory clientHttpRequestFactory
    ) {
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(oClientPayWapResourceDetails, clientContext);
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        restTemplate.setAccessTokenProvider(accessTokenProvider)
        return restTemplate;
    }

    @Bean
    public OAuth2ProtectedResourceDetails oClientOauthWapResourceDetails(
            QhAgencyProperties props
    ) {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setId(props.qhPay.wap.resourceId);          // 要请求资源的ID
        details.setClientId(props.qhAgency.server.clientId);
        details.setClientSecret(props.qhAgency.server.clientSecret);
        details.setAccessTokenUri(props.qhOAuth.wap.accessTokenUri);
        details.setScope(props.qhAgency.server.scopes);
        //details.setGrantType("")
        return details;
    }

    @Bean
    public OAuth2RestTemplate oClientOauthWapRestTemplate(
            OAuth2ClientContext clientContext,
            AccessTokenProvider accessTokenProvider,
            @Qualifier("oClientOauthWapResourceDetails") OAuth2ProtectedResourceDetails oClientOauthWapResourceDetails,
            ClientHttpRequestFactory clientHttpRequestFactory
    ) {
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(oClientOauthWapResourceDetails, clientContext);
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        restTemplate.setAccessTokenProvider(accessTokenProvider)
        return restTemplate;
    }

}
