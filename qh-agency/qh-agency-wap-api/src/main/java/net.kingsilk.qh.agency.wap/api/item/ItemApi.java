package net.kingsilk.qh.agency.wap.api.item;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.kingsilk.qh.agency.wap.api.UniResp;
import net.kingsilk.qh.agency.wap.api.common.dto.Category;
import net.kingsilk.qh.agency.wap.api.item.dto.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api(
        tags = "item",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "商品相关API"
)
@Path("/item")
@Component
public interface ItemApi {
    @ApiOperation(
            value = "搜索商品",
            nickname = "搜索商品",
            notes = "搜索商品"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UniResp<List<ItemMiniInfoModel>> search(@BeanParam ItemSearchReq req, @Context HttpServletRequest request);

    @ApiOperation(
            value = "商品详情",
            nickname = "商品详情",
            notes = "商品详情"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/detail")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UniResp<ItemInfoModel> detail(@PathParam(value = "itemId") String itemId);

    @ApiOperation(
            value = "获取商品分类",
            nickname = "获取商品分类",
            notes = "获取商品分类"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/getCategory")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UniResp<List<CategoryResp>> getCategory();

}
