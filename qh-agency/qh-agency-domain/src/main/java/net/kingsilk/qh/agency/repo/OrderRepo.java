package net.kingsilk.qh.agency.repo;

import net.kingsilk.qh.agency.domain.PartnerStaff;
import net.kingsilk.qh.agency.domain.Order;

/**
 * 保留该接口，方法统一追加自定义方法
 */
public interface OrderRepo extends BaseRepo<Order, String> {
    Order findOneByPartnerStaffAndId(PartnerStaff partnerStaff, String id);
}
