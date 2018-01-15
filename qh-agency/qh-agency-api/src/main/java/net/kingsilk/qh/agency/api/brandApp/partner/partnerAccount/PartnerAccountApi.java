package net.kingsilk.qh.agency.api.brandApp.partner.partnerAccount;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniPageResp;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.partnerAccount.dto.PaLogResp;
import net.kingsilk.qh.agency.api.brandApp.partnerAccount.dto.PartnerAccountInfoResp;
import net.kingsilk.qh.agency.api.brandApp.partnerAccount.dto.PartnerAccountLogReq;
import net.kingsilk.qh.agency.api.brandApp.partnerAccount.dto.PartnerAccountLogResp;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 *
 */
@Api(
        tags = "account",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "账户管理相关API"
)
@Path("/brandApp/{brandAppId}/partner/{partnerId}/partnerAccount")
public interface PartnerAccountApi {

    //----------------------------账户信息-------------------------------//
    @ApiOperation(
            value = "账户信息",
            nickname = "账户信息",
            notes = "账户信息")
    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<PartnerAccountInfoResp> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId
    );

//    @ApiOperation(
//            value = "重置支付密码",
//            nickname = "重置支付密码",
//            notes = "重置支付密码")
//    @PUT
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<String> resetPayPassword(
//            @ApiParam(value = "品牌商ID")
//            @PathParam(value = "brandAppId") String brandAppId,
//            @ApiParam(value = "渠道商ID")
//            @PathParam(value = "partnerId") String partnerId,
//            @ApiParam(value = "老密码")
//            @QueryParam(value = "oldPassword") String oldPassword,
//            @ApiParam(value = "新密码")
//            @QueryParam(value = "newPassword") String newPassword
//    );
//
//    @ApiOperation(
//            value = "判断支付密码是否正确",
//            nickname = "判断支付密码是否正确",
//            notes = "判断支付密码是否正确")
//    @Path("/{id}")
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<String> checkPayPassword(
//            @ApiParam(value = "品牌商ID")
//            @PathParam(value = "brandAppId") String brandAppId,
//            @ApiParam(value = "渠道商ID")
//            @PathParam(value = "partnerId") String partnerId,
//            @ApiParam(value = "订单ID")
//            @PathParam(value = "id") String id,
//            @ApiParam(value = "密码")
//            @QueryParam(value = "password") String password);
//
//    @ApiOperation(
//            value = "判断支付密码是否存在是否过期",
//            nickname = "判断支付密码是否存在是否过期",
//            notes = "判断支付密码是否存在是否过期")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<String> judgePayPassword(
//            @ApiParam(value = "品牌商ID")
//            @PathParam(value = "brandAppId") String brandAppId,
//            @ApiParam(value = "渠道商ID")
//            @PathParam(value = "partnerId") String partnerId);

    @ApiOperation(
            value = "账户明细",
            nickname = "账户明细",
            notes = "账户明细")
    @GET
    @Path("/{id}/sendSms")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> sendSms(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "订单ID")
            @PathParam(value = "id") String id);

    @ApiOperation(
            value = "账户明细",
            nickname = "账户明细",
            notes = "账户明细")
    @GET
    @Path("/{id}/checkSms")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> checkSms(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "订单ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "验证码")
            @QueryParam(value = "code") String code);


    @ApiOperation(
            value = "账户明细",
            nickname = "账户明细",
            notes = "账户明细")
    @GET
    @Path("/log")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<List<PartnerAccountLogResp>> partnerAccountLog(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @BeanParam PartnerAccountLogReq partnerAccountLogReq);


    @ApiOperation(
            value = "时间排序的账户明细ID",
            nickname = "时间排序的账户明细ID",
            notes = "时间排序的账户明细ID")
    @GET
    @Path("/logIds")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<UniPageResp<PaLogResp>> getIds(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @BeanParam PartnerAccountLogReq partnerAccountLogReq);
}
