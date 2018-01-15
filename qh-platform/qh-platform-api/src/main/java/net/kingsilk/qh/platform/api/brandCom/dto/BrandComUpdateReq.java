package net.kingsilk.qh.platform.api.brandCom.dto;

import io.swagger.annotations.ApiModel;

import java.util.Set;

/**
 * 品牌商信息。
 */
@ApiModel(description = "品牌商信息")
public class BrandComUpdateReq {

    private String title;

    private Set<String> categoryId;


    private String logoUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<String> getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Set<String> categoryId) {
        this.categoryId = categoryId;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
