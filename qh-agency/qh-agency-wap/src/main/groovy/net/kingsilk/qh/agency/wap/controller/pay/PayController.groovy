package net.kingsilk.qh.agency.wap.controller.pay

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import net.kingsilk.qh.agency.QhAgencyProperties
import net.kingsilk.qh.agency.wap.api.UniResp
import net.kingsilk.qh.agency.core.OrderStatusEnum
import net.kingsilk.qh.agency.domain.Order
import net.kingsilk.qh.agency.repo.OrderRepo
import net.kingsilk.qh.agency.repo.SkuRepo
import net.kingsilk.qh.agency.service.SecService
import net.kingsilk.qh.agency.service.UserService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest

@RestController()
@RequestMapping("/api/pay")
@Api(
        tags = "pay",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        protocols = "http,https",
        description = "支付相关接口"
)
class PayController {

    @Autowired
    QhAgencyProperties props

    @Autowired
    SecService secService

    @Autowired
    UserService userService

    @Autowired
    OrderRepo orderRepo

    @Autowired
    SkuRepo skuRepo

    @Autowired
    OAuth2RestTemplate oClientPayWapRestTemplate

    @RequestMapping(path = "/order",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "对订单进行支付",
            nickname = "对订单进行支付",
            notes = "对订单进行支付"
    )
    @ApiResponses([
            @ApiResponse(
                    code = 200,
                    message = "正常结果")
    ])
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('MEMBER')")
    UniResp<OrderPayResp> pay(OrderPayReq req, HttpServletRequest request) {
        String curUserId = secService.curUserId()
        Order order = orderRepo.findOneByUserIdAndId(curUserId, req.orderId)
        Assert.notNull(order, "订单错误")
        Assert.isTrue(order.status == OrderStatusEnum.UNPAYED, "订单状态错误")

        def oauthUser = userService.getOauthUserInfo(request)

        def map = [
                total_fee: order.paymentPrice,
                orderId  : order.id,
                openId   : oauthUser?.openId,
                notifyUrl: props.qhAgency.wap.payNotifyUrl
        ]
        HttpEntity<Map> reqEntity = new HttpEntity<Map>(map);

        Map payMap = oClientPayWapRestTemplate.postForObject(props.qhPay.wap.api.pay_create, reqEntity, Map)
        println("--------------------支付系统返回值\n${payMap}")

        OrderPayResp resp = new OrderPayResp(payId: payMap.data)
        return new UniResp<OrderPayResp>(status: 200, data: resp)
    }
}
