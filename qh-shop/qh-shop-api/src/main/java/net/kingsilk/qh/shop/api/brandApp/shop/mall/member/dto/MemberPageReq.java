package net.kingsilk.qh.shop.api.brandApp.shop.mall.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.kingsilk.qh.shop.api.UniPageReq;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
@ApiModel
public class MemberPageReq extends UniPageReq {

    @ApiModelProperty("搜索关键字，范围手机号和名字")
    @QueryParam("keyword")
    private String keyword;

    @ApiModelProperty("公众号平台id")
    @QueryParam("wxComAppId")
    private String wxComAppId;

    @ApiModelProperty("公众号id")
    @QueryParam("wxMpAppId")
    private String wxMpAppId;

    @ApiModelProperty("公众号id")
    @QueryParam("enable")
    @DefaultValue("true")
    private Boolean enable;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getWxComAppId() {
        return wxComAppId;
    }

    public void setWxComAppId(String wxComAppId) {
        this.wxComAppId = wxComAppId;
    }

    public String getWxMpAppId() {
        return wxMpAppId;
    }

    public void setWxMpAppId(String wxMpAppId) {
        this.wxMpAppId = wxMpAppId;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
