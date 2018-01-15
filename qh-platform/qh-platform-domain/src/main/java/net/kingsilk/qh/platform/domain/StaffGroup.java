package net.kingsilk.qh.platform.domain;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 角色。
 */
@Document
public class StaffGroup extends Base {

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
    @DBRef
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
