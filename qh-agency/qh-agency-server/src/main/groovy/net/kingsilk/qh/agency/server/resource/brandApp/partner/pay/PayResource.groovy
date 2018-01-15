package net.kingsilk.qh.agency.server.resource.brandApp.partner.pay

import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.QhAgencyProperties
import net.kingsilk.qh.agency.api.ErrStatus
import net.kingsilk.qh.agency.api.ErrStatusException
import net.kingsilk.qh.agency.api.UniResp
import net.kingsilk.qh.agency.api.brandApp.partner.pay.PayApi
import net.kingsilk.qh.agency.core.OrderStatusEnum
import net.kingsilk.qh.agency.core.PartnerTypeEnum
import net.kingsilk.qh.agency.domain.Order
import net.kingsilk.qh.agency.domain.QPartner
import net.kingsilk.qh.agency.repo.OrderRepo
import net.kingsilk.qh.agency.repo.PartnerRepo
import net.kingsilk.qh.agency.service.SecService
import net.kingsilk.qh.agency.service.UserService
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.stereotype.Component

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.core.Context

@Component(value = "payResource")
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

    @Context
    HttpServletRequest request

    @Autowired
    PartnerRepo partnerRepo

    @Override
    UniResp<String> pay(
            String brandAppId,
            String partnerId,
            String orderId,
            String memo) {
//        String curUserId = secService.curUserId()
        String ip = request.getRemoteAddr()
        Order order = orderRepo.findOne(orderId)
        orderRepo.save(order)
        String appId = userService.getAppId(brandAppId)
        if (!order) {
            return new UniResp<String>(status: 10084, data: "订单不存在")
        }
        if (order.status != OrderStatusEnum.UNPAYED && order.status != OrderStatusEnum.UNCOMMITED) {
            return new UniResp<String>(status: 10083, data: "订单状态错误")
        }
        def oauthUser = userService.getOauthUserInfo(request)
        final String[] openId = new String[1];
        Object users = oauthUser.get("wxUsers");
        if (oauthUser != null &&
                users != null) {
            ArrayList<LinkedHashMap<String, String>> wxUsers = (ArrayList<LinkedHashMap<String, String>>) users;
            wxUsers.each {
                LinkedHashMap<String, String> wxUser ->
                    //相同的用户不同的公众号有不同的opeId
                    if (appId.equals(wxUser.get("appId"))) {
                        openId[0] = wxUser.get("openId")
                    }

            };
        }
        if (StringUtils.isBlank(openId[0])) {
            throw new ErrStatusException(ErrStatus.PARTNER_401, "openId为空")
        }
        def partner = partnerRepo.findOne(
                Expressions.allOf(
                        QPartner.partner.brandAppId.eq(brandAppId),
                        QPartner.partner.deleted.ne(true),
                        QPartner.partner.disabled.ne(true),
                        QPartner.partner.partnerTypeEnum.eq(PartnerTypeEnum.BRAND_COM)
                )
        )
        if (StringUtils.isBlank(order.buyerPartnerId)) {
            throw new ErrStatusException(ErrStatus.PARTNER_500, "找不到买家ID")
        }
        def partner1 = partnerRepo.findOne(
                Expressions.allOf(
                        QPartner.partner.id.eq(order.buyerPartnerId),
                        QPartner.partner.brandAppId.eq(brandAppId),
                        QPartner.partner.deleted.ne(true),
                        QPartner.partner.disabled.ne(true)
                )
        )

        memo = partner1.getPhone()
        def map = [
                totalFee      : order.paymentPrice,
                outTradeNo    : order.id,
                openId        : openId[0],
                bizMemo       : memo,
                itemTitle     : partner.getRealName() + "-" + "生意参谋" + "-" + order.getSeq(),
                itemDetail    : order.getSeq(),
                spBillCreateIp: ip,
//                defaultTradeChannels : "WX_JSSDK",
                notifyUrl     : props.qhAgency.server.url + "/brandApp/" + brandAppId + props.qhAgency.server.payNotifyUrl

        ]
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_JSON);
        reqHeaders.setAccept([MediaType.APPLICATION_JSON])

        HttpEntity<Map> reqEntity = new HttpEntity<Map>(map, reqHeaders);
        def aaa = props.qhPay.wap.api.create
        Map payMap = oClientPayWapRestTemplate.postForObject(props.qhPay.wap.api.create + brandAppId + "/trade", reqEntity, Map)
        println("--------------------支付系统返回值\n${payMap}")
        def resp = payMap.data.get("tradeId")
        return new UniResp<String>(status: 200, data: resp)
    }
}
