package net.kingsilk.qh.agency.core;

import java.util.HashMap;
import java.util.Map;

public enum RefundReasonEnum {

    NO_REASON("NO_REASON", "七天无理由退换货"),
    FREIGHT_REFUND("FREIGHT_REFUND", "退运费"),
    SEND_WRONG("SEND_WRONG", "商品错发/漏发"),
    DAMAGED_GOODS("DAMAGED_GOODS", "收到商品破损"),
    DESCRIBE_DIFF("DESCRIBE_DIFF", "收到商品与描述不符"),
    QUALITY_PROBLEM("QUALITY_PROBLEM", "商品质量问题"),
    OVERTIME("OVERTIME", "未按约定时间发货"),
    DO_NOT_WANT("DO_NOT_WANT", "不想要了"),
    OTHER("OTHER", "其它");

    RefundReasonEnum(String code) {
        this(code, null);
    }

    RefundReasonEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    public String getCode() {
        return code;
    }

    public String getDesp() {
        return desp;
    }

    public final String getDescription() {
        return desp;
    }

    private final String code;
    private final String desp;

    public static Map<String,String> getMap(){
        Map<String,String> enumMap = new HashMap<String,String>();
        for(RefundReasonEnum refundReasonEnum : RefundReasonEnum.values()){
            enumMap.put(refundReasonEnum.code,refundReasonEnum.desp);
        }
        return enumMap;
    }
}
