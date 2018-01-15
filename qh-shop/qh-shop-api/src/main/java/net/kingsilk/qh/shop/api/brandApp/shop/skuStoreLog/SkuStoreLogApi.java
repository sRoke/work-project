package net.kingsilk.qh.shop.api.brandApp.shop.skuStoreLog;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.brandApp.shop.skuStoreLog.dto.SkuStoreLogResp;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api(
        tags = "Stock",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "出入库相关管理"
)
@Component
@Path("/brandApp/{brandAppId}/shop/{shopId}/sku")
public interface SkuStoreLogApi {

    @ApiOperation(
            value = "详情",
            nickname = "详情",
            notes = "详情"
    )
    @Path("{skuStoreLogId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<SkuStoreLogResp> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "库存记录id")
            @PathParam(value = "skuStoreLogId") String skuStoreLogId
    );

    @ApiOperation(
            value = "分页",
            nickname = "分页",
            notes = "分页"
    )
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<UniPageResp<SkuStoreLogResp>> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,

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
