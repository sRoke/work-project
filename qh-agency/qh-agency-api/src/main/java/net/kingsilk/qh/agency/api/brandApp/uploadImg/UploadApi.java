package net.kingsilk.qh.agency.api.brandApp.uploadImg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniResp;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Api( // 用在类上，用于设置默认值
        tags = "uploadImg",
        consumes = MediaType.APPLICATION_JSON,
        produces = MediaType.MULTIPART_FORM_DATA,
        protocols = "http,https",
        description = "公共的上传图片"
)
@Path("/brandApp/{brandAppId}/uploadImg")
@Component
interface UploadApi {
    /**
     * 上传图片
     */
    @Path("")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    UniResp<Map<String, String>> uploadImg(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @FormDataParam("excelFile") MultipartFile upfile);

//    @Path("/ueditor")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    String ueditor(HttpServletRequest request);
}
