package net.kingsilk.qh.shop.api.brandApp.shop.supplier;

import io.swagger.annotations.*;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.supplier.dto.SupplierModel;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api(
        tags = "supplier",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "供应商相关api")
@Path("/brandApp/{brandAppId}/shop/{shopId}/supplier")
@Component
public interface SupplierApi{

//-----------------------------------------------------------------------------------//
    @ApiOperation(
            value = "新增供应商",
            nickname = "新增供应商",
            notes = "新增供应商"
    )
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    UniResp<String> add(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId")
                    String shopId,
            @ApiParam(value = "会员信息")
            @BeanParam
                    SupplierModel supplierReq
    );
//-----------------------------------------------------------------------------------//

    @ApiOperation(
            value = "删除供应商",
            nickname = "删除供应商",
            notes = "删除供应商"
    )
    @Path("/{supplierId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    UniResp<String> delete(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId")
                    String shopId,
            @ApiParam(value = "供应商ID")
            @PathParam(value = "supplierId")
                    String supplierId
    );
//-----------------------------------------------------------------------------------//
    @ApiOperation(
            value = "更新供应商",
            nickname = "更新供应商",
            notes = "更新供应商"
    )
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    UniResp<String> update(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId")
                    String shopId,
            @ApiParam(value = "供应商信息")
            @BeanParam
                    SupplierModel supplierReq
    );
//-----------------------------------------------------------------------------------//

    @ApiOperation(
            value = "供应商信息",
            nickname = "供应商信息",
            notes = "供应商信息"
    )
    @Path("/{supplierId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    UniResp<SupplierModel> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId")
                    String shopId,
            @ApiParam(value = "商品ID")
            @PathParam(value = "supplierId")
                    String supplierId
    );

//-----------------------------------------------------------------------------------//
    @ApiOperation(
            value = "供应商信息分页",
            nickname = "供应商信息分页",
            notes = "供应商信息分页"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    UniResp<UniPageResp<SupplierModel>> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId")
                    String shopId,
            @ApiParam(value = "每页多少个记录,最大100")
            @QueryParam("size")
            @DefaultValue("10")
                    int size,
            @ApiParam(value = "默认，从0开始")
            @QueryParam("page")
            @DefaultValue("0")
                    int page,
            @ApiParam("排序条件，如：'dateCreated,desc'")
            @QueryParam("sort")
            @DefaultValue("dateCreated,desc")
                    List<String> sort
    );
}
