package net.kingsilk.qh.agency.core;

/**
 * Created by lit on 17/9/4.
 */
public enum SysConfTypeEnum {

    INT("INT","数字类型"),
    TEXT("TEXT","自定义文本"),
    LIST("LIST","单选列表"),
    MAP("MAP","集合字段");
    SysConfTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    SysConfTypeEnum(String code) {
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
