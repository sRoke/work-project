package net.kingsilk.qh.vote.api.voteApp.vote.voteRecord.dto;


/**
 * 投票人返回信息
 */
public class VoteRecordResp {


    private String userId;

    private String name;

    private String voteNum;

    private String wxHeaderImg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(String voteNum) {
        this.voteNum = voteNum;
    }

    public String getWxHeaderImg() {
        return wxHeaderImg;
    }

    public void setWxHeaderImg(String wxHeaderImg) {
        this.wxHeaderImg = wxHeaderImg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
