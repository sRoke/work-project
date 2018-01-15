package net.kingsilk.qh.raffle.api.raffleApp.raffle.record.dto;

import io.swagger.annotations.ApiModelProperty;
import net.kingsilk.qh.raffle.api.common.UniPageReq;

import javax.ws.rs.QueryParam;

public class RaffleRecordReq extends UniPageReq {

    @ApiModelProperty("抽奖记录的的处理的状态")
    @QueryParam("handleStatus")
    private String handleStatus;

    @ApiModelProperty("搜索的关键字")
    @QueryParam("keyWord")
    private String keyWord;

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
