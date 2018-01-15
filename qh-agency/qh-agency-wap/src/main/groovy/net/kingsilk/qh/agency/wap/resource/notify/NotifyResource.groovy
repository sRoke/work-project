package net.kingsilk.qh.agency.wap.resource.notify

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import net.kingsilk.qh.agency.domain.Order
import net.kingsilk.qh.agency.repo.OrderRepo
import net.kingsilk.qh.agency.service.OrderService
import net.kingsilk.qh.agency.wap.api.notify.dto.NotifyQhPayReq
import net.kingsilk.qh.agency.wap.controller.notify.NotifyQhPayResp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * Created by zcw on 3/30/17.
 * 调用外部系统回调相关接口
 */

@Api(
        tags = "notify",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "调用外部系统回调相关接口"
)
@Path("/notify")
@Component
public class NotifyResource {

    @Autowired
    OrderRepo orderRepo

    @Autowired
    OrderService orderService

    @ApiOperation(
            value = "支付系统回调",
            nickname = "支付系统回调",
            notes = "支付系统回调"
    )
    @Path("/qhPay")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    String qhPay(NotifyQhPayReq req) {
        Order order = orderRepo.findOne(req.bizOrderNo)

        if (!order) {
            return new NotifyQhPayResp(code: "FAILED")
        }
        if (req.type == "PAY") {
            orderService.paySuccessHandle(order, req.payType, req.payTime)
        } else if (req.type == "REFUND") {
            orderService.refundHandle()
        }
        return new NotifyQhPayResp(code: "SUCCESS")
    }
}