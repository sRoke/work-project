package net.kingsilk.qh.shop.api.brandApp.shop.dto;

public class ShopCreateRep {

    /**
     * 商家。
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
     * 门店类型
     */
    private String shopType;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }
}
