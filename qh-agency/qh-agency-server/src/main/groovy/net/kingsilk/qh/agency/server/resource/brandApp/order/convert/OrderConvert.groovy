package net.kingsilk.qh.agency.server.resource.brandApp.order.convert

import net.kingsilk.qh.agency.api.brandApp.addr.dto.AddressInfo
import net.kingsilk.qh.agency.api.brandApp.order.dto.*
import net.kingsilk.qh.agency.core.LogisticsCompanyEnum
import net.kingsilk.qh.agency.core.OrderStatusEnum
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.repo.*
import net.kingsilk.qh.agency.server.resource.brandApp.addr.AddressConvert
import net.kingsilk.qh.agency.server.resource.brandApp.deliverInvoice.convert.DeliverInvoiceConvert
import net.kingsilk.qh.agency.service.PartnerStaffService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.Assert

import java.text.SimpleDateFormat

/**
 * Created by lit on 17/7/21.
 */
@Component
class OrderConvert {

    @Autowired
    SkuConvert skuConvert

    @Autowired
    PartnerStaffService partnerStaffService

    @Autowired
    AddressConvert addressConvert

    @Autowired
    AdcRepo adcRepo

    @Autowired
    AddressRepo addressRepo

    @Autowired
    DeliverInvoiceRepo deliverInvoiceRepo

    @Autowired
    PartnerRepo partnerRepo

    @Autowired
    PartnerAccountRepo partnerAccountRepo

    @Autowired
    DeliverInvoiceConvert deliverInvoiceConvert

    @Autowired
    OrderRepo orderRepo

    def orderInfoRespConvert(Order order, List<Refund> refunds) {
        OrderInfoResp orderInfoResp = new OrderInfoResp()
        orderInfoResp.id = order.id
        orderInfoResp.seq = order.seq
        orderInfoResp.memo = order.sellerMemo
        orderInfoResp.buyerMemo = order.buyerMemo
//        orderInfoResp.payType=order.payType
//        orderInfoResp.payTime=order.payTime
//        orderInfoResp.realName = order.partnerStaff?.realName
//        orderInfoResp.phone = order.partnerStaff?.phone
        if (refunds && refunds.size() > 0) {
            orderInfoResp.haveRefund = true
        }
        Partner partner = partnerRepo.findOne(order.buyerPartnerId)
        orderInfoResp.realName = partner.realName
        orderInfoResp.phone = partner.phone
        orderInfoResp.partnerType = partner.partnerTypeEnum.desp

        orderInfoResp.orderPrice = order.orderPrice
        orderInfoResp.paymentPrice = order.paymentPrice
        orderInfoResp.adjustPrice = order.adjustPrice
        orderInfoResp.noCashBalancePrice = order.noCashBalancePrice
        orderInfoResp.balancePrice = order.balancePrice
        orderInfoResp.status = order.status
        orderInfoResp.statusDesp = order.status.desp
        orderInfoResp.address = addressInfoConvert(order.orgShippingAddr)
        orderInfoResp.dateCreated = order.dateCreated
        orderInfoResp.total = 0
        for (Order.OrderItem orderItem : order.orderItems) {
            orderInfoResp.total = orderInfoResp.total + orderItem.num
            def type = order.partnerStaff.partner.partnerTypeEnum.code
            OrderItemInfo orderItemInfo = orderItemInfoConvert(orderItem, type)
            orderInfoResp.orderItems.add(orderItemInfo)
        }
        if (order.status == OrderStatusEnum.UNRECEIVED || order.status == OrderStatusEnum.FINISHED) {
            DeliverInvoice deliverInvoice = deliverInvoiceRepo.findByOrder(order)
            Assert.notNull(deliverInvoice, "订单物流信息错误！")
            for (Logistics logistics : deliverInvoice.logisticses) {
                LogisticInfo logisticInfo = logisticInfoConvert(logistics)
                orderInfoResp.logisticses.add(logisticInfo)
            }
        }

        orderInfoResp.logisticsCompanys = LogisticsCompanyEnum.getMap();
        return orderInfoResp
    }

    def addressInfoConvert(Order.OrderAddress address) {
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

    def orderItemInfoConvert(Order.OrderItem orderItem, String type) {
        OrderItemInfo orderItemInfo = new OrderItemInfo();
        orderItemInfo.skuImg = orderItem.sku.imgs ? orderItem.sku.imgs[0] : orderItem.sku.item.imgs[0]
        orderItemInfo.skuTitle = orderItem.sku.title
        orderItemInfo.itemTitle = orderItem.sku.item?.title
        orderItemInfo.code = orderItem.sku.code
        orderItemInfo.skuPrice = orderItem.skuPrice

        orderItemInfo.num = orderItem.num
        for (Sku.Spec spec : orderItem.sku.specs) {
            orderItemInfo.specInfos.add(specInfoConvert(spec))
        }
        return orderItemInfo
    }

    static def specInfoConvert(Sku.Spec spec) {
        OrderItemInfo.SpecInfo specInfo = new OrderItemInfo.SpecInfo()
        specInfo.propName = spec?.itemProp?.name
        specInfo.propValue = spec?.itemPropValue?.name
        return specInfo
    }

    static def logisticInfoConvert(Logistics logistic) {
        LogisticInfo logisticInfo = new LogisticInfo()
        if (logistic.company) {
            logisticInfo.company = logistic.company.desp
        }
        logisticInfo.expressNo = logistic.expressNo
        return logisticInfo;
    }

    static def addrRespConvert(String parent, String level, List<Adc> adcList) {
        AddrResp addrResp = new AddrResp()
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

    OrderMiniInfo orderMiniInfoConvert(Order order) {
        OrderMiniInfo info = new OrderMiniInfo();
        order.adjustPrice = order.adjustPrice ? order.adjustPrice : 0
        info.id = order.id
        info.seq = order.seq
        info.orderPrice = order.orderPrice
        info.paymentPrice = order.orderPrice - order.adjustPrice
        info.status = order.status
        info.statusDesp = order.status.desp
        info.dateCreated = order.dateCreated
        info.total = 0
        for (Order.OrderItem orderItem : order.orderItems) {
            info.total = info.total + orderItem.num
            def type = order.partnerStaff?.partner?.partnerTypeEnum?.code
            OrderItemInfo orderItemInfo = orderItemInfoConvert(orderItem, type)
            info.orderItems.add(orderItemInfo)
        }
        return info
    }

    OrderInfoModel orderInfoModelConvert(Order order, PartnerAccount partnerAccount) {
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        OrderInfoModel orderInfoModel = new OrderInfoModel()
        DeliverInvoice deliverInvoice = deliverInvoiceRepo.findByOrder(order)
        if (deliverInvoice) {
            orderInfoModel.deliverSeq = deliverInvoice.seq
            orderInfoModel.logisticsInfo = new ArrayList<>()
            if (deliverInvoice.logisticses) {
                deliverInvoice.logisticses.each { Logistics logistics ->
                    OrderInfoModel.LogisticsInfoModel logisticsInfoModel = deliverInvoiceConvert.logisticsInfoModelConvert(logistics)
                    orderInfoModel.logisticsInfo.add(logisticsInfoModel)
                }
            }
        }
        orderInfoModel.id = order.id
        orderInfoModel.seq = order.seq
        orderInfoModel.memo = order.buyerMemo
        orderInfoModel.status = order.status.code
        orderInfoModel.statusDesp = order.status.desp
        orderInfoModel.orderPrice = order.orderPrice
        orderInfoModel.paymentPrice = order.paymentPrice
        orderInfoModel.noCashBalancePrice = order.noCashBalancePrice
        orderInfoModel.balancePrice = order.balancePrice
        orderInfoModel.items = []
        orderInfoModel.total = 0
        if (partnerAccount == null) {
            orderInfoModel.balance = 0
            orderInfoModel.noCashBalance = 0
        } else if (partnerAccount != null) {
            orderInfoModel.noCashBalance = partnerAccount.noCashBalance
            orderInfoModel.balance = partnerAccount.balance
        }
        order.orderItems.each { Order.OrderItem item ->
            orderInfoModel.total = orderInfoModel.total + item.num
            OrderInfoModel.OrderSkuModel sku = skuConvert.orderSkuModelConvert(item, partnerStaff?.partner?.partnerTypeEnum?.code)
            if (item.refund) {
                OrderInfoModel.OrderSkuModel.RefundModel refundModel = new OrderInfoModel.OrderSkuModel.RefundModel()
                refundModel.status = item.refund.status.code
                refundModel.statusDesp = item.refund.status.description
                sku.refund = refundModel
                sku.code = item.sku.code
            }
            orderInfoModel.items.add(sku)
        }

        Integer totalBalance = partnerAccount.balance +
                partnerAccount.noCashBalance
        if (totalBalance <= 0) {
            orderInfoModel.useDeduction = 0
        } else if (totalBalance > 0) {
            orderInfoModel.useDeduction = (totalBalance >= order.orderPrice ? order.orderPrice : totalBalance)
        }
        if (order.orgShippingAddr) {
            orderInfoModel.address = addressConvert.addressInfoConvert(order.orgShippingAddr)
        } else if (!orderInfoModel.address) {
            Order.OrderAddress address = addressConvert.convertAddressToOrderAdr(addressRepo.findOneByPartnerAndDefaultAddrAndDeleted(partnerAccount.partner, true, false))
            order.orgShippingAddr = address
            orderInfoModel.address = addressConvert.addressInfoConvert(addressConvert.convertAddressToOrderAdr(address))
            orderRepo.save(order)
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        orderInfoModel.createDate = sdf.format(order.dateCreated)
        return orderInfoModel
    }

//    OrderInfoModel orderInfoModelConvert(Order order) {
//        PartnerStaff curMember = partnerStaffService.getCurPartnerStaff()
//        OrderInfoModel info = new OrderInfoModel();
//        info.id = order.id
//        info.seq = order.seq
//        info.status = order.status.code
//        info.statusDesp = order.status.desp
//        info.orderPrice = order.orderPrice / 100
//        info.paymentPrice = order.paymentPrice / 100
//        info.items = []
//        order.orderItems.each { OrderItem item ->
//            OrderInfoModel.OrderSkuModel sku = skuConvert.skuConvert(item, curMember?.partner?.partnerTypeEnum?.code)
//            if (item.refund) {
//                OrderInfoModel.OrderSkuModel.RefundModel refundModel = new OrderInfoModel.OrderSkuModel.RefundModel()
//                refundModel.status = item.refund.status.code
//                refundModel.statusDesp = item.refund.status.description
//                sku.refund = refundModel
//            }
//            info.items.add(sku)
//        }
//        info.address = new AddressInfo()
//        info.address = addressInfoConvert(order.orgShippingAddr)
//        return info
//    }

}
