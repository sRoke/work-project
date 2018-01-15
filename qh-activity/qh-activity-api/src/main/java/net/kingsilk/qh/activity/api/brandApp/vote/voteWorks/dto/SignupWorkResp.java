package net.kingsilk.qh.activity.api.brandApp.vote.voteWorks.dto;

public class SignupWorkResp {

    /**
     * wx用户openid
     */
    private String openId;

    /**
     * 第三方平台appId
     */
    private String wxComAppId;

    /**
     * 关联的微信公众号
     */
    private String wxMpAppId;

    /**
     * 头像
     */
    private String wxheadImg;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 作品id
     */
    private String workId;

    /**
     * 是否需要重新登录
     */
    private Boolean logOut;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getWxComAppId() {
        return wxComAppId;
    }

    public void setWxComAppId(String wxComAppId) {
        this.wxComAppId = wxComAppId;
    }

    public String getWxMpAppId() {
        return wxMpAppId;
    }

    public void setWxMpAppId(String wxMpAppId) {
        this.wxMpAppId = wxMpAppId;
    }

    public String getWxheadImg() {
        return wxheadImg;
    }

    public void setWxheadImg(String wxheadImg) {
        this.wxheadImg = wxheadImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public Boolean getLogOut() {
        return logOut;
    }

    public void setLogOut(Boolean logOut) {
        this.logOut = logOut;
    }
}
