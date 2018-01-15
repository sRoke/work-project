package net.kingsilk.qh.raffle.api.raffleApp.bargain.wap;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.raffle.api.common.UniResp;
import net.kingsilk.qh.raffle.api.raffleApp.bargain.wap.dto.BackInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("/raffleApp/{raffleAppId}")
public interface CommonApi {
    /**
     * 拿brandAppId
     */
    @ApiOperation(
            value = "raffleAppId"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> get(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId
    );


    @ApiOperation(
            value = "获取物流公司列表",
            nickname = "获取物流公司列表",
            notes = "获取物流公司列表"
    )
    @Path("/getLogisticsCompanyEnum")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Map<String, String>> getLogisticsCompanyEnum(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId
    );

    @ApiOperation(
            value = "获取shopId和brandAppId",
            nickname = "获取shopId和brandAppId",
            notes = "获取shopId和brandAppId"
    )
    @GET
    @Path("/backToShop")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<BackInfo> backToShop(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId
    );

    /**
     * 拿shopName
     */
    @ApiOperation(
            value = "拿shopName"
    )
    @GET
    @Path("/getShopName")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> getShopName(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId
    );

}
