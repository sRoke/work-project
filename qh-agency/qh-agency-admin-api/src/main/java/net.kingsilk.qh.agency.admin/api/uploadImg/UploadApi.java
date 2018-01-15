package net.kingsilk.qh.agency.admin.api.uploadImg;

import io.swagger.annotations.Api;
import net.kingsilk.qh.agency.admin.api.UniResp;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@RequestMapping("/uploadImg")
@Api( // 用在类上，用于设置默认值
        tags = "uploadImg",
        consumes = MediaType.APPLICATION_JSON,
        produces = MediaType.MULTIPART_FORM_DATA,
        protocols = "http,https",
        description = "公共的上传图片"
)
@Path("/uploadImg")
@Component
interface UploadApi {
    /**
     * 上传图片
     */
    @Path("/uploadImg")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    UniResp<Map<String, String>> uploadImg(@FormDataParam("excelFile") MultipartFile upfile);

    @Path("/ueditor")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    String ueditor(HttpServletRequest request);
}
