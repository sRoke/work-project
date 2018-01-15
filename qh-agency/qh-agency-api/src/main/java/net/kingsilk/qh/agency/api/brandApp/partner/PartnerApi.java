package net.kingsilk.qh.agency.api.brandApp.partner;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniPageResp;
import net.kingsilk.qh.agency.api.UniResp;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */

@Api(
        tags = "partner",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "渠道商管理相关API"
)
@Path("/brandApp/{brandAppId}/partner")
public interface PartnerApi {

    //-------------------------查看渠道商信息---------------------------------//
    @ApiOperation(
            value = "渠道商信息",
            nickname = "渠道商信息",
            notes = "渠道商信息"
    )
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<PartnerInfoResp> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,

            @ApiParam(value = "渠道商ID")
            @PathParam(value = "id")
                    String id);

    //-------------------------更新渠道商信息---------------------------------//
    @ApiOperation(
            value = "更新渠道商信息",
            nickname = "更新渠道商信息",
            notes = "更新渠道商信息"
    )
    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> update(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "id") String id,
            PartnerSaveReq partnerSaveReq);

    //-------------------------获取渠道商列表---------------------------------//
    @ApiOperation(
            value = "获取渠道商列表",
            nickname = "获取渠道商列表",
            notes = "获取渠道商列表"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<UniPageResp<PartnerInfoResp>> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @BeanParam PartnerPageReq partnerPageReq);

    //-------------------------渠道商审核---------------------------------//
    @ApiOperation(
            value = "渠道商审核",
            nickname = "渠道商审核",
            notes = "渠道商审核"
    )
    @Path("/{id}/review")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> review(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "审核通过情况")
            @QueryParam(value = "status") Boolean status,
            @QueryParam(value = "parentId") String parentId,
            @QueryParam(value = "shopBrandAppId") String shopBrandAppId,
            @QueryParam(value = "shopId") String shopId);


    //-------------------------渠道商禁用---------------------------------//
    @ApiOperation(
            value = "渠道商禁用",
            nickname = "渠道商禁用",
            notes = "渠道商禁用"
    )
    @Path("/{id}/changeStatus")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> changeStatus(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "禁用情况")
            @QueryParam(value = "status") Boolean status);

    //-------------------------渠道商申请---------------------------------//
    @ApiOperation(
            value = "渠道商申请",
            nickname = "渠道商申请",
            notes = "渠道商申请"
    )
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> apply(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            PartnerApplyReq partnerApplyReq);

    //-------------------------检查渠道商信息---------------------------------//
    @ApiOperation(
            value = "检查渠道商信息",
            nickname = "检查渠道商信息",
            notes = "检查渠道商信息"
    )
    @Path("/check")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> check(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId
    );

    //-------------------------检查渠道商信息---------------------------------//
    @ApiOperation(
            value = "获得渠道商类型信息",
            nickname = "获得渠道商类型信息",
            notes = "获得渠道商类型信息"
    )
    @Path("/partnerTypes")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<LinkedHashMap<String, String>> partnerTypes(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId
    );

}
