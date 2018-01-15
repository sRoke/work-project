package net.kingsilk.qh.platform.api.brandApp.dto;

import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.platform.api.UniPageReq;

import javax.ws.rs.QueryParam;

public class BrandAppPageReq extends UniPageReq {

    @ApiParam(value = "关键字")
    @QueryParam(value = "keyWord")
    private String keyWord;

    @ApiParam(value = "关键字")
    @QueryParam(value = "brandComId")
    private String brandComId;


    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getBrandComId() {
        return brandComId;
    }

    public void setBrandComId(String brandComId) {
        this.brandComId = brandComId;
    }
}
