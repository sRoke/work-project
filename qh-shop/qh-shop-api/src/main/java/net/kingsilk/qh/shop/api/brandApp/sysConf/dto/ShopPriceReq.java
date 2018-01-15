package net.kingsilk.qh.shop.api.brandApp.sysConf.dto;

import javax.ws.rs.QueryParam;

/**
 * Created by lit on 17/12/4.
 */
public class ShopPriceReq {

    @QueryParam(value = "mOldPrice")
    private Integer mOldPrice;
    @QueryParam(value = "mSallPrice")
    private Integer mSallPrice;
    @QueryParam(value = "mDay")
    private Integer mDay;
//    @QueryParam(value = "tOldPrice")
//    private Integer tOldPrice;
//    @QueryParam(value = "tSallPrice")
//    private Integer tSallPrice;
//    @QueryParam(value = "tDay")
//    private Integer tDay;
//    @QueryParam(value = "ySallPrice")
//    private Integer yOldPrice;
//    @QueryParam(value = "ySallPrice")
//    private Integer ySallPrice;
//    @QueryParam(value = "free")
//    private Integer yDay;
    @QueryParam(value = "free")
    private Integer free;

    public Integer getmOldPrice() {
        return mOldPrice;
    }

    public void setmOldPrice(Integer mOldPrice) {
        this.mOldPrice = mOldPrice;
    }

    public Integer getmSallPrice() {
        return mSallPrice;
    }

    public void setmSallPrice(Integer mSallPrice) {
        this.mSallPrice = mSallPrice;
    }

    public Integer getmDay() {
        return mDay;
    }

    public void setmDay(Integer mDay) {
        this.mDay = mDay;
    }

//    public Integer gettOldPrice() {
//        return tOldPrice;
//    }
//
//    public void settOldPrice(Integer tOldPrice) {
//        this.tOldPrice = tOldPrice;
//    }
//
//    public Integer gettSallPrice() {
//        return tSallPrice;
//    }
//
//    public void settSallPrice(Integer tSallPrice) {
//        this.tSallPrice = tSallPrice;
//    }
//
//    public Integer gettDay() {
//        return tDay;
//    }
//
//    public void settDay(Integer tDay) {
//        this.tDay = tDay;
//    }
//
//    public Integer getyOldPrice() {
//        return yOldPrice;
//    }
//
//    public void setyOldPrice(Integer yOldPrice) {
//        this.yOldPrice = yOldPrice;
//    }
//
//    public Integer getySallPrice() {
//        return ySallPrice;
//    }
//
//    public void setySallPrice(Integer ySallPrice) {
//        this.ySallPrice = ySallPrice;
//    }
//
//    public Integer getyDay() {
//        return yDay;
//    }
//
//    public void setyDay(Integer yDay) {
//        this.yDay = yDay;
//    }

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }
}
