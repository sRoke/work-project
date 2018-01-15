package net.kingsilk.qh.agency.core;

/**
 * Created by lit on 17/8/24.
 */
public enum PartnerOperateEnum {
    APPLYING("APPLYING", "渠道商申请"),
    AUDITING("AUDITING", "渠道商审核"),
    UPGRADE("UPGRADE", "渠道商类型变更"),
    BASIC("BASIC", "基础信息变更"),
    STATUS("STATUS", "渠道商状态变更");

    PartnerOperateEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    PartnerOperateEnum(String code) {
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
