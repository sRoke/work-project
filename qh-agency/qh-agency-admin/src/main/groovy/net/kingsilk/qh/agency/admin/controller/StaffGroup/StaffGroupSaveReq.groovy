package net.kingsilk.qh.agency.admin.controller.StaffGroup

import io.swagger.annotations.ApiModelProperty
import net.kingsilk.qh.agency.domain.Base
import net.kingsilk.qh.agency.domain.Staff
import net.kingsilk.qh.agency.domain.StaffGroup

/**
 * Created by lit on 17-3-20.
 */
 class StaffGroupSaveReq extends Base {

    /**
     * 用户组名称
     */
    @ApiModelProperty(value = "用户组名称")
     String name;

    /**
     * 是否是系统预留用户组。如果是预留，则不允许删除
     */
     Boolean reserved;

    /**
     * 对应的员工
     */
     Set<Staff> staffs
     /**
      * 状态
      */
     Boolean status;

    /**
     * 描述
     */
     String desp;

     /**
     * 权限列表
     */
     Map<String, Boolean> authorMap=new HashMap<>();



     StaffGroup convertToStaffGroup(StaffGroup staffGroup) {
        staffGroup.setStatus(this.status)
        staffGroup.setName(this.getName());
        staffGroup.setDesp(this.getDesp());
        staffGroup.setAuthorities(convertAuthorMapToAuthorities(authorMap));
        staffGroup.setStaffs(this.getStaffs());
        staffGroup.setReserved(this.getReserved());
        return staffGroup;
    }


    static  Set<String> convertAuthorMapToAuthorities(Map<String, Boolean> authorMap) {
        Set<String> Authorities = new HashSet<>()
        for (String auth:authorMap.keySet()){
            if (authorMap.get(auth)){
                Authorities.add(auth)
            }
        }
        return Authorities
    }
}
