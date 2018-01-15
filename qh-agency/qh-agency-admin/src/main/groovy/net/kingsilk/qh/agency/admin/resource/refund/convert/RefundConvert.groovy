package net.kingsilk.qh.agency.admin.resource.refund.convert

import net.kingsilk.qh.agency.admin.api.refund.dto.RefundInfoResp
import net.kingsilk.qh.agency.admin.api.refund.dto.RefundPageResp
import net.kingsilk.qh.agency.core.LogisticsCompanyEnum
import net.kingsilk.qh.agency.domain.Logistics
import net.kingsilk.qh.agency.domain.OrderLog
import net.kingsilk.qh.agency.domain.Refund
import net.kingsilk.qh.agency.domain.Sku
import org.springframework.stereotype.Component

/**
 * Created by lit on 17/7/21.
 */
@Component
class RefundConvert {
    static def refundInfoRespConvert(Refund refund, List<OrderLog> orderLogs) {
        RefundInfoResp resp = new RefundInfoResp()
        resp.id = refund.id
        resp.type = refund.refundType
        resp.typeDesp = refund.refundType.desp
        resp.status = refund.status
        resp.statusDesp = refund.status.desp
        //resp.applyNum = refund.applyNum
        resp.refundAmount = refund.refundAmount
        resp.status = refund.status
        resp.reason = refund.reason
        resp.rejectReason = refund.rejectReason
        resp.deliveryTime = refund.deliveryTime
        resp.receiveTime = refund.receiveTime
        resp.dateCreated = refund.dateCreated
        resp.memo = refund.memo
        resp.realName = refund.order.partnerStaff.realName
        resp.phone = refund.order.partnerStaff.phone
        resp.orderId = refund.order.seq
        resp.orderDate = refund.order.dateCreated
        resp.skuImg = refund.sku.item.imgs[0]
        resp.itemTitle = refund.sku.item.title
        for (Sku.Spec spec : refund.sku.specs) {
            resp.specInfos.add(specInfoConvert(spec))
        }
        if (orderLogs.size() > 0) {
            for (OrderLog orderLog : orderLogs) {
                if (!resp.orderLogInfoMap.containsKey(orderLog.operate.code)) {
                    resp.orderLogInfoMap.put(orderLog.operate.code, convertOrderLogToResp(orderLog))
                }
            }
        }
        resp.logisticsMap = LogisticsCompanyEnum.getMap()
        return resp;
    }

    static RefundInfoResp.SpecInfo specInfoConvert(Sku.Spec spec){
        RefundInfoResp.SpecInfo specInfo=new RefundInfoResp.SpecInfo()
        specInfo.propName = spec.itemProp.name
        specInfo.propValue = spec.itemPropValue.name
        return specInfo
    }

    static RefundInfoResp.OrderLogInfo convertOrderLogToResp(OrderLog orderLog) {
        RefundInfoResp.OrderLogInfo orderLogInfo=new RefundInfoResp.OrderLogInfo()
        orderLogInfo.operate = orderLog.operate
        orderLogInfo.price = orderLog.price
        orderLogInfo.dateCreated = orderLog.dateCreated
        orderLogInfo.orderStatus = orderLog.status
        return orderLogInfo;
    }

    static RefundPageResp.SkuInfo skuInfoConvert(Sku sku) {
        RefundPageResp.SkuInfo skuInfo=new RefundPageResp.SkuInfo()
        skuInfo.id = sku.id
        skuInfo.title = sku.item.title
        skuInfo.skuTitle = sku.title
        skuInfo.skuImage = sku.item.imgs[0]
        for(Sku.Spec spec : sku.specs){
            skuInfo.specInfos.add(specInfoConvert(spec))
        }
        return skuInfo;
    }

    static RefundPageResp.LogisticsInfo convertLogisticsToResp(Logistics logistics) {
        RefundPageResp.LogisticsInfo logisticsInfo=new RefundPageResp.LogisticsInfo()
        if(logistics.company){
            logisticsInfo.company = logistics.company.desp
        }
        logisticsInfo.expressNo = logistics.expressNo
        return logisticsInfo;
    }


}
