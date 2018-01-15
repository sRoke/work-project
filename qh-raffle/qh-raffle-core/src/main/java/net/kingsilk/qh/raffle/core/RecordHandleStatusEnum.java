package net.kingsilk.qh.raffle.core;

/**
 * 奖品处理状态
 */
public enum RecordHandleStatusEnum {

    HANDLING("HANDLING","待处理"),
    HANDLED("HANDLED","已处理");

    RecordHandleStatusEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    RecordHandleStatusEnum(String code) {
        this(code, null);
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
