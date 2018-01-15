package net.kingsilk.qh.shop.domain;

import net.kingsilk.qh.shop.core.ShopEnum;
import net.kingsilk.qh.shop.core.ShopStatusEnum;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Document
public class Shop extends Base {

    /**
     * 应用的Id。
     */
    private String brandAppId;

    /**
     * 负责人帐号
     */
    private String phone;

    /**
     * 门店名称
     */
    private String name;

    /**
     * 所属地区
     */
    private String adcNo;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 门店照片
     */
    private String img;

    /**
     * 联系电话
     */
    private String tel;

    /**
     * 组织id
     */
    private String orgId;

    /**
     * 店铺类型
     */
    private ShopEnum shopType;

    /**
     * 店铺状态
     */
    private ShopStatusEnum status;

    /**
     * 购买记录
     */
    private List<String> payLog =new ArrayList<>();

    /**
     * 到期时间，到期后店铺状态变成店铺到期
     */
    private Date expireDate;

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAdcNo() {
        return adcNo;
    }

    public void setAdcNo(String adcNo) {
        this.adcNo = adcNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public ShopEnum getShopType() {
        return shopType;
    }

    public void setShopType(ShopEnum shopType) {
        this.shopType = shopType;
    }

    public ShopStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ShopStatusEnum status) {
        this.status = status;
    }

    public List<String> getPayLog() {
        return payLog;
    }

    public void setPayLog(List<String> payLog) {
        this.payLog = payLog;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}
