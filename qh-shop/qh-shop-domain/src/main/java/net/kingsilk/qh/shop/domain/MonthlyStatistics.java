package net.kingsilk.qh.shop.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 每月统计
 */
@Document
public class MonthlyStatistics extends Base {

    private String brandAppId;

    /**
     * 是否是最新数据
     */
    private Boolean isNew;

    /**
     * 每月订单交易额
     */
    private Long monthOrder;

    /**
     * 每月订单退款额
     */
    private Long monthOrderRefund;

    /**
     * 当月新注册用户数
     */
    private Integer monthMember;

    /**
     * 统计的时间
     */
    private Date statisticsTime;

    /**
     * 门店ID
     */
    private String shopId;


    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public Long getMonthOrder() {
        return monthOrder;
    }

    public void setMonthOrder(Long monthOrder) {
        this.monthOrder = monthOrder;
    }

    public Long getMonthOrderRefund() {
        return monthOrderRefund;
    }

    public void setMonthOrderRefund(Long monthOrderRefund) {
        this.monthOrderRefund = monthOrderRefund;
    }

    public Integer getMonthMember() {
        return monthMember;
    }

    public void setMonthMember(Integer monthMember) {
        this.monthMember = monthMember;
    }

    public Date getStatisticsTime() {
        return statisticsTime;
    }

    public void setStatisticsTime(Date statisticsTime) {
        this.statisticsTime = statisticsTime;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
