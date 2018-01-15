package net.kingsilk.qh.agency.core;

/**
 * Created by zcw on 3/13/17.
 * 快递跟踪状态。
 * 状态 "退签"、"同城派送中"、"退回"、"转单" 需要与快递100签订增值服务才能开通。
 */
public enum LogisticsStatusEnum {

    ON_WAY("ON_WAY", "运输中"),
    POSTED("POSTED", "已揽收"),
    IN_TROUBLE("IN_TROUBLE", "疑难"),
    RECEIVED("RECEIVED", "已签收"),
    REJECTED("REJECTED", "退签"),
    CITY_WIDE_ON_WAY("CITY_WIDE_ON_WAY", "同城派送中"),
    RETURNED("RETURNED", "退回"),
    SEND_AGAIN("SEND_AGAIN", "转单");

    LogisticsStatusEnum(String code) {
        this(code, null);
    }

    LogisticsStatusEnum(String code, String desp) {
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
}
