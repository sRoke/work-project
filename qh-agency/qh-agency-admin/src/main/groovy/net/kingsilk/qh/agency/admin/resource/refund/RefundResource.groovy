package net.kingsilk.qh.agency.admin.resource.refund

import com.querydsl.core.types.dsl.Expressions
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import net.kingsilk.qh.agency.admin.api.UniResp
import net.kingsilk.qh.agency.admin.api.refund.RefundApi
import net.kingsilk.qh.agency.admin.api.refund.dto.RefundHandleReq
import net.kingsilk.qh.agency.admin.api.refund.dto.RefundInfoResp
import net.kingsilk.qh.agency.admin.api.refund.dto.RefundPageReq
import net.kingsilk.qh.agency.admin.api.refund.dto.RefundPageResp
import net.kingsilk.qh.agency.admin.resource.refund.convert.RefundConvert
import net.kingsilk.qh.agency.core.RefundReasonEnum
import net.kingsilk.qh.agency.core.RefundStatusEnum
import net.kingsilk.qh.agency.core.RefundTypeEnum
import net.kingsilk.qh.agency.domain.OrderLog
import net.kingsilk.qh.agency.domain.QRefund
import net.kingsilk.qh.agency.domain.Refund
import net.kingsilk.qh.agency.repo.RefundRepo
import net.kingsilk.qh.agency.service.MemberService
import net.kingsilk.qh.agency.service.OrderLogService
import net.kingsilk.qh.agency.service.RefundService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import org.springframework.util.Assert

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Api(
        tags = "refund",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "售后订单相关API"
)
@Component
@Path("/refund")
class RefundResource implements RefundApi {

    @Autowired
    RefundRepo refundRepo

    @Autowired
    RefundService refundService

    @Autowired
    MemberService memberService

    @Autowired
    OrderLogService orderLogService

    @Autowired
    RefundConvert refundConvert

    @ApiOperation(
            value = "售后订单信息",
            nickname = "售后订单信息",
            notes = "售后信息"
    )
    @Path("/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<RefundInfoResp> info(@PathParam(value = "id") String id) {
        Refund refund = refundRepo.findOne(id)
        Assert.notNull(refund, "售后订单信息不存在")
//        List<PartnerStaff> members = memberService.findByUserIdAndCompanyId(refund.order.userId,BrandIdFilter.companyId)
        List<OrderLog> orderLogs = orderLogService.findByOrderId(refund.order.id)
        return new UniResp<RefundInfoResp>(status: 200, data: refundConvert.refundInfoRespConvert(refund, orderLogs))
    }

    @ApiOperation(
            value = "售后订单流程处理",
            nickname = "售后订单流程处理",
            notes = "售后订单流程处理"
    )
    @Path("/refundHandle")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> refundHandle(RefundHandleReq refundHandleReq) {
        if (refundHandleReq.id != null) {
            refundService.handle(refundHandleReq.id, refundHandleReq.isAgree, refundHandleReq.company, refundHandleReq.expressNo)
        }
        return new UniResp<String>(status: 200, data: "保存成功")
    }

    @ApiOperation(
            value = "售后分页信息",
            nickname = "售后分页信息",
            notes = "售后分页信息"
    )
    @Path("/page")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<RefundPageResp> page(@BeanParam RefundPageReq refundPageReq) {
        PageRequest pageRequest = new PageRequest(refundPageReq.curPage, refundPageReq.pageSize,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))
        Page page = refundRepo.findAll(
                Expressions.allOf(
                        QRefund.refund.deleted.in([false, null]),
                        refundPageReq.status ? QRefund.refund.status.eq(RefundStatusEnum.valueOf(refundPageReq.status)) : QRefund.refund.status.ne(RefundStatusEnum.FINISHED),
                        refundPageReq.reason ? QRefund.refund.reason.eq(refundPageReq.reason) : null,
                        refundPageReq.type ? QRefund.refund.refundType.eq(RefundTypeEnum.valueOf(refundPageReq.type)) : null,
                ),
                pageRequest
        )
        Page<RefundPageResp.RefundInfo> infoPage = page.map({ Refund refund ->
            RefundPageResp.RefundInfo info = new RefundPageResp.RefundInfo();
            info.id = refund.id
            info.type = refund.refundType
            info.status = refund.status
            info.statusDesp = refund.status.desp
            info.orderAmount = refund.order.paymentPrice
            info.refundAmount = refund.refundAmount
            info.orderSeq = refund.order.seq
            info.reason = refund.reason
            info.rejectReason = refund.rejectReason
            info.deliveryTime = refund.deliveryTime
            info.receiveTime = refund.receiveTime
            info.dateCreated = refund.dateCreated
            info.memo = refund.memo
//            List<PartnerStaff> members = memberService.findByUserIdAndCompanyId(refund.order.userId,BrandIdFilter.companyId)
//            if(members.size()>0){
            info.realName = refund.order.partnerStaff.realName
            info.phone = refund.order.partnerStaff.phone
//            }
            if (refund.sku) {
                info.skuInfo = refundConvert.skuInfoConvert(refund.sku)
            }
            if (refund.logistics) {
                info.logisticsInfo = refundConvert.convertLogisticsToResp(refund.logistics)
            }
            return info
        })

        RefundPageResp resp = new RefundPageResp()
        resp.recPage = infoPage
        resp.refundStatusEnumMap = RefundStatusEnum.getMap()
        resp.refundTypeEnumMap = RefundTypeEnum.getMap()
        resp.refundReasonEnumMap = RefundReasonEnum.getMap()
        return new UniResp<RefundPageResp>(status: 200, data: resp)
    }


}
