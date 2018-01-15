package net.kingsilk.qh.shop.api.brandApp.shop.shopStaffGroup;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.shopStaffGroup.dto.ShopStaffGroupInfoResp;
import net.kingsilk.qh.shop.api.brandApp.shop.shopStaffGroup.dto.ShopStaffGroupLoadResp;
import net.kingsilk.qh.shop.api.brandApp.shop.shopStaffGroup.dto.ShopStaffGroupSaveReq;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api(
        tags = "staffGroup",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "角色管理相关API"
)
@Path("/brandApp/{brandAppId}/shop/{shopId}/shopStaffGroup")
@Component
public interface ShopStaffGroupApi {

    @ApiOperation(
            value = "当前正在编辑用户的权限信息",
            nickname = "当前正在编辑用户的权限信息",
            notes = "当前正在编辑用户的权限信息"
    )
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<ShopStaffGroupInfoResp> info(
            @ApiParam(value = "应用id")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店id")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "当前员工ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "来源类型")
            @QueryParam(value = "source") String source);


    @ApiOperation(
            value = "保存角色信息",
            nickname = "保存角色信息",
            notes = "保存角色信息"
    )
    @Path("")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> save(
            @ApiParam(value = "应用id")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店id")
            @PathParam(value = "shopId") String shopId,
            ShopStaffGroupSaveReq shopStaffGroupSaveReq);

    @ApiOperation(
            value = "更新角色信息",
            nickname = "更新角色信息",
            notes = "更新角色信息"
    )
    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> update(
            @ApiParam(value = "应用id")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店id")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "当前员工ID")
            @PathParam(value = "id") String id,
            ShopStaffGroupSaveReq shopStaffGroupSaveReq);

    @ApiOperation(
            value = "角色分页信息",
            nickname = "角色分页信息",
            notes = "角色分页信息"
    )
    @Path("")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<UniPageResp<ShopStaffGroupInfoResp>> page(
            @ApiParam(value = "应用id")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店id")
            @PathParam(value = "shopId") String shopId,

            @ApiParam("每页多少个记录,最大100")
            @QueryParam("size")
            @DefaultValue("10")
                    int size,

            @ApiParam("页码。从0开始")

            @QueryParam("page")
            @DefaultValue("0")
                    int page,

            @ApiParam(
                    value = "排序。格式为 '排序字段,排序方向'。其中，排序方向为 'asc'、'desc'。可以传递多个 sort 参数",
                    example = "age,asc"
            )
            @QueryParam("sort")
                    List<String> sort,

            @ApiParam(value = "帐号")
            @QueryParam("keyWord")
                    String keyWord
    );

    @ApiOperation(
            value = "删除角色",
            nickname = "删除角色",
            notes = "删除角色"
    )
    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> delete(
            @ApiParam(value = "应用id")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店id")
            @PathParam(value = "shopId") String shopId,

            @ApiParam(value = "角色ID")
            @PathParam(value = "id") String id);


    @ApiOperation(
            value = "加载角色信息",
            nickname = "加载角色信息",
            notes = "加载角色信息，如果来源于新建页面，则staffId随便传source传入页面来源类型，如果来自编辑页面，则需要调用该方法两次"
    )
    @Path("/load")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<ShopStaffGroupLoadResp> load();
}
