package net.kingsilk.qh.agency.server.conf

import io.swagger.jaxrs.config.BeanConfig
import io.swagger.jaxrs.listing.ApiListingResource
import io.swagger.jaxrs.listing.SwaggerSerializers
import net.kingsilk.qh.agency.server.resource.brandApp.addr.AddrResource
import net.kingsilk.qh.agency.server.resource.brandApp.authorities.AuthoritiesResource
import net.kingsilk.qh.agency.server.resource.brandApp.category.CategoryResource
import net.kingsilk.qh.agency.server.resource.brandApp.certificate.CertificateResource
import net.kingsilk.qh.agency.server.resource.brandApp.partner.cart.CartResource
import net.kingsilk.qh.agency.server.resource.brandApp.partner.partnerStaff.PartnerStaffResource
import net.kingsilk.qh.agency.server.resource.brandApp.partner.saleRecord.SaleRecordResource
import net.kingsilk.qh.agency.server.resource.brandApp.partner.withdrawCash.WithdrawCashResource
import net.kingsilk.qh.agency.server.resource.brandApp.partnerAccount.PartnerAccountResource
import net.kingsilk.qh.agency.server.resource.brandApp.withdrawCashManage.WithdrawCashManageResource
import net.kingsilk.qh.agency.server.resource.common.CommonResource
import net.kingsilk.qh.agency.server.resource.brandApp.deliverInvoice.DeliverInvoiceResource
import net.kingsilk.qh.agency.server.resource.home.HomeResource
import net.kingsilk.qh.agency.server.resource.brandApp.item.ItemResource
import net.kingsilk.qh.agency.server.resource.brandApp.itemProp.ItemPropResource
import net.kingsilk.qh.agency.server.resource.brandApp.notify.NotifyResource
import net.kingsilk.qh.agency.server.resource.brandApp.order.OrderResource
import net.kingsilk.qh.agency.server.resource.brandApp.partner.PartnerResource
import net.kingsilk.qh.agency.server.resource.brandApp.partnerStaff.PartnerStaffManageResource
import net.kingsilk.qh.agency.server.resource.brandApp.partner.pay.PayResource
import net.kingsilk.qh.agency.server.resource.brandApp.refund.RefundResource
import net.kingsilk.qh.agency.server.resource.brandApp.partner.report.ReportResource
import net.kingsilk.qh.agency.server.resource.brandApp.partner.skuStore.SkuStoreResource
import net.kingsilk.qh.agency.server.resource.brandApp.staff.StaffResource
import net.kingsilk.qh.agency.server.resource.brandApp.staffGroup.StaffGroupResource
import net.kingsilk.qh.agency.server.resource.brandApp.sysConf.SysConfResource
import net.kingsilk.qh.agency.server.resource.brandApp.test.TestLitResource
import net.kingsilk.qh.agency.server.resource.brandApp.test.TestResource
import net.kingsilk.qh.agency.server.resource.brandApp.uploadImg.UploadResource
import org.glassfish.jersey.media.multipart.MultiPartFeature
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.server.ServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
import javax.ws.rs.core.MediaType

// 参考： JerseyAutoConfiguration
@Configuration
public class JerseyConf {

//    @Bean
//    BeanConfig swaggerBeanConfig() {
//        BeanConfig config = new BeanConfig();
//        config.setTitle("Swagger sample app");
//        config.setVersion("1.0.0");
//        config.setBasePath("/api");
//        //config.setSchemes(new String[]{"https"});
////        config.setResourcePackage(
////                "io.swagger.jaxrs.listing," +
////                        "net.kingsilk.qh.net.kingsilk.qh.agency.net.kingsilk.qh.net.kingsilk.qh.agency.net.kingsilk.qh.net.kingsilk.qh.agency.wap.controller," +
////                        "net.kingsilk.qh.net.kingsilk.qh.agency.net.kingsilk.qh.net.kingsilk.qh.agency.net.kingsilk.qh.net.kingsilk.qh.agency.wap.resource"
////        )
//        config.setScan(true);
//        return config;
//    }

    @Bean
    ResourceConfig resourceConfig(
            BeanConfig swaggerBeanConfig,
            Environment environment
    ) {


        ResourceConfig resourceConfig = new ResourceConfig();

        // 强制 响应的contentType中追加 charset
        // http://stackoverflow.com/questions/5514087/jersey-rest-default-character-encoding/20569571
        resourceConfig.register(new ContainerResponseFilter() {
            @Override
            public void filter(ContainerRequestContext request, ContainerResponseContext response) {
                MediaType type = response.getMediaType();
                if (type != null) {
                    String contentType = type.toString();
                    if (!contentType.contains("charset")) {
                        contentType = contentType + ";charset=utf-8";
                        response.getHeaders().putSingle("Content-Type", contentType);
                    }
                }
            }
        });

        resourceConfig.register(MultiPartFeature.class);
        resourceConfig.register(ApiListingResource.class);
        resourceConfig.register(SwaggerSerializers.class);

        // TODO 根据文档，需要手动一一将RESTful controller 添加至此。
        // http://docs.spring.io/spring-boot/docs/1.5.1.RELEASE/reference/htmlsingle/#boot-features-jersey
//        resourceConfig.packages("net.kingsilk.qh.net.kingsilk.qh.agency.net.kingsilk.qh.net.kingsilk.qh.agency.wap.resource")
//        resourceConfig.packages("net.kingsilk.qh.net.kingsilk.qh.agency.net.kingsilk.qh.net.kingsilk.qh.agency.wap.api")
        resourceConfig.property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
        resourceConfig.property(ServerProperties.PROCESSING_RESPONSE_ERRORS_ENABLED, true);


        resourceConfig.register(HomeResource.class);


        resourceConfig.register(PartnerStaffManageResource.class);
        resourceConfig.register(StaffResource.class);
        resourceConfig.register(AuthoritiesResource.class);
        resourceConfig.register(ItemPropResource.class);
        resourceConfig.register(OrderResource.class);
        resourceConfig.register(ItemResource.class);
        resourceConfig.register(RefundResource.class);
        resourceConfig.register(CategoryResource.class);
        resourceConfig.register(UploadResource.class);
        resourceConfig.register(PartnerResource.class);
        resourceConfig.register(TestLitResource.class);
        resourceConfig.register(DeliverInvoiceResource.class);
        resourceConfig.register(AddrResource.class);
        resourceConfig.register(CommonResource.class);
        resourceConfig.register(SkuStoreResource.class);
        resourceConfig.register(ReportResource.class);
        resourceConfig.register(NotifyResource.class);
        resourceConfig.register(CertificateResource.class);
        resourceConfig.register(WithdrawCashManageResource.class);
        resourceConfig.register(SysConfResource.class);
        resourceConfig.register(StaffGroupResource.class)
        resourceConfig.register(PartnerAccountResource.class);
        resourceConfig.register(SaleRecordResource.class);


        //Wap
        resourceConfig.register(net.kingsilk.qh.agency.server.resource.brandApp.partner.item.ItemResource.class)

        resourceConfig.register(net.kingsilk.qh.agency.server.resource.brandApp.partner.category.CategoryResource.class);
        resourceConfig.register(CartResource.class);
        resourceConfig.register(net.kingsilk.qh.agency.server.resource.brandApp.partner.order.OrderResource.class);
        resourceConfig.register(PayResource.class);
        resourceConfig.register(net.kingsilk.qh.agency.server.resource.brandApp.partner.refund.RefundResource.class);
        resourceConfig.register(net.kingsilk.qh.agency.server.resource.brandApp.partner.deliverInvoice.DeliverInvoiceResource.class);
        resourceConfig.register(WithdrawCashResource.class);
        resourceConfig.register(PartnerStaffResource.class)
        resourceConfig.register(net.kingsilk.qh.agency.server.resource.brandApp.partner.partnerAccount.PartnerAccountResource.class)
        resourceConfig.register(net.kingsilk.qh.agency.server.resource.brandApp.partner.sysConf.SysConfResource.class)
        resourceConfig.register(net.kingsilk.qh.agency.server.resource.brandApp.partner.addr.AddrResource.class);


        if (!Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
            resourceConfig.register(TestResource.class);
            resourceConfig.register(TestLitResource.class);
        }

        return resourceConfig;
    }


}
