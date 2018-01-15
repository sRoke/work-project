package net.kingsilk.qh.agency.api.brandApp.category;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniPageReq;
import net.kingsilk.qh.agency.api.UniPageResp;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.category.dto.CategoryInfoResp;
import net.kingsilk.qh.agency.api.brandApp.category.dto.CategorySaveReq;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Path("/brandApp/{brandAppId}/category")
@Component
public interface CategoryApi {

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
            @BeanParam UniPageReq uniPageReq
    );


    //--------------------------------商品分类列表,带分页---------------------------------------//
    @ApiOperation(
            value = "商品分类列表,带分页",
            nickname = "商品分类列表,带分页",
            notes = "商品分类列表,带分页"
    )
    @Path("/page")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('CATEGORY_R')")
    UniResp<UniPageResp<CategoryInfoResp>> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @BeanParam UniPageReq uniPageReq
    );

    //--------------------------------保存商品分类---------------------------------------//
    @ApiOperation(
            value = "保存商品分类",
            nickname = "保存商品分类",
            notes = "保存商品分类"
    )

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('CATEGORY_C')")
    UniResp<String> save(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            CategorySaveReq categorySaveReq);

    //--------------------------------更新商品分类---------------------------------------//
    @ApiOperation(
            value = "更新商品分类",
            nickname = "更新商品分类",
            notes = "更新商品分类"
    )
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('CATEGORY_U')")
    UniResp<String> update(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "商品分类ID")
            @PathParam(value = "id") String id,
            CategorySaveReq categorySaveReq);

    //--------------------------------商品分类禁用启用---------------------------------------//
    @ApiOperation(
            value = "商品分类禁用启用",
            nickname = "商品分类禁用启用",
            notes = "商品分类禁用启用"
    )
    @PUT
    @Path("/{id}/enable")
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('CATEGORY_U')")
    UniResp<String> enable(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(required = true, value = "商品分类的ID")
            @QueryParam(value = "id") String id,
            @ApiParam(required = true, value = "false:正常;true:禁用")
            @QueryParam(value = "disabled") boolean disabled);

    //--------------------------------删除商品分类---------------------------------------//
    @ApiOperation(
            value = "删除商品分类",
            nickname = "删除商品分类",
            notes = "删除商品分类"
    )
    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('CATEGORY_D')")
    UniResp<String> delete(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(required = true, value = "商品分类的ID")
            @PathParam(value = "id") String id);

    //--------------------------------获取商品分类---------------------------------------//
    //FIXME 是否使用
//    @ApiOperation(
//            value = "获取商品分类",
//            nickname = "获取商品分类",
//            notes = "获取商品分类"
//    )
//    @Path("/getCategory")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public UniResp<List<CategoryResp>> getCategory();
}
