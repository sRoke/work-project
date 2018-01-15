package net.kingsilk.qh.raffle.domain;

public class WxShareRecord extends Base{

    private String userId;

    private String raffleAppId;

    private String raffleId;

    private String shareUrl;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getraffleAppId() {
        return raffleAppId;
    }

    public void setraffleAppId(String raffleAppId) {
        this.raffleAppId = raffleAppId;
    }

    public String getRaffleId() {
        return raffleId;
    }

    public void setRaffleId(String raffleId) {
        this.raffleId = raffleId;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
}
