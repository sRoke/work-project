package net.kingsilk.qh.shop.api.controller.pay;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniResp;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Api(
        tags = "pay",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "支付相关接口"
)
@Path("/brandApp/{brandAppId}/partner/{partnerId}/pay")
@Component
public interface PayApi {

    /**
     * 调用Qh-Pay项目相应接口生成支付二维码 ->
     * <p>
     * 流程：
     * 用户扫码后，调到微信或支付宝的支付，支付成功后，
     * 调用notify，更改订单状态，更改库存信息，更改账户信息
     */
    @ApiOperation(
            value = "对订单进行支付",
            nickname = "对订单进行支付",
            notes = "对订单进行支付"
    )
    @Path("/{orderId}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> pay(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "订单ID")
            @PathParam(value = "orderId") String orderId,
            @QueryParam(value = "memo") String memo);
}
