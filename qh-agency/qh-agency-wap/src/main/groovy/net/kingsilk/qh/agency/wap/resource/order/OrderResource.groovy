package net.kingsilk.qh.agency.wap.resource.order

import com.querydsl.core.types.dsl.BooleanExpression
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.core.*
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.repo.*
import net.kingsilk.qh.agency.security.BrandAppIdFilter
import net.kingsilk.qh.agency.service.*
import net.kingsilk.qh.agency.wap.api.UniResp
import net.kingsilk.qh.agency.wap.api.addr.dto.AddrModel
import net.kingsilk.qh.agency.wap.api.order.OrderApi
import net.kingsilk.qh.agency.wap.api.order.dto.*
import net.kingsilk.qh.agency.wap.resource.order.convert.AddrConvert
import net.kingsilk.qh.agency.wap.resource.order.convert.OrderConvert
import net.kingsilk.qh.agency.wap.resource.order.convert.SkuConvert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.RequestBody

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

import static com.querydsl.core.types.dsl.Expressions.allOf

/**
 * Created by lit on 17/7/20.
 */
@Api(
        tags = "order",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "订单相关API"
)
@Path("/order")
@Component
class OrderResource implements OrderApi {

    @Autowired
    SecService secService
    @Autowired
    MemberService memberService
    @Autowired
    OrderRepo orderRepo
    @Autowired
    SkuConvert skuConvert

    @Autowired
    AddrConvert addrConvert

    @Autowired
    OrderConvert orderConvert

    @Autowired
    CommonService commonService

    @Autowired
    AddressRepo addressRepo

    @Autowired
    SkuRepo skuRepo

    @Autowired
    MongoTemplate mongoTemplate

    @Autowired
    QhPayService qhPayService

    @Autowired
    CartRepo cartRepo

    @Autowired
    RefundRepo refundRepo

    @Autowired
    OrderLogRepo orderLogRepo

    @Autowired
    SkuService skuService

    @ApiOperation(
            value = "订单列表",
            nickname = "订单列表",
            notes = "订单列表"
    )
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<OrderListResp> list(@BeanParam OrderListReq req) {

        PartnerStaff curMember = memberService.getCurPartnerStaff()
//        String[] tags = curMember?.tags*.code

        BooleanExpression bStatus = QOrder.order.status.ne(OrderStatusEnum.UNCOMMITED)
        if (req.status) {
            bStatus = req.status ? QOrder.order.status.eq(OrderStatusEnum.valueOf(req.status)) : null
        }
        PageRequest pageRequest = new PageRequest(req.curPage, req.pageSize,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))
        Page page = orderRepo.findAll(
                allOf(
                        QOrder.order.partnerStaff.eq(curMember),
                        QOrder.order.deleted.in([false, null]),
                        bStatus
                ),
                pageRequest
        )

        Page<OrderInfoModel> infoPage = page.map({ Order order ->
            OrderInfoModel info = new OrderInfoModel();
            info.id = order.id
            info.seq = order.seq
            info.status = order.status.code
            info.statusDesp = order.status.desp
            info.orderPrice = order.orderPrice / 100
            info.paymentPrice = order.paymentPrice / 100
            info.items = []
            order.orderItems.each { Order.OrderItem item ->
                OrderInfoModel.OrderSkuModel sku = new OrderInfoModel.OrderSkuModel()
                sku = skuConvert.skuConvert(item, curMember?.partner?.partnerTypeEnum?.code)
                if (item.refund) {
                    OrderInfoModel.OrderSkuModel.RefundModel refundModel = new OrderInfoModel.OrderSkuModel.RefundModel()
                    refundModel.status = item.refund.status.code
                    refundModel.statusDesp = item.refund.status.description
                    sku.refund = refundModel
                }
                info.items.add(sku)
            }
            info.address = new AddrModel()
            info.address = addrConvert.addrModelConvert(order.getAddress())
            return info
        })
        OrderListResp resp = new OrderListResp()
        resp.orderInfoModel = infoPage
        return new UniResp<OrderListResp>(status: 200, data: resp)
    }

    @ApiOperation(
            value = "订单详情",
            nickname = "订单详情",
            notes = "订单详情"
    )
    @Path("/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<OrderInfoModel> info(@ApiParam(value = "id", required = true)
                                 @QueryParam(value = "id") String id) {
        def curUserId = secService.curUserId()
        PartnerStaff curMember = memberService.getCurPartnerStaff()
        Order order = orderRepo.findOne(id)
//        Order order = orderRepo.findOneByMemberAndId(curMember, id)
        Assert.notNull(order, "订单错误")

        OrderInfoModel resp = orderConvert.orderInfoModelConvert(order, curMember?.partner?.partnerTypeEnum?.code)
        return new UniResp<OrderInfoModel>(status: 200, data: resp)
    }

    /**
     * 从购物车提交、或者直接购买
     *
     * @return
     */
    @ApiOperation(
            value = "生成订单",
            nickname = "生成订单",
            notes = "生成订单"
    )
    @Path("/check")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> check(OrderCheckReq[] reqs) {
        String curUserId = secService.curUserId()
        PartnerStaff partnerStaff = memberService.getCurPartnerStaff()
        Order order = new Order()
        order.seq = commonService.getDateString()
//        order.userId = curUserId

        order.partnerStaff = partnerStaff
        order.status = OrderStatusEnum.UNCOMMITED
        Address address = addressRepo.findOne(
                allOf(
                        QAddress.address.partnerStaff.eq(partnerStaff),
                        QAddress.address.deleted.in([false, null]),
                        QAddress.address.defaultAddr.eq(true)
                ),

        )
        order.address = address
//        order.company = companyService.getCurCompany()
        order.brandId=BrandAppIdFilter.getBrandAppId()
        order.orderPrice = 0
        order.orderItems = []
        Assert.isTrue(reqs.size() > 0, "商品错误")
        println(reqs)
        reqs.each {
            def orderItem = new Order.OrderItem();
            Sku sku = skuRepo.findOne(it.skuId)
            Assert.notNull(sku, "商品错误")

            orderItem.sku = sku
            orderItem.num = it.num
            orderItem.skuPrice = skuService.getTagPrice(sku)?.price ?: 0
            orderItem.realTotalPrice = it.num * orderItem.skuPrice
            order.orderItems.add(orderItem)
            order.orderPrice += orderItem.realTotalPrice;
        }
        order.paymentPrice = order.orderPrice

        mongoTemplate.save(order)
        return new UniResp<String>(status: 200, data: order.id)
    }

    @ApiOperation(
            value = "提交订单",
            nickname = "提交订单",
            notes = "提交订单"
    )
    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> create(OrderCreateReq req) {
//        def curUserId = secService.curUserId()
        PartnerStaff partnerStaff=memberService.getCurPartnerStaff()
//        Order order = orderRepo.findOneByMemberAndIdndId(curUserId, req.orderId)
        Order order = orderRepo.findOne(req.orderId)
        Assert.notNull(order, "订单错误")
        Assert.isTrue(order.status == OrderStatusEnum.UNCOMMITED, "请勿需重复提交订单！")
        Assert.notNull(order.address, "请选择收货地址")
        order.buyerMemo = req.memo
        order.status = OrderStatusEnum.UNPAYED
        orderRepo.save(order)
        qhPayService.createQhPayByOrder(order)

        // 删除购物车商品
        if (req.from == "CART") {
            println("提交订单 >>>> 从购物车移除商品")
            Cart cart = cartRepo.findOneByPartnerStaffAndBrandId(partnerStaff, order.brandId)
            //便利订单中的商品
            order.orderItems.each { Order.OrderItem orderItem ->
                //从购物车找出该商品，并移除购物出
                Cart.CartItem cartItem = cart.cartItems.find {
                    it.sku.id == orderItem.sku.id
                }
                cart.cartItems.remove(cartItem)
            }
            cartRepo.save(cart)
        }
        return new UniResp<String>(status: 200, data: "提交成功")
    }

    @ApiOperation(
            value = "取消订单",
            nickname = "取消订单",
            notes = "取消订单"
    )
    @Path("/cancel")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> cancel(@RequestBody OrderCancelReq req) {
        def curUserId = secService.curUserId()
        PartnerStaff partnerStaff=memberService.getCurPartnerStaff()
        Order order = orderRepo.findOneByPartnerStaffAndId(partnerStaff, req.id)
        Assert.notNull(order, "订单错误")
        Assert.isTrue(order.status == OrderStatusEnum.UNPAYED, "订单状态错误")
        order.status = OrderStatusEnum.CANCELED
        order.lastStatus = OrderStatusEnum.UNPAYED

//        ////refund相关 //TODO 产品说支付后的不能取消订单
//        refund refund = new refund()
//        refund.dateCreated = new Date()
//        refund.createdBy = curUserId
//        refund.lastModifiedBy = curUserId
//        refund.lastModifiedDate = new Date()
//        refund.setType(RefundTypeEnum.CANCEL)
//        refund.setStatus(RefundStatusEnum.UNCHECKED)
//        refund.setOrder(order)
//        refund.setRefundAmount(order.paymentPrice)
//        refund.setReason(req.reason)
//        mongoTemplate.save(refund)
        mongoTemplate.save(order)
        return new UniResp<String>(status: 200, data: "提交成功")
    }

    @ApiOperation(
            value = "确认收货",
            nickname = "确认收货",
            notes = "确认收货"
    )
    @Path("/confirmReceive")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> confirmReceive(
            @ApiParam(value = "id", required = true)
            @QueryParam(value = "id") String id) {
        def curUserId = secService.curUserId()
        Order order = orderRepo.findOne(id)
        Assert.notNull(order, "订单错误")
        Assert.isTrue(order.status == OrderStatusEnum.UNRECEIVED, "订单状态错误")
        order.status = OrderStatusEnum.FINISHED
        mongoTemplate.save(order)
        return new UniResp<String>(status: 200, data: "SUCCESS")
    }

    @ApiOperation(
            value = "申请退款",
            nickname = "申请退款",
            notes = "申请退款"
    )
    @Path("/refund")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> refund(@RequestBody OrderRefundReq req) {
        def curUserId = secService.curUserId()
        PartnerStaff member=memberService.getCurPartnerStaff()
        Order order = orderRepo.findOneByPartnerStaffAndId(member, req.orderId)
//TODO
        Sku sku = skuRepo.findOne(req.skuId)
        List<Refund> refun = refundRepo.findAll(
                allOf(
                        QRefund.refund.order.eq(order),
                        QRefund.refund.sku.eq(sku),
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
                break;
            }
        }

        //int applyPrice = (int) (Double.parseDouble(req.applyPrice) * 100)
        ////refund相关
        Refund refund = new Refund()
        refund.dateCreated = new Date()
        refund.createdBy = curUserId
        refund.lastModifiedBy = curUserId
        refund.lastModifiedDate = new Date()
        refund.refundType = typeEnum
        refund.applyPrice = req.applyPrice
        refund.status = RefundStatusEnum.UNCHECKED
        refund.order = order
        refund.sku = sku
        refund.refundAmount = req.applyPrice
        refund.reason = req.reason
        refund.memo = req.memo
        mongoTemplate.save(refund)
        if (orderItem) {
            orderItem.refund = refund
            mongoTemplate.save(order)
        }

        return new UniResp<String>(status: 200, data: "提交成功")
    }

    @ApiOperation(
            value = "申请退款用户物流信息",
            nickname = "申请退款用户物流信息",
            notes = "申请退款用户物流信息"
    )
    @Path("/refundLogistics")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> refundLogistics(@RequestBody OrderRefundLogisticsReq req) {
        def curUserId = secService.curUserId()
        PartnerStaff member=memberService.getCurPartnerStaff()
        Order order = orderRepo.findOneByPartnerStaffAndId(member, req.orderId)
        Sku sku = skuRepo.findOne(req.skuId)
        Refund refund = refundRepo.findOne(
                allOf(
                        QRefund.refund.order.eq(order),
                        QRefund.refund.sku.eq(sku),
                        QRefund.refund.refundType.eq(RefundTypeEnum.ITEM),
                        QRefund.refund.status.eq(RefundStatusEnum.WAIT_SENDING),
                        QRefund.refund.sku.deleted.in([false, null])
                )
        )
        //TODO
        refund.logistics = req.logistics
        if (RefundStatusEnum.WAIT_SENDING.equals(refund.status)) {
            refund.status = RefundStatusEnum.WAIT_RECEIVED
            OrderLog orderLog = new OrderLog(refund.order, OrderOperateEnum.USER_DELIVER, refund.order.status, refund.applyPrice, refund.memo)
            orderLogRepo.save(orderLog)
        }
        refund.memo = req.memo
        refundRepo.save(refund)
        return new UniResp<String>(status: 200, data: "提交成功")
    }

    @ApiOperation(
            value = "选择收货地址",
            nickname = "选择收货地址",
            notes = "选择收货地址"
    )
    @Path("/chooseAddr")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> chooseAddr(@RequestBody OrderChooseAddrReq req) {
        def curUserId = secService.curUserId()
        PartnerStaff partnerStaff=memberService.getCurPartnerStaff()
        Order order = orderRepo.findOneByPartnerStaffAndId(partnerStaff, req.orderId)
        Assert.notNull(order, "订单错误")
        Assert.isTrue(order.status == OrderStatusEnum.UNCOMMITED, "订单状态错误")
        Address address = addressRepo.findOneByIdAndPartnerStaff(req.addrId, partnerStaff)
        Assert.notNull(address, "地址错误")
        order.address = address
        mongoTemplate.save(order)
        return new UniResp<String>(status: 200, data: "SUCCESS")
    }

    @ApiOperation(
            value = "获取物流公司列表",
            nickname = "获取物流公司列表",
            notes = "获取物流公司列表"
    )
    @Path("/getLogisticsCompanyEnum")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Map<String, String>> getLogisticsCompanyEnum() {
        Map<String, String> LogisticsCompanyEnumMap = LogisticsCompanyEnum.getMap()
        return new UniResp<Map<String, String>>(status: 200, data: LogisticsCompanyEnumMap)
    }
}
