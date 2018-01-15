package net.kingsilk.qh.agency.service

import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.core.OrderStatusEnum
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.repo.OrderRepo
import net.kingsilk.qh.agency.repo.QhPayRepo
import net.kingsilk.qh.agency.util.DbUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.util.stream.Collectors
import java.util.stream.Stream

/**
 * Created by lit on 17/8/1.
 */
@Service
class ReportService {
    @Autowired
    QhPayRepo qhPayRepo

    @Autowired
    OrderRepo orderRepo

    @Autowired
    PartnerStaffService memberService

    @Autowired
    DbUtil dbUtil

    Map<String,Integer> purchase(Date startDate,Date endDate,List<OrderStatusEnum> olist) {
        PartnerStaff partnerStaff = memberService.getCurPartnerStaff()

        List<Order> orders = orderRepo.findAll(
                Expressions.allOf(
                        QOrder.order.sellerPartnerId.eq(partnerStaff.partner.id),
                        dbUtil.opNotIn(QOrder.order.status, olist)
                )
        ).toList().stream().distinct().collect(Collectors.toList())

        Integer price = 0
        Integer num = 0

        Map<String,Integer> map=new HashMap<>()
        if (orders.size() > 0) {
            List<QhPay> pays = qhPayRepo.findAll(
                    Expressions.allOf(
                            dbUtil.opIn(QQhPay.qhPay.order, orders),
                            startDate ? QQhPay.qhPay.dateCreated.gt(startDate) : null,
                            endDate? QQhPay.qhPay.dateCreated.lt(endDate):null
                    )
            ).toList()
            List<Order> orderList = new LinkedList<>()
            pays.each {
                QhPay qhPay ->
                    orderList.add(qhPay.order)
            }

            orderList.unique().each {Order order ->
                order.orderItems.each {
                    Order.OrderItem orderItem ->
                        num = num + orderItem.num
                        price = price + orderItem.realTotalPrice
                }
            }
            map.put("num",num)
            map.put("price",price)
        }
        return map
    }

    def saleStatistics(Date date) {
        PartnerStaff partnerStaff = memberService.getCurPartnerStaff()
        /**
         * DbUtil 工具类示范代码区，可用list的addAll方法多次调用进行追加，用allOf或anyOf处理list中元素并/或的关系，
         * deleted参数只需要在最后一次调用传入即可
         */
//        List<BooleanExpression> expressions = dbUtil.opNotIn([
//                OrderStatusEnum.UNCOMMITED,
//                OrderStatusEnum.UNPAYED,
//                OrderStatusEnum.REJECTED,
//                OrderStatusEnum.CANCELED
//        ].asList(), QQhPay.qhPay.order.status, null)
//        expressions.addAll([
//                QQhPay.qhPay.staff.partner.eq(partnerStaff.partner),
//                endDate ? QQhPay.qhPay.dateCreated.lt(endDate) : null,
//                startDate ? QQhPay.qhPay.dateCreated.gt(startDate) : null
//        ].asList())
        List<QhPay> orders = qhPayRepo.findAll(
                Expressions.allOf(
                        QQhPay.qhPay.order.sellerPartnerId.eq(partnerStaff.partner.id),
//                        endDate ? QQhPay.qhPay.dateCreated.lt(endDate) : null,
                        date ? QQhPay.qhPay.dateCreated.gt(date) : null
//                        (BooleanExpression[]) expressions.toArray(),
                )
        ).toList()

        Integer price = 0
        Integer num = 0
        orders.each {
            QhPay qhPay ->
                qhPay.order.orderItems.each {
                    Order.OrderItem orderItem ->
                        num = num + orderItem.num
//                        price = price + orderItem.realTotalPrice
                }
        }
        return num

    }
}
