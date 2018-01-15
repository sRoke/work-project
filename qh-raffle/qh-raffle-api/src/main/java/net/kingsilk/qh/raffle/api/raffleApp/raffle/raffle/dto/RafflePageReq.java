package net.kingsilk.qh.raffle.api.raffleApp.raffle.raffle.dto;

import io.swagger.annotations.ApiModelProperty;
import net.kingsilk.qh.raffle.api.common.UniPageReq;

import javax.ws.rs.QueryParam;

public class RafflePageReq extends UniPageReq {

    @ApiModelProperty("活动的状态")
    @QueryParam("status")
    private String status;

    @ApiModelProperty("搜索的关键字")
    @QueryParam("keyWord")
    private String keyWord;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
