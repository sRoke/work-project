package net.kingsilk.qh.agency.repo;

import net.kingsilk.qh.agency.domain.DeliverInvoice;
import net.kingsilk.qh.agency.domain.Order;

/**
 * Created by lit on 17/7/25.
 */
public interface DeliverInvoiceRepo extends BaseRepo<DeliverInvoice, String> {
    DeliverInvoice findByOrder(Order order);
}
