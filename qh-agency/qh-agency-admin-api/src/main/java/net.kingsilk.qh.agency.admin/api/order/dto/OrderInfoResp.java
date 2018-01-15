package net.kingsilk.qh.agency.admin.api.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.core.OrderStatusEnum;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by yanfq
 */
@ApiModel(value = "OrderInfoResp")
public class OrderInfoResp {
//    public OrderInfoResp convertOrderToResp(Order order, List<Member> members, List<refund> refunds) {
//        this.id = ((String) (order.id));
//        this.seq = ((String) (order.seq));
//        this.payType = ((String) (order.payType));
//        this.payTime = ((Date) (order.payTime));
//        if (members.size() > 0) {
//            this.realName = ((String) (members.get(0).realName));
//            this.phone = ((String) (members.get(0).phone));
//        }
//
//        if (refunds.size() > 0) {
//            this.haveRefund = true;
//        }
//
//        this.orderPrice = ((Integer) (order.orderPrice));
//        this.paymentPrice = ((Integer) (order.paymentPrice));
//        this.status = ((OrderStatusEnum) (order.status));
//        this.statusDesp = ((String) (order.status.desp));
//        if (order.orderAddress.asBoolean()) {
//            this.address = new AddressInfo().convertAddressToResp(order.orderAddress);
//        } else {
//            this.address = new AddressInfo().convertAddressToResp(order.address);
//        }
//
//        this.dateCreated = ((Date) (order.dateCreated));
//        for (OrderItem orderItem : order.orderItems) {
//            OrderItemInfo orderItemInfo = new OrderItemInfo();
//            orderItemInfo.convertOrderItemToResp(orderItem);
//            this.orderItems.add(orderItemInfo);
//        }
//
//        for (Logistics logistics : order.logisticses) {
//            LogisticInfo logisticInfo = new LogisticInfo();
//            logisticInfo.convertLogisticToResp(logistics);
//            this.logisticses.add(logisticInfo);
//        }
//
//        companyEnums = LogisticsCompanyEnum.getMap();
//        return this;
//    }


    @ApiParam(value = "id")
    private String id;

    @ApiParam(value = "订单号")
    private String seq;

    @ApiParam(value = "支付方式")
    private String payType;

    @ApiParam(value = "支付方式")
    private Date payTime;

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

    @ApiParam(value = "是否存在退款请求")
    private Boolean haveRefund = false;

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
    @ApiParam(value = "状态描述")
    private String statusDesp;

    /**
     * 收货地址
     */
    @ApiParam(value = "地址")
    private AddressInfo address;

    @ApiParam(value = "创建时间")
    private Date dateCreated;

    /**
     * 下单的商品
     */
    @ApiParam(value = "下单的商品信息")
    private List<OrderItemInfo> orderItems = new ArrayList<OrderItemInfo>();

    @ApiParam(value = "物流")
    private Set<LogisticInfo> logisticses = new LinkedHashSet<LogisticInfo>();
    @ApiParam(value = "物流公司列表")
    private Map<String, String> logisticsCompanys = new HashMap<String, String>();

    @ApiParam(value = "卖家备注")
    private String memo;

    @ApiParam(value = "买家备注")
    private String buyerMemo;

    public static class OrderItemInfo {
//        public OrderItemInfo convertOrderItemToResp(OrderItem orderItem) {
//            this.skuImg = ((String) (orderItem.sku.item.imgs.getAt(0)));
//            this.skuTitle = ((String) (orderItem.sku.title));
//            this.itemTitle = ((String) (orderItem.sku.item.title));
//            this.skuPrice = ((String) (orderItem.sku.price));
//            this.num = ((String) (orderItem.num));
//            for (Spec spec : orderItem.sku.specs) {
//                this.specInfos.add(new SpecInfo().convertSpecToResp(spec));
//            }
//
//            return this;
//        }


        private String skuImg;
        private String skuTitle;
        private String itemTitle;
        private BigDecimal skuPrice;
        private Integer num;
        private List<SpecInfo> specInfos = new ArrayList<SpecInfo>();

        public static class SpecInfo {
//            public SpecInfo convertSpecToResp(Spec spec) {
//                this.propName = ((String) (spec.itemProp.name));
//                this.propValue = ((String) (spec.itemPropValue.name));
//                return this;
//            }


            private String propName;
            private String propValue;

            public String getPropName() {
                return propName;
            }

            public void setPropName(String propName) {
                this.propName = propName;
            }

            public String getPropValue() {
                return propValue;
            }

            public void setPropValue(String propValue) {
                this.propValue = propValue;
            }
        }

        public String getSkuImg() {
            return skuImg;
        }

        public void setSkuImg(String skuImg) {
            this.skuImg = skuImg;
        }

        public String getSkuTitle() {
            return skuTitle;
        }

        public void setSkuTitle(String skuTitle) {
            this.skuTitle = skuTitle;
        }

        public String getItemTitle() {
            return itemTitle;
        }

        public void setItemTitle(String itemTitle) {
            this.itemTitle = itemTitle;
        }

        public BigDecimal getSkuPrice() {
            return skuPrice;
        }

        public void setSkuPrice(BigDecimal skuPrice) {
            this.skuPrice = skuPrice;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

        public List<SpecInfo> getSpecInfos() {
            return specInfos;
        }

        public void setSpecInfos(List<SpecInfo> specInfos) {
            this.specInfos = specInfos;
        }
    }

    public static class AddressInfo {
//        public AddressInfo convertAddressToResp(Object address) {
//            this.receiver = ((String) (address.receiver));
//            this.street = ((String) (address.street));
//            this.phone = ((String) (address.phone));
//            if (address.adc.asBoolean()) {
//                this.county = ((String) (address.adc.name));
//                this.countyNo = ((String) (address.adc.no));
//                if (address.adc.parent.asBoolean()) {
//                    this.city = ((String) (address.adc.parent.name));
//                    this.cityNo = ((String) (address.adc.parent.no));
//                    if (address.adc.parent.parent.asBoolean()) {
//                        this.province = ((String) (address.adc.parent.parent.name));
//                        this.provinceNo = ((String) (address.adc.parent.parent.no));
//                    }
//
//                }
//
//            }
//
//            return this;
//        }


        private String receiver;
        private String street;
        private String phone;
        private String province;
        private String provinceNo;
        private String city;
        private String cityNo;
        private String county;
        private String countyNo;

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getProvinceNo() {
            return provinceNo;
        }

        public void setProvinceNo(String provinceNo) {
            this.provinceNo = provinceNo;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCityNo() {
            return cityNo;
        }

        public void setCityNo(String cityNo) {
            this.cityNo = cityNo;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getCountyNo() {
            return countyNo;
        }

        public void setCountyNo(String countyNo) {
            this.countyNo = countyNo;
        }
    }

    public static class LogisticInfo {
//        public LogisticInfo convertLogisticToResp(Logistics logistic) {
//            if (logistic.company.asBoolean()) {
//                this.company = ((String) (logistic.company.desp));
//            }
//
//            this.expressNo = ((String) (logistic.expressNo));
//            return this;
//        }

        private String company;
        private String expressNo;

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getExpressNo() {
            return expressNo;
        }

        public void setExpressNo(String expressNo) {
            this.expressNo = expressNo;
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
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

    public Boolean getHaveRefund() {
        return haveRefund;
    }

    public void setHaveRefund(Boolean haveRefund) {
        this.haveRefund = haveRefund;
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

    public AddressInfo getAddress() {
        return address;
    }

    public void setAddress(AddressInfo address) {
        this.address = address;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<OrderItemInfo> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemInfo> orderItems) {
        this.orderItems = orderItems;
    }

    public Set<LogisticInfo> getLogisticses() {
        return logisticses;
    }

    public void setLogisticses(Set<LogisticInfo> logisticses) {
        this.logisticses = logisticses;
    }

    public Map<String, String> getLogisticsCompanys() {
        return logisticsCompanys;
    }

    public void setLogisticsCompanys(Map<String, String> logisticsCompanys) {
        this.logisticsCompanys = logisticsCompanys;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getBuyerMemo() {
        return buyerMemo;
    }

    public void setBuyerMemo(String buyerMemo) {
        this.buyerMemo = buyerMemo;
    }
}
