package net.kingsilk.qh.shop.server.conf;

import groovy.transform.CompileStatic;
import net.kingsilk.qh.shop.service.QhShopProperties;
import net.kingsilk.qh.shop.service.security.BrandAppIdFilter;
import net.kingsilk.qh.shop.service.security.ShopExpireFilter;
import net.kingsilk.qh.shop.service.service.StaffUserDetailsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter;
import org.springframework.web.client.RestOperations;

/**
 * FIXME : @EnableResourceServer -> ResourceServerConfiguration 已经提供了一些配置，能否重用？
 */
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@Configuration
@EnableResourceServer
@CompileStatic
public class OAuth2ResourceServerConf {

    @Bean
    public UserAuthenticationConverter userAuthenticationConverter(
            StaffUserDetailsService staffUserDetailsService
    ) {

        DefaultUserAuthenticationConverter uac = new DefaultUserAuthenticationConverter();
        uac.setUserDetailsService(staffUserDetailsService);
        return uac;
    }

    @Bean
    public DefaultAccessTokenConverter defaultAccessTokenConverter(
            UserAuthenticationConverter userAuthenticationConverter
    ) {
        DefaultAccessTokenConverter tc = new DefaultAccessTokenConverter();
        tc.setUserTokenConverter(userAuthenticationConverter);
        return tc;
    }

//    @Bean
//    public QhShopProperties props(){
//        return new QhShopProperties();
//    }

//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter(
//             QhShopProperties props,
//            DefaultAccessTokenConverter defaultAccessTokenConverter
//    ) {
//        JwtAccessTokenConverter tc = new JwtAccessTokenConverter();
//        tc.setAccessTokenConverter(defaultAccessTokenConverter);
//        tc.setVerifierKey(props.getJwt().getVerifierKey());
//        return tc;
//    }
//
//    @Bean
//    public JwtTokenStore jwtTokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
//        return new JwtTokenStore(jwtAccessTokenConverter);
//    }

//    // ResourceServerTokenServicesConfiguration 有自动配置
//    @Bean
//    ResourceServerTokenServices remoteTokenServices(RestTemplateBuilder restTemplateBuilder) {
//        RemoteTokenServices ts = new RemoteTokenServices()
//
//        ts.setCheckTokenEndpointUrl("http://localhost:10040/oauth/check_token");
//        ts.setClientId("CLIENT_ID_qh-net.kingsilk.qh.agency-net.kingsilk.qh.net.kingsilk.qh.agency.admin");
//        ts.setClientSecret("CLIENT_PWD_qh-agency-admin_123456");
//        // 内部默认自己新建的，为了方便跟踪调试，统一使用自己配置的。
//        ts.setRestTemplate(restTemplateBuilder.build())
//        return ts
//    }


//    @Bean
//    public ResourceServerTokenServices resourceServerTokenServices(
//            @Qualifier("jwtTokenStore") TokenStore tokenStore) {
//        DefaultTokenServices ts = new DefaultTokenServices();
//        ts.setTokenStore(tokenStore);
//        return ts;
//    }

    @Bean
    ResourceServerTokenServices resourceServerTokenServices(
            QhShopProperties props,
            AccessTokenConverter defaultAccessTokenConverter,
            @Qualifier("oauthRestTemplate")
                    RestOperations restTemplate

    ) {
        RemoteTokenServices ts = new RemoteTokenServices();
        ts.setAccessTokenConverter(defaultAccessTokenConverter);
        ts.setCheckTokenEndpointUrl(props.getQhOAuth().getWap().getCheckTokenUri());
        ts.setClientId(props.getQhShop().getClientId());
        ts.setClientSecret(props.getQhShop().getClientSecret());
        ts.setRestTemplate(restTemplate);
        return ts;
    }

    @Bean
    public BrandAppIdFilter brandAppIdFilter() {

        return new BrandAppIdFilter();
    }

    @Bean
    public ShopExpireFilter shopExpireFilter() {

        return new ShopExpireFilter();
    }

    @Bean
    public ResourceServerConfigurerAdapter resourceServerConfigurerAdapter(
            ResourceServerTokenServices resourceServerTokenServices,
            BrandAppIdFilter brandAppIdFilter,
            ShopExpireFilter shopExpireFilter
    ) {
        return new ResourceServerConfigurerAdapter() {
            // 先调用 @ ResourceServerConfiguration.configure(HttpSecurity)
            @Override
            public void configure(ResourceServerSecurityConfigurer resources) {

                // 通过 application.yml "security.oauth2.resource.id" 配置
                resources.resourceId("RSC_ID_qh-shop-server")
                        .tokenServices(resourceServerTokenServices)
                        .stateless(true);
            }

            // 后调用 @ ResourceServerConfiguration.configure(HttpSecurity)
            @Override
            public void configure(HttpSecurity http) throws Exception {

                http.requestMatchers()
                        .antMatchers("/api/**");

                http.sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                // 权限的配置建议统一使用 @PreAuthorize 等注解来处理。
                // FIXME : 必须调用该语句，否则 IllegalStateException @ ResourceServerSecurityConfigurer.configure() -> http.authorizeRequests()
                http.authorizeRequests()
                        .anyRequest()
                        .permitAll();

                http.csrf()
                        .disable();
                // 处理 "Company-Id" http 请求头
                http.addFilterBefore(brandAppIdFilter, X509AuthenticationFilter.class);
                http.addFilterAfter(shopExpireFilter, BrandAppIdFilter.class);

            }

        };
    }


}















