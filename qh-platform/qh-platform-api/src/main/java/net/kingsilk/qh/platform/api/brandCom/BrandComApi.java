package net.kingsilk.qh.platform.api.brandCom;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.platform.api.UniPage;
import net.kingsilk.qh.platform.api.UniResp;
import net.kingsilk.qh.platform.api.brandCom.dto.BrandComAddReq;
import net.kingsilk.qh.platform.api.brandCom.dto.BrandComGetResp;
import net.kingsilk.qh.platform.api.brandCom.dto.BrandComUpdateReq;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * 品牌商相关API。
 */
@Api
@Path("/brandCom")
@Singleton
public interface BrandComApi {

    /**
     * 新增品牌商
     */
    @ApiOperation(
            value = "新增",
            notes = "限后台应用直接调用"
    )
    @POST
    @Path("/add")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
//    @PreAuthorize("#oauth2.clientHasAnyRole('SERVER')")
    UniResp<String> add(
            BrandComAddReq brandComAddReq
    );

    /**
     * 逻辑删除。
     */
    @ApiOperation(
            value = "删除指定的品牌商"
    )
    @DELETE
    @Path("/{brandComId}")
    @Produces({MediaType.APPLICATION_JSON})
//    @PreAuthorize("#oauth2.clientHasAnyRole('SERVER')")
    UniResp<Void> del(

            @ApiParam("品牌商ID")
            @PathParam("brandComId")
                    String brandComId
    );

    @ApiOperation(
            value = "更新品牌商信息"
    )
    @PUT
    @Path("/{brandComId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
//    @PreAuthorize("#oauth2.clientHasAnyRole('SERVER')")
    UniResp<Void> update(

            @ApiParam("品牌商ID")
            @PathParam("brandComId")
                    String brandComId,

            BrandComUpdateReq brandComUpdateReq
    );


    @ApiOperation(
            value = "获取单个品牌商信息"
    )
    @GET
    @Path("/{brandComId}")
    @Produces({MediaType.APPLICATION_JSON})
//    @PreAuthorize("#oauth2.clientHasAnyRole('SERVER')")
    UniResp<BrandComGetResp> get(

            @ApiParam("品牌商ID")
            @PathParam("brandComId")
                    String brandComId
    );


    @ApiOperation(
            value = "获取多个品牌商信息"
    )
    @GET
    @Produces({MediaType.APPLICATION_JSON})
//    @PreAuthorize("#oauth2.clientHasAnyRole('SERVER')")
    UniResp<UniPage<BrandComGetResp>> list(

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

            @ApiParam(value = "品牌商ID", allowMultiple = true)
            @QueryParam("brandComIds")
                    List<String> brandComIds

    );


    @ApiOperation(
            value = "查询品牌商信息"
    )
    @GET
    @Path("/search")
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<UniPage<BrandComGetResp>> search(

            @ApiParam(value = "每页多少个记录,最大100")
            @QueryParam("size")
            @DefaultValue("10")
                    Integer size,

            @ApiParam(value = "页码。从0开始")
            @QueryParam("page")
            @DefaultValue("0")
                    Integer page,

            @ApiParam(
                    value = "排序。格式为 '排序字段,排序方向'。其中，排序方向为 'asc'、 '+'、'desc'、'-'。可以传递多个 sort 参数",
                    allowMultiple = true,
                    example = "age,asc"
            )
            @QueryParam("sort")
                    List<String> sort,


            @ApiParam(value = "查询语句")
            @QueryParam("q")
                    String q

    );

    @ApiOperation(
            value = "查询品牌商信息"
    )
    @GET
    @Path("/getBrandAppList")
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<List<Map<String,String>>> getBrandAppList(
            @ApiParam(value = "当前品牌商AppId")
            @QueryParam("brandAppId")
                    String brandAppId
    );


}
