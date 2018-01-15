package net.kingsilk.qh.agency.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zcw on 3/13/17.
 * 售后类型
 */
public enum RefundTypeEnum {
    /**
     * 取消整个订单
     */
//    CANCEL("CANCEL", "取消订单"),

    /**
     * 单间商品仅退款
     */
    MONEY_ONLY("MONEY_ONLY", "仅退款"),

    /**
     * 单间商品退款并退款
     */
    ITEM("ITEM", "退货并退款");

    RefundTypeEnum(String code) {
        this(code, null);
    }

    RefundTypeEnum(String code, String desp) {
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
        for(RefundTypeEnum refundTypeEnum : RefundTypeEnum.values()){
            enumMap.put(refundTypeEnum.code,refundTypeEnum.desp);
        }
        return enumMap;
    }
}
