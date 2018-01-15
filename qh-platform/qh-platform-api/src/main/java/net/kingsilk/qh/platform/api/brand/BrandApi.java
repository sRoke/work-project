package net.kingsilk.qh.platform.api.brand;

import io.swagger.annotations.*;
import net.kingsilk.qh.platform.api.*;
import net.kingsilk.qh.platform.api.brand.dto.BrandAddReq;
import net.kingsilk.qh.platform.api.brand.dto.BrandGetResp;
import net.kingsilk.qh.platform.api.brand.dto.BrandUpdateReq;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

/**
 * 品牌商所拥有的品牌的相关信息。
 */
@Api
@Path("/brandApp/{brandAppId}/brand")
@Singleton
@Deprecated
public interface BrandApi {

    /**
     * 新增品牌
     */
    @ApiOperation(
            value = "新增"
    )
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<String> add(
            BrandAddReq brandAddReq
    );

    /**
     * 逻辑删除。
     */
    @ApiOperation(
            value = "删除"
    )
    @DELETE
    @Path("/{bandId}")
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<Void> del(

            @ApiParam("品牌商ID")
            @PathParam("brandComId")
                    String brandComId,

            @ApiParam("品牌ID")
            @PathParam("bandId")
                    String bandId

    );

    @ApiOperation(
            value = "使用手机号，手机短信验证码进行登录"
    )
    @PUT
    @Path("/{bandId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<Void> update(

            @ApiParam("品牌商ID")
            @PathParam("brandComId")
                    String brandComId,

            @ApiParam("品牌ID")
            @PathParam("bandId")
                    String bandId,

            BrandUpdateReq brandUpdateReq
    );


    @ApiOperation(
            value = "获取单个品牌信息"
    )
    @GET
    @Path("/{bandId}")
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<BrandGetResp> get(

            @ApiParam("品牌商ID")
            @PathParam("brandComId")
                    String brandComId,

            @ApiParam("品牌ID")
            @PathParam("bandId")
                    String bandId
    );


    @ApiOperation(
            value = "获取多个品牌商信息"
    )
    @GET
    @Path("")
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<UniPage<BrandGetResp>> list(

            @ApiParam(value = "每页多少个记录,最大100")
            @QueryParam("size")
            @DefaultValue("10")
                    int size,

            @ApiParam(value = "页码。从0开始")
            @QueryParam("page")
            @DefaultValue("0")
                    int page,

            @ApiParam(
                    value = "排序。格式为 '排序字段,排序方向'。其中，排序方向为 'asc'、 '+'、'desc'、'-'。可以传递多个 sort 参数",
                    allowMultiple = true,
                    example = "age,asc"
            )
            @QueryParam("sort")
                    List<String> sort,

            @ApiParam("品牌商ID")
            @PathParam("brandComId")
                    String brandComId,

            @ApiParam(value = "品牌ID", allowMultiple = true)
            @QueryParam("bandIds")
                    List<String> bandIds

    );

}
