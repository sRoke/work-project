package net.kingsilk.qh.raffle.domain;


import net.kingsilk.qh.raffle.core.AwardTypeEnum;

public class RaffleAward extends Base{

    /**
     * 抽奖活动
     */
    private String raffleId;

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
     * 中奖提示
     */
    private String prompt;

    /**
     * 奖品中奖概率
     */
    private Double chance;

    /**
     * 奖品类型
     */
    private AwardTypeEnum awardType;

    /**
     * 奖品类型：优惠券
     */
//    private CouponDef couponDef;

    /**
     * 奖品序号
     */
    private int seqNum;

    /**
     * 奖品图片
     */
    private String picture;

    private String raffleAppId;

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


    public String getRaffleId() {
        return raffleId;
    }

    public void setRaffleId(String raffleId) {
        this.raffleId = raffleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getPrompt() {
        return prompt;
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

    public AwardTypeEnum getAwardType() {
        return awardType;
    }

    public void setAwardType(AwardTypeEnum awardType) {
        this.awardType = awardType;
    }

    public int getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(int seqNum) {
        this.seqNum = seqNum;
    }

    public String getraffleAppId() {
        return raffleAppId;
    }

    public void setraffleAppId(String raffleAppId) {
        this.raffleAppId = raffleAppId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
