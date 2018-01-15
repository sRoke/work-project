package net.kingsilk.qh.shop.api.controller.common;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.kingsilk.qh.shop.api.UniResp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Api(
        tags = "common",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "公共接口API"
)
@Path("/common")
public interface CommonApi {

    //--------------------------------后端搜索adc---------------------------------------//
    @ApiOperation(
            value = "后端搜索date",
            nickname = "后端搜索date",
            notes = "后端搜索date"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/queryDate")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Date> queryDate();

}
