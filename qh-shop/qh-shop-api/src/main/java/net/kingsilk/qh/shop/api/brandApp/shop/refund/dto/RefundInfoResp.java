package net.kingsilk.qh.shop.api.brandApp.shop.refund.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.core.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@ApiModel(value = "RefundInfoResp")
public class RefundInfoResp {

    @ApiParam(value = "id")
    private String id;

    private String brandAppId;

    /**
     * 编号
     */
    private String seq;

    /**
     * 退款状态
     */
    private RefundStatusEnum refundStatus;
    private String refundStatusDesp;

    /**
     * 退款类型
     */
    private RefundTypeEnum refundType;
    private String refundTypeDesp;

    /**
     * 付款时间
     */
    private String payTime;
    //下单人
    private String realName;
    //手机号
    private String phone;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 申请退货的skus
     */
    private Set<Sku> skus;

    /**
     * 申请退货的金额
     */
    private Integer refundMoney;

    /**
     * 客服修改的金额
     */
    private Integer adjustMoney;

    /**
     * 需退款到支付宝的最终金额(单价：分)
     */
    private Integer aliAmount = 0;

    /**
     * 需退款到微信的最终金额(单价：分)
     */
    private Integer wxAmount = 0;

    /**
     * 退货原因
     */
    private String reason;

    /**
     * 备注
     */
    private String memo;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    /**
     * 退货款快递状态
     */
    private ExpressStatusEnum expressStatus;
    private String expressStatusDesp;

    /**
     * 买家退货时间
     */
    private String buyerDeliveredTime;

    /**
     * 卖家确认收货时间
     */
    private String receivedTime;

    /**
     * 物流信息
     */
    private String logisticsId;

    /**
     * 门店
     */
    private String shopId;

    /**
     * 所选的物流公司
     */
    private LogisticsCompanyEnum company;
    private String companyName;

    /**
     * 快递单号
     */
    private String expressNo;

    /**
     * 快递单状态
     */
    private LogisticsStatusEnum status;

    private AddressInfo addrInfo;


    //内部类
    public static class Sku {

        //申请退货的数量
        private Integer applyedNum;

        //申请退货的订单总金额
        private Integer orderPrice;

        //申请退货的sku实际支付金额
        private Integer skuPrice;

        //skuId
        private String skuId;

        //商品标题
        private String title;

        /**
         * sku主图
         */
        private LinkedHashSet<String> skuImg;

        //商品属性和属性值
        private List<SkuSpec> skuSpecs;

        public static class SkuSpec {
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

        public LinkedHashSet<String> getSkuImg() {
            return skuImg;
        }

        public void setSkuImg(LinkedHashSet<String> skuImg) {
            this.skuImg = skuImg;
        }

        public Integer getApplyedNum() {
            return applyedNum;
        }

        public void setApplyedNum(Integer applyedNum) {
            this.applyedNum = applyedNum;
        }

        public Integer getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(Integer orderPrice) {
            this.orderPrice = orderPrice;
        }

        public Integer getSkuPrice() {
            return skuPrice;
        }

        public void setSkuPrice(Integer skuPrice) {
            this.skuPrice = skuPrice;
        }

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<SkuSpec> getSkuSpecs() {
            return skuSpecs;
        }

        public void setSkuSpecs(List<SkuSpec> skuSpecs) {
            this.skuSpecs = skuSpecs;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public RefundStatusEnum getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(RefundStatusEnum refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRefundStatusDesp() {
        return refundStatusDesp;
    }

    public void setRefundStatusDesp(String refundStatusDesp) {
        this.refundStatusDesp = refundStatusDesp;
    }

    public RefundTypeEnum getRefundType() {
        return refundType;
    }

    public void setRefundType(RefundTypeEnum refundType) {
        this.refundType = refundType;
    }

    public String getRefundTypeDesp() {
        return refundTypeDesp;
    }

    public void setRefundTypeDesp(String refundTypeDesp) {
        this.refundTypeDesp = refundTypeDesp;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Set<Sku> getSkus() {
        return skus;
    }

    public void setSkus(Set<Sku> skus) {
        this.skus = skus;
    }

    public Integer getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Integer refundMoney) {
        this.refundMoney = refundMoney;
    }

    public Integer getAdjustMoney() {
        return adjustMoney;
    }

    public void setAdjustMoney(Integer adjustMoney) {
        this.adjustMoney = adjustMoney;
    }

    public Integer getAliAmount() {
        return aliAmount;
    }

    public void setAliAmount(Integer aliAmount) {
        this.aliAmount = aliAmount;
    }

    public Integer getWxAmount() {
        return wxAmount;
    }

    public void setWxAmount(Integer wxAmount) {
        this.wxAmount = wxAmount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public ExpressStatusEnum getExpressStatus() {
        return expressStatus;
    }

    public void setExpressStatus(ExpressStatusEnum expressStatus) {
        this.expressStatus = expressStatus;
    }

    public String getBuyerDeliveredTime() {
        return buyerDeliveredTime;
    }

    public void setBuyerDeliveredTime(String buyerDeliveredTime) {
        this.buyerDeliveredTime = buyerDeliveredTime;
    }

    public String getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(String receivedTime) {
        this.receivedTime = receivedTime;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public AddressInfo getAddrInfo() {
        return addrInfo;
    }

    public void setAddrInfo(AddressInfo addrInfo) {
        this.addrInfo = addrInfo;
    }

    public String getExpressStatusDesp() {
        return expressStatusDesp;
    }

    public void setExpressStatusDesp(String expressStatusDesp) {
        this.expressStatusDesp = expressStatusDesp;
    }

    public LogisticsCompanyEnum getCompany() {
        return company;
    }

    public void setCompany(LogisticsCompanyEnum company) {
        this.company = company;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public LogisticsStatusEnum getStatus() {
        return status;
    }

    public void setStatus(LogisticsStatusEnum status) {
        this.status = status;
    }


}
