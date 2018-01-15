package net.kingsilk.qh.agency.wap.resource.pay

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import net.kingsilk.qh.agency.QhAgencyProperties
import net.kingsilk.qh.agency.core.OrderStatusEnum
import net.kingsilk.qh.agency.domain.Order
import net.kingsilk.qh.agency.repo.OrderRepo
import net.kingsilk.qh.agency.service.SecService
import net.kingsilk.qh.agency.service.UserService
import net.kingsilk.qh.agency.wap.api.UniResp
import net.kingsilk.qh.agency.wap.api.pay.PayApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.stereotype.Component
import org.springframework.util.Assert

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType

/**
 * Created by lit on 17/7/25.
 */
@Api(
        tags = "pay",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "支付相关接口"
)
@Path("/pay")
@Component
public class PayResource implements PayApi {

    @Autowired
    OAuth2RestTemplate oClientPayWapRestTemplate

    @Autowired
    SecService secService
    @Autowired
    OrderRepo orderRepo

    @Autowired
    UserService userService

    @Autowired
    QhAgencyProperties props

    @ApiOperation(
            value = "对订单进行支付",
            nickname = "对订单进行支付",
            notes = "对订单进行支付"
    )
    @Path("/order")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public UniResp<String> pay(@QueryParam(value = "orderId") String orderId,
                               @QueryParam(value = "memo") String memo,
                               @Context HttpServletRequest request) {
        String curUserId = secService.curUserId()
        Order order = orderRepo.findOne(orderId)
        order.buyerMemo=memo
        orderRepo.save(order)
        Assert.notNull(order, "订单错误")
        Assert.isTrue(order.status == OrderStatusEnum.UNPAYED, "订单状态错误")

        def oauthUser = userService.getOauthUserInfo(request)

        def map = [
                total_fee: order.paymentPrice,
                orderId  : order.id,
                openId   : oauthUser?.openId,
                notifyUrl: props.qhAgency.wap.payNotifyUrl
        ]
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        reqHeaders.setAccept([org.springframework.http.MediaType.APPLICATION_JSON])

        HttpEntity<Map> reqEntity = new HttpEntity<Map>(map, reqHeaders);

        Map payMap = oClientPayWapRestTemplate.postForObject(props.qhPay.wap.api.pay_create, reqEntity, Map)
        println("--------------------支付系统返回值\n${payMap}")
        def resp = payMap.data
        return new UniResp<String>(status: 200, data: resp)
    }
}
