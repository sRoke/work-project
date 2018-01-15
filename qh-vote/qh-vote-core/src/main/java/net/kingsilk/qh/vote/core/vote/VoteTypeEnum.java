package net.kingsilk.qh.vote.core.vote;

/**
 *
 */
public enum VoteTypeEnum {
    ONLY_ONE("ONLY_ONE", "仅一人"),
    MANY("MANY", "多人"),
    UNLIMITED("UNLIMITED", "不限制");

    VoteTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    VoteTypeEnum(String code) {
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
