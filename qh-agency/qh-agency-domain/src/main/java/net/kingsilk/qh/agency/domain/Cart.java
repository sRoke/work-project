package net.kingsilk.qh.agency.domain;

import net.kingsilk.qh.agency.core.CartTypeEnum;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

/**
 * 购物车
 */
@Document
//@CompoundIndexes({
//        @CompoundIndex(def = "brandAppId:1,sellerPartnerId:1,buyerPartnerId:1}")
//})
public class Cart extends Base {

    /**
     * 所属品牌商ID。
     */
    private String brandAppId;

    /**
     * 向哪个渠道商进货。如果为空，则表示直接向品牌商进货。
     */
    private String sellerPartnerId;

    /**
     * 向哪个渠道商进货。如果为空，则表示直接向品牌商进货。
     */
    private String buyerPartnerId;

    /**
     * 购物车类型
     */
    private CartTypeEnum cartTypeEnum;

    /**
     * 购物车所属用户。
     * 该系统无匿名用户，必须登录才能访问
     */
//    @Indexed
//    private String userId;

    private PartnerStaff partnerStaff;

    /**
     * 下单的商品
     */
    //@Field("cartItems")
    private Set<CartItem> cartItems;

    /**
     * 购物车商品信息。
     */
    public static class CartItem {

        /**
         * 购物车商品ID。
         * <p>
         * 由前端生成，必须能转换为 ObjectId。主要用以方便更新。
         */
        private String id;

        /**
         * 下单的商品
         */
        @DBRef
        private Sku sku;

        /**
         * 数量,为0时移除该条记录
         */
        private Integer num = 0;

        public Sku getSku() {
            return sku;
        }

        public void setSku(Sku sku) {
            this.sku = sku;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }
    }

    public CartTypeEnum getCartTypeEnum() {
        return cartTypeEnum;
    }

    public void setCartTypeEnum(CartTypeEnum cartTypeEnum) {
        this.cartTypeEnum = cartTypeEnum;
    }

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getSellerPartnerId() {
        return sellerPartnerId;
    }

    public void setSellerPartnerId(String sellerPartnerId) {
        this.sellerPartnerId = sellerPartnerId;
    }

    public String getBuyerPartnerId() {
        return buyerPartnerId;
    }

    public void setBuyerPartnerId(String buyerPartnerId) {
        this.buyerPartnerId = buyerPartnerId;
    }

    public PartnerStaff getPartnerStaff() {
        return partnerStaff;
    }

    public void setPartnerStaff(PartnerStaff partnerStaff) {
        this.partnerStaff = partnerStaff;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
