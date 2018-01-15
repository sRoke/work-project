package net.kingsilk.qh.agency.domain;

import java.util.Date;

/**
 * Created by lit on 17/8/29.
 */
public class CertificateTemplate extends Base{
    /**
     * 所属品牌商
     */
    private String brandAppId;

    /**
     * 模板背景图
     */
    private String imgUrl;

    /**
     * 模板有效时间
     */
    private Date expiresAt;

    /**
     * 授权单位
     */
    private String certificateCompany;

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }
}
