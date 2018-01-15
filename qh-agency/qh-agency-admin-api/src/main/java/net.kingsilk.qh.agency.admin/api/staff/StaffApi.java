package net.kingsilk.qh.agency.admin.api.staff;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.kingsilk.qh.agency.admin.api.UniResp;
import net.kingsilk.qh.agency.admin.api.staff.dto.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@Api(
        tags = "Staff",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "员工管理相关API"
)
@Path("/staff")
@Component
public interface StaffApi {
    @ApiOperation(
            value = "员工信息",
            nickname = "员工信息",
            notes = "员工信息"
    )
    @Path("/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<StaffInfoResp> info(@QueryParam(value = "id") String id);

    @ApiOperation(
            value = "保存或更新员工信息",
            nickname = "保存或更新员工信息",
            notes = "保存或更新员工信息"
    )
    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> save(@BeanParam StaffSaveReq staffSaveReq);

    @ApiOperation(
            value = "员工分页信息",
            nickname = "员工分页信息",
            notes = "员工分页信息"
    )
    @Path("/page")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<StaffPageResp> page(@BeanParam StaffPageReq staffPageReq);

    @ApiOperation(
            value = "禁用或启用员工",
            nickname = "禁用或启用员工",
            notes = "禁用或启用员工"
    )
    @Path("/enable")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> enable(@BeanParam StaffEnableReq staffEnableReq);


    @ApiOperation(
            value = "查询手机号是否重复",
            nickname = "查询手机号是否重复",
            notes = "查询手机号是否重复"
    )
    @Path("/queryPhone")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Boolean> queryPhone(@PathParam(value = "phone") String phone,@PathParam(value = "id") String id);


}
