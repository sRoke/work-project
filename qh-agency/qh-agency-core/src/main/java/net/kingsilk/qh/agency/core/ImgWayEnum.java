package net.kingsilk.qh.agency.core;

/**
 * 图片上传的加密方式。
 * @deprecated  挪到独立工程中。
 */
@Deprecated
public enum ImgWayEnum {
    MD5("MD5", "MD5"),
    ETAG("ETAG", "ETAG");

    ImgWayEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    ImgWayEnum(String code) {
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
