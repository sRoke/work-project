package net.kingsilk.qh.activity.api.brandApp.vote.voteRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.activity.api.UniPage;
import net.kingsilk.qh.activity.api.UniResp;
import net.kingsilk.qh.activity.api.brandApp.vote.voteRecord.dto.VoteRecordPageResp;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Api
@Path("/brandApp/{brandAppId}/activity/{activityId}/vote/voteRecord")
@Singleton
public interface VoteRecordApi {

    /**
     * 查看投票记录
     */
    @ApiOperation(
            value = "投票记录"
    )
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<UniPage<VoteRecordPageResp>> page(
            @ApiParam("品牌商ID")
            @PathParam("brandAppId")
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

            @ApiParam("关键字搜索-投票人相关")
            @QueryParam("voteKeyword")
                    String voteKeyword,

            @ApiParam("关键字搜索-报名人相关")
            @QueryParam("workKeyword")
                    String workKeyword,

            @ApiParam(
                    value = "排序。格式为 '排序字段,排序方向'。其中，排序方向为 'asc'、'desc'。可以传递多个 sort 参数",
                    allowMultiple = true,
                    example = "age,asc"
            )
            @QueryParam("sort")
                    List<String> sort

    );


    /**
     * 根据搜索的结果 导出Excel.xlsx
     */
    @ApiOperation(
            value = "作品的搜索结果导出Excel.xlsx"
    )
    @GET
    @Path("/toGridfs/voteRecords")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @ResponseBody
    @PreAuthorize("permitAll()")
    UniResp voteRecordsToGridfs(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam("活动id")
            @PathParam("activityId")
                    String activityId,


            @ApiParam(
                    value = "排序。格式为 '排序字段,排序方向'。其中，排序方向为 'asc'、'desc'。可以传递多个 sort 参数",
                    allowMultiple = true,
                    example = "age,asc"
            )
//            @DefaultValue(sort=)
            @QueryParam("sort")
                    List<String> sort,

            @ApiParam("关键字搜索-投票人相关")
            @QueryParam("voteKeyword")
                    String voteKeyword,

            @ApiParam("关键字搜索-报名人相关")
            @QueryParam("workKeyword")
                    String workKeyword
    ) throws IOException;

    @GET
    @Path("/export/voteRecords/taskType/{taskTypeEnum}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @ResponseBody
    @PreAuthorize("permitAll()")
    UniResp exportVoteRecords(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam("活动id")
            @PathParam("activityId")
                    String activityId,
            @ApiParam("任务类型")
            @PathParam("taskTypeEnum")
                    String taskTypeEnum
    ) throws IOException;
}
