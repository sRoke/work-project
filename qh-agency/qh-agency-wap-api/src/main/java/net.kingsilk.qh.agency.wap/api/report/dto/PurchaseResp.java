package net.kingsilk.qh.agency.wap.api.report.dto;

import java.math.BigDecimal;

/**
 * Created by lit on 17/7/28.
 */
public class PurchaseResp {
    private BigDecimal monthPpurchaseNum;
    private Integer todayPurchaseNum;

    public BigDecimal getMonthPpurchaseNum() {
        return monthPpurchaseNum;
    }

    public void setMonthPpurchaseNum(BigDecimal monthPpurchaseNum) {
        this.monthPpurchaseNum = monthPpurchaseNum;
    }

    public Integer getTodayPurchaseNum() {
        return todayPurchaseNum;
    }

    public void setTodayPurchaseNum(Integer todayPurchaseNum) {
        this.todayPurchaseNum = todayPurchaseNum;
    }
}
