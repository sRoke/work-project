package net.kingsilk.qh.shop.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 每日统计
 */
@Document
public class DailyStatistics {

    private String brandAppId;

    /**
     * 每日订单交易额
     */
    private Long dailyOrder;  //todo

    /**
     * 每日订单退款额
     */
    private Long dailyOrderRefund;

    /**
     * 每日新注册用户人数
     */
    private Integer dailyMember;

    /**
     * 是否是当然最新数据
     */
    private Boolean isNew;

    /**
     * 统计时间
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

    public Long getDailyOrder() {
        return dailyOrder;
    }

    public void setDailyOrder(Long dailyOrder) {
        this.dailyOrder = dailyOrder;
    }

    public Long getDailyOrderRefund() {
        return dailyOrderRefund;
    }

    public void setDailyOrderRefund(Long dailyOrderRefund) {
        this.dailyOrderRefund = dailyOrderRefund;
    }

    public Integer getDailyMember() {
        return dailyMember;
    }

    public void setDailyMember(Integer dailyMember) {
        this.dailyMember = dailyMember;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
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
