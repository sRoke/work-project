package net.kingsilk.qh.shop.api.controller.common;

import net.kingsilk.qh.shop.api.controller.cart.dto.Base;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lit on 17/8/24.
 */

public class Staff extends Base {

    /**
     * 所属品牌
     */
    private String brandAppId;

    /**
     * 所属渠道商
     */
    private String partnerId;

    /**
     * 账户ID
     */
    private String userId;

    /**
     * 当前子系统内是否禁用。
     * <p>
     * true - 已经禁用。
     */
    private String disabled;

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

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
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
