package net.kingsilk.qh.vote.api.voteApp.vote.voteWorks.dto;

public class VoteShareResp {


    private String shareTitle;

    private String shareContent;

    private String worksImgUrl;

    private String status;

    private String primaryImgUrl;

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

    public String getWorksImgUrl() {
        return worksImgUrl;
    }

    public void setWorksImgUrl(String worksImgUrl) {
        this.worksImgUrl = worksImgUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrimaryImgUrl() {
        return primaryImgUrl;
    }

    public void setPrimaryImgUrl(String primaryImgUrl) {
        this.primaryImgUrl = primaryImgUrl;
    }
}
