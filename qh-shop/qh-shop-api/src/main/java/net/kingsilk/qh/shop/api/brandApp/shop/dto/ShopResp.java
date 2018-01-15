package net.kingsilk.qh.shop.api.brandApp.shop.dto;

import net.kingsilk.qh.shop.api.common.dto.Base;

import java.util.Date;

public class ShopResp extends Base {

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
     * 当前时间
     */
    private Date curDate;

    /**
     * 所属地区
     */
    private String adcNo;

    /**
     * 店铺铺地址城市编号
     */
    private String cityNo;

    /**
     * 店铺地址省编号
     */
    private String provinceNo;

    /**
     * 展示地址
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
     * 状态
     */
    private String status;

    /**
     * 类型
     */
    private String shopType;

    /**
     * 类型码
     */
    private String shopTypeCode;

    /**
     * 详细地址
     */
    private String detailAddr;


    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 到期时间，到期后店铺状态变成店铺到期
     */
    private Date expireDate;

    /**
     * 今日订单
     */
    private Integer todayOrder;

    /**
     * 本月收入
     */
    private Integer monthlyIncome;

    /**
     * 累计收入
     */
    private Integer totalIncome;

    public Date getCurDate() {
        return curDate;
    }

    public void setCurDate(Date curDate) {
        this.curDate = curDate;
    }

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

    public String getCityNo() {
        return cityNo;
    }

    public void setCityNo(String cityNo) {
        this.cityNo = cityNo;
    }

    public String getProvinceNo() {
        return provinceNo;
    }

    public void setProvinceNo(String provinceNo) {
        this.provinceNo = provinceNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getShopTypeCode() {
        return shopTypeCode;
    }

    public void setShopTypeCode(String shopTypeCode) {
        this.shopTypeCode = shopTypeCode;
    }

    public String getDetailAddr() {
        return detailAddr;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getTodayOrder() {
        return todayOrder;
    }

    public void setTodayOrder(Integer todayOrder) {
        this.todayOrder = todayOrder;
    }

    public Integer getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(Integer monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public Integer getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Integer totalIncome) {
        this.totalIncome = totalIncome;
    }
}
