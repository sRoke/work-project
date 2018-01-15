package net.kingsilk.qh.shop.service.service;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.UserGetResp;
import net.kingsilk.qh.platform.api.brandApp.BrandAppApi;
import net.kingsilk.qh.platform.api.brandApp.dto.BrandAppResp;
import net.kingsilk.qh.shop.core.OperatorTypeEnum;
import net.kingsilk.qh.shop.core.OrderStatusEnum;
import net.kingsilk.qh.shop.core.PayTypeEnum;
import net.kingsilk.qh.shop.core.RefundStatusEnum;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.*;
import net.kingsilk.qh.shop.service.QhShopProperties;
import net.kingsilk.wx4j.broker.api.wxCom.mp.at.WxComMpAtApi;
import net.kingsilk.wx4j.broker.api.wxCom.mp.user.GetResp;
import net.kingsilk.wx4j.broker.api.wxCom.mp.user.WxComMpUserApi;
import net.kingsilk.wx4j.client.mp.api.user.InfoResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private QhPayRepo qhPayRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private RefundRepo refundRepo;

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private OrderLogRepo orderLogRepo;

    @Autowired
    private MsgService msgService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private BrandAppApi brandAppApi;

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private WxComMpAtApi wxComMpAtApi;

    @Autowired
    private WxComMpUserApi wxComMpUserApi;

    @Autowired
    private net.kingsilk.wx4j.client.mp.api.user.UserApi wxUserApi;

    @Autowired
    private UserApi userApi;

    @Autowired
    private QhShopProperties qhShopProperties;

    /**
     * 支付成功处理
     *
     * @param order
     */
    public void paySuccessHandle(Order order, PayTypeEnum payType, Date payTime) {
        if (!OrderStatusEnum.UNPAYED.equals(order.getStatus())) {
            return;
        }

        QhPay qhPay = new QhPay();
        qhPay.setOrderId(order.getId());
        qhPay.setThirdPayAmount(order.getPaymentAmount());
        qhPay.setBrandAppId(order.getBrandAppId());
        qhPay.setRefundAmount(order.getPaymentAmount());
        qhPay.setPayType(payType);
        qhPay.setPayTime(payTime);
        qhPayRepo.save(qhPay);
//
//        Payment payment = paymentRepo.findOne(
//                Expressions.allOf(
//                        QPayment.payment.tradeNo.eq(order.getId()),
//                        QPayment.payment.deleted.ne(true),
//                        QPayment.payment.brandAppId.eq(order.getBrandAppId())
//                )
//        );
//        payment.setPaymentStatus(PaymentStatusEnum.PAYED);
//        payment.setPayType(payType);
//        paymentRepo.save(payment);

        order.setQhPayId(qhPay.getId());
        order.setStatus(OrderStatusEnum.UNCONFIRMED);
        orderRepo.save(order);
        //账户日志表进行保存
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(order.getId());
        orderLog.setShopId(order.getShopId());
        orderLog.setBrandAppId(order.getBrandAppId());
        orderLog.setStatus(OrderStatusEnum.UNCONFIRMED);
        orderLog.setLastStatus(OrderStatusEnum.UNPAYED);
        orderLog.setType(OperatorTypeEnum.PAYED);
        orderLog.setMemo(order.getBuyerMemo());
        orderLogRepo.save(orderLog);

        net.kingsilk.qh.platform.api.UniResp<BrandAppResp> info = brandAppApi.info(order.getBrandAppId());
        String wxComAppId = info.getData().getWxComAppId();
        String wxMpAppId = info.getData().getWxMpId();

        Shop shop = shopRepo.findOne(
                Expressions.allOf(
                        QShop.shop.brandAppId.eq(order.getBrandAppId()),
                        QShop.shop.id.eq(order.getShopId()),
                        QShop.shop.deleted.ne(true)
                )
        );

        net.kingsilk.qh.oauth.api.UniResp<UserGetResp> infoByPhone = userApi.getInfoByPhone(shop.getPhone());

        List<UserGetResp.WxUser> openIdList = infoByPhone.getData().getWxUsers().stream().filter(wxUser ->
                wxMpAppId.equals(wxUser.getAppId())
        ).collect(Collectors.toList());

        String openId = openIdList.get(0).getOpenId();

        String curUserId = memberRepo.findOne(order.getMemberId()).getUserId();
        //获取本地accesstoken
        net.kingsilk.wx4j.broker.api.UniResp<net.kingsilk.wx4j.broker.api.wxCom.mp.at.GetResp> uniRes =
                wxComMpAtApi.get(wxComAppId, wxMpAppId);
        net.kingsilk.qh.oauth.api.UniResp<UserGetResp> oauthResp = userApi.get(curUserId);
        String phone = oauthResp.getData().getPhone();
        //获取微信头像
        String openIdcur = oauthResp.getData().getWxUsers().stream().filter(wxUser ->
                wxMpAppId.equals(wxUser.getAppId())
        ).findFirst().orElse(new UserGetResp.WxUser()).getOpenId();

        String nicName = null;
        if (StringUtils.hasText(openId)) {
            //查看用户是否关注微信公众号
            InfoResp infoResp = wxUserApi.info(uniRes.getData().getAccessToken(), openIdcur, "zh_CN");
            if (StringUtils.hasText(infoResp.getNickName())) {
                nicName = infoResp.getNickName();
            } else {
                net.kingsilk.wx4j.broker.api.UniResp<GetResp> wxResp
                        = wxComMpUserApi.get(wxComAppId, wxMpAppId, openIdcur);
                nicName = wxResp.getData().getNickName();
            }
        }
        if (StringUtils.hasText(nicName)) {
            nicName = "\"" + nicName + "\"";
        } else {
            nicName = "\"" + phone + "\"";
        }
        String sharUrl = qhShopProperties.getQhShop().getHtml().getUrl() + "brandApp/" + order.getBrandAppId() + "/store/" + order.getShopId() + "/order/orderDetail?id=" + order.getId();

//        谭号（158581）支付了一笔订单，等你确认接单，请查看。
        String content = new StringBuilder(nicName).append("支付了一笔订单，等你确认接单\n").append("<a href='").append(sharUrl).append("'>点击查看</a>").toString();
        msgService.sendMsg(wxComAppId, wxMpAppId, openId, content);

    }

    public void refundHandle(Refund refund, Order order) {
        //订单不为取消或者拒绝，且退款单不为待退款
        if (!RefundStatusEnum.REFUNDING.equals(refund.getRefundStatus())) {
            return;
        }

//        Payment payment = paymentRepo.findOne(
//                Expressions.allOf(
//                        QPayment.payment.tradeNo.eq(order.getId()),
//                        QPayment.payment.deleted.ne(true),
//                        QPayment.payment.brandAppId.eq(order.getBrandAppId())
//                )
//        );
//        payment.setPaymentStatus(PaymentStatusEnum.PAYED);
//        payment.setPayType(payType);

        //订单状态修改
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(order.getId());
        orderLog.setShopId(order.getShopId());
        orderLog.setBrandAppId(order.getBrandAppId());
        if (OrderStatusEnum.CANCELING.equals(order.getStatus())) {

            order.setStatus(OrderStatusEnum.CANCELED);
            orderLog.setStatus(OrderStatusEnum.CANCELED);
            orderLog.setLastStatus(OrderStatusEnum.CANCELING);
        } else if (OrderStatusEnum.REJECTED.equals(order.getStatus())) {

            order.setStatus(OrderStatusEnum.CLOSED);
            orderLog.setStatus(OrderStatusEnum.CLOSED);
            orderLog.setLastStatus(OrderStatusEnum.REJECTED);
        } else {
            return;
        }
        refund.setOrderId(order.getId());
        refund.setShopId(order.getShopId());
        refund.setBrandAppId(order.getBrandAppId());
        refund.setRefundStatus(RefundStatusEnum.FINISHED);
        refund.setMemberId(order.getMemberId());
        refund.setSeq(commonService.getDateString());
        refund.setRefundMoney(order.getPaymentAmount());
        //TODO 退款 销售价
        order.getOrderItems().forEach(orderReq ->
                {
                    Refund.Sku refundSku = new Refund.Sku();
                    refundSku.setApplyedNum(Integer.parseInt(orderReq.getNum()));
                    Order.OrderItem item = order.getOrderItems().stream()
                            .filter(it ->
                                    orderReq.getSkuId().equals(it.getSkuId())
                            )
                            .findFirst()
                            .orElse(new Order().new OrderItem());
                    refundSku.setOrderPrice(Integer.parseInt(item.getOrderPrice()));
                    refundSku.setSkuId(orderReq.getSkuId());
                    refundSku.setSkuPrice(Integer.parseInt(item.getSkuPrice()));
                    refund.getSkus().add(refundSku);
                }
        );
//        qhPayRepo.save(qhPay);
        refundRepo.save(refund);
//        paymentRepo.save(payment);
        orderRepo.save(order);

        //账户日志表进行保存
        orderLog.setType(OperatorTypeEnum.PAYED);
        orderLog.setMemo(order.getBuyerMemo());
        orderLogRepo.save(orderLog);
    }

}
