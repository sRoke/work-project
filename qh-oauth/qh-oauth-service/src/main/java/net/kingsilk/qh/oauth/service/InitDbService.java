package net.kingsilk.qh.oauth.service;

import net.kingsilk.qh.oauth.core.OrgStatusEnum;
import net.kingsilk.qh.oauth.domain.Org;
import net.kingsilk.qh.oauth.domain.User;
import net.kingsilk.qh.oauth.repo.OAuthClientDetailsRepo;
import net.kingsilk.qh.oauth.repo.OrgRepo;
import net.kingsilk.qh.oauth.repo.UserRepo;
import net.kingsilk.qh.oauth.security.SecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

/**
 * 因为管理后台不完善，先再应用一启动的时候就导入一部分 "静态" 数据。
 */
@Service
public class InitDbService {

    private Logger log = LoggerFactory.getLogger(getClass());

    public static final String org_id_qh = "5982c8c3add7b434a6f42f80";
    public static final String user_id_superAdmin = "58de6b27785a82000005a140";
    public static final String user_id_httAdmin = "58e4bd0c785a82000005a141";
    public static final String user_id_zll = "58e5dfb42a9f2d0000313b1d";

    private static final String[] authorizedGrantTypes = new String[]{
            "authorization_code",
            "implicit",
            "client_credentials",
            "password",
            "refresh_token"
    };

    // 一天
    private static final int defaultAccessTokenValiditySeconds = 1 * 24 * 60 * 60;

    // 一个月
    private static final int defaultRefreshTokenValiditySeconds = 30 * 24 * 60 * 60;


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrgRepo orgRepo;

    @Autowired
    private OAuthClientDetailsRepo oauthClientDetailsRepo;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private ClientRegistrationService clientRegistrationService;


    @Autowired
    private SecService secUtils;

    @EventListener
    public void onApplicationStated(ContextRefreshedEvent event) {

        checkAndAddSupperAdmin();
        checkAndAddHttAdmin();
        //checkAndAddUserZll();

        addClients();

    }

    private void addClients() {

        // qh-oauth-*
        checkAndAddQhOAuthServer();
        checkAndAddQhOAuthWapFront();
        checkAndAddOrgQh();

        // qh-agency-*
        checkAndAddQhAgencyServer();
        checkAndAddQhAgencyWapFront();
        checkAndAddQhAgencyAdminFront();

        // qh-platform-*
        checkAndAddQhPlatformServer();
        checkAndAddQhPlatformAdminFront();

        // wx4j-*
        checkAndAddWx4jBrokerServer();
    }

    /**
     * 超级管理员
     */
    private void checkAndAddSupperAdmin() {

        if (userRepo.findOne(user_id_superAdmin) != null) {
            return;
        }

        User user = new User();
        user.setId(user_id_superAdmin);
        user.setEnabled(true);
        user.setAccountLocked(false);
        user.setAccountExpired(false);
        user.setInviterUserId(null);
        user.setUsername("qhAdmin");
        user.setPassword(secUtils.encodePassword("qhAdmin"));

        userRepo.save(user);
    }

    /**
     * 郝太太管理员
     */
    private void checkAndAddHttAdmin() {

        if (userRepo.findOne(user_id_httAdmin) != null) {
            return;
        }

        User user = new User();
        user.setId(user_id_httAdmin);
        user.setEnabled(true);
        user.setAccountLocked(false);
        user.setAccountExpired(false);
        user.setInviterUserId(null);
        user.setUsername("httAdmin");
        user.setPassword(secUtils.encodePassword("httAdmin"));

        // 绑定手机号码
        user.setPhone("18069855776");
        user.setPhoneVerifiedAt(new Date());

        userRepo.save(user);
    }

    /**
     * 般若
     */
    private void checkAndAddUserZll() {

        if (userRepo.findOne(user_id_zll) != null) {
            return;
        }

        User user = new User();
        user.setId(user_id_zll);
        user.setEnabled(true);
        user.setAccountLocked(false);
        user.setAccountExpired(false);
        user.setInviterUserId(null);

        // 绑定用户名密码
        user.setUsername("btpka3");
        user.setPassword(secUtils.encodePassword("btpka3"));

        // 绑定微信信息
        //user.setOpenId("oETxKwnXo4pPaK1S-AWyywQCCsJ4");


        // 绑定手机号码
        user.setPhone("17091602013");
        user.setPhoneVerifiedAt(new Date());

        userRepo.save(user);
    }

    /**
     * 初始化钱皇这个组织
     */
    private void checkAndAddOrgQh() {
        if (orgRepo.findOne(org_id_qh) != null) {
            return;
        }
        Org org = new Org();
        org.setName("钱皇");
        org.setUserId(user_id_superAdmin);
        org.setStatus(OrgStatusEnum.ENABLED);
        org.setId("59782691f8fdbc1f9b2c4323");
        orgRepo.save(org);
    }


    /**
     * 注册 OAuth client : qh-oauth-server
     */
    private void checkAndAddQhOAuthServer() {

        // "code"       - authorize code 模式
        // "token"      - implicit
        // "password"   - 密码模式
        // ""

        String clientId = "CLIENT_ID_qh-oauth-server";


        try {
            ClientDetails cd = clientDetailsService.loadClientByClientId(clientId);
            log.debug("======= ClientDetails = " + cd);
            return;
        } catch (NoSuchClientException e) {
            log.debug("---------- 数据库中不存在" + clientId, e);
            // DO NOTHING
        }

        BaseClientDetails cd = new BaseClientDetails();
        cd.setClientId(clientId);
        cd.setClientSecret("CLIENT_PWD_qh-oauth-server_123456");
        cd.setScope(new HashSet<>(Arrays.asList("LOGIN")));
        cd.setResourceIds(Arrays.asList("RSC_ID_qh-oauth-wap", "RSC_ID_qh-pay-wap"));
        cd.setAuthorizedGrantTypes(Arrays.asList(
                "authorization_code",
                //"implicit",
                "client_credentials",
                //"password",
                "refresh_token"
        ));
        cd.setRegisteredRedirectUri(Collections.emptySet());
        cd.setAutoApproveScopes(new HashSet<>(Arrays.asList("LOGIN")));
        cd.setAuthorities(AuthorityUtils.createAuthorityList("SERVER"));
        cd.setAccessTokenValiditySeconds(defaultAccessTokenValiditySeconds);
        cd.setRefreshTokenValiditySeconds(defaultRefreshTokenValiditySeconds);
        cd.setAdditionalInformation(Collections.emptyMap());

        clientRegistrationService.addClientDetails(cd);
    }


    /**
     * 注册 OAuth client : qh-oauth-wap-front
     */
    private void checkAndAddQhOAuthWapFront() {

        String clientId = "CLIENT_ID_qh-oauth-wap-front";

        try {
            clientDetailsService.loadClientByClientId(clientId);
            return;
        } catch (NoSuchClientException e) {
            // DO NOTHING
        }

        BaseClientDetails cd = new BaseClientDetails();
        cd.setClientId(clientId);
        cd.setClientSecret("CLIENT_PWD_qh-oauth-wap-front_123456");
        cd.setScope(new HashSet<>(Arrays.asList("LOGIN")));
        cd.setResourceIds(Arrays.asList("RSC_ID_qh-oauth-wap"));
        cd.setAuthorizedGrantTypes(Arrays.asList(
                //"authorization_code",
                "implicit"
                //"client_credentials",
                //"password",
                //"refresh_token"
        ));
        cd.setRegisteredRedirectUri(Collections.emptySet());
        cd.setAutoApproveScopes(new HashSet<>(Arrays.asList("LOGIN")));
        cd.setAuthorities(Collections.emptyList());
        cd.setAccessTokenValiditySeconds(defaultAccessTokenValiditySeconds);
        cd.setRefreshTokenValiditySeconds(defaultRefreshTokenValiditySeconds);
        cd.setAdditionalInformation(Collections.emptyMap());

        clientRegistrationService.addClientDetails(cd);
    }


    /**
     * 注册 OAuth client : qh-agency-server
     */
    private void checkAndAddQhAgencyServer() {

        // "code"       - authorize code 模式
        // "token"      - implicit
        // "password"   - 密码模式
        // ""

        String clientId = "CLIENT_ID_qh-agency-server";


        try {
            clientDetailsService.loadClientByClientId(clientId);
            return;
        } catch (NoSuchClientException e) {
            // DO NOTHING
        }

        BaseClientDetails cd = new BaseClientDetails();
        cd.setClientId(clientId);
        cd.setClientSecret("CLIENT_PWD_qh-agency-server_123456");
        cd.setScope(new HashSet<>(Arrays.asList("LOGIN")));
        cd.setResourceIds(Arrays.asList("RSC_ID_qh-oauth-wap", "RSC_ID_qh-pay-wap"));
        cd.setAuthorizedGrantTypes(Arrays.asList(
                "authorization_code",
                //"implicit",
                "client_credentials",
                //"password",
                "refresh_token"
        ));
        cd.setRegisteredRedirectUri(Collections.emptySet());
        cd.setAutoApproveScopes(new HashSet<>(Arrays.asList("LOGIN")));
        cd.setAuthorities(AuthorityUtils.createAuthorityList("SERVER"));
        cd.setAccessTokenValiditySeconds(defaultAccessTokenValiditySeconds);
        cd.setRefreshTokenValiditySeconds(defaultRefreshTokenValiditySeconds);
        cd.setAdditionalInformation(Collections.emptyMap());

        clientRegistrationService.addClientDetails(cd);
    }

    /**
     * 注册 OAuth client : qh-agency-wap-front
     */
    private void checkAndAddQhAgencyWapFront() {

        String clientId = "CLIENT_ID_qh-agency-wap-front";

        try {
            clientDetailsService.loadClientByClientId(clientId);
            return;
        } catch (NoSuchClientException e) {
            // DO NOTHING
        }

        BaseClientDetails cd = new BaseClientDetails();
        cd.setClientId(clientId);
        cd.setClientSecret("CLIENT_PWD_qh-agency-wap-front_123456");
        cd.setScope(new HashSet<>(Arrays.asList("LOGIN")));
        cd.setResourceIds(Arrays.asList("RSC_ID_qh-oauth-wap", "RSC_ID_qh-agency-server"));
        cd.setAuthorizedGrantTypes(Arrays.asList(
                //"authorization_code",
                "implicit"
                //"client_credentials",
                //"password",
                //"refresh_token"
        ));
        cd.setRegisteredRedirectUri(Collections.emptySet());
        cd.setAutoApproveScopes(new HashSet<>(Arrays.asList("LOGIN")));
        cd.setAuthorities(Collections.emptyList());
        cd.setAccessTokenValiditySeconds(defaultAccessTokenValiditySeconds);
        cd.setRefreshTokenValiditySeconds(defaultRefreshTokenValiditySeconds);
        cd.setAdditionalInformation(Collections.emptyMap());

        clientRegistrationService.addClientDetails(cd);
    }

    /**
     * 注册 OAuth client : qh-agency-admin-front
     */
    private void checkAndAddQhAgencyAdminFront() {

        String clientId = "CLIENT_ID_qh-agency-admin-front";

        try {
            clientDetailsService.loadClientByClientId(clientId);
            return;
        } catch (NoSuchClientException e) {
            // DO NOTHING
        }

        BaseClientDetails cd = new BaseClientDetails();
        cd.setClientId(clientId);
        cd.setClientSecret("CLIENT_PWD_qh-agency-admin-front_123456");
        cd.setScope(new HashSet<>(Arrays.asList("LOGIN")));
        cd.setResourceIds(Arrays.asList("RSC_ID_qh-oauth-wap", "RSC_ID_qh-agency-server"));
        cd.setAuthorizedGrantTypes(Arrays.asList(
                //"authorization_code",
                "implicit"
                //"client_credentials",
                //"password",
                //"refresh_token"
        ));
        cd.setRegisteredRedirectUri(Collections.emptySet());
        cd.setAutoApproveScopes(new HashSet<>(Arrays.asList("LOGIN")));
        cd.setAuthorities(Collections.emptyList());
        cd.setAccessTokenValiditySeconds(defaultAccessTokenValiditySeconds);
        cd.setRefreshTokenValiditySeconds(defaultRefreshTokenValiditySeconds);
        cd.setAdditionalInformation(Collections.emptyMap());

        clientRegistrationService.addClientDetails(cd);
    }


    /**
     * 注册 OAuth client : qh-platform-server
     */
    private void checkAndAddQhPlatformServer() {

        // "code"       - authorize code 模式
        // "token"      - implicit
        // "password"   - 密码模式
        // ""

        String clientId = "CLIENT_ID_qh-platform-server";


        try {
            clientDetailsService.loadClientByClientId(clientId);
            return;
        } catch (NoSuchClientException e) {
            // DO NOTHING
        }

        BaseClientDetails cd = new BaseClientDetails();
        cd.setClientId(clientId);
        cd.setClientSecret("CLIENT_PWD_qh-platform-server_123456");
        cd.setScope(new HashSet<>(Arrays.asList("LOGIN")));
        cd.setResourceIds(Arrays.asList("RSC_ID_qh-oauth-wap", "RSC_ID_qh-pay-wap"));
        cd.setAuthorizedGrantTypes(Arrays.asList(
                "authorization_code",
                //"implicit",
                "client_credentials",
                //"password",
                "refresh_token"
        ));
        cd.setRegisteredRedirectUri(Collections.emptySet());
        cd.setAutoApproveScopes(new HashSet<>(Arrays.asList("LOGIN")));
        cd.setAuthorities(AuthorityUtils.createAuthorityList("SERVER"));
        cd.setAccessTokenValiditySeconds(defaultAccessTokenValiditySeconds);
        cd.setRefreshTokenValiditySeconds(defaultRefreshTokenValiditySeconds);
        cd.setAdditionalInformation(Collections.emptyMap());

        clientRegistrationService.addClientDetails(cd);
    }


    /**
     * 注册 OAuth client : qh-platform-admin-front
     */
    private void checkAndAddQhPlatformAdminFront() {

        String clientId = "CLIENT_ID_qh-platform-admin-front";

        try {
            clientDetailsService.loadClientByClientId(clientId);
            return;
        } catch (NoSuchClientException e) {
            // DO NOTHING
        }

        BaseClientDetails cd = new BaseClientDetails();
        cd.setClientId(clientId);
        cd.setClientSecret("CLIENT_PWD_qh-platform-admin-front_123456");
        cd.setScope(new HashSet<>(Arrays.asList("LOGIN")));
        cd.setResourceIds(Arrays.asList("RSC_ID_qh-oauth-wap", "RSC_ID_qh-platform-server"));
        cd.setAuthorizedGrantTypes(Arrays.asList(
                //"authorization_code",
                "implicit"
                //"client_credentials",
                //"password",
                //"refresh_token"
        ));
        cd.setRegisteredRedirectUri(Collections.emptySet());
        cd.setAutoApproveScopes(new HashSet<>(Arrays.asList("LOGIN")));
        cd.setAuthorities(Collections.emptyList());
        cd.setAccessTokenValiditySeconds(defaultAccessTokenValiditySeconds);
        cd.setRefreshTokenValiditySeconds(defaultRefreshTokenValiditySeconds);
        cd.setAdditionalInformation(Collections.emptyMap());

        clientRegistrationService.addClientDetails(cd);
    }


    /**
     * 注册 OAuth client : wx4j-broker-server
     */
    private void checkAndAddWx4jBrokerServer() {

        // "code"       - authorize code 模式
        // "token"      - implicit
        // "password"   - 密码模式
        // ""

        String clientId = "CLIENT_ID_wx4j-broker-server";


        try {
            clientDetailsService.loadClientByClientId(clientId);
            return;
        } catch (NoSuchClientException e) {
            // DO NOTHING
        }

        BaseClientDetails cd = new BaseClientDetails();
        cd.setClientId(clientId);
        cd.setClientSecret("CLIENT_PWD_wx4j-broker-server_123456");
        cd.setScope(new HashSet<>(Arrays.asList("LOGIN")));
        cd.setResourceIds(Arrays.asList("RSC_ID_qh-oauth-wap", "RSC_ID_qh-pay-wap"));
        cd.setAuthorizedGrantTypes(Arrays.asList(
                "authorization_code",
                //"implicit",
                "client_credentials",
                //"password",
                "refresh_token"
        ));
        cd.setRegisteredRedirectUri(Collections.emptySet());
        cd.setAutoApproveScopes(new HashSet<>(Arrays.asList("LOGIN")));
        cd.setAuthorities(AuthorityUtils.createAuthorityList("SERVER"));
        cd.setAccessTokenValiditySeconds(defaultAccessTokenValiditySeconds);
        cd.setRefreshTokenValiditySeconds(defaultRefreshTokenValiditySeconds);
        cd.setAdditionalInformation(Collections.emptyMap());

        clientRegistrationService.addClientDetails(cd);
    }

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
