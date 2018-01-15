package net.kingsilk.qh.raffle.domain;

import net.kingsilk.qh.raffle.core.DrawTypeEnum;
import net.kingsilk.qh.raffle.core.RaffleEntranceEnum;
import net.kingsilk.qh.raffle.core.RaffleStatusEnum;
import net.kingsilk.qh.raffle.core.RaffleTypeEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Raffle extends Base {

    /**
     * 抽奖活动类型
     */
    private RaffleTypeEnum raffleType = RaffleTypeEnum.DIAL;

    /**
     * 抽奖活动自定义活动编码
     */
    private String seq;

    /**
     * 关联公众号二维码
     *
     * @return
     */
    private Boolean mustFollow = true;

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
     * 总共抽奖次数
     */
    private Integer lotteryCount = 0;

    /**
     * 抽奖活动是否每天
     */
    private Boolean isDaily = true;

    /**
     * 抽奖活动 积分消耗
     */
    private Integer integralConsumption;

    /**
     * 抽奖活动未中奖提示语
     */
    private List<String> lostPrompts = new ArrayList<String>();

    /**
     * 抽奖活动大转盘图片
     */
    private String dialImg;

    /**
     * 抽奖活动规则
     */
    private String rule;

    /**
     * 抽奖活动领取方式
     */
    private DrawTypeEnum drawType;

    /**
     * 抽奖活动图文说明
     */
    private String desp;

    /**
     * 抽奖活动状态
     */
    private RaffleStatusEnum raffleStatus = RaffleStatusEnum.EDITING;

    /**
     * 获奖活动入口
     */
    private RaffleEntranceEnum raffleEntrance;

    /**
     * 关联的获奖活动批次
     */
    private String batchId;

    /**
     * 抽奖活动奖品
     */
    private List<String> awardIds = new LinkedList<>();

    /**
     * 关联公众号二维码
     * @return
     */
//    private YunFile barcode;

    /**
     * 活动品牌
     */
    private String raffleAppId;

    /**
     * 分享标题
     */
    private String shareTitle;
    /**
     * 分享描述
     */
    private String shareDesc;
    /**
     * 分享链接
     */
    private String shareUrl;
    /**
     * 分享图片
     */
    private String shareImg;

    /**
     * 参加的人数
     */
    private Integer joins;
//
//    /**
//     * 登录页图片
//     */
//    private YunFile loginImg;
//    /**
//     * 二维码图片
//     */
//    private YunFile qRCodeImg;


    public RaffleTypeEnum getRaffleType() {
        return raffleType;
    }

    public void setRaffleType(RaffleTypeEnum raffleType) {
        this.raffleType = raffleType;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
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

    public Integer getFreeCount() {
        return freeCount;
    }

    public void setFreeCount(Integer freeCount) {
        this.freeCount = freeCount;
    }

    public Integer getLotteryCount() {
        return lotteryCount;
    }

    public void setLotteryCount(Integer lotteryCount) {
        this.lotteryCount = lotteryCount;
    }

    public Boolean getDaily() {
        return isDaily;
    }

    public void setDaily(Boolean daily) {
        isDaily = daily;
    }

    public Integer getIntegralConsumption() {
        return integralConsumption;
    }

    public void setIntegralConsumption(Integer integralConsumption) {
        this.integralConsumption = integralConsumption;
    }

    public List<String> getLostPrompts() {
        return lostPrompts;
    }

    public void setLostPrompts(List<String> lostPrompts) {
        this.lostPrompts = lostPrompts;
    }

    public String getDialImg() {
        return dialImg;
    }

    public DrawTypeEnum getDrawType() {
        return drawType;
    }

    public void setDrawType(DrawTypeEnum drawType) {
        this.drawType = drawType;
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

    public RaffleEntranceEnum getRaffleEntrance() {
        return raffleEntrance;
    }

    public void setRaffleEntrance(RaffleEntranceEnum raffleEntrance) {
        this.raffleEntrance = raffleEntrance;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public List<String> getAwardIds() {
        return awardIds;
    }

    public void setAwardIds(List<String> awardIds) {
        this.awardIds = awardIds;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public Boolean getMustFollow() {
        return mustFollow;
    }

    public void setMustFollow(Boolean mustFollow) {
        this.mustFollow = mustFollow;
    }

    public String getRaffleAppId() {
        return raffleAppId;
    }

    public void setRaffleAppId(String raffleAppId) {
        this.raffleAppId = raffleAppId;
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

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public Integer getJoins() {
        return joins;
    }

    public void setJoins(Integer joins) {
        this.joins = joins;
    }
}