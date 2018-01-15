package net.kingsilk.qh.platform.api.brandApp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("品牌商下的app信息")
public class BrandAppResp {

    @ApiModelProperty("应用名称")
    private String appName;

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("有效期")
    private String date;

    @ApiModelProperty("店铺名称")
    private String name;

    @ApiModelProperty("创建日期")
    private String creatDate;

    @ApiModelProperty("创建人")
    private String creatBy;

    @ApiModelProperty("微信公众号")
    private String wxMpId;

    @ApiModelProperty("第三方平台appId")
    private String wxComAppId;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(String creatDate) {
        this.creatDate = creatDate;
    }

    public String getCreatBy() {
        return creatBy;
    }

    public void setCreatBy(String creatBy) {
        this.creatBy = creatBy;
    }

    public String getWxMpId() {
        return wxMpId;
    }

    public void setWxMpId(String wxMpId) {
        this.wxMpId = wxMpId;
    }

    public String getWxComAppId() {
        return wxComAppId;
    }

    public void setWxComAppId(String wxComAppId) {
        this.wxComAppId = wxComAppId;
    }
}
