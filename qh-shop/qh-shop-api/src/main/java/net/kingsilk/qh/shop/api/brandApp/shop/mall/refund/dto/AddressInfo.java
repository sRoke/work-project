package net.kingsilk.qh.shop.api.brandApp.shop.mall.refund.dto;

public class AddressInfo {

    /**
     * 收货人
     */
    private String receiver;
    /**
     * 街道
     */
    private String street;

    /**
     * 省市区地址
     */
    private String address;
    /**
     * 手机号
     */
    private String phone;
//    /**
//     * 省
//     */
//    private String province;
//    /**
//     *
//     */
//    private String provinceNo;
//    /**
//     * 市
//     */
//    private String city;
//    /**
//     *
//     */
//     private String cityNo;
//     /**
//     *县
//     */
//    private String county;
//    /**
//     *
//     */
//    private String countyNo;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}