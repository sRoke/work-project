package net.kingsilk.qh.agency.admin.api.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

@ApiModel(value = "地址请求")
public class AddrReq {

    @ApiParam(value = "地区编码", required = false)
    @QueryParam(value = "typeNo")
    private String typeNo;
    @ApiParam(value = "级别")
    @QueryParam(value = "level")
    private String level;

    public String getTypeNo() {
        return typeNo;
    }

    public void setTypeNo(String typeNo) {
        this.typeNo = typeNo;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
