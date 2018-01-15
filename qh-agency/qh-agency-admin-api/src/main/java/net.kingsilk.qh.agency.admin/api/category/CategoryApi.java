package net.kingsilk.qh.agency.admin.api.category;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.admin.api.UniResp;
import net.kingsilk.qh.agency.admin.api.category.dto.CategorySaveReq;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by lit on 17/7/24.
 */

@Api(
        tags = "category",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "会员相关API"
)
@Path("/category")
@Component
public interface CategoryApi {
    @ApiOperation(
            value = "商品分类列表",
            nickname = "商品分类列表",
            notes = "商品分类列表"
    )
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> list();


    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "保存或更新商品分类",
            nickname = "保存或更新商品分类",
            notes = "保存或更新商品分类"
    )
    UniResp<String> save(@RequestBody CategorySaveReq categorySaveReq);

    @ApiOperation(
            value = "商品分类禁用启用",
            nickname = "商品分类禁用启用",
            notes = "商品分类禁用启用"
    )
    @Path("/enable")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> enable(
            @ApiParam(required = true, value = "商品分类的ID")
            @QueryParam(value = "id") String id,
            @ApiParam(required = true, value = "false:正常;true:禁用")
            @QueryParam(value = "disabled") boolean disabled);

    @ApiOperation(
            value = "删除商品分类",
            nickname = "删除商品分类",
            notes = "删除商品分类"
    )
    @Path("/delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> delete(@ApiParam(required = true, value = "商品分类的ID")
                           @QueryParam(value = "id") String id,
                           @ApiParam(required = true, value = "false:正常;true:禁用")
                           @QueryParam(value = "deleted") boolean deleted);
}
