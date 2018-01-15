package net.kingsilk.qh.agency.admin.api.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.kingsilk.qh.agency.core.PartnerTypeEnum;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@ApiModel(value = "会员信息")
public class MemberInfo {
    /**
     * 所属公司。
     */
    @ApiModelProperty(
            value = "会员所属公司ID"
    )
    private String companyId;

    /**
     * 所属公司。
     */
    @ApiModelProperty(
            value = "会员所属公司名字"
    )
    private String companyName;
    /**
     * 账户
     */
    @ApiModelProperty(value = "会员对应账户")
    private String userId;
    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String realName;
    /**
     * 用户标签。
     * <p>
     * 比如："代理商", "加盟商"
     */
    @ApiModelProperty(value = "代理商,加盟商")
    private Set<PartnerTypeEnum> tags = new HashSet<>();
    /**
     * 头像地址
     */
    @ApiModelProperty(value = "头像地址")
    private String avatar;
    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码")
    private String idNo;
    /**
     * 是否已经禁用。
     * <p>
     * true - 已经禁用。
     */
    @ApiModelProperty(value = "是否已经禁用")
    private boolean disabled;
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

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

    public Set<PartnerTypeEnum> getTags() {
        return tags;
    }

    public void setTags(Set<PartnerTypeEnum> tags) {
        this.tags = tags;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
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
