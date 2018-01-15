package net.kingsilk.qh.agency.admin.resource.deliverInvoice

import com.querydsl.core.types.dsl.Expressions
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.admin.api.UniResp
import net.kingsilk.qh.agency.admin.api.deliverInvoice.DeliverInvoiceApi
import net.kingsilk.qh.agency.admin.api.deliverInvoice.dto.DeliverInvoiceInfoResp
import net.kingsilk.qh.agency.admin.api.deliverInvoice.dto.DeliverInvoicePageReq
import net.kingsilk.qh.agency.admin.api.deliverInvoice.dto.DeliverInvoicePageResp
import net.kingsilk.qh.agency.admin.api.deliverInvoice.dto.DeliverInvoiceShipReq
import net.kingsilk.qh.agency.admin.api.order.dto.OrderInfoResp
import net.kingsilk.qh.agency.admin.api.order.dto.OrderPageResp
import net.kingsilk.qh.agency.admin.resource.order.convert.OrderConvert
import net.kingsilk.qh.agency.core.DeliverStatusEnum
import net.kingsilk.qh.agency.core.LogisticsCompanyEnum
import net.kingsilk.qh.agency.core.OrderStatusEnum
import net.kingsilk.qh.agency.core.SourceTypeEnum
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.repo.DeliverInvoiceRepo
import net.kingsilk.qh.agency.repo.LogisticsRepo
import net.kingsilk.qh.agency.repo.OrderRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

/**
 *
 */
@Api(
        tags = "deliverInvoice",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "发货单相关API"
)
@Path("/deliverInvoice")
@Component
public class DeliverInvoiceResource implements DeliverInvoiceApi{

    @Autowired
    OrderRepo orderRepo
    @Autowired
    LogisticsRepo logisticsRepo

    @Autowired
    DeliverInvoiceRepo deliverInvoiceRepo

    @Autowired
    OrderConvert orderConvert

    //----------------------------发货单信息-------------------------------//
    @ApiOperation(
            value = "发货单信息",
            nickname = "发货单信息",
            notes = "发货单信息")
    @ApiParam(value = "id")
    @Path("/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<DeliverInvoiceInfoResp> info(@QueryParam(value = "id") String id) {
        if (id == null) {
            return new UniResp(status: 10027, message: "订单信息不存在")
        }
        DeliverInvoice deliverInvoice = deliverInvoiceRepo.findOne(id);
        if (!deliverInvoice) {
            return new UniResp(status: 10027, message: "订单信息不存在")
        }
//        List<PartnerStaff> members = memberService.findByUserIdAndBrandId(order.userId,null)
//        List<Refund> refunds = refundService.search(null, id).asList()
//        OrderInfoResp resp = new OrderInfoResp()
        DeliverInvoiceInfoResp infoResp=new DeliverInvoiceInfoResp()
        infoResp.order=orderConvert.orderInfoRespConvert(deliverInvoice.order, null)
        infoResp.seq=deliverInvoice.seq
        infoResp.id=deliverInvoice.id
        infoResp.deliverStatusEnum=deliverInvoice.deliverStatusEnum
        infoResp.statusDesp = deliverInvoice.deliverStatusEnum.desp
        return new UniResp<DeliverInvoiceInfoResp>(status: 200, data: infoResp)
    }

    //----------------------------发货单分页信息-------------------------------//
    @ApiOperation(
            value = "发货单分页信息",
            nickname = "发货单分页信息",
            notes = "发货单分页信息"
    )
    @Path("/page")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<DeliverInvoicePageResp> page(@BeanParam DeliverInvoicePageReq deliverInvoicePageReq) {
        PageRequest pageRequest = new PageRequest(
                deliverInvoicePageReq.curPage,
                deliverInvoicePageReq.pageSize,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))

//        Date startDate = DateUtil.dateToStartDateTime(orderPageReq.startDate)
//        Date endDate = DateUtil.dateToEndDateTime(orderPageReq.endDate)

        Page page = deliverInvoiceRepo.findAll(
                Expressions.allOf(
                        QOrder.order.deleted.in([false, null]),
//                        startDate ? QOrder.order.dateCreated.gt(startDate) : null,
//                        endDate ? QOrder.order.dateCreated.lt(endDate) : null,
                        deliverInvoicePageReq.status ? QDeliverInvoice.deliverInvoice.deliverStatusEnum.eq(DeliverStatusEnum.valueOf(deliverInvoicePageReq.status)) : null,
                        Expressions.anyOf(
                                deliverInvoicePageReq.keyWords ? QDeliverInvoice.deliverInvoice.seq.eq(deliverInvoicePageReq.keyWords) : null,
                                deliverInvoicePageReq.keyWords ? QDeliverInvoice.deliverInvoice.address.phone.eq(deliverInvoicePageReq.keyWords) : null,
                        )
                ),
                pageRequest
        )
        Page<DeliverInvoicePageResp> infoPage = page.map({ DeliverInvoice deliverInvoice ->
            Order order =deliverInvoice.order
            DeliverInvoicePageResp info = new DeliverInvoicePageResp();
            info.id = deliverInvoice.id
            info.seq = deliverInvoice.seq
            info.orderSeq=deliverInvoice.order.seq
            info.addressInfo=orderConvert.addressInfoConvert(deliverInvoice.address)
//            List<PartnerStaff> members = memberService.findByUserIdAndCompanyId(order.userId, BrandIdFilter.companyId)
//            if (members.size() > 0) {
//            info.realName = deliverInvoice.partnerStaff?.realName
//            info.phone = order.partnerStaff?.phone
//            }
            info.status = deliverInvoice.deliverStatusEnum
            info.statusDesp = deliverInvoice.deliverStatusEnum.desp
            info.dateCreated = deliverInvoice.dateCreated
            for (Order.OrderItem orderItem : deliverInvoice.order.orderItems) {
                def type = order.partnerStaff.partner.partnerTypeEnum.code
                OrderInfoResp.OrderItemInfo orderItemInfo = orderConvert.orderItemInfoConvert(orderItem,type)
                info.orderItems.add(orderItemInfo)
            }
            return info
        });
//
        OrderPageResp resp = new OrderPageResp()
        resp.recPage = infoPage
//        resp.orderStatusEnumMap = OrderStatusEnum.getMap()
//        resp.dataCountMap = orderService.getDataCountMap()
        return new UniResp<DeliverInvoicePageResp>(status: 200, data: resp)
    }

    //----------------------------发货-------------------------------//
    @ApiOperation(
            value = "发货",
            nickname = "发货",
            notes = "发货"
    )
    @Path("/ship")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> ship(DeliverInvoiceShipReq deliverInvoiceShipReq) {

        if (deliverInvoiceShipReq.id != null) {
            DeliverInvoice deliverInvoice = deliverInvoiceRepo.findOne(deliverInvoiceShipReq.id)
            Order order = deliverInvoice.order
            if (order != null && deliverInvoiceShipReq.company != null && deliverInvoiceShipReq.expressNo != null) {
                Logistics logistic = new Logistics()
                logistic.company = LogisticsCompanyEnum.valueOf(deliverInvoiceShipReq.company)
                logistic.expressNo = deliverInvoiceShipReq.expressNo
                logisticsRepo.save(logistic)
                /**
                 * 订单相关信息变更
                 */
                order.logisticses.add(logistic)
                order.status = OrderStatusEnum.UNRECEIVED
                orderRepo.save(order)
                /**
                 * 发货单相关信息变更
                 */
                deliverInvoice.logisticses.add(logistic)
                deliverInvoice.deliverStatusEnum = DeliverStatusEnum.UNRECEIVED
                deliverInvoice.sourceTypeEnum = SourceTypeEnum.valueOf(deliverInvoiceShipReq.sourceType)
                deliverInvoiceRepo.save(deliverInvoice)

            }
        }
        return new UniResp<String>(status: 200, data: "发货成功")
    }

    @ApiOperation(
            value = "根据订单Id获取发货单信息",
            nickname = "根据订单Id获取发货单信息",
            notes = "根据订单Id获取发货单信息"
    )
    @Path("/getDeliverInvoiceIdByOrderId")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    UniResp<String> getDeliverInvoiceIdByOrderId(@QueryParam(value = "id") String id) {
        Order order=orderRepo.findOne(id)
        DeliverInvoice deliverInvoice=deliverInvoiceRepo.findByOrder(order)
        if (!deliverInvoice){
            return new UniResp<String>(status: 301, data: "发货单信息不存在！")
        }
        return new UniResp<String>(status: 200, data: deliverInvoice.id)
    }
}
