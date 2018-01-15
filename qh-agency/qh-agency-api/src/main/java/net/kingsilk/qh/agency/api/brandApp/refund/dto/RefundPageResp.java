package net.kingsilk.qh.agency.api.brandApp.refund.dto;

import io.swagger.annotations.ApiModel;
import net.kingsilk.qh.agency.api.UniPageResp;

import java.util.Map;

//import sun.jvm.hotspot.debugger.Page;

@ApiModel(value = "订单分页返回信息")
public class RefundPageResp<RefundPageInfo> extends UniPageResp<RefundPageInfo> {


    private Map<String, String> refundTypeEnumMap;
    private Map<String, String> refundStatusEnumMap;
    private Map<String, String> refundReasonEnumMap;

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
}
