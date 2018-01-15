package net.kingsilk.qh.vote.api.voteApp.staff;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.vote.api.UniPage;
import net.kingsilk.qh.vote.api.UniResp;
import net.kingsilk.qh.vote.api.voteApp.staff.dto.StaffAddReq;
import net.kingsilk.qh.vote.api.voteApp.staff.dto.StaffResp;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api
@Path("/voteApp/{voteAppId}/staff")
@Singleton
public interface StaffApi {

    @ApiOperation(
            value = "新增"
    )
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<String> add(
            @ApiParam("voteAppID")
            @PathParam("voteAppId")
                    String voteAppId,
            @BeanParam
                    StaffAddReq staffAddReq

    );

    @ApiOperation(
            value = "修改"
    )
    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<String> update(
            @ApiParam("voteAppID")
            @PathParam("voteAppId")
                    String voteAppId,
            @BeanParam
                    StaffAddReq staffAddReq
    );

    @ApiOperation(
            value = "删除"
    )
    @DELETE
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<Void> delete(
            @ApiParam("voteAppID")
            @PathParam("voteAppId")
                    String voteAppId,
            @ApiParam("id")
            @PathParam("id")
                    String id
    );


    @ApiOperation(
            value = "详情"
    )
    @GET
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<StaffResp> info(
            @ApiParam("voteAppID")
            @PathParam("voteAppId")
                    String voteAppId,
            @ApiParam("id")
            @PathParam("id")
                    String id
    );


    @ApiOperation(
            value = "分页"
    )
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<UniPage<StaffResp>> page(
            @ApiParam("voteAppID")
            @PathParam("voteAppId")
                    String voteAppId,
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
                    List<String> sort
    );
}
