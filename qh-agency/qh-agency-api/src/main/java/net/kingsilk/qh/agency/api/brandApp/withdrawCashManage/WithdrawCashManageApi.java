package net.kingsilk.qh.agency.api.brandApp.withdrawCashManage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniPageResp;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.withdrawCashManage.dto.WithdrawCashPageReq;
import net.kingsilk.qh.agency.api.brandApp.withdrawCashManage.dto.WithdrawCashPageResp;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * 提现相关API
 */
@Api(
        tags = "withdrawCash",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "员工管理相关API"
)
@Path("/brandApp/{brandAppId}/withdrawCash")
@Component
public interface WithdrawCashManageApi {

    @ApiOperation(
            value = "提现列表页",
            nickname = "提现列表页",
            notes = "提现列表页"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('WITHDRAWCASH_R')")
    UniResp<UniPageResp<WithdrawCashPageResp>> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @BeanParam WithdrawCashPageReq withdrawCashPageReq
    );


    @ApiOperation(
            value = "同意打款",
            nickname = "同意打款",
            notes = "同意打款"
    )
    @Path("/{id}/agreeWithdraw")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('WITHDRAWCASH_U')")
    UniResp<Map> agreeWithdraw(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "提现单ID")
            @PathParam(value = "id") String id);


    @ApiOperation(
            value = "拒绝打款",
            nickname = "拒绝打款",
            notes = "拒绝打款"
    )
    @Path("/{id}/rejectWithdraw")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('WITHDRAWCASH_J')")
    UniResp<String> rejectWithdraw(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "提现单ID")
            @PathParam(value = "id") String id);

}
