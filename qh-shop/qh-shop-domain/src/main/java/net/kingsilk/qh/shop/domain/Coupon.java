package net.kingsilk.qh.shop.domain;

import net.kingsilk.qh.shop.core.CouponStatusEnum;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 优惠卷
 */
@Document
public class Coupon extends Base {

    private String brandAppId;

    /**
     * 优惠卷定义
     */
    private CouponDef couponDef;

    /**
     *优惠券是否已经使用，现在优惠券可以被多个订单使用
     */
    private CouponStatusEnum status;

    /**
     * 优惠券号码
     */
    private String num;

    /**
     *领用的会员的ID
     */
    private String memberId;

    /**
     * 使用时间
     */
    private Date useDate;

    /**
     * 使用的订单
     */
    private String orderId;

    /**
     * 门店的ID
     */
    private String shopId;

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public CouponDef getCouponDef() {
        return couponDef;
    }

    public void setCouponDef(CouponDef couponDef) {
        this.couponDef = couponDef;
    }

    public CouponStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CouponStatusEnum status) {
        this.status = status;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
