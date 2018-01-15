package net.kingsilk.qh.agency.admin.api.partner.dto;

import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.admin.api.common.dto.BasePageResp;
import net.kingsilk.qh.agency.admin.api.order.dto.AddrResp;

import java.util.Date;

/**
 *
 */
public class PartnerPageResp extends BasePageResp {

    private String id;

    /**
     * 申请状态
     */
    private String partnerApplyStatus;

    /**
     * 申请时间
     */
    private String createDate;

    /**
     * 编号
     */
    private String seq;

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
     * 店铺地址。
     */
    @ApiParam(value = "店铺地址", required = true)
    private String adc;
    /**
     * 禁用/启用
     */
    private Boolean disabled;


    /**
     * 邀请码
     */
    @ApiParam(value = "邀请码", required = false)
    private String invitationCode;


    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartnerApplyStatus() {
        return partnerApplyStatus;
    }

    public void setPartnerApplyStatus(String partnerApplyStatus) {
        this.partnerApplyStatus = partnerApplyStatus;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getAdc() {
        return adc;
    }

    public void setAdc(String adc) {
        this.adc = adc;
    }
}
