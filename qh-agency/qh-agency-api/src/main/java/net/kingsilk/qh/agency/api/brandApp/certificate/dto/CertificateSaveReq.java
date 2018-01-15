package net.kingsilk.qh.agency.api.brandApp.certificate.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

/**
 * Created by lit on 17/8/29.
 */
public class CertificateSaveReq {

    /**
     * 所属品牌商
     */
    @ApiParam(value = "所属品牌商")
    @ApiModelProperty(value = "所属品牌商")
    private String brandAppId;

    /**
     * 所属渠道商
     */
    @ApiParam(value = "所属渠道商")
    @ApiModelProperty(value = "所属渠道商")
    private String partnerId;

    /**
     *  授权书截图
     */
    @ApiParam(value = "授权书截图")
    @ApiModelProperty(value = "授权书截图")
    private String certificateImg;

    /**
     * 被授权人name
     */
    @ApiParam(value = "被授权人name")
    @ApiModelProperty(value = "被授权人name")
    private String realName;

    /**
     * 所授权的渠道商类型
     */
    private String prox;

    /**
     * 授权地址信息
     */
    @ApiParam(value = "授权地址信息")
    @ApiModelProperty(value = "授权地址信息")
    private String address;

    /**
     * 授权期限开始时间年
     */
    @ApiParam(value = "授权期限开始时间")
    @ApiModelProperty(value = "授权期限开始时间")
    private String startYear;

    /**
     * 授权期限开始时间月
     */
    @ApiParam(value = "授权期限开始时间")
    @ApiModelProperty(value = "授权期限开始时间")
    private String startMonth;

    /**
     * 授权期限开始时间日
     */
    @ApiParam(value = "授权期限开始时间日")
    @ApiModelProperty(value = "授权期限开始时间日")
    private String startDay;

    /**
     * 授权期限结束时间年
     */
    @ApiParam(value = "授权期限结束时间")
    @ApiModelProperty(value = "授权期限结束时间")
    private String endYear;

    /**
     * 授权期限结束时间月
     */
    @ApiParam(value = "授权期限结束时间")
    @ApiModelProperty(value = "授权期限结束时间")
    private String endMonth;

    /**
     * 授权期限结束时间日
     */
    @ApiParam(value = "授权期限结束时间")
    @ApiModelProperty(value = "授权期限结束时间")
    private String endDay;

    /**
     * 授权书合同编号
     */
    @ApiParam(value = "授权书合同编号")
    @ApiModelProperty(value = "授权书合同编号")
    private String seq;

    /**
     * 签署日期
     */
    @ApiParam(value = "签署日期")
    @ApiModelProperty(value = "签署日期")
    private String signDay;

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getProx() {
        return prox;
    }

    public void setProx(String prox) {
        this.prox = prox;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getSignDay() {
        return signDay;
    }

    public void setSignDay(String signDay) {
        this.signDay = signDay;
    }
}
