package net.kingsilk.qh.agency.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zcw on 3/13/17.
 * 售后状态
 */
public enum RefundStatusEnum {

    /**
     * 待买家确认
     */
    BUYER_UNCHECKED("BUYER_UNCHECKED", "待买家确认"),
    /**
     * 待确认
     */
    UNCHECKED("UNCHECKED", "待确认"),

    /**
     * 卖家拒绝售后
     */
    REJECTED("REJECTED", "卖家拒绝售后"),

    /**
     * 待退货，用户选择退货并退款时，卖家确认后到此状态
     */
    WAIT_SENDING("WAIT_SENDING", "待退货"),

    /**
     * 接上个状态，用户发货后，变为此状态
     */
    WAIT_RECEIVED("WAIT_RECEIVED", "待收货"),

    /**
     * 仅退款、取消订单，从待确认到此状态；
     * 退货并退款，卖家收货后到此状态
     */
    UNPAYED("UNPAYED", "待退款"),

    /**
     * 任意状态取消；
     * todo 待和产品确认
     */
    //CANCEL("CANCEL", "已取消"),

    CLOSED("CLOSED","已关闭"),

    /**
     * 正常退款完成
     */
    FINISHED("FINISHED", "已完成");

    RefundStatusEnum(String code) {
        this(code, null);
    }

    RefundStatusEnum(String code, String desp) {
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
        for(RefundStatusEnum refundStatusEnum : RefundStatusEnum.values()){
            enumMap.put(refundStatusEnum.code,refundStatusEnum.desp);
        }
        return enumMap;
    }
}
