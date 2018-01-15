package net.kingsilk.qh.agency.api.brandApp.partner.partnerStaff;

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
@Path("/brandApp/{brandAppId}/partner/{partnerId}/partnerStaff")
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
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "会员ID")
            @PathParam(value = "id") String id
    );

    //-------------------------保存会员信息---------------------------------//
    //TODO PartnerStaffSaveReq
    @ApiOperation(
            value = "保存会员信息",
            nickname = "保存会员信息",
            notes = "保存会员信息"
    )
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> save(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @BeanParam PartnerStaffSaveReq req);

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
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "渠道商员工ID")
            @PathParam(value = "id") String id,
            PartnerStaffSaveReq req);

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
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
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
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "会员ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "禁用状态")
            @QueryParam(value = "disabled") Boolean disabled);


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
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "会员ID")
            @PathParam(value = "userId") String userId);



    //-------------------------禁用或启用会员---------------------------------//
    //FIXME 是否使用
//    @ApiOperation(
//            value = "注册用户",
//            nickname = "注册用户",
//            notes = "注册用户"
//    )
//    @Path("/register")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<String> register( @Context HttpServletRequest request);


//    @ApiOperation(
//            value = "上传会员信息",
//            nickname = "上传会员信息",
//            notes = "上传会员信息"
//    )
//    @Path("/upload")
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<String> readExcel(@BeanParam MultipartFile file) throws IOException;

//    @ApiOperation(
//            value = "查询手机号是否重复",
//            nickname = "查询手机号是否重复",
//            notes = "查询手机号是否重复"
//    )
//    @Path("/queryPhone")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<Boolean> queryPhone(@PathParam(value = "phone") String phone, @PathParam(value = "id") String id);

}
