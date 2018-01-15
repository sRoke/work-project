package net.kingsilk.qh.agency.api.common;

import io.swagger.annotations.*;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.addr.dto.AddrQueryAdcResp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@Api(
        tags = "addr",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "公共接口API"
)
@Path("/common")
public interface CommonApi {
    //--------------------------------后端搜索adc---------------------------------------//
    @ApiOperation(
            value = "后端搜索adc",
            nickname = "后端搜索adc",
            notes = "后端搜索adc"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/queryAdc")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<AddrQueryAdcResp> queryAdc(
            @ApiParam(value = "地址adc编码")
            @QueryParam(value = "adc") String adc);

    //--------------------------------前端搜索adc---------------------------------------//
    @ApiOperation(
            value = "前端搜索adc",
            nickname = "前端搜索adc",
            notes = "前端搜索adc"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/getAdcList")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<AddrQueryAdcResp> getAdcList();

//    //----------------------------------获取当前登录用户信息-------------------------------//
//
//    @ApiOperation(
//            value = "获取当前登录用户信息",
//            nickname = "获取当前登录用户信息",
//            notes = "获取当前登录用户信息"
//    )
//    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
//    @Path("/getUserInfo")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<String> getUserInfo();


}
