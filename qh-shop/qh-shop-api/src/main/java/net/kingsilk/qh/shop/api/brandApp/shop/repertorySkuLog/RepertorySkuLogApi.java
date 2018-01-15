package net.kingsilk.qh.shop.api.brandApp.shop.repertorySkuLog;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.repertorySkuLog.dto.RepertoryLogRep;
import net.kingsilk.qh.shop.api.brandApp.shop.repertorySkuLog.dto.RepertorySkuLogRep;
import net.kingsilk.qh.shop.api.brandApp.shop.repertorySkuLog.dto.RepertoryLogResp;
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
@Path("/brandApp/{brandAppId}/shop/{shopId}/repertory/{repertoryId}/skuLog")
public interface RepertorySkuLogApi {

    @ApiOperation(
            value = "草稿",
            nickname = "仓库库存信息",
            notes = "仓库库存信息"
    )
    @Path("/{skuLogId}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<RepertoryLogRep> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "仓库ID")
            @PathParam(value = "repertoryId") String repertoryId,
            @ApiParam(value = "仓库ID")
            @PathParam(value = "skuLogId") String skuLogId
    );

    @ApiOperation(
            value = "更新仓库",
            nickname = "更新仓库",
            notes = "更新仓库"
    )
    @Path("/{skuLogId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> update(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "仓库ID")
            @PathParam(value = "repertoryId") String repertoryId,
            @ApiParam(value = "仓库ID")
            @PathParam(value = "skuLogId") String skuLogId,
            RepertoryLogResp repertoryLogResp
    );

    @ApiOperation(
            value = "关闭仓库",
            nickname = "关闭仓库",
            notes = "关闭仓库"
    )
    @Path("/{skuLogId}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> delet(@ApiParam(value = "品牌商ID")
                          @PathParam(value = "brandAppId") String brandAppId,
                          @ApiParam(value = "门店ID")
                          @PathParam(value = "shopId") String shopId,
                          @ApiParam(value = "仓库ID")
                          @PathParam(value = "repertoryId") String repertoryId,
                          @ApiParam(value = "仓库ID")
                          @PathParam(value = "skuLogId") String skuLogId
    );

//    @ApiOperation(
//            value = "仓库库存分页",
//            nickname = "门店分页",
//            notes = "门店分页"
//    )
//    @Path("")
//    @GET
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<UniPageResp<RepertorySkuLogRep>> page(
//            @ApiParam(value = "品牌商ID")
//            @PathParam(value = "brandAppId") String brandAppId,
//            @ApiParam(value = "门店ID")
//            @PathParam(value = "shopId") String shopId,
//            @ApiParam(value = "仓库ID")
//            @PathParam(value = "repertoryId") String repertoryId,
//            @ApiParam(value = "每页多少个记录,最大100")
//            @QueryParam("size")
//            @DefaultValue("10")
//                    int size,
//
//            @ApiParam(value = "页码。从0开始")
//            @QueryParam("page")
//            @DefaultValue("0")
//                    int page,
//
//            @ApiParam(
//                    value = "排序。格式为 '排序字段,排序方向'。其中，排序方向为 'asc'、'desc'。可以传递多个 sort 参数",
//                    allowMultiple = true,
//                    example = "age,asc"
//            )
//            @QueryParam("sort")
//                    List<String> sort,
//
//            @ApiParam(value = "关键字")
//            @QueryParam("keyWord")
//                    String keyWord
//    );

}
