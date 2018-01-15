package net.kingsilk.qh.raffle.api.raffleApp.raffle.raffle.dto;


import net.kingsilk.qh.raffle.core.RaffleStatusEnum;

public class RaffleMinResp {

    private String id;

    private String raffleAppid;
    /**
     * 抽奖活动名称
     */
    private String raffleName;

    /**
     * 抽奖活动开始时间
     */
    private String beginTime;
    /**
     * 抽奖活动大转盘图片
     */
    private String dialImg;
    /**
     * 抽奖活动结束时间
     */
    private String endTime;

    /**
     * 参与的人数
     */
    private Integer joins;
    /**
     * 分享标题
     */
    private String shareTitle;
    /**
     * 分享描述
     */
    private String shareDesc;
    /**
     * 活动的状态
     */
    private RaffleStatusEnum status;

    /**
     * 状态描述
     */
    private String statusDesp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRaffleAppid() {
        return raffleAppid;
    }

    public void setRaffleAppid(String raffleAppid) {
        this.raffleAppid = raffleAppid;
    }

    public String getRaffleName() {
        return raffleName;
    }

    public void setRaffleName(String raffleName) {
        this.raffleName = raffleName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getJoins() {
        return joins;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public String getDialImg() {
        return dialImg;
    }

    public void setDialImg(String dialImg) {
        this.dialImg = dialImg;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareDesc() {
        return shareDesc;
    }

    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }

    public void setJoins(Integer joins) {
        this.joins = joins;
    }

    public RaffleStatusEnum getStatus() {
        return status;
    }

    public void setStatus(RaffleStatusEnum status) {
        this.status = status;
    }

    public String getStatusDesp() {
        return statusDesp;
    }

    public void setStatusDesp(String statusDesp) {
        this.statusDesp = statusDesp;
    }
}
