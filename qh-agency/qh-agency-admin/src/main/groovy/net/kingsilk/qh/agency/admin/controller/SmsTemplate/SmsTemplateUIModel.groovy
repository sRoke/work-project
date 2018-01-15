package net.kingsilk.qh.agency.admin.controller.SmsTemplate

import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.domain.SmsTemplate

/**
 * Created by zcw on 3/16/17.
 */
class SmsTemplateUIModel {

    @ApiParam(value = "id", required = false)
    String id;

    @ApiParam(value = "标题", required = false)
    String title

    @ApiParam(value = "发送平台", required = true, allowableValues = "ALI,YP")
    String platform

    @ApiParam(value = "模板描述", required = false)
    String description

    @ApiParam(value = "key", required = true)
    String key

    @ApiParam(value = "是否可用", required = true)
    boolean usable

    @ApiParam(value = "短信内容", required = true)
    String content
    
    @ApiParam(value = "默认参数", required = false)
    Map<String, Object> defaultParams

    @ApiParam(value = "阿里大于编码", required = false)
    String aliTemplateCode

    static SmsTemplateUIModel convert(SmsTemplate smsTemplate) {
        SmsTemplateUIModel model = new SmsTemplateUIModel()
        model.id = smsTemplate.id
        model.title = smsTemplate.title
        model.platform = smsTemplate.platform.code
        model.key = smsTemplate.key
        model.description = smsTemplate.description
        model.usable = smsTemplate.usable
        model.content = smsTemplate.content
        model.defaultParams = smsTemplate.defaultParams
        model.aliTemplateCode = smsTemplate.aliTemplateCode
        return model
    }
}
