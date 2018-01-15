package net.kingsilk.qh.shop.msg.api.search.esItem.sync;

import java.io.Serializable;

/**
 * 通知同步 指定微信公众号 下用户信息。
 *
 * 加锁路径为： ${prefix}/${className}/${wxMpAppId}
 */
public class SyncEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 微信公众号 APP ID。
     */
    private String itemId;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
