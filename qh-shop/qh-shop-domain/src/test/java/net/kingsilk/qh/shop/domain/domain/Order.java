package net.kingsilk.qh.shop.domain.bak;

import net.kingsilk.qh.shop.core.ActivityTypeEnum;
import net.kingsilk.qh.shop.core.OrderStatusEnum;
import net.kingsilk.qh.shop.core.PayTypeEnum;
import net.kingsilk.qh.shop.domain.Base;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * 进货订单，每一步非操作人见OrderLog
 */
@Document
public class Order extends Base {

    /**
     * 所属品牌商ID。
     */
    private String brandAppId;

    /**
     * 当前渠道商ID
     */
    private String partnerId;

//    /**
//     * 订单号.
//     *参考：http://bbs.51cto.com/thread-1145208-1.html
//     * 时间戳+（机器码+PID）异或+计数器
//     * 仅在 TODO 待定，先用 ObjectId, 确定序列号种类数量
//     */
//    private String seq;

    /**
     * 购买者填写手机号，可为空
     */
    private String buyerPhone;

    /**
     * 购买者填写姓名，可为空
     */
    private String buyerName;

    /**
     * 操作的渠道商员工
     */
    private String createdStaff;

    /**
     * 享受的活动类型
     */
    private ActivityTypeEnum activityType;

    /**
     * 享受的折扣
     */
    private Integer discount;

    /**
     * 活动减价
     */
    private Integer reducePrice;
    /**
     * 商品合计,原价，不包含改价
     * SUM(sku.parterTagPrice * sku.num)
     */
    private Integer orderPrice;

    /**
     * 客服调整的金额。
     * <p>
     * - 正值 : 让购买者多支付
     * - 负值 : 让购买者少支付
     */
    private Integer adjustPrice = 0;

    /**
     * 实际支付价
     * <p>
     * = orderPrice - 优惠 + 改价(adjustPrice) + 邮费 + ...
     */
    private Integer paymentPrice;

    /**
     * 订单当前状态
     */
    private OrderStatusEnum status;

    /**
     * 支付方式
     */
    private PayTypeEnum type;

//    /**
//     * 支付记录
//     */
//    @DBRef
//    private QhPay qhPay;

    /**
     * 订单前一次状态
     */
    private OrderStatusEnum lastStatus;

    // TODO 该字段应该将来可能会用。比如顾客是来旅游的，只是到店面下了个单，要求店员邮寄。
//    /**
//     * 渠道商收货地址
//     */
//    private OrderAddress orgShippingAddr;
//    private String buyerMemo;


    /**
     * 卖家备注
     */
    private String sellerMemo;


    /**
     * 下单的商品
     */
    private List<OrderItem> orderItems = new ArrayList<>();

    /**
     * 手机号
     */
    private String cellphone;

    /**
     * 订单号
     */
    private String seq;


    public static class OrderItem {

        /**
         * 下单的商品
         */
        private String skuId;

        /**
         * 下单数量
         */
        private Integer num = 0;

        /**
         * 提交订单时刻,sku的单价
         * 增加该字段原因：若取sku的价格，sku可能会修改，不准确
         */
        private Integer skuPrice = 0;


        /* XXX DO NOT DELETE ME

         单品活动价格，初始值为 skuPrice.
         如果启用，则 realTotalPrice = activityPrice * num - 订单类优惠百分比 - 订单总改价的百分比
         */
        private Integer activityPrice = 0;

        /* XXX DO NOT DELETE ME

        提交订单后，单个SKU改价。
        如果启用，则 realTotalPrice = usedPrice * num - 订单类优惠百分比 - (订单总改价的百分比 或 adjustSkuPrice *  num)
        */
        private Integer adjustSkuPrice = 0;

        /**
         * 订单中该商品实际的支付金额
         * skuPrice * num - 优惠百分比 - 订单总改价的百分比
         */
        private Integer realTotalPrice = 0;

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

        public Integer getSkuPrice() {
            return skuPrice;
        }

        public void setSkuPrice(Integer skuPrice) {
            this.skuPrice = skuPrice;
        }

        public Integer getActivityPrice() {
            return activityPrice;
        }

        public void setActivityPrice(Integer activityPrice) {
            this.activityPrice = activityPrice;
        }

        public Integer getAdjustSkuPrice() {
            return adjustSkuPrice;
        }

        public void setAdjustSkuPrice(Integer adjustSkuPrice) {
            this.adjustSkuPrice = adjustSkuPrice;
        }

        public Integer getRealTotalPrice() {
            return realTotalPrice;
        }

        public void setRealTotalPrice(Integer realTotalPrice) {
            this.realTotalPrice = realTotalPrice;
        }
    }

    public static class OrderAddress {
        private String adc;
        private String street;
        private String receiver;
        private String phone;

        public String getAdc() {
            return adc;
        }

        public void setAdc(String adc) {
            this.adc = adc;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }


    // --------------------------------------- getter && setter
    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
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

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getCreatedStaff() {
        return createdStaff;
    }

    public void setCreatedStaff(String createdStaff) {
        this.createdStaff = createdStaff;
    }

    public Integer getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getAdjustPrice() {
        return adjustPrice;
    }

    public void setAdjustPrice(Integer adjustPrice) {
        this.adjustPrice = adjustPrice;
    }

    public Integer getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(Integer paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public OrderStatusEnum getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(OrderStatusEnum lastStatus) {
        this.lastStatus = lastStatus;
    }


    public PayTypeEnum getType() {
        return type;
    }

    public void setType(PayTypeEnum type) {
        this.type = type;
    }

    public String getSellerMemo() {
        return sellerMemo;
    }

    public void setSellerMemo(String sellerMemo) {
        this.sellerMemo = sellerMemo;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public ActivityTypeEnum getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityTypeEnum activityType) {
        this.activityType = activityType;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getReducePrice() {
        return reducePrice;
    }

    public void setReducePrice(Integer reducePrice) {
        this.reducePrice = reducePrice;
    }
}
