package net.kingsilk.qh.platform.api.app;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.platform.api.UniPage;
import net.kingsilk.qh.platform.api.UniResp;
import net.kingsilk.qh.platform.api.app.dto.AppPageReq;
import net.kingsilk.qh.platform.api.app.dto.AppReq;
import net.kingsilk.qh.platform.api.app.dto.AppResp;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


/***
 * 应用信息api
 */
@Api
@Path("/app")
@Singleton
public interface AppApi {

    /**
     * 编辑
     */
    @ApiOperation(
            value = "编辑应用信息"
    )
    @PUT
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
//    @PreAuthorize("#oauth2.clientHasAnyRole('SERVER')")
    UniResp<String> update(
            @ApiParam(value = "应用id")
            @PathParam(value = "id")
                    String id,
            AppReq appReq
    );

    /**
     * 分页
     */
    @ApiOperation(
            value = "应用分页信息"
    )
    @GET
    @Produces({MediaType.APPLICATION_JSON})
//    @PreAuthorize("#oauth2.clientHasAnyRole('SERVER')")
    UniResp<UniPage<AppResp>> page(
            @BeanParam AppPageReq appPageReq
    );


    /**
     * 详情
     */
    @ApiOperation(
            value = "应用详细信息"
    )
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
//    @PreAuthorize("#oauth2.clientHasAnyRole('SERVER')")
    UniResp<AppResp> info(
            @ApiParam(value = "应用id")
            @PathParam(value = "id") String id
    );
}


