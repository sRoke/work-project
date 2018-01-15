package net.kingsilk.qh.shop.service.bak;

import org.springframework.stereotype.Service;

/**
 * Created by yanfq on 17-4-6.
 */
@Service
//@CompileStatic
class OrderService {

//    @Autowired
//    OrderRepo orderRepo;
//
//    @Autowired
//    SkuRepo skuRepo;
//
//    @Autowired
//    MongoTemplate mongoTemplate;
//
//    @Autowired
//    QhPayRepo qhPayRepo;
//
//    @Autowired
//    PALogService paLogService;
//
//    @Autowired
//    PartnerAccountRepo partnerAccountRepo;
//
//
//    Map<String, Integer> getDataCountMap(String countId) {
//        GroupBy groupBy = GroupBy.key("status").initialDocument("{count:0}")
//                .reduceFunction("function(key, values){values.count+=1;}");
//        Criteria criteria = new Criteria();
//        criteria.and("deleted").in([false, null]);
//        criteria.and("sellerPartnerId").in(countId);
//        GroupByResults<Order> r = mongoTemplate.group(criteria, "order", groupBy, Order.class);
//        BasicDBList list = (BasicDBList) r.getRawResults().get("retval");
//        Map<String, Integer> countMap = new HashMap<>();
//        int allCount = 0;
//        for (int i = 0; i < list.size(); i++) {
//            BasicDBObject obj = (BasicDBObject) list.get(i);
//            String key = obj.get("status") + "Count";
//            Integer value = (Integer)obj.get("count");
//            countMap.put(key, value);
//            allCount += value
//        }
//        countMap.put("allCount", allCount);
//        return countMap;
//    }
//
//    /**
//     * 支付成功处理
//     * @param order
//     */
//    void paySuccessHandle(Order order, String payType, Date payTime) {
//        if (order.status != OrderStatusEnum.UNPAYED) {
//            println("订单状态错误");
//            return;
//        }
//
//        ////减库存，暂时只考虑库存足够的情况，不够也可购买
//        order.orderItems.forEach(
//                Order.OrderItem it ->{
//
//                }
//        );
////        { Order.OrderItem it ->
//////            it.sku.storage -= it.num;
//////            it.sku.storage = it.sku.storage > 0 ? it.sku.storage : 0
//////            skuRepo.save(it.sku)
////        }
////        order.payType=payType
////        order.payTime=
//        QhPay qhPay = qhPayRepo.findByOrder(order);
//        qhPay.payType = payType;
//        qhPay.payTime = payTime;
//        order.status = OrderStatusEnum.UNCONFIRMED;       //支付成功，直接到待发货
//        orderRepo.save(order);
//
//        //账户日志表进行保存
//        PartnerAccount partnerAccount = partnerAccountRepo.findOne(
//                QPartnerAccount.
//                        partnerAccount.
//                        partner.id.eq(order.getBuyerPartnerId())
//        );
//        paLogService.pALogConvert(partnerAccount, order);
//    }
//
//    /**
//     * 退款成功处理
//     */
//    void refundHandle() {
//        //TODO
//    }


}
