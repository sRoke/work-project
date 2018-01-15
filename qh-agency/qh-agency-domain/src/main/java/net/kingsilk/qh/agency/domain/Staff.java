package net.kingsilk.qh.agency.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * 品牌商员工
 */
@Document
//@CompoundIndexes({
//        @CompoundIndex(unique = true, def = "{'brandAppId': 1, 'userId': 1}")
//})
public class Staff extends Base {

    /**
     * 所属品牌
     */
    private String brandAppId;

    /**
     * 账户ID
     */
    private String userId;

    /**
     * 是否已经禁用。
     * <p>
     * true - 已经禁用。
     */
    private boolean disabled;

    /**
     * 权限列表。
     * 暂时不用,使用用户组的权限
     */
    private Set<String> authorities = new HashSet<>();

    /**
     * 备注
     */
    private String memo;

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
