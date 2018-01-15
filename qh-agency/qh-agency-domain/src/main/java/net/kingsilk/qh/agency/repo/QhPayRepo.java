package net.kingsilk.qh.agency.repo;

import net.kingsilk.qh.agency.domain.Order;
import net.kingsilk.qh.agency.domain.QhPay;

/**
 * 保留该接口，方法统一追加自定义方法
 */
public interface QhPayRepo extends BaseRepo<QhPay, String> {
    QhPay findByOrder(Order order);
    QhPay findByOrderAndPayType(Order order,String payType);
}
