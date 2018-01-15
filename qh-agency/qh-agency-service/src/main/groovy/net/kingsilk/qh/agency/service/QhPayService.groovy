package net.kingsilk.qh.agency.service

import groovy.transform.CompileStatic
import net.kingsilk.qh.agency.domain.Order
import net.kingsilk.qh.agency.domain.QhPay
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service

/**
 * 支付相关service
 */
@Service
@CompileStatic
class QhPayService {

    @Autowired
    MongoTemplate mongoTemplate

    /**
     * 由订单创建支付记录
     * @param order
     * @return
     */
    public QhPay createQhPayByOrder(Order order) {
        def qhPay = new QhPay()
        qhPay.brandAppId = order.brandAppId
        qhPay.order = order
//        qhPay.order.partnerStaff=order.partnerStaff
        mongoTemplate.save(qhPay)
        return qhPay
    }


}
