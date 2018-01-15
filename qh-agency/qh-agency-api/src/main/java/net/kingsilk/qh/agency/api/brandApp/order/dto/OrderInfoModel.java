package net.kingsilk.qh.agency.api.brandApp.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.kingsilk.qh.agency.api.brandApp.addr.dto.AddressInfo;
import net.kingsilk.qh.agency.api.common.dto.SkuInfoModel;

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
    @ApiModelProperty(value = "用户收货地址")
    private AddressInfo address;
    @ApiModelProperty(value = "商品信息")
    private List<OrderSkuModel> items;

    @ApiModelProperty(value = "可以使用的抵扣")
    private Integer useDeduction;

    @ApiModelProperty(value = "账户可用余额抵扣")
    private Integer balancePrice;

    @ApiModelProperty(value = "账户不可用余额抵扣")
    private Integer noCashBalancePrice;

    @ApiModelProperty(value = "账户不可用余额")
    private Integer noCashBalance;

    @ApiModelProperty(value = "账户可用余额")
    private Integer balance;

    @ApiModelProperty(value = "运单编号")
    private String deliverSeq;

    @ApiModelProperty(value = "物流信息")
    private List<LogisticsInfoModel> logisticsInfo;

    @ApiModelProperty(value = "商品信息")
    private String createDate;

    private String memo;

    private Integer total;

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
        private SkuInfoModel skuInfo;
        @ApiModelProperty(value = "当前sku下单数量")
        private Integer num = 0;
        @ApiModelProperty(value = "订单中该商品实际支付的金额")
        private Integer realTotalPrice;
        @ApiModelProperty(value = "提交订单时刻,sku的单价")
        private Integer skuPrice;
        @ApiModelProperty(value = "退货状态")
        private RefundModel refund;
        @ApiModelProperty(value = "商品编号")
        private String code;


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

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    /**
     * 同Order中的OrderItem
     */
    @ApiModel
    public static class LogisticsInfoModel {
        @ApiModelProperty(value = "物流公司")
        private String company;

        @ApiModelProperty(value = "物流状态")
        private String deliverStatus;

        @ApiModelProperty(value = "物流单号")
        private String expressNo;

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getDeliverStatus() {
            return deliverStatus;
        }

        public void setDeliverStatus(String deliverStatus) {
            this.deliverStatus = deliverStatus;
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

    public AddressInfo getAddress() {
        return address;
    }

    public void setAddress(AddressInfo address) {
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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getBalancePrice() {
        return balancePrice;
    }

    public void setBalancePrice(Integer balancePrice) {
        this.balancePrice = balancePrice;
    }

    public Integer getNoCashBalancePrice() {
        return noCashBalancePrice;
    }

    public void setNoCashBalancePrice(Integer noCashBalancePrice) {
        this.noCashBalancePrice = noCashBalancePrice;
    }

    public Integer getNoCashBalance() {
        return noCashBalance;
    }

    public void setNoCashBalance(Integer noCashBalance) {
        this.noCashBalance = noCashBalance;
    }

    public Integer getUseDeduction() {
        return useDeduction;
    }

    public void setUseDeduction(Integer useDeduction) {
        this.useDeduction = useDeduction;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getDeliverSeq() {
        return deliverSeq;
    }

    public void setDeliverSeq(String deliverSeq) {
        this.deliverSeq = deliverSeq;
    }

    public List<LogisticsInfoModel> getLogisticsInfo() {
        return logisticsInfo;
    }

    public void setLogisticsInfo(List<LogisticsInfoModel> logisticsInfo) {

        this.logisticsInfo = logisticsInfo;
    }

}
