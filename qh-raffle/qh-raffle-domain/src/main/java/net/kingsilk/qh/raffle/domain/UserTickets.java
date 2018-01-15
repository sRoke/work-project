package net.kingsilk.qh.raffle.domain;

public class UserTickets extends Base {

    private String userId;

    private String raffleId;

    private String raffleAppId;

    /**
     * 分享得的总票数
     */
    private Integer shareTotalTicket = 0;

    /**
     * 当前用户剩余的总票数
     */
    private Integer surplusTicket = 0;

    /**
     * 已经投出的总票数
     */
    private Integer usedTotalTicket = 0;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRaffleId() {
        return raffleId;
    }

    public void setRaffleId(String raffleId) {
        this.raffleId = raffleId;
    }

    public String getraffleAppId() {
        return raffleAppId;
    }

    public void setraffleAppId(String raffleAppId) {
        this.raffleAppId = raffleAppId;
    }

    public Integer getShareTotalTicket() {
        return shareTotalTicket;
    }

    public void setShareTotalTicket(Integer shareTotalTicket) {
        this.shareTotalTicket = shareTotalTicket;
    }

    public Integer getSurplusTicket() {
        return surplusTicket;
    }

    public void setSurplusTicket(Integer validTotalTicket) {
        this.surplusTicket = validTotalTicket;
    }

    public Integer getUsedTotalTicket() {
        return usedTotalTicket;
    }

    public void setUsedTotalTicket(Integer usedTotalTicket) {
        this.usedTotalTicket = usedTotalTicket;
    }
}
