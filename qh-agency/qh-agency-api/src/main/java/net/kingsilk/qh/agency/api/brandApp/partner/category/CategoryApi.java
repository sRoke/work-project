package net.kingsilk.qh.agency.api.brandApp.partner.category;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniPageReq;
import net.kingsilk.qh.agency.api.UniPageResp;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.category.dto.CategoryInfoResp;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 *
 */

@Api(
        tags = "category",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "会员相关API"
)
@Path("/brandApp/{brandAppId}/partner/{partnerId}/category")
@Component
public interface CategoryApi {


    //--------------------------------前端商品分类列表---------------------------------------//
    @ApiOperation(
            value = "前端商品分类列表",
            nickname = "前端商品分类列表",
            notes = "前端商品分类列表"
    )
    @Path("/getCategoryList")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<UniPageResp<CategoryInfoResp>> getCategoryList(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @BeanParam UniPageReq uniPageReq
    );
    //--------------------------------后台商品分类列表---------------------------------------//
    @ApiOperation(
            value = "后台商品分类列表",
            nickname = "后台商品分类列表",
            notes = "后台商品分类列表"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<UniPageResp<CategoryInfoResp>> list(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @BeanParam UniPageReq uniPageReq
    );
}
