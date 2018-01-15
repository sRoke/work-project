package net.kingsilk.qh.oauth.core.wap.conf;

import net.kingsilk.qh.oauth.core.wap.errorHandler.Oauth2ResponseExceptionTranslator;
import net.kingsilk.qh.oauth.mongo.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.oauth2.config.annotation.configurers.*;
import org.springframework.security.oauth2.config.annotation.web.configuration.*;
import org.springframework.security.oauth2.config.annotation.web.configurers.*;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.approval.*;
import org.springframework.security.oauth2.provider.client.*;
import org.springframework.security.oauth2.provider.code.*;
import org.springframework.security.oauth2.provider.implicit.*;
import org.springframework.security.oauth2.provider.password.*;
import org.springframework.security.oauth2.provider.refresh.*;
import org.springframework.security.oauth2.provider.request.*;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.web.authentication.preauth.*;

import java.util.*;

/**
 *
 * 通过 AuthorizationServerSecurityConfiguration 仅仅过滤以下 URL:
 *      /oauth/token
 *      /oauth/token_key
 *      /oauth/check_token
 *
 * 注意 :  虽然 OAuth2AuthorizationServerConfiguration 可提供默认的配置，但是有以下缺陷：
 *      1. 默认只能从 application.yml 中获取一个 client 身份信息，就意味着只能有一个第三方应用。
 * 故不要使用，自己明确提供 AuthorizationServerConfigurer/AuthorizationServerConfigurerAdapter
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConf {


//    @Bean
//    public MongoClientDetailsService mongoClientDetailsService() {
//        return new MongoClientDetailsService();
//    }

//
//    @Bean
//    public KeyStore jwtJks(
//            QhOAuthProperties props
//    ) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
//
//        Resource rsc = props.getQhOAuth().getWap().getJwt().getJks().getPath();
//        String storePass = props.getQhOAuth().getWap().getJwt().getJks().getStorePass();
//
//        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
//        ks.load(rsc.getInputStream(), storePass.toCharArray());
//        return ks;
//    }
//
//    @Bean
//    public KeyPair jwtKeyPair(
//            QhOAuthProperties props,
//            KeyStore jwtJks
//    ) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
//
//        QhOAuthProperties.QhOAuth.Wap.Jwt.Jks jks = props.getQhOAuth().getWap().getJwt().getJks();
//        String keyAlias = jks.getKeyAlias();
//        String keyPass = jks.getKeyPass();
//        Key key = jwtJks.getKey(keyAlias, keyPass.toCharArray());
//
//        Certificate cert = jwtJks.getCertificate(keyAlias);
//        return new KeyPair(cert.getPublicKey(), (PrivateKey) key);
//    }
//
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter(KeyPair jwtKeyPair) {
//
//        JwtAccessTokenConverter tc = new JwtAccessTokenConverter();
//        tc.setKeyPair(jwtKeyPair);
//
//        // 如果集群模式下部署，则这里必须明确指明签名和验证签名的key。
//        return tc;
//    }
//
//
//    @Bean
//    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
//        // FIXME : JwkTokenStore
//        TokenStore tokenStore = new JwtTokenStore(jwtAccessTokenConverter);
//        return tokenStore;
//    }

//    @Bean
//    public ApprovalStore approvalStore(TokenStore tokenStore) {
//        TokenApprovalStore store = new TokenApprovalStore();
//        store.setTokenStore(tokenStore);
//        return store;
//    }

    @Autowired
    ApplicationContext appCtx;



    @Bean
    public OAuth2RequestFactory oAuth2RequestFactory(
            ClientDetailsService clientDetailsService // MongoClientDetailsService
    ) {
        return new DefaultOAuth2RequestFactory(clientDetailsService);
    }

    @Bean
    public UserApprovalHandler userApprovalHandler(
            ApprovalStore approvalStore,
            OAuth2RequestFactory oAuth2RequestFactory,
            ClientDetailsService clientDetailsService
    ){
        ApprovalStoreUserApprovalHandler handler = new ApprovalStoreUserApprovalHandler();
        handler.setApprovalStore(approvalStore);
        handler.setRequestFactory(oAuth2RequestFactory);
        handler.setClientDetailsService(clientDetailsService);
        return handler;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices(
            ClientDetailsService clientDetailsService,  // MongoClientDetailsService
            TokenStore tokenStore,                      // MongoTokenStore
            UserDetailsService userDetailsService
    ) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setReuseRefreshToken(true);
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setTokenEnhancer(null);

        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(userDetailsService));
        tokenServices.setAuthenticationManager(new ProviderManager(Arrays.asList(provider)));

        return tokenServices;
    }


    @Bean
    public TokenGranter tokenGranter(
            ClientDetailsService clientDetailsService,  // MongoClientDetailsService
            AuthorizationCodeServices authorizationCodeServices,    // MongoAuthorizationCodeServices
            //@Qualifier("tokenServices")
            AuthorizationServerTokenServices tokenServices,
            OAuth2RequestFactory oAuth2RequestFactory,
            AuthenticationManager authenticationManager
    ) {

        // AuthorizationEndpoint#getDefaultTokenGranters
        List<TokenGranter> tokenGranters = new ArrayList<TokenGranter>();
        tokenGranters.add(new AuthorizationCodeTokenGranter(
                tokenServices,
                authorizationCodeServices,
                clientDetailsService,
                oAuth2RequestFactory
        ));
        tokenGranters.add(new RefreshTokenGranter(
                tokenServices,
                clientDetailsService,
                oAuth2RequestFactory
        ));

        ImplicitTokenGranter implicit = new ImplicitTokenGranter(
                tokenServices,
                clientDetailsService,
                oAuth2RequestFactory
        );

        tokenGranters.add(implicit);
        tokenGranters.add(new ClientCredentialsTokenGranter(
                tokenServices,
                clientDetailsService,
                oAuth2RequestFactory
        ));
        if (authenticationManager != null) {
            tokenGranters.add(new ResourceOwnerPasswordTokenGranter(
                    authenticationManager,
                    tokenServices,
                    clientDetailsService,
                    oAuth2RequestFactory
            ));
        }

        return new CompositeTokenGranter(tokenGranters);
    }

    @Bean
    public AuthorizationServerConfigurerAdapter authorizationServerConfigurerAdapter(

            // 注意：ClientDetailsServiceConfiguration 有定义该类型，且是Lazy的。
            // 因此这里必须使用具体类型，否则 StackOverflowError
            MongoClientDetailsService mongoClientDetailsService,    // MongoClientDetailsService

            TokenStore tokenStore,                                  // MongoTokenStore
            AuthorizationCodeServices authorizationCodeServices,    // MongoAuthorizationCodeServices
            OAuth2RequestFactory oAuth2RequestFactory,
            DefaultTokenServices tokenServices,
            TokenGranter tokenGranter,
            AuthenticationManager authenticationManager,
            ApprovalStore approvalStore,
            UserApprovalHandler userApprovalHandler
    ) {
        return new AuthorizationServerConfigurerAdapter() {

            // AuthorizationServerEndpointsConfiguration#authorizationEndpoint() 已经配置好了
            @Override
            public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
                endpoints
                        .tokenStore(tokenStore)
                        .authorizationCodeServices(authorizationCodeServices)
                        .requestFactory(oAuth2RequestFactory)
                        .tokenServices(tokenServices)
                        .tokenGranter(tokenGranter)
                        .approvalStore(approvalStore)
                        //.accessTokenConverter(jwtAccessTokenConverter)
                        .exceptionTranslator(new Oauth2ResponseExceptionTranslator())
                        .userApprovalHandler(userApprovalHandler)
                        .authenticationManager(authenticationManager);  // 启用 ResourceOwnerPasswordTokenGranter
                //.pathMapping("/oauth/confirm_access","/your/controller") // 可以修改映射路径。
            }


            @Override
            public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//
//                String[] authorizedGrantTypes = new String[]{
//                        "authorization_code",
//                        "implicit",
//                        "client_credentials",
//                        "password",
//                        "refresh_token"
//                };

//                String[] authorities = new String[]{"ROLE_CLIENT"};

                // FIXME 应当使用数据库进行存储
                clients.withClientDetails(mongoClientDetailsService);

//                clients.inMemory()
//
//                        .withClient("CLIENT_ID_qh-oauth-wap")
//                        .resourceIds("RSC_ID_qh-common-admin", "RSC_ID_qh-pay-wap")
//                        .secret("CLIENT_PWD_qh-oauth-wap_123456")
//                        .scopes("LOGIN", "SERVER")
//                        .authorizedGrantTypes(authorizedGrantTypes)
//                        //.redirectUris()
//                        .authorities(authorities)
//                        //.accessTokenValiditySeconds()
//                        //.refreshTokenValiditySeconds()
//                        //.additionalInformation()
//                        .autoApprove("LOGIN")
//                        .and()
//
//                        /////////////////qh-agency-admin
//                        .withClient("CLIENT_ID_qh-agency-admin")
//                        .resourceIds("RSC_ID_qh-oauth-wap", "RSC_ID_qh-pay-wap")
//                        .secret("CLIENT_PWD_qh-agency-admin_123456")
//                        .scopes("LOGIN", "SERVER")
//                        .authorizedGrantTypes(authorizedGrantTypes)
//                        .authorities(authorities)
//                        .autoApprove("LOGIN")
//
//                        /////////////////qh-agency-wap
//                        .and()
//                        .withClient("CLIENT_ID_qh-agency-wap")
//                        .resourceIds("RSC_ID_qh-oauth-wap", "RSC_ID_qh-pay-wap")
//                        .secret("CLIENT_PWD_qh-agency-wap_123456")
//                        .scopes("LOGIN", "SERVER")
//                        .authorizedGrantTypes(authorizedGrantTypes)
//                        .authorities(authorities)
//                        .autoApprove("LOGIN")
//
//                        ///////////////qh-pay-wap
//                        .and()
//                        .withClient("CLIENT_ID_qh-pay-wap")
//                        .resourceIds("RSC_ID_qh-oauth-wap", "RSC_ID_qh-common-admin")
//                        .secret("CLIENT_PWD_qh-pay-wap_123456")
//                        .scopes("LOGIN", "SERVER")
//                        .authorizedGrantTypes(authorizedGrantTypes)
//                        .authorities(authorities)
//                        .autoApprove("LOGIN")
//
//                        //////////////////////qh-agency-admin-front
//                        .and()
//                        .withClient("CLIENT_ID_qh-agency-admin-front")
//                        .resourceIds("RSC_ID_qh-oauth-wap", "RSC_ID_qh-agency-admin")
//                        .secret("CLIENT_PWD_qh-agency-admin-front_123456")
//                        .scopes("LOGIN")
//                        .authorizedGrantTypes(authorizedGrantTypes)
//                        .authorities(authorities)
//                        .autoApprove("LOGIN")
//
//                        /////////////////////qh-agency-wap-front
//                        .and()
//                        .withClient("CLIENT_ID_qh-agency-wap-front")
//                        .resourceIds("RSC_ID_qh-oauth-wap", "RSC_ID_qh-agency-wap")
//                        .secret("CLIENT_PWD_qh-agency-wap-front_123456")
//                        .scopes("LOGIN")
//                        .authorizedGrantTypes(authorizedGrantTypes)
//                        .authorities(authorities)
//                        .autoApprove("LOGIN")
//
//                        //////////////////////kingsilk-qh-wap
//                        .and()
//                        .withClient("CLIENT_ID_kingsilk_qh-wap")
//                        .resourceIds("RSC_ID_qh-common-admin", "RSC_ID_qh-oauth-wap")
//                        .secret("CLIENT_PWD_kingsilk_qh-wap_123456")
//                        .scopes("LOGIN", "SERVER")
//                        .authorizedGrantTypes(authorizedGrantTypes)
//                        .authorities(authorities)
//                        .autoApprove("LOGIN")
//
//                        //////////////////////kingsilk-qh-admin
//                        .and()
//                        .withClient("CLIENT_ID_kingsilk_qh-admin")
//                        .resourceIds("RSC_ID_qh-common-admin", "RSC_ID_qh-oauth-wap")
//                        .secret("CLIENT_PWD_kingsilk_qh-admin_123456")
//                        .scopes("LOGIN", "SERVER")
//                        .authorizedGrantTypes(authorizedGrantTypes)
//                        .authorities(authorities)
//                        .autoApprove("LOGIN")
//
//                        //////////////////////kingsilk-qh-lottery-wap-front
//                        .and()
//                        .withClient("CLIENT_ID_kingsilk_qh-lottery-wap-front")
//                        .resourceIds("RSC_ID_qh-oauth-wap")
//                        .secret("CLIENT_PWD_kingsilk_qh-lottery-wap-front_123456")
//                        .scopes("LOGIN")
//                        .authorizedGrantTypes(authorizedGrantTypes)
//                        .authorities(authorities)
//                        .autoApprove("LOGIN");


            }


            @Override
            public void configure(AuthorizationServerSecurityConfigurer oauthServer) {

                // 通过 application.yml : "security.oauth2.authorization.realm"
                oauthServer.realm("qh-oauth")
                        .tokenKeyAccess("isFullyAuthenticated()")
                        .checkTokenAccess("isFullyAuthenticated()");
            }
        };

    }

}















