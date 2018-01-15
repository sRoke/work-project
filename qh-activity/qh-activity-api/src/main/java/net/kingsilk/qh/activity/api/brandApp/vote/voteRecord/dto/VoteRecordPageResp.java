package net.kingsilk.qh.activity.api.brandApp.vote.voteRecord.dto;

public class VoteRecordPageResp {

    /**
     * 投票人
     */
    private String voteName;

    /**
     * 投票人电话
     */
    private String votePhoe;


    /**
     * 作品人
     */
    private String workName;

    /**
     * 作品人电话
     */
    private String workPhone;


    /**
     * 投票时间
     */
    private String voteDate;


    /***
     * 投票人的头像
     */
    private String voterHeaderImgUrl;

    public String getVoteName() {
        return voteName;
    }

    public void setVoteName(String voteName) {
        this.voteName = voteName;
    }

    public String getVotePhoe() {
        return votePhoe;
    }

    public void setVotePhoe(String votePhoe) {
        this.votePhoe = votePhoe;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(String voteDate) {
        this.voteDate = voteDate;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getVoterHeaderImgUrl() {
        return voterHeaderImgUrl;
    }

    public void setVoterHeaderImgUrl(String voterHeaderImgUrl) {
        this.voterHeaderImgUrl = voterHeaderImgUrl;
    }
}

