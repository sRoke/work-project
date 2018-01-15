package net.kingsilk.qh.agency.service

import com.querydsl.core.types.dsl.Expressions
import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode
import net.kingsilk.qh.agency.QhAgencyProperties
import net.kingsilk.qh.agency.core.*
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.repo.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.stereotype.Service
import org.springframework.util.Assert

/**
 * Created by yanfq on 17-4-6.
 */
@Service
@CompileStatic
class RefundService {

    @Autowired
    RefundRepo refundRepo

    @Autowired
    OrderRepo orderRepo

    @Autowired
    OrderLogRepo orderLogRepo
    @Autowired
    LogisticsRepo logisticsRepo
    @Autowired
    SkuRepo skuRepo


    @Autowired
    PartnerAccountRepo partnerAccountRepo

    @Autowired
    QhAgencyProperties props

    @Autowired
    OAuth2RestTemplate oClientPayWapRestTemplate


    def search(String keywords, String orderId) {
        def list = refundRepo.findAll(
                Expressions.allOf(
                        orderId ? QRefund.refund.order.id.eq(orderId) : null,
                        QRefund.refund.deleted.in([null, false])
                ));
        return list;
    }

    def handle(String id, boolean isAgree, LogisticsCompanyEnum company, String expressNo) {
        Refund refund = refundRepo.findOne(id);
        if (refund != null) {
            if (RefundTypeEnum.MONEY_ONLY.equals(refund.refundType)) {
                /*
                * 只退款的类型下，退款的状态为:
                * RefundStatusEnum.UNCHECKED
                *    ---同意退款-> RefundStatusEnum.FINISHED(做退款处理后将状态至为已完成)
                *    ---拒绝退款-> RefundStatusEnum.CLOSED
                */

                if (RefundStatusEnum.UNCHECKED.equals(refund.status)) {
                    moneyOnlyUncheckedHandle(refund, isAgree)
                }
            } else {
                /*
                * 退货退款的类型下，退款的状态为:
                * RefundStatusEnum.UNCHECKED
                *     ---拒绝退货退款-> RefundStatusEnum.CLOSED
                *     ---同意退货退款-> RefundStatusEnum.WAIT_SENDING
                *                     ---买家超过七天未填写物流信息->RefundStatusEnum.CLOSED
                *                     ---买家在期限内填写了物流信息->RefundStatusEnum.WAIT_RECEIVED
                *                                                 ---卖家检查货物不对劲拒绝收货->RefundStatusEnum.CLOSED
                *                                                 ---卖家检查货物没问题签收并退款->RefundStatusEnum.FINISHED
                */
                switch (refund.status) {
                    case RefundStatusEnum.UNCHECKED:
                        itemUncheckedHandle(refund, isAgree)
                        break;
                    case RefundStatusEnum.WAIT_SENDING:
                        itemWaitSendingHandle(refund, isAgree, company, expressNo)
                        break;
                    case RefundStatusEnum.WAIT_RECEIVED:
                        itemWaitReceivedHandle(refund, isAgree)
                        break;
                }
            }
        }
    }

    def moneyOnlyUncheckedHandle(Refund refund, boolean isAgree) {
        if (isAgree) {
            //处理退款业务
            oauthRefund(refund)
        } else {
            refund.status = RefundStatusEnum.REJECTED
            refundRepo.save(refund)
            OrderLog orderLog = new OrderLog(refund.order.id, OrderOperateEnum.REFUND_CLOSED as OperatorTypeEnum, refund.order.status, refund.applyPrice, refund.memo)
            orderLogRepo.save(orderLog)
        }
    }

    def itemUncheckedHandle(Refund refund, boolean isAgree) {
        refund.status = isAgree ? RefundStatusEnum.WAIT_SENDING : RefundStatusEnum.REJECTED
        refundRepo.save(refund)
        OrderLog orderLog = new OrderLog(refund.order.id, OrderOperateEnum.CONFIRM_REFUND as OperatorTypeEnum, refund.order.status, refund.applyPrice, refund.memo)
        orderLogRepo.save(orderLog)
    }

    def itemWaitSendingHandle(Refund refund, boolean isAgree, LogisticsCompanyEnum company, String expressNo) {
        Logistics logistics = new Logistics()
        logistics.setCompany(company)
        logistics.setExpressNo(expressNo)
        logisticsRepo.save(logistics)
        refund.setLogistics(logistics)
        refund.status = RefundStatusEnum.WAIT_RECEIVED
        refundRepo.save(refund)
        OrderLog orderLog = new OrderLog(refund.order.id, OrderOperateEnum.USER_DELIVER as OperatorTypeEnum, refund.order.status, refund.applyPrice, refund.memo)
        orderLogRepo.save(orderLog)

    }

    def itemWaitReceivedHandle(Refund refund, boolean isAgree) {
        if (isAgree) {
            //处理退款业务
            oauthRefund(refund)
        } else {
            refund.status = RefundStatusEnum.REJECTED
            refundRepo.save(refund)
            OrderLog orderLog = new OrderLog(refund.order.id, OrderOperateEnum.REFUND_CLOSED as OperatorTypeEnum, refund.order.status, refund.applyPrice, refund.memo)
            orderLogRepo.save(orderLog)
        }
    }

    /**
     * 退款操作，这里不做状态校验
     * @param refund
     */
    @CompileStatic(value = TypeCheckingMode.SKIP)
    void oauthRefund(Refund refund) {
        def map = [
                refundId  : refund.id,
                orderId   : refund.order.id,
                refund_fee: refund.refundAmount
        ]
        HttpEntity<Map> reqEntity = new HttpEntity<Map>(map);

        Map payMap = oClientPayWapRestTemplate.postForObject(props.qhPay.wap.api.pay_refund, reqEntity, Map)
        println("--------------------支付系统返回值\n${payMap}")

        if (payMap.data.result_code == "SUCCESS") {
            refund.status = RefundStatusEnum.FINISHED
            refundRepo.save(refund)

            ////恢复库存，暂时只考虑库存足够的情况，不够也可购买
            refund.order.orderItems.each { Order.OrderItem it ->
//                it.sku.storage += it.num;
                skuRepo.save(it.sku)
            }
            //修改订单状态
            Order order = orderRepo.findOne(refund.order.id)
            boolean allRefund = true
            for (Order.OrderItem orderItem : order.orderItems) {
                if (orderItem.refund == null || !RefundStatusEnum.FINISHED.equals(orderItem.refund.status)) {
                    allRefund = false
                    break;
                }
            }
            if (allRefund) {
                order.status = OrderStatusEnum.CLOSED
                orderRepo.save(order)
                OrderLog orderLog = new OrderLog(refund.order, OrderOperateEnum.REFUND_FINISHED, refund.order.status, refund.applyPrice, refund.memo)
                orderLogRepo.save(orderLog)
            }
        } else {
            Assert.notNull(null, payMap.data.result_msg ? (payMap.data.result_msg as String) : "退款失败")
        }
    }

//    def refund(Refund refund) {
//
////        refund.buyerPartner
//    }
}
