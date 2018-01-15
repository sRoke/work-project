package net.kingsilk.qh.agency.wap.api.addr;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.kingsilk.qh.agency.wap.api.UniResp;
import net.kingsilk.qh.agency.wap.api.addr.dto.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by zcw on 17-3-16.
 */
@Path("/addr")
@Component
@Api(
        tags = "addr",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "地址管理相关API"
)
public interface AddrApi {
    @ApiOperation(
            value = "获取收货地址列表",
            nickname = "获取收货地址列表",
            notes = "获取收货地址列表")
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<AddrListResp> list(AddrListReq req);

    @ApiOperation(
            value = "获取收货地址详情",
            nickname = "获取收货地址详情",
            notes = "获取收货地址详情"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/detail")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<AddrModel> detail(@QueryParam(value = "id") String id);

    @ApiOperation(
            value = "保存收货地址",
            nickname = "保存收货地址",
            notes = "保存收货地址"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> save(AddrSaveReq req);

    @ApiOperation(
            value = "设置默认收货地址",
            nickname = "设置默认收货地址",
            notes = "设置默认收货地址"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/setDefault")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> setDefault(@QueryParam(value = "id") String id);

    @ApiOperation(
            value = "搜索adc",
            nickname = "搜索adc",
            notes = "搜索adc"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/queryAdc")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<ArrayList> queryAdc();

    @ApiOperation(
            value = "删除收货地址",
            nickname = "删除收货地址",
            notes = "删除收货地址"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> delete(@QueryParam(value = "id") String id);



}
