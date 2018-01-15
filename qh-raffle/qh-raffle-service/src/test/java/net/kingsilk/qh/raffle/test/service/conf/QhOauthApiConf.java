package net.kingsilk.qh.raffle.test.service.conf;

import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.addr.AddrApi;
import net.kingsilk.qh.oauth.api.user.org.OrgApi;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffApi;
import net.kingsilk.qh.oauth.client.impl.user.UserApiImpl;
import net.kingsilk.qh.oauth.client.impl.user.addr.AddrApiImpl;
import net.kingsilk.qh.oauth.client.impl.user.org.OrgApiImpl;
import net.kingsilk.qh.oauth.client.impl.user.org.orgStaff.OrgStaffApiImpl;
import net.kingsilk.qh.raffle.QhRaffleProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;


@Configuration
public class QhOauthApiConf {

    @Bean
    public UserApi qhOauthUserApi(

            @Qualifier("oauthRestTemplate")
                    RestOperations restOperations,
            QhRaffleProperties qhRaffleProperties
    ) {
        return new UserApiImpl(restOperations, qhRaffleProperties.getOauthUt().getBasePath());
    }

    @Bean
    public OrgApi qhOauthOrgApi(
            @Qualifier("oauthRestTemplate")
                    RestOperations restOperations,
            QhRaffleProperties qhRaffleProperties
    ) {
        return new OrgApiImpl(restOperations, qhRaffleProperties.getOauthUt().getBasePath());
    }

    @Bean
    public OrgStaffApi qhOauthOrgStaffApi(
            @Qualifier("oauthRestTemplate")
                    RestOperations restOperations,
            QhRaffleProperties qhRaffleProperties
    ) {
        return new OrgStaffApiImpl(restOperations, qhRaffleProperties.getOauthUt().getBasePath());
    }

    @Bean
    public AddrApi qhOauthAddrApi(
            @Qualifier("oauthRestTemplate")
                    RestOperations restOperations,
            QhRaffleProperties qhRaffleProperties
    ) {
        return new AddrApiImpl(restOperations, qhRaffleProperties.getOauthUt().getBasePath());
    }

}
