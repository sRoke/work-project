package net.kingsilk.qh.vote.server.conf;

import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.org.OrgApi;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffApi;
import net.kingsilk.qh.oauth.client.impl.user.UserApiImpl;
import net.kingsilk.qh.oauth.client.impl.user.org.OrgApiImpl;
import net.kingsilk.qh.oauth.client.impl.user.org.orgStaff.OrgStaffApiImpl;
import net.kingsilk.qh.vote.service.QhVoteProperties;
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
            QhVoteProperties qhVoteProperties
    ) {
        return new UserApiImpl(restOperations, qhVoteProperties.getOauthUt().getBasePath());
    }

    @Bean
    public OrgApi qhOauthOrgApi(
            @Qualifier("oauthRestTemplate")
                    RestOperations restOperations,
            QhVoteProperties qhVoteProperties
    ) {
        return new OrgApiImpl(restOperations, qhVoteProperties.getOauthUt().getBasePath());
    }

    @Bean
    public OrgStaffApi qhOauthOrgStaffApi(
            @Qualifier("oauthRestTemplate")
                    RestOperations restOperations,
            QhVoteProperties qhVoteProperties
    ) {
        return new OrgStaffApiImpl(restOperations, qhVoteProperties.getOauthUt().getBasePath());
    }

}
