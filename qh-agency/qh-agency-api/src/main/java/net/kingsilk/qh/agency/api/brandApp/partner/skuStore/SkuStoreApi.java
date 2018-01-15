package net.kingsilk.qh.agency.api.brandApp.partner.skuStore;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniPageResp;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.common.dto.SkuInfoModel;
import net.kingsilk.qh.agency.api.brandApp.item.dto.ItemMinInfo;
import net.kingsilk.qh.agency.api.brandApp.item.dto.ItemSearchReq;
import net.kingsilk.qh.agency.api.brandApp.partner.skuStore.dto.SkuStoreInfoResp;
import net.kingsilk.qh.agency.api.brandApp.partner.skuStore.dto.SkuStorePageReq;
import net.kingsilk.qh.agency.api.brandApp.partner.skuStore.dto.SkuStorePageResp;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 库存管理
 */
@Api(
        tags = "order",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "库存管理相关API"
)
@Path("/brandApp/{brandAppId}/partner/{partnerId}/skuStore")
public interface SkuStoreApi {
    //----------------------------库存信息-------------------------------//
    @ApiOperation(
            value = "库存信息",
            nickname = "库存信息",
            notes = "库存信息")
    @ApiParam(value = "id")
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<SkuStoreInfoResp> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "库存表ID")
            @PathParam(value = "id") String id);

    //----------------------------库存列表信息-------------------------------//
    @ApiOperation(
            value = "库存列表信息",
            nickname = "库存列表信息",
            notes = "库存列表信息")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<UniPageResp<SkuStorePageResp>> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @BeanParam SkuStorePageReq req);



    //----------------------------搜索商品-------------------------------//
    @ApiOperation(
            value = "搜索商品",
            nickname = "搜索商品",
            notes = "搜索商品"
    )
    @Path("/searchSkuStore")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UniResp<UniPageResp<ItemMinInfo>> searchSkuStore(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @BeanParam ItemSearchReq req);

    //---------------------------根据skuId获取sku信息(client专用)--------------------------//
    @ApiOperation(
            value = "sku信息",
            nickname = "sku信息",
            notes = "sku信息"
    )
    @Path("/skuDetail")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<SkuInfoModel> skuDetail(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "skuId")
            @QueryParam(value = "skuId") String skuId,
            @ApiParam(value = "商品编号")
            @QueryParam(value = "code") String code
    );


}
