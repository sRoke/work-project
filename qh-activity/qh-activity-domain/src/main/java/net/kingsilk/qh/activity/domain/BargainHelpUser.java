package net.kingsilk.qh.activity.domain;

public class BargainHelpUser extends Base {

    /**
     * 帮砍价的用户Id
     */
    private String userId;

    /**
     * 砍价
     */
    private Integer helpPrice;

    /**
     * 砍价活动Id
     */
    private String bargainId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getHelpPrice() {
        return helpPrice;
    }

    public void setHelpPrice(Integer helpPrice) {
        this.helpPrice = helpPrice;
    }

    public String getBargainId() {
        return bargainId;
    }

    public void setBargainId(String bargainId) {
        this.bargainId = bargainId;
    }
}
