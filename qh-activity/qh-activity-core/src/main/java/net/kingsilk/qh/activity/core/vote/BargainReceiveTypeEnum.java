package net.kingsilk.qh.activity.core.vote;

public enum BargainReceiveTypeEnum {

    ONLINEBUY("ONLINEBUY", "线上购买"),

    LINEBUY("LINEBUY", "线下联系客服兑换"),

    COMPLETEBUY("COMPLETEBUY", "砍价完成后线上购买");

    BargainReceiveTypeEnum(String code, String desp) {
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
