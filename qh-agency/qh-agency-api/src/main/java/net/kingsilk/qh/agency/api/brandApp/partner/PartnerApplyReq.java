package net.kingsilk.qh.agency.api.brandApp.partner;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

/**
 * Created by lit on 17/7/19.
 */
@ApiModel
public class PartnerApplyReq {

//    /**
//     * 所属品牌。
//     */
//    @ApiParam(value = "当前页数", required = false)
//    private String brandId;

//    /**
//     * 渠道商负责人
//     */
//    @ApiParam(value = "渠道商负责人", required = true)
//    private String userId;

    /**
     * 渠道商类型
     * 比如："代理商", "加盟商"
     */
    @ApiParam(value = "渠道商类型", required = true)
    private String partnerType;

    /**
     * 手机号
     */
    @ApiParam(value = "手机号", required = true)
    private String phone;

    @ApiParam(value = "真实姓名", required = true)
    private String realName;


    /**
     * 店铺地址。
     */
    @ApiParam(value = "店铺地址", required = true)
    private String shopAddr;

    /**
     * 身份证号码
     */
    @ApiParam(value = "身份证号码", required = true)
    private String idNo;


    /**
     * 邀请码
     */
    @ApiParam(value = "邀请码", required = false)
    private String invitationCode;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }
}
