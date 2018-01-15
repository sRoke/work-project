package net.kingsilk.qh.agency.api.brandApp.partner.partnerStaff.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

/**
 *
 */
@ApiModel(
        value = "PartnerStaffEnableReq"
)
public class PartnerStaffEnableReq {

    @ApiParam(
            required = true,
            value = "会员的ID"
    )
    @QueryParam(value = "id")
    private String id;

    @ApiParam(
            required = true,
            value = "状态"
    )
    @QueryParam(value = "disabled")
    private boolean disabled;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
