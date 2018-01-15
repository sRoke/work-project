package net.kingsilk.qh.agency.admin.api.staff.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

/**
 *
 */
@ApiModel(value = "StaffEnableReq")
public class StaffEnableReq {

    @ApiParam(required = true, value = "员工的ID")
    @QueryParam(value = "id")
    private String id;
    @ApiParam(required = true, value = "是否禁用")
    /**
     * true启用
     * false禁用
     */
    @QueryParam(value = "status")
    private boolean status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
