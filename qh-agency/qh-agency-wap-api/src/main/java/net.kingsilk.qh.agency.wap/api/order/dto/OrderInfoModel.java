package net.kingsilk.qh.agency.wap.api.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.kingsilk.qh.agency.wap.api.addr.dto.AddrModel;
import net.kingsilk.qh.agency.wap.api.item.dto.SkuInfoModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel
public class OrderInfoModel {
//    public void convert(Order order, final String[] tags) {
//        id = ((String) (order.id));
//        seq = ((String) (order.seq));
//        status = ((String) (order.status.code));
//        statusDesp = ((String) (order.status.desp));
//        orderPrice = ((Integer) (order.orderPrice));
//        paymentPrice = ((Integer) (order.paymentPrice));
//        items = new ArrayList<OrderSkuModel>();
//        order.orderItems.invokeMethod("each", new Object[]{new Closure(this, this) {
//            public Boolean doCall(OrderItem item) {
//                OrderSkuModel sku = new OrderSkuModel();
//                sku.convert(item, tags);
//                if (item.refund.asBoolean()) {
//                    OrderSkuModel.RefundModel refundModel = new OrderSkuModel.RefundModel();
//                    refundModel.status = ((String) (item.refund.status.code));
//                    refundModel.statusDesp = ((String) (item.refund.status.description));
//                    sku.setRefund(refundModel);
//                }
//
//                return items.add(sku);
//            }
//
//        }});
//        address = new AddrModel();
//        address.invokeMethod("convert", new Object[]{order.invokeMethod("getAddress", new Object[0])});
//    }



    @ApiModelProperty(value = "订单id")
    private String id;
    @ApiModelProperty(value = "订单编号")
    private String seq;
    @ApiModelProperty(value = "订单状态")
    private String status;
    @ApiModelProperty(value = "订单状态描述")
    private String statusDesp;
    @ApiModelProperty(value = "订单原价")
    private BigDecimal orderPrice;
    @ApiModelProperty(value = "订单实际支付的金额")
    private BigDecimal paymentPrice;
    @ApiModelProperty(value = "用户收货地址")
    private AddrModel address;
    @ApiModelProperty(value = "商品信息")
    private List<OrderSkuModel> items;

    @ApiModelProperty(value = "商品信息")
    private String createDate;

    private String memo;
    /**
     * 同Order中的OrderItem
     */
    @ApiModel
    public static class OrderSkuModel{
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
        private SkuInfoModel skuInfo;
        @ApiModelProperty(value = "当前sku下单数量")
        private Integer num = 0;
        @ApiModelProperty(value = "订单中该商品实际支付的金额")
        private BigDecimal realTotalPrice;
        @ApiModelProperty(value = "提交订单时刻,sku的单价")
        private BigDecimal skuPrice ;
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

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public SkuInfoModel getSkuInfo() {
            return skuInfo;
        }

        public void setSkuInfo(SkuInfoModel skuInfo) {
            this.skuInfo = skuInfo;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

        public BigDecimal getRealTotalPrice() {
            return realTotalPrice;
        }

        public void setRealTotalPrice(BigDecimal realTotalPrice) {
            this.realTotalPrice = realTotalPrice;
        }

        public BigDecimal getSkuPrice() {
            return skuPrice;
        }

        public void setSkuPrice(BigDecimal skuPrice) {
            this.skuPrice = skuPrice;
        }

        public RefundModel getRefund() {
            return refund;
        }

        public void setRefund(RefundModel refund) {
            this.refund = refund;
        }
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

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public BigDecimal getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(BigDecimal paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    public AddrModel getAddress() {
        return address;
    }

    public void setAddress(AddrModel address) {
        this.address = address;
    }

    public List<OrderSkuModel> getItems() {
        return items;
    }

    public void setItems(List<OrderSkuModel> items) {
        this.items = items;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
