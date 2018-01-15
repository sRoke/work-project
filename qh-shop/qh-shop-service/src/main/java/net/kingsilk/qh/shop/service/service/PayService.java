package net.kingsilk.qh.shop.service.service;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.core.OrderStatusEnum;
import net.kingsilk.qh.shop.core.PayTypeEnum;
import net.kingsilk.qh.shop.core.PaymentStatusEnum;
import net.kingsilk.qh.shop.core.WithdrawStatusEnum;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.*;
import net.kingsilk.qh.shop.service.QhShopProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class PayService {


    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private QhShopProperties prop;

    @Autowired
    @Qualifier("oauthRestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private RefundRepo refundRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private WithdrawRepo withdrawRepo;

    //支付
    public UniResp<String> pay(Order order, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String appId = userService.getAppId(order.getBrandAppId());
        if (order.getStatus() != OrderStatusEnum.UNPAYED && order.getStatus() != OrderStatusEnum.UNCOMMITED) {
            throw new ErrStatusException(ErrStatus.FINDNULL, "订单状态错误");
        }
        Map oauthUser = userService.getOauthUserInfo(request);

//        Payment payment = new Payment();
//        payment.setTotalFee(order.getPaymentAmount());
//        payment.setApplyTime(new Date());
//        payment.setShopId(order.getShopId());
//        payment.setSeq(commonService.getDateString());
//        payment.setBrandAppId(order.getBrandAppId());
//        payment.setPaymentStatus(PaymentStatusEnum.UNPAYED);
//        payment.setReason("支付");
//        payment.setTradeNo(order.getId());
//        payment.setDateCreated(new Date());
//        paymentRepo.save(payment);

        //获得openId
//        StringBuilder openId = new StringBuilder();
//        ArrayList<LinkedHashMap<String, String>> wxUserList = (ArrayList<LinkedHashMap<String, String>>) oauthUser.get("wxUsers");
//        Optional.ofNullable(wxUserList).ifPresent(wxUsers ->
//
//                wxUsers.stream().filter(wxUser ->
//                        appId.equals(wxUser.get("appId"))
//                ).findFirst().ifPresent(it ->
//                        openId.append(it.get("openId"))
//                )
//
//        );

        String openId=commonService.getOpenId(request,appId);

//        if (StringUtils.isBlank(openId)) {
//            throw new ErrStatusException(ErrStatus.PARTNER_401, "openId为空");
//        }

        //拼接title
        Shop shop = shopRepo.findOne(
                Expressions.allOf(
                        QShop.shop.brandAppId.eq(order.getBrandAppId()),
                        QShop.shop.id.eq(order.getShopId()),
                        QShop.shop.deleted.ne(true)
                )
        );
        StringBuffer sb = new StringBuffer();
        String name = shop.getName();
        Optional.ofNullable(name).ifPresent(it ->
                sb.append(it).append("-")
        );
        sb.append("门店系统").append("-").append(order.getSeq());
        String phone = (String) oauthUser.get("phone");
        Map<String, String> map = new LinkedHashMap();
        map.put("totalFee", String.valueOf(order.getPaymentAmount()));
        map.put("outTradeNo", order.getId());
        map.put("openId", openId);
        map.put("bizMemo", order.getBuyerMemo());
        map.put("itemTitle", sb.toString());
        map.put("itemDetail", order.getSeq());
//        map.put("spBillCreateIp", ip);
        ///brandApp/{brandAppId}/shop/{shopId}/notify/pay
        map.put("notifyUrl", prop.getQhShop().getUrl() + "brandApp/" + order.getBrandAppId() + "/shop/" + order.getShopId() + "/notify/pay");

        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> type = new ArrayList<>();
        type.add(MediaType.APPLICATION_JSON);
        reqHeaders.setAccept(type);

        HttpEntity<Map> reqEntity = new HttpEntity<>(map, reqHeaders);
        String url = prop.getQhPay().getWap().getApi().getUrl() + order.getBrandAppId() + "/trade";
        UniResp<Map> payMap;
        try {
            payMap = restTemplate.postForObject(url, reqEntity, UniResp.class);
        } catch (Exception e) {
            throw new ErrStatusException(ErrStatus.HTTPERR, "支付失败，请稍后再试");
        }
        System.out.println("--------------------支付系统返回值\n${payMap}");
        Map<String, String> data = (Map) payMap.getData();
        String resp = data.get("tradeId");
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData(resp);
        return uniResp;
    }

    //退款
    public Map refund(Refund refund, Order order) {
//        Assert.notNull(refund, "退款单错误");
//        Assert.isTrue(RefundStatusEnum.REFUNDING.equals(refund.getRefundStatus()), "退款单状态错误");

//        Payment payment = new Payment();
//        payment.setTotalFee(order.getPaymentAmount());
//        payment.setApplyTime(new Date());
//        payment.setShopId(order.getShopId());
//        payment.setSeq(commonService.getDateString());
//        payment.setBrandAppId(order.getBrandAppId());
//        payment.setPaymentStatus(PaymentStatusEnum.UNPAYED);
//        payment.setReason("退款");
//        payment.setTradeNo(order.getId());
//        payment.setDateCreated(new Date());
//        paymentRepo.save(payment);

        Map<String, String> map = new LinkedHashMap<>();
        map.put("refundFee", String.valueOf(refund.getAliAmount() + refund.getWxAmount()));
        map.put("orderId", order.getId());
        ///brandApp/{brandAppId}/shop/{shopId}/notify/pay
        map.put("notifyUrl", prop.getQhShop().getUrl() + "brandApp/" + order.getBrandAppId() + "/shop/" + order.getShopId() + "/notify/pay");

        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> type = new ArrayList<>();
        type.add(MediaType.APPLICATION_JSON);
        reqHeaders.setAccept(type);

        HttpEntity<Map> reqEntity = new HttpEntity<>(map, reqHeaders);
        String url = prop.getQhPay().getWap().getApi().getUrl() + order.getBrandAppId() + "/refund";
        UniResp<Map> payMap;
        try {
            payMap = restTemplate.postForObject(url, reqEntity, UniResp.class);
        } catch (Exception e) {
            throw new ErrStatusException(ErrStatus.HTTPERR, "取消失败，请稍后再试");
        }
        System.out.println("--------------------支付系统返回值\n${payMap}");
        orderRepo.save(order);
        return Optional.ofNullable(payMap.getData()).orElse(new HashMap());
    }

    //提现
    public Map withdrawCash(Withdraw withdrawCash, ShopAccount shopAccount) {

        if (withdrawCash.getPayType() == null) {
            return null;
        }

        Shop shop = shopRepo.findOne(
                Expressions.allOf(
                        QShop.shop.brandAppId.eq(withdrawCash.getBrandAppId()),
                        QShop.shop.id.eq(withdrawCash.getShopId()),
                        QShop.shop.deleted.ne(true)
                )
        );
        String phone = shop.getPhone();
        String appId = userService.getAppId(withdrawCash.getBrandAppId());
        Map oauthUser = userService.getOauthUserInfoByPhone(phone);

        Payment payment = new Payment();
        payment.setTotalFee(withdrawCash.getRefundFee());
        payment.setApplyTime(new Date());
        payment.setShopId(withdrawCash.getShopId());
        payment.setSeq(commonService.getDateString());
        payment.setBrandAppId(withdrawCash.getBrandAppId());
        payment.setPayType(withdrawCash.getPayType());
        payment.setPaymentStatus(PaymentStatusEnum.UNPAYED);
        payment.setReason("提现");
        payment.setDateCreated(new Date());
        paymentRepo.save(payment);

        withdrawCash.setPaymentId(payment.getId());
        withdrawRepo.save(withdrawCash);

        StringBuilder openId = new StringBuilder();
        ArrayList<LinkedHashMap<String, String>> wxUserList = (ArrayList<LinkedHashMap<String, String>>) oauthUser.get("wxUsers");
        Optional.ofNullable(wxUserList).ifPresent(wxUsers ->

                wxUsers.stream().filter(wxUser ->
                        appId.equals(wxUser.get("appId"))
                ).findFirst().ifPresent(it ->
                        openId.append(it.get("openId"))
                )

        );
//        String openId=commonService.getOpenId(request,appId);
        if (StringUtils.isBlank(openId)) {
            throw new ErrStatusException(ErrStatus.UNLOGIN, "openId为空");
        }

        Map<String, String> map = new LinkedHashMap();
        //微信支付宝提现的参数
        map.put("amount", String.valueOf(payment.getTotalFee()));
        map.put("outTradeNo", payment.getId());

        map.put("openId", openId.toString());
        map.put("transferChannel", withdrawCash.getPayType().getCode());
        map.put("desc", withdrawCash.getMemo());
        map.put("checkName", "false");
        map.put("remark",  withdrawCash.getMemo());
//        map.put("userRealName", shopAccount.getAliAccount().getRealName());
        map.put("payeeAccount", shopAccount.getAliAccount() == null ? "" : shopAccount.getAliAccount().getAccount());

        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> type = new ArrayList<>();
        type.add(MediaType.ALL);
        reqHeaders.setAccept(type);
        HttpEntity<Map> reqEntity = new HttpEntity<>(map, reqHeaders);
        String aaa = prop.getQhPay().getWap().getApi().getUrl() + withdrawCash.getBrandAppId() + "/transfer";
        UniResp<Map> payMap;
        try {
            payMap = restTemplate.postForObject(aaa, reqEntity, UniResp.class);
        } catch (Exception e) {
            throw new ErrStatusException(ErrStatus.HTTPERR, "提现失败，请稍后再试");
        }
        Map payMapData = Optional.ofNullable(payMap.getData()).orElse(new HashMap());
        System.out.println("--------------------支付系统返回值\n" + payMap);
        //对提现结果进行处理
        if (PayTypeEnum.WX.equals(withdrawCash.getPayType())) {
            if ("SUCCESS".equals(payMapData.get("return_code"))
                    && "SUCCESS".equals(payMapData.get("result_code"))) {
                payment.setPaymentStatus(PaymentStatusEnum.PAYED);
                paymentRepo.save(payment);
                withdrawCash.setStatus(WithdrawStatusEnum.FINISH);
                withdrawRepo.save(withdrawCash);
                //TODO 明细表记录提现成功

                return payMap.getData();
            } else {
                payment.setReason((String) payMapData.get("err_code_des"));
                payment.setPaymentStatus(PaymentStatusEnum.CANCELD);
                paymentRepo.save(payment);
                withdrawCash.setStatus(WithdrawStatusEnum.FAIL);
                withdrawRepo.save(withdrawCash);

                return Optional.ofNullable(payMap.getData()).orElse(new HashMap());
            }
        } else {
            //支付宝的提现结果处理
            if ("10000".equals(payMapData.get("code"))) {

                withdrawCash.setStatus(WithdrawStatusEnum.FINISH);
                withdrawRepo.save(withdrawCash);
                payment.setPaymentStatus(PaymentStatusEnum.PAYED);
                paymentRepo.save(payment);

                return Optional.ofNullable(payMap.getData()).orElse(new HashMap());
            } else {
                payment.setReason((String) payMapData.get("msg"));
                payment.setPaymentStatus(PaymentStatusEnum.CANCELD);
                paymentRepo.save(payment);
                withdrawCash.setStatus(WithdrawStatusEnum.FAIL);
                withdrawRepo.save(withdrawCash);

                return Optional.ofNullable(payMap.getData()).orElse(new HashMap());
            }
        }
    }

}
