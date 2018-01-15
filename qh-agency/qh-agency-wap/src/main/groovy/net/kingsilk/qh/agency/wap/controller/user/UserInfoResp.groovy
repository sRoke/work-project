package net.kingsilk.qh.agency.wap.controller.user

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import net.kingsilk.qh.agency.domain.PartnerStaff

@ApiModel
class UserInfoResp {

    @ApiModelProperty(value = "userId")
    String userId

    @ApiModelProperty(value = "联系人")
    String contacts

    @ApiModelProperty(value = "真实姓名")
    String realName

    @ApiModelProperty(value = "手机号")
    String phone

    @ApiModelProperty(value = "url，没什么用")
    String url

    @ApiModelProperty(value = "头像路径,没什么卵用")
    String avatar

    void convert(PartnerStaff member) {
        userId = member.userId
        contacts = member.contacts
        realName = member.realName
        phone = member.phone
        url = "二维码地址什么的，你们看着办"
        avatar = "这是头像地址，暂时没有"
    }
}
