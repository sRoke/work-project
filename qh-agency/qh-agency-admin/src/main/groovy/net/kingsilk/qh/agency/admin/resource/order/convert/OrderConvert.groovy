package net.kingsilk.qh.agency.admin.resource.order.convert

import net.kingsilk.qh.agency.admin.api.order.dto.AddrResp
import net.kingsilk.qh.agency.admin.api.order.dto.OrderInfoResp
import net.kingsilk.qh.agency.core.LogisticsCompanyEnum
import net.kingsilk.qh.agency.domain.*
import org.springframework.stereotype.Component

/**
 * Created by lit on 17/7/21.
 */
@Component
class OrderConvert {
    static def orderInfoRespConvert(Order order, List<Refund> refunds) {
        OrderInfoResp orderInfoResp = new OrderInfoResp()
        orderInfoResp.id = order.id
        orderInfoResp.seq = order.seq
        orderInfoResp.memo=order.memo
        orderInfoResp.buyerMemo=order.buyerMemo
//        orderInfoResp.payType=order.payType
//        orderInfoResp.payTime=order.payTime
//        orderInfoResp.realName = order.partnerStaff?.realName
//        orderInfoResp.phone = order.partnerStaff?.phone
        if (refunds && refunds.size() > 0) {
            orderInfoResp.haveRefund = true
        }
        orderInfoResp.orderPrice = order.orderPrice/100
        orderInfoResp.paymentPrice = order.paymentPrice/100
        orderInfoResp.status=order.status
        orderInfoResp.statusDesp = order.status.desp
        orderInfoResp.address = addressInfoConvert(order.address)
        orderInfoResp.dateCreated = order.dateCreated
        for (Order.OrderItem orderItem : order.orderItems) {
            def type = order.partnerStaff.partner.partnerTypeEnum.code
            OrderInfoResp.OrderItemInfo orderItemInfo = orderItemInfoConvert(orderItem, type)
            orderInfoResp.orderItems.add(orderItemInfo)
        }
        for (Logistics logistics : order.logisticses) {
            OrderInfoResp.LogisticInfo logisticInfo = logisticInfoConvert(logistics)
            orderInfoResp.logisticses.add(logisticInfo)
        }
        orderInfoResp.logisticsCompanys = LogisticsCompanyEnum.getMap();
        return orderInfoResp;
    }

    static def addressInfoConvert(Address address) {
        OrderInfoResp.AddressInfo addressInfo = new OrderInfoResp.AddressInfo()
        addressInfo.receiver = address.receiver
        addressInfo.street = address.street
        addressInfo.phone = address.phone
        if (address.adc) {
            addressInfo.county = address.adc.name
            addressInfo.countyNo = address.adc.no
            if (address.adc.parent) {
                addressInfo.city = address.adc.parent.name
                addressInfo.cityNo = address.adc.parent.no
                if (address.adc.parent.parent) {
                    addressInfo.province = address.adc.parent.parent.name
                    addressInfo.provinceNo = address.adc.parent.parent.no
                }
            }
        }
        return addressInfo;
    }

    static def orderItemInfoConvert(Order.OrderItem orderItem, String type) {
        OrderInfoResp.OrderItemInfo orderItemInfo = new OrderInfoResp.OrderItemInfo();
        orderItemInfo.skuImg = orderItem.sku.item.imgs[0]
        orderItemInfo.skuTitle = orderItem.sku.title
        orderItemInfo.itemTitle = orderItem.sku.item.title
        Sku.TagPrice minTag = orderItem.sku.tagPrices.findAll { Sku.TagPrice it ->
            return type == it.tag.code
        }.min { Sku.TagPrice it ->
            it.price
        }
        orderItemInfo.skuPrice = minTag.price / 100

        orderItemInfo.num = orderItem.num
        for (Sku.Spec spec : orderItem.sku.specs) {
            orderItemInfo.specInfos.add(specInfoConvert(spec))
        }
        return orderItemInfo;
    }

    static def specInfoConvert(Sku.Spec spec) {
        OrderInfoResp.OrderItemInfo.SpecInfo specInfo = new OrderInfoResp.OrderItemInfo.SpecInfo()
        specInfo.propName = spec.itemProp.name
        specInfo.propValue = spec.itemPropValue.name
        return specInfo
    }

    static def logisticInfoConvert(Logistics logistic) {
        OrderInfoResp.LogisticInfo logisticInfo = new OrderInfoResp.LogisticInfo()
        if (logistic.company) {
            logisticInfo.company = logistic.company.desp
        }
        logisticInfo.expressNo = logistic.expressNo
        return logisticInfo;
    }

    static def addrRespConvert(String parent, String level, List<Adc> adcList) {
        AddrResp addrResp=new AddrResp()
        addrResp.parent = parent
        addrResp.level = level
        addrResp.list = []
        adcList.each { Adc adc ->
            AddrResp.AdcModel model = new AddrResp.AdcModel()
            model.no = adc.no
            model.name = adc.name
            addrResp.list.add(model)
        }

        return addrResp
    }

}
