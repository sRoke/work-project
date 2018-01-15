package net.kingsilk.qh.agency.admin.controller.StaffGroup

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import net.kingsilk.qh.agency.admin.api.common.dto.Staff
import net.kingsilk.qh.agency.admin.api.common.dto.StaffGroup
import net.kingsilk.qh.agency.domain.Base
//import net.kingsilk.qh.agency.domain.Staff
//import net.kingsilk.qh.agency.domain.staffGroup

/**
 * Created by lit on 17-3-20.
 */
@ApiModel(value = "StaffGroupInfoResp")
class StaffGroupInfoResp extends Base {

    /**
     * 用户组名称
     */
    @ApiModelProperty(value = "用")
    String name;

    /**
     * 是否是系统预留用户组。如果是预留，则不允许删除
     */
    Boolean reserved;

    /**
     * authorMap 角色权限三层map结构
     */

    Set<String> author = new HashSet<>()

    /**
     * 状态
     */
    String status;
    /**
     * 对应的员工
     */
    Set<Staff> staffs = new HashSet<>();

    String source;

    String desp;


    StaffGroupInfoResp convertStaffGroupToResp(String source, StaffGroup staffGroup) {
        if (staffGroup.getStatus()){
            this.setStatus("true")
        }else {
            this.setStatus("false")
        }
        this.setName(staffGroup.getName());
        this.setStaffs(staffGroup.getStaffs())
        this.setAuthor(staffGroup.getAuthorities())
        this.setSource(source)
        this.setDesp(staffGroup.getDesp())
        this.setReserved(staffGroup.getReserved());
        return this;
    }
}
