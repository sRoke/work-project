package net.kingsilk.qh.raffle.api.raffleApp.raffle.raffle;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.raffle.api.common.UniPageResp;
import net.kingsilk.qh.raffle.api.common.UniResp;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.raffle.dto.RaddleInfoAdmin;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.raffle.dto.RaffleInfo;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.raffle.dto.RaffleMinResp;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.raffle.dto.RafflePageReq;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Api
@Path("/raffleApp/{raffleAppId}/raffle")
public interface RaffleApi {

    /**
     * 店主端
     */

    /**
     * 新增和更新抽奖活动
     */
    @ApiOperation(
            value = "新增和更新抽奖活动"
    )
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<String> save(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId,
            RaffleInfo req
    );

    /**
     * 编辑更新抽奖活动
     */
    @ApiOperation(
            value = "编辑更新抽奖活动"
    )
    @Path("/{id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<String> update(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId,
            @ApiParam("抽奖活动id")
            @PathParam("id")
                    String raffleId,
            RaffleInfo req
    );

    @ApiOperation(
            value = "奖项详情"
    )
    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<RaddleInfoAdmin> info(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId,
            @ApiParam("抽奖活动id")
            @PathParam("id")
                    String id
    );

    @ApiOperation(
            value = "启用/停用抽奖活动"
    )
    @Path("/{id}/enable")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<String> enable(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId,
            @ApiParam("抽奖活动id")
            @PathParam("id")
                    String id
    );


    @ApiOperation(
            value = "删除抽奖活动"
    )
    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
//    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<String> delete(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId,
            @ApiParam("抽奖活动id")
            @PathParam("id")
                    String id
    );


    @ApiOperation(
            value = "抽奖活动列表"
    )
    @Path("/page")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
//    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<UniPageResp<RaffleMinResp>> page(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId,
            @BeanParam
                    RafflePageReq pageReq
    );
}
