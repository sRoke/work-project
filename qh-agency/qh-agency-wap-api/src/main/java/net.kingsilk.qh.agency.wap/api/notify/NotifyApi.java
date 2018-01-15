package net.kingsilk.qh.agency.wap.api.notify;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.kingsilk.qh.agency.wap.api.notify.dto.NotifyQhPayReq;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by zcw on 3/30/17.
 * 调用外部系统回调相关接口
 */

@Api(
        tags = "notify",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "调用外部系统回调相关接口"
)
@Path("/brandApp/{bandComId}/partner/{partnerId}/notify")
@Component
public interface NotifyApi {

    @ApiOperation(
            value = "支付系统回调",
            nickname = "支付系统回调",
            notes = "支付系统回调"
    )
    @Path("")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    String qhPay(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "bandComId") String bandComId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            NotifyQhPayReq req);
}