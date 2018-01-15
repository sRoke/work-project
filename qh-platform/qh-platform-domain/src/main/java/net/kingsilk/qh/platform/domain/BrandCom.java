package net.kingsilk.qh.platform.domain;

import net.kingsilk.qh.platform.core.BrandComStatusEnum;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

/**
 * 品牌商， 特指一个公司。
 */
@Document
public class BrandCom extends Base {

    /***
     * 拥有者电话
     */
    private String phone;

    /**
     * 状态
     */
    private BrandComStatusEnum status = BrandComStatusEnum.APPLYING;

    /**
     * 主营类目
     */
    private Set<String> category;

    /**
     * 商家名称
     */
    private String brandComName;

    /**
     * 商家logo
     */
    private String logo;



    /*
        TODO 营业执照URL，法人身份证正面图片URL，法人身份证反面图片URL 等等
     */


    // --------------------------------------- getter && setter


    public BrandComStatusEnum getStatus() {
        return status;
    }

    public void setStatus(BrandComStatusEnum status) {
        this.status = status;
    }

    public Set<String> getCategory() {
        return category;
    }

    public void setCategory(Set<String> category) {
        this.category = category;
    }

    public String getBrandComName() {
        return brandComName;
    }

    public void setBrandComName(String brandComName) {
        this.brandComName = brandComName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
