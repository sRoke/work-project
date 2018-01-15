package net.kingsilk.qh.platform.server.conf;

import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.org.OrgApi;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffApi;
import net.kingsilk.qh.oauth.client.impl.user.UserApiImpl;
import net.kingsilk.qh.oauth.client.impl.user.org.OrgApiImpl;
import net.kingsilk.qh.oauth.client.impl.user.org.orgStaff.OrgStaffApiImpl;
import net.kingsilk.qh.platform.service.QhPlatformProperties;
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

                    QhPlatformProperties qhPlatformProperties
    ) {
        return new UserApiImpl(restOperations, qhPlatformProperties.getOauthUt().getBasePath());
    }

    @Bean
    public OrgApi qhOauthOrgApi(
            @Qualifier("oauthRestTemplate")
                    RestOperations restOperations,

                    QhPlatformProperties qhPlatformProperties
    ) {
        return new OrgApiImpl(restOperations, qhPlatformProperties.getOauthUt().getBasePath());
    }

    @Bean
    public OrgStaffApi qhOauthOrgStaffApi(
            @Qualifier("oauthRestTemplate")
                    RestOperations restOperations,

                    QhPlatformProperties qhPlatformProperties
    ) {
        return new OrgStaffApiImpl(restOperations, qhPlatformProperties.getOauthUt().getBasePath());
    }

}
