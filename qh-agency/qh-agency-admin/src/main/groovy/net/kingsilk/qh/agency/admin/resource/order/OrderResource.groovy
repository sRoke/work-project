package net.kingsilk.qh.agency.admin.resource.order

import com.querydsl.core.types.dsl.Expressions
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.admin.api.UniResp
import net.kingsilk.qh.agency.admin.api.order.OrderApi
import net.kingsilk.qh.agency.admin.api.order.dto.*
import net.kingsilk.qh.agency.admin.resource.order.convert.OrderConvert
import net.kingsilk.qh.agency.admin.service.ExcelWrite
import net.kingsilk.qh.agency.core.DeliverStatusEnum
import net.kingsilk.qh.agency.core.OrderStatusEnum
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.repo.*
import net.kingsilk.qh.agency.service.*
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
        tags = "order",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "订单管理相关API"
)
@Path("/order")
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
    MemberService memberService


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
    AddressRepo addressRepo

    //----------------------------订单信息-------------------------------//
    @ApiOperation(
            value = "订单信息",
            nickname = "订单信息",
            notes = "订单信息")
    @ApiParam(value = "id")
    @Path("/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<OrderInfoResp> info(@QueryParam(value = "id") String id) {
        if (id == null) {
            return new UniResp(status: 10027, message: "订单信息不存在")
        }
        Order order = orderRepo.findOne(id);
        if (!order) {
            return new UniResp(status: 10027, message: "订单信息不存在")
        }
//        List<PartnerStaff> members = memberService.findByUserIdAndBrandId(order.userId,null)
        List<Refund> refunds = refundService.search(null, id).asList()
        OrderInfoResp resp = new OrderInfoResp()
        return new UniResp<OrderInfoResp>(status: 200, data: orderConvert.orderInfoRespConvert(order, refunds))
    }

    //----------------------------更改价格-------------------------------//
    @ApiOperation(
            value = "更改价格",
            nickname = "更改价格",
            notes = "更改价格"
    )
    @Path("/adjustPrice")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> adjustPrice(@QueryParam(value = "id") String id,
                                 @QueryParam(value = "adjustPrice") Double adjustPrice) {
        if (id != null) {
            Order order = orderRepo.findOne(id);
            if (order != null && adjustPrice != null) {
                order.paymentPrice = (adjustPrice * 100).intValue()
                Integer adjust = order.orderPrice - order.paymentPrice;
                Integer allPrice = 0;
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
                orderRepo.save(order);
            }
        }
        return new UniResp<String>(status: 200, data: "保存成功")
    }

//    //----------------------------发货-------------------------------//
//    @ApiOperation(
//            value = "发货",
//            nickname = "发货",
//            notes = "发货"
//    )
//    @Path("/ship")
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<String> ship(@BeanParam OrderShipReq orderShipReq);

    //----------------------------修改收货地址-------------------------------//
    @ApiOperation(
            value = "修改收货地址",
            nickname = "修改收货地址",
            notes = "修改收货地址"
    )
    @Path("/updateAddress")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> updateAddress(OrderAddressReq orderAddressReq) {
        if (orderAddressReq.orderId != null) {
            Order order = orderRepo.findOne(orderAddressReq.orderId);
            if (order != null) {
                Address orderAddress = order.address;
                if (order.address == null) {
                    orderAddress = new Address()
                }
                Adc adc = adcRepo.findOneByNo(orderAddressReq.countyNo)
                orderAddress.receiver = orderAddressReq.receiver
                orderAddress.phone = orderAddressReq.phone
                orderAddress.street = orderAddressReq.street
                orderAddress.adc = adc
                addressRepo.save(orderAddress)
                order.address = orderAddress;
                orderRepo.save(order);
                DeliverInvoice deliverInvoice = deliverInvoiceRepo.findByOrder(order)
                if (deliverInvoice) {
                    deliverInvoice.address = orderAddress;
                    deliverInvoiceRepo.save(deliverInvoice);
                }
            }

        }
        return new UniResp<String>(status: 200, data: "保存成功")
    }

    //----------------------------订单分页信息-------------------------------//
    @ApiOperation(
            value = "订单分页信息",
            nickname = "订单分页信息",
            notes = "订单分页信息"
    )
    @Path("/page")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<OrderPageResp> page(@BeanParam OrderPageReq orderPageReq) {
        PageRequest pageRequest = new PageRequest(
                orderPageReq.curPage,
                orderPageReq.pageSize,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))

//        Date startDate = DateUtil.dateToStartDateTime(orderPageReq.startDate)
//        Date endDate = DateUtil.dateToEndDateTime(orderPageReq.endDate)

        Page page = orderRepo.findAll(
                Expressions.allOf(
                        QOrder.order.deleted.in([false, null]),
//                        startDate ? QOrder.order.dateCreated.gt(startDate) : null,
//                        endDate ? QOrder.order.dateCreated.lt(endDate) : null,
                        orderPageReq.status ? QOrder.order.status.eq(OrderStatusEnum.valueOf(orderPageReq.status)) : QOrder.order.status.ne(OrderStatusEnum.UNCOMMITED),
                        Expressions.anyOf(
                                orderPageReq.keyWord ? QOrder.order.seq.contains(orderPageReq.keyWord) : null,
                                orderPageReq.keyWord ? QOrder.order.address.phone.eq(orderPageReq.keyWord) : null,
                                orderPageReq.keyWord ? QOrder.order.address.phone.eq(orderPageReq.keyWord) : null,
                        )
                ),
                pageRequest
        )
        Page<OrderPageResp.OrderInfo> infoPage = page.map({ Order order ->
            OrderPageResp.OrderInfo info = new OrderPageResp.OrderInfo();
            info.id = order.id
            info.seq = order.seq
//            List<PartnerStaff> members = memberService.findByUserIdAndCompanyId(order.userId, BrandIdFilter.companyId)
//            if (members.size() > 0) {
//            info.realName = order.partnerStaff?.realName
//            info.phone = order.partnerStaff?.phone
//            }
            info.orderPrice = order.orderPrice/100
            info.paymentPrice = order.paymentPrice/100
            info.status = order.status
            info.statusDesp = order.status.desp
            info.dateCreated = order.dateCreated
            for (Order.OrderItem orderItem : order.orderItems) {
                def type = order.partnerStaff.partner.partnerTypeEnum.code
                OrderInfoResp.OrderItemInfo orderItemInfo = orderConvert.orderItemInfoConvert(orderItem,type)
                info.orderItems.add(orderItemInfo)
            }
            return info
        });

        OrderPageResp resp = new OrderPageResp()
        resp.recPage = infoPage
        resp.orderStatusEnumMap = OrderStatusEnum.getMap()
        resp.dataCountMap = orderService.getDataCountMap()
        return new UniResp<OrderPageResp>(status: 200, data: resp)
    }

    //----------------------------搜索adc地址-------------------------------//
    @ApiOperation(
            value = "搜索adc地址",
            nickname = "搜索adc地址",
            notes = "搜索adc地址"
    )
    @Path("/queryAdc")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<AddrResp> queryAdc(@BeanParam AddrReq req) {
        Adc adc = null
        if (req.typeNo) {
            adc = adcRepo.findOneByNo(req.typeNo)
        }
        List<Adc> adcList = adcRepo.findAllByParent(adc)

        AddrResp resp = orderConvert.addrRespConvert(adc?.name, req.level, adcList)
        return new UniResp<AddrResp>(status: 200, data: resp)
    }

    //----------------------------导出订单-------------------------------//
//    @ApiOperation(
//            value = "导出订单",
//            nickname = "导出订单",
//            notes = "导出订单"
//    )
//    @Path("/export")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    void exportExcel(@PathParam(value = "id") String id, @Context HttpServletResponse response) throws Exception;

    //----------------------------取消订单-------------------------------//
    /**
     * 取消订单时：
     * 如果已支付->会生成一条退款记录
     * 如果未支付->改变订单状态为拒绝接单
     * 如果处于代发货已生成发货单->1. 改变发货单状态为取消状态，2. 生成一条退款记录
     */
    @ApiOperation(
            value = "取消订单",
            nickname = "取消订单",
            notes = "取消订单"
    )
    @Path("/cancelOrder")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> cancelOrder(@QueryParam(value = "id") String id,
                                @QueryParam(value = "memo") String memo) {
        Order order=orderRepo.findOne(id)
        order.status=OrderStatusEnum.CANCELED
//        QhPay qhPay=qhPayRepo.findOne(id)
        //TODO 生成相应的退款单
        orderRepo.save(order)
        return new UniResp<String>(status: 200, data: "操作成功")
    }
//----------------------------拒绝订单-------------------------------//
    @ApiOperation(
            value = "拒绝订单",
            nickname = "拒绝订单",
            notes = "拒绝订单"
    )
    @Path("/rejectOrder")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> rejectOrder(@QueryParam(value = "id") String id,
                                @QueryParam(value = "memo") String memo) {
        Order order=orderRepo.findOne(id)
        order.status=OrderStatusEnum.REJECTED
//        QhPay qhPay=qhPayRepo.findOne(id)
        //TODO 生成相应的退款单
        orderRepo.save(order)
        return new UniResp<String>(status: 200, data: "操作成功")
    }

    //----------------------------卖家备注-------------------------------//
    @ApiOperation(
            value = "卖家备注",
            nickname = "卖家备注",
            notes = "卖家备注"
    )
    @Path("/sellerMemo")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> sellerMemo(@QueryParam(value = "id") String id,
                               @QueryParam(value = "memo") String memo) {
        Order order = orderRepo.findOne(id)
        if (order.memo) {
            order.memo = order.memo + "\n" + new Date().toLocaleString() + ":" + memo
        } else {
            order.memo = new Date().toLocaleString() + ":" + memo
        }

        orderRepo.save(order)
        return new UniResp<String>(status: 200, data: "操作成功")
    }

    //----------------------------确认接单-------------------------------//
    /**
     * 确认接单后会生成相应的发货单
     */
    @ApiOperation(
            value = "确认接单",
            nickname = "确认接单",
            notes = "确认接单"
    )
    @Path("/confirmOrder")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> confirmOrder(@QueryParam(value = "id") String id) {
        Order order = orderRepo.findOne(id)
//        if (confirm){
//            order.status = OrderStatusEnum.FINANCE_CONFIRM
//        }else {
//            //TODO 生成一条退款记录
//        }

//        order.memo = order.memo + memo
        order.status = OrderStatusEnum.UNSHIPPED
        DeliverInvoice deliverInvoice = new DeliverInvoice()
        deliverInvoice.order = order
        deliverInvoice.partner = order.partner
        deliverInvoice.brandId = order.partner?.brandId
        deliverInvoice.address = order.address
        deliverInvoice.seq = commonService.dateString
        deliverInvoice.deliverStatusEnum = DeliverStatusEnum.UNSHIPPED
        deliverInvoice.sourceTypeEnum = null
        deliverInvoice.dateCreated = new Date()
        deliverInvoiceRepo.save(deliverInvoice)
        orderRepo.save(order)
        return new UniResp<String>(status: 200, data: "操作成功")
    }

    //----------------------------财务确认订单-------------------------------//
    /**
     * 财务确认订单后会生成相应的发货单
     */
    @ApiOperation(
            value = "财务确认订单",
            nickname = "财务确认订单",
            notes = "财务确认订单"
    )
    @Path("/financeConfirm")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> financeConfirm(@QueryParam(value = "id") String id,
                                   @QueryParam(value = "confirm") Boolean confirm,
                                   @QueryParam(value = "memo") String memo) {
        Order order = orderRepo.findOne(id)
        if (confirm) {
            order.status = OrderStatusEnum.UNSHIPPED
            DeliverInvoice deliverInvoice = new DeliverInvoice()
            deliverInvoice.order = order
            deliverInvoice.partner = order.partner
            deliverInvoice.brandId = order.partner.brandId
            deliverInvoice.address = order.address
            deliverInvoice.seq = commonService.dateString
            deliverInvoice.deliverStatusEnum = DeliverStatusEnum.UNSHIPPED
            deliverInvoice.sourceTypeEnum = null
            deliverInvoice.dateCreated = new Date()
            deliverInvoiceRepo.save(deliverInvoice)
        } else {
            order.status = OrderStatusEnum.UNCONFIRMED
        }

        order.memo = order.memo + memo
        orderRepo.save(order)
        return new UniResp<String>(status: 200, data: "操作成功")
    }

}
