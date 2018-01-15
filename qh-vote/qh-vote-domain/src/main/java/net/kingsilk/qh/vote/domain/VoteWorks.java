package net.kingsilk.qh.vote.domain;


import net.kingsilk.qh.vote.core.vote.VoteWorksStatusEnum;

import java.util.Date;

/**
 * 参赛人报名信息
 */
public class VoteWorks extends Base {
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
    private Integer order = Integer.MAX_VALUE;

    /**
     * 排名
     */
    private Integer rank;

    /**
     * 参赛者userId
     */
    private String userId;

    /**
     * 报名时间
     */
    private Date signUpTime;

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
     * 参赛感言
     */

    private String slogan;

    /**
     * 参赛作品
     */
    private String worksImgUrl;

    /**
     * 参赛者openId
     */
    private String openId;

    /**
     * 参赛者头像
     */
    private String workerHeaderImgUrl;


    /**
     * 参赛者昵称
     */
    private String nickName;

    //------------------------统计相关信息----------------//


    /**
     * 累计票数
     */
    private int totalVotes = 0;

    /**
     * 后台加票数量
     */
    private int virtualVotes = 0;

    /**
     * 累计访问
     */
    private int pv = 0;


    /**
     * 最后投票时间
     */
    private Date lastVoteTime;


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

    public Date getSignUpTime() {
        return signUpTime;
    }

    public void setSignUpTime(Date signUpTime) {
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

    public Date getLastVoteTime() {
        return lastVoteTime;
    }

    public void setLastVoteTime(Date lastVoteTime) {
        this.lastVoteTime = lastVoteTime;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getWorkerHeaderImgUrl() {
        return workerHeaderImgUrl;
    }

    public void setWorkerHeaderImgUrl(String workerHeaderImgUrl) {
        this.workerHeaderImgUrl = workerHeaderImgUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
