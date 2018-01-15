package net.kingsilk.qh.agency.api.brandApp.partner.saleRecord.dto;

import net.kingsilk.qh.agency.core.PartnerTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class SaleRecordResp {

    /**
     * 销售总金额
     */
    private Integer num;

    /**
     * 对应的渠道商ID
     */
    private String partnerId;

    /**
     * 渠道商类型
     */
    private String partnerTypeEnum;

    /**
     * 渠道商排名
     */
    private Integer placeNum;

    /**
     * 昨日排名
     */
    private Integer rankYesterday;

    /**
     * 本月排名百分比
     */
    private Integer rankMonthPercent;

    /**
     * 本月排名
     */
    private Integer rankMonth;

    private List<SaleRecordEach> saleRecordeList = new ArrayList<>();

    /**
     * 销售时间
     */
    private String saleDate;

    public class SaleRecordEach{

        public SaleRecordEach(Integer sale,Integer day){
            this.day = day;
            this.sale = sale;
        }

        /**
         * 销售额
         */
        private Integer sale;

        /**
         * 日期
         */
        private Integer day;

        public Integer getSale() {
            return sale;
        }

        public void setSale(Integer sale) {
            this.sale = sale;
        }

        public Integer getDay() {
            return day;
        }

        public void setDay(Integer day) {
            this.day = day;
        }
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getPartnerTypeEnum() {
        return partnerTypeEnum;
    }

    public void setPartnerTypeEnum(String partnerTypeEnum) {
        this.partnerTypeEnum = partnerTypeEnum;
    }

    public Integer getRankMonthPercent() {
        return rankMonthPercent;
    }

    public void setRankMonthPercent(Integer rankMonthPercent) {
        this.rankMonthPercent = rankMonthPercent;
    }

    public Integer getPlaceNum() {
        return placeNum;
    }

    public void setPlaceNum(Integer placeNum) {
        this.placeNum = placeNum;
    }

    public List<SaleRecordEach> getSaleRecordeList() {
        return saleRecordeList;
    }

    public void setSaleRecordeList(List<SaleRecordEach> saleRecordeList) {
        this.saleRecordeList = saleRecordeList;
    }
}
