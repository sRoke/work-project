package net.kingsilk.qh.raffle.api.raffleApp.bargain;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.raffle.api.common.UniResp;
import net.kingsilk.qh.raffle.api.raffleApp.bargain.convert.BargainReq;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 砍价活动api
 */
@Api
@Path("/raffleApp/{raffleAppId}/bargain")
public interface BargainApi {

    /**
     * 新增砍价活动
     */
    @ApiOperation(
            value = "新增砍价活动"
    )
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
//    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<String> save(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId,
            BargainReq bargainReq
    );


    /**
     * 查看砍价活动
     */
    @ApiOperation(
            value = "查看砍价活动"
    )
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
//    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<String> info(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId
    );

    /**
     * 砍价活动分页
     */
    @ApiOperation(
            value = "砍价活动分页"
    )
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
//    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<String> page(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId
    );


    /**
     * 改变活动状态
     */
    @ApiOperation(
            value = "改变活动状态"
    )
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
//    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<String> changeStatus(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId
    );


    /**
     * 删除活动
     */
    @ApiOperation(
            value = "删除活动"
    )
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
//    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<String> delete(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId
    );


    /**
     * 获得砍价记录
     */
    @ApiOperation(
            value = "获得砍价记录"
    )
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
//    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<String> getBargainRecord(
            @ApiParam("应用id")
            @PathParam("raffleAppId")
                    String raffleAppId
    );

}
