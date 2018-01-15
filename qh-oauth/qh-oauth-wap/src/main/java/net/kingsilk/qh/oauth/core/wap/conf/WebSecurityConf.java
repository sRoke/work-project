package net.kingsilk.qh.oauth.core.wap.conf;

import net.kingsilk.qh.oauth.security.login.phone.*;
import net.kingsilk.qh.oauth.security.login.wxComMp.*;
import net.kingsilk.qh.oauth.security.login.wxMp.*;
import org.springframework.boot.autoconfigure.security.*;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.authentication.configurers.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.savedrequest.*;
import org.springframework.security.web.util.matcher.*;

/**
 * 对 Spring Security 进行配置
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConf {

    @Bean
    public AuthenticationTrustResolver authenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl();
    }

//    @Bean
//    public QhOAuthAuthEntryPoint authEntryPoint(
//            QhOAuthProperties props
//    ) {
//        QhOAuthAuthEntryPoint authEntryPoint = new QhOAuthAuthEntryPoint(props);
//        return authEntryPoint;
//    }

//    @Bean
//    public DelegatingAuthenticationEntryPoint authEntryPoint() {
//
//
////        // FIXME:
////        RequestMatcher http403ReqMatcher = new AndRequestMatcher(Arrays.asList(
////                new AntPathRequestMatcher("/**"),
////                new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest")
////                //new ELRequestMatcher("hasHeader('X-Requested-With', 'XMLHttpRequest')")
////        ));
////        Http403ForbiddenEntryPoint http403AEP = new Http403ForbiddenEntryPoint();
////
////
//        LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> entryPoints = new LinkedHashMap<>();
////        entryPoints.put(http403ReqMatcher, http403AEP);
//
//
//        DelegatingAuthenticationEntryPoint aep = new DelegatingAuthenticationEntryPoint(entryPoints);
//        aep.setDefaultEntryPoint();
//        return aep;
//    }

//    @Bean
//    public WxMpAuthenticationEntryPoint wxMpAuthenticationEntryPoint() {
//        return new WxMpAuthenticationEntryPoint();
//    }
//
//    @Bean
//    public PhoneCodeAuthenticationEntryPoint phoneCodeAuthenticationEntryPoint() {
//        return new PhoneCodeAuthenticationEntryPoint();
//    }

//    @Bean
//    public WxMpScanAuthenticationEntryPoint wxMpScanAuthenticationEntryPoint() {
//        return new WxMpScanAuthenticationEntryPoint();
//    }

//    @Bean
//    public OAuthUserDetailsService oauthUserDetailsService() {
//        return new OAuthUserDetailsService();
//    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(7);
    }

    @Bean
    // see AuthenticationManagerConfiguration
    public GlobalAuthenticationConfigurerAdapter globalAuthenticationConfigurerAdapter(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            WxMpAuthenticationProvider wxMpAuthenticationProvider,
            WxComMpAuthenticationProvider wxComMpAuthenticationProvider,
            PhoneCodeAuthenticationProvider phoneCodeAuthenticationProvider
    ) {
        return new GlobalAuthenticationConfigurerAdapter() {

            @Override
            public void init(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userDetailsService)
                        .passwordEncoder(passwordEncoder);

                auth.authenticationProvider(wxMpAuthenticationProvider);
                auth.authenticationProvider(wxComMpAuthenticationProvider);
                auth.authenticationProvider(phoneCodeAuthenticationProvider);
            }

            @Override
            public void configure(AuthenticationManagerBuilder auth) throws Exception {

            }
        };
    }


    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter(
//            QhOAuthAuthEntryPoint authEntryPoint,
//            WxLoginFilter wxLoginFilter,
//            PhoneLoginFilter phoneLoginFilter,

            CustomLogoutSuccessHandler customLogoutSuccessHandler

//            WxMpAuthenticationEntryPoint wxMpAuthenticationEntryPoint,
//            PhoneCodeAuthenticationEntryPoint phoneCodeAuthenticationEntryPoint,
//            WxMpScanAuthenticationEntryPoint wxMpScanAuthenticationEntryPoint

    ) {

        return new WebSecurityConfigurerAdapter() {

            @Override
            public void configure(WebSecurity web) throws Exception {

            }

            @Override
            protected void configure(HttpSecurity http) throws Exception {

                http.requestCache()
                        .requestCache(new NullRequestCache());


                // 因为 AuthenticationEntryPoint 有先后顺序，最特殊的，放到最前面配置
                http.exceptionHandling()
                        .defaultAuthenticationEntryPointFor(
                                new Http401AuthenticationEntryPoint("qh-oauth"),
                                new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest"));
                //.defaultAuthenticationEntryPointFor(phoneCodeAuthenticationEntryPoint, new LoginTypeRequestMatcher(LoginTypeEnum.PHONE))
                //.defaultAuthenticationEntryPointFor(wxMpAuthenticationEntryPoint, new LoginTypeRequestMatcher(LoginTypeEnum.WX))
                //.defaultAuthenticationEntryPointFor(wxMpScanAuthenticationEntryPoint, new LoginTypeRequestMatcher(LoginTypeEnum.WX_SCAN));

                // 只对以下路径规则应用该安全设置。
                http.requestMatchers()
                        .antMatchers("/**");
                //.antMatchers();  // 虽然匹配所有路径，但是优先级最低。
//                        .antMatchers("/login")
//                        .antMatchers("/sec")
//                        .antMatchers("/oauth/authorize")
//                        .antMatchers("/oauth/confirm_access")
//                        .antMatchers("/logoutSuccess")
//                        .antMatchers("/logout")

                http.formLogin()
                        .loginPage("/login")
                        .permitAll();

                //http.addFilterBefore(wxLoginFilter, UsernamePasswordAuthenticationFilter.class);
                //http.addFilterBefore(phoneLoginFilter, UsernamePasswordAuthenticationFilter.class);

                http.formLogin()
                        .loginPage("/phoneLogin")
                        .permitAll();

                http.logout()
                        //.logoutUrl("/logout")
                        //.logoutSuccessUrl("/logoutSuccess") // /login?logout
//                        .logoutSuccessHandler(null) // TODO
                        .logoutSuccessHandler(customLogoutSuccessHandler)
                        .permitAll();

                http.authorizeRequests()
                        .antMatchers("/")
                        .permitAll();

//                http.authorizeRequests()
//                        .antMatchers("/oauth/**")
//                        .permitAll()

                /*.anyRequest()
                .authenticated()*/

                http.csrf()
                        .disable();

            }
        };
    }


}