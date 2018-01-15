package net.kingsilk.qh.raffle.api.raffleApp.raffle.record;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.raffle.api.common.UniPageResp;
import net.kingsilk.qh.raffle.api.common.UniResp;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.record.dto.DeliverInvoiceShipReq;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.record.dto.RaffleRecordDetail;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.record.dto.RaffleRecordMinResp;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.record.dto.RaffleRecordReq;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Api
@Path("/raffleApp/{raffleAppId}/raffle/admin/{raffleId}")
public interface RaffleRecordApi {

    /**
     * 店主端
     */

    /**
     * 新增和更新抽奖记录
     */
    @ApiOperation(
            value = "新增和更新抽奖记录"
    )
    @PUT
    @Path("/ship/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<String> ship(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId,
            @ApiParam("应用id")
            @PathParam("raffleId")
                    String raffleId,
            @ApiParam("记录id")
            @PathParam("id")
                    String id,
            DeliverInvoiceShipReq deliverInvoiceShipReq
    );

    @ApiOperation(
            value = "领取详情"
    )
    @Path("/{id}/detail")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<RaffleRecordDetail> detail(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId,
            @ApiParam("抽奖活动id")
            @PathParam("raffleId")
                    String raffleId,
            @ApiParam("抽奖记录id")
            @PathParam("id")
                    String id
    );

    @ApiOperation(
            value = "抽奖记录列表"
    )
    @Path("/getraffleRecord")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<UniPageResp<RaffleRecordMinResp>> page(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId,
            @ApiParam("应用id")
            @PathParam("raffleId")
                    String raffleId,
            @BeanParam
                    RaffleRecordReq recordReq
    );


}
