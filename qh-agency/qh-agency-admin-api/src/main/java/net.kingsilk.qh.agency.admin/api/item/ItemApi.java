package net.kingsilk.qh.agency.admin.api.item;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.kingsilk.qh.agency.admin.api.UniResp;
import net.kingsilk.qh.agency.admin.api.common.dto.BasePageResp;
import net.kingsilk.qh.agency.admin.api.item.dto.ItemInfoResp;
import net.kingsilk.qh.agency.admin.api.item.dto.ItemMinInfo;
import net.kingsilk.qh.agency.admin.api.item.dto.ItemPageReq;
import net.kingsilk.qh.agency.admin.api.item.dto.ItemSaveReq;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by tpx on 17-3-14.
 * <p>
 * <p>
 * <p>
 * 新增：
 * 1. get /api/category/list  获取 “分类标签列表”
 * 1. TODO  上传图片
 * 1. 查询商品熟悉个 /api/itemProp/search
 * 1. POST /api/sku/add  注意：批量
 */
@Component
@Path("/item")
@Api(
        tags = "item",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "商品相关API"
)
public interface ItemApi {

    @ApiOperation(
            value = "保存商品信息",
            nickname = "保存商品信息",
            notes = "保存商品信息"
    )
    @ResponseBody
    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> save(ItemSaveReq itemSaveReq);

    @ApiOperation(
            value = "商品信息详情",
            nickname = "商品信息详情",
            notes = "商品信息详情"
    )
    @Path("/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<ItemInfoResp> info(@PathParam(value = "id") String id);

    @ApiOperation(
            value = "商品分页信息详情",
            nickname = "商品分页信息详情",
            notes = "商品分页信息详情"
    )
    @Path("/page")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<BasePageResp<ItemMinInfo>> page(@BeanParam ItemPageReq itemPageReq);


    @ApiOperation(
            value = "删除商品",
            nickname = "删除商品",
            notes = "删除商品"
    )
    @Path("/delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<BasePageResp<ItemMinInfo>> delete(@PathParam(value = "id") String id);


    @ApiOperation(
            value = "商品状态更改",
            nickname = "商品状态更改",
            notes = "商品状态更改"
    )
    @ResponseBody
    @Path("/changeStatus")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<BasePageResp<ItemMinInfo>> changeStatus(@PathParam(value = "id") String id, @PathParam(value = "status") String status);


}
