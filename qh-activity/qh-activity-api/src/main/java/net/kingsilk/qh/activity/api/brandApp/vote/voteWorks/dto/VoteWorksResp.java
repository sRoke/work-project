package net.kingsilk.qh.activity.api.brandApp.vote.voteWorks.dto;

import net.kingsilk.qh.activity.api.common.dto.Base;
import net.kingsilk.qh.activity.core.vote.VoteWorksStatusEnum;

/**
 * 参赛人报名信息
 */
public class VoteWorksResp extends Base {

    /**
     * 报名活动id
     */
    private String voteActivityId;

    /**
     * 编号，从1开始累加
     */
    private String seq;

    /**
     * 排序号，数字越小越靠前
     */
    private int order = Integer.MAX_VALUE;

    /**
     * 参赛者userId
     */
    private String userId;

    /**
     * 报名时间
     */
    private String signUpTime;

    /**
     * 状态
     */
    private VoteWorksStatusEnum status;

    //----------------------报名信息-------------------//

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 排名
     */

    private int rank;

    /**
     * 参赛感言
     */

    private String slogan;

    /**
     * 参赛作品
     */
    private String worksImgUrl;

    //------------------------统计相关信息----------------//

    /**
     * 累计票数
     */
    private int totalVotes;

    /**
     * 后台加票数量
     */
    private int virtualVotes;

    /**
     * 累计访问
     */
    private int pv;

    /**
     * 名次
     */
    private Integer ranking;

    /**
     * 距离上一名的差票
     */
    private Integer lessVoteNum;

    public String getVoteActivityId() {
        return voteActivityId;
    }

    public void setVoteActivityId(String voteActivityId) {
        this.voteActivityId = voteActivityId;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSignUpTime() {
        return signUpTime;
    }

    public void setSignUpTime(String signUpTime) {
        this.signUpTime = signUpTime;
    }

    public VoteWorksStatusEnum getStatus() {
        return status;
    }

    public void setStatus(VoteWorksStatusEnum status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getWorksImgUrl() {
        return worksImgUrl;
    }

    public void setWorksImgUrl(String worksImgUrl) {
        this.worksImgUrl = worksImgUrl;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }

    public int getVirtualVotes() {
        return virtualVotes;
    }

    public void setVirtualVotes(int virtualVotes) {
        this.virtualVotes = virtualVotes;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getLessVoteNum() {
        return lessVoteNum;
    }

    public void setLessVoteNum(Integer lessVoteNum) {
        this.lessVoteNum = lessVoteNum;
    }
}
