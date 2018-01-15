package net.kingsilk.qh.shop.api.brandApp.home;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.home.dto.HomeResp;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api
@Path("/brandAppId/{brandAppId}/home")
@Singleton
public interface HomeApi {


    @ApiOperation(
            value = "获取首页所需要的信息"
    )
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<List<HomeResp>> get(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId
    );
}
