package net.kingsilk.qh.shop.server.conf;



import net.kingsilk.qh.agency.api.brandApp.notify.NotifyApi;


import net.kingsilk.qh.agency.api.brandApp.partner.partnerStaff.PartnerStaffApi;
import net.kingsilk.qh.agency.api.brandApp.partner.skuStore.SkuStoreApi;

import net.kingsilk.qh.agency.api.brandApp.sysConf.SysConfApi;
import net.kingsilk.qh.agency.client.NotifyApiImpl;

import net.kingsilk.qh.agency.client.SkuStoreApiImpl;
import net.kingsilk.qh.agency.client.SysConfApiImpl;
import net.kingsilk.qh.agency.client.partner.PartnerStaffApiImpl;
import net.kingsilk.qh.shop.service.QhShopProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;


@Configuration
public class QhAgencyApiConf {

    @Bean
    public SkuStoreApi qhAgencySkuStoreApi(

            @Qualifier("agencyRestTemplate")
                    RestOperations restOperations,
            QhShopProperties qhShopProperties
    ) {

        return new SkuStoreApiImpl(restOperations, qhShopProperties.getAgencyUt().getBasePath());

    }

    @Bean
    public PartnerStaffApi qhAgencyPartnerStaffApi(
            @Qualifier("agencyRestTemplate")
                    RestOperations restOperations,
            QhShopProperties qhShopProperties
    ) {

        return new PartnerStaffApiImpl(restOperations, qhShopProperties.getAgencyUt().getBasePath());

    }


    @Bean
    public NotifyApi qhAgencyNotifyApi(
            @Qualifier("agencyRestTemplate")
                    RestOperations restOperations,
            QhShopProperties qhShopProperties
    ) {

        return new NotifyApiImpl(restOperations, qhShopProperties.getAgencyUt().getBasePath());

    }

    @Bean
    public SysConfApi qhAgencySysConfApi(
            @Qualifier("agencyRestTemplate")
                    RestOperations restOperations,
            QhShopProperties qhShopProperties
    ) {

        return new SysConfApiImpl(restOperations, qhShopProperties.getAgencyUt().getBasePath());

    }

    @Bean
    public net.kingsilk.qh.agency.api.brandApp.partner.sysConf.SysConfApi qhAgencySysConf(
            @Qualifier("agencyRestTemplate")
                    RestOperations restOperations,
            QhShopProperties qhShopProperties
    ) {

        return new net.kingsilk.qh.agency.client.partner.SysConfApiImpl(restOperations, qhShopProperties.getAgencyUt().getBasePath());

    }

}
