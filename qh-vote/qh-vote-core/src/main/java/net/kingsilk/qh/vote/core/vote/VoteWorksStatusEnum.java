package net.kingsilk.qh.vote.core.vote;

/**
 * 投票活动参与人状态枚举
 */
public enum VoteWorksStatusEnum {
    APPLYING("APPLYING", "待审核"),
    NORMAL("NORMAL", "审核通过"),
    REJECT("REJECT", "已拒绝");

    VoteWorksStatusEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    VoteWorksStatusEnum(String code) {
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
