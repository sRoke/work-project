package net.kingsilk.qh.agency.admin.controller.SmsTemplate

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import net.kingsilk.qh.agency.admin.api.UniResp
import net.kingsilk.qh.agency.core.SmsPlatformEnum
import net.kingsilk.qh.agency.domain.SmsTemplate
import net.kingsilk.qh.agency.service.CommonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*

/**
 * Created by zcw on 17-3-16.
 */
@RestController()
@RequestMapping("/api/smsTemplate")
@Api(
        tags = "smsTemplate",
        consumes = MediaType.APPLICATION_ATOM_XML_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        protocols = "http,https",
        description = "短信模板相关API"
)
class SmsTemplateController {

    @Autowired
    CommonService commonService

    @Autowired
    MongoTemplate mongoTemplate

    @RequestMapping(path = "/list",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "短信模板列表",
            nickname = "短信模板列表",
            notes = "短信模板列表"
    )
    @ApiResponses([@ApiResponse(
            code = 200,
            message = "正常结果")
    ])
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF')")
    UniResp<SmsTemplateListResp> list(@RequestParam SmsTemplateListReq req) {
        Criteria criteria = new Criteria()
        Query query = Query.query(criteria)
        query.with(new Sort(Sort.Direction.DESC, "dateCreated"))
        Page<SmsTemplate> page = commonService.domainPages(SmsTemplate, query, req.curPage, req.pageSize)
        SmsTemplateListResp resp = new SmsTemplateListResp()
        resp.convert(page)
        return new UniResp<SmsTemplateListResp>(status: 200, data: resp)
    }

    @RequestMapping(path = "/info",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "短信模板详情",
            nickname = "短信模板详情",
            notes = "短信模板详情"
    )
    @ApiResponses([@ApiResponse(
            code = 200,
            message = "正常结果")
    ])
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF')")
    UniResp<SmsTemplateUIModel> info(@RequestParam SmsTemplateInfoReq req) {
        SmsTemplate smsTemplate = mongoTemplate.findById(req.id, SmsTemplate)
        Assert.notNull(smsTemplate, "模板id错误")
        //SmsTemplateInfoResp resp = new SmsTemplateInfoResp()
        SmsTemplateUIModel resp = SmsTemplateUIModel.convert(smsTemplate)
        return new UniResp<SmsTemplateInfoResp>(status: 200, data: resp)
    }

    @RequestMapping(path = "/save",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "保存短信模板",
            nickname = "保存短信模板",
            notes = "保存短信模板"
    )
    @ApiResponses([@ApiResponse(
            code = 200,
            message = "正常结果")
    ])
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF')")
    UniResp<String> save(@RequestBody SmsTemplateSaveReq req) {
        SmsTemplate smsTemplate = new SmsTemplate()
        smsTemplate.dateCreated = new Date()
        if (req.id) {
            smsTemplate = mongoTemplate.findById(req.id, SmsTemplate)
            Assert.notNull(smsTemplate, "模板id错误")
        }
        smsTemplate.title = req.title
        Assert.isTrue(SmsPlatformEnum.values()*.code.contains(req.platform), "短信平台错误")
        smsTemplate.platform = SmsPlatformEnum.valueOf(req.platform)
        smsTemplate.description = req.description
        smsTemplate.key = req.key
        smsTemplate.usable = req.usable
        smsTemplate.content = req.content
        smsTemplate.defaultParams = req.defaultParams
        smsTemplate.aliTemplateCode = req.aliTemplateCode
        mongoTemplate.save(smsTemplate)
        return new UniResp<SmsTemplateSaveResp>(status: 200, data: "保存成功")
    }
}
