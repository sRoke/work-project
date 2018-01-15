package net.kingsilk.qh.shop.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 最近搜索
 */
@Document
public class RecentSearch extends Base {

    /**
     * 商家id
     */
    private String brandAppId;

    /**
     * 门店id
     */
    private String shopId;

    /**
     * 会员id
     */
    private String memberId;

    /**
     * 搜索字
     */
    private String keyWord;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }
}
