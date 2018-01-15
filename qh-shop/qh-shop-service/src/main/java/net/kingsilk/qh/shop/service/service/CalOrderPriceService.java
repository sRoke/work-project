package net.kingsilk.qh.shop.service.service;

import net.kingsilk.qh.shop.api.brandApp.shop.mall.order.dto.OrderCheckReq;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.order.dto.OrderCommitReq;
import net.kingsilk.qh.shop.domain.Order;
import net.kingsilk.qh.shop.domain.Sku;
import net.kingsilk.qh.shop.repo.SkuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service                //todo 还有 ×100 的问题
public class CalOrderPriceService {
    /**
     * 第二期不做优惠活动
     * 都按原价处理
     */

    @Autowired
    private SkuRepo skuRepo;

    /**
     * 原价
     * @param order
     */

    //生成订单
    public void  calCheckOrigPrice(Order order,List<OrderCheckReq> req){

        if (req == null || req.size() == 0){
            return;
        }
        order.setTotalPrice(0);
        for (OrderCheckReq orderReq : req){
            Sku sku = skuRepo.findOne(orderReq.getSkuId());
            //order普通成员变量的赋值
//            Integer totalPrice = order.getTotalPrice();
//            int i = sku.getSalePrice() + orderReq.getNum();
//            totalPrice = totalPrice + i;
//            order.setTotalPrice(totalPrice);
            order.setTotalPrice(order.getTotalPrice() + (sku.getSalePrice() * orderReq.getNum()));
        }
    }

    public void  calCommitOrigPrice(Order order){

        if (order == null || order.getId() == null){
            return;
        }
        order.setTotalPrice(0);
        List<Order.OrderItem> orderItems = order.getOrderItems();
        orderItems.forEach(it ->{
            Sku sku = skuRepo.findOne(it.getSkuId());
            //order普通成员变量的赋值
            order.setTotalPrice(order.getTotalPrice() + sku.getSalePrice() * Integer.parseInt(it.getNum()));
        });
    }

    /**
     * 原价 赋值order 中List<OrderItem> 每个商品的价格 sku * 单价
     * @param orderItem
     */
    public void  calSkuInOrderOrigPrice(Order.OrderItem orderItem, OrderCheckReq req){
        Sku sku = skuRepo.findOne(req.getSkuId());
        Assert.notNull(sku ,"找不到 " + req.getSkuId() + " 对应的sku");
        orderItem.setSkuPrice(sku.getSalePrice() + "");
        orderItem.setOrderPrice(sku.getSalePrice() * req.getNum() + "");
        orderItem.setRealPayPrice(sku.getSalePrice()+ "");
        orderItem.setAllRealPayPrice(sku.getSalePrice() * req.getNum() + "");
    }

    public void  calSkuInOrderOrigPrice(Order.OrderItem orderItem){
        Sku sku = skuRepo.findOne(orderItem.getSkuId());
        Assert.notNull(sku ,"找不到 " + orderItem.getSkuId() + " 对应的sku");
        orderItem.setSkuPrice(sku.getSalePrice() + "");
        orderItem.setOrderPrice(sku.getSalePrice() * Integer.parseInt(orderItem.getNum()) + "");
        orderItem.setRealPayPrice(sku.getSalePrice() * Integer.parseInt(orderItem.getNum()) + "");
    }
}
