package net.kingsilk.qh.agency.api.brandApp.wx;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniResp;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Created by lit on 17/8/31.
 */
@Api(
        tags = "wx",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "微信API"
)
@Path("/brandApp/{brandAppId}/weiXin")
@Component
public interface WeiXinApi {


    @ApiOperation(
            value = "微信jsSdkConf",
            nickname = "微信jsSdkConf",
            notes = "微信jsSdkConf"
    )
    @Path("/jsSdkConf")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Map> jsSdkConf(
            @ApiParam(value = "url")
            @QueryParam(value = "url") String url);
}
