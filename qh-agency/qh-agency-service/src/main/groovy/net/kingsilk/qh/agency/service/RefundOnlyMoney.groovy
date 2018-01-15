package net.kingsilk.qh.agency.service

import net.kingsilk.qh.agency.QhAgencyProperties
import net.kingsilk.qh.agency.core.RefundStatusEnum
import net.kingsilk.qh.agency.core.RefundTypeEnum
import net.kingsilk.qh.agency.domain.Refund
import net.kingsilk.qh.agency.repo.RefundRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.stereotype.Service
import org.springframework.util.Assert

@Service
public class RefundOnlyMoney {

    @Autowired
    private RefundRepo refundRepo;

    @Autowired
    private OAuth2RestTemplate oClientPayWapRestTemplate;

    @Autowired
    private QhAgencyProperties props;

    public String refund(String refundId, String brandAppId) {
        Refund refund = refundRepo.findOne(refundId);
        Assert.notNull(refund, "退款单错误");
        Assert.isTrue(refund.getStatus() == RefundStatusEnum.UNPAYED, "退款单状态错误");
        Assert.isTrue(refund.getRefundType() == RefundTypeEnum.MONEY_ONLY, "退款单类型错误");
        def map = [
                refundFee: (refund.getAliAmount() + refund.wxAmount),
                orderId  : refund.getOrder().getId(),
                notifyUrl: props.qhAgency.server.url+"/brandApp/" + brandAppId + props.qhAgency.server.payNotifyUrl
        ]
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_JSON);
        reqHeaders.setAccept([MediaType.APPLICATION_JSON])

        HttpEntity<String> reqEntity = new HttpEntity<Map>(map, reqHeaders);
        def aaa = props.qhPay.wap.api.refund + brandAppId + "/refund"
        oClientPayWapRestTemplate.postForObject(aaa, reqEntity, Map)
//        println("--------------------支付系统返回值\n${payMap}")
        return reqEntity.getBody()
    }
}
