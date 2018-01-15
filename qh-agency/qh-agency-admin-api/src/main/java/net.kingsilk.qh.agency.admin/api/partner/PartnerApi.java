package net.kingsilk.qh.agency.admin.api.partner;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.kingsilk.qh.agency.admin.api.UniResp;
import net.kingsilk.qh.agency.admin.api.partner.dto.PartnerInfoResp;
import net.kingsilk.qh.agency.admin.api.partner.dto.PartnerPageReq;
import net.kingsilk.qh.agency.admin.api.partner.dto.PartnerPageResp;
import net.kingsilk.qh.agency.admin.api.partner.dto.PartnerSaveReq;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 *
 */

@Api(
        tags = "partner",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "员工管理相关API"
)
@Path("/partner")
@Component
public interface PartnerApi {

    //-------------------------查看渠道商信息---------------------------------//
    @ApiOperation(
            value = "渠道商信息",
            nickname = "渠道商信息",
            notes = "渠道商信息"
    )
    @Path("/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<PartnerInfoResp> info(@QueryParam(value = "id") String id);

    //-------------------------保存渠道商信息---------------------------------//
    @ApiOperation(
            value = "保存渠道商信息",
            nickname = "保存渠道商信息",
            notes = "保存渠道商信息"
    )
    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> save(PartnerSaveReq partnerSaveReq);

    //-------------------------更新渠道商信息---------------------------------//
    @ApiOperation(
            value = "更新渠道商信息",
            nickname = "更新渠道商信息",
            notes = "更新渠道商信息"
    )
    @Path("/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> update( PartnerSaveReq partnerSaveReq);

    //-------------------------获取渠道商列表---------------------------------//
    @ApiOperation(
            value = "获取渠道商列表",
            nickname = "获取渠道商列表",
            notes = "获取渠道商列表"
    )
    @Path("/page")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<PartnerPageResp> page(@BeanParam PartnerPageReq partnerPageReq,
                                  @QueryParam(value = "source") String source);

    //-------------------------渠道商审核---------------------------------//
    @ApiOperation(
            value = "渠道商审核",
            nickname = "渠道商审核",
            notes = "渠道商审核"
    )
    @Path("/review")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> review(@QueryParam(value = "id") String id,
                           @QueryParam(value = "status") Boolean status);


    //-------------------------渠道商禁用---------------------------------//
    @ApiOperation(
            value = "渠道商禁用",
            nickname = "渠道商禁用",
            notes = "渠道商禁用"
    )
    @Path("/changeStatus")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> changeStatus(@QueryParam(value = "id") String id,
                                 @QueryParam(value = "status") Boolean status);

}
