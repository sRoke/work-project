package net.kingsilk.qh.agency.admin.api.staffGroup.dto;

import io.swagger.annotations.ApiModelProperty;
import net.kingsilk.qh.agency.admin.api.common.dto.Base;
import net.kingsilk.qh.agency.admin.api.common.dto.Staff;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by lit on 17-3-20.
 */
public class StaffGroupSaveReq extends Base {
//    public StaffGroup convertToStaffGroup(StaffGroup staffGroup) {
//        staffGroup.invokeMethod("setStatus", new Object[]{this.status});
//        staffGroup.invokeMethod("setName", new Object[]{this.getName()});
//        staffGroup.invokeMethod("setDesp", new Object[]{this.getDesp()});
//        staffGroup.invokeMethod("setAuthorities", new Object[]{StaffGroupSaveReq.convertAuthorMapToAuthorities(authorMap)});
//        staffGroup.invokeMethod("setStaffs", new Object[]{this.getStaffs()});
//        staffGroup.invokeMethod("setReserved", new Object[]{this.getReserved()});
//        return staffGroup;
//    }

//    public static Set<String> convertAuthorMapToAuthorities(Map<String, Boolean> authorMap) {
//        Set<String> Authorities = new HashSet<String>();
//        for (String auth : authorMap.keySet()) {
//            if (authorMap.get(auth)) {
//                ((HashSet<String>) Authorities).add(auth);
//            }
//
//        }
//
//        return Authorities;
//    }


    /**
     * 用户组名称
     */
    @ApiModelProperty(value = "用户组名称")
    private String name;
    /**
     * 是否是系统预留用户组。如果是预留，则不允许删除
     */
    private Boolean reserved;
    /**
     * 对应的员工
     */
    private Set<Staff> staffs;
    /**
     * 状态
     */
    private Boolean status;
    /**
     * 描述
     */
    private String desp;
    /**
     * 权限列表
     */
    private Map<String, Boolean> authorMap = new HashMap<String, Boolean>();


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

    public Set<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(Set<Staff> staffs) {
        this.staffs = staffs;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public Map<String, Boolean> getAuthorMap() {
        return authorMap;
    }

    public void setAuthorMap(Map<String, Boolean> authorMap) {
        this.authorMap = authorMap;
    }
}
