package net.kingsilk.qh.agency.server.conf;

import groovy.transform.CompileStatic;
import net.kingsilk.qh.agency.QhAgencyProperties;
import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.org.OrgApi;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffApi;
import net.kingsilk.qh.oauth.client.impl.user.UserApiImpl;
import net.kingsilk.qh.oauth.client.impl.user.org.OrgApiImpl;
import net.kingsilk.qh.oauth.client.impl.user.org.orgStaff.OrgStaffApiImpl;
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
            QhAgencyProperties qhAgencyProperties
    ) {
        return new UserApiImpl(restOperations,qhAgencyProperties.getOauthUt().getBasePath());
    }


    @Bean
    public OrgStaffApi qhOauthOrgStaffApi(
            @Qualifier("oauthRestTemplate")
                    RestOperations restOperations,
            QhAgencyProperties qhAgencyProperties
    ) {
        return new OrgStaffApiImpl(restOperations,qhAgencyProperties.getOauthUt().getBasePath());
    }

    @Bean
    public OrgApi qhOauthOrgApi(
            @Qualifier("oauthRestTemplate")
                    RestOperations restOperations,
            QhAgencyProperties qhAgencyProperties
    ) {
        return new OrgApiImpl(restOperations,qhAgencyProperties.getOauthUt().getBasePath());
    }

}
