package net.kingsilk.qh.shop.api.brandApp.shop.mall.notify;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.notify.dto.NotifyQhPayReq;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 调用外部系统回调相关接口
 */

@Api(
        tags = "notify",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "调用外部系统回调相关接口"
)
@Path("/brandApp/{brandAppId}/shop/{shopId}/notify")
@Component
public interface NotifyApi {

    @ApiOperation(
            value = "qhShop支付成功后",
            nickname = "qhShop支付成功后",
            notes = "qhShop支付成功后"
    )
    @Path("/pay")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    boolean qhShop(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "店铺id")
            @PathParam(value = "shopId")
                    String shopId,
            NotifyQhPayReq req);
}