package net.kingsilk.qh.raffle.api.raffleApp.raffle.wap.raffle;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.raffle.api.common.UniResp;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.award.dto.AwardInfo;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.raffle.dto.RaffleInfo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Api
@Path("/raffleApp/{raffleAppId}/wap/raffle")
public interface RaffleApi {
    /**
     * 用户端
     */
    @ApiOperation(
            value = "用户端抽奖详情"
    )
    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<RaffleInfo> info(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId,
            @ApiParam("抽奖活动id")
            @PathParam("id")
                    String id,
            @ApiParam("openId")
            @QueryParam("openId")
                    String openId
    );

    /**
     * 用户端
     */
    @ApiOperation(
            value = "用户端抽奖详情"
    )
    @Path("/{id}/addTickets")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<String> addTickets(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId,
            @ApiParam("抽奖活动id")
            @PathParam("id")
                    String id,
            @ApiParam("openId")
            @QueryParam("openId")
                    String openId
    );


    @ApiOperation(
            value = "获取raffleAppId",
            nickname = "获取raffleAppId",
            notes = "获取raffleAppId"
    )
    @GET
    @Path("/judge")
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF_R')")
    UniResp<Boolean> judgeOpenId(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "raffleAppId") String raffleAppId,
            @ApiParam(value = "店铺id")
            @QueryParam(value = "openId") String openId);

    /**
     * 该活动是否强制关注微信公众号 是且该用户未关注 生成二维码
     */
    @GET
    @Path("/{raffleId}/isFollow/{recordId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<String> isForceFollow(
            @ApiParam("品牌商ID")
            @PathParam("raffleAppId")
                    String raffleAppId,
            @ApiParam("活动id")
            @PathParam("raffleId")
                    String raffleId,
            @ApiParam("活动id")
            @PathParam("recordId")
                    String recordId,
            @ApiParam("openId")
            @QueryParam("openId")
                    String openId,
            @ApiParam("分享链接")
            @QueryParam("shareUrl")
                    String shareUrl
    );

    @ApiOperation(
            value = "进行抽奖"
    )
    @Path("/{id}/lottery")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<AwardInfo> lottery(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId,
            @ApiParam("抽奖活动id")
            @PathParam("id")
                    String id
    );

}
