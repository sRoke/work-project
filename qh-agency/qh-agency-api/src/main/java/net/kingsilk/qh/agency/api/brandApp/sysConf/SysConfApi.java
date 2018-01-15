package net.kingsilk.qh.agency.api.brandApp.sysConf;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.addr.dto.AddrSaveReq;
import net.kingsilk.qh.agency.api.brandApp.sysConf.dto.SysConfInfoResp;
import net.kingsilk.qh.agency.api.brandApp.sysConf.dto.SysConfListRep;
import net.kingsilk.qh.agency.api.brandApp.sysConf.dto.SysConfListResp;
import net.kingsilk.qh.agency.api.brandApp.sysConf.dto.SysConfMinPlace;
import net.kingsilk.qh.agency.core.PartnerTypeEnum;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Api(
        tags = "staff",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "员工管理相关API"
)
@Path("/brandApp/{brandAppId}/sysConf")
@Component
public interface SysConfApi {

    //-------------------------设置信息---------------------------------//
    @ApiOperation(
            value = "设置信息",
            nickname = "设置信息",
            notes = "设置信息"
    )
    @Path("/{key}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('PARAMETER_R')")
    UniResp<SysConfInfoResp> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "key")
            @PathParam(value = "key") String key);

    //-------------------------设置全部信息---------------------------------//
    @ApiOperation(
            value = "设置全部信息",
            nickname = "设置全部信息",
            notes = "设置全部信息"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('PARAMETER_R')")
    UniResp<SysConfListResp> list(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @BeanParam SysConfListRep req);

    //-------------------------修改设置信息---------------------------------//
    @ApiOperation(
            value = "修改设置信息",
            nickname = "修改设置信息",
            notes = "修改设置信息"
    )
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('PARAMETER_U')")
    UniResp<String> update(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "该后的值")
            @QueryParam(value = "disCount") String disCount,
            @ApiParam(value = "minAmount")
            @QueryParam(value = "minAmount") String minAmount,
            @ApiParam(value = "partnerTypes")
            @QueryParam(value = "partnerTypes")String partnerTypes);

    //-------------------------修改渠道商排名设置信息---------------------------------//
    @ApiOperation(
            value = "修改设置信息",
            nickname = "修改设置信息",
            notes = "修改设置信息"
    )
    @PUT
    @Path("/minPlace")
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('PARAMETER_U')")
    UniResp<String> updateMinPlace(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "品牌商ID")
            @BeanParam SysConfMinPlace sysConfMinPlace);

    //-------------------------修改设置信息---------------------------------//
    @ApiOperation(
            value = "修改设置信息",
            nickname = "修改设置信息",
            notes = "修改设置信息"
    )
    @Path("/brandAppAddr")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('REFUNDADDR_U')")
    UniResp<String> setBrandAppAddr(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
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
            @PathParam(value = "brandAppId") String brandAppId);

}
