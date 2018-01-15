package net.kingsilk.qh.agency.service

import com.mongodb.BasicDBList
import com.mongodb.BasicDBObject
import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.api.brandApp.notify.dto.NotifyShopInfoReq
import net.kingsilk.qh.agency.core.OrderStatusEnum
import net.kingsilk.qh.agency.core.SkuStoreChangeEnum
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.msg.EventPublisher
import net.kingsilk.qh.agency.msg.api.search.esSkuStore.sync.SyncEvent
import net.kingsilk.qh.agency.repo.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapreduce.GroupBy
import org.springframework.data.mongodb.core.mapreduce.GroupByResults
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Service

/**
 * Created by yanfq on 17-4-6.
 */
@Service
//@CompileStatic
class OrderService {

    @Autowired
    OrderRepo orderRepo

    @Autowired
    SkuRepo skuRepo

    @Autowired
    SkuStoreRepo skuStoreRepo

    @Autowired
    MongoTemplate mongoTemplate

    @Autowired
    QhPayRepo qhPayRepo

    @Autowired
    PALogService paLogService

    @Autowired
    PartnerAccountRepo partnerAccountRepo

    @Autowired
    PartnerStaffService partnerStaffService

    @Autowired
    SkuStoreLogRepo skuStoreLogRepo

    @Autowired
    EventPublisher eventPublisher

    @Autowired
    CalOrderPriceService calOrderPriceService

    Map<String, Integer> getDataCountMap(String countId) {
        GroupBy groupBy = GroupBy.key("status").initialDocument("{count:0}")
                .reduceFunction("function(key, values){values.count+=1;}");
        Criteria criteria = new Criteria();
        criteria.and("deleted").in([false, null]);
        criteria.and("sellerPartnerId").in(countId);
        GroupByResults<Order> r = mongoTemplate.group(criteria, "order", groupBy, Order.class);
        BasicDBList list = (BasicDBList) r.getRawResults().get("retval");
        Map<String, Integer> countMap = new HashMap<String, Long>();
        int allCount = 0;
        for (int i = 0; i < list.size(); i++) {
            BasicDBObject obj = (BasicDBObject) list.get(i);
            String key = obj.get("status") + "Count";
            Integer value = obj.get("count");
            countMap.put(key, value);
            allCount += value
        }
        countMap.put("allCount", allCount);
        return countMap;
    }

    /**
     * 支付成功处理
     * @param order
     */
    void paySuccessHandle(Order order, String payType, Date payTime) {
        if (order.status != OrderStatusEnum.UNPAYED) {
            println("订单状态错误")
            return;
        }

        ////减库存，暂时只考虑库存足够的情况，不够也可购买
//        order.orderItems.each { Order.OrderItem it ->
//            it.sku.storage -= it.num;
//            it.sku.storage = it.sku.storage > 0 ? it.sku.storage : 0
//            skuRepo.save(it.sku)
//        }
//        order.payType=payType
//        order.payTime=
        QhPay qhPay = new QhPay()
        qhPay.order = order
        qhPay.thirdPayAmount = order.paymentPrice
        qhPay.brandAppId = order.brandAppId
        qhPay.balanceAmount = order.balancePrice
        qhPay.refundAmount = order.orderPrice
        qhPay.payType = payType
        qhPay.payTime = payTime
        order.status = OrderStatusEnum.UNCONFIRMED        //支付成功，直接到待发货
        qhPayRepo.save(qhPay)
        orderRepo.save(order)
//        calOrderPriceService.calParnterAccount(order,partnerAccountRepo.findByPartner(orderRepo.findOne(order.buyerPartnerId)))
        //账户日志表进行保存
        PartnerAccount partnerAccount = partnerAccountRepo.findOne(
                QPartnerAccount.
                        partnerAccount.
                        partner.id.eq(order.getBuyerPartnerId())
        )
        paLogService.pALogConvert(partnerAccount, order, payType)
    }

    /**
     * 退款成功处理
     */
    void refundHandle() {

    }

    /***
     * qhSHOP操作
     */
    void payShopSuccessHandle(NotifyShopInfoReq req) {

        if (!req.getShopInfo().get("orderType").equals("FINISHED")) {
            println("订单状态错误")
            return;
        }
        for (String key : req.getSkuIds().keySet()) {
            SkuStore skuStore = skuStoreRepo.findOne(
                    Expressions.allOf(
                            QSkuStore.skuStore.deleted.in(false),
                            QSkuStore.skuStore.partner.id.eq(req.getShopInfo().get("partnerId")),
                            QSkuStore.skuStore.sku.id.eq(key)
                    )
            )
            skuStore.num = skuStore.num - req.getSkuIds().get(key)
            skuStore.salesVolume = skuStore.salesVolume + req.getSkuIds().get(key)
            skuStoreRepo.save(skuStore)

            SyncEvent syncEvent = new SyncEvent()
            syncEvent.skuStoreId = skuStore.id
            eventPublisher.publish(syncEvent);

            SkuStoreLog skuStoreLog = new SkuStoreLog(
                    skuStore.getBrandAppId(),
                    req.getShopInfo().get("partnerId"),
                    skuStore.id,
                    skuStore.num,
                    SkuStoreChangeEnum.SELL
            )
            skuStoreLogRepo.save(skuStoreLog)
        }
        PartnerAccount account = partnerAccountRepo.findOne(
                QPartnerAccount.partnerAccount.partner.id.eq(req.getShopInfo().get("partnerId"))
        )
        account.balance = account.balance + req.getPrice()
        partnerAccountRepo.save(account)

        paLogService.pAShopLog(account, req.getPrice(), req.getShopInfo())


    }
}
