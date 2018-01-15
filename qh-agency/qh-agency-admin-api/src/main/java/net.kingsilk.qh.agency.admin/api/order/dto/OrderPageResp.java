package net.kingsilk.qh.agency.admin.api.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.core.OrderStatusEnum;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApiModel(value = "订单分页返回信息")
public class OrderPageResp  {


    private Map<String, Integer> dataCountMap;
    private Map<String, String> orderStatusEnumMap;
    private Page recPage;

    @ApiModel(value = "订单信息")
    public static class OrderInfo  {

        @ApiParam(value = "id")
        private String id;
        @ApiParam(value = "订单号")
        private String seq;
        /**
         * 下单的用户，即代理商
         */
        @ApiParam(value = "realName")
        private String realName;
        @ApiParam(value = "phone")
        private String phone;
        /**
         * 订单价，原价，不包含改价
         */
        @ApiParam(value = "订单价")
        private BigDecimal orderPrice ;
        /**
         * 实际支付的金额
         */
        @ApiParam(value = "实际支付金额")
        private BigDecimal paymentPrice ;
        /**
         * 订单当前状态
         */
        @ApiParam(value = "订单当前状态")
        private OrderStatusEnum status;
        private String statusDesp;
        @ApiParam(value = "售后")
        private List<RefundInfo> refundList = new ArrayList<RefundInfo>();
        @ApiParam(value = "创建日期")
        private Date dateCreated;
        /**
         * 下单的商品
         */
        @ApiParam(value = "下单的商品信息")
        private List<OrderInfoResp.OrderItemInfo> orderItems = new ArrayList<>();

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

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public OrderStatusEnum getStatus() {
            return status;
        }

        public void setStatus(OrderStatusEnum status) {
            this.status = status;
        }

        public String getStatusDesp() {
            return statusDesp;
        }

        public void setStatusDesp(String statusDesp) {
            this.statusDesp = statusDesp;
        }

        public List<RefundInfo> getRefundList() {
            return refundList;
        }

        public void setRefundList(List<RefundInfo> refundList) {
            this.refundList = refundList;
        }

        public Date getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(Date dateCreated) {
            this.dateCreated = dateCreated;
        }

        public List<OrderInfoResp.OrderItemInfo> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(List<OrderInfoResp.OrderItemInfo> orderItems) {
            this.orderItems = orderItems;
        }
    }

    public static class RefundInfo{
//        public RefundInfo convertRefundToResp(refund refund) {
//            this.id = ((String) (refund.id));
//            this.skuId = refund.sku ? refund.sku.id : "";
//            this.status = ((String) (refund.status.desp));
//            return this;
//        }


        private String id;
        private String skuId;
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

//    public static class OrderItemInfo  {
////        public OrderItemInfo convertOrderItemToResp(OrderItem orderItem) {
////            this.skuImg = ((String) (orderItem.sku.item.imgs.getAt(0)));
////            this.skuTitle = ((String) (orderItem.sku.title));
////            this.itemTitle = ((String) (orderItem.sku.item.title));
////            this.skuPrice = ((String) (orderItem.skuPrice));
////            this.num = ((String) (orderItem.num));
////            if (orderItem.refund.asBoolean()) {
////                this.refundInfo = new RefundInfo().convertRefundToResp(orderItem.refund);
////            }
////
////            for (Spec spec : orderItem.sku.specs) {
////                this.specInfos.add(new SpecInfo().convertSpecToResp(spec));
////            }
////
////            return this;
////        }
//
//
//        private String skuImg;
//        private String skuTitle;
//        private String itemTitle;
//        private String skuPrice;
//        private String num;
//        private RefundInfo refundInfo;
//        private List<SpecInfo> specInfos = new ArrayList<SpecInfo>();
//
//        public static class SpecInfo {
////            public SpecInfo convertSpecToResp(Spec spec) {
////                this.propName = ((String) (spec.itemProp.name));
////                this.propValue = ((String) (spec.itemPropValue.name));
////                return this;
////            }
//
//
//
//            private String propName;
//            private String propValue;
//
//            public String getPropName() {
//                return propName;
//            }
//
//            public void setPropName(String propName) {
//                this.propName = propName;
//            }
//
//            public String getPropValue() {
//                return propValue;
//            }
//
//            public void setPropValue(String propValue) {
//                this.propValue = propValue;
//            }
//        }
//
//        public String getSkuImg() {
//            return skuImg;
//        }
//
//        public void setSkuImg(String skuImg) {
//            this.skuImg = skuImg;
//        }
//
//        public String getSkuTitle() {
//            return skuTitle;
//        }
//
//        public void setSkuTitle(String skuTitle) {
//            this.skuTitle = skuTitle;
//        }
//
//        public String getItemTitle() {
//            return itemTitle;
//        }
//
//        public void setItemTitle(String itemTitle) {
//            this.itemTitle = itemTitle;
//        }
//
//        public String getSkuPrice() {
//            return skuPrice;
//        }
//
//        public void setSkuPrice(String skuPrice) {
//            this.skuPrice = skuPrice;
//        }
//
//        public String getNum() {
//            return num;
//        }
//
//        public void setNum(String num) {
//            this.num = num;
//        }
//
//        public RefundInfo getRefundInfo() {
//            return refundInfo;
//        }
//
//        public void setRefundInfo(RefundInfo refundInfo) {
//            this.refundInfo = refundInfo;
//        }
//
//        public List<SpecInfo> getSpecInfos() {
//            return specInfos;
//        }
//
//        public void setSpecInfos(List<SpecInfo> specInfos) {
//            this.specInfos = specInfos;
//        }
//
//
//    }

    public Map<String, Integer> getDataCountMap() {
        return dataCountMap;
    }

    public void setDataCountMap(Map<String, Integer> dataCountMap) {
        this.dataCountMap = dataCountMap;
    }

    public Map<String, String> getOrderStatusEnumMap() {
        return orderStatusEnumMap;
    }

    public void setOrderStatusEnumMap(Map<String, String> orderStatusEnumMap) {
        this.orderStatusEnumMap = orderStatusEnumMap;
    }

    public Page getRecPage() {
        return recPage;
    }

    public void setRecPage(Page recPage) {
        this.recPage = recPage;
    }
}
