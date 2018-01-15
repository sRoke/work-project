package net.kingsilk.qh.shop.api.brandApp.shop.repertorySku;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.repertorySku.dto.RepertoryItemPageRep;
import net.kingsilk.qh.shop.api.brandApp.shop.repertorySku.dto.RepertoryItemRep;
import net.kingsilk.qh.shop.api.brandApp.shop.repertorySku.dto.RepertorySkuCreateRep;
import net.kingsilk.qh.shop.api.brandApp.shop.repertorySku.dto.RepertorySkuRep;
import net.kingsilk.qh.shop.api.brandApp.shop.repertorySkuLog.dto.RepertoryLogRep;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api(
        tags = "repertory",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "仓库管理相关API"
)
@Component
@Path("/brandApp/{brandAppId}/shop/{shopId}/repertory/{repertoryId}")
public interface RepertouySkuApi {

    @ApiOperation(
            value = "仓库库存信息",
            nickname = "仓库库存信息",
            notes = "仓库库存信息"
    )
    @Path("/item/{itemId}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<RepertorySkuRep> create(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "仓库ID")
            @PathParam(value = "repertoryId") String repertoryId,
            @ApiParam(value = "itemId")
            @PathParam(value = "itemId") String itemId
    );

    @ApiOperation(
            value = "仓库库存信息",
            nickname = "仓库库存信息",
            notes = "仓库库存信息"
    )
    @Path("/item")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> add(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "仓库ID")
            @PathParam(value = "repertoryId") String repertoryId,
            RepertorySkuCreateRep repertorySkuCreateRep

    );

    @ApiOperation(
            value = "仓库库存信息",
            nickname = "仓库库存信息",
            notes = "仓库库存信息"
    )
    @Path("/item")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> out(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "仓库ID")
            @PathParam(value = "repertoryId") String repertoryId,
            RepertorySkuCreateRep repertorySkuCreateRep
    );

    @ApiOperation(
            value = "草稿",
            nickname = "仓库库存信息",
            notes = "仓库库存信息"
    )
    @Path("/draf")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<RepertoryLogRep> drafInfo(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "仓库ID")
            @PathParam(value = "repertoryId") String repertoryId
    );

    @ApiOperation(
            value = "仓库库存信息",
            nickname = "仓库库存信息",
            notes = "仓库库存信息"
    )
    @Path("/item/{itemId}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<RepertoryItemRep> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "仓库ID")
            @PathParam(value = "repertoryId") String repertoryId,
            @ApiParam(value = "itemId")
            @PathParam(value = "itemId") String itemId
    );


    @ApiOperation(
            value = "仓库库存分页",
            nickname = "门店分页",
            notes = "门店分页"
    )
    @Path("/item")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<RepertoryItemPageRep> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "仓库ID")
            @PathParam(value = "repertoryId") String repertoryId,
            @ApiParam(value = "每页多少个记录,最大100")
            @QueryParam("size")
            @DefaultValue("10")
                    int size,

            @ApiParam(value = "页码。从0开始")
            @QueryParam("page")
            @DefaultValue("0")
                    int page,

            @ApiParam(
                    value = "排序。格式为 '排序字段,排序方向'。其中，排序方向为 'asc'、'desc'。可以传递多个 sort 参数",
                    allowMultiple = true,
                    example = "age,asc"
            )
            @QueryParam("sort")
                    List<String> sort,

            @ApiParam(value = "关键字")
            @QueryParam("keyWord")
                    String keyWord
    );

}
