package net.kingsilk.qh.activity.api.brandApp.vote.wap.voteRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.activity.api.UniPage;
import net.kingsilk.qh.activity.api.UniResp;
import net.kingsilk.qh.activity.api.brandApp.vote.voteRecord.dto.RecordInfoResp;
import net.kingsilk.qh.activity.api.brandApp.vote.voteRecord.dto.VoteNotifyInfo;
import net.kingsilk.qh.activity.api.brandApp.vote.voteRecord.dto.VoteRecordResp;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api
@Path("/brandApp/{brandAppId}/activity/{activityId}/vote/wap/voteRecord")
public interface VoteRecordApi {

    /***
     * 投票
     */
    @ApiOperation(
            value = "投票"
    )
    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated()")
    UniResp<String> vote(
            @ApiParam("品牌商ID")
            @PathParam("brandAppId")
                    String brandAppId,
            @ApiParam("活动id")
            @PathParam("activityId")
                    String activityId,
            @ApiParam("作品")
            @PathParam("id")
                    String id,
            @ApiParam("微信头像相关")
            @QueryParam("wxComAppId")
                    String wxComAppId,
            @ApiParam("微信头像相关")
            @QueryParam("wxMpAppId")
                    String wxMpAppId,
            @ApiParam("用户id")
            @QueryParam("curUserId")
                    String curUserId
    );


    /***
     * 查看给我投票的人(亲友团)
     */
    @ApiOperation(
            value = "查看给我投票的人"
    )
    @GET
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("permitAll()")
    UniResp<UniPage<VoteRecordResp>> get(
            @ApiParam("品牌商ID")
            @PathParam("brandAppId")
                    String brandAppId,
            @ApiParam("活动id")
            @PathParam("activityId")
                    String activityId,
            @ApiParam("作品")
            @PathParam("id")
                    String id,
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


    /**
     * 获取当前用户已经投了多少票等信息
     */
    @ApiOperation(
            value = "获取当前用户已经投了多少票等信息"
    )
    @GET
    @Path("/{id}/voteNum")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated()")
    UniResp<RecordInfoResp> voteNum(
            @ApiParam("品牌商ID")
            @PathParam("brandAppId")
                    String brandAppId,
            @ApiParam("活动id")
            @PathParam("activityId")
                    String activityId,
            @ApiParam("作品id")
            @PathParam("id")
                    String id
    );


    /**
     * 投票成功后回调
     */
    @ApiOperation(
            value = "投票成功后回调"
    )
    @GET
    @Path("/{id}/notify")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated()")
    UniResp<VoteNotifyInfo> notify(
            @ApiParam("品牌商ID")
            @PathParam("brandAppId")
                    String brandAppId,
            @ApiParam("活动id")
            @PathParam("activityId")
                    String activityId,
            @ApiParam("作品id")
            @PathParam("id")
                    String id,
            @ApiParam("投票id")
            @QueryParam("voteId")
                    String voteId

    );



}
