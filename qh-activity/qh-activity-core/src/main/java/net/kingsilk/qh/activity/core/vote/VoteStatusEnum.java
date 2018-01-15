package net.kingsilk.qh.activity.core.vote;

/**
 * Created by lit on 17/10/17.
 */
public enum VoteStatusEnum {

    EDITING("EDITING", "编辑中"),
    NORMAL("NORMAL", "正常"),
    END("END", "已结束");

    VoteStatusEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    VoteStatusEnum(String code) {
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
