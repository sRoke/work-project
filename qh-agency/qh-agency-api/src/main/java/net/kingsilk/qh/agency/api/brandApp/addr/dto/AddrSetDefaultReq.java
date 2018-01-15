package net.kingsilk.qh.agency.api.brandApp.addr.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

/**
 * Created by zcw on 3/16/17.
 */
@ApiModel
public class AddrSetDefaultReq {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 地址id
     */
    @ApiParam(value = "id", required = true)
    private String id;
}
