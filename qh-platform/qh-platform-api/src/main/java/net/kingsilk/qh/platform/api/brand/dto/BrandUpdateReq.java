package net.kingsilk.qh.platform.api.brand.dto;

import io.swagger.annotations.*;

import java.util.*;

/**
 * 品牌商信息。
 */
@ApiModel
public class BrandUpdateReq {

    @ApiModelProperty("品牌商ID")
    private Optional<String> brandComId = Optional.empty();

    @ApiModelProperty("品牌中文名")
    private Optional<String> nameCN = Optional.empty();

    @ApiModelProperty("品牌英文名")
    private Optional<String> nameEN = Optional.empty();

    @ApiModelProperty("品牌官网URL")
    private Optional<String> website = Optional.empty();

    @ApiModelProperty("品牌Logo图片URL")
    private Optional<String> logoUrl = Optional.empty();

    @ApiModelProperty("品牌说明")
    private Optional<String> desp = Optional.empty();

    @ApiModelProperty("品牌创始年代。格式任意")
    private Optional<String> foundingTime = Optional.empty();

    // --------------------------------------- getter && setter

    public Optional<String> getBrandComId() {
        return brandComId;
    }

    public void setBrandComId(Optional<String> brandComId) {
        this.brandComId = brandComId;
    }

    public Optional<String> getNameCN() {
        return nameCN;
    }

    public void setNameCN(Optional<String> nameCN) {
        this.nameCN = nameCN;
    }

    public Optional<String> getNameEN() {
        return nameEN;
    }

    public void setNameEN(Optional<String> nameEN) {
        this.nameEN = nameEN;
    }

    public Optional<String> getWebsite() {
        return website;
    }

    public void setWebsite(Optional<String> website) {
        this.website = website;
    }

    public Optional<String> getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(Optional<String> logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Optional<String> getDesp() {
        return desp;
    }

    public void setDesp(Optional<String> desp) {
        this.desp = desp;
    }

    public Optional<String> getFoundingTime() {
        return foundingTime;
    }

    public void setFoundingTime(Optional<String> foundingTime) {
        this.foundingTime = foundingTime;
    }
}
