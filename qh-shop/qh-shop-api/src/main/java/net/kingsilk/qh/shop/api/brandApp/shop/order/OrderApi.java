package net.kingsilk.qh.shop.api.brandApp.shop.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.order.dto.OrderInfoResp;
import net.kingsilk.qh.shop.api.brandApp.shop.order.dto.OrderResp;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Api(
        tags = "OrderApi",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "商家订单相关API"
)
@Path("/brandApp/{brandAppId}/shop/{shopId}/order")
public interface OrderApi {


    @ApiOperation(
            value = "订单分页信息",
            nickname = "订单分页信息",
            notes = "订单分页信息"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ORDER_R')")
    UniResp<UniPageResp<OrderResp>> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "店铺id")
            @PathParam(value = "shopId")
                    String shopId,
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

            @ApiParam(value = "订单状态")
            @QueryParam("status")
                    List<String> status,

            @ApiParam(value = "帐号")
            @QueryParam(value = "keyWord")
                    String keyWord);


    @ApiOperation(
            value = "订单信息",
            nickname = "订单信息",
            notes = "订单信息")
    @ApiParam(value = "id")
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ORDER_R')")
    UniResp<OrderInfoResp> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "店铺id")
            @PathParam(value = "shopId")
                    String shopId,
            @ApiParam(value = "订单ID")
            @PathParam(value = "id")
                    String id);


    @ApiOperation(
            value = "更改价格",
            nickname = "更改价格",
            notes = "更改价格"
    )
    @Path("/{id}/sku/{skuId}/adjustPrice")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ORDER_C')")
    UniResp<String> adjustPrice(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "店铺id")
            @PathParam(value = "shopId")
                    String shopId,
            @ApiParam(value = "订单ID")
            @PathParam(value = "id")
                    String id,
            @ApiParam(value = "skuId")
            @PathParam(value = "skuId")
                    String skuId,
            @ApiParam(value = "调整后价格")
            @QueryParam(value = "adjustPrice")
                    Integer adjustPrice
    );


    /**
     * 确认接单后会生成相应的发货单
     */
    @ApiOperation(
            value = "确认接单",
            nickname = "确认接单",
            notes = "确认接单"
    )
    @Path("/{id}/confirmOrder")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ORDER_Y')")
    UniResp<String> confirmOrder(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "店铺id")
            @PathParam(value = "shopId")
                    String shopId,
            @ApiParam(value = "订单ID")
            @PathParam(value = "id") String id);


    /**
     * 确认接单后会生成相应的发货单
     */
    @ApiOperation(
            value = "拒绝接单",
            nickname = "拒绝接单",
            notes = "拒绝接单"
    )
    @Path("/{id}/rejectOrder")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ORDER_Y')")
    UniResp<String> rejectOrder(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "店铺id")
            @PathParam(value = "shopId")
                    String shopId,
            @ApiParam(value = "订单ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "备注")
            @QueryParam(value = "memo")
                    String memo);


    @ApiOperation(
            value = "获取物流公司列表",
            nickname = "获取物流公司列表",
            notes = "获取物流公司列表"
    )
    @Path("/getLogisticsCompanyEnum")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Map<String, String>> getLogisticsCompanyEnum(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "店铺id")
            @PathParam(value = "shopId")
                    String shopId);

}
