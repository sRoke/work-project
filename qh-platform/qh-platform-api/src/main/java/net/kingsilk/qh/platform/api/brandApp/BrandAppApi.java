package net.kingsilk.qh.platform.api.brandApp;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.platform.api.UniPage;
import net.kingsilk.qh.platform.api.UniResp;
import net.kingsilk.qh.platform.api.brandApp.dto.BrandAppPageReq;
import net.kingsilk.qh.platform.api.brandApp.dto.BrandAppReq;
import net.kingsilk.qh.platform.api.brandApp.dto.BrandAppResp;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/***
 * 应用与品牌商关联操作
 */
@Api
@Path("/brandApp")
@Singleton
public interface BrandAppApi {

    @ApiOperation(
            value = "新增关联"
    )
    @POST
    @Path("/add")
    @Produces({MediaType.APPLICATION_JSON})
//    @PreAuthorize("#oauth2.clientHasAnyRole('SERVER')")
    UniResp<String> add(
            BrandAppReq brandAppReq
    );

    @ApiOperation(
            value = "查看详细"
    )
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
//    @PreAuthorize("#oauth2.clientHasAnyRole('SERVER')")
    UniResp<BrandAppResp> info(
            @ApiParam(value = "关联id")
            @PathParam(value = "id")
                    String id

    );

    @ApiOperation(
            value = "查看列表"
    )
    @GET
    @Produces({MediaType.APPLICATION_JSON})
//    @PreAuthorize("#oauth2.clientHasAnyRole('SERVER')")
    UniResp<UniPage<BrandAppResp>> page(
            @BeanParam
                    BrandAppPageReq brandAppPageReq
    );

    @ApiOperation(
            value = "删除关联"
    )
    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
//    @PreAuthorize("#oauth2.clientHasAnyRole('SERVER')")
    UniResp<String> del(
            @ApiParam(value = "应用id")
            @PathParam(value = "id")
                    String id
    );

    @ApiOperation(
            value = "给brandApp关联表新增关联微信公众号"
    )
    @PUT
    @Path("{brandAppId}/wxMpId/{wxMpId}")
    @Produces({MediaType.APPLICATION_JSON})
//    @PreAuthorize("#oauth2.clientHasAnyRole('SERVER')")
    UniResp<String> wxMpId(
            @ApiParam(value = "商家id")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "应用id")
            @PathParam(value = "wxMpId")
                    String wxMpId
    );


}
