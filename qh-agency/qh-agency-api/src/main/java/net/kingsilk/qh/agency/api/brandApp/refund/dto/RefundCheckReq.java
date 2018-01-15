package net.kingsilk.qh.agency.api.brandApp.refund.dto;

import io.swagger.annotations.ApiModel;

import javax.ws.rs.QueryParam;

@ApiModel(value = "确认退货单请求参数")
public class RefundCheckReq {

    /**备注**/
    @QueryParam(value = "memo")
    private String memo;

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}

