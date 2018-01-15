package net.kingsilk.qh.raffle.api.raffleApp.raffle.wap.record;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.raffle.api.common.UniResp;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.award.dto.AwardInfo;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.record.dto.RaffleRecordDetail;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.wap.record.dto.RecordInfoResp;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api
@Path("/raffleApp/{raffleAppId}/raffle/{raffleId}/wap/record")
@Singleton
public interface RecordWapApi {

    @ApiOperation(
            value = "查看奖品"
    )
    @Path("/list")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<List<AwardInfo>> list(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId,
            @ApiParam("抽奖活动id")
            @PathParam("raffleId")
                    String raffleId,
            @QueryParam("openId")
                    String openId
    );

    @ApiOperation(
            value = "领取详情"
    )
    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<RecordInfoResp> info(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId,
            @ApiParam("抽奖活动id")
            @PathParam("raffleId")
                    String raffleId,
            @ApiParam("抽奖记录id")
            @PathParam("id")
                    String id,
            @QueryParam("openId")
                    String openId
    );

    @ApiOperation(
            value = "领取详情"
    )
    @Path("/{id}/detail")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<RaffleRecordDetail> detail(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId,
            @ApiParam("抽奖活动id")
            @PathParam("raffleId")
                    String raffleId,
            @ApiParam("抽奖记录id")
            @PathParam("id")
                    String id,
            @QueryParam("openId")
                    String openId
    );

    @ApiOperation(
            value = "领取"
    )
    @Path("/{id}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<String> accept(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId,
            @ApiParam("抽奖活动id")
            @PathParam("raffleId")
                    String raffleId,
            @ApiParam("抽奖记录id")
            @PathParam("id")
                    String id,
            @QueryParam("openId")
                    String openId,
            @QueryParam("memo")
                    String memo
    );

    @ApiOperation(
            value = "修改地址",
            nickname = "修改地址",
            notes = "修改地址")
    @Path("/{id}/addr/{addrId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> chooseAddr(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "raffleAppId") String raffleAppId,
            @ApiParam("活动Id")
            @PathParam("raffleId") String raffleId,
            @PathParam(value = "id") String id,
            @PathParam(value = "addrId") String addrId,
            @QueryParam("openId")
                    String openId
    );
}
