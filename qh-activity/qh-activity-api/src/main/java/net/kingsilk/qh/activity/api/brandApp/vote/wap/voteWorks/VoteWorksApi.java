package net.kingsilk.qh.activity.api.brandApp.vote.wap.voteWorks;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.activity.api.UniPage;
import net.kingsilk.qh.activity.api.UniResp;
import net.kingsilk.qh.activity.api.brandApp.vote.voteWorks.dto.SignupWorkResp;
import net.kingsilk.qh.activity.api.brandApp.vote.voteWorks.dto.VoteShareResp;
import net.kingsilk.qh.activity.api.brandApp.vote.voteWorks.dto.VoteWorksResp;
import net.kingsilk.qh.activity.api.brandApp.vote.voteWorks.dto.VoteworkReq;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api
@Path("/brandApp/{brandAppId}/activity/{activityId}/vote/wap/voteWorks")
public interface VoteWorksApi {

    /**
     * 我要报名
     */
    @ApiOperation(
            value = "我要报名"
    )
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated()")
    UniResp<String> join(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam("活动id")
            @PathParam("activityId")
                    String activityId,
            VoteworkReq voteworkReq
    );

    /**
     * 判断当前用户是否报名
     */
    @ApiOperation(
            value = "判断当前用户是否报名"
    )
    @GET
    @Path("/s/isSignup")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated()")
    UniResp<SignupWorkResp> isSignup(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam("活动id")
            @PathParam("activityId")
                    String activityId
    );

    /**
     * 报名详情页分享
     */
    @ApiOperation(
            value = "报名详情页分享"
    )
    @GET
    @Path("/{id}/shareInfo")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("permitAll()")
    UniResp<VoteShareResp> shareInfo(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam("活动id")
            @PathParam("activityId")
                    String activityId,
            @ApiParam(value = "id")
            @PathParam(value = "id")
                    String id
    );


    /***
     * 报名信息分页
     */
    @ApiOperation(
            value = "分页"
    )
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("permitAll()")
    UniResp<UniPage<VoteWorksResp>> page(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam("活动id")
            @PathParam("activityId")
                    String activityId,

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

            @ApiParam(value = "关键字")
            @QueryParam("keyWord")
                    String keyWord
    );


    /***
     * 报名详情
     */
    @ApiOperation(
            value = "报名详情"
    )
    @GET
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("permitAll()")
    UniResp<VoteWorksResp> info(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam("活动id")
            @PathParam("activityId")
                    String activityId,
            @ApiParam(value = "id")
            @PathParam(value = "id")
                    String id
    );


}
