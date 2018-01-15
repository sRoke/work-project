package net.kingsilk.qh.shop.core;

/**
 * Created by lit on 17/8/31.
 */
public enum ActivityTypeEnum {

    ITEM_SPECIALS("ITEM_SPECIALS","商品限时特价"),
    ITEM_FULL_REDUCE("ITEM_FULL_REDUCE","商品满减");
//    DISCOUNT("DISCOUNT", "折扣活动"),
//    REDUCE("REDUCE", "减价活动");

    private final String code;

    private final String desp;

    ActivityTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }
    public String getCode() {
        return code;
    }

    public String getDesp() {
        return desp;
    }

}
