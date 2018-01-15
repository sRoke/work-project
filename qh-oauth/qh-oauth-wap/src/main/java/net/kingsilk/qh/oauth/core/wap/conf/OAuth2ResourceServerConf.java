package net.kingsilk.qh.oauth.core.wap.conf;

import org.springframework.boot.autoconfigure.security.*;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.oauth2.config.annotation.web.configuration.*;
import org.springframework.security.oauth2.config.annotation.web.configurers.*;
import org.springframework.security.web.savedrequest.*;
import org.springframework.security.web.util.matcher.*;

/**
 *
 */

@Configuration
@EnableResourceServer // -> ResourceServerConfiguration
public class OAuth2ResourceServerConf {


//    @Bean
//    RemoteTokenServices remoteTokenServices(RestTemplateBuilder restTemplateBuilder) {
//        RemoteTokenServices ts = new RemoteTokenServices()
//        // 去检查token的URL
//        ts.setCheckTokenEndpointUrl("http://a.localhost:10001/oauth/check_token");
//        // 去检查token时，自己的身份信息
//        ts.setClientId("CLIENT_ID_rsc_server");
//        ts.setClientSecret("CLIENT_PWD_rsc_server");
//        // 内部默认自己新建的，为了方便跟踪调试，统一使用自己配置的。
//        ts.setRestTemplate(restTemplateBuilder.build())
//        return ts
//    }

//    public static final int OAUTH2_RSC_FILTER_CHAIN_ORDER = SecurityProperties.ACCESS_OVERRIDE_ORDER - 10 as int;

    @Bean
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    public ResourceServerConfigurerAdapter resourceServerConfigurerAdapter(
            //RemoteTokenServices remoteTokenServices
    ) {
        return new ResourceServerConfigurerAdapter() {

            // 先调用 @ ResourceServerConfiguration.configure(HttpSecurity)
            @Override
            public void configure(ResourceServerSecurityConfigurer resources) {

                // 通过 application.yml "security.oauth2.resource.id" 配置
                resources.resourceId("RSC_ID_qh-oauth-server")
                        //.tokenServices(remoteTokenServices) //RemoteTokenServices
                        .stateless(true);
            }

            // 后调用 @ ResourceServerConfiguration.configure(HttpSecurity)
            @Override
            public void configure(HttpSecurity http) throws Exception {

                http.requestCache()
                        .requestCache(new NullRequestCache());

                http.requestMatchers()
                        .requestMatchers(
                                new AndRequestMatcher(
                                        new AntPathRequestMatcher("/api/**"),
                                        new NegatedRequestMatcher(new AntPathRequestMatcher("/api/s/**"))
                                )
                        );
                        //.regexMatchers("/api/(^)")
                        //.antMatchers("/api/**");

                // 权限的配置建议统一使用 @PreAuthorize 等注解来处理。

                // FIXME : 必须调用该语句，否则 IllegalStateException @ ResourceServerSecurityConfigurer.configure() -> http.authorizeRequests()
                http.authorizeRequests()
                        .anyRequest()
                        .permitAll();

                // 明确指明不在这里配置CORS，统一在 WebSecurityConf 中配置。
                http.cors()
                        .disable();

            }
        };
    }
    // ResourceServerTokenServicesConfiguration 有自动配置
//
//    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
//    static final class MyResourceServerConfigurerAdapter(){
//
//    }

}















