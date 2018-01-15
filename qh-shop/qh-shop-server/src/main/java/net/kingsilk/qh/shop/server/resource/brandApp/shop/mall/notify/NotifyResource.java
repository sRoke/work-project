package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.notify;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.notify.NotifyApi;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.notify.dto.NotifyQhPayReq;
import net.kingsilk.qh.shop.core.PayTypeEnum;
import net.kingsilk.qh.shop.domain.Order;
import net.kingsilk.qh.shop.domain.QOrder;
import net.kingsilk.qh.shop.domain.QRefund;
import net.kingsilk.qh.shop.domain.Refund;
import net.kingsilk.qh.shop.repo.OrderRepo;
import net.kingsilk.qh.shop.repo.RefundRepo;
import net.kingsilk.qh.shop.service.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class NotifyResource implements NotifyApi {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private RefundRepo refundRepo;

    @Autowired
    private OrderService orderService;

    @Override
    public boolean qhShop(String brandAppId, String shopId, NotifyQhPayReq req) {
        Order order = orderRepo.findOne(
                Expressions.allOf(
                        QOrder.order.brandAppId.eq(brandAppId),
                        QOrder.order.id.eq(req.getBizOrderNo()),
                        QOrder.order.deleted.ne(true)
                )
        );
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        if (!StringUtils.isBlank(req.getPayTime())) {
            try {
                date = format.parse(req.getPayTime());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        if (order == null) {
            throw new ErrStatusException(ErrStatus.FINDNULL, "找不到订单");
        }
        if ("PAY".equals(req.getType())) {
            orderService.paySuccessHandle(order, PayTypeEnum.valueOf(req.getPayType()), date);
        } else if ("REFUND".equals(req.getType())) {

            Refund refund = refundRepo.findOne(
                    Expressions.allOf(
                            QRefund.refund.brandAppId.eq(brandAppId),
                            QRefund.refund.orderId.eq(order.getId()),
                            QRefund.refund.deleted.ne(true)
                    )
            );

            orderService.refundHandle(refund, order);
        }
        return true;
    }

}
