package net.kingsilk.qh.shop.api.brandApp.staff;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.staff.dto.StaffInfoResp;
import net.kingsilk.qh.shop.api.brandApp.staff.dto.StaffMinInfo;
import net.kingsilk.qh.shop.api.brandApp.staff.dto.StaffSaveReq;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by lit on 17/11/7.
 */
@Api(
        tags = "staff",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "员工管理相关API"
)
@Path("/brandApp/{brandAppId}/staff")
@Component
public interface StaffApi {
    //-------------------------员工信息---------------------------------//
    @ApiOperation(
            value = "员工信息",
            nickname = "员工信息",
            notes = "员工信息"
    )
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF_R')")
    UniResp<StaffInfoResp> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "员工ID")
            @PathParam(value = "id") String id);

    //-------------------------保存员工信息---------------------------------//
    @ApiOperation(
            value = "保存员工信息",
            nickname = "保存员工信息",
            notes = "保存员工信息"
    )
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF_C')")
    UniResp<String> save(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            StaffSaveReq staffSaveReq);

    //-------------------------更新员工信息---------------------------------//
    @ApiOperation(
            value = "更新员工信息",
            nickname = "更新员工信息",
            notes = "更新员工信息"
    )
    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF_U')")
    UniResp<String> update(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "员工ID")
            @PathParam(value = "id") String id,
            StaffSaveReq staffSaveReq);

    //-------------------------员工分页信息---------------------------------//
    @ApiOperation(
            value = "员工分页信息",
            nickname = "员工分页信息",
            notes = "员工分页信息"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF_R')")
    UniResp<UniPageResp<StaffMinInfo>> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
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

            @ApiParam(value = "帐号")
            @QueryParam(value = "keyWord")
                    String keyWord
    );

    //-------------------------禁用或启用员工---------------------------------//
    @ApiOperation(
            value = "禁用或启用员工",
            nickname = "禁用或启用员工",
            notes = "禁用或启用员工"
    )
    @Path("/{id}/enable")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF_U')")
    UniResp<String> enable(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "员工ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "员工状态")
            @QueryParam(value = "status") boolean status);

}
