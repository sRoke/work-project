package net.kingsilk.qh.shop.api.brandApp.shop.refund;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.refund.dto.RefundInfoResp;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api(
        tags = "refund",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "售后订单相关API"
)
@Path("/brandApp/{brandAppId}/shop/{shopId}/refund")
public interface RefundApi {


    @ApiOperation(
            value = "售后订单信息",
            nickname = "售后订单信息",
            notes = "售后信息"
    )
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('REFUND_R')")
    UniResp<RefundInfoResp> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "店铺id")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "售后订单ID")
            @PathParam(value = "id") String id);


    @ApiOperation(
            value = "确认退款订单",
            nickname = "确认退款订单",
            notes = "确认退款订单"
    )
    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('REFUNDMONEY_C')")
    UniResp<String> agreeReturnGoods(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "店铺id")
            @PathParam(value = "shopId") String shopId,
            @PathParam(value = "id") String id,
            @ApiParam(value = "备注")
            @QueryParam(value = "memo") String memo);

    @ApiOperation(
            value = "确认收货并同意退款|同意退款",
            nickname = "确认收货并同意退款|同意退款",
            notes = "确认收货并同意退款|同意退款"
    )
    @Path("/{id}/agreeRefund")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('REFUND_C')")
    UniResp<String> agreeRefund(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "店铺id")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "售后订单ID")
            @PathParam(value = "id") String id);


    @ApiOperation(
            value = "拒绝买家的退货|退款申请",
            nickname = "拒绝买家的退货|退款申请",
            notes = "拒绝买家的退货|退款申请"
    )
    @Path("/{id}/reject")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('REFUND_J')")
    UniResp<String> reject(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "店铺id")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "售后订单ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "拒绝退款原因")
            @QueryParam(value = "rejectReason") String rejectReason);

    @ApiOperation(
            value = "售后分页信息",
            nickname = "售后分页信息",
            notes = "售后分页信息"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('REFUND_R')")
    UniResp<UniPageResp<RefundInfoResp>> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "店铺id")
            @PathParam(value = "shopId") String shopId,

            @ApiParam("每页多少个记录,最大100")
            @QueryParam("size")
            @DefaultValue("10")
                    int size,

            @ApiParam("页码。从0开始")

            @QueryParam("page")
            @DefaultValue("0")
                    int page,

            @ApiParam(
                    value = "排序。格式为 '排序字段,排序方向'。其中，排序方向为 'asc'、'desc'。可以传递多个 sort 参数",
                    example = "age,asc"
            )
            @QueryParam("sort")
                    List<String> sort,

            @ApiParam(value = "售后订单类型")
            @QueryParam(value = "status")
                    List<String> status,
            @ApiParam(value = "关键字")
            @QueryParam(value = "keyWord")
                    String keyWord

    );

}
