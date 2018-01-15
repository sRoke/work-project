package net.kingsilk.qh.activity.api.brandApp.vote.task;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.activity.api.UniResp;
import net.kingsilk.qh.activity.api.brandApp.vote.task.dto.TaskResp;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 导出excel的任务（下载完成后删除）
 */
@Api
@Path("/brandApp/{brandAppId}/activity/{activityId}/task/{id}")
public interface TaskApi {
    @ApiOperation(
            value = "导出excel的任务的详情"
    )
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<TaskResp> info(
            @ApiParam("品牌商ID")
            @PathParam("brandAppId")
                    String brandAppId,
            @ApiParam("活动id")
            @PathParam("activityId")
                    String activityId,
            @ApiParam("任务")
            @PathParam("id")
                    String id
    );
}
///**
// * 品牌商相关API。
// */
//@Api
//@Path("/brandApp/{brandAppId}/voteActivity")
//public interface VoteActivityApi {
//
//    /**
//     * 新增投票活动
//     */
//    @ApiOperation(
//            value = "新增投票活动"
//    )
//    @POST
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Produces({MediaType.APPLICATION_JSON})
//    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
//    UniResp<String> add(
//            @ApiParam("应用id")
//            @PathParam("brandAppId")
//                    String brandAppId,
////            @BeanParam
//            VoteActivityReq voteActivityReq
//    );