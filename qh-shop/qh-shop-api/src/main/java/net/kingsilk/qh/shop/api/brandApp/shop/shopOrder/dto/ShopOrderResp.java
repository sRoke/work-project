package net.kingsilk.qh.shop.api.brandApp.shop.shopOrder.dto;

import java.util.Date;

public class ShopOrderResp {

    /**
     * 订单状态
     */
    private String status;

    /**
     * 订单原价总和
     */
    private Integer totalPrice;

    /**
     * 购买时间
     */
    private Date buyDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }
}
