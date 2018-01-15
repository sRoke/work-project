package net.kingsilk.qh.activity.api.brandApp.vote.voteActivity;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.activity.api.UniPage;
import net.kingsilk.qh.activity.api.UniResp;
import net.kingsilk.qh.activity.api.brandApp.vote.voteActivity.dto.VoteActivityPageResp;
import net.kingsilk.qh.activity.api.brandApp.vote.voteActivity.dto.VoteActivityReq;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 品牌商相关API。
 */
@Api
@Path("/brandApp/{brandAppId}/vote")
public interface VoteActivityApi {

    /**
     * 新增投票活动
     */
    @ApiOperation(
            value = "新增投票活动"
    )
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<String> add(
            @ApiParam("应用id")
            @PathParam("brandAppId")
                    String brandAppId,
//            @BeanParam
            VoteActivityReq voteActivityReq
    );

    /***
     * 修改投票活动
     */
    @ApiOperation(
            value = "修改编辑投票活动"
    )
    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<String> update(
            @ApiParam("品牌商ID")
            @PathParam("brandAppId")
                    String brandAppId,
            @ApiParam("应用id")
            @PathParam("id")
                    String id,
            @ApiParam("修改信息")
//            @BeanParam
                    VoteActivityReq voteActivityReq
    );


    /***
     * 删除投票活动
     */
    @ApiOperation(
            value = "删除投票活动"
    )
    @DELETE
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<Void> delete(
            @ApiParam("品牌商ID")
            @PathParam("brandAppId")
                    String brandAppId,
            @ApiParam("活动id")
            @PathParam("id")
                    String id
    );


    /***
     * 投票活动分页
     */
    @ApiOperation(
            value = "投票活动分页"
    )
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
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
                    String keyWord

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


    /**
     * 该活动是否强制手机号
     */
    @GET
    @Path("/{id}/isPhone")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<String> isForcePhone(
            @ApiParam("品牌商ID")
            @PathParam("brandAppId")
                    String brandAppId,
            @ApiParam("活动id")
            @PathParam("id")
                    String id
    );

    /**
     * 该活动是否强制关注微信公众号 是且该用户未关注 生成二维码
     */
    @GET
    @Path("/{activityId}/isFollow")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<String> isForceFollow(
            @ApiParam("品牌商ID")
            @PathParam("brandAppId")
                    String brandAppId,
            @ApiParam("活动id")
            @PathParam("activityId")
                    String activityId,
            @ApiParam("作品id")
            @QueryParam("workId")
                    String workId,
            @ApiParam("分享链接")
            @QueryParam("shareUrl")
                    String shareUrl
    );
}
