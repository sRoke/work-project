package net.kingsilk.qh.agency.api.brandApp.partner.partnerStaff.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.util.Date;

/**
 *
 */
@ApiModel(value = "会员信息")
public class PartnerStaffInfoResp {

    /**
     * id
     */
    private String id;

    /**
     * userId
     */
    @ApiModelProperty(value = "userId")
    private String userId;

    /***
     * orgId
     */
    @ApiModelProperty(value = "orgId")
    private String orgId;

//    /**
//     * 真实姓名
//     */
//    @ApiModelProperty(value = "真实姓名")
//    private String realName;
//    /**
//     * 手机号
//     */
//    @ApiModelProperty(value = "手机号")
//    private String phone;
    /**
     * 用户标签。
     * <p>
     * 比如："代理商", "加盟商"
     */
    @ApiModelProperty(value = "代理商,加盟商")
    private String partnerType;
    @ApiModelProperty(value = "渠道商编号")
    private String partnerSeq;
    @ApiModelProperty(value = "渠道商id")
    private String partnerId;
//    /**
//     * 头像地址
//     */
//    @ApiModelProperty(value = "头像地址")
//    private String avatar;
//    /**
//     * 联系人
//     */
//    @ApiModelProperty(value = "联系人")
//    private String contacts;
//    /**
//     * 身份证号码
//     */
//    @ApiModelProperty(value = "身份证号码")
//    private String idNumber;
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
//    /**
//     * 店铺地址。
//     */
//    @ApiModelProperty(value = "店铺地址")
//    private String shopAddr;
//    /**
//     * 店铺地址在百度地中经度。
//     */
//    @ApiModelProperty(value = "店铺地址在百度地中经度")
//    private String shopAddrMapCoorX;
//    /**
//     * 店铺地址在百度地中维度。
//     */
//    @ApiModelProperty(value = "店铺地址在百度地中维度")
//    private String shopAddrMapCoorY;


    @ApiParam(value = "创建时间")
    private Date dateCreated;
    @ApiParam(value = "创建时间")
    private Date lastModifiedDate;

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getPartnerSeq() {
        return partnerSeq;
    }

    public void setPartnerSeq(String partnerSeq) {
        this.partnerSeq = partnerSeq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
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

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
