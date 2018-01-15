package net.kingsilk.qh.shop.server.conf;

import net.kingsilk.qh.shop.repo.ShopStaffRepo;
import net.kingsilk.qh.shop.repo.StaffRepo;
import net.kingsilk.qh.shop.service.service.AuthorityService;
import net.kingsilk.qh.shop.service.service.StaffUserDetailsService;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;

/**
 * 对 Spring Security 进行配置
 * <p>
 * 原则上，该安全配置应当不会处理任何请求。
 */
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
class WebSecurityConf {

    @Bean
    AuthenticationTrustResolver authenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl();
    }

    @Bean
    StaffUserDetailsService staffUserDetailsService(
            StaffRepo staffRepo,
            ShopStaffRepo shopStaffRepo,
            AuthorityService authorityService
    ) {

        return new StaffUserDetailsService(
                staffRepo,
                shopStaffRepo,
                authorityService
        );

    }

//    @Bean
//    UserDetailsService staffUserDetailsService() {
//        InMemoryUserDetailsManager m = new InMemoryUserDetailsManager();
//
//        UserDetails userDetails = User.withUsername("a_admin")
//                .password("a_admin")
//                .authorities("ADMIN", "USER")
//                .build()
//        m.createUser(userDetails);
//
//        userDetails = User.withUsername("a_user")
//                .password("a_user")
//                .authorities("USER")
//                .build()
//        m.createUser(userDetails);
//
//        return m
//    }

    @Bean
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    WebSecurityConfigurerAdapter webSecurityConfigurerAdapter(
            CorsConfigurationSource corsConfigurationSource
    ) {

        return new WebSecurityConfigurerAdapter() {

            @Override
            public void configure(AuthenticationManagerBuilder auth) {
                //auth.inMemoryAuthentication()
                //        .withUser("a_admin")
                //        .password("a_admin")
                //        .authorities("AAA")
                //        .roles("ADMIN", "USER")
            }

            @Override
            public void configure(WebSecurity web) throws Exception {
                // empty
            }


            @Override
            protected void configure(HttpSecurity http) throws Exception {

                // 只对以下路径规则应用该安全设置。
                http.requestMatchers()
                        .antMatchers("/**");

                http.authorizeRequests()
                        .anyRequest()
                        .permitAll()
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll();

                http.csrf() // 仅仅测试用
                        .disable();


//                http.cors().configurationSource(corsConfigurationSource);
                http.cors();
            }

        };
    }

}