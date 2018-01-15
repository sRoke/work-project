package net.kingsilk.qh.shop.api.brandApp.shop.repertory;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.repertory.dto.RepertoryCreateRep;
import net.kingsilk.qh.shop.api.brandApp.shop.repertory.dto.RepertoryResp;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Path("/brandApp/{brandAppId}/shop/{shopId}/repertory")
public interface RepertoryApi {

    @ApiOperation(
            value = "创建仓库",
            nickname = "创建仓库",
            notes = "创建仓库"
    )
    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> create(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            RepertoryCreateRep repertoryCreateRep
    );

    @ApiOperation(
            value = "仓库详情",
            nickname = "仓库详情",
            notes = "仓库详情"
    )
    @Path("/{repertoryId}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<RepertoryResp> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "仓库ID")
            @PathParam(value = "repertoryId") String repertoryId
    );

    @ApiOperation(
            value = "更新仓库",
            nickname = "更新仓库",
            notes = "更新仓库"
    )
    @Path("/{repertoryId}")
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
            RepertoryCreateRep repertoryCreateRep
    );

    @ApiOperation(
            value = "关闭仓库",
            nickname = "关闭仓库",
            notes = "关闭仓库"
    )
    @Path("/{repertoryId}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> delet(@ApiParam(value = "品牌商ID")
                          @PathParam(value = "brandAppId") String brandAppId,
                          @ApiParam(value = "门店ID")
                          @PathParam(value = "shopId") String shopId,
                          @ApiParam(value = "仓库ID")
                          @PathParam(value = "repertoryId") String repertoryId
    );

    @ApiOperation(
            value = "停用启用仓库",
            nickname = "停用启用仓库",
            notes = "停用启用仓库"
    )
    @Path("/{repertoryId}/disable")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> disable(@ApiParam(value = "品牌商ID")
                          @PathParam(value = "brandAppId") String brandAppId,
                          @ApiParam(value = "门店ID")
                          @PathParam(value = "shopId") String shopId,
                          @ApiParam(value = "仓库ID")
                          @PathParam(value = "repertoryId") String repertoryId,
                          @ApiParam(value = "是否启用")
                          @QueryParam(value = "disable") Boolean disable
    );


    @ApiOperation(
            value = "仓库分页",
            nickname = "仓库分页",
            notes = "仓库分页"
    )
    @Path("")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<List<RepertoryResp>> list(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
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
