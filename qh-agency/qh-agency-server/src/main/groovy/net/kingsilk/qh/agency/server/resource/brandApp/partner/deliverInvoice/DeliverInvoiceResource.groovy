package net.kingsilk.qh.agency.server.resource.brandApp.partner.deliverInvoice

import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.api.UniPageResp
import net.kingsilk.qh.agency.api.UniResp
import net.kingsilk.qh.agency.api.brandApp.deliverInvoice.dto.DeliverInvoiceInfoResp
import net.kingsilk.qh.agency.api.brandApp.deliverInvoice.dto.DeliverInvoicePageReq
import net.kingsilk.qh.agency.api.brandApp.deliverInvoice.dto.DeliverInvoicePageResp
import net.kingsilk.qh.agency.api.brandApp.deliverInvoice.dto.DeliverInvoiceShipReq
import net.kingsilk.qh.agency.api.brandApp.order.dto.LogisticInfo
import net.kingsilk.qh.agency.api.brandApp.partner.deliverInvoice.DeliverInvoiceApi
import net.kingsilk.qh.agency.core.*
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.repo.*
import net.kingsilk.qh.agency.server.resource.brandApp.deliverInvoice.convert.DeliverInvoiceConvert
import net.kingsilk.qh.agency.server.resource.brandApp.order.convert.OrderConvert
import net.kingsilk.qh.agency.service.PartnerService
import net.kingsilk.qh.agency.service.PartnerStaffService
import net.kingsilk.qh.agency.service.SecService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.convert.ConversionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import org.springframework.util.Assert
import org.springframework.util.StringUtils

import java.text.SimpleDateFormat

@Component(value = "deliverInvoice")
class DeliverInvoiceResource implements DeliverInvoiceApi {

    @Autowired
    DeliverInvoiceRepo deliverInvoiceRepo

    @Autowired
    SecService secService

    @Autowired
    StaffRepo staffRepo

    @Autowired
    OrderConvert orderConvert

    @Autowired
    LogisticsRepo logisticsRepo

    @Autowired
    OrderRepo orderRepo

    @Autowired
    PartnerRepo partnerRepo

    @Autowired
    @Qualifier(value = "mvcConversionService")
    ConversionService conversionService

    @Autowired
    DeliverInvoiceConvert deliverInvoiceConvert
    @Autowired
    PartnerStaffService partnerStaffService

    @Autowired
    DeliverInvoiceLogRepo deliverInvoiceLogRepo

    @Autowired
    private PartnerService partnerService

    @Override
    UniResp<DeliverInvoiceInfoResp> info(
            String brandAppId,
            String deliverInvoiceid
    ) {

        // String brandAppId
        //获取当前用户id
//        String userId = secService.curUserId()
//        Assert.isTrue(!StringUtils.isEmpty(userId), "请先登录")

//        Staff staff = staffRepo.findOne(
//                Expressions.allOf(
//                        QStaff.staff.deleted.in([false, null]),
//                        QStaff.staff.userId.eq(userId)))
//        Assert.notNull(staff, "用户不存在")

        //Assert.isTrue(staff.getAuthorities().contains(StaffAuthorityEnum.ORDER_R),
        //      "该用户无此权限")

//        Assert.isTrue(staff.getBrandAppId().equals(brandAppId),
//                "该用户不是不隶属于品牌商")
//        Assert.isTrue(!StringUtils.isEmpty(partnerId), "渠道商id不能为空")
//        Partner partner = partnerRepo.findOne(
//                Expressions.allOf(
//                        QPartner.partner.deleted.in([false, null]),
//                        QPartner.partner.id.eq(partnerId)
//                )
//        )
//        Assert.isTrue(partner.getBrandAppId()==(brandAppId),"该渠道商不属于该品牌商")
//        Assert.isTrue(!StringUtils.isEmpty(deliverInvoiceid),
//                "查找发货单的id不能为空")
        partnerService.check()
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        DeliverInvoice deliverInvoice = deliverInvoiceRepo.findOne(
                Expressions.allOf(
                        Expressions.anyOf(
                                partnerStaff ? QDeliverInvoice.deliverInvoice.deliverPartner.eq(partnerStaff.partner) : null,
                                QDeliverInvoice.deliverInvoice.brandAppId.eq(brandAppId)
                        ),
                        QDeliverInvoice.deliverInvoice.deleted.in([false, null]),
                        QDeliverInvoice.deliverInvoice.id.eq(deliverInvoiceid)
//                        QDeliverInvoice.deliverInvoice.brandAppId.eq(brandAppId),
//                        QDeliverInvoice.deliverInvoice.deliverPartner.id.eq(partnerId)
                )
        )
        Assert.notNull(deliverInvoice, "没有查到该订单")

        // 类型转换
        DeliverInvoiceInfoResp infoResp = new DeliverInvoiceInfoResp()
        infoResp.brandId = deliverInvoice.brandAppId
        infoResp.deliverStatusEnum = deliverInvoice.deliverStatusEnum
        infoResp.statusDesp = deliverInvoice.deliverStatusEnum.desp
        for (Logistics logistics : deliverInvoice.logisticses) {
            LogisticInfo logisticInfo = orderConvert.logisticInfoConvert(logistics)
            infoResp.logisticses.add(logisticInfo)
        }
        infoResp.logisticsCompanys = LogisticsCompanyEnum.getMap()
        infoResp.id = deliverInvoice.id
        infoResp.orderId = deliverInvoice.order.id
        infoResp.seq = deliverInvoice.seq
        infoResp.deliverStaffId = deliverInvoice.deliverStaff?.userId
        infoResp.partnerId = deliverInvoice.buyerPartner.id
        UniResp<DeliverInvoiceInfoResp> uniResp = new UniResp<>()
        uniResp.setStatus(200)
        uniResp.setData(infoResp)
        return uniResp
    }

    @Override
    UniResp<UniPageResp<DeliverInvoicePageResp>> page(String brandAppId, DeliverInvoicePageReq deliverInvoicePageReq) {
        partnerService.check()
        //获取当前用户id
        String userId = secService.curUserId()
        Assert.isTrue(!StringUtils.isEmpty(userId), "请先登录")

//        List<Boolean> list = new ArrayList<>()
//        list.add(false)
//        list.add(null)

        //判断该员工是否存在
        Staff staff = staffRepo.findOne(
                Expressions.allOf(
                        QStaff.staff.deleted.in([false, null]),
                        QStaff.staff.brandAppId.eq(brandAppId),
                        QStaff.staff.userId.in(userId)))
//        Assert.notNull(staff, "用户不存在")
//        Assert.isTrue(staff.getBrandAppId().equals(brandAppId),
//                "该员工不属于不该渠道商")

        //需要否？
//        Assert.isTrue(staff.getAuthorities().contains(StaffAuthorityEnum.ORDER_R),
//                "该用户无此权限")

        //判断该partnerId是否存在 且parentId是否隶属brandAppId
//        Assert.isTrue(!StringUtils.isEmpty(partnerId),"渠道商id不能为空")

//        Partner partner = partnerRepo.findOne(
//                Expressions.allOf(
//                        QPartner.partner.deleted.in([false, null]),
//                        QPartner.partner.id.in(partnerId)
//                )
//        )
//        Assert.isTrue(partner.getBrandId().equals(brandAppId),"该渠道商不属于该品牌商")
        Partner partner = partnerRepo.findOneByPartnerTypeEnumAndBrandAppId(PartnerTypeEnum.BRAND_COM, brandAppId)
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        PageRequest pageRequest = new PageRequest(
                deliverInvoicePageReq.page,
                deliverInvoicePageReq.size,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        Date startDate = null
        Date endDate = null


        if (deliverInvoicePageReq.startDate) {
            startDate = sdf.parse(deliverInvoicePageReq.startDate)
        }
        if (deliverInvoicePageReq.endDate) {
            endDate = sdf.parse(deliverInvoicePageReq.endDate)
        }
        Logistics logistics = null
        String buyerPartner = null
        if (!StringUtils.isEmpty(deliverInvoicePageReq.keyWords)) {
            logistics = logisticsRepo.findOne(
                    Expressions.anyOf(
                            QLogistics.logistics.expressNo.eq(deliverInvoicePageReq.keyWords)
                    )
            )
            buyerPartner = partnerRepo.findOne(
                    Expressions.allOf(
                            QPartner.partner.phone.eq(deliverInvoicePageReq.keyWords)
                    )
            )?.id
            if (!logistics) {
                logistics = new Logistics()
                logistics.id = new ObjectId()
            }
            if (!buyerPartner) {
                buyerPartner = new ObjectId()
            }
        }
        if (staff) {
            partnerStaff = null
        }

        Predicate p = Expressions.allOf(
                QDeliverInvoice.deliverInvoice.deleted.in([false, null]),
                QDeliverInvoice.deliverInvoice.brandAppId.eq(brandAppId),
                staff ? QDeliverInvoice.deliverInvoice.deliverPartner.eq(partner) : null,
                partnerStaff ? QDeliverInvoice.deliverInvoice.deliverPartner.eq(partnerStaff.partner) : null,
                Expressions.anyOf(
                        buyerPartner ? QDeliverInvoice.deliverInvoice.buyerPartner.id.eq(buyerPartner) : null,
                        logistics ? QDeliverInvoice.deliverInvoice.logisticses.contains(logistics) : null,
                        deliverInvoicePageReq.keyWords ?
                                QDeliverInvoice.deliverInvoice.seq.eq(deliverInvoicePageReq.keyWords) : null,
                ),
                deliverInvoicePageReq.status ? QDeliverInvoice.deliverInvoice.deliverStatusEnum.eq(
                        DeliverStatusEnum.valueOf(deliverInvoicePageReq.getStatus())) : null,
                QDeliverInvoice.deliverInvoice.order.deleted.in([false, null]),
                startDate ? QDeliverInvoice.deliverInvoice.dateCreated.gt(startDate) : null,
                endDate ? QDeliverInvoice.deliverInvoice.dateCreated.lt(endDate) : null
        )

        Page<DeliverInvoice> page = deliverInvoiceRepo.findAll(p, pageRequest)

        UniResp<UniPageResp<DeliverInvoicePageResp>> uniResp = new UniResp<>()
        uniResp.data = conversionService.convert(page, UniPageResp)
        page.content.each {
            DeliverInvoice deliverInvoice ->
                uniResp.data.content.add(deliverInvoiceConvert.deliverInvoicePageRespConvert(deliverInvoice))
        }
        uniResp.status = 200
        return uniResp
    }

    @Override
    UniResp<String> ship(
            String brandAppId,
            String id,
            DeliverInvoiceShipReq deliverInvoiceShipReq) {
        partnerService.check()
        //获取当前用户id
        String userId = secService.curUserId()
        Assert.isTrue(!StringUtils.isEmpty(userId), "请先登录")

        //判断该员工是否存在
        Staff staff = staffRepo.findOne(
                Expressions.allOf(
                        QStaff.staff.brandAppId.eq(brandAppId),
                        QStaff.staff.deleted.eq(false),
                        QStaff.staff.userId.eq(userId))
        )
//        Assert.notNull(staff, "用户不存在")
//        Assert.isTrue(staff.getBrandAppId().equals(brandAppId),
//                "该员工不属于不该渠道商")

//        Assert.isTrue(staff.getAuthorities().contains(StaffAuthorityEnum.ORDER_R),
//                "该用户无此权限")

        //判断该partnerId是否存在 且parentId是否隶属brandAppId
//        Assert.isTrue(!StringUtils.isEmpty(partnerId), "渠道商id不能为空")
//        Partner partner = partnerRepo.findOne(
//                Expressions.allOf(
//                        QPartner.partner.deleted.in([false, null]),
//                        QPartner.partner.id.eq(partnerId)
//                )
//        )
//        Assert.isTrue(partner.getBrandAppId().equals(brandAppId), "该渠道商不属于该品牌商")

        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()

        Assert.isTrue(!StringUtils.isEmpty(id), "订单id不能为空")
        DeliverInvoice deliverInvoice = deliverInvoiceRepo.findOne(
                Expressions.allOf(
                        QDeliverInvoice.deliverInvoice.deleted.in([false, null]),
                        QDeliverInvoice.deliverInvoice.id.eq(id),
                        Expressions.anyOf(
                                QDeliverInvoice.deliverInvoice.brandAppId.eq(brandAppId),
                                partnerStaff ? QDeliverInvoice.deliverInvoice.deliverPartner.eq(partnerStaff.partner) : null
                        )
                )
        )
        Assert.notNull(deliverInvoice, "没有查到该订单")

        Order order = deliverInvoice.order
        if (order != null && deliverInvoiceShipReq.company != null && deliverInvoiceShipReq.getExpressNo() != null) {
            Logistics logistics = new Logistics()
            logistics.setCompany(LogisticsCompanyEnum.valueOf(deliverInvoiceShipReq.company))
            logistics.setExpressNo(deliverInvoiceShipReq.expressNo)
            logisticsRepo.save(logistics)
            order.setStatus(OrderStatusEnum.UNRECEIVED)
            orderRepo.save(order)
            deliverInvoice.logisticses.add(logistics)
            deliverInvoice.setDeliverStatusEnum(DeliverStatusEnum.UNRECEIVED)
            if (deliverInvoice.deliverPartner.partnerTypeEnum == PartnerTypeEnum.BRAND_COM) {
                deliverInvoice.sourceTypeEnum = SourceTypeEnum.BRAND_DELIVER
            } else {
                deliverInvoice.sourceTypeEnum = SourceTypeEnum.PARTNER_DELIVER
            }
            deliverInvoice.deliverStaff = staff
//            deliverInvoice.setSourceTypeEnum(SourceTypeEnum.valueOf(deliverInvoiceShipReq.sourceType))
            deliverInvoiceRepo.save(deliverInvoice)
            DeliverInvoiceLog deliverInvoiceLog = new DeliverInvoiceLog(
                    deliverInvoice.id, deliverInvoice.order.id,
                    deliverInvoice.deliverStatusEnum, deliverInvoice.deliverStaff?.id,
                    deliverInvoice.deliverStaff?.id, deliverInvoice.dateCreated, deliverInvoice.logisticses[0]?.id
            )
            deliverInvoiceLogRepo.save(deliverInvoiceLog)
        }
        UniResp<String> uniResp = new UniResp<>()
        uniResp.status = 200
        uniResp.data = "发货成功！"
        return uniResp
    }

    @Override
    UniResp<String> getDeliverInvoiceIdByOrderId(String brandAppId,String OrderId) {
        Assert.isTrue(!StringUtils.isEmpty(OrderId), "订单id不能为空")
        partnerService.check()
        Order order = orderRepo.findOne(
                Expressions.allOf(
                        QOrder.order.deleted.in([false, null]),
                        QOrder.order.id.eq(OrderId),
                        QOrder.order.brandAppId.eq(brandAppId)
                )
        )
        DeliverInvoice deliverInvoice = deliverInvoiceRepo.findByOrder(order)
        Assert.notNull(deliverInvoice, "发货信息不存在")
        UniResp<String> uniResp = new UniResp<>()
        uniResp.status = 200
        uniResp.data = deliverInvoice.id
        return uniResp
    }
}
