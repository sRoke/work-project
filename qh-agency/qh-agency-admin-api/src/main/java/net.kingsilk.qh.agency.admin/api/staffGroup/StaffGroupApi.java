package net.kingsilk.qh.agency.admin.api.staffGroup;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.kingsilk.qh.agency.admin.api.staffGroup.dto.*;
import net.kingsilk.qh.agency.admin.api.UniResp;
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
@Path("/api/staffGroup")
@Component
public interface StaffGroupApi {
    @ApiOperation(
            value = "当前用户的权限信息",
            nickname = "当前用户的权限信息",
            notes = "当前用户的权限信息"
    )
    @Path("/currentUserInfo")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<StaffGroupLoadCurrentUserResp> currentUserInfo(@PathParam(value = "id") String id, @PathParam(value = "source") String source);

    @ApiOperation(
            value = "当前正在编辑用户的权限信息",
            nickname = "当前正在编辑用户的权限信息",
            notes = "当前正在编辑用户的权限信息"
    )
    @Path("/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<StaffGroupInfoResp> info(@PathParam(value = "id") String id, @PathParam(value = "source") String source);

    @ApiOperation(
            value = "保存或更新角色信息",
            nickname = "保存或更新角色信息",
            notes = "保存或更新角色信息"
    )
    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> save(@BeanParam StaffGroupSaveReq staffGroupSaveReq);

    @ApiOperation(
            value = "角色分页信息",
            nickname = "角色分页信息",
            notes = "角色分页信息"
    )
    @Path("/page")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<StaffGroupPageResp> page(@BeanParam StaffGroupPageReq staffGroupPageReq);

    @ApiOperation(
            value = "保存或更新角色信息",
            nickname = "保存或更新角色信息",
            notes = "加载角色信息，如果来源于新建页面，则staffId随便传source传入页面来源类型，如果来自编辑页面，则需要调用该方法两次"
    )
    @Path("/load")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<StaffGroupLoadResp> load();

    @ApiOperation(
            value = "删除角色",
            nickname = "删除角色",
            notes = "删除角色"
    )
    @Path("/delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> delete(@PathParam(value = "id") String id);

    @ApiOperation(
            value = "员工组信息",
            nickname = "员工组信息",
            notes = "员工组信息"
    )
    @Path("/search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp search(@PathParam(value = "keywords") String keywords);

    /**
     * 根据当前登录用户,获取当前登录人的权限列表
     *
     * @return
     */
    @ApiOperation(
            value = "根据当前登录用户,获取当前登录人的权限列表",
            nickname = "根据当前登录用户,获取当前登录人的权限列表",
            notes = "根据当前登录用户,获取当前登录人的权限列表"
    )
    @Path("/currentAuth")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Set<String> currentAuth();

    @ApiOperation(
            value = "禁用或启用角色",
            nickname = "禁用或启用角色",
            notes = "禁用或启用角色"
    )
    @Path("/enable")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> enable(@PathParam(value = "id") String id, @PathParam(value = "status") String status);


}
