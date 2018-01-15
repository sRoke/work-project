package net.kingsilk.qh.activity.api.brandApp.bargain.wap;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.activity.api.UniResp;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/brandApp/{brandAppId}/activity/{activityId}/bargain/{userId}")
@Singleton
public interface BargainApi {

    /**
     * 砍价
     */
    @ApiOperation(
            value = "砍价"
    )
    @PUT
    @Path("{skuId}")
    @Consumes({MediaType.APPLICATION_JSON})
    UniResp<String> bargain(
            @ApiParam("应用id")
            @PathParam("brandAppId") String brandAppId,
            @ApiParam("活动Id")
            @PathParam("activityId") String activityId,
            @ApiParam("参与者用户Id")
            @PathParam("userId") String userId,
            @ApiParam("skuid")
            @PathParam("skuId") String skuId
    );


    /**
     * 查看砍价活动
     */
    @ApiOperation(
            value = "查看砍价活动"
    )
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    UniResp<String> info(
            @ApiParam("应用id")
            @PathParam("brandAppId") String brandAppId,
            @ApiParam("活动Id")
            @PathParam("activityId") String activityId
    );

    /**
     * 获得帮砍榜
     */
    @ApiOperation(
            value = "获得帮砍榜"
    )
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    UniResp<String> getHeplerList(
            @ApiParam("应用id")
            @PathParam("brandAppId")
                    String brandAppId
    );


    /**
     * 获得砍价结果
     */
    @ApiOperation(
            value = "获得砍价结果"
    )
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    UniResp<String> getBargainResult(
            @ApiParam("应用id")
            @PathParam("brandAppId")
                    String brandAppId
    );


    /**
     * 订单
     */
    @ApiOperation(
            value = "订单"
    )
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    UniResp<String> check(
            @ApiParam("应用id")
            @PathParam("brandAppId")
                    String brandAppId
    );


    /**
     * 订单
     */
    @ApiOperation(
            value = "订单"
    )
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<String> create(
            @ApiParam("应用id")
            @PathParam("brandAppId")
                    String brandAppId
    );

    /**
     * 订单
     */
    @ApiOperation(
            value = "订单"
    )
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<String> generateOrder(
            @ApiParam("应用id")
            @PathParam("brandAppId")
                    String brandAppId
    );

    /**
     * 判断是否曾经参加
     */
    @ApiOperation(
            value = "判断是否曾经参加"
    )
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
    UniResp<String> judgeJoined(
            @ApiParam("应用id")
            @PathParam("brandAppId")
                    String brandAppId
    );
}
