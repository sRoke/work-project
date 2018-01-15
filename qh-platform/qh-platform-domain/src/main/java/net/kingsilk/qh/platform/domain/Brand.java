package net.kingsilk.qh.platform.domain;

import net.kingsilk.qh.platform.core.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * 品牌信息
 */
@Document
@CompoundIndexes({
})
public class Brand extends Base {

    /**
     * 拥有该品牌的品牌商。
     */
    @Indexed
    private String brandComId;

    /** 中文名 */
    private String nameCN;

    /** 英文名 */
    private String nameEN;

    /** 官网URL */
    private String website;

    /**
     *  品牌 logo URL
     */
    private String logoUrl;

    /** 品牌说明 */
    private String desp;

    /**
     * 创始年代
     *
     * 任意文字描述。
     */
    private String foundingTime;

    /** 状态 */
    private BrandStatusEnum status = BrandStatusEnum.EDITING;


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

    public BrandStatusEnum getStatus() {
        return status;
    }

    public void setStatus(BrandStatusEnum status) {
        this.status = status;
    }
}
