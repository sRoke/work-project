package net.kingsilk.qh.vote.api.voteApp.vote.voteRecord.dto;

public class RecordInfoResp {

    /**
     * 是否是本人
     */
    private Boolean isSelf;


    /**
     * 已经投了多少票
     */
    private String voteNum;


    /**
     * 今日还可再投
     */
    private String residueNum;

    /**
     * 今日已投多少人
     */
    private String votePeopleToday;


    /***
     * 作品状态
     */
    private String status;

    public Boolean getSelf() {
        return isSelf;
    }

    public void setSelf(Boolean self) {
        isSelf = self;
    }

    public String getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(String voteNum) {
        this.voteNum = voteNum;
    }

    public String getResidueNum() {
        return residueNum;
    }

    public void setResidueNum(String residueNum) {
        this.residueNum = residueNum;
    }

    public String getVotePeopleToday() {
        return votePeopleToday;
    }

    public void setVotePeopleToday(String votePeopleToday) {
        this.votePeopleToday = votePeopleToday;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
