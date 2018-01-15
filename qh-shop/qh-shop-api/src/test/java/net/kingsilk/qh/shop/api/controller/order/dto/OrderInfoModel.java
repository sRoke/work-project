package net.kingsilk.qh.shop.api.controller.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class OrderInfoModel {


    @ApiModelProperty(value = "订单id")
    private String id;
    @ApiModelProperty(value = "订单编号")
    private String seq;
    @ApiModelProperty(value = "订单状态")
    private String status;
    @ApiModelProperty(value = "订单状态描述")
    private String statusDesp;
    @ApiModelProperty(value = "订单原价")
    private Integer orderPrice;
    @ApiModelProperty(value = "订单实际支付的金额")
    private Integer paymentPrice;

    @ApiModelProperty(value = "折扣活动类型")
    private String activityType;

    @ApiModelProperty(value = "享受的折扣")
    private Integer discount;

    @ApiModelProperty(value = "活动减价")
    private Integer reducePrice;

    @ApiModelProperty(value = "商品信息")
    private List<OrderSkuModel> items;

    @ApiModelProperty(value = "商品信息")
    private String createDate;

    private String orderCreateDate;

    private String orderCreateTime;

    private String memo;

    private Integer total;


    @ApiModelProperty(value = "手机号")
    private String cellphone;

    @ApiModelProperty(value = "支付方式")
    private String payType;


    /**
     * 同Order中的OrderItem
     */
    @ApiModel
    public static class OrderSkuModel {
//        public void convert(OrderItem item, String[] tags) {
//            skuId = ((String) (item.sku.id));
//            num = ((Integer) (item.num));
//            realTotalPrice = ((Integer) (item.realTotalPrice));
//            skuPrice = ((Integer) (item.skuPrice));
//            skuInfo = new SkuInfoModel();
//            skuInfo.invokeMethod("convert", new Object[]{item.sku, tags});
//        }


        @ApiModelProperty(value = "skuId")
        private String skuId;
        @ApiModelProperty(value = "sku详情")
        private SkuInfo skuInfo;
        @ApiModelProperty(value = "当前sku下单数量")
        private Integer num = 0;
        @ApiModelProperty(value = "订单中该商品实际支付的金额")
        private Integer realTotalPrice;
        @ApiModelProperty(value = "订单中该商品实际支付的金额")
        private Integer skuStoreNum;
        @ApiModelProperty(value = "提交订单时刻,sku的单价")
        private Integer skuPrice;
        @ApiModelProperty(value = "退货状态")
        private RefundModel refund;

        public static class RefundModel {

            @ApiModelProperty(value = "状态")
            private String status;
            @ApiModelProperty(value = "状态描述")
            private String statusDesp;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getStatusDesp() {
                return statusDesp;
            }

            public void setStatusDesp(String statusDesp) {
                this.statusDesp = statusDesp;
            }
        }

        public Integer getSkuStoreNum() {
            return skuStoreNum;
        }

        public void setSkuStoreNum(Integer skuStoreNum) {
            this.skuStoreNum = skuStoreNum;
        }

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public SkuInfo getSkuInfo() {
            return skuInfo;
        }

        public void setSkuInfo(SkuInfo skuInfo) {
            this.skuInfo = skuInfo;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

        public Integer getRealTotalPrice() {
            return realTotalPrice;
        }

        public void setRealTotalPrice(Integer realTotalPrice) {
            this.realTotalPrice = realTotalPrice;
        }

        public Integer getSkuPrice() {
            return skuPrice;
        }

        public void setSkuPrice(Integer skuPrice) {
            this.skuPrice = skuPrice;
        }

        public RefundModel getRefund() {
            return refund;
        }

        public void setRefund(RefundModel refund) {
            this.refund = refund;
        }
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getOrderCreateDate() {
        return orderCreateDate;
    }

    public void setOrderCreateDate(String orderCreateDate) {
        this.orderCreateDate = orderCreateDate;
    }

    public String getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesp() {
        return statusDesp;
    }

    public void setStatusDesp(String statusDesp) {
        this.statusDesp = statusDesp;
    }

    public Integer getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(Integer paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    public List<OrderSkuModel> getItems() {
        return items;
    }

    public void setItems(List<OrderSkuModel> items) {
        this.items = items;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
