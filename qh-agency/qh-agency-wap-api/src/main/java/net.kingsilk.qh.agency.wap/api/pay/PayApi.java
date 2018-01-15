package net.kingsilk.qh.agency.wap.api.pay;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.kingsilk.qh.agency.wap.api.UniResp;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Api(
        tags = "pay",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "支付相关接口"
)
@Path("/pay")
@Component
public interface PayApi {


    @ApiOperation(
            value = "对订单进行支付",
            nickname = "对订单进行支付",
            notes = "对订单进行支付"
    )
    @Path("/order")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> pay(
            @QueryParam(value = "orderId") String orderId,
            @QueryParam(value = "memo") String memo,
            @Context HttpServletRequest request);
}
