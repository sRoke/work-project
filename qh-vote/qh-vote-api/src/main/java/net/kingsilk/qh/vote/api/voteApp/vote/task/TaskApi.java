package net.kingsilk.qh.vote.api.voteApp.vote.task;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.vote.api.UniResp;
import net.kingsilk.qh.vote.api.voteApp.vote.task.dto.TaskResp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 导出excel的任务（下载完成后删除）
 */
@Api
@Path("/voteApp/{voteAppId}/vote/{voteActivityId}/task/{id}")
public interface TaskApi {
    @ApiOperation(
            value = "导出excel的任务的详情"
    )
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<TaskResp> info(
            @ApiParam("品牌商ID")
            @PathParam("voteAppId")
                    String voteAppId,
            @ApiParam("活动id")
            @PathParam("voteActivityId")
                    String voteActivityId,
            @ApiParam("任务")
            @PathParam("id")
                    String id
    );
}
///**
// * 品牌商相关API。
// */
//@Api
//@Path("/voteApp/{voteAppId}/voteActivity")
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
//            @PathParam("voteAppId")
//                    String voteAppId,
////            @BeanParam
//            VoteActivityReq voteActivityReq
//    );