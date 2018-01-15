package net.kingsilk.qh.shop.msg.impl.order.sync;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.core.DeliverStatusEnum;
import net.kingsilk.qh.shop.core.OrderStatusEnum;
import net.kingsilk.qh.shop.domain.DeliverInvoice;
import net.kingsilk.qh.shop.domain.Order;
import net.kingsilk.qh.shop.domain.QDeliverInvoice;
import net.kingsilk.qh.shop.domain.QOrder;
import net.kingsilk.qh.shop.msg.impl.AbstractJobImpl;
import net.kingsilk.qh.shop.repo.DeliverInvoiceRepo;
import net.kingsilk.qh.shop.repo.OrderRepo;
import net.kingsilk.qh.shop.service.QhShopProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.stream.StreamSupport;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Component("orderSyncTrigger")
public class OrderSyncTrigger extends AbstractJobImpl implements Runnable {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private DeliverInvoiceRepo deliverInvoiceRepo;

    @Autowired
    private QhShopProperties qhShopProperties;

    public void orderClose(Order order) {
        DeliverInvoice deliverInvoice = deliverInvoiceRepo.findOne(
                Expressions.allOf(
                        QDeliverInvoice.deliverInvoice.orderId.eq(order.getId()),
                        QDeliverInvoice.deliverInvoice.deleted.ne(true),
                        QDeliverInvoice.deliverInvoice.deliverStatus.eq(DeliverStatusEnum.UNRECEIVED)
                )
        );

        if (deliverInvoice != null) {
            if (deliverInvoice.getDateCreated().before(new Date())) {
                order.setStatus(OrderStatusEnum.FINISHED);
                orderRepo.save(order);
            }
        }
    }


    public String getLockKey() {
        StringBuffer buf = new StringBuffer();
        buf
                .append(qhShopProperties.getMq().getPrefix())
                .append("/").append(Order.class.getName()).append("close");
        return buf.toString();
    }

    // 每 8 小时执行一次
    @Scheduled(fixedRate = 8 * 60 * 60 * 1000)
    @Override
    public void run() {
        String lockKey = getLockKey();
        Long waitLockTime = qhShopProperties.getMq().getDefaultConf().getLockWaitTime();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();

        lockAndExec(lockKey, waitLockTime, () -> {

            // 检索所有
            Iterable<Order> orders = orderRepo.findAll(allOf(
                    QOrder.order.deleted.ne(true),
                    QOrder.order.status.ne(OrderStatusEnum.FINISHED),
                    QOrder.order.status.ne(OrderStatusEnum.CLOSED),
                    QOrder.order.dateCreated.after(m)
            ));

            StreamSupport.stream(orders.spliterator(), false)
                    .forEach(this::orderClose);

        });

    }
}
