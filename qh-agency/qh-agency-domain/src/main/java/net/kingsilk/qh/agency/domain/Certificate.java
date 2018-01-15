package net.kingsilk.qh.agency.domain;

import net.kingsilk.qh.agency.core.PartnerTypeEnum;

import java.util.Date;

/**
 * Created by lit on 17/8/29.
 */
public class Certificate extends Base {
    /**
     * 所属品牌商
     */
    private String brandAppId;

    /**
     * 所属渠道商
     */
    private String partnerId;

    /**
     *  授权书截图
     */
    private String certificateImg;

    /**
     * 关联的授权书模板
     */
    private String certificateTemplateId;

    /**
     * 被授权人name
     */
    private String realName;

    /**
     * 授权地址abc编码
     */
    private String adcNum;

    /**
     * 授权地址信息
     */
    private String shopAdress;

    /**
     * 所授权的渠道商类型
     */
    private PartnerTypeEnum partnerType;

    /**
     * 授权期限开始时间
     */
    private Date startTime;

    /**
     * 授权期限结束时间
     */
    private Date endTime;

    /**
     * 授权书合同编号
     */
    private String seq;

    /**
     * 签署日期
     */
    private Date signDate;

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getCertificateImg() {
        return certificateImg;
    }

    public void setCertificateImg(String certificateImg) {
        this.certificateImg = certificateImg;
    }

    public String getCertificateTemplateId() {
        return certificateTemplateId;
    }

    public void setCertificateTemplateId(String certificateTemplateId) {
        this.certificateTemplateId = certificateTemplateId;
    }

    public String getShopAdress() {
        return shopAdress;
    }

    public void setShopAdress(String shopAdress) {
        this.shopAdress = shopAdress;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAdcNum() {
        return adcNum;
    }

    public void setAdcNum(String adcNum) {
        this.adcNum = adcNum;
    }

    public PartnerTypeEnum getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(PartnerTypeEnum partnerType) {
        this.partnerType = partnerType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }
}
