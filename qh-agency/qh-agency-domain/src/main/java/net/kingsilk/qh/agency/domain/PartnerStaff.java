package net.kingsilk.qh.agency.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * 渠道商员工信息
 * <p>
 * 这里只管理员工权限。至于是不是渠道商员工，则在 OAuth 的表中管理。
 */
//@CompoundIndexes({
//        @CompoundIndex(unique = true, def = "{'brandId': 1, 'partner': 1, 'userId': 1}")
//})
@Document
public class PartnerStaff extends Base {
    /**
     * 所属品牌
     */
    private String brandAppId;

    /**
     * 所属渠道商
     */
    @DBRef
    private Partner partner;

    /**
     * 账户ID
     */
    private String userId;

    /**
     * 当前子系统内是否禁用。
     * <p>
     * true - 已经禁用。
     */
    private boolean disabled;

    /**
     * 权限列表。
     * 暂时不用,使用用户组的权限
     */
    //FIXME 之后可能会用到，暂时保留
    private Set<String> authorities = new HashSet<>();

    /**
     * 备注
     */
    private String memo;

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
