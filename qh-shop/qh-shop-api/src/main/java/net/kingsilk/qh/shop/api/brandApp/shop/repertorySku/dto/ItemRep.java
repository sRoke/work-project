package net.kingsilk.qh.shop.api.brandApp.shop.repertorySku.dto;

public class ItemRep{

    private String itemId;

    private Integer sum;

    //TODO sku价格？
    private Integer price;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}