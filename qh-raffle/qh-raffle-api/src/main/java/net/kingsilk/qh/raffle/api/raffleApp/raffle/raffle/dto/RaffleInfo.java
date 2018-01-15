package net.kingsilk.qh.raffle.api.raffleApp.raffle.raffle.dto;

import net.kingsilk.qh.raffle.api.raffleApp.raffle.award.dto.AwardInfo;
import net.kingsilk.qh.raffle.core.DrawTypeEnum;
import net.kingsilk.qh.raffle.core.RaffleStatusEnum;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class RaffleInfo {


//    /**
//     * 抽奖活动自定义活动编码
//     */
//    private String seq;

    /**
     * 抽奖活动名称
     */
    private String raffleName;

    /**
     * 抽奖活动开始时间
     */
    private Date beginTime;

    /**
     * 抽奖活动结束时间
     */
    private Date endTime;

    /**
     * 抽奖活动免费次数
     */
    private Integer freeCount = 0;

    /**
     * 分享可获得抽奖的次数
     */
    private Integer shareCount = 0;

    /**
     * 总共做多抽奖次数
     */
    private Integer lotteryCount = 0;

    /**
     * 抽奖活动领取方式
     */
    private DrawTypeEnum drawType;

    /**
     * 抽奖活动大转盘图片
     */
    private String dialImg;

    /**
     * 抽奖活动规则
     */
    private String rule;

    /**
     * 抽奖活动图文说明
     */
    private String desp;

    /**
     * 抽奖活动状态
     */
    private RaffleStatusEnum raffleStatus;

    /**
     * 抽奖活动奖品
     */
    private List<AwardInfo> awards = new LinkedList<AwardInfo>();

    /**
     * 关联公众号二维码
     * @return
     */
    private String barcode;

    /**
     * 关联公众号二维码
     * @return
     */
    private Boolean mustFollow;
    /**
     * 活动品牌
     */
    private String  raffleAppId;

    /**
     * 分享标题
     */
    private String shareTitle;
    /**
     * 分享描述
     */
    private String shareDesp;
    /**
     * 分享链接
     */
    private String shareUrl;

    /**
     * 分享图片
     */
    private String shareImg;

    /**
     * 参与人数
     */
    private Integer joins;

    /**
     * 当前用户已经抽奖的次数
     */
    private Integer usedTotalTicket;

    /**
     * 当前用户剩余的有效票数
     */
    private Integer surplus;

    /**
     * 当前用户是否还可以获得抽奖次数
     */
    private Boolean canShare;
//    /**
//     * 关联的获奖活动批次
//     */
//    private String batchId;
//    /**
//     * 获奖活动入口
//     */
//    private RaffleEntranceEnum raffleEntrance;
//    /**
//     * 分享图片
//     */
//    /**
//     * 抽奖活动 积分消耗
//     */
//    private Integer integralConsumption;
//    private YunFile shareImg;
//
//    /**
//     * 登录页图片
//     */
//    private YunFile loginImg;
//    /**
//     * 二维码图片
//     */
//    private YunFile qRCodeImg;


    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public String getRaffleName() {
        return raffleName;
    }

    public void setRaffleName(String raffleName) {
        this.raffleName = raffleName;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getUsedTotalTicket() {
        return usedTotalTicket;
    }

    public void setUsedTotalTicket(Integer usedTotalTicket) {
        this.usedTotalTicket = usedTotalTicket;
    }

    public String getRaffleAppId() {
        return raffleAppId;
    }

    public void setRaffleAppId(String raffleAppId) {
        this.raffleAppId = raffleAppId;
    }

    public Boolean getMustFollow() {
        return mustFollow;
    }

    public void setMustFollow(Boolean mustFollow) {
        this.mustFollow = mustFollow;
    }

    public Integer getFreeCount() {
        return freeCount;
    }

    public DrawTypeEnum getDrawType() {
        return drawType;
    }

    public void setDrawType(DrawTypeEnum drawType) {
        this.drawType = drawType;
    }

    public void setFreeCount(Integer freeCount) {
        this.freeCount = freeCount;
    }

    public Integer getLotteryCount() {
        return lotteryCount;
    }

    public Boolean getCanShare() {
        return canShare;
    }

    public void setCanShare(Boolean canShare) {
        this.canShare = canShare;
    }

    public void setLotteryCount(Integer lotteryCount) {
        this.lotteryCount = lotteryCount;
    }

//    public List<String> getLostPrompts() {
//        return lostPrompts;
//    }
//
//    public void setLostPrompts(List<String> lostPrompts) {
//        this.lostPrompts = lostPrompts;
//    }

    public String getDialImg() {
        return dialImg;
    }

    public void setDialImg(String dialImg) {
        this.dialImg = dialImg;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public RaffleStatusEnum getRaffleStatus() {
        return raffleStatus;
    }

    public void setRaffleStatus(RaffleStatusEnum raffleStatus) {
        this.raffleStatus = raffleStatus;
    }

    public List<AwardInfo> getAwards() {
        return awards;
    }

    public void setAwards(List<AwardInfo> awards) {
        this.awards = awards;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareDesp() {
        return shareDesp;
    }

    public void setShareDesp(String shareDesp) {
        this.shareDesp = shareDesp;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
    }

    public Integer getJoins() {
        return joins;
    }

    public void setJoins(Integer joins) {
        this.joins = joins;
    }

    public Integer getSurplus() {
        return surplus;
    }

    public void setSurplus(Integer surplus) {
        this.surplus = surplus;
    }
}