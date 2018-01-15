package net.kingsilk.qh.agency.admin.api.addr.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

/**
 * Created by zcw on 3/16/17.
 */
@ApiModel
public class AddrQueryAdcReq  {
    public String getAdc() {
        return adc;
    }

    public void setAdc(String adc) {
        this.adc = adc;
    }

    @ApiParam(value = "地区编码", required = false)
    @QueryParam(value = "adc")
    private String adc;
}
