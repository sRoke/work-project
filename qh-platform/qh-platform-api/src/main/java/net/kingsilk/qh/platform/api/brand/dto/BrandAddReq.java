package net.kingsilk.qh.platform.api.brand.dto;

import io.swagger.annotations.*;

/**
 * 品牌商信息。
 */
@ApiModel
public class BrandAddReq {


    @ApiModelProperty("品牌商ID")
    private String brandComId;
//    private Optional<String> brandComId;

    @ApiModelProperty("品牌中文名")
    private String nameCN;

    @ApiModelProperty("品牌英文名")
    private String nameEN;

    @ApiModelProperty("品牌官网URL")
    private String website;

    @ApiModelProperty("品牌Logo图片URL")
    private String logoUrl;

    @ApiModelProperty("品牌说明")
    private String desp;

    @ApiModelProperty("品牌创始年代。格式任意")
    private String foundingTime;

    // --------------------------------------- getter && setter


    public String getBrandComId() {
        return brandComId;
    }

    public void setBrandComId(String brandComId) {
        this.brandComId = brandComId;
    }

    public String getNameCN() {
        return nameCN;
    }

    public void setNameCN(String nameCN) {
        this.nameCN = nameCN;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getFoundingTime() {
        return foundingTime;
    }

    public void setFoundingTime(String foundingTime) {
        this.foundingTime = foundingTime;
    }
}
