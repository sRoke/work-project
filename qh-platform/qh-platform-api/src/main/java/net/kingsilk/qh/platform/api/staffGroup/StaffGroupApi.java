package net.kingsilk.qh.platform.api.staffGroup;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.platform.api.UniPageResp;
import net.kingsilk.qh.platform.api.UniResp;
import net.kingsilk.qh.platform.api.staffGroup.dto.*;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Set;

/**
 *
 */
@Api(
        tags = "staffGroup",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "角色管理相关API"
)
@Path("/staffGroup")
@Component
public interface StaffGroupApi {
    //-------------------------当前用户的权限信息---------------------------------//
    @ApiOperation(
            value = "当前用户的权限信息",
            nickname = "当前用户的权限信息",
            notes = "当前用户的权限信息"
    )
    @Path("/{id}/currentUserInfo")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<StaffGroupLoadCurrentUserResp> currentUserInfo(
            @ApiParam(value = "当前员工ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "来源类型")
            @QueryParam(value = "source") String source);

    //-------------------------当前正在编辑用户的权限信息---------------------------------//
    @ApiOperation(
            value = "当前正在编辑用户的权限信息",
            nickname = "当前正在编辑用户的权限信息",
            notes = "当前正在编辑用户的权限信息"
    )
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<StaffGroupInfoResp> info(
            @ApiParam(value = "当前员工ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "来源类型")
            @QueryParam(value = "source") String source);

    //-------------------------保存角色信息---------------------------------//
    @ApiOperation(
            value = "保存角色信息",
            nickname = "保存角色信息",
            notes = "保存角色信息"
    )
    @Path("")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> save(
            StaffGroupSaveReq staffGroupSaveReq);

    //-------------------------更新角色信息---------------------------------//
    @ApiOperation(
            value = "更新角色信息",
            nickname = "更新角色信息",
            notes = "更新角色信息"
    )
    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> update(
            @ApiParam(value = "当前员工ID")
            @PathParam(value = "id") String id,
            StaffGroupSaveReq staffGroupSaveReq);

    //-------------------------角色分页信息---------------------------------//
    @ApiOperation(
            value = "角色分页信息",
            nickname = "角色分页信息",
            notes = "角色分页信息"
    )
    @Path("")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<UniPageResp<StaffGroupPageResp>> page(
            @ApiParam(value = "渠道商ID")
            @BeanParam StaffGroupPageReq staffGroupPageReq);

    //-------------------------加载角色信息---------------------------------//
    //FIXME 具体设计重新考量
    @ApiOperation(
            value = "加载角色信息",
            nickname = "加载角色信息",
            notes = "加载角色信息，如果来源于新建页面，则staffId随便传source传入页面来源类型，如果来自编辑页面，则需要调用该方法两次"
    )
    @Path("/load")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<StaffGroupLoadResp> load();

    //-------------------------删除角色---------------------------------//
    @ApiOperation(
            value = "删除角色",
            nickname = "删除角色",
            notes = "删除角色"
    )
    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> delete(
            @ApiParam(value = "角色ID")
            @PathParam(value = "id") String id);


//    @ApiOperation(
//            value = "员工组信息",
//            nickname = "员工组信息",
//            notes = "员工组信息"
//    )
//    @Path("/search")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp search(@PathParam(value = "keywords") String keywords);


//-------------------------根据当前登录用户,获取当前登录人的权限列表---------------------------------//

    /**
     * 根据当前登录用户,获取当前登录人的权限列表
     */
    @ApiOperation(
            value = "根据当前登录用户,获取当前登录人的权限列表",
            nickname = "根据当前登录用户,获取当前登录人的权限列表",
            notes = "根据当前登录用户,获取当前登录人的权限列表"
    )
    @Path("/currentAuth")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Set<String> currentAuth(
    );


    @ApiOperation(
            value = "禁用或启用角色",
            nickname = "禁用或启用角色",
            notes = "禁用或启用角色"
    )
    @Path("/{id}/enable")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> enable(
            @ApiParam(value = "角色ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "状态")
            @QueryParam(value = "status") String status);


}
