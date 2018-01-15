package net.kingsilk.qh.agency.api.brandApp.partner.cart.dto;

import java.util.List;

@Deprecated
public class CartListResp {


    private List<CartItemInfo> items;

    public List<CartItemInfo> getItems() {
        return items;
    }

    public void setItems(List<CartItemInfo> items) {
        this.items = items;
    }
}
