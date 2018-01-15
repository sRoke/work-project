package net.kingsilk.qh.agency.wap.api.partner;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.kingsilk.qh.agency.wap.api.UniResp;
import net.kingsilk.qh.agency.wap.api.partner.dto.PartnerApplyReq;
import net.kingsilk.qh.agency.wap.api.partner.dto.PartnerInfoResp;
import net.kingsilk.qh.agency.wap.api.partner.dto.PartnerUpdateReq;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@Api(
        tags = "partner",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "渠道商管理API"
)
@Path("/partner")
public interface PartnerApi {

    //-------------------------渠道商申请---------------------------------//
    @ApiOperation(
            value = "渠道商申请",
            nickname = "渠道商申请",
            notes = "渠道商申请"
    )
    @Path("/apply")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> apply( PartnerApplyReq partnerApplyReq);

    //-------------------------检查渠道商信息---------------------------------//
    @ApiOperation(
            value = "检查渠道商信息",
            nickname = "检查渠道商信息",
            notes = "检查渠道商信息"
    )
    @Path("/check")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Boolean> check(@Context HttpServletRequest request);

    //-------------------------渠道商信息---------------------------------//
    @ApiOperation(
            value = "渠道商信息",
            nickname = "渠道商信息",
            notes = "渠道商信息"
    )
    @Path("/info")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<PartnerInfoResp> info();


    //-------------------------更新渠道商信息---------------------------------//
    @ApiOperation(
            value = "更新渠道商信息",
            nickname = "更新渠道商信息",
            notes = "更新渠道商信息"
    )
    @Path("/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> update(@BeanParam PartnerUpdateReq partnerUpdateReq);


}
