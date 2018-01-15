package net.kingsilk.qh.agency.wap.resource.order.convert

import net.kingsilk.qh.agency.domain.Order
import net.kingsilk.qh.agency.wap.api.addr.dto.AddrModel
import net.kingsilk.qh.agency.wap.api.order.dto.OrderInfoModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

/**
 * Created by lit on 17/7/20.
 */
@Component
class OrderConvert {
    @Autowired
    SkuConvert skuConvert

    @Autowired
    AddrConvert addrConvert

    OrderInfoModel orderInfoModelConvert(Order order, String partnerTypeEnum) {
        OrderInfoModel orderInfoModel = new OrderInfoModel()
        orderInfoModel.id = order.id
        orderInfoModel.seq = order.seq
        orderInfoModel.memo=order.buyerMemo
        orderInfoModel.status = order.status.code
        orderInfoModel.statusDesp = order.status.desp
        orderInfoModel.orderPrice = order.orderPrice/100
        orderInfoModel.paymentPrice = order.paymentPrice/100
        orderInfoModel.items = []
        order.orderItems.each { Order.OrderItem item ->
            OrderInfoModel.OrderSkuModel sku = new OrderInfoModel.OrderSkuModel()
            sku = skuConvert.orderSkuModelConvert(item, partnerTypeEnum)
            if (item.refund) {
                OrderInfoModel.OrderSkuModel.RefundModel refundModel = new OrderInfoModel.OrderSkuModel.RefundModel()
                refundModel.status = item.refund.status.code
                refundModel.statusDesp = item.refund.status.description
                sku.refund = refundModel
            }
            orderInfoModel.items.add(sku)
        }
        orderInfoModel.address = new AddrModel()
        orderInfoModel.address = addrConvert.addrModelConvert(order.getAddress())
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        orderInfoModel.createDate = sdf.format(order.dateCreated);
        return orderInfoModel
    }
}
