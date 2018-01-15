package net.kingsilk.qh.platform.api.brandCom.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;
import java.util.Set;

/**
 * 品牌商信息。
 */
@ApiModel
public class BrandComAddReq {


    @ApiParam(value = "品牌商所属组织人i电话",required = true)
    @QueryParam(value = "phone")
    private String phone;

    @ApiParam(value = "品牌商名称",required = true)
    @QueryParam(value = "title")
    private String title;

    @ApiParam(value = "品牌商的主营类目",required = true)
    @QueryParam(value = "categoryId")
    private Set<String> categoryId;

    @ApiParam(value = "品牌商logo",required = true)
    @QueryParam(value = "logoUrl")
    private String logoUrl;

    @ApiParam(value = "品牌商logo",required = true)
    @QueryParam(value = "appIds")
    private Set<String> appIds;

    @ApiParam(value = "有限时间", required = true)
    @QueryParam(value = "date")
    private String date;


    // --------------------------------------- getter && setter


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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


    public Set<String> getAppIds() {
        return appIds;
    }

    public void setAppIds(Set<String> appIds) {
        this.appIds = appIds;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
