package net.kingsilk.qh.agency.admin.api.common.dto;


import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 角色。
 */
public class StaffGroup extends Base {

    /**
     * 所属公司。
     */
    private String brandId;

    /**
     * 用户组名称
     */
    private String name;

    /**
     * 权限列表
     */
    private Set<String> authorities = new LinkedHashSet<>();

    /**
     * 对应的员工
     */
    private Set<Staff> staffs = new LinkedHashSet<>();

    /**
     * 是否是系统预留用户组。如果是预留，则不允许删除
     */
    private Boolean reserved;

    /**
     * 描述
     */
    private String desp;

    /**
     * 状态
     */
    private Boolean status;


    // --------------------------------------- getter && setter


    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Set<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(Set<Staff> staffs) {
        this.staffs = staffs;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
