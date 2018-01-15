package net.kingsilk.qh.shop.api.brandApp.shop.withdraw;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.withdraw.dto.AliRep;
import net.kingsilk.qh.shop.api.brandApp.shop.withdraw.dto.WithdrawInfo;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Api(
        tags = "shop",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "门店管理相关API"
)
@Singleton
@Path("/brandApp/{brandAppId}/shop/{shopId}/withdraw")
public interface WithDrawApi {
    @ApiOperation(
            value = "提现申请",
            nickname = "提现申请",
            notes = "提现申请"
    )
    @PUT
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> apply(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "shopId") String shopId,
            @QueryParam(value = "applyAmount") Integer applyAmount
    );

    @ApiOperation(
            value = "提现明细",
            nickname = "提现明细",
            notes = "提现明细"
    )
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<WithdrawInfo> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "shopId") String shopId
    );

    @ApiOperation(
            value = "设置支付宝信息",
            nickname = "设置支付宝信息",
            notes = "设置支付宝信息"
    )
    @POST
    @Path("/ali")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    UniResp<String> setAli(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "shopId") String shopId,
            AliRep aliRep

    );

    @ApiOperation(
            value = "验证短信",
            nickname = "验证短信",
            notes = "验证短信"
    )
    @PUT
    @Path("/checkAli")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> checkAli(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "短信验证码")
            @QueryParam("code")
                    String code
    );

    @ApiOperation(
            value = "支付宝信息",
            nickname = "支付宝信息",
            notes = "支付宝信息"
    )
    @GET
    @Path("/ali")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<AliRep> aliInfo(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "shopId") String shopId
    );

    @ApiOperation(
            value = "解绑支付宝",
            nickname = "解绑支付宝",
            notes = "解绑支付宝"
    )
    @PUT
    @Path("/binding")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> bandingAli(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "shopId") String shopId,
            @QueryParam("binding") Boolean binding
    );

    @ApiOperation(
            value = "删除支付宝",
            nickname = "删除支付宝",
            notes = "删除支付宝"
    )
    @DELETE
    @Path("/ali")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> deletAli(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "shopId") String shopId
    );

}
