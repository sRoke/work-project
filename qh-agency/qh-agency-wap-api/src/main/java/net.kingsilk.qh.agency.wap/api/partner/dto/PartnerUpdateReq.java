package net.kingsilk.qh.agency.wap.api.partner.dto;

/**
 *
 */
public class PartnerUpdateReq {

    /**
     * id
     */
    private String id;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 联系方式
     */
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
