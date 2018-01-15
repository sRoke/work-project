package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.order.convert;


import net.kingsilk.qh.shop.api.brandApp.shop.mall.order.dto.OrderCheckReq;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.order.dto.OrderCommitReq;
import net.kingsilk.qh.shop.core.OrderSourceTypeEnum;
import net.kingsilk.qh.shop.domain.Order;
import net.kingsilk.qh.shop.repo.SkuRepo;
import net.kingsilk.qh.shop.service.service.CalOrderPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderCreateConvert {

    @Autowired
    private SkuRepo skuRepo;

    @Autowired
    private CalOrderPriceService calOrderPriceService;

    public Order checkReqConvert(Order order, List<OrderCheckReq> req) {
        if (req == null) {
            return null;
        }
        //所以商品的总价(原件)
        calOrderPriceService.calCheckOrigPrice(order,req);

        //内部类，每个商品的价格信息(原价)
        List<Order.OrderItem> orderItems = order.getOrderItems();
        req.forEach(it -> {
            Order.OrderItem orderItem = order.new OrderItem();
            orderItem.setSkuId(it.getSkuId());
            orderItem.setNum(it.getNum() + "");
            orderItem.setAdjustedAmount(0+"");
            order.getOrderItems().add(orderItem);
            calOrderPriceService.calSkuInOrderOrigPrice(orderItem, it);
        });
        return order;
    }


    public Order commitReqConvert(Order order, OrderCommitReq req) {
        if (req == null) {
            return null;
        }
//        order.setBrandAppId(req.getBrandAppId());
//        order.setShopId(req.getShopId());
//        order.setSeq(req.getSeq());
        order.setBuyerMemo(req.getBuyerMemo());
        order.setInvoiceTitle(req.getInvoiceTitle());
        order.setNeedInvoice(req.getNeedInvoice());
//        order.setSellerMemo(req.getSellerMemo());
        order.setSourceType(OrderSourceTypeEnum.valueOf(req.getSourceType()));

        //所以商品的总价(原件)
//        calOrderPriceService.calCommitOrigPrice(order);

        //内部类，每个商品的价格信息(原价)
//        List<Order.OrderItem> orderItems = order.getOrderItems();
//        orderItems.forEach(it -> {
//            Order.OrderItem orderItem = order.new OrderItem();
//            orderItem.setSkuId(it.getSkuId());
//            orderItem.setNum(it.getNum() + "");
//            orderItem.setNo(it.getNo());
//            order.getOrderItems().add(orderItem);
//            calOrderPriceService.calSkuInOrderOrigPrice(orderItem);
//        });
        return order;
    }


}
