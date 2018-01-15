package net.kingsilk.qh.agency.admin.controller.uploadImg

import io.swagger.annotations.Api
import net.kingsilk.qh.agency.admin.api.UniResp
import net.kingsilk.qh.agency.core.MsgCodeEnum
import net.kingsilk.qh.agency.domain.YunFile
import net.kingsilk.qh.agency.service.UploadService
//import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.MediaType
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

import javax.servlet.http.HttpServletRequest

/**
 * 公共的上传图片
 */
/**
 * Created by tpx on 17-3-14.
 */
@RestController()
@RequestMapping("/api/uploadImg")
@Api( // 用在类上，用于设置默认值
        tags = "uploadImg",
        consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        protocols = "http,https",
        description = "公共的上传图片"
)
class UploadImgController {

    @Autowired
    private Environment env;

    @Autowired
    MongoTemplate mongoTemplate

    @Autowired
    UploadService uploadService;

    /**
     * 上传图片
     */
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('QINIU_U')")
    @RequestMapping(path = "/uploadImg",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    UniResp<Map<String, String>> uploadImg( @RequestPart("excelFile") MultipartFile upfile) {
        byte[] bytes = null;
        try {
//            bytes = IOUtils.toByteArray(upfile.getInputStream())
        } catch (Exception e1) {
            Assert.notNull(bytes, "暂不支持此类型的图片");
        }
        float size = bytes.length / 1024;

        Assert.isTrue(size <= 5192, "图片大小不能大于5M");
        YunFile yunFile = uploadService.qiNiuUploadCustom(bytes);
        Map<String, String> resultObj = new HashMap<>()
        if (yunFile) {
            resultObj.put("code",MsgCodeEnum.SUCCESS.getCode())
            resultObj.put("key",yunFile.key)
            resultObj.put("file_path",env.getProperty("qh.qiniu.prefix") + yunFile.key)
            return new UniResp(status: 200, data: resultObj);

        } else {
            resultObj.put("code",MsgCodeEnum.ERROR.getCode())
            resultObj.put("msg","上传图片失败")
            return new UniResp(status: 200, data: resultObj);
        }
    }
    /**
     * ueditor使用的参数返回值
     * @return ueditor的参数
     */
    @RequestMapping(path = "/ueditor",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    def ueditor(HttpServletRequest request) {
//        imgType=BATCH_IMG&X-Requested-With=XMLHttpRequest&_umeditor

        return [
                imageActionName  : "uploadImg",
                "imageAllowFiles": [".png", ".jpg", ".jpeg", ".gif", ".bmp"],
                serverUrl        : env.getProperty("qh.url") + request.getContextPath() +
                        "/api/uploadImg/uploadImg?imgType=&X-Requested-With=XMLHttpRequest&_umeditor=true",
                imageFieldName   : "upfile",
                imageUrlPrefix   : env.getProperty("qh.qiniu.prefix")
        ]
    }
}
