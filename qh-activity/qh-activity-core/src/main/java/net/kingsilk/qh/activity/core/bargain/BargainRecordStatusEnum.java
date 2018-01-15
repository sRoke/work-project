package net.kingsilk.qh.activity.core.bargain;

public enum BargainRecordStatusEnum {

    PROGRESS("PROGRESS", "进行中"),
    FINISH("FINISH", "已完成"),
    RECEIVED("RECEIVED", "已领取");

    final String desp;
    final String code;

    BargainRecordStatusEnum(String code, String desp) {
        this.desp = desp;
        this.code = code;
    }

}
