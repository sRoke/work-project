package net.kingsilk.qh.agency.admin.controller.Refund

import com.querydsl.core.types.dsl.Expressions
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.admin.api.UniResp
import net.kingsilk.qh.agency.core.RefundReasonEnum
import net.kingsilk.qh.agency.core.RefundStatusEnum
import net.kingsilk.qh.agency.core.RefundTypeEnum
import net.kingsilk.qh.agency.domain.OrderLog
import net.kingsilk.qh.agency.domain.PartnerStaff
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
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("/api/refund")
@Api( // 用在类上，用于设置默认值
        tags = "refund",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        protocols = "http,https",
        description = "售后订单相关API"
)
class RefundController {

    @Autowired
    RefundRepo refundRepo

    @Autowired
    RefundService refundService

    @Autowired
    MemberService memberService

    @Autowired
    OrderLogService orderLogService

    @RequestMapping(path = "/info",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "售后订单信息",
            nickname = "售后订单信息",
            notes = "售后信息"
    )
    @ApiParam(value = "id")
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('REFUND_R')")
    UniResp<RefundInfoResp> info(String id) {
        Refund refund = refundRepo.findOne(id)
        Assert.notNull(refund, "售后订单信息不存在")
        List<PartnerStaff> members = memberService.findByUserIdAndCompanyId(refund.order.userId,BrandIdFilter.companyId)
        List<OrderLog> orderLogs = orderLogService.findByOrderId(refund.order.id)
        return new UniResp<RefundInfoResp>(status: 200, data: new RefundInfoResp().convertRefundToResp(refund,members,orderLogs))
    }

    @RequestMapping(path = "/refundHandle",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "售后订单流程处理",
            nickname = "售后订单流程处理",
            notes = "售后订单流程处理"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('REFUND_C')")
    UniResp<String> refundHandle(@RequestBody RefundHandleReq refundHandleReq) {
        if (refundHandleReq.id != null) {
            refundService.handle(refundHandleReq.id, refundHandleReq.isAgree,refundHandleReq.company,refundHandleReq.expressNo)
        }
        return new UniResp<String>(status: 200, data: "保存成功")
    }

    @RequestMapping(path = "/page",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "售后分页信息",
            nickname = "售后分页信息",
            notes = "售后分页信息"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('REFUND_R')")
    UniResp<RefundPageResp> page(RefundPageReq refundPageReq) {
        PageRequest pageRequest = new PageRequest(refundPageReq.curPage, refundPageReq.pageSize,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))
        Page page = refundRepo.findAll(
                Expressions.allOf(
                        QRefund.refund.deleted.in([false, null]),
                        refundPageReq.status ? QRefund.refund.status.eq(refundPageReq.status) : QRefund.refund.status.ne(RefundStatusEnum.FINISHED),
                        refundPageReq.reason ? QRefund.refund.reason.eq(refundPageReq.reason) : null,
                        refundPageReq.type ? QRefund.refund.refundType.eq(refundPageReq.type) : null,
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
            info.memo =refund.memo
            List<PartnerStaff> members = memberService.findByUserIdAndCompanyId(refund.order.userId,BrandIdFilter.companyId)
            if(members.size()>0){
                info.realName = members[0].realName
                info.phone = members[0].phone
            }
            if(refund.sku){
                info.skuInfo = new RefundPageResp.SkuInfo().convertSkuToResp(refund.sku)
            }
            if (refund.logistics) {
                info.logisticsInfo = new RefundPageResp.LogisticsInfo().convertLogisticsToResp(refund.logistics)
            }
            return info
        });

        RefundPageResp resp = new RefundPageResp()
        resp.recPage = infoPage
        resp.refundStatusEnumMap = RefundStatusEnum.getMap()
        resp.refundTypeEnumMap = RefundTypeEnum.getMap()
        resp.refundReasonEnumMap = RefundReasonEnum.getMap()
        return new UniResp<RefundPageResp<Page<RefundPageResp.RefundInfo>>>(status: 200, data: resp)
    }

}
