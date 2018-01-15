package net.kingsilk.qh.raffle.api.raffleApp.common;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.kingsilk.qh.raffle.api.common.UniResp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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

}
