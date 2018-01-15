package net.kingsilk.qh.vote.core.vote;

public enum BargainStatusEnum {

    EDITING("EDITING", "编辑中"),
    ENABLE("ENABLE", "启用"),
    CLOSED("CLOSED", "关闭");


    BargainStatusEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    public String getCode() {
        return code;
    }

    public String getDesp() {
        return desp;
    }

    private final String code;
    private final String desp;

}
