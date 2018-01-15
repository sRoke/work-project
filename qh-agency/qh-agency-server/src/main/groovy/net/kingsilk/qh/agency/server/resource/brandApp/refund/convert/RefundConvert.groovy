package net.kingsilk.qh.agency.server.resource.brandApp.refund.convert

import net.kingsilk.qh.agency.api.brandApp.addr.dto.AddressInfo
import net.kingsilk.qh.agency.api.brandApp.order.dto.OrderItemInfo
import net.kingsilk.qh.agency.api.brandApp.refund.dto.RefundInfoResp
import net.kingsilk.qh.agency.api.brandApp.refund.dto.RefundPageInfo
import net.kingsilk.qh.agency.core.LogisticsCompanyEnum
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.repo.AdcRepo
import net.kingsilk.qh.agency.repo.RefundSkuRepo
import net.kingsilk.qh.agency.server.resource.brandApp.addr.AddressConvert
import net.kingsilk.qh.agency.server.resource.brandApp.order.convert.OrderConvert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 *
 */
@Component
class RefundConvert {

    @Autowired
    OrderConvert orderConvert

    @Autowired
    RefundSkuRepo refundSkuRepo

    @Autowired
    AdcRepo adcRepo

    @Autowired
    AddressConvert addressConvert

    def refundInfoRespConvert(Refund refund) {
        RefundInfoResp resp = new RefundInfoResp()
        resp.wxAmount = refund.wxAmount
        resp.aliAmount = refund.aliAmount
        def order = refund.order
        resp.balanceAmount = order?.balancePrice
        resp.noBalanceAmount = order?.noCashBalancePrice
        resp.id = refund.id
        resp.type = refund.refundType
        resp.typeDesp = refund.refundType.desp
        resp.status = refund.status
        resp.statusDesp = refund.status.desp
        resp.refundAmount = refund.refundAmount
        resp.status = refund.status
        resp.reason = refund.reason
        resp.rejectReason = refund.rejectReason
        resp.deliveryTime = refund.deliveryTime
        resp.receiveTime = refund.receiveTime
        resp.dateCreated = refund.dateCreated
        resp.memo = refund.memo
        resp.refundSeq = refund.seq
        resp.address = addressConvert.convertRefundAdrToAddress(refund.refundAddress)
        resp.total = 0
        if (order) {
            List<Order.OrderItem> orderItems = order.orderItems
            orderItems.each {
                resp.total = resp.total + it.num
                resp.orderItemInfos.add(orderConvert.orderItemInfoConvert(it, refund.buyerPartner.partnerTypeEnum.code))
            }
        } else {
            List<RefundSku> refundSkus = refundSkuRepo.findAll(
                    QRefundSku.refundSku.refund.eq(refund

                    )
            ).asList()
            refundSkus.each {
                resp.total = resp.total + it.num
                resp.orderItemInfos.add(refundSkuConvert(it, refund.buyerPartner.partnerTypeEnum.code))
            }
        }
//        if (orderLogs.size() > 0) {
//            for (OrderLog orderLog : orderLogs) {
//                if (!resp.orderLogInfoMap.containsKey(orderLog.operate.code)) {
//                    resp.orderLogInfoMap.put(orderLog.operate.code, convertOrderLogToResp(orderLog))
//                }
//            }
//        }
        resp.logisticsMap = LogisticsCompanyEnum.getMap()

        if (refund.logistics) {
            resp.logisticsInfo = convertLogisticsToResp(refund.logistics)
        }
        if (refund.refundAddress) {
            resp.address = new AddressInfo()
            resp.address = addressInfoConvert(refund.refundAddress)
        }
        return resp
    }

    RefundPageInfo refundPageInfoConvert(Refund refund) {
        RefundPageInfo info = new RefundPageInfo();
        def buyerPartner = refund.buyerPartner
        def order = refund.order
        info.id = refund.id
        info.type = refund.refundType
        info.typeDesp = refund.refundType?.desp
        info.status = refund.status
        info.statusDesp = refund.status?.desp
        info.refundAmount = refund.refundAmount
        info.refundSeq = refund.seq
        info.aliAmount = refund.aliAmount
        info.wxAmount =refund.wxAmount
        info.balanceAmount = order?.balancePrice
        info.noBalanceAmount = order?.noCashBalancePrice
        info.reason = refund.reason
        info.rejectReason = refund.rejectReason
        info.deliveryTime = refund.deliveryTime
        info.receiveTime = refund.receiveTime
        info.dateCreated = refund.dateCreated
        info.memo = refund.memo
        Optional.ofNullable(buyerPartner).ifPresent { it ->
            info.buyerPartnerPhone = it.getPhone()
            info.buyerPartnerName = it.realName
        }
        info.refundSeq = refund.seq
        info.total = 0
        if (order) {
            List<Order.OrderItem> orderItems = order.orderItems
            orderItems.each {
                info.total = info.total + it.num
                Optional.ofNullable(buyerPartner).ifPresent{i ->
                    info.orderItemInfos.add(orderConvert.orderItemInfoConvert(it, i.partnerTypeEnum.code))
                }
            }
            info.orderAmount = order.paymentPrice
            info.orderSeq = order.seq
        } else {
            List<RefundSku> refundSkus = refundSkuRepo.findAll(
                    QRefundSku.refundSku.refund.eq(refund

                    )
            ).asList()
            refundSkus.each {
                info.total = info.total + it.num
                Optional.ofNullable(buyerPartner).ifPresent { i ->
                    info.orderItemInfos.add(refundSkuConvert(it, i.partnerTypeEnum.code))
                }
            }

        }
        if (refund.logistics) {
            info.logisticsInfo = convertLogisticsToResp(refund.logistics)
        }
        return info
    }

    OrderItemInfo.SpecInfo specInfoConvert(Sku.Spec spec) {
        OrderItemInfo.SpecInfo specInfo = new OrderItemInfo.SpecInfo()
            specInfo.propName = spec.itemProp.name
            specInfo.propValue = spec.itemPropValue.name
        return specInfo
    }

    RefundInfoResp.OrderLogInfo convertOrderLogToResp(OrderLog orderLog) {
        RefundInfoResp.OrderLogInfo orderLogInfo = new RefundInfoResp.OrderLogInfo()
        orderLogInfo.operate = orderLog.operate
        orderLogInfo.price = orderLog.price
        orderLogInfo.dateCreated = orderLog.dateCreated
        orderLogInfo.orderStatus = orderLog.status
        return orderLogInfo;
    }

//    RefundPageInfo.SkuInfo skuInfoConvert(Sku sku) {
//        RefundPageInfo.SkuInfo skuInfo = new RefundPageInfo.SkuInfo()
//        skuInfo.id = sku.id
//        skuInfo.title = sku.item.title
//        skuInfo.skuTitle = sku.title
//        skuInfo.skuImage = sku.item.imgs[0]
//        for (Sku.Spec spec : sku.specs) {
//            skuInfo.specInfos.add(specInfoConvert(spec))
//        }
//        return skuInfo;
//    }

    RefundPageInfo.LogisticsInfo convertLogisticsToResp(Logistics logistics) {
        RefundPageInfo.LogisticsInfo logisticsInfo = new RefundPageInfo.LogisticsInfo()
        if (logistics.company) {
            logisticsInfo.company = logistics.company.desp
        }
        logisticsInfo.expressNo = logistics.expressNo
        return logisticsInfo;
    }


    def refundSkuConvert(RefundSku refundSku, String type) {
        OrderItemInfo orderItemInfo = new OrderItemInfo();
        orderItemInfo.skuImg = refundSku.sku.item?.imgs[0]
        orderItemInfo.skuTitle = refundSku.sku.title
        orderItemInfo.itemTitle = refundSku.sku.item?.title
        Sku.TagPrice minTag = refundSku.sku.tagPrices.findAll { Sku.TagPrice it ->
            return type == it.tag.code
        }.min { Sku.TagPrice it ->
            it.price
        }
        orderItemInfo.skuPrice = minTag ? minTag?.price : -1

        orderItemInfo.num = refundSku.num
        for (Sku.Spec spec : refundSku.sku.specs) {
            orderItemInfo.specInfos.add(specInfoConvert(spec))
        }
        return orderItemInfo;
    }

    def addressInfoConvert(Refund.RefundAddress address) {
        if (!address.asBoolean()) {
            return;

        }
        AddressInfo addressInfo = new AddressInfo()
        addressInfo.receiver = address.receiver
        addressInfo.street = address.street
        addressInfo.phone = address.phone
        Adc adc = adcRepo.findOneByNo(address.adc)
        if (address.adc) {
            addressInfo.county = adc.name
            addressInfo.countyNo = adc.no
            if (adc.parent) {
                addressInfo.city = adc.parent.name
                addressInfo.cityNo = adc.parent.no
                if (adc.parent.parent) {
                    addressInfo.province = adc.parent.parent.name
                    addressInfo.provinceNo = adc.parent.parent.no
                }
            }
        }
        return addressInfo;
    }
}
