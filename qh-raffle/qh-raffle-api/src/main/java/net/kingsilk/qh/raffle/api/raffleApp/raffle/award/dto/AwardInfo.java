package net.kingsilk.qh.raffle.api.raffleApp.raffle.award.dto;

import net.kingsilk.qh.raffle.core.AwardTypeEnum;
import net.kingsilk.qh.raffle.core.DrawTypeEnum;
import net.kingsilk.qh.raffle.core.RecordHandleStatusEnum;

public class AwardInfo {

    /**
     * 奖品名称
     */
    private String name;

//    /**
//     * 几等奖
//     */
//    private String title;

    /**
     * 奖品数量
     */
    private Integer num;

    /**
     * 奖品数量
     */
    private String recordId;

    /**
     * 中奖提示
     */
    private String prompt;

    /**
     * 奖品中奖概率
     */
    private Double chance;

    /**
     * 奖品图片
     */
    private String picture;

    /**
     * 奖品是否领取
     */
    private Boolean accept;

    /**
     * 中奖处理状态
     */
    private RecordHandleStatusEnum handleStatus;

    /**
     * 奖品序号
     */
    private int seqNum;

    /**
     * 奖品类型
     */
    private AwardTypeEnum awardType;

    /**
     * 抽奖活动领取方式
     */
    private DrawTypeEnum drawType;

//    /**
//     * 奖品类型：优惠券
//     */
//    private CouponDef couponDef;

//    /**
//     * 奖品类型
//     */
//    private AwardTypeEnum awardType;

//    /**
//     * 奖品类型：商品
//     */
//    private String goodsName;
//    //    @Deprecated
//    //    Sku sku


//    /**
//     * 奖品图片
//     */
//    private YunFile awardImg;

//    /**
//     * 奖品类型：积分
//     */
//    private Integer integral;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getPrompt() {
        return prompt;
    }

    public Boolean getAccept() {
        return accept;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }

    public RecordHandleStatusEnum getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(RecordHandleStatusEnum handleStatus) {
        this.handleStatus = handleStatus;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public Double getChance() {
        return chance;
    }

    public void setChance(Double chance) {
        this.chance = chance;
    }

//    public AwardTypeEnum getAwardType() {
//        return awardType;
//    }
//
//    public void setAwardType(AwardTypeEnum awardType) {
//        this.awardType = awardType;
//    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public int getSeqNum() {
        return seqNum;
    }

    public DrawTypeEnum getDrawType() {
        return drawType;
    }



    public void setDrawType(DrawTypeEnum drawType) {
        this.drawType = drawType;
    }

    public void setSeqNum(int seqNum) {
        this.seqNum = seqNum;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public AwardTypeEnum getAwardType() {
        return awardType;
    }

    public void setAwardType(AwardTypeEnum awardType) {
        this.awardType = awardType;
    }
}
