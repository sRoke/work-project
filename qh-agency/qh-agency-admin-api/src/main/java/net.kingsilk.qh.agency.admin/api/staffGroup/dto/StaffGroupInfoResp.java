package net.kingsilk.qh.agency.admin.api.staffGroup.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.kingsilk.qh.agency.admin.api.common.dto.Base;
import net.kingsilk.qh.agency.admin.api.common.dto.Staff;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lit on 17-3-20.
 */
@ApiModel(value = "StaffGroupInfoResp")
public class StaffGroupInfoResp extends Base {
//    public StaffGroupInfoResp convertStaffGroupToResp(String source, StaffGroup staffGroup) {
//        if (staffGroup.invokeMethod("getStatus", new Object[0]).asBoolean()) {
//            this.setStatus("true");
//        } else {
//            this.setStatus("false");
//        }
//
//        this.setName(staffGroup.invokeMethod("getName", new Object[0]));
//        this.setStaffs(staffGroup.invokeMethod("getStaffs", new Object[0]));
//        this.setAuthor(staffGroup.invokeMethod("getAuthorities", new Object[0]));
//        this.setSource(source);
//        this.setDesp(staffGroup.invokeMethod("getDesp", new Object[0]));
//        this.setReserved(staffGroup.invokeMethod("getReserved", new Object[0]));
//        return this;
//    }


    /**
     * 用户组名称
     */
    @ApiModelProperty(value = "用")
    private String name;
    /**
     * 是否是系统预留用户组。如果是预留，则不允许删除
     */
    private Boolean reserved;
    /**
     * authorMap 角色权限三层map结构
     */
    private Set<String> author = new HashSet<String>();
    /**
     * 状态
     */
    private String status;
    /**
     * 对应的员工
     */
    private Set<Staff> staffs = new HashSet<Staff>();
    private String source;
    private String desp;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public Set<String> getAuthor() {
        return author;
    }

    public void setAuthor(Set<String> author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(Set<Staff> staffs) {
        this.staffs = staffs;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }
}
