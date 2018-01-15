package net.kingsilk.qh.agency.admin.api.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 */
@ApiModel(value = "会员信息")
public class MemberInfoResp  {

    /**
     * id
     */
    private String id;

    /**
     * 账户
     */
    @ApiModelProperty(value = "账户")
    private String userId;
    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String realName;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;
    /**
     * 用户标签。
     * <p>
     * 比如："代理商", "加盟商"
     */
    @ApiModelProperty(value = "代理商,加盟商")
    private String tags;
    /**
     * 头像地址
     */
    @ApiModelProperty(value = "头像地址")
    private String avatar;
    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private String contacts;
    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码")
    private String idNumber;
    /**
     * 是否已经禁用。
     * <p>
     * true - 已经禁用。
     */
    @ApiModelProperty(value = "是否已经禁用")
    private String disabled;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String memo;
    /**
     * 店铺地址。
     */
    @ApiModelProperty(value = "店铺地址")
    private String shopAddr;
    /**
     * 店铺地址在百度地中经度。
     */
    @ApiModelProperty(value = "店铺地址在百度地中经度")
    private String shopAddrMapCoorX;
    /**
     * 店铺地址在百度地中维度。
     */
    @ApiModelProperty(value = "店铺地址在百度地中维度")
    private String shopAddrMapCoorY;

    private String partnerType;

    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    //    public MemberInfoResp convertMemberToResp(Member member) {
//        this.setMemo(member.getMemo());
//        this.setUserId(member.getUserId());
//        this.setId(member.getId());
//        this.setRealName(member.getRealName());
//        this.setPhone(member.getPhone());
//        this.setTags(member.getMemberTypeEnum().getCode());
//        this.setAvatar(member.getAvatar());
//        this.setIdNo(member.getIdNo());
//        if (member.isDisabled()) {
//            this.setDisabled("true");
//        } else {
//            this.setDisabled("false");
//        }
//
//        this.setShopAddr(member.getShopAddr());
//        this.setShopAddrMapCoorX(member.getShopAddrMapCoorX());
//        this.setShopAddrMapCoorY(member.getShopAddrMapCoorY());
//        return this;
//    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }

    public String getShopAddrMapCoorX() {
        return shopAddrMapCoorX;
    }

    public void setShopAddrMapCoorX(String shopAddrMapCoorX) {
        this.shopAddrMapCoorX = shopAddrMapCoorX;
    }

    public String getShopAddrMapCoorY() {
        return shopAddrMapCoorY;
    }

    public void setShopAddrMapCoorY(String shopAddrMapCoorY) {
        this.shopAddrMapCoorY = shopAddrMapCoorY;
    }

}
