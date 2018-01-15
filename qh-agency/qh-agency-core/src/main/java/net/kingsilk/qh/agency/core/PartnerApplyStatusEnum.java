package net.kingsilk.qh.agency.core;

/**
 * Created by lit on 17/7/18.
 */
public enum  PartnerApplyStatusEnum {

    APPLYING("APPLYING", "待审核"),
    NORMAL("NORMAL", "审核通过"),
    REJECT("REJECT", "已拒绝");

    PartnerApplyStatusEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    PartnerApplyStatusEnum(String code) {
        this(code, null);
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
