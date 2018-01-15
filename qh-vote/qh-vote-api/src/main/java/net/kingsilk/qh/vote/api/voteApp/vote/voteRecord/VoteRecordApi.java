package net.kingsilk.qh.vote.api.voteApp.vote.voteRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.vote.api.UniPage;
import net.kingsilk.qh.vote.api.UniResp;
import net.kingsilk.qh.vote.api.voteApp.vote.voteRecord.dto.VoteRecordPageResp;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api
@Path("/voteApp/{voteAppId}/vote/{voteActivityId}/voteWork/{voteWorkId}/voteRecord")
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
//    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<UniPage<VoteRecordPageResp>> page(
            @ApiParam("品牌商ID")
            @PathParam("voteAppId")
                    String voteAppId,
            @ApiParam("活动id")
            @PathParam("voteActivityId")
                    String voteActivityId,
            @ApiParam("活动id")
            @PathParam("voteWorkId")
                    String voteWorkId,

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


//    /**
//     * 根据搜索的结果 导出Excel.xlsx
//     */
//    @ApiOperation(
//            value = "作品的搜索结果导出Excel.xlsx"
//    )
//    @GET
//    @Path("/toGridfs/voteRecords")
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Produces({MediaType.APPLICATION_JSON})
//    @ResponseBody
//    @PreAuthorize("permitAll()")
//    UniResp voteRecordsToGridfs(
//            @ApiParam(value = "voteAppId")
//            @PathParam(value = "voteAppId")
//                    String voteAppId,
//            @ApiParam("活动id")
//            @PathParam("voteActivityId")
//                    String voteActivityId,
//
//
//            @ApiParam(
//                    value = "排序。格式为 '排序字段,排序方向'。其中，排序方向为 'asc'、'desc'。可以传递多个 sort 参数",
//                    allowMultiple = true,
//                    example = "age,asc"
//            )
////            @DefaultValue(sort=)
//            @QueryParam("sort")
//                    List<String> sort,
//
//            @ApiParam("关键字搜索-投票人相关")
//            @QueryParam("voteKeyword")
//                    String voteKeyword,
//
//            @ApiParam("关键字搜索-报名人相关")
//            @QueryParam("workKeyword")
//                    String workKeyword
//    ) throws IOException;

//    @GET
//    @Path("/export/voteRecords/taskType/{taskTypeEnum}")
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Produces({MediaType.APPLICATION_JSON})
//    @ResponseBody
//    @PreAuthorize("permitAll()")
//    UniResp exportVoteRecords(
//            @ApiParam(value = "voteAppId")
//            @PathParam(value = "voteAppId")
//                    String voteAppId,
//            @ApiParam("活动id")
//            @PathParam("voteActivityId")
//                    String voteActivityId,
//            @ApiParam("任务类型")
//            @PathParam("taskTypeEnum")
//                    String taskTypeEnum
//    ) throws IOException;
}
