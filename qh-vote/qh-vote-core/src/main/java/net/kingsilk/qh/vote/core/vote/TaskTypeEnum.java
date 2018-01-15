package net.kingsilk.qh.vote.core.vote;

/**
 * Created by lit on 17/10/17.
 */
public enum TaskTypeEnum {

    VOTERECORDS_TASK("VOTERECORDS_TASK", "投票记录导出"),
    VOTEWORKS_TASK("VOTEWORKS_TASK", "作品列表导出");


    TaskTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    TaskTypeEnum(String code) {
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
