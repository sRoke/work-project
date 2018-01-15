package net.kingsilk.qh.vote.server.conf;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConf {

    @Bean
    AuthenticationTrustResolver authenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(6);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
//        configuration.setExposedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    WebSecurityConfigurerAdapter webSecurityConfigurerAdapter(
            CorsConfigurationSource corsConfigurationSource
    ) {
        return new WebSecurityConfigurerAdapter() {

            @Override
            public void configure(AuthenticationManagerBuilder auth) {

            }

            @Override
            public void configure(WebSecurity web) throws Exception {
                // empty
            }


            @Override
            protected void configure(HttpSecurity http) throws Exception {

                // 只对以下路径规则应用该安全设置。
                http
                        .authorizeRequests()
                        // 允许对于网站静态资源的无授权访问
                        .antMatchers(
                                HttpMethod.GET,
                                "/",
                                "/*.html",
                                "/favicon.ico",
                                "/**/*.html",
                                "/**/*.css",
                                "/**/*.js"
                        ).permitAll()
                        // 对于获取token的rest api要允许匿名访问
                        .antMatchers("/auth/**").permitAll()
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        // 除上面外的所有请求全部需要鉴权认证
                        .anyRequest().authenticated();


//                http.sessionManagement()
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                // 默认对所有URL都开放权限
//                http.authorizeRequests()
//                        .anyRequest()
//                        .permitAll();

                // 对所有的路径均不使用 CSRF token （因为 stateless）
                http.csrf().disable();

//                // 统一使用 spring-webmvc 中相关的cors配置，不使用 spring-security 的。
                http.cors().configurationSource(corsConfigurationSource);

            }
        };
    }
}
