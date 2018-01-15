package net.kingsilk.qh.shop.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zcw on 3/13/17.
 * 订单状态
 */
public enum ShopOrderStatusEnum {

    UNCOMMITED("UNCOMMITED", "未提交"),
    UNPAYED("UNPAYED", "待付款"),
//    UNCONFIRMED("UNCONFIRMED", "待接单"),

//    SINCEING("SINCEING", "待自提"),
//    REJECTED("REJECTED", "已拒绝"),

    CANCELED("CANCELED", "已取消"),
//    UNSHIPPED("UNSHIPPED", "待发货"),
//    UNRECEIVED("UNRECEIVED", "待收货"),
//    CANCELING("CANCELING", "申请取消中"),
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

    ShopOrderStatusEnum(String code) {
        this(code, null);
    }

    ShopOrderStatusEnum(String code, String desp) {
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

    public static Map<String, String> getMap() {
        Map<String, String> enumMap = new HashMap<String, String>();
        for (ShopOrderStatusEnum orderStatusEnum : ShopOrderStatusEnum.values()) {
            enumMap.put(orderStatusEnum.code, orderStatusEnum.desp);
        }
        return enumMap;
    }
}
