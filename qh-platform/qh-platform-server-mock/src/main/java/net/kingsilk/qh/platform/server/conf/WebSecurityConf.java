package net.kingsilk.qh.platform.server.conf;

import org.springframework.boot.autoconfigure.security.*;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.*;

/**
 * 对 Spring Security 进行配置
 */
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
class WebSecurityConf {

//    @Bean
//    MemberUserDetailsService memberUserDetailsService(
//            MemberRepo memberRepo,
//            CompanyRepo companyRepo
//    ) {
//        return new MemberUserDetailsService(
//                memberRepo: memberRepo,
//                companyRepo: companyRepo,
//        )
//    }

    @Bean
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
        return new WebSecurityConfigurerAdapter() {

//            @Override
//            void configure(AuthenticationManagerBuilder auth) {
//
//            }
//
//            @Override
//            void configure(WebSecurity web) throws Exception {
//                // empty
//            }

            @Override
            protected void configure(HttpSecurity http) throws Exception {

                // 只对以下路径规则应用该安全设置。
                http.requestMatchers()
                        .antMatchers("/**");

                http.sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                // 默认对所有URL都开放权限
                http.authorizeRequests()
                        .anyRequest()
                        .permitAll();

                // 对所有的路径均不使用 CSRF token （因为 stateless）
                http.csrf()
                        .disable();

                // 统一使用 spring-webmvc 中相关的cors配置，不使用 spring-security 的。
                //http.cors()
            }
        };
    }

}