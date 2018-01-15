package net.kingsilk.qh.agency.server.resource.brandApp.refund

import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.api.UniResp
import net.kingsilk.qh.agency.api.brandApp.order.dto.OrderCheckReq
import net.kingsilk.qh.agency.api.brandApp.order.dto.RefundLogisticsReq
import net.kingsilk.qh.agency.api.brandApp.refund.RefundApi
import net.kingsilk.qh.agency.api.brandApp.refund.dto.*
import net.kingsilk.qh.agency.core.*
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.msg.EventPublisher
import net.kingsilk.qh.agency.msg.api.search.esSkuStore.sync.SyncEvent
import net.kingsilk.qh.agency.repo.*
import net.kingsilk.qh.agency.server.resource.brandApp.addr.AddressConvert
import net.kingsilk.qh.agency.server.resource.brandApp.refund.convert.RefundConvert
import net.kingsilk.qh.agency.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import org.springframework.util.Assert
import org.springframework.util.StringUtils

import java.text.SimpleDateFormat

import static com.querydsl.core.types.dsl.Expressions.allOf

@Component(value = "refundResource")
class RefundResource implements RefundApi {

    @Autowired
    RefundRepo refundRepo

    @Autowired
    RefundService refundService

    @Autowired
    CalOrderPriceService calOrderPriceService

    @Autowired
    SkuStoreLogRepo skuStoreLogRepo

    @Autowired
    PartnerStaffService memberService

    @Autowired
    OrderLogService orderLogService

    @Autowired
    RefundConvert refundConvert

    @Autowired
    SysConfRepo sysConfRepo

    @Autowired
    AddressRepo addressRepo

    @Autowired
    PartnerStaffService partnerStaffService

    @Autowired
    SkuRepo skuRepo

    @Autowired
    RefundSkuRepo refundSkuRepo

    @Autowired
    SkuService skuService

    @Autowired
    AddressConvert addressConvert

    @Autowired
    CommonService commonService

    @Autowired
    LogisticsRepo logisticsRepo

    @Autowired
    PartnerAccountRepo partnerAccountRepo

    @Autowired
    StaffRepo staffRepo

    @Autowired
    SecService secService

    @Autowired
    PartnerRepo partnerRepo

    @Autowired
    PALogService paLogService

    @Autowired
    SkuStoreRepo skuStoreRepo

    @Autowired
    CartRepo cartRepo

    @Autowired
    RefundLogRepo refundLogRepo

    @Autowired
    EventPublisher eventPublisher

    @Autowired
    OrderRepo orderRepo

    @Override
    UniResp<RefundInfoResp> info(
            String brandAppId,
            String id) {
        Refund refund = refundRepo.findOne(id)
        Assert.notNull(refund, "售后订单信息不存在")
        return new UniResp<RefundInfoResp>(status: 200, data: refundConvert.refundInfoRespConvert(refund))
    }

    @Override
    UniResp<String> reject(
            String brandAppId,
            String id,
            String rejectReason) {
        Refund refund = refundRepo.findOne(id)
        refund.status = RefundStatusEnum.CLOSED
        refund.rejectReason = rejectReason

        List<RefundSku> refundSku = refundSkuRepo.findAll(
                QRefundSku.refundSku.refund.id.eq(refund.id)
        ).asList()

        Assert.notNull(refundSku, "退货单商品不存在")

        refundSku.each {
            SkuStore skuStore = skuStoreRepo.findOne(
                    allOf(
                            QSkuStore.skuStore.partner.id.eq(refund.buyerPartner.id),
                            QSkuStore.skuStore.sku.id.eq(it.sku.id)
                    ))
            Assert.notNull(skuStore, "库存中无此商品")
            skuStore.num = skuStore.num + it.num
            skuStoreRepo.save(skuStore)

            SyncEvent syncEvent = new SyncEvent()
            syncEvent.skuStoreId = skuStore.id
            eventPublisher.publish(syncEvent);
        }
        if (refund.refundType == RefundTypeEnum.MONEY_ONLY) {
            def order = refund.order
            order.status = order.lastStatus
            order.lastStatus = OrderStatusEnum.CANCELED
            orderRepo.save(order)
        }
        refundRepo.save(refund)
        return new UniResp<String>(status: 200, data: "操作成功")
    }

    //同意退款 -> WAIT_SENDING(待买家发货)
    @Override
    UniResp<String> agreeReturnGoods(
            String brandAppId,
            String id) {
        Refund refund = refundRepo.findOne(id)
        refund.status = RefundStatusEnum.WAIT_SENDING
        refundRepo.save(refund)
        return new UniResp<String>(status: 200, data: "操作成功")
    }

    @Override
    UniResp<String> agreeRefund(
            String brandAppId,
            String id) {
        Refund refund = refundRepo.findOne(id)
        refund.status = RefundStatusEnum.UNPAYED
        // TODO 加库存
        // TODO 减销量
        List<RefundSku> refundSku = refundSkuRepo.findAll(
                QRefundSku.refundSku.refund.id.eq(refund.id)
        ).asList()

        Assert.notNull(refundSku, "退货单商品不存在")

        refundSku.each {
            SkuStore skuStore = skuStoreRepo.findOne(
                    Expressions.allOf(
                            QSkuStore.skuStore.partner.id.eq(refund.sellerPartner.id),
                            QSkuStore.skuStore.sku.id.eq(it.sku.id)
                    ))
            Assert.notNull(skuStore, "库存中无此商品")
            skuStore.num = skuStore.num + it.num
            skuStore.salesVolume = skuStore.salesVolume - it.num
            skuStoreRepo.save(skuStore)

            SyncEvent syncEvent = new SyncEvent()
            syncEvent.skuStoreId = skuStore.id
            eventPublisher.publish(syncEvent);
        }
        refundRepo.save(refund)
        return new UniResp<String>(status: 200, data: "操作成功")
    }

    @Override
    UniResp<String> refundHandle(
            String brandAppId,
            String id) {
        Refund refund = refundRepo.findOne(id)
        Assert.notNull(refund, "退款订单错误")
        PartnerAccount buyAccount = partnerAccountRepo.findOne(
                allOf(
                        QPartnerAccount.partnerAccount.partner.eq(refund.buyerPartner),
                        QPartnerAccount.partnerAccount.brandAppId.eq(refund.brandAppId)
                )
        )
        if (!buyAccount) {
            buyAccount = new PartnerAccount()
            buyAccount.brandAppId = refund.brandAppId
            buyAccount.partner = refund.buyerPartner
            buyAccount.noCashBalance = 0
            buyAccount.balance = 0
            buyAccount.freezeBalance = 0
            buyAccount.owedBalance = 0
        }
        calOrderPriceService.calParnterAccount(refund)
        refund.status = RefundStatusEnum.FINISHED
        PartnerAccount sellAccount = partnerAccountRepo.findOne(
                allOf(
                        QPartnerAccount.partnerAccount.partner.eq(refund.sellerPartner),
                        QPartnerAccount.partnerAccount.brandAppId.eq(refund.brandAppId)
                )
        )
        //账户日志记录
        if(RefundTypeEnum.ITEM == refund.refundType) {
            paLogService.refundPartnerAccountLog(buyAccount, refund, "NOCASHBALANCE")
            paLogService.refundPartnerAccountLog(sellAccount, refund, "OWEDBALANCE");
        } else {
            def order = refund.order
            if(order){
                if(order.noCashBalancePrice!=0){
                    paLogService.refundPartnerAccountLog(buyAccount, refund, "NOCASHBALANCE")
                }
                if(order.balancePrice!=0){
                    paLogService.refundPartnerAccountLog(buyAccount, refund, "BALANCE")
                }
                List<Order.OrderItem> items = refund.order.orderItems
                items.each {
                    SkuStore skuStore = skuStoreRepo.findOne(
                            Expressions.allOf(
                                    QSkuStore.skuStore.partner.id.eq(refund.sellerPartner.id),
                                    QSkuStore.skuStore.sku.id.eq(it.sku.id)
                            ))
                    Assert.notNull(skuStore, "库存中无此商品")
                    skuStore.num = skuStore.num + it.num
                    skuStore.salesVolume = skuStore.salesVolume - it.num
                    skuStoreRepo.save(skuStore)

                    SyncEvent syncEvent = new SyncEvent()
                    syncEvent.skuStoreId = skuStore.id
                    eventPublisher.publish(syncEvent);
                }
        }
        if (refund.aliAmount != null && refund.aliAmount != 0) {
            paLogService.refundPartnerAccountLog(buyAccount, refund, "ALI")

        } else if (refund.wxAmount != null && refund.wxAmount != 0) {
            paLogService.refundPartnerAccountLog(buyAccount, refund, "WX")

            }
        }
        refundRepo.save(refund)
        return new UniResp<String>(status: 200, data: "保存成功")
    }

    @Override
    UniResp<RefundPageResp<RefundPageInfo>> page(
            String brandAppId,
            RefundPageReq refundPageReq) {
        String userId = secService.curUserId()
        Staff staff = staffRepo.findOne(
                allOf(
                        QStaff.staff.brandAppId.eq(brandAppId),
                        QStaff.staff.deleted.in([false, null]),
                        QStaff.staff.userId.eq(userId)))
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        Partner partner
        if (staff) {
            partner = partnerRepo.findOneByPartnerTypeEnumAndBrandAppId(PartnerTypeEnum.BRAND_COM, brandAppId)
        } else {
            partner = partnerStaff.partner
        }
        def statusList = []
        if("MONEY_ONLY".equals(refundPageReq.type)) {
            statusList = [
                    RefundStatusEnum.FINISHED,
                    RefundStatusEnum.CLOSED,
                    RefundStatusEnum.UNPAYED
            ]
        }else {
            statusList = [
                    RefundStatusEnum.FINISHED,
                    RefundStatusEnum.CLOSED,
                    RefundStatusEnum.UNPAYED,
                    RefundStatusEnum.UNCHECKED,
                    RefundStatusEnum.REJECTED,
                    RefundStatusEnum.WAIT_RECEIVED,
                    RefundStatusEnum.WAIT_SENDING,
                    RefundStatusEnum.CLOSED
            ]
        }
        PageRequest pageRequest = new PageRequest(refundPageReq.page, refundPageReq.size,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        Date startDate = null
        Date endDate = null


        if (refundPageReq.startDate) {
            startDate = sdf.parse(refundPageReq.startDate)
        }
        if (refundPageReq.endDate) {
            endDate = sdf.parse(refundPageReq.endDate)
        }

        Partner searchPartner = null
        if (refundPageReq.keyWord) {
            searchPartner = partnerRepo.findOne(
                    QPartner.partner.phone.eq(refundPageReq.keyWord)
            )
        }
        def all = refundRepo.findAll()
        Page<Refund> page = refundRepo.findAll(
                allOf(
                        QRefund.refund.brandAppId.eq(brandAppId),
                        QRefund.refund.deleted.in([false, null]),
                        refundPageReq.status ? QRefund.refund.status.eq(RefundStatusEnum.valueOf(refundPageReq.status)) : null,
                        refundPageReq.source == "BUYER" ?
                                QRefund.refund.buyerPartner.eq(partner) :
                                QRefund.refund.sellerPartner.eq(partner),
                        staff ? QRefund.refund.sellerPartner.eq(partner) : null,
                        refundPageReq.type ? QRefund.refund.refundType.eq(RefundTypeEnum.valueOf(refundPageReq.type)) : null,
                        statusList.size() > 0 ? QRefund.refund.status.in(statusList) : null,
                        QRefund.refund.status.in(statusList),
                        Expressions.anyOf(
                                refundPageReq.keyWord ? QRefund.refund.seq.like("%" + refundPageReq.getKeyWord() + "%") : null,
                                searchPartner ? QRefund.refund.buyerPartner.id.eq(searchPartner.id) : null,
                        ),
                        startDate ? QRefund.refund.dateCreated.gt(startDate) : null,
                        endDate ? QRefund.refund.dateCreated.lt(endDate) : null
                ),
                pageRequest
        )
        UniResp<RefundPageResp<RefundPageInfo>> resp = new UniResp<RefundPageResp<RefundPageInfo>>()
        resp.data = new RefundPageResp<RefundPageInfo>()
        resp.data.size = page.size
        resp.data.number = page.number
        resp.data.totalPages = page.totalPages
        resp.data.totalElements = page.totalElements
        page.content.each {
            resp.data.content.add(refundConvert.refundPageInfoConvert(it))
        }

        resp.data.refundStatusEnumMap = RefundStatusEnum.getMap()
        resp.data.refundTypeEnumMap = RefundTypeEnum.getMap()
        resp.data.refundReasonEnumMap = RefundReasonEnum.getMap()
        return resp
    }


    @Override
    UniResp<String> generateRefund(
            String brandAppId,
            List<OrderCheckReq> req) {
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()

        Address address = addressRepo.findOne(
                Expressions.allOf(
                        QAddress.address.partner.id.eq(partnerStaff.partner.parent.id),
                        QAddress.address.deleted.in([false, null]),
                        QAddress.address.defaultAddr.eq(true)
                ),

        )
        Refund refund = new Refund()
        refund.dateCreated = new Date()
        refund.brandAppId = brandAppId
        refund.lastModifiedDate = new Date()
        refund.buyerPartner = partnerStaff.partner
        refund.sellerPartner = partnerStaff.partner.parent
        refund.seq = commonService.dateString
        refund.refundType = RefundTypeEnum.ITEM
        refund.status = RefundStatusEnum.BUYER_UNCHECKED
        refund.applyPrice = 0
        refund.refundAmount = 0
        refund.reason = ""
        refund.refundAddress = addressConvert.convertAddressToRefundAdr(address)
        refundRepo.save(refund)
        req.each {
            RefundSku refundSku = new RefundSku();
            Sku sku = skuRepo.findOne(it.skuId)
            Assert.notNull(sku, "商品错误")
            refundSku.sku = sku
            refundSku.num = it.num
            refundSku.price = skuService.getTagPrice(sku)?.price ?: 0
            refundSku.refundAmount = it.num * refundSku.price
            refundSku.refund = refund
            refund.refundAmount = refund.refundAmount + refundSku.refundAmount
            refundSkuRepo.save(refundSku)
        }
        refund.applyPrice = refund.refundAmount
        refundRepo.save(refund)
        return new UniResp<String>(status: 200, data: refund.id)
    }

    @Override
    UniResp<String> check(
            String brandAppId,
            String id,
            RefundCheckReq req) {
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        Refund refund = refundRepo.findOne(id)
        List<RefundSku> refundSku = refundSkuRepo.findAll(
                QRefundSku.refundSku.refund.id.eq(refund.id)
        ).asList()

        refundSku.each {
            RefundSku resku ->
                SkuStore skuStore = skuStoreRepo.findOne(
                        allOf(
                                QSkuStore.skuStore.sku.id.eq(resku.sku.id),
                                QSkuStore.skuStore.partner.id.eq(refund.buyerPartner.id)
                        )
                )
                skuStore.num = skuStore.num - resku.num
                skuStoreRepo.save(skuStore)

                SyncEvent syncEvent = new SyncEvent()
                syncEvent.skuStoreId = skuStore.id
                eventPublisher.publish(syncEvent);

                Cart cart =
                        cartRepo.findOneByPartnerStaffAndBrandAppIdAndCartTypeEnum(partnerStaff, brandAppId, CartTypeEnum.REFUND)

                Cart.CartItem cartItem = cart.cartItems.find {
                    it.sku.id == resku.sku.id
                }
                cart.cartItems.remove(cartItem)
                cartRepo.save(cart)
        }

        refund.status = RefundStatusEnum.UNCHECKED
        refund.memo = req.memo
        refundRepo.save(refund)
        return new UniResp<String>(status: 200, data: "操作成功")
    }

    @Override
    UniResp<String> refundLogistics(
            String brandAppId,
            String id,
            RefundLogisticsReq req) {
        Refund refund = refundRepo.findOne(
                allOf(
                        QRefund.refund.id.eq(id),
                        QRefund.refund.refundType.eq(RefundTypeEnum.ITEM),
                        QRefund.refund.status.eq(RefundStatusEnum.WAIT_SENDING),
                )
        )
        //TODO
        Logistics logistics = new Logistics()
        logistics.company = LogisticsCompanyEnum.valueOf(req.company)
        logistics.expressNo = req.expressNo
        logisticsRepo.save(logistics)
        refund.logistics = logistics
        refund.status = RefundStatusEnum.WAIT_RECEIVED
        refundRepo.save(refund)
        return new UniResp<String>(status: 200, data: "提交成功")
    }

    @Override
    UniResp<String> cancelRefund(
            String brandAppId,
            String id,
            String rejectReason) {
        Refund refund = refundRepo.findOne(id)
        refund.status = RefundStatusEnum.CLOSED
        refund.rejectReason = rejectReason


        List<RefundSku> refundSku = refundSkuRepo.findAll(
                QRefundSku.refundSku.refund.id.eq(refund.id)
        ).asList()

        Assert.notNull(refundSku, "退货单商品不存在")

        refundSku.each {
            SkuStore skuStore = skuStoreRepo.findOne(
                    allOf(
                            QSkuStore.skuStore.partner.id.eq(refund.buyerPartner.id),
                            QSkuStore.skuStore.sku.id.eq(it.sku.id)
                    ))
            Assert.notNull(skuStore, "库存中无此商品")
            skuStore.num = skuStore.num + it.num
            skuStoreRepo.save(skuStore)

            SyncEvent syncEvent = new SyncEvent()
            syncEvent.skuStoreId = skuStore.id
            eventPublisher.publish(syncEvent);
        }
        refundRepo.save(refund)
        return new UniResp<String>(status: 200, data: "操作成功")
    }
}
