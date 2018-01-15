package net.kingsilk.qh.raffle.api.raffleApp.raffle.wap.award;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.award.dto.AwardInfo;
import net.kingsilk.qh.raffle.api.common.UniOrder;
import net.kingsilk.qh.raffle.api.common.UniPageResp;
import net.kingsilk.qh.raffle.api.common.UniResp;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
    /**
     * 奖项api    手机店主端
     */
    @Api
    @Path("/raffleApp/{raffleAppId}/wap/raffle/{raffleId}/Award")
    public interface AwardApi {
        /**
         * 店主端
         */

        @ApiOperation(
                value = "奖项列表"
        )
        @Path("/list")
        @GET
//        @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
        UniResp<UniPageResp<AwardInfo>> list(
                @ApiParam("应用id")
                @PathParam("raffleAppId")
                        String raffleAppId,
                @ApiParam("抽奖活动id")
                @PathParam("raffleId")
                        String raffleId
        );
//        /**
//         * 新增奖项
//         */
//        @ApiOperation(
//                value = "新增奖项"
//        )
//        @POST
//        @Consumes({MediaType.APPLICATION_JSON})
//        @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
//        UniResp<String> save(
//                @ApiParam("应用id")
//                @PathParam("raffleAppId")
//                        String raffleAppId,
//                @ApiParam("抽奖活动id")
//                @PathParam("raffleId")
//                        String raffleId,
//                AwardInfo awardReq
//        );
//
//        /**
//         * 编辑更新奖项
//         */
//        @ApiOperation(
//                value = "编辑更新奖项"
//        )
//        @Path("/{awardId}")
//        @PUT
//        @Consumes({MediaType.APPLICATION_JSON})
//        @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
//        UniResp<String> update(
//                @ApiParam("应用id")
//                @PathParam("raffleAppId")
//                        String raffleAppId,
//                @ApiParam("抽奖活动id")
//                @PathParam("raffleId")
//                        String raffleId,
//                @ApiParam("奖项id")
//                @PathParam("awardId")
//                        String awardId,
//                AwardInfo awardReq
//        );
//
//        @ApiOperation(
//                value = "奖项详情"
//        )
//        @Path("/{awardId}")
//        @GET
//        @Produces({MediaType.APPLICATION_JSON})
//        @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
//        UniResp<AwardInfo> info(
//                @ApiParam("应用id")
//                @PathParam("raffleAppId")
//                        String raffleAppId,
//                @ApiParam("抽奖活动id")
//                @PathParam("raffleId")
//                        String raffleId,
//                @ApiParam("奖项id")
//                @PathParam("awardId")
//                        String awardId
//        );
//
//        @ApiOperation(
//                value = "删除奖项"
//        )
//        @Path("/{awardId}")
//        @DELETE
//        @PreAuthorize("isAuthenticated()&&hasAnyAuthority('SA')")
//        UniResp<String> delete(
//                @ApiParam("应用id")
//                @PathParam("raffleAppId")
//                        String raffleAppId,
//                @ApiParam("抽奖活动id")
//                @PathParam("raffleId")
//                        String raffleId,
//                @ApiParam("奖项id")
//                @PathParam("awardId")
//                        String awardId
//        );

}
