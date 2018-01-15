package net.kingsilk.qh.agency.server.resource.home;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.kingsilk.qh.agency.api.home.HomeApi;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Date;

/**
 * 主页
 */
@Api(
        tags = "home",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "主页跳转"
)
@Component
@Path("/home")
public class HomeResource implements HomeApi {

    @ApiOperation(
            value = "提示",
            nickname = "提示",
            notes = "提示"
    )
    @Path("/index")
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Override
    public String index() {
        return "qh-net.kingsilk.qh.agency-net.kingsilk.qh.net.kingsilk.qh.agency.admin : " + new Date();
    }

    @ApiOperation(
            value = "提示",
            nickname = "提示",
            notes = "提示"
    )
    @Path("/api")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String api() {
        return "qh-net.kingsilk.qh.agency-net.kingsilk.qh.net.kingsilk.qh.agency.admin : " + new Date();
    }

    @ApiOperation(
            value = "跳转到swagger",
            nickname = "跳转到swagger",
            notes = "跳转到swagger"
    )
    @Path("/swagger")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String swagger(@Context HttpServletResponse response) throws IOException {

        // http://localhost:10070/admin/local/14300/rs/webjars/swagger-ui/3.0.19/index.html
        // url=http://localhost:10070/admin/local/14300/rs/api/swagger.json
        String url = "https://agency.kingsilk.net/admin/local/11300/rs/webjars/swagger-ui/3.0.19/index.html?url=https://agency.kingsilk.net/admin/local/11300/rs/api/swagger.json";
        response.sendRedirect(url);
//        return "redirect:https://agency.kingsilk.net/admin/local/14300/rs/webjars/swagger-ui/3.0.19/index.html?url=https://agency.kingsilk.net/admin/local/14300/rs/api/swagger.json";
        return null;

    }

}
