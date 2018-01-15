package net.kingsilk.qh.agency.admin.controller.staff;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.core.StaffStatusEnum;

/**
 * Created by tpx on 17-3-16.
 */
@ApiModel(value = "StaffEnableReq")
class StaffEnableReq {

    @ApiParam(required = true, value = "员工的ID")
    String id;

    @ApiParam(required = true, value = "是否禁用")
    boolean disabled;

}
