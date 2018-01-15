package net.kingsilk.qh.shop.api.brandApp.shop;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.dto.ShopCreateRep;
import net.kingsilk.qh.shop.api.brandApp.shop.dto.ShopResp;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Api(
        tags = "shop",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "门店管理相关API"
)
@Component
@Path("/brandApp/{brandAppId}/shop")
public interface ShopApi {

    @ApiOperation(
            value = "创建门店",
            nickname = "创建门店",
            notes = "创建门店"
    )
    @Path("")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> create(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            ShopCreateRep shopCreateRep
    );

    @ApiOperation(
            value = "门店详情",
            nickname = "门店详情",
            notes = "门店详情"
    )
    @Path("/{shopId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<ShopResp> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId
    );

    @ApiOperation(
            value = "关闭门店",
            nickname = "关闭门店",
            notes = "关闭门店"
    )
    @Path("/{shopId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> delet(@ApiParam(value = "品牌商ID")
                          @PathParam(value = "brandAppId") String brandAppId,
                          @ApiParam(value = "门店ID")
                          @PathParam(value = "shopId") String shopId
    );

    @ApiOperation(
            value = "是否启用",
            nickname = "是否启用",
            notes = "是否启用"
    )
    @Path("/{shopId}/enable")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> enable(@ApiParam(value = "品牌商ID")
                           @PathParam(value = "brandAppId") String brandAppId,
                           @ApiParam(value = "门店ID")
                           @PathParam(value = "shopId") String shopId,
                           @ApiParam(value = "是否启用")
                           @QueryParam(value = "enable")
                                   Boolean enable
    );


    @ApiOperation(
            value = "更新门店",
            nickname = "更新门店",
            notes = "更新门店"
    )
    @PUT
    @Path("/{shopId}")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> update(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,

            ShopCreateRep shopCreateRep
    );


    @ApiOperation(
            value = "门店分页",
            nickname = "门店分页",
            notes = "门店分页"
    )
    @Path("")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<UniPageResp<ShopResp>> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
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

    @ApiOperation(
            value = "门店详情",
            nickname = "门店详情",
            notes = "门店详情"
    )
    @Path("/getShopList")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<List<Map<String, String>>> getShopList(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "用户ID")
            @QueryParam(value = "userId") String userId
    );

    @ApiOperation(
            value = "门店详情",
            nickname = "门店详情",
            notes = "门店详情"
    )
    @Path("/{shopId}/getNum")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Map<String, Long>> getShopOrderNum(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "shopId") String shopId
    );
}
