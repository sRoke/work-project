package net.kingsilk.qh.activity.domain;

/**
 *
 */
public class VoteRecord extends Base{

    /**
     * 投票活动id
     */
    private String voteActivityId;

    /**
     * 被投票的参赛作品Id
     */
    private String voteWorksId;

    /**
     * 投票人 userId
     */
    private String voterUserId;

    /**
     * 投票人投票IP地址
     */
    private String voterIp;


    /**
     * 投票人姓名
     */
    private String voterNickName;

    /**
     * 投票人手机号码
     */
    private String voterPhone;

    /**
     * 投票人头像
     */
    private String voterHeaderImgUrl;

    public String getVoteActivityId() {
        return voteActivityId;
    }

    public void setVoteActivityId(String voteActivityId) {
        this.voteActivityId = voteActivityId;
    }

    public String getVoteWorksId() {
        return voteWorksId;
    }

    public void setVoteWorksId(String voteWorksId) {
        this.voteWorksId = voteWorksId;
    }

    public String getVoterUserId() {
        return voterUserId;
    }

    public void setVoterUserId(String voterUserId) {
        this.voterUserId = voterUserId;
    }

    public String getVoterIp() {
        return voterIp;
    }

    public void setVoterIp(String voterIp) {
        this.voterIp = voterIp;
    }

    public String getVoterNickName() {
        return voterNickName;
    }

    public void setVoterNickName(String voterNickName) {
        this.voterNickName = voterNickName;
    }

    public String getVoterPhone() {
        return voterPhone;
    }

    public void setVoterPhone(String voterPhone) {
        this.voterPhone = voterPhone;
    }

    public String getVoterHeaderImgUrl() {
        return voterHeaderImgUrl;
    }

    public void setVoterHeaderImgUrl(String voterHeaderImgUrl) {
        this.voterHeaderImgUrl = voterHeaderImgUrl;
    }
}
