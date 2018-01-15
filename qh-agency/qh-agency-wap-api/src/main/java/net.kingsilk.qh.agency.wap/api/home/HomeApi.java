package net.kingsilk.qh.agency.wap.api.home;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 *
 */

@Api(
        tags = "home",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "主页跳转"
)
@Component
@Path("/home")
public interface HomeApi {

    @ApiOperation(
            value = "提示",
            nickname = "提示",
            notes = "提示"
    )
    @Path("/index")
    @GET
//    @Produces(MediaType.APPLICATION_JSON)
    String index();

    @ApiOperation(
            value = "提示",
            nickname = "提示",
            notes = "提示"
    )
    @Path("/api")
    @GET
//    @Produces(MediaType.APPLICATION_JSON)
    String api();

    @ApiOperation(
            value = "跳转到swagger",
            nickname = "跳转到swagger",
            notes = "跳转到swagger"
    )
    @Path("/swagger")
    @GET
//    @Produces(MediaType.APPLICATION_JSON)
    String swagger(@Context HttpServletResponse response) throws IOException;
}
