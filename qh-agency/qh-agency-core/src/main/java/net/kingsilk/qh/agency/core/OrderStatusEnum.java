package net.kingsilk.qh.agency.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zcw on 3/13/17.
 * 订单状态
 */
public enum OrderStatusEnum {

    UNCOMMITED("UNCOMMITED", "未提交"),
    UNPAYED("UNPAYED", "待付款"),
    UNCONFIRMED("UNCONFIRMED", "待确认接单"),
    FINANCE_CONFIRM("FINANCE_CONFIRM", "待财务确认"),
    REJECTED("REJECTED", "卖家拒绝接单"),
    CANCELING("CANCELING", "申请取消中"),
    CANCELED("CANCELED", "已取消"),
    UNSHIPPED("UNSHIPPED", "待发货"),
    UNRECEIVED("UNRECEIVED", "待收货"),

    /*
     * 该状态暂不使用
     */
    //UNCOMMENTED("UNCOMMENTED", "待评价"),

    /**
     * 若全部退款完成，已关闭;
     * 未支付的订单，取消，已关闭
     */
    CLOSED("CLOSED", "已关闭"),
    FINISHED("FINISHED", "已完成");

    OrderStatusEnum(String code) {
        this(code, null);
    }

    OrderStatusEnum(String code, String desp) {
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
        for(OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()){
            enumMap.put(orderStatusEnum.code,orderStatusEnum.desp);
        }
        return enumMap;
    }
}
