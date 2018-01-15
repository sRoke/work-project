package net.kingsilk.qh.shop.server.conf;

import net.kingsilk.qh.shop.server.resource.brandApp.authorities.AuthoritiesResource;
import net.kingsilk.qh.shop.server.resource.brandApp.home.HomeResource;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.ShopResource;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.deliverInvoice.DeliverInvoiceResource;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.itemProp.ItemPropResource;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.addr.AddrResource;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.cart.CartResource;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.member.MemberResource;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.notify.NotifyResource;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.order.OrderResource;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.pay.PayResource;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.recentSearch.RecentSearchResource;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.refund.RefundResource;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.shopAccount.ShopAccountResource;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.shopOrder.ShopBuyResource;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.shopOrder.ShopOrderResource;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.shopStaff.ShopStaffResource;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.shopStaffGroup.ShopStaffGroupResource;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.withdraw.WithdrawResource;
import net.kingsilk.qh.shop.server.resource.brandApp.staff.StaffResource;
import net.kingsilk.qh.shop.server.resource.brandApp.staffGroup.StaffGroupResource;
import net.kingsilk.qh.shop.server.resource.brandApp.sysConf.SysConfResource;
import net.kingsilk.qh.shop.server.resource.common.CommonResource;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;

// 参考： JerseyAutoConfiguration
@Configuration
public class JerseyConf {


//package net.kingsilk.qh.shop.server.conf;
//
//import AuthoritiesResource;
//import net.kingsilk.qh.shop.server.resource.brandApp.home.HomeResource;
//import net.kingsilk.qh.shop.server.resource.brandApp.shop.ShopResource;
//import net.kingsilk.qh.shop.server.resource.brandApp.shop.category.CategoryResource;
//import net.kingsilk.qh.shop.server.resource.brandApp.shop.item.ItemResource;
//import net.kingsilk.qh.shop.server.resource.brandApp.shop.shopStaff.ShopStaffResource;
//import net.kingsilk.qh.shop.server.resource.brandApp.shop.shopStaffGroup.ShopStaffGroupResource;
//import net.kingsilk.qh.shop.server.resource.brandApp.staff.StaffResource;
//import net.kingsilk.qh.shop.server.resource.brandApp.staffGroup.StaffGroupResource;
//import net.kingsilk.qh.shop.server.resource.common.CommonResource;
//import org.glassfish.jersey.media.multipart.MultiPartFeature;
//import org.glassfish.jersey.server.ResourceConfig;
//import org.glassfish.jersey.server.ServerProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
//import javax.ws.rs.container.ContainerResponseFilter;
//import javax.ws.rs.core.MediaType;
//import java.util.Arrays;
//
//// 参考： JerseyAutoConfiguration
//@Configuration
//public class JerseyConf {
//
//
////    @Bean
////    BeanConfig swaggerBeanConfig() {
////        BeanConfig config = new BeanConfig();
////        config.setTitle("Swagger sample app");
////        config.setVersion("1.0.0");
////        config.setBasePath("/api");
//////        config.setSchemes(["https"]);
//////        config.setResourcePackage(
//////                "io.swagger.jaxrs.listing," +
//////                        "net.kingsilk.qh.net.kingsilk.qh.agency.net.kingsilk.qh.net.kingsilk.qh.agency.net.kingsilk.qh.net.kingsilk.qh.agency.wap.controller," +
//////                        "net.kingsilk.qh.net.kingsilk.qh.agency.net.kingsilk.qh.net.kingsilk.qh.agency.net.kingsilk.qh.net.kingsilk.qh.agency.wap.resource"
//////        )
////        config.setScan(true);
////        return config;
////    }
//
//    @Bean
//    ResourceConfig resourceConfig(
//            Environment environment
//    ) {
//
//
//        ResourceConfig resourceConfig = new ResourceConfig();
//
//        // 强制 响应的contentType中追加 charset
//        // http://stackoverflow.com/questions/5514087/jersey-rest-default-character-encoding/20569571
//        resourceConfig.register((ContainerResponseFilter) (request, response) -> {
//            MediaType type = response.getMediaType();
//            if (type != null) {
//                String contentType = type.toString();
//                if (!contentType.contains("charset")) {
//                    contentType = contentType + ";charset=utf-8";
//                    response.getHeaders().putSingle("Content-Type", contentType);
//                }
//            }
//        });
//
//        resourceConfig.register(MultiPartFeature.class);
//        resourceConfig.register(io.swagger.jaxrs.listing.ApiListingResource.class);
//        resourceConfig.register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
//
//        // http://docs.spring.io/spring-boot/docs/1.5.1.RELEASE/reference/htmlsingle/#boot-features-jersey
//        resourceConfig.property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
//        resourceConfig.property(ServerProperties.PROCESSING_RESPONSE_ERRORS_ENABLED, true);
//
//
//        resourceConfig.register(CommonResource.class);
//        resourceConfig.register(AuthoritiesResource.class);
//        resourceConfig.register(StaffGroupResource.class);
//        resourceConfig.register(ShopStaffResource.class);
//        resourceConfig.register(ShopStaffGroupResource.class);
//        resourceConfig.register(StaffResource.class);
//        resourceConfig.register(ItemResource.class);
//        resourceConfig.register(HomeResource.class);
//        resourceConfig.register(ShopResource.class);
//        resourceConfig.register(net.kingsilk.qh.shop.server.resource.brandApp.shop.authorities.AuthoritiesResource.class);
//
//        resourceConfig.register(CategoryResource.class);
//
//
//        // 确保测试用的代码，线上环境不可用
//        if (!Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
//        }
//        return resourceConfig;
//
//    }

    @Bean
    ResourceConfig resourceConfig(
            Environment environment
    ) {


        ResourceConfig resourceConfig = new ResourceConfig();

        // 强制 响应的contentType中追加 charset
        // http://stackoverflow.com/questions/5514087/jersey-rest-default-character-encoding/20569571
        resourceConfig.register((ContainerResponseFilter) (request, response) -> {
            MediaType type = response.getMediaType();
            if (type != null) {
                String contentType = type.toString();
                if (!contentType.contains("charset")) {
                    contentType = contentType + ";charset=utf-8";
                    response.getHeaders().putSingle("Content-Type", contentType);
                }
            }
        });

        resourceConfig.register(MultiPartFeature.class);
        resourceConfig.register(io.swagger.jaxrs.listing.ApiListingResource.class);
        resourceConfig.register(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        // http://docs.spring.io/spring-boot/docs/1.5.1.RELEASE/reference/htmlsingle/#boot-features-jersey
        resourceConfig.property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
        resourceConfig.property(ServerProperties.PROCESSING_RESPONSE_ERRORS_ENABLED, true);

        resourceConfig.register(net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.home.HomeResource.class);
        resourceConfig.register(MemberResource.class);
        resourceConfig.register(PayResource.class);
        resourceConfig.register(WithdrawResource.class);
        resourceConfig.register(CartResource.class);
        resourceConfig.register(AddrResource.class);
        resourceConfig.register(NotifyResource.class);
        resourceConfig.register(ShopBuyResource.class);
        resourceConfig.register(CommonResource.class);
        resourceConfig.register(AuthoritiesResource.class);
        resourceConfig.register(StaffGroupResource.class);
        resourceConfig.register(ShopStaffResource.class);
        resourceConfig.register(ShopStaffGroupResource.class);
        resourceConfig.register(StaffResource.class);
        resourceConfig.register(net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.Item.ItemResource.class);
        resourceConfig.register(HomeResource.class);
        resourceConfig.register(net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.home.HomeResource.class);
        resourceConfig.register(ShopResource.class);
        resourceConfig.register(net.kingsilk.qh.shop.server.resource.brandApp.shop.authorities.AuthoritiesResource.class);
        resourceConfig.register(net.kingsilk.qh.shop.server.resource.brandApp.shop.order.OrderResource.class);
        resourceConfig.register(net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.category.CategoryResource.class);
        resourceConfig.register(net.kingsilk.qh.shop.server.resource.brandApp.shop.category.CategoryResource.class);
        resourceConfig.register(OrderResource.class);
        resourceConfig.register(DeliverInvoiceResource.class);
        resourceConfig.register(net.kingsilk.qh.shop.server.resource.brandApp.shop.item.ItemResource.class);
        resourceConfig.register(RecentSearchResource.class);
        resourceConfig.register(RefundResource.class);
        resourceConfig.register(net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.refund.RefundResource.class);
        resourceConfig.register(ShopAccountResource.class);
        resourceConfig.register(ItemPropResource.class);
        resourceConfig.register(SysConfResource.class);
        resourceConfig.register(ShopOrderResource.class);
        // 确保测试用的代码，线上环境不可用
        if (!Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
        }
        return resourceConfig;

    }
}
