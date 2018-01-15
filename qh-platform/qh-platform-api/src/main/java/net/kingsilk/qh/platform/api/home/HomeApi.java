package net.kingsilk.qh.platform.api.home;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.kingsilk.qh.platform.api.UniResp;
import net.kingsilk.qh.platform.api.home.dto.HomeResp;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 首页相关qpi
 */
@Api
@Path("/home")
@Singleton
public interface HomeApi {


    @ApiOperation(
            value = "获取首页所需要的信息"
    )
    @GET

    @Produces({MediaType.APPLICATION_JSON})
//    @PreAuthorize("#oauth2.clientHasAnyRole('SERVER')")
    UniResp<List<HomeResp>> get();


}
