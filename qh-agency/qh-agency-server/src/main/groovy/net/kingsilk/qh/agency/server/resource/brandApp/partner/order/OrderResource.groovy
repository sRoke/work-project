package net.kingsilk.qh.agency.server.resource.brandApp.partner.order

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.api.ErrStatus
import net.kingsilk.qh.agency.api.ErrStatusException
import net.kingsilk.qh.agency.api.UniPageResp
import net.kingsilk.qh.agency.api.UniResp
import net.kingsilk.qh.agency.api.brandApp.addr.dto.AddrSaveReq
import net.kingsilk.qh.agency.api.brandApp.order.dto.*
import net.kingsilk.qh.agency.api.brandApp.partner.order.OrderApi
import net.kingsilk.qh.agency.core.*
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.msg.EventPublisher
import net.kingsilk.qh.agency.msg.api.search.esSkuStore.sync.SyncEvent
import net.kingsilk.qh.agency.repo.*
import net.kingsilk.qh.agency.security.BrandAppIdFilter
import net.kingsilk.qh.agency.server.resource.brandApp.addr.AddressConvert
import net.kingsilk.qh.agency.server.resource.brandApp.order.convert.OrderConvert
import net.kingsilk.qh.agency.server.resource.brandApp.order.convert.SkuConvert
import net.kingsilk.qh.agency.server.service.ExcelWrite
import net.kingsilk.qh.agency.service.*
import org.bson.types.ObjectId
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

@Component(value = "order")
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

    @Autowired
    RefundOnlyMoney refundOnlyMoney

    @Autowired
    private PartnerService partnerService
    //----------------------------订单信息-------------------------------//
    @Override
    UniResp<OrderInfoResp> info(
            String brandAppId,
            String partnerId,
            String id
    ) {
        partnerService.check()
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

//----------------------------订单分页信息-------------------------------//
    @Override
    UniResp<OrderPageResp<OrderMiniInfo>> page(
            String brandAppId,
            String partnerId,
            OrderPageReq orderPageReq
    ) {
        partnerService.check()
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
        PartnerStaff partnerStaff = partnerStaffService.curPartnerStaff
//        String userId = secService.curUserId()
//        Staff staff = staffRepo.findOne(
//                allOf(
//                        QStaff.staff.deleted.ne(true),
//                        QStaff.staff.userId.eq(userId)))
//        Partner partner = partnerRepo.findOneByPartnerTypeEnumAndBrandAppId(PartnerTypeEnum.BRAND_COM, brandAppId)

        Partner searchPartner = null
        if (orderPageReq.keyWord) {
            searchPartner = partnerRepo.findOne(
                    QPartner.partner.phone.eq(orderPageReq.keyWord)
            )
        }

        Page<Order> page = orderRepo.findAll(
                allOf(
                        QOrder.order.brandAppId.eq(brandAppId),
                        QOrder.order.deleted.ne(true),
                        partnerStaff ? QOrder.order.sellerPartnerId.eq(partnerStaff.partner.id) : null,
//                        staff ? QOrder.order.sellerPartnerId.eq(partner.id) : null,
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
        String countId = partnerStaff.partner.id
        resp.data.dataCountMap = orderService.getDataCountMap(countId)
        return resp
    }


    @Override
    UniResp<String> check(
            String brandAppId,
            String partnerId,
            List<OrderCheckReq> req) {
        partnerService.check()
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        Order order = new Order()
        order.seq = commonService.getDateString()
//        order.userId = curUserId

        order.partnerStaff = partnerStaff
        order.status = OrderStatusEnum.UNCOMMITED
        Address address = addressRepo.findOne(
                allOf(
                        QAddress.address.partner.eq(partnerStaff.partner),
                        QAddress.address.deleted.ne(true),
                        QAddress.address.defaultAddr.eq(true)
                ),

        )
        order.orgShippingAddr = addressConvert.convertAddressToOrderAdr(address)
        order.dateCreated = new Date()
//        order.company = companyService.getCurCompany()
        order.brandAppId = brandAppId
        order.orderPrice = 0
        order.orderItems = []
        Assert.isTrue(req.size() > 0, "商品错误")
        println(req)
        req.each {
            Order.OrderItem orderItem = new Order.OrderItem()
            Sku sku = skuRepo.findOne(it.skuId)
            Assert.notNull(sku, "商品错误")

            orderItem.sku = sku
            orderItem.num = it.num
            orderItem.skuPrice = skuService.getTagPrice(sku)?.price ?: 0
            orderItem.realTotalPrice = it.num * orderItem.skuPrice
            order.orderItems.add(orderItem)
            order.orderPrice += orderItem.realTotalPrice
            order.paymentPrice = order.orderPrice
        }
        order.setNoCashBalancePrice(0)
        order.setBalancePrice(0)
        order.buyerPartnerId = partnerStaff.partner.id
        order.sellerPartnerId = partnerStaff.partner.parent.id
        order.id = new ObjectId().toString()
        orderRepo.save(order)
        OrderLog orderLog = new OrderLog(
                order.id,
                OperatorTypeEnum.CREATE,
                order.status,
                order.paymentPrice,
                order.sellerMemo
        )
        orderLogRepo.save(orderLog)
        return new UniResp<String>(status: 200, data: order.id)
    }

    @Override
    UniResp<String> calculatePrice(
            String brandAppId,
            String partnerId,
            String id,
            String type) {
        partnerService.check()
        Order order = orderRepo.findOne(id)
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        PartnerAccount partnerAccount = partnerAccountRepo.findOne(
                Expressions.allOf(
                        QPartnerAccount.partnerAccount.deleted.in(false),
                        QPartnerAccount.partnerAccount.brandAppId.eq(brandAppId),
                        QPartnerAccount.partnerAccount.partner.id.eq(partnerStaff.getPartner().getId())
                )
        )
        if ("true".equals(type)) {
            calOrderPriceService.calNoCashBalancePrice(order, partnerAccount)
            calOrderPriceService.calOrderPrice(order)
            calOrderPriceService.calBalancePrice(order, partnerAccount)
            calOrderPriceService.calOrderPrice(order)
        } else if ("false".equals(type)) {
            calOrderPriceService.cancelNoCashBalanceDeductible(partnerAccount, order)
            calOrderPriceService.calOrderPrice(order)
            calOrderPriceService.cancelBalanceDeductible(partnerAccount, order)
            calOrderPriceService.calOrderPrice(order)
        }
        orderRepo.save(order)
        calOrderPriceService.calParnterAccount(order, partnerAccount)
        return new UniResp<String>(status: 200, data: order.id)
    }


    @Override
    UniResp<String> create(
            String brandAppId,
            String partnerId,
            String id,
            OrderCreateReq req) {
        partnerService.check()
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        PartnerAccount partnerAccount = partnerAccountRepo.findOne(
                Expressions.allOf(
                        QPartnerAccount.partnerAccount.deleted.in(false),
                        QPartnerAccount.partnerAccount.brandAppId.eq(brandAppId),
                        QPartnerAccount.partnerAccount.partner.id.eq(partnerStaff.getPartner().getId())
                )
        )
//        Order order = orderRepo.findOneByMemberAndIdndId(curUserId, req.orderId)
        Order order = orderRepo.findOne(id)
        Assert.notNull(order, "订单错误")
        if (!OrderStatusEnum.UNCOMMITED.equals(order.status) &&
                !OrderStatusEnum.UNCONFIRMED.equals(order.status)) {
            return new UniResp<String>(status: 10081, data: "请勿需重复提交订单！")
        }
        if (OrderStatusEnum.UNCOMMITED.equals(order.status)) {
            order.buyerMemo = req.memo
            order.status = OrderStatusEnum.UNPAYED
            order.balancePrice = order.balancePrice == null ? 0 : order.balancePrice
            order.noCashBalancePrice = order.noCashBalancePrice == null ? 0 : order.noCashBalancePrice
            order.buyerPartnerId = order.buyerPartnerId ?: partnerStaff.partner.id
            order.sellerPartnerId = order.sellerPartnerId ?: partnerStaff.partner.parent.id
            Partner buyerPartner = partnerRepo.findOne(order.buyerPartnerId)
            if (!order.orgShippingAddr) {
                Address address = addressRepo.findOneByPartnerAndDefaultAddrAndDeleted(buyerPartner, true, false)

                order.orgShippingAddr = addressConvert.convertAddressToOrderAdr(address)
            }
            calOrderPriceService.calParnterAccount(order, partnerAccount)
            qhPayService.createQhPayByOrder(order)
            if (order.noCashBalancePrice) {
                paLogService.pALogConvert(partnerAccount, order, "NOCASHBALANCE")
            }
            if (order.balancePrice) {
                paLogService.pALogConvert(partnerAccount, order, "BALANCE")
            }

        }

        // 删除购物车商品
        if (req.from == "CART") {
            println("提交订单 >>>> 从购物车移除商品")
            Cart cart = cartRepo.findOneByPartnerStaffAndBrandAppIdAndCartTypeEnum(partnerStaff, brandAppId, CartTypeEnum.PURCHASE)
            //便利订单中的商品
            order.orderItems.each { Order.OrderItem orderItem ->
                //从购物车找出该商品，并移除购物出
                Cart.CartItem cartItem = cart.cartItems.find {
                    it.sku.id == orderItem.sku.id
                }
                cart.cartItems.remove(cartItem)

                //从上级渠道商库存中减去相应的SKU
                SkuStore skuStore = skuStoreRepo.findOne(
                        allOf(
                                QSkuStore.skuStore.brandAppId.eq(brandAppId),
                                QSkuStore.skuStore.sku.eq(cartItem.sku),
                                QSkuStore.skuStore.partner.eq(partnerStaff.partner.parent),
                                QSkuStore.skuStore.deleted.ne(true)
                        )
                )
                Assert.notNull(skuStore, "sku信息错误")
                if (skuStore.num - cartItem.num < 0) {
                    throw new ErrStatusException(ErrStatus.PARTNER_500, "库存不足")
                }
                skuStore.num = skuStore.num - cartItem.num
                skuStore.salesVolume = skuStore.salesVolume + cartItem.num
                skuStoreRepo.save(skuStore)

                SyncEvent syncEvent = new SyncEvent()
                syncEvent.skuStoreId = skuStore.id
                eventPublisher.publish(syncEvent);
            }
            cartRepo.save(cart)
        }
        orderRepo.save(order)

        return new UniResp<String>(status: 200, data: "提交成功")
    }

    @Override
    UniResp<String> cancel(
            String brandAppId,
            String partnerId,
            String id,
            String reason) {
        partnerService.check()
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        PartnerAccount partnerAccount = partnerAccountRepo.findOne(
                Expressions.allOf(
                        QPartnerAccount.partnerAccount.deleted.in(false),
                        QPartnerAccount.partnerAccount.brandAppId.eq(brandAppId),
                        QPartnerAccount.partnerAccount.partner.id.eq(partnerStaff.getPartner().getId())
                )
        )
        Order order = orderRepo.findOne(id)
        def curUserId = secService.curUserId()
        Assert.notNull(order, "订单错误")
        Assert.isTrue(order.status == OrderStatusEnum.UNPAYED || OrderStatusEnum.UNCONFIRMED, "订单状态错误")
        if (order.status.equals(OrderStatusEnum.UNPAYED)) {
            order.status = OrderStatusEnum.CANCELED
            order.lastStatus = OrderStatusEnum.UNPAYED
            calOrderPriceService.calParnterAccount(order, partnerAccount);
            orderRepo.save(order)
            return new UniResp<String>(status: 200, data: "提交成功")
        }
        order.status = OrderStatusEnum.CANCELED
        order.lastStatus = OrderStatusEnum.UNCONFIRMED

        Refund refund = new Refund()
        def qhWXPay = qhPayRepo.findByOrderAndPayType(order, "WX")
        Integer wxPay = qhWXPay ? qhWXPay.thirdPayAmount : 0
        def qhAliPay = qhPayRepo.findByOrderAndPayType(order, "ALI")
        Integer aliPay = qhAliPay ? qhAliPay.thirdPayAmount : 0
        refund.aliAmount = aliPay
        refund.wxAmount = wxPay
        refund.dateCreated = new Date()
        refund.createdBy = curUserId
        refund.status = RefundStatusEnum.UNPAYED
        refund.lastModifiedBy = curUserId
        refund.buyerPartner = partnerRepo.findOne(order.buyerPartnerId)
        refund.sellerPartner = partnerRepo.findOne(order.sellerPartnerId)
        refund.lastModifiedDate = new Date()
        refund.setRefundType(RefundTypeEnum.MONEY_ONLY)
        refund.seq = commonService.dateString
        refund.setOrder(order)
        refund.brandAppId = order.brandAppId
        refund.setRefundAmount(order.paymentPrice)
        refund.setReason(reason)
        refundRepo.save(refund)
        orderRepo.save(order)
        OrderLog orderLog = new OrderLog(
                order.id,
                OperatorTypeEnum.CANCEL,
                order.status,
                order.paymentPrice,
                order.sellerMemo
        )
        orderLogRepo.save(orderLog)
        return new UniResp<String>(status: 200, data: "提交成功")
    }

    @Override
    UniResp<String> confirmReceive(

            String brandAppId,
            String partnerId,
            String id) {
        partnerService.check()
        Order order = orderRepo.findOne(id)
        Assert.notNull(order, "订单错误")
        Assert.isTrue(order.status == OrderStatusEnum.UNRECEIVED, "订单状态错误")
        order.status = OrderStatusEnum.FINISHED
        orderRepo.save(order)
        // 生成相应的库存记录
        order.orderItems.each {
            it.sku
            Partner partner = partnerRepo.findOne(order.buyerPartnerId)
            SkuStore skuStore = skuStoreRepo.findOne(
                    allOf(
                            QSkuStore.skuStore.partner.eq(partner),
                            QSkuStore.skuStore.sku.eq(it.sku)
                    )
            )
            if (!skuStore) {
                skuStore = new SkuStore()
                skuStore.dateCreated = new Date()
                skuStore.brandAppId = BrandAppIdFilter.getBrandAppId()
                skuStore.partner = partner
                skuStore.num = 0
                skuStore.salesVolume = 0
            }
            skuStore.lastModifiedDate = new Date()
            skuStore.sku = it.sku
            skuStore.num = skuStore.num + it.num
            skuStoreRepo.save(skuStore)

            SyncEvent syncEvent = new SyncEvent()
            syncEvent.skuStoreId = skuStore.id
            eventPublisher.publish(syncEvent);

            SkuStoreLog skuStoreLog = new SkuStoreLog(
                    brandAppId,
                    partner.getId(),
                    skuStore.id,
                    skuStore.num,
                    SkuStoreChangeEnum.PURCHASE
            )
            skuStoreLogRepo.save(skuStoreLog)
        }

        //改变发货单状态
        DeliverInvoice deliverInvoice = deliverInvoiceRepo.findOne(
                allOf(
                        QDeliverInvoice.deliverInvoice.deleted.ne(true),
                        QDeliverInvoice.deliverInvoice.order.eq(order),
                )
        )
        deliverInvoice.deliverStatusEnum = DeliverStatusEnum.RECEIVED
        deliverInvoiceRepo.save(deliverInvoice)

        //确认收货后 卖家的账户处理
        PartnerAccount partnerAccount = partnerAccountRepo.findByPartnerAndBrandAppId(partnerRepo.findOne(order.getBuyerPartnerId()), brandAppId)
        calOrderPriceService.calParnterAccount(order, partnerAccount)

        //确认收货时的账户明细变更
        PartnerAccount sellPartnerAccount = partnerAccountRepo.findOne(
                QPartnerAccount.partnerAccount.partner.id.eq(order.sellerPartnerId)
        )
        if (order.noCashBalancePrice) {
            paLogService.pALogConvert(sellPartnerAccount, order, "OWEDBALANCE")
        }
        if (order.balancePrice) {
            paLogService.pALogConvert(sellPartnerAccount, order, "BALANCE")
        }
        if (order.paymentPrice) {
            paLogService.pALogConvert(sellPartnerAccount, order, "confirmReceive")
        }
        OrderLog orderLog = new OrderLog(
                order.id,
                OperatorTypeEnum.CONFIRMED,
                order.status,
                order.paymentPrice,
                order.sellerMemo
        )

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

        //账户日志
        orderLogRepo.save(orderLog)

        return new UniResp<String>(status: 200, data: "SUCCESS")
    }

//----------------------------申请退款-------------------------------//

    /**
     * 申请退款|退货退款信息,修改状态为->等待卖家确认
     */
    @Override
    UniResp<String> refund(
            String brandAppId,
            String partnerId,
            OrderRefundReq req) {
        partnerService.check()
        def curUserId = secService.curUserId()
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        Order order = orderRepo.findOneByPartnerStaffAndId(partnerStaff, req.orderId)
        Sku sku = skuRepo.findOne(req.skuId)
        List<Refund> refun = refundRepo.findAll(
                allOf(
                        QRefund.refund.order.eq(order),
                        QRefund.refund.status.in([RefundStatusEnum.UNCHECKED, RefundStatusEnum.UNCHECKED])
                )
        ).toList()
        if (refun && refun.size() > 0) {
            return new UniResp<String>(status: 300, data: "请勿重复提交退款订单！")
        }

        Assert.notNull(order, "订单错误")
        Assert.isTrue(RefundTypeEnum.values()*.code.contains(req.type), "退货类型错误")
        RefundTypeEnum typeEnum = RefundTypeEnum.valueOf(req.type)
//        Sku sku = skuRepo.findOne(req.skuId)
        Assert.notNull(sku, "sku错误")
        Assert.isTrue(order.orderItems*.sku*.id.contains(sku.id), "订单中未找到该商品")
        Order.OrderItem orderItem
        for (def it : order.orderItems) {
            if (it.sku.id == req.skuId) {
                orderItem = it
                break
            }
        }

        Refund refund = new Refund()
        def qhWXPay = qhPayRepo.findByOrderAndPayType(order, "WX")
        Integer wxPay = qhWXPay ? qhWXPay.thirdPayAmount : 0
        def qhAliPay = qhPayRepo.findByOrderAndPayType(order, "ALI")
        Integer aliPay = qhAliPay ? qhAliPay.thirdPayAmount : 0
        refund.aliAmount = aliPay
        refund.wxAmount = wxPay
        refund.dateCreated = new Date()
        refund.createdBy = curUserId
        refund.lastModifiedBy = curUserId
        refund.lastModifiedDate = new Date()
        refund.refundType = typeEnum
        refund.applyPrice = req.applyPrice
        refund.status = RefundStatusEnum.UNCHECKED
        refund.order = order
        refund.refundAmount = req.applyPrice
        refund.reason = req.reason
        refund.memo = req.memo
        if (orderItem) {
            orderItem.refund = refund
        }
        refundRepo.save(refund)
        return new UniResp<String>(status: 200, data: "提交成功")
    }


    @Override
    UniResp<String> chooseAddr(
            String brandAppId,
            String partnerId,
            String id,
            String addrId) {
        partnerService.check()
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
            String partnerId,
            String id,
            String addrId) {
        partnerService.check()
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
            String partnerId,
            String id,
            AddrSaveReq req) {
        partnerService.check()
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
            String partnerId,
            String orderId,
            String id, AddrSaveReq req) {
        partnerService.check()
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
            String brandAppId,
            String partnerId) {
        partnerService.check()
        Map<String, String> LogisticsCompanyEnumMap = LogisticsCompanyEnum.getMap()
        return new UniResp<Map<String, String>>(status: 200, data: LogisticsCompanyEnumMap)
    }


    @Override
    UniResp<UniPageResp<OrderInfoModel>> list(
            String brandAppId,
            String partnerId,
            OrderListReq req) {
        partnerService.check()
        PartnerStaff curMember = partnerStaffService.getCurPartnerStaff()
        PartnerAccount partnerAccount = partnerAccountRepo.findOne(
                Expressions.allOf(
                        QPartnerAccount.partnerAccount.deleted.in(false),
                        QPartnerAccount.partnerAccount.brandAppId.eq(brandAppId),
                        QPartnerAccount.partnerAccount.partner.id.eq(curMember.getPartner().getId())
                )
        )
//        String[] tags = curMember?.tags*.code

        BooleanExpression bStatus = QOrder.order.status.ne(OrderStatusEnum.UNCOMMITED)
        if (req.status) {
            bStatus = req.status ? QOrder.order.status.eq(OrderStatusEnum.valueOf(req.status)) : null
        }
        PageRequest pageRequest = new PageRequest(req.page, req.size,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))
        Page<Order> page = orderRepo.findAll(
                allOf(
                        QOrder.order.buyerPartnerId.eq(curMember.partner.id),
                        QOrder.order.deleted.ne(true),
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
            String partnerId,
            String id) {
        partnerService.check()
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
        Refund refund = new Refund()
        refund.dateCreated = new Date()
        def qhWXPay = qhPayRepo.findByOrderAndPayType(order, "WX")
        Integer wxPay = qhWXPay ? qhWXPay.thirdPayAmount : 0
        def qhAliPay = qhPayRepo.findByOrderAndPayType(order, "ALI")
        Integer aliPay = qhAliPay ? qhAliPay.thirdPayAmount : 0
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
                order.sellerMemo ?: ""
        )
        orderLogRepo.save(orderLog)
        return new UniResp<String>(status: 200, data: "操作成功")
    }

}
