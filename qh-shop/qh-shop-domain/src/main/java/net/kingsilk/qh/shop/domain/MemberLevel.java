package net.kingsilk.qh.shop.domain;

/**
 * Created by lit on 17/11/6.
 */
public class MemberLevel extends Base {

    /**
     * 应用的Id。
     */
    private String brandAppId;

    /**
     * 门店的ID
     */
    private String shopId;

    /**
     * 会员等级名称
     */
    private String name;

    /**
     * 等级需要的积分数/每年
     */
    private Integer integral;

    /**
     * 等级需要的金额/累计
     */
    private Integer price;

    private Integer orderNum;

    /**
     * 是否禁用
     */
    private Boolean enable;

    /**
     * 反积分比例
     */
    private Integer payRate;

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

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Integer getPayRate() {
        return payRate;
    }

    public void setPayRate(Integer payRate) {
        this.payRate = payRate;
    }
}
