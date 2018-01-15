package net.kingsilk.qh.activity.core.vote;

/**
 * Created by lit on 17/10/17.
 */
public enum VoteSoureEnum {

    VIRTUAL("VIRTUAL", "后台票"),
    NORMAL("NORMAL", "正常");


    VoteSoureEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    VoteSoureEnum(String code) {
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
