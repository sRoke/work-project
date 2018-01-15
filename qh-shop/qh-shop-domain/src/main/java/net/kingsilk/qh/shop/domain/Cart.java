package net.kingsilk.qh.shop.domain;

import net.kingsilk.qh.shop.core.CartTypeEnum;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 收银和线上会员下单的购物车，根据购物车类型区分
 */
@Document
public class Cart extends Base {

    /**
     * 应用ID
     */
    private String brandAppId;

    /**
     * 门店Id
     */
    private String shopId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 购物车类型
     */
    private CartTypeEnum cartTypeEnum;

    /**
     * 线上会员Id
     */
    private String memberId;

    /**
     * 收银时员工ID
     */
    private String staffId;

    /**
     * 最后一次访问购物车时间，收银可能会用到超时自动清除购物车功能
     */
    private Date lastAccessTime;

    /**
     * 购物车的sku列表
     */
    private List<CartItem> cartItems = new LinkedList<>();

    public class CartItem {

        /**
         * 随机编号，前端生成
         */
        private String no;

        /**
         * sku的Id
         */
        private String skuId;

        /**
         * sku的数量
         */
        private Integer num;

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public CartTypeEnum getCartTypeEnum() {
        return cartTypeEnum;
    }

    public void setCartTypeEnum(CartTypeEnum cartTypeEnum) {
        this.cartTypeEnum = cartTypeEnum;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
