package net.kingsilk.qh.shop.api.brandApp.shop.repertorySku.dto;

import net.kingsilk.qh.shop.api.UniPageResp;

public class RepertoryItemPageRep {

    //TODO 统计什么？
    private Integer total;

    private Integer totalSku;

    private Integer totalPrice;

    /**
     *  商品分页
     */
    private UniPageResp<ItemRep> uniPageResp;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalSku() {
        return totalSku;
    }

    public void setTotalSku(Integer totalSku) {
        this.totalSku = totalSku;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public UniPageResp<ItemRep> getUniPageResp() {
        return uniPageResp;
    }

    public void setUniPageResp(UniPageResp<ItemRep> uniPageResp) {
        this.uniPageResp = uniPageResp;
    }
}
