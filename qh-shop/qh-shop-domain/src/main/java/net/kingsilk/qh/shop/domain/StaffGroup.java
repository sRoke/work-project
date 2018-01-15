package net.kingsilk.qh.shop.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lit on 17/11/6.
 */
@Document
public class StaffGroup extends Base {
    /**
     * 应用的Id。
     */
    private String brandAppId;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色包含的员工
     */
    private Set<Staff> staffS = new HashSet<>();

    /**
     * 权限列表
     */
    private Set<String> authorities = new HashSet<>();

    /**
     * 是否系统预留角色
     */
    private Boolean reserved;

    /**
     * 描述
     */
    private String desp;

    /**
     * 是否禁用
     */
    private Boolean enable;

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Staff> getStaffS() {
        return staffS;
    }

    public void setStaffS(Set<Staff> staffS) {
        this.staffS = staffS;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
