package net.kingsilk.qh.agency.api.brandApp.partnerAccount.dto;


import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniPageReq;

import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * Created by yk on 17/9/2.
 */
public class PartnerAccountLogReq extends UniPageReq {


    @ApiParam(value = "订单类型")
    @QueryParam(value = "logType")
    private String logType;

    @ApiParam(value = "各种单合集")
    @QueryParam(value = "ids")
    private List<String> ids;

    @ApiParam(value = "渠道商id(后台用)")
    @QueryParam(value = "parnterId")
    private String parnterId;


    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getParnterId() {
        return parnterId;
    }

    public void setParnterId(String parnterId) {
        this.parnterId = parnterId;
    }
}
