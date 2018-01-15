package net.kingsilk.qh.agency.wap.api.order.dto;

/**
 * Created by zcw on 3/16/17.
 */
@Deprecated
public class OrderCheckResp  {
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    private String orderId;
}
