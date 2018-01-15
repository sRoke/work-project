package net.kingsilk.qh.shop.api.brandApp.shop.category;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniPageReq;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.category.dto.CategoryInfoResp;
import net.kingsilk.qh.shop.api.brandApp.shop.category.dto.CategorySaveReq;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 *
 */
@Api(
        tags = "category",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "商品分类相关API"
)
@Path("/brandApp/{brandAppId}/shop/{shopId}/category")
@Component
public interface CategoryApi {
    //--------------------------------商品分类列表,不带分页---------------------------------------//
    @ApiOperation(
            value = "商品分类列表",
            nickname = "商品分类列表",
            notes = "商品分类列表"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<List<CategoryInfoResp>> list(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId
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
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('CATEGORY_R')")  //todo
    UniResp<UniPageResp<CategoryInfoResp>> page(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
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
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('CATEGORY_C')")   //todo
    UniResp<String> save(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
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
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('CATEGORY_U')")   //todo
    UniResp<String> update(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
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
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('CATEGORY_U')")  //todo
    UniResp<String> enable(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(required = true, value = "商品分类的ID")
            @QueryParam(value = "id") String id,
            @ApiParam(required = true, value = "false:禁用;true:正常")
            @QueryParam(value = "disabled") boolean enable);

    //--------------------------------删除商品分类---------------------------------------//
    @ApiOperation(
            value = "删除商品分类",
            nickname = "删除商品分类",
            notes = "删除商品分类"
    )
    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('CATEGORY_D')")  //todo
    UniResp<String> delete(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(required = true, value = "商品分类的ID")
            @PathParam(value = "id") String id);




    //--------------------------------单个商品分类信息-------------------------------------//
    @ApiOperation(
            value = "单个商品分类信息",
            nickname = "单个商品分类信息",
            notes = "单个商品分类信息"
    )
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('CATEGORY_D')") //todo
    UniResp<CategoryInfoResp> info(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(required = true, value = "商品分类的ID")
            @PathParam(value = "id") String id);

}
