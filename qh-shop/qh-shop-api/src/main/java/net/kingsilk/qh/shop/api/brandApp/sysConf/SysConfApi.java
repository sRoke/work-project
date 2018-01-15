package net.kingsilk.qh.shop.api.brandApp.sysConf;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.sysConf.dto.ShopPriceReq;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 *
 */
@Api(
        tags = "sysConf",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "系统配置API"
)
@Path("/brandApp/{brandAppId}/sysConf")
@Component
public interface SysConfApi {

    @ApiOperation(
            value = "门店购买价格设置",
            nickname = "门店购买价格设置",
            notes = "门店购买价格设置")
    @Path("/shopPrice")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> shopPrice(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            ShopPriceReq shopPrice
    );

    @ApiOperation(
            value = "门店购买价格设置",
            nickname = "门店购买价格设置",
            notes = "门店购买价格设置")
    @Path("/shopPrice")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Map<String, Map<String,Integer>>> shopPrice(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId
    );
}
