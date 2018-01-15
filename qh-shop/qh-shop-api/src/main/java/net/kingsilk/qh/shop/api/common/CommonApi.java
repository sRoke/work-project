package net.kingsilk.qh.shop.api.common;

import io.swagger.annotations.*;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.common.dto.AddrQueryAdcResp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lit on 17/11/8.
 */
@Api(
        tags = "addr",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "公共接口API"
)
@Path("/common")
public interface CommonApi {

    public UniResp<Date> queryDate();


    @ApiOperation(
            value = "前端搜索adc",
            nickname = "前端搜索adc",
            notes = "前端搜索adc"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/getAdcList")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<List<Map<String, Object>>> getAdcList();

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

    @ApiOperation(
            value = "引导页",
            nickname = "引导页",
            notes = "引导页"
    )
    @Path("/guidePage")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Boolean> guidePage(
            @ApiParam(value = "引导页类型")
            @QueryParam(value = "type") String type
    );

}
