package net.kingsilk.qh.shop.api.brandApp.shop.item;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.item.dto.ItemInfoResp;
import net.kingsilk.qh.shop.api.brandApp.shop.item.dto.ItemMinInfo;
import net.kingsilk.qh.shop.api.brandApp.shop.item.dto.ItemPageReq;
import net.kingsilk.qh.shop.api.brandApp.shop.item.dto.ItemSaveReq;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

/**
 *
 */
@Path("/brandApp/{brandAppId}/shop/{shopId}/item")
public interface ItemApi {
    //--------------------------------保存商品信息---------------------------------------//

    @ApiOperation(
            value = "保存商品信息",
            nickname = "保存商品信息",
            notes = "保存商品信息"
    )
    @Path("")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF','SHOPSTAFF')")
    UniResp<String> save(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            ItemSaveReq itemSaveReq);

    //--------------------------------商品信息详情---------------------------------------//
    @ApiOperation(
            value = "商品信息详情",
            nickname = "商品信息详情",
            notes = "商品信息详情"
    )
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF','SHOPSTAFF')")
    UniResp<ItemInfoResp> info(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "商品ID")
            @PathParam(value = "id") String id);

    //--------------------------------商品分页信息详情---------------------------------------//
    @ApiOperation(
            value = "商品分页信息详情",
            nickname = "商品分页信息详情",
            notes = "商品分页信息详情"
    )

    @Path("")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF','SHOPSTAFF')")  //todo
    UniResp<UniPageResp<ItemMinInfo>> search(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @BeanParam ItemPageReq itemPageReq);

    //--------------------------------删除商品---------------------------------------//
    @ApiOperation(
            value = "删除商品",
            nickname = "删除商品",
            notes = "删除商品"
    )
    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF','SHOPSTAFF')")
    UniResp<String> delete(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "商品ID")
            @PathParam(value = "id") String id);

    //--------------------------------商品状态更改---------------------------------------//
    @ApiOperation(
            value = "商品状态更改",
            nickname = "商品状态更改",
            notes = "商品状态更改"
    )
    @Path("/{id}/changeStatus")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF','SHOPSTAFF')")
    UniResp<String> changeStatus(

            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,

            @ApiParam(value = "商品ID")
            @PathParam(value = "id")
                    String id,

            @ApiParam(value = "商品状态")
            @QueryParam(value = "status")
                    String status
    );

    //--------------------------------更新商品信息---------------------------------------//
    @ApiOperation(
            value = "更新商品信息",
            nickname = "更新商品信息",
            notes = "更新商品信息"
    )
    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF','SHOPSTAFF')")
    UniResp<String> update(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "商品ID")
            @PathParam(value = "id") String id,
            ItemSaveReq itemSaveReq);


    //--------------------------------下载excel模板---------------------------------------//
    @ApiOperation(
            value = "下载excel模板",
            nickname = "下载excel模板",
            notes = "下载excel模板"
    )
    @Path("/downloadModel")
    @GET
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF','SHOPSTAFF')")
    @Consumes(MediaType.APPLICATION_JSON)
    UniResp<String> downloadModel(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId
    );

    //--------------------------------导入商品信息---------------------------------------//
//    @ApiOperation(
//            value = "下载excel模板",
//            nickname = "下载excel模板",
//            notes = "下载excel模板"
//    )
//    @Path("/uploadModel")
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF','SHOPSTAFF')")
//    UniResp<String> uploadModel(
//            @ApiParam(value = "brandAppId")
//            @PathParam(value = "brandAppId") String brandAppId,
//            @ApiParam(value = "门店ID")
//            @PathParam(value = "shopId") String shopId,
//            @FormDataParam("certBytes") InputStream uploadedInputStream,
//            @FormDataParam("certBytes") FormDataContentDisposition fileDetail);

    //--------------------------------导入商品信息---------------------------------------//
    @ApiOperation(
            value = "导入商品信息",
            nickname = "导入商品信息",
            notes = "导入商品信息"
    )
    @Path("/import")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF','SHOPSTAFF')")
    UniResp<String> importItems(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @FormDataParam("certBytes") InputStream uploadedInputStream,
            @FormDataParam("certBytes") FormDataContentDisposition fileDetail);


}
