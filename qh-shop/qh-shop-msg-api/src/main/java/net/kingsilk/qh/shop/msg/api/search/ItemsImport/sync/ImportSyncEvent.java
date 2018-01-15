package net.kingsilk.qh.shop.msg.api.search.ItemsImport.sync;

import java.io.Serializable;

/**
 * 通知同步 指定微信公众号 下用户信息。
 *
 * 加锁路径为： ${prefix}/${className}/${wxMpAppId}
 *
 */
public class ImportSyncEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 微信公众号 APP ID。
     */
    private String brandAppId;

    private String shopId;

    private String dbFileId;

    private String daFileName;

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getDbFileId() {
        return dbFileId;
    }

    public void setDbFileId(String dbFileId) {
        this.dbFileId = dbFileId;
    }

    public String getDaFileName() {
        return daFileName;
    }

    public void setDaFileName(String daFileName) {
        this.daFileName = daFileName;
    }
}
