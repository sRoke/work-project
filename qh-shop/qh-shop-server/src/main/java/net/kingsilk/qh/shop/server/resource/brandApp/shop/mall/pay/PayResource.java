package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.pay;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.pay.PayApi;
import net.kingsilk.qh.shop.domain.Order;
import net.kingsilk.qh.shop.domain.QOrder;
import net.kingsilk.qh.shop.repo.OrderRepo;
import net.kingsilk.qh.shop.service.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

@Component
public class PayResource implements PayApi {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderRepo orderRepo;

    @Context
    HttpServletRequest request;

    @Override
    public UniResp<String> pay(String brandAppId, String shopId, String orderId, String memo) {
        Order order = orderRepo.findOne(
                Expressions.allOf(
                        QOrder.order.brandAppId.eq(brandAppId),
                        QOrder.order.deleted.ne(true),
                        QOrder.order.id.eq(orderId)
                )
        );
        if (order == null) {
            throw new ErrStatusException(ErrStatus.FINDNULL, "没有找到订单");
        }
        return payService.pay(order, request);
    }
}
