package net.kingsilk.qh.agency.api.brandApp.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniPageReq;

import javax.ws.rs.QueryParam;

/**
 * Created by zcw on 3/16/17.
 */
@ApiModel
public class OrderListReq extends UniPageReq {


    @ApiParam(value = "订单状态", required = false)
    @QueryParam(value = "status")
    private String status;
    @QueryParam(value = "keyWord")
    private String keyWord;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
