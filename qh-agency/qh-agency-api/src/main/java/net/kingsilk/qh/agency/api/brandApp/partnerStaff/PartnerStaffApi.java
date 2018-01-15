package net.kingsilk.qh.agency.api.brandApp.partnerStaff;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniPageResp;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.partner.partnerStaff.dto.PartnerStaffInfoResp;
import net.kingsilk.qh.agency.api.brandApp.partner.partnerStaff.dto.PartnerStaffPageReq;
import net.kingsilk.qh.agency.api.brandApp.partner.partnerStaff.dto.PartnerStaffSaveReq;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@Api(
        tags = "partnerStaff",
        description = "会员管理相关API"
)
@Path("/brandApp/{brandAppId}/partnerStaff")
public interface PartnerStaffApi {
    //-------------------------会员信息---------------------------------//
    @ApiOperation(
            value = "会员信息",
            notes = "会员信息")
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<PartnerStaffInfoResp> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "会员ID")
            @PathParam(value = "id") String id
    );


    //-------------------------会员分页信息---------------------------------//
    @ApiOperation(
            value = "会员分页信息",
            nickname = "会员分页信息",
            notes = "会员分页信息"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<UniPageResp<PartnerStaffInfoResp>> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @BeanParam PartnerStaffPageReq partnerStaffPageReq);

    //-------------------------禁用或启用会员---------------------------------//
    @ApiOperation(
            value = "禁用或启用会员",
            notes = "禁用或启用会员"
    )
    @Path("/{id}/enable")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> enable(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "会员ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "禁用状态")
            @QueryParam(value = "disabled") Boolean disabled);

    //-------------------------更新会员信息---------------------------------//
    @ApiOperation(
            value = "更新会员信息",
            nickname = "更新会员信息",
            notes = "更新会员信息"
    )
    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> update(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商员工ID")
            @PathParam(value = "id") String id,
            PartnerStaffSaveReq req);

    //--------------------------根据userId查会员信息-----------------------//
    @ApiOperation(
            value = "根据userId查找会员",
            notes = "根据userId查找会员"
    )
    @Path("/find/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<PartnerStaffInfoResp> find(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "会员ID")
            @PathParam(value = "userId") String userId);

}
