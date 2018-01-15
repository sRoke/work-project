package net.kingsilk.qh.activity.domain;

import net.kingsilk.qh.activity.core.bargain.BargainRecordStatusEnum;

import java.util.ArrayList;
import java.util.List;

public class BargainRecord extends Base {

    /**
     * 砍价活动Id
     */
    private String bargainId;

    /**
     * 被砍价的用户
     */
    private String userId;

    /**
     * 砍价的商品
     */
    private String skuId;

    /**
     * 砍价最终价格
     */
    private Integer finalPrice;

    /**
     * 活动状态
     */
    private BargainRecordStatusEnum status = BargainRecordStatusEnum.PROGRESS;

    /**
     * 帮忙砍价好友列表
     */
    private List<String> helpUsers = new ArrayList<>();

    public String getBargainId() {
        return bargainId;
    }

    public void setBargainId(String bargainId) {
        this.bargainId = bargainId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Integer getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Integer finalPrice) {
        this.finalPrice = finalPrice;
    }

    public BargainRecordStatusEnum getStatus() {
        return status;
    }

    public void setStatus(BargainRecordStatusEnum status) {
        this.status = status;
    }

    public List<String> getHelpUsers() {
        return helpUsers;
    }

    public void setHelpUsers(List<String> helpUsers) {
        this.helpUsers = helpUsers;
    }
}
