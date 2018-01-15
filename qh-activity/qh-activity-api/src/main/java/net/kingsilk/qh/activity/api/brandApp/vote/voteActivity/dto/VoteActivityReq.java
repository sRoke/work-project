package net.kingsilk.qh.activity.api.brandApp.vote.voteActivity.dto;


import net.kingsilk.qh.activity.api.common.dto.Base;
import net.kingsilk.qh.activity.core.vote.VoteStatusEnum;

import javax.ws.rs.QueryParam;

/**
 * 投票活动
 */
public class VoteActivityReq extends Base {

    /**
     * brandAppId，根据platform系统生成
     */
    @QueryParam("brandAppId")
    private String brandAppId;

    /**
     * 活动名称
     */
    @QueryParam("activityName")
    private String activityName;

    /**
     * 报名开始时间
     */
    @QueryParam("signUpStartTime")
    private String signUpStartTime;

    /**
     * 报名结束时间
     */
    @QueryParam("signUpEndTime")
    private String signUpEndTime;

    /**
     * 投票开始时间
     */
    @QueryParam("voteStartTime")
    private String voteStartTime;

    /**
     * 投票结束时间
     */
    @QueryParam("voteEndTime")
    private String voteEndTime;

    /**
     * 投票人每天最大允许的投票次数
     */
    @QueryParam("maxVotePerDay")
    private Integer maxVotePerDay;

    /**
     * 投票人累计总共最多投票次数
     * <p>
     * 示例：totalVoteCount=10， voteCountPerDay=3
     * 则能投票 3+3+3+1
     */
    @QueryParam("totalVoteCount")
    private Integer totalVoteCount;

    /**
     * 参赛者每天能从同一个投票人获取的最大票数
     */
    @QueryParam("maxTicketPerDay")
    private Integer maxTicketPerDay;

//    /**
//     * 参赛者累计能从同一个投票人获取的最大票数。
//     */
//    private  Integer totalTickect;

//    /**
//     * 投票类型（只能给一人投票，给限定个数人投票，不限定），枚举类
//     */
//    private VoteTypeEnum voteTypeEnum;

    /**
     * 每天能给多少个选手投票（只能给一人投票，给限定个数人投票，不限定），-1 代表不限定
     */
    @QueryParam("votePeoplePerDay")
    private Integer votePeoplePerDay;

    /**
     * 强制输入手机号
     */
    @QueryParam("forcePhone")
    private Boolean forcePhone;

    /**
     * 强制关注公众号
     */
    @QueryParam("forceFollow")
    private Boolean forceFollow;

    /**
     * 活动主图
     */
    @QueryParam("primaryImgUrl")
    private String primaryImgUrl;

    /**
     * 比赛规则
     */
    @QueryParam("rule")
    private String rule;

    /**
     * 活动图文(副文本)
     */
    @QueryParam("desp")
    private String desp;

    /**
     * 默认参赛感言
     */
    @QueryParam("wordsOfThanks")
    private String wordsOfThanks;

    /**
     *
     */

    /**
     * 分享标题
     */
    @QueryParam("shareTitle")
    private String shareTitle;

    /**
     * 分享内容
     */
    @QueryParam("shareContent")
    private String shareContent;

    /**
     * 状态
     */
    @QueryParam("voteStatusEnum")
    private VoteStatusEnum voteStatusEnum;


    /**
     * 累计票数
     */
    @QueryParam("totalVote")
    private Integer totalVote;

    /**
     * 累计访问
     */
    @QueryParam("totalVisit")
    private Integer totalVisit;

    /**
     * 参与人数
     */
    @QueryParam("totalJoinPeople")
    private Integer totalJoinPeople;

    @QueryParam("mustCheck")
    private Boolean mustCheck;


    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getSignUpStartTime() {
        return signUpStartTime;
    }

    public void setSignUpStartTime(String signUpStartTime) {
        this.signUpStartTime = signUpStartTime;
    }

    public String getSignUpEndTime() {
        return signUpEndTime;
    }

    public void setSignUpEndTime(String signUpEndTime) {
        this.signUpEndTime = signUpEndTime;
    }

    public String getVoteStartTime() {
        return voteStartTime;
    }

    public void setVoteStartTime(String voteStartTime) {
        this.voteStartTime = voteStartTime;
    }

    public String getVoteEndTime() {
        return voteEndTime;
    }

    public void setVoteEndTime(String voteEndTime) {
        this.voteEndTime = voteEndTime;
    }

    public Integer getMaxVotePerDay() {
        return maxVotePerDay;
    }

    public void setMaxVotePerDay(Integer maxVotePerDay) {
        this.maxVotePerDay = maxVotePerDay;
    }

    public Integer getTotalVoteCount() {
        return totalVoteCount;
    }

    public void setTotalVoteCount(Integer totalVoteCount) {
        this.totalVoteCount = totalVoteCount;
    }

    public Integer getMaxTicketPerDay() {
        return maxTicketPerDay;
    }

    public void setMaxTicketPerDay(Integer maxTicketPerDay) {
        this.maxTicketPerDay = maxTicketPerDay;
    }

    public Integer getVotePeoplePerDay() {
        return votePeoplePerDay;
    }

    public void setVotePeoplePerDay(Integer votePeoplePerDay) {
        this.votePeoplePerDay = votePeoplePerDay;
    }

    public Boolean isForcePhone() {
        return forcePhone;
    }

    public void setForcePhone(Boolean forcePhone) {
        this.forcePhone = forcePhone;
    }

    public Boolean isForceFollow() {
        return forceFollow;
    }

    public void setForceFollow(Boolean forceFollow) {
        this.forceFollow = forceFollow;
    }

    public String getPrimaryImgUrl() {
        return primaryImgUrl;
    }

    public void setPrimaryImgUrl(String primaryImgUrl) {
        this.primaryImgUrl = primaryImgUrl;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getWordsOfThanks() {
        return wordsOfThanks;
    }

    public void setWordsOfThanks(String wordsOfThanks) {
        this.wordsOfThanks = wordsOfThanks;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public VoteStatusEnum getVoteStatusEnum() {
        return voteStatusEnum;
    }

    public void setVoteStatusEnum(VoteStatusEnum voteStatusEnum) {
        this.voteStatusEnum = voteStatusEnum;
    }

    public Integer getTotalVote() {
        return totalVote;
    }

    public void setTotalVote(Integer totalVote) {
        this.totalVote = totalVote;
    }

    public Integer getTotalVisit() {
        return totalVisit;
    }

    public void setTotalVisit(Integer totalVisit) {
        this.totalVisit = totalVisit;
    }

    public Integer getTotalJoinPeople() {
        return totalJoinPeople;
    }

    public void setTotalJoinPeople(Integer totalJoinPeople) {
        this.totalJoinPeople = totalJoinPeople;
    }

    public Boolean getMustCheck() {
        return mustCheck;
    }

    public void setMustCheck(Boolean mustCheck) {
        this.mustCheck = mustCheck;
    }
}





