package net.kingsilk.qh.agency.api.brandApp.partner.report.dto;


/**
 * Created by lit on 17/7/28.
 */
public class PurchaseResp {
    private Integer monthPpurchaseNum;
    private Integer todayPurchaseNum;


    private Integer monthSaleNum;
    private Integer todaySaleNum;

    private Integer yesterdaySaleNum;

    public Integer getYesterdaySaleNum() {
        return yesterdaySaleNum;
    }

    public void setYesterdaySaleNum(Integer yesterdaySaleNum) {
        this.yesterdaySaleNum = yesterdaySaleNum;
    }

    public Integer getMonthSaleNum() {
        return monthSaleNum;
    }

    public void setMonthSaleNum(Integer monthSaleNum) {
        this.monthSaleNum = monthSaleNum;
    }

    public Integer getTodaySaleNum() {
        return todaySaleNum;
    }

    public void setTodaySaleNum(Integer todaySaleNum) {
        this.todaySaleNum = todaySaleNum;
    }

    public Integer getMonthPpurchaseNum() {
        return monthPpurchaseNum;
    }

    public void setMonthPpurchaseNum(Integer monthPpurchaseNum) {
        this.monthPpurchaseNum = monthPpurchaseNum;
    }

    public Integer getTodayPurchaseNum() {
        return todayPurchaseNum;
    }

    public void setTodayPurchaseNum(Integer todayPurchaseNum) {
        this.todayPurchaseNum = todayPurchaseNum;
    }
}
