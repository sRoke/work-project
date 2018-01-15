package net.kingsilk.qh.agency.repo;

import net.kingsilk.qh.agency.domain.Order;
import net.kingsilk.qh.agency.domain.Refund;

/**
 * 保留该接口，方法统一追加自定义方法
 */
public interface RefundRepo extends BaseRepo<Refund, String> {
    Refund findByOrder(Order order);
}
