package net.kingsilk.qh.shop.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 线下收银收集的客户数据
 */
@Document
public class Contacts extends Base {
    /**
     * 应用的Id。
     */
    private String brandAppId;

    /**
     * 门店的ID
     */
    private String shopId;

    /**
     * 客户姓名
     */
    private String name;

    /**
     * 客户手机号
     */
    private String phone;

    /**
     * 客户性别
     */
    private String sex;

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
