package net.kingsilk.qh.platform.api.app.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.platform.api.UniPageReq;

import javax.ws.rs.QueryParam;

@ApiModel(value = "应用分页请求信息")
public class AppPageReq extends UniPageReq {

    @ApiParam(value = "关键字")
    @QueryParam(value = "keyWord")
    private String keyWord;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
