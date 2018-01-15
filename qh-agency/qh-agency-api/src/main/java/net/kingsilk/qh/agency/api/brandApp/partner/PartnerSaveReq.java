package net.kingsilk.qh.agency.api.brandApp.partner;

import io.swagger.annotations.ApiParam;

/**
 *
 */
public class PartnerSaveReq {
    //    @ApiParam(value = "渠道商的ID")
//    private String id;
    @ApiParam(required = true, value = "渠道商类型")
    private String partnerType;


    @ApiParam(required = true, value = "真实姓名")
    private String realName;


    @ApiParam(required = true, value = "手机号")
    private String phone;


    @ApiParam(required = true, value = "店铺地址")
    private String shopAddr;

    /**
     * 上级渠道商ID
     */
    @ApiParam(required = true, value = "上级渠道商ID")
    private String parentId;


    /**
     * 身份证号码
     */
    @ApiParam(required = true, value = "身份证号码")
    private String idNo;
    /**
     * 邀请码
     */
    @ApiParam(required = true, value = "邀请码")
    private String invitationCode;

    @ApiParam(required = true, value = "邀请码")
    private String applyStatus;


    @ApiParam(required = true, value = "邀请码")
    private String shopName;


//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }


    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
