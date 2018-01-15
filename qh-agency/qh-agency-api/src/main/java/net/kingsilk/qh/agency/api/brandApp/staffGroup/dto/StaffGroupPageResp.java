package net.kingsilk.qh.agency.api.brandApp.staffGroup.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.kingsilk.qh.agency.api.common.dto.Base;
//import sun.jvm.hotspot.debugger.Page;

/**
 *
 */
@ApiModel(value = "StaffGroupInfoResp")
public class StaffGroupPageResp extends Base {


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 用户组名称
     */
    @ApiModelProperty(value = "用户组名称")
    private String name;
    /**
     * 对应的员工数量
     */
    @ApiModelProperty(value = "对应的员工数量")
    private int staffSize;
    @ApiModelProperty(value = "状态")
    private Boolean status;

    @ApiModelProperty(value = "描述")
    private String desp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStaffSize() {
        return staffSize;
    }

    public void setStaffSize(int staffSize) {
        this.staffSize = staffSize;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }
}
