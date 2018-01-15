package net.kingsilk.qh.agency.service

import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.domain.OrderLog
import net.kingsilk.qh.agency.domain.QOrderLog
import net.kingsilk.qh.agency.repo.OrderLogRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by yanfq on 17-4-6.
 */
@Service
//@CompileStatic
class OrderLogService {

    @Autowired
    OrderLogRepo orderLogRepo



    List<OrderLog> findByOrderId(String orderId) {
        def list = orderLogRepo.findAll(
                Expressions.allOf(
                        QOrderLog.orderLog.deleted.in([false, null]),
                        orderId ? QOrderLog.orderLog.order.id.eq(orderId) : null,
                )
        );
        return list;
    }


}
