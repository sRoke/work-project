package net.kingsilk.qh.agency.domain;

import net.kingsilk.qh.agency.core.PartnerApplyStatusEnum;
import net.kingsilk.qh.agency.core.PartnerTypeEnum;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 渠道商表。
 */
@Document
//@CompoundIndexes({
//        @CompoundIndex(unique = true, def = "{'brandId': 1, 'userId': 1}")
//})
public class Partner extends Base {

    /**
     * 所属品牌。
     */
    private String brandAppId;

    /**
     * 渠道商负责人的用户ID。
     */
    private String userId;

    /**
     * 编号
     */
    private String seq;

    /**
     * 第几个加入的渠道商
     */
    private  Integer placeNum;
    /**
     * 组织ID
     */
    private String orgId;

    /**
     * 渠道商类型
     */
    private PartnerTypeEnum partnerTypeEnum;

    /**
     * 上级渠道商Id
     */
    @DBRef
    private Partner parent;

    /**
     * 身份证号码
     */
    private String idNo;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 渠道商申请状态
     */
    private PartnerApplyStatusEnum partnerApplyStatus;

    /**
     * 是否已经禁用。
     * <p>
     * true - 已经禁用。
     */
    private boolean disabled;

    /**
     * 备注
     */
    private String memo;

    /**
     * 店铺地址 adc 6位编码。
     */
    private String adc;

    /**
     * 店铺名称
     */
    private String shopName;

    //TODO 收货地址、退货默认地址

    /**
     * 邀请码
     */
    private String invitationCode;
    /**
     * 店铺地址在百度地中经度。
     */
    private String shopAddrMapCoorX;

    /**
     * 店铺地址在百度地中纬度。
     */
    private String shopAddrMapCoorY;

    /**
     * 关联的门店brandAppId
     */
    private String shopBrandAppId;

    /**
     * 关联的门店shopId
     */
    private String shopId;


    // --------------------------------------- getter && setter

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Integer getPlaceNum() {
        return placeNum;
    }

    public void setPlaceNum(Integer placeNum) {
        this.placeNum = placeNum;
    }

    public PartnerTypeEnum getPartnerTypeEnum() {
        return partnerTypeEnum;
    }

    public void setPartnerTypeEnum(PartnerTypeEnum partnerTypeEnum) {
        this.partnerTypeEnum = partnerTypeEnum;
    }

    public Partner getParent() {
        return parent;
    }

    public void setParent(Partner parent) {
        this.parent = parent;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public PartnerApplyStatusEnum getPartnerApplyStatus() {
        return partnerApplyStatus;
    }

    public void setPartnerApplyStatus(PartnerApplyStatusEnum partnerApplyStatus) {
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

    public String getAdc() {
        return adc;
    }

    public void setAdc(String adc) {
        this.adc = adc;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
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
