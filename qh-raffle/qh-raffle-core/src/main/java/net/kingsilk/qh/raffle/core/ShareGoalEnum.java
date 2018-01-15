package net.kingsilk.qh.raffle.core;

public enum ShareGoalEnum {

    FRIEND("FRIEND","朋友"),
    CIRCLE_FRIENDS("CIRCLE_FRIENDS","朋友圈");

    ShareGoalEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    ShareGoalEnum(String code) {
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
