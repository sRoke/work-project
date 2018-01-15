package net.kingsilk.qh.agency.api.brandApp.partner;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

/**
 *
 */
@ApiModel(value = "渠道商基础信息返回")
public class PartnerInfoResp {

    @ApiParam(required = true, value = "渠道商的ID")
    private String id;
    /**
     * 所属品牌。
     */
    @ApiParam(required = true, value = "所属品牌")
    private String brandNameCN;

    private String parentId;

    private String parentDesp;
    /**
     * 渠道商负责人
     */
    @ApiParam(required = true, value = "渠道商负责人ID")
    private String userId;

    /**
     * 渠道商组织
     */
    @ApiParam(required = true, value = "渠道商组织")
    private String orgId;

    @ApiParam(required = true, value = "编号")
    private String seq;

    /**
     * 身份证号码
     */
    private String idNo;

    private String invitationCode;
    /**
     * 渠道商类型
     * 比如："代理商", "加盟商"
     */
    @ApiParam(required = true, value = "渠道商类型描述")
    private String partnerType;

    @ApiParam(required = true, value = "渠道商类型")
    private String partnerTypeEnum;

    @ApiParam(required = true, value = "真实姓名")
    private String realName;


    @ApiParam(required = true, value = "手机号")
    private String phone;


    @ApiParam(required = true, value = "头像URL")
    private String avatar;


    @ApiParam(required = true, value = "渠道商申请的状态")
    private String partnerApplyStatus;

    /**
     * 申请时间
     */
    private String createDate;

    /**
     * 是否已经禁用。
     * <p>
     * true - 已经禁用。
     */
    @ApiParam(required = true, value = "是否已经禁用")
    private boolean disabled;
    @ApiParam(required = true, value = "商铺名称")
    private String shopName;


    @ApiParam(required = true, value = "备注")
    private String memo;


    @ApiParam(required = true, value = "店铺地址")
    private String shopAddr;

    @ApiParam(required = true, value = "店铺地址六位编码")
    private String adc;

    @ApiParam(required = true, value = "铺地址城市编号")
    private String cityNo;

    @ApiParam(required = true, value = "店铺地址省编号")
    private String provinceNo;

    private String certificateStatus;

    private String shopBrandAppId;

    private String shopId;

    public String getParentDesp() {
        return parentDesp;
    }

    public void setParentDesp(String parentDesp) {
        this.parentDesp = parentDesp;
    }

    public String getPartnerTypeEnum() {
        return partnerTypeEnum;
    }

    public void setPartnerTypeEnum(String partnerTypeEnum) {
        this.partnerTypeEnum = partnerTypeEnum;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandNameCN() {
        return brandNameCN;
    }

    public void setBrandNameCN(String brandNameCN) {
        this.brandNameCN = brandNameCN;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPartnerApplyStatus() {
        return partnerApplyStatus;
    }

    public void setPartnerApplyStatus(String partnerApplyStatus) {
        this.partnerApplyStatus = partnerApplyStatus;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
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

    public String getAdc() {
        return adc;
    }

    public void setAdc(String adc) {
        this.adc = adc;
    }

    public String getCityNo() {
        return cityNo;
    }

    public void setCityNo(String cityNo) {
        this.cityNo = cityNo;
    }

    public String getProvinceNo() {
        return provinceNo;
    }

    public void setProvinceNo(String provinceNo) {
        this.provinceNo = provinceNo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCertificateStatus() {
        return certificateStatus;
    }

    public void setCertificateStatus(String certificateStatus) {
        this.certificateStatus = certificateStatus;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getShopBrandAppId() {
        return shopBrandAppId;
    }

    public void setShopBrandAppId(String shopBrandAppId) {
        this.shopBrandAppId = shopBrandAppId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }


}
