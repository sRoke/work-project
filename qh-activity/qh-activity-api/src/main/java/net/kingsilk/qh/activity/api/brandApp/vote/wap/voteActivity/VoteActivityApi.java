package net.kingsilk.qh.activity.api.brandApp.vote.wap.voteActivity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.activity.api.UniPage;
import net.kingsilk.qh.activity.api.UniResp;
import net.kingsilk.qh.activity.api.brandApp.vote.voteActivity.dto.VoteActivityPageResp;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 手机端活动相关api
 */
@Api
@Path("/brandApp/{brandAppId}/vote/wap")
public interface VoteActivityApi {


    /***
     * 投票活动分页
     */
    @ApiOperation(
            value = "投票活动分页"
    )
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("permitAll()")
    UniResp<UniPage<VoteActivityPageResp>> page(
            @ApiParam("品牌商ID")
            @PathParam("brandAppId")
                    String brandAppId,
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
            @ApiParam(value = "状态")
            @QueryParam("status")
                    String status

    );


    /***
     * 投票活动详情
     */
    @ApiOperation(
            value = "投票活动详情"
    )
    @GET
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("permitAll()")
    UniResp<VoteActivityPageResp> info(
            @ApiParam("品牌商ID")
            @PathParam("brandAppId")
                    String brandAppId,
            @ApiParam("活动id")
            @PathParam("id")
                    String id
    );


}
