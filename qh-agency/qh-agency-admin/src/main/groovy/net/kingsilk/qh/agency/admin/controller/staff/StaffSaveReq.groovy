package net.kingsilk.qh.agency.admin.controller.staff

import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.domain.Staff;

/**
 * Created by tpx on 17-3-20.
 */
public class StaffSaveReq {

     String id;

    /**
     * 账户
     */
    @ApiParam(value = "用户id")
     String userId;

    /**
     * 真实姓名。 第一次创建时从 UserDetails 中复制得来
     */
    @ApiParam(value = "用户姓名")
     String realName;

    @ApiParam(value = "用户电话 ")
     String phone;

    @ApiParam(value = "用户组Id")
     List<String> staffGroupIds = new ArrayList<String>();
    /**
     * 是否已经禁用。
     * <p>
     * true - 已经禁用。
     */
    @ApiParam(value = "是否禁用")
     boolean disabled;



    public Staff convertReqToStaff(Staff staff) {
        staff.realName = realName
        staff.phone = phone
        staff.disabled = disabled
        staff.userId = userId
        return staff;
    }

}
