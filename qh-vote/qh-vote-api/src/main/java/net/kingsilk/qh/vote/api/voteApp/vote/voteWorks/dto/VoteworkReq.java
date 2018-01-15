package net.kingsilk.qh.vote.api.voteApp.vote.voteWorks.dto;

public class VoteworkReq {

    /**
     * 姓名
     */
//    @QueryParam("name")
    private String name;


    /***
     * 手机号
     */
//    @QueryParam("phone")
    private String phone;

    /***
     * 参赛作品
     */
//    @QueryParam("worksImgUrl")
    private String worksImgUrl;

    /**
     * 参赛宣言
     */
//    @QueryParam("slogan")
    private String slogan;

    private String voteActivityId;


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
     * openId
     */
    private String openId;

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

    public String getVoteActivityId() {
        return voteActivityId;
    }

    public void setVoteActivityId(String voteActivityId) {
        this.voteActivityId = voteActivityId;
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

    public String getWorksImgUrl() {
        return worksImgUrl;
    }

    public void setWorksImgUrl(String worksImgUrl) {
        this.worksImgUrl = worksImgUrl;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
