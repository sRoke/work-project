package net.kingsilk.qh.agency.server.resource.brandApp.order

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.api.UniPageResp
import net.kingsilk.qh.agency.api.UniResp
import net.kingsilk.qh.agency.api.brandApp.addr.dto.AddrSaveReq
import net.kingsilk.qh.agency.api.brandApp.order.OrderApi
import net.kingsilk.qh.agency.api.brandApp.order.dto.*
import net.kingsilk.qh.agency.core.*
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.msg.EventPublisher
import net.kingsilk.qh.agency.repo.*
import net.kingsilk.qh.agency.server.resource.brandApp.addr.AddressConvert
import net.kingsilk.qh.agency.server.resource.brandApp.order.convert.OrderConvert
import net.kingsilk.qh.agency.server.resource.brandApp.order.convert.SkuConvert
import net.kingsilk.qh.agency.server.service.ExcelWrite
import net.kingsilk.qh.agency.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.convert.ConversionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import org.springframework.util.Assert

import java.text.SimpleDateFormat

import static com.querydsl.core.types.dsl.Expressions.allOf

/**
 *
 */

@Component
class OrderResource implements OrderApi {

    @Autowired
    OrderRepo orderRepo

    @Autowired
    CompanyRepo companyRepo

    @Autowired
    RefundRepo refundRepo

    @Autowired
    LogisticsRepo logisticsRepo

    @Autowired
    RefundService refundService

    @Autowired
    EnumService enumService

    @Autowired
    OrderService orderService

    @Autowired
    AdcRepo adcRepo

    @Autowired
    PartnerStaffService partnerStaffService

    @Autowired
    PALogService paLogService

    @Autowired
    ExcelWrite excelWrite

    @Autowired
    OrderConvert orderConvert

    @Autowired
    QhPayRepo qhPayRepo

    @Autowired
    DeliverInvoiceRepo deliverInvoiceRepo

    @Autowired
    CommonService commonService

    @Autowired
    AddrService addrService

    @Autowired
    AddressRepo addressRepo

    @Autowired
    @Qualifier(value = "mvcConversionService")
    ConversionService conversionService

    @Autowired
    SkuRepo skuRepo

    @Autowired
    StaffRepo staffRepo

    @Autowired
    SkuService skuService

    @Autowired
    QhPayService qhPayService

    @Autowired
    CartRepo cartRepo

    @Autowired
    SkuConvert skuConvert

    @Autowired
    AddressConvert addressConvert

    @Autowired
    DeliverInvoiceLogRepo deliverInvoiceLogRepo

    @Autowired
    PartnerRepo partnerRepo

    @Autowired
    SkuStoreRepo skuStoreRepo

    @Autowired
    SecService secService

    @Autowired
    PartnerAccountRepo partnerAccountRepo

    @Autowired
    OrderLogRepo orderLogRepo

    @Autowired
    CalOrderPriceService calOrderPriceService

    @Autowired
    SkuStoreLogRepo skuStoreLogRepo

    @Autowired
    EventPublisher eventPublisher

    //----------------------------订单信息-------------------------------//
    @Override
    UniResp<OrderInfoResp> info(
            String brandAppId,
            String id
    ) {
        if (id == null) {
            return new UniResp(status: 10027, message: "订单信息不存在")
        }
        Order order = orderRepo.findOne(id)
        if (!order) {
            return new UniResp(status: 10027, message: "订单信息不存在")
        }
        List<Refund> refunds = refundService.search(null, id).asList()
        return new UniResp<OrderInfoResp>(status: 200, data: orderConvert.orderInfoRespConvert(order, refunds))
    }

    //----------------------------更改价格-------------------------------//
    @Override
    UniResp<String> adjustPrice(
            String brandAppId,
            String id,
            Integer adjustPrice
    ) {
        if (id != null) {
            Order order = orderRepo.findOne(id)
            if (order != null && adjustPrice != null) {

                Integer adjust = order.adjustPrice = order.orderPrice - adjustPrice.intValue()
                order.paymentPrice = (adjustPrice).intValue()
                Integer allPrice = 0
                for (Order.OrderItem orderItem : order.orderItems) {
                    allPrice += orderItem.num * orderItem.skuPrice
                }
                for (int i = 0; i < order.orderItems.size(); i++) {
                    Integer price = order.orderItems.get(i).skuPrice * order.orderItems.get(i).num
                    if (i != 0 && i == order.orderItems.size() - 1) {
                        order.orderItems.get(i).realTotalPrice = price - adjust
                    } else {
                        int thisPrice = (price / allPrice) * adjust
                        order.orderItems.get(i).realTotalPrice = price - thisPrice
                        adjust = adjust - thisPrice
                        allPrice = allPrice - price
                    }
                }
                orderRepo.save(order)
                OrderLog orderLog = new OrderLog(
                        order.id,
                        OperatorTypeEnum.MODIFIED,
                        order.status,
                        order.adjustPrice,
                        order.sellerMemo
                )
                orderLogRepo.save(orderLog)
            }
        }
        return new UniResp<String>(status: 200, data: "保存成功")
    }

    //----------------------------修改收货地址-------------------------------//
    @Override
    UniResp<String> updateAddress(
            String brandAppId,
            String id,
            OrderAddressReq orderAddressReq
    ) {
        if (id != null) {
            Order order = orderRepo.findOne(id)
            if (order != null) {
                Order.OrderAddress orderAddress = order.orgShippingAddr
                if (order.orgShippingAddr == null) {
                    orderAddress = new Order.OrderAddress()
                }
                Adc adc = adcRepo.findOneByNo(orderAddressReq.countyNo)
                orderAddress.receiver = orderAddressReq.receiver
                orderAddress.phone = orderAddressReq.phone
                orderAddress.street = orderAddressReq.street
                orderAddress.adc = adc.no
//                addressRepo.save(orderAddress)
                order.orgShippingAddr = orderAddress
                orderRepo.save(order)
                DeliverInvoice deliverInvoice = deliverInvoiceRepo.findByOrder(order)
                if (deliverInvoice) {
                    deliverInvoice.orgShippingAddr = orderAddress
                    deliverInvoiceRepo.save(deliverInvoice)
                }
            }

        }
        return new UniResp<String>(status: 200, data: "保存成功")
    }

    //----------------------------订单分页信息-------------------------------//
    @Override
    UniResp<OrderPageResp<OrderMiniInfo>> page(
            String brandAppId,
            OrderPageReq orderPageReq
    ) {
        PageRequest pageRequest = new PageRequest(orderPageReq.page, orderPageReq.size,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")//小写的mm表示的是分钟
        Date startDate = null
        Date endDate = null

        if (orderPageReq.startDate) {
            startDate = sdf.parse(orderPageReq.startDate)
        }
        if (orderPageReq.endDate) {
            endDate = sdf.parse(orderPageReq.endDate)
        }
//        PartnerStaff partnerStaff = partnerStaffService.curPartnerStaff
        String userId = secService.curUserId()
        Staff staff = staffRepo.findOne(
                allOf(
                        QStaff.staff.brandAppId.eq(brandAppId),
                        QStaff.staff.deleted.in([false, null]),
                        QStaff.staff.userId.eq(userId)))
        Partner partner = partnerRepo.findOneByPartnerTypeEnumAndBrandAppId(PartnerTypeEnum.BRAND_COM, brandAppId)

        Partner searchPartner = null
        if (orderPageReq.keyWord) {
            searchPartner = partnerRepo.findOne(
                    QPartner.partner.phone.eq(orderPageReq.keyWord)
            )
        }

        Page<Order> page = orderRepo.findAll(
                allOf(
                        QOrder.order.deleted.in([false, null]),
                        QOrder.order.brandAppId.eq(brandAppId),
//                        partnerStaff ? QOrder.order.sellerPartnerId.eq(partnerStaff.partner.id) : null,
                        staff ? QOrder.order.sellerPartnerId.eq(partner.id) : null,
                        startDate ? QOrder.order.dateCreated.gt(startDate) : null,
                        endDate ? QOrder.order.dateCreated.lt(endDate) : null,
                        orderPageReq.status ? QOrder.order.status.eq(OrderStatusEnum.valueOf(orderPageReq.status)) : QOrder.order.status.ne(OrderStatusEnum.UNCOMMITED),
                        Expressions.anyOf(
                                orderPageReq.keyWord ? QOrder.order.seq.like("%" + orderPageReq.keyWord + "%") : null,
                                searchPartner ? QOrder.order.buyerPartnerId.eq(searchPartner.id) : null,
                        )
                ),
                pageRequest
        )
        UniResp<OrderPageResp<OrderMiniInfo>> resp = new UniResp<OrderPageResp<OrderMiniInfo>>()
        resp.status = 200
//        resp.data = conversionService.convert(page, OrderPageResp)
        resp.data = new OrderPageResp<OrderMiniInfo>()
        resp.data.size = page.size
        resp.data.number = page.number
        resp.data.totalPages = page.totalPages
        resp.data.totalElements = page.totalElements
        page.content.each {
            resp.data.content.add(orderConvert.orderMiniInfoConvert(it))
        }
        resp.data.orderStatusEnumMap = OrderStatusEnum.getMap()
        String countId = partner.id
        resp.data.dataCountMap = orderService.getDataCountMap(countId)
        return resp
    }

    //----------------------------取消订单-------------------------------//
    /**
     * 取消订单时：
     * 如果已支付->会生成一条退款记录
     * 如果未支付->改变订单状态为拒绝接单
     * 如果处于代发货已生成发货单->1. 改变发货单状态为取消状态，2. 生成一条退款记录
     */
    @Override
    UniResp<String> cancelOrder(
            String brandAppId,
            String id,
            String memo
    ) {
        Order order = orderRepo.findOne(id)
        PartnerStaff partnerStaff = order.partnerStaff
        PartnerAccount partnerAccount = partnerAccountRepo.findOne(
                Expressions.allOf(
                        QPartnerAccount.partnerAccount.deleted.in(false),
                        QPartnerAccount.partnerAccount.brandAppId.eq(brandAppId),
                        QPartnerAccount.partnerAccount.partner.id.eq(partnerStaff.getPartner().getId())
                )
        )
        order.lastStatus = order.status
        order.status = OrderStatusEnum.CANCELED
        order.sellerMemo = memo
        orderRepo.save(order)
        Refund refund = new Refund()
        refund.brandAppId = order.brandAppId
        refund.dateCreated = new Date()
        refund.brandAppId = brandAppId
        refund.lastModifiedDate = new Date()
        refund.seq = commonService.dateString
        refund.buyerPartner = partnerRepo.findOne(order.buyerPartnerId)
        refund.sellerPartner = partnerRepo.findOne(order.sellerPartnerId)
        refund.order = order
        if (order.lastStatus == OrderStatusEnum.UNSHIPPED ||
                order.lastStatus == OrderStatusEnum.UNRECEIVED ||
                order.lastStatus == OrderStatusEnum.FINISHED) {
            refund.refundType = RefundTypeEnum.ITEM
        } else if (order.lastStatus == OrderStatusEnum.UNCONFIRMED ||
                order.lastStatus == OrderStatusEnum.FINANCE_CONFIRM) {
            refund.refundType = RefundTypeEnum.MONEY_ONLY
            def qhWXPay = qhPayRepo.findByOrderAndPayType(order,"WX")
            Integer wxPay = qhWXPay?qhWXPay.thirdPayAmount:0
            def qhAliPay = qhPayRepo.findByOrderAndPayType(order,"ALI")
            Integer aliPay = qhAliPay?qhAliPay.thirdPayAmount:0
            refund.aliAmount = aliPay
            refund.wxAmount = wxPay
        }
        refund.seq = commonService.dateString
        refund.status = RefundStatusEnum.WAIT_SENDING
        refund.applyPrice = order.orderPrice
        refund.refundAmount = order.orderPrice
        refund.reason = memo
        refundRepo.save(refund)
        OrderLog orderLog = new OrderLog(
                order.id,
                OperatorTypeEnum.CANCEL,
                order.status,
                order.paymentPrice,
                order.sellerMemo
        )
        orderLogRepo.save(orderLog)
        return new UniResp<String>(status: 200, data: "操作成功")
    }
//----------------------------拒绝订单-------------------------------//
    @Override
    UniResp<String> rejectOrder(
            String brandAppId,
            String id,
            String memo
    ) {
        Order order = orderRepo.findOne(id)
        order.status = OrderStatusEnum.REJECTED
        order.lastStatus = OrderStatusEnum.UNCONFIRMED
        order.sellerMemo = memo
        PartnerStaff partnerStaff = order.partnerStaff
        Refund refund = new Refund()
        refund.dateCreated = new Date()
        def qhWXPay = qhPayRepo.findByOrderAndPayType(order,"WX")
        Integer wxPay = qhWXPay?qhWXPay.thirdPayAmount:0
        def qhAliPay = qhPayRepo.findByOrderAndPayType(order,"ALI")
        Integer aliPay = qhAliPay?qhAliPay.thirdPayAmount:0
        refund.aliAmount = aliPay
        refund.wxAmount = wxPay
        refund.brandAppId = brandAppId
        refund.lastModifiedDate = new Date()
        refund.buyerPartner = partnerRepo.findOne(order.buyerPartnerId)
        refund.sellerPartner = partnerRepo.findOne(order.sellerPartnerId)
        refund.seq = commonService.dateString
        refund.order = order
        refund.refundType = RefundTypeEnum.MONEY_ONLY
        refund.status = RefundStatusEnum.UNPAYED
        refund.applyPrice = order.paymentPrice
        refund.refundAmount = order.paymentPrice
        refund.reason = memo
        refundRepo.save(refund)
        orderRepo.save(order)
        OrderLog orderLog = new OrderLog(
                order.id,
                OperatorTypeEnum.REJECTE,
                order.status,
                order.paymentPrice,
                order.sellerMemo?:""
        )
        orderLogRepo.save(orderLog)
        return new UniResp<String>(status: 200, data: "操作成功")
    }

    //----------------------------卖家备注-------------------------------//
    @Override
    UniResp<String> sellerMemo(
            String brandAppId,
            String id,
            String memo
    ) {
        Order order = orderRepo.findOne(id)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        String date = sdf.format(new Date())
        if (order.sellerMemo) {
            order.sellerMemo = order.sellerMemo + "<br>" + date + ":&nbsp&nbsp&nbsp&nbsp" + memo
        } else {
            order.sellerMemo = date + ":" + memo
        }

        orderRepo.save(order)
        return new UniResp<String>(status: 200, data: "操作成功")
    }

    //----------------------------确认接单-------------------------------//
    /**
     * 确认接单后会生成相应的发货单
     */
    @Override
    UniResp<String> confirmOrder(
            String brandAppId,
            String id
    ) {
        Order order = orderRepo.findOne(id)
        order.status = OrderStatusEnum.UNSHIPPED
        DeliverInvoice deliverInvoice = new DeliverInvoice()
        deliverInvoice.order = order
        Partner buyerPartner = partnerRepo.findOne(order.buyerPartnerId)
        Partner sellerPartner = partnerRepo.findOne(order.sellerPartnerId)
        deliverInvoice.deliverPartner = sellerPartner
        deliverInvoice.buyerPartner = buyerPartner
        deliverInvoice.brandAppId = order.brandAppId
        deliverInvoice.orgShippingAddr = order.orgShippingAddr
        deliverInvoice.seq = commonService.dateString
        deliverInvoice.deliverStatusEnum = DeliverStatusEnum.UNSHIPPED
        deliverInvoice.sourceTypeEnum = null
        deliverInvoice.dateCreated = new Date()

        deliverInvoiceRepo.save(deliverInvoice)
        orderRepo.save(order)
        OrderLog orderLog = new OrderLog(
                order.id,
                OperatorTypeEnum.CONFIRM_PAYMENT,
                order.status,
                order.paymentPrice,
                order.sellerMemo
        )
        orderLogRepo.save(orderLog)

        DeliverInvoiceLog deliverInvoiceLog = new DeliverInvoiceLog(
                deliverInvoice.id,
                deliverInvoice.order.id,
                deliverInvoice.deliverStatusEnum,
                deliverInvoice.deliverStaff?.id,
                deliverInvoice.deliverStaff?.id,
                deliverInvoice.dateCreated,
                deliverInvoice.logisticses[0]?.id
        )
        deliverInvoiceLogRepo.save(deliverInvoiceLog)
        return new UniResp<String>(status: 200, data: "操作成功")
    }


    @Override
    UniResp<String> chooseAddr(
            String brandAppId,
            String id,
            String addrId) {
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        Order order = orderRepo.findOne(id)
        Assert.notNull(order, "订单错误")
        Address address = addressRepo.findOneByIdAndPartner(addrId, partnerStaff.partner)
        Assert.notNull(address, "地址错误")
        order.orgShippingAddr = addressConvert.convertAddressToOrderAdr(address)
        orderRepo.save(order)
        return new UniResp<String>(status: 200, data: "SUCCESS")
    }

    @Override
    UniResp<String> changeAddr(
            String brandAppId,
            String id,
            String addrId) {
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        Order order = orderRepo.findOne(id)
        Assert.notNull(order, "订单错误")
        Address address = addressRepo.findOneByIdAndPartner(addrId, partnerStaff.partner)
        Assert.notNull(address, "地址错误")
        addrService.updateDefaultAddr(address)
        addressRepo.save(address)
        order.orgShippingAddr = addressConvert.convertAddressToOrderAdr(address)
        orderRepo.save(order)
        return new UniResp<String>(status: 200, data: "SUCCESS")
    }

    @Override
    UniResp<String> addAddr(
            String brandAppId,
            String id,
            AddrSaveReq req) {
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        Address address = new Address()
        Adc adc = adcRepo.findOneByNo(req.adcNo)
        address.adc = adc
        address.street = req.street
        address.receiver = req.receiver
        address.phone = req.phone
        address.memo = req.memo
        address.partnerStaff = partnerStaff
        address.partner = partnerStaff.partner
        address.defaultAddr = true

        addrService.updateDefaultAddr(address)
        Order order = orderRepo.findOne(id)
        order.orgShippingAddr = addressConvert.convertAddressToOrderAdr(address)
        orderRepo.save(order)
        addressRepo.save(address)

        return new UniResp<String>(status: 200, data: "保存成功")
    }

    @Override
    UniResp<String> updateAddr(
            String brandAppId,
            String orderId,
            String id, AddrSaveReq req) {
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        Address address = addressRepo.findOneByIdAndPartner(id, partnerStaff.partner)
        Assert.isTrue(!address.deleted, "该地址已删除")
        Assert.notNull(address, "原地址错误")
        Adc adc = adcRepo.findOneByNo(req.adcNo)
        address.adc = adc
        address.street = req.street
        address.receiver = req.receiver
        address.phone = req.phone
        address.memo = req.memo
        address.partnerStaff = partnerStaff
        address.partner = partnerStaff.partner

        //判断是否有默认地址，没有则自动添加
        Address tmpAddr = addressRepo.findOneByPartnerAndDefaultAddrAndDeleted(partnerStaff.partner, true, false)
        if (!tmpAddr) {
            req.defaultAddr = true
        }

        if (req.defaultAddr) {
            addrService.updateDefaultAddr(address)
            Order order = orderRepo.findOne(orderId)
            order.orgShippingAddr = addressConvert.convertAddressToOrderAdr(address)
            orderRepo.save(order)
        }
        addressRepo.save(address)
        return new UniResp<String>(status: 200, data: "保存成功")
    }

    @Override
    UniResp<Map<String, String>> getLogisticsCompanyEnum(
            String brandAppId) {
        Map<String, String> LogisticsCompanyEnumMap = LogisticsCompanyEnum.getMap()
        return new UniResp<Map<String, String>>(status: 200, data: LogisticsCompanyEnumMap)
    }

    @Override
    UniResp<UniPageResp<OrderInfoModel>> list(
            String brandAppId,
            OrderListReq req) {
        PartnerStaff curMember = partnerStaffService.getCurPartnerStaff()
        PartnerAccount partnerAccount = partnerAccountRepo.findOne(
                Expressions.allOf(
                        QPartnerAccount.partnerAccount.deleted.in(false),
                        QPartnerAccount.partnerAccount.brandAppId.eq(brandAppId),
                        QPartnerAccount.partnerAccount.partner.id.eq(curMember.getPartner().getId())
                )
        )

        BooleanExpression bStatus = QOrder.order.status.ne(OrderStatusEnum.UNCOMMITED)
        if (req.status) {
            bStatus = req.status ? QOrder.order.status.eq(OrderStatusEnum.valueOf(req.status)) : null
        }
        PageRequest pageRequest = new PageRequest(req.page, req.size,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))
        Page<Order> page = orderRepo.findAll(
                allOf(
                        QOrder.order.buyerPartnerId.eq(curMember.partner.id),
                        QOrder.order.deleted.in([false, null]),
                        bStatus,

                ),
                pageRequest
        )

        UniResp<UniPageResp<OrderInfoModel>> resp = new UniResp<UniPageResp<OrderInfoModel>>()
        resp.status = 200
        resp.data = conversionService.convert(page, UniPageResp)
        page.content.each {
            resp.data.content.add(orderConvert.orderInfoModelConvert(it, partnerAccount))
        }
        return resp
    }

    @Override
    UniResp<OrderInfoModel> detail(
            String brandAppId,
            String id) {
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        PartnerAccount partnerAccount = partnerAccountRepo.findOne(
                allOf(
                        QPartnerAccount.partnerAccount.deleted.in(false),
                        QPartnerAccount.partnerAccount.brandAppId.eq(brandAppId),
                        QPartnerAccount.partnerAccount.partner.id.eq(partnerStaff.getPartner().getId())
                )
        )
        Order order = orderRepo.findOne(id)
        Assert.notNull(order, "订单错误")
        OrderInfoModel resp = orderConvert.orderInfoModelConvert(order, partnerAccount)
        return new UniResp<OrderInfoModel>(status: 200, data: resp)
    }
}
