package net.kingsilk.qh.shop.api.brandApp.sync;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.sync.dto.ItemSyncReq;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@Path("/brandApp/{brandAppId}/SyncItem")
public interface SyncItemApi {

    @ApiOperation(
            value = "保存商品信息",
            nickname = "保存商品信息",
            notes = "保存商品信息"
    )
    @Path("")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated()")
    UniResp<String> syncItem(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId") String brandAppId,
            ItemSyncReq itemSyncReq
    );

}
