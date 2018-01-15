package net.kingsilk.qh.shop.domain.bak;

import net.kingsilk.qh.shop.domain.Base;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

/**
 * 购物车。
 * 面向员工——即员工开单。
 */
// TODO 主键
@Document
public class Cart extends Base {

    /**
     * 所属品牌商ID。
     */
    @Indexed
    private String brandAppId;

    /**
     * 收银对应的渠道商Id
     */
    @Indexed
    private String partnerId;

    /**
     * 开单员工ID
     */
    private String createdStaffId;

    /**
     * 客户名称
     *
     * 允许为空
     */
    private String customName;

    /**
     * 客户手机。
     *
     * 允许为空
     */
    private String customPhone;

    /**
     * 购物车编号. 新建时要
     *
     * 新增的值为 MAX + 1. 最小值是1.
     */
    private int cartNum;

    /**
     * 下单的商品
     */
    private Set<CartItem> cartItems;

//    /**
//     * 自动过期时间。到达改时间点后会自动删除该记录。
//     *
//     * 购物车没操作 24 小时后过期。
//     *
//     * 只要购物车有更新，该字段就需要重新赋值新的过期时间。
//     */
//    @Indexed(expireAfterSeconds = 0)
//    private Date expiredAt;

    /**
     * 备注
     */
    private String memo;

    /**
     * 购物车商品信息。
     */
    public static class CartItem {

        /**
         * 购物车商品ID。
         * <p>
         * 由前端生成，必须能转换为 ObjectId。主要用以方便更新。
         */
        public String id;

        /**
         * 下单的商品
         */
        public String skuId;

        /**
         * 数量,为0时移除该条记录
         */
        public Integer num = 0;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getCreatedStaffId() {
        return createdStaffId;
    }

    public void setCreatedStaffId(String createdStaffId) {
        this.createdStaffId = createdStaffId;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getCustomPhone() {
        return customPhone;
    }

    public void setCustomPhone(String customPhone) {
        this.customPhone = customPhone;
    }

    public int getCartNum() {
        return cartNum;
    }

    public void setCartNum(int cartNum) {
        this.cartNum = cartNum;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
