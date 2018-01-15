package net.kingsilk.qh.agency.api.brandApp.certificate.dto;

import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniPageReq;
import net.kingsilk.qh.agency.core.PartnerTypeEnum;

import javax.ws.rs.QueryParam;
import java.util.Date;

/**
 * Created by lit on 17/8/29.
 */
public class CertificatePageResp extends UniPageReq {

    /**
     * 所属品牌商
     */
    @ApiParam(value = "所属品牌商")
    @QueryParam(value = "brandAppId")
    private String brandAppId;

    /**
     * 所属渠道商
     */
    @ApiParam(value = "所属渠道商")
    @QueryParam(value = "partnerId")
    private String partnerId;

    /**
     *  授权书截图
     */
    @ApiParam(value = "授权书截图")
    @QueryParam(value = "certificateImg")
    private String certificateImg;

    /**
     * 关联的授权书模板
     */
    @ApiParam(value = "关联的授权书模板")
    @QueryParam(value = "certificateTemplateId")
    private String certificateTemplateId;

    /**
     * 被授权人name
     */
    @ApiParam(value = "被授权人name")
    @QueryParam(value = "realName")
    private String realName;

    /**
     * 授权地址信息
     */
    @ApiParam(value = "授权地址信息")
    @QueryParam(value = "adcNum")
    private String adcNum;

    /**
     * 所授权的渠道商类型
     */
    @ApiParam(value = "所授权的渠道商类型")
    @QueryParam(value = "partnerType")
    private PartnerTypeEnum partnerType;

    /**
     * 授权期限开始时间
     */
    @ApiParam(value = "授权期限开始时间")
    @QueryParam(value = "startTime")
    private Date startTime;

    /**
     * 授权期限结束时间
     */
    @ApiParam(value = "授权期限结束时间")
    @QueryParam(value = "endTime")
    private Date endTime;

    /**
     * 授权书合同编号
     */
    @ApiParam(value = "授权书合同编号")
    @QueryParam(value = "seq")
    private String seq;

    /**
     * 签署日期
     */
    @ApiParam(value = "签署日期")
    @QueryParam(value = "signDate")
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
