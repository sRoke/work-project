package net.kingsilk.qh.shop.domain.bak;

import net.kingsilk.qh.shop.core.PayOperateEnum;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by lit on 17/8/22.
 */
@Document
public class PayLog {


    /**
     * 关联的品牌商Id
     */
    private String brandAppId;

    /**
     * 关联的订单
     */
    @DBRef
    private Order order;

    /**
     * 进行的操作
     */
    private PayOperateEnum operate;

    /**
     * 关联的支付记录
     */
    @DBRef
    private QhPay qhPay;

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PayOperateEnum getOperate() {
        return operate;
    }

    public void setOperate(PayOperateEnum operate) {
        this.operate = operate;
    }

    public QhPay getQhPay() {
        return qhPay;
    }

    public void setQhPay(QhPay qhPay) {
        this.qhPay = qhPay;
    }
}
