package net.kingsilk.qh.agency.server.resource.brandApp.deliverInvoice.convert

import net.kingsilk.qh.agency.api.brandApp.deliverInvoice.dto.DeliverInvoicePageResp
import net.kingsilk.qh.agency.api.brandApp.order.dto.LogisticInfo
import net.kingsilk.qh.agency.api.brandApp.order.dto.OrderInfoModel
import net.kingsilk.qh.agency.domain.DeliverInvoice
import net.kingsilk.qh.agency.domain.Logistics
import net.kingsilk.qh.agency.server.resource.brandApp.order.convert.OrderConvert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DeliverInvoiceConvert {
    @Autowired
    OrderConvert orderConvert

    def deliverInvoicePageRespConvert(DeliverInvoice deliverInvoice) {
        DeliverInvoicePageResp info = new DeliverInvoicePageResp()
        info.id = deliverInvoice.id
        info.seq = deliverInvoice.seq
        info.orderSeq = deliverInvoice.order.seq
        info.addressInfo = orderConvert.addressInfoConvert(deliverInvoice.orgShippingAddr)
        info.status = deliverInvoice.deliverStatusEnum
        info.total = 0
        deliverInvoice.order.orderItems.each {
            info.orderItems.add(orderConvert.orderItemInfoConvert(it, deliverInvoice.deliverPartner.partnerTypeEnum.code))
            info.total = info.total + it.num
        }
        info.logisticInfo = new ArrayList<>()
        if (deliverInvoice.logisticses != null) {
            deliverInvoice.logisticses.each {
                info.logisticInfo.add(logisticInfoConvert(it))
            }
        }
        info.paymentPrice = deliverInvoice.order.paymentPrice
        info.statusDesp = deliverInvoice.deliverStatusEnum.desp
        info.dateCreated = deliverInvoice.dateCreated
        return info
    }


    LogisticInfo logisticInfoConvert(Logistics logistics) {
        LogisticInfo logisticInfo = new LogisticInfo()
        logisticInfo.setCompany(logistics.getCompany().getDesp())
        logisticInfo.setExpressNo(logistics.getExpressNo())
        return logisticInfo
    }

    OrderInfoModel.LogisticsInfoModel logisticsInfoModelConvert(Logistics logistics) {
        OrderInfoModel.LogisticsInfoModel logisticsInfoModel = new OrderInfoModel.LogisticsInfoModel()
        logisticsInfoModel.company = logistics.company.desp
        logisticsInfoModel.deliverStatus = logistics.status
        logisticsInfoModel.expressNo = logistics.expressNo
        return logisticsInfoModel
    }
}
