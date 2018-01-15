package net.kingsilk.qh.activity.domain;

import net.kingsilk.qh.activity.core.vote.BargainReceiveTypeEnum;
import net.kingsilk.qh.activity.core.vote.BargainStatusEnum;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class BargainActivity extends Base {


    //brandAppId
    private String brandAppId;
    /**
     * 活动名称
     */
    private String name;

    //自定义编码
    private String seq;

    //活动开始时间
    private Date beginTime;

    //活动结束时间
    private Date endTime;

    //活动规则说明
    private String rule;

    //活动图文
    private String desp;

    //参与人数
    private Integer participantNum;

    // 强制关注
    private Boolean mustFollow;


    //分享标题
    private String shareTitle;

    //分享描述
    private String shareDesp;

    //分享图片
    private String shareImg;

    //活动状态
    private BargainStatusEnum status = BargainStatusEnum.EDITING;

    /**
     * 兑换方式
     */
    private BargainReceiveTypeEnum receiveType = BargainReceiveTypeEnum.LINEBUY;

    /**
     * 活动标题图片
     */
    private String titleImg;

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public Integer getParticipantNum() {
        return participantNum;
    }

    public void setParticipantNum(Integer participantNum) {
        this.participantNum = participantNum;
    }

    public Boolean getMustFollow() {
        return mustFollow;
    }

    public void setMustFollow(Boolean mustFollow) {
        this.mustFollow = mustFollow;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareDesp() {
        return shareDesp;
    }

    public void setShareDesp(String shareDesp) {
        this.shareDesp = shareDesp;
    }

    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
    }

    public BargainStatusEnum getStatus() {
        return status;
    }

    public void setStatus(BargainStatusEnum status) {
        this.status = status;
    }

    public BargainReceiveTypeEnum getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(BargainReceiveTypeEnum receiveType) {
        this.receiveType = receiveType;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }
}
