package net.kingsilk.qh.shop.domain.bak;

import net.kingsilk.qh.shop.core.OrderOperateEnum;
import net.kingsilk.qh.shop.core.OrderStatusEnum;
import net.kingsilk.qh.shop.domain.Base;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by zcw on 3/13/17.
 * 订单日志
 */
@Document
public class OrderLog extends Base {

    /**
     * 关联的订单ID
     */
    private String orderId;

    /**
     * 进行的操作
     */
    private OrderOperateEnum operate;

    /**
     * 订单进行该操作后的状态
     */
    private OrderStatusEnum status;

    /**
     * 进行改价、支付、退款的金额，根据operate来区分
     */
    private Integer price = 0;

    /**
     * 备注
     */
    private String memo;


    public OrderLog(String orderId, OrderOperateEnum operate, OrderStatusEnum status, Integer price, String memo) {
        this.orderId = orderId;
        this.operate = operate;
        this.status = status;
        this.price = price;
        this.memo = memo;
    }


    // --------------------------------------- getter && setter

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderOperateEnum getOperate() {
        return operate;
    }

    public void setOperate(OrderOperateEnum operate) {
        this.operate = operate;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
