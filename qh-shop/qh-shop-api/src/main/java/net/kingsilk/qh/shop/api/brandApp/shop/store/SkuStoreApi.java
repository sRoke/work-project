package net.kingsilk.qh.shop.api.brandApp.shop.store;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.brandApp.shop.skuStoreLog.dto.SkuStoreLogReq;
import net.kingsilk.qh.shop.api.UniResp;
import org.springframework.stereotype.Component;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Api(
        tags = "Stock",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "出入库相关管理"
)
@Component
@Path("/brandApp/{brandAppId}/shop/{shopId}/xxx")
public interface SkuStoreApi {

    @ApiOperation(
            value = "库存操作",
            nickname = "库存操作",
            notes = "库存操作"
    )
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> creat(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            SkuStoreLogReq skuStoreLogReq
    );

}
