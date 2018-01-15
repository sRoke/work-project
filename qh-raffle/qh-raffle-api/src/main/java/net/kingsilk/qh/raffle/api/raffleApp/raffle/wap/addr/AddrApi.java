package net.kingsilk.qh.raffle.api.raffleApp.raffle.wap.addr;

import io.swagger.annotations.*;
import net.kingsilk.qh.raffle.api.common.UniResp;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.wap.addr.dto.AddrModel;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.wap.addr.dto.AddrSaveReq;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api(
        tags = "addr",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "地址管理相关API"
)
@Component
@Path("/raffleApp/{raffleAppId}/raffle/{raffleId}/addr")
public interface AddrApi {
    //--------------------------------获取收货地址列表---------------------------------------//
    @ApiOperation(
            value = "获取收货地址列表",
            nickname = "获取收货地址列表",
            notes = "获取收货地址列表")
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<List<AddrModel>> list(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "raffleAppId") String raffleAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "raffleId") String raffleId,
            @QueryParam("openId")
                    String openId);

    //--------------------------------获取收货地址详情---------------------------------------//
    @ApiOperation(
            value = "获取收货地址详情",
            nickname = "获取收货地址详情",
            notes = "获取收货地址详情"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<AddrModel> detail(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "raffleAppId") String raffleAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "raffleId") String raffleId,
            @ApiParam(value = "收货地址ID")
            @PathParam(value = "id") String id,
            @QueryParam("openId")
                    String openId);

    //--------------------------------保存收货地址---------------------------------------//
    @ApiOperation(
            value = "保存收货地址",
            nickname = "保存收货地址",
            notes = "保存收货地址"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> save(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "raffleAppId") String raffleAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "raffleId") String raffleId,
            AddrSaveReq req);


    //--------------------------------更新收货地址---------------------------------------//
    @ApiOperation(
            value = "更新收货地址",
            nickname = "更新收货地址",
            notes = "更新收货地址"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> update(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "raffleAppId") String raffleAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "raffleId") String raffleId,
            @ApiParam(value = "地址ID")
            @PathParam(value = "id") String id,
            AddrSaveReq req);

    //--------------------------------设置默认收货地址---------------------------------------//
    @ApiOperation(
            value = "设置默认收货地址",
            nickname = "设置默认收货地址",
            notes = "设置默认收货地址"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/{id}/setDefault")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> setDefault(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "raffleAppId") String raffleAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "raffleId") String raffleId,
            @ApiParam(value = "收货地址ID")
            @PathParam(value = "id") String id,
            @QueryParam("openId")
                    String openId);


    //--------------------------------删除收货地址---------------------------------------//
    @ApiOperation(
            value = "删除收货地址",
            nickname = "删除收货地址",
            notes = "删除收货地址"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> delete(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "raffleAppId") String raffleAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "raffleId") String raffleId,
            @PathParam(value = "id") String id,
            @QueryParam("openId")
                    String openId);

    //--------------------------------删除收货地址---------------------------------------//
    @ApiOperation(
            value = "删除收货地址",
            nickname = "删除收货地址",
            notes = "删除收货地址"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/getDefault")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<AddrModel> getDefault(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "raffleAppId") String raffleAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "raffleId") String raffleId,
            @QueryParam("openId")
                    String openId
    );


}

