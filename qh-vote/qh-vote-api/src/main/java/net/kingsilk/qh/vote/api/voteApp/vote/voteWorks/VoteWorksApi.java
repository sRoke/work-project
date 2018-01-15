package net.kingsilk.qh.vote.api.voteApp.vote.voteWorks;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.vote.api.UniPage;
import net.kingsilk.qh.vote.api.UniResp;
import net.kingsilk.qh.vote.api.voteApp.vote.voteWorks.dto.CheckNumResp;
import net.kingsilk.qh.vote.api.voteApp.vote.voteWorks.dto.VoteWorksResp;
import net.kingsilk.qh.vote.api.voteApp.vote.voteWorks.dto.WxSendReq;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Api
@Path("/voteApp/{voteAppId}/vote/{voteActivityId}/vote/voteWorks")
@Singleton
public interface VoteWorksApi {

    /**
     * 审核报名信息
     */
    @ApiOperation(
            value = "审核"
    )
    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<String> check(
            @ApiParam(value = "voteAppId")
            @PathParam(value = "voteAppId")
                    String voteAppId,
            @ApiParam("活动id")
            @PathParam("voteActivityId")
                    String voteActivityId,
            @ApiParam(value = "id")
            @PathParam(value = "id")
                    String id,
            WxSendReq wxSendReq
    );

    /**
     * 审核和未审核数量
     */
    @ApiOperation(
            value = "审核和未审核数量"
    )
    @GET
    @Path("/checkNum")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<CheckNumResp> checkNum(
            @ApiParam(value = "voteAppId")
            @PathParam(value = "voteAppId")
                    String voteAppId,
            @ApiParam("活动id")
            @PathParam("voteActivityId")
                    String voteActivityId);


    /***
     * 报名信息分页
     */
    @ApiOperation(
            value = "分页"
    )
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<UniPage<VoteWorksResp>> page(
            @ApiParam(value = "voteAppId")
            @PathParam(value = "voteAppId")
                    String voteAppId,
            @ApiParam("活动id")
            @PathParam("voteActivityId")
                    String voteActivityId,

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
                    String keyWord,
            @ApiParam(value = "关键字")
            @QueryParam("status")
                    String status
    );


    /**
     * 加票，包括后台加票和正常加票
     */
    @ApiOperation(
            value = "加票"
    )
    @PUT
    @Path("/{id}/addVote")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<String> addVote(
            @ApiParam(value = "voteAppId")
            @PathParam(value = "voteAppId")
                    String voteAppId,
            @ApiParam("活动id")
            @PathParam("voteActivityId")
                    String voteActivityId,
            @ApiParam(value = "作品id")
            @PathParam(value = "id")
                    String id,
            @ApiParam(value = "票数")
            @QueryParam(value = "votes")
                    String votes,
            @ApiParam(value = "类型")
            @QueryParam("sourceType")
                    String sourceType
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
            @ApiParam(value = "voteAppId")
            @PathParam(value = "voteAppId")
                    String voteAppId,
            @ApiParam("活动id")
            @PathParam("voteActivityId")
                    String voteActivityId,
            @ApiParam(value = "id")
            @PathParam(value = "id")
                    String id
    );


    /**
     * 删除
     */
    @ApiOperation(
            value = "报名详情"
    )
    @DELETE
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated()")
    UniResp<Void> delete(
            @ApiParam(value = "voteAppId")
            @PathParam(value = "voteAppId")
                    String voteAppId,
            @ApiParam("活动id")
            @PathParam("voteActivityId")
                    String voteActivityId,
            @ApiParam(value = "id")
            @PathParam(value = "id")
                    String id
    );


    /**
     * 根据搜索的结果 导出Excel.xlsx
     */
    @ApiOperation(
            value = "作品的搜索结果导出Excel.xlsx"
    )
    @GET
    @Path("/toGridfs/voteWorks")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @ResponseBody
    @PreAuthorize("permitAll()")
    UniResp voteWorksToGridsf(
            @ApiParam(value = "voteAppId")
            @PathParam(value = "voteAppId")
                    String voteAppId,
            @ApiParam("活动id")
            @PathParam("voteActivityId")
                    String voteActivityId,


            @ApiParam(
                    value = "排序。格式为 '排序字段,排序方向'。其中，排序方向为 'asc'、'desc'。可以传递多个 sort 参数",
                    allowMultiple = true,
                    example = "age,asc"
            )
            @QueryParam("sort")
                    List<String> sort,

            @ApiParam(value = "关键字")
            @QueryParam("keyWord")
                    String workKeyword
    ) throws IOException;


    @GET
    @Path("/export/voteWorks/taskType/{taskTypeEnum}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @ResponseBody
    @PreAuthorize("permitAll()")
    UniResp exportVoteworks(
            @ApiParam(value = "voteAppId")
            @PathParam(value = "voteAppId")
                    String voteAppId,
            @ApiParam("活动id")
            @PathParam("voteActivityId")
                    String voteActivityId,
            @ApiParam("任务类型")
            @PathParam("taskTypeEnum")
                    String taskTypeEnum
    ) throws IOException;


}
