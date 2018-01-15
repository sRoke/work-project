package net.kingsilk.qh.agency.domain;

import net.kingsilk.qh.agency.core.PartnerTypeEnum;

import java.util.Date;

public class SaleRecord extends Base {

    /**
     * 昨天销售总金额
     */
    private Integer num;

    /**
     * 本月销售总金额
     */
    private Integer numMouth;

    /**
     * 对应的渠道商ID
     */
    private String partnerId;

    /**
     * 渠道商类型
     */
    private PartnerTypeEnum partnerTypeEnum;

    /**
     * 昨日排名
     */
    private Integer rankYesterdayReal;

    /**
     * 昨日真实排名
     */
    private Integer rankYesterday;

    /**
     * 昨日真实排名
     */
    private Integer rankYesterdayPercent;

    /**
     * 本月真实排名
     */
    private Integer rankMonthReal;

    /**
     * 本月排名
     */
    private Integer rankMonth;
    /**
     * 本月排名百分比
     */
    private Integer rankMonthPercent;
    /**
     * 销售时间
     */
    private Date saleDate;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;

    }

    public Integer getNumMouth() {
        return numMouth;
    }

    public void setNumMouth(Integer numMouth) {
        this.numMouth = numMouth;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getRankYesterday() {
        return rankYesterday;
    }

    public void setRankYesterday(Integer rankYesterday) {
        this.rankYesterday = rankYesterday;
    }

    public Integer getRankMonth() {
        return rankMonth;
    }

    public void setRankMonth(Integer rankMonth) {
        this.rankMonth = rankMonth;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public PartnerTypeEnum getPartnerTypeEnum() {
        return partnerTypeEnum;
    }

    public void setPartnerTypeEnum(PartnerTypeEnum partnerTypeEnum) {

        this.partnerTypeEnum = partnerTypeEnum;
    }

    public Integer getRankYesterdayReal() {
        return rankYesterdayReal;
    }

    public void setRankYesterdayReal(Integer rankYesterdayReal) {
        this.rankYesterdayReal = rankYesterdayReal;
    }

    public Integer getRankMonthReal() {
        return rankMonthReal;
    }

    public void setRankMonthReal(Integer rankMonthReal) {
        this.rankMonthReal = rankMonthReal;
    }

    public Integer getRankYesterdayPercent() {
        return rankYesterdayPercent;
    }

    public void setRankYesterdayPercent(Integer rankYesterdayPercent) {
        this.rankYesterdayPercent = rankYesterdayPercent;
    }

    public Integer getRankMonthPercent() {
        return rankMonthPercent;
    }

    public void setRankMonthPercent(Integer rankMonthPercent) {
        this.rankMonthPercent = rankMonthPercent;
    }
}

