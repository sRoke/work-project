package net.kingsilk.qh.agency.api.brandApp.partner.sysConf;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.addr.dto.AddrSaveReq;
import net.kingsilk.qh.agency.api.brandApp.sysConf.dto.SysConfInfoResp;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@Api(
        tags = "staff",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "员工管理相关API"
)
@Path("/brandApp/{brandAppId}/partner/{partnerId}/sysConf")
public interface SysConfApi {
    @ApiOperation(
            value = "获取信息",
            nickname = "获取信息",
            notes = "获取信息"
    )
    @Path("/{key}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<SysConfInfoResp> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "key")
            @PathParam(value = "key") String key);
    //-------------------------修改设置信息---------------------------------//
    @ApiOperation(
            value = "修改设置信息",
            nickname = "修改设置信息",
            notes = "修改设置信息"
    )
    @Path("/brandAppAddr")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> setBrandAppAddr(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            AddrSaveReq req);


    //------------------------- 判断退货地址---------------------------------//
    @ApiOperation(
            value = "判断退货地址",
            nickname = "判断退货地址",
            notes = "判断退货地址"
    )
    @Path("/brandAppAddr")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> judgeBrandAppAddr(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId);

}
