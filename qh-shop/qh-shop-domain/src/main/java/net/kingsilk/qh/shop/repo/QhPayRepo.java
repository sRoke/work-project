package net.kingsilk.qh.shop.repo;

import net.kingsilk.qh.shop.domain.QhPay;

public interface QhPayRepo extends BaseRepo<QhPay, String> {

    QhPay findByOrderIdAndPayType(String orderId, String payType);
}
