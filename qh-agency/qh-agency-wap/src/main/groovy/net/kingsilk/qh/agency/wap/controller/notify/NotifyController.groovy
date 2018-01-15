package net.kingsilk.qh.agency.wap.controller.notify

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import net.kingsilk.qh.agency.domain.Order
import net.kingsilk.qh.agency.repo.OrderRepo
import net.kingsilk.qh.agency.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

/**
 * Created by zcw on 3/30/17.
 * 调用外部系统回调相关接口
 */
@RestController()
@RequestMapping("/api/notify")
@Api(
        tags = "notify",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        protocols = "http,https",
        description = "调用外部系统回调相关接口"
)
class NotifyController {

    @Autowired
    OrderRepo orderRepo

    @Autowired
    OrderService orderService

    @RequestMapping(path = "/qhPay",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "支付系统回调",
            nickname = "支付系统回调",
            notes = "支付系统回调"
    )
    @ApiResponses([
            @ApiResponse(
                    code = 200,
                    message = "正常结果")
    ])
    @ResponseBody
    @PreAuthorize("permitAll()")
    NotifyQhPayResp qhPay(@RequestBody NotifyQhPayReq req) {
        println(req)
        Order order = orderRepo.findOne(req.bizOrderNo)

        if (!order) {
            return new NotifyQhPayResp(code: "FAILED")
        }
        if (req.type == "PAY") {
            orderService.paySuccessHandle(order,req.payType,req.payTime)
        } else if (req.type == "REFUND") {
            orderService.refundHandle()
        }
        return new NotifyQhPayResp(code: "SUCCESS")
    }
}
