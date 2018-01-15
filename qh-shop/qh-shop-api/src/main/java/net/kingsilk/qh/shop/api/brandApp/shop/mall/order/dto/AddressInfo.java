package net.kingsilk.qh.shop.api.brandApp.shop.mall.order.dto;

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
     * 手机号
     */
    private String phone;
    /**
     * 省
     */
    private String province;
    /**
     *
     */
    private String provinceNo;
    /**
     * 市
     */
    private String city;
    /**
     *
     */
     private String cityNo;
     /**
     *县
     */
    private String county;
    /**
     *
     */
    private String countyNo;

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceNo() {
        return provinceNo;
    }

    public void setProvinceNo(String provinceNo) {
        this.provinceNo = provinceNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityNo() {
        return cityNo;
    }

    public void setCityNo(String cityNo) {
        this.cityNo = cityNo;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountyNo() {
        return countyNo;
    }

    public void setCountyNo(String countyNo) {
        this.countyNo = countyNo;
    }
}