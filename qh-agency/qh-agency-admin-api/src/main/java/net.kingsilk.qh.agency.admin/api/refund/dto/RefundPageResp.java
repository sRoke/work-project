package net.kingsilk.qh.agency.admin.api.refund.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.core.RefundStatusEnum;
import net.kingsilk.qh.agency.core.RefundTypeEnum;
import org.springframework.data.domain.Page;
//import sun.jvm.hotspot.debugger.Page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApiModel(value = "订单分页返回信息")
public class RefundPageResp {


    private Map<String, String> refundTypeEnumMap;
    private Map<String, String> refundStatusEnumMap;
    private Map<String, String> refundReasonEnumMap;
    private Page recPage;

    @ApiModel(value = "订单信息")
    public static class RefundInfo {

        @ApiParam(value = "id")
        /**
         * id
         */
        private String id;
        /**
         * sku信息
         */
        private SkuInfo skuInfo;
        /**
         * 退款类型
         */
        private RefundTypeEnum type;
        /**
         * 退款状态
         */
        private RefundStatusEnum status;
        /**
         * 退款状态描述
         */
        private String statusDesp;
        /**
         * 申请退款人真实姓名
         */
        private String realName;
        /**
         * 申请退款人手机号
         */
        private String phone;
        /**
         * 退款订单编号
         */
        private String orderSeq;
        /**
         * 订单总金额
         */
        private Integer orderAmount;
        /**
         * 需退款的最终金额(单价：分)
         */
        private Integer refundAmount;
        /**
         * 退款原因
         */
        private String reason;
        /**
         * 拒绝退款原因
         */
        private String rejectReason;
        /**
         * 物流地址信息
         */
        private LogisticsInfo logisticsInfo;
        /**
         * 用户发货时间
         */
        private Date deliveryTime;
        /**
         * 后台确认收货时间
         */
        private Date receiveTime;
        /**
         * 退款订单创建时间
         */
        private Date dateCreated;
        /**
         * 备注
         */
        private String memo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public SkuInfo getSkuInfo() {
            return skuInfo;
        }

        public void setSkuInfo(SkuInfo skuInfo) {
            this.skuInfo = skuInfo;
        }

        public RefundTypeEnum getType() {
            return type;
        }

        public void setType(RefundTypeEnum type) {
            this.type = type;
        }

        public RefundStatusEnum getStatus() {
            return status;
        }

        public void setStatus(RefundStatusEnum status) {
            this.status = status;
        }

        public String getStatusDesp() {
            return statusDesp;
        }

        public void setStatusDesp(String statusDesp) {
            this.statusDesp = statusDesp;
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

        public String getOrderSeq() {
            return orderSeq;
        }

        public void setOrderSeq(String orderSeq) {
            this.orderSeq = orderSeq;
        }

        public Integer getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(Integer orderAmount) {
            this.orderAmount = orderAmount;
        }

        public Integer getRefundAmount() {
            return refundAmount;
        }

        public void setRefundAmount(Integer refundAmount) {
            this.refundAmount = refundAmount;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getRejectReason() {
            return rejectReason;
        }

        public void setRejectReason(String rejectReason) {
            this.rejectReason = rejectReason;
        }

        public LogisticsInfo getLogisticsInfo() {
            return logisticsInfo;
        }

        public void setLogisticsInfo(LogisticsInfo logisticsInfo) {
            this.logisticsInfo = logisticsInfo;
        }

        public Date getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(Date deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public Date getReceiveTime() {
            return receiveTime;
        }

        public void setReceiveTime(Date receiveTime) {
            this.receiveTime = receiveTime;
        }

        public Date getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(Date dateCreated) {
            this.dateCreated = dateCreated;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }
    }

    public static class SkuInfo {
//        public SkuInfo convertSkuToResp(Sku sku) {
//            this.id = sku.getId();
//            this.title = sku.getItem().getTitle();
//            this.skuTitle = sku.getTitle();
//            this.skuImage = ((String) (sku.getItem().getImgs().getAt(0)));
//            for (Sku.Spec spec : sku.getSpecs()) {
//                this.specInfos.add(new SpecInfo().convertSpecToResp(spec));
//            }
//
//            return this;
//        }


        private String id;
        private String title;
        private String skuTitle;
        private String skuImage;
        private List<RefundInfoResp.SpecInfo> specInfos = new ArrayList<>();

//        public static class SpecInfo {
////            public SpecInfo convertSpecToResp(Sku.Spec spec) {
////                this.propName = spec.getItemProp().getName();
////                this.propValue = spec.getItemPropValue().name;
////                return this;
////            }
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSkuTitle() {
            return skuTitle;
        }

        public void setSkuTitle(String skuTitle) {
            this.skuTitle = skuTitle;
        }

        public String getSkuImage() {
            return skuImage;
        }

        public void setSkuImage(String skuImage) {
            this.skuImage = skuImage;
        }

        public List<RefundInfoResp.SpecInfo> getSpecInfos() {
            return specInfos;
        }

        public void setSpecInfos(List<RefundInfoResp.SpecInfo> specInfos) {
            this.specInfos = specInfos;
        }
    }

    public static class LogisticsInfo {
//        public LogisticsInfo convertLogisticsToResp(Logistics logistics) {
//            if (logistics.company.asBoolean()) {
//                this.company = ((String) (logistics.company.desp));
//            }
//
//            this.expressNo = ((String) (logistics.expressNo));
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

    public Map<String, String> getRefundTypeEnumMap() {
        return refundTypeEnumMap;
    }

    public void setRefundTypeEnumMap(Map<String, String> refundTypeEnumMap) {
        this.refundTypeEnumMap = refundTypeEnumMap;
    }

    public Map<String, String> getRefundStatusEnumMap() {
        return refundStatusEnumMap;
    }

    public void setRefundStatusEnumMap(Map<String, String> refundStatusEnumMap) {
        this.refundStatusEnumMap = refundStatusEnumMap;
    }

    public Map<String, String> getRefundReasonEnumMap() {
        return refundReasonEnumMap;
    }

    public void setRefundReasonEnumMap(Map<String, String> refundReasonEnumMap) {
        this.refundReasonEnumMap = refundReasonEnumMap;
    }

    public Page getRecPage() {
        return recPage;
    }

    public void setRecPage(Page recPage) {
        this.recPage = recPage;
    }
}
