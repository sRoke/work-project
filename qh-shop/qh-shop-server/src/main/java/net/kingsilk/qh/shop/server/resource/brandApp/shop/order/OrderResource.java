package net.kingsilk.qh.shop.server.resource.brandApp.shop.order;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.order.OrderApi;
import net.kingsilk.qh.shop.api.brandApp.shop.order.dto.OrderInfoResp;
import net.kingsilk.qh.shop.api.brandApp.shop.order.dto.OrderResp;
import net.kingsilk.qh.shop.core.*;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.*;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.order.convert.OrderConvert;
import net.kingsilk.qh.shop.service.service.CommonService;
import net.kingsilk.qh.shop.service.service.PayService;
import net.kingsilk.qh.shop.service.service.UserService;
import net.kingsilk.qh.shop.service.util.DbUtil;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Component
public class OrderResource implements OrderApi {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderLogRepo orderLogRepo;

    @Autowired
    private ShopAccountRepo shopAccountRepo;

    @Autowired
    private QhPayRepo qhPayRepo;

    @Autowired
    private DeliverInvoiceRepo deliverInvoiceRepo;

    @Autowired
    private RefundRepo refundRepo;

    @Autowired
    private DeliverInvoiceLogRepo deliverInvoiceLogRepo;

    @Autowired
    private CommonService commonService;

    @Autowired
    private OrderConvert orderConvert;

    @Autowired
    @Qualifier(value = "mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private PayService payService;

    @Autowired
    private WithdrawRepo withdrawRepo;

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ShopAccountLogRepo shopAccountLogRepo;

    @Autowired
    private SkuStoreRepo skuStoreRepo;

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<UniPageResp<OrderResp>> page(
            String brandAppId,
            String shopId,
            int size,
            int page,
            List<String> sort,
            List<String> status,
            String keyWord) {


        PageRequest pageRequest = new PageRequest(page, size,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")));

//        DbUtil.opIn(QItemPropValue.itemPropValue.id, Lists.newArrayList(spec.getItemPropValueIds())),

        List<OrderStatusEnum> list = new ArrayList<>();
        if (status.size() > 0) {
            status.forEach(s ->
                    list.add(OrderStatusEnum.valueOf(s)));
        }
        Page<Order> respPage = orderRepo.findAll(
                allOf(
                        QOrder.order.deleted.ne(true),
                        QOrder.order.brandAppId.eq(brandAppId),
                        QOrder.order.shopId.eq(shopId),
                        status.size() > 0 ? DbUtil.opIn(QOrder.order.status, list)
                                : QOrder.order.status.ne(OrderStatusEnum.UNCOMMITED),
                        !StringUtils.isEmpty(keyWord) ? QOrder.order.seq.like("%" + keyWord + "%") : null

                ),
                pageRequest
        );


        UniResp<UniPageResp<OrderResp>> uniResp = new UniResp<>();
        Page<OrderResp> resp = respPage.map(order ->
                orderConvert.toOrderResp(order)
        );
        UniPageResp<OrderResp> uniPageResp = conversionService.convert(resp, UniPageResp.class);
        uniPageResp.setContent(resp.getContent());
        uniResp.setStatus(200);
        uniResp.setData(uniPageResp);
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<OrderInfoResp> info(String brandAppId, String shopId, String id) {

        Order order = orderRepo.findOne(id);

        OrderInfoResp orderInfoResp = orderConvert.toOrderInfoResp(order);

        UniResp<OrderInfoResp> uniResp = new UniResp<>();
        uniResp.setData(orderInfoResp);
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> adjustPrice(
            String brandAppId,
            String shopId,
            String id,
            String skuId,
            Integer adjustPrice) {
        if (id != null) {
            Order order = orderRepo.findOne(
                    Expressions.allOf(
                            QOrder.order.id.eq(id),
                            QOrder.order.deleted.ne(true),
                            QOrder.order.shopId.eq(shopId),
                            QOrder.order.status.eq(OrderStatusEnum.UNPAYED)
                    ));
            if (order != null && adjustPrice != null) {

                //单个sku改价
                order.getOrderItems().forEach(
                        orderItem -> {
                            if (skuId.equals(orderItem.getSkuId())) {
                                //改的价格不能高于原价
                                if (adjustPrice > Integer.valueOf(orderItem.getOrderPrice())) {
                                    throw new ErrStatusException(ErrStatus.PARAMNUll, "改的价格不能高于原价");
                                }
                                orderItem.setAdjustedAmount(String.valueOf(Integer.valueOf(orderItem.getOrderPrice()) - adjustPrice));
                                orderItem.setRealPayPrice(String.valueOf((int) Math.rint(adjustPrice / Integer.valueOf(orderItem.getNum()))));
                                orderItem.setAllRealPayPrice(String.valueOf(adjustPrice));
                            }
                        }

                );
                order.setAdjustedAmount(order.getOrderItems().stream().
                        reduce(0, (sum, orderItem) -> sum + Integer.valueOf(orderItem.getAdjustedAmount()), (a, b) -> a + b));
                if (order.getSourceType() == OrderSourceTypeEnum.SINCE) {
                    order.setPaymentAmount(order.getTotalPrice() - order.getAdjustedAmount());
                } else {
                    order.setPaymentAmount(order.getTotalPrice() + order.getFreight() - order.getAdjustedAmount());
                }
                orderRepo.save(order);

                OrderLog orderLog = new OrderLog();
                orderLog.setShopId(shopId);
                orderLog.setOrderId(order.getId());
                orderLog.setType(OperatorTypeEnum.MODIFIED);
                orderLog.setAdjustMoney(String.valueOf(adjustPrice));
                orderLog.setMemo(order.getSellerMemo());
                orderLogRepo.save(orderLog);
            }
        }
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData("success");
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> rejectOrder(String brandAppId, String shopId, String id, String memo) {
        Order order = orderRepo.findOne(
                Expressions.allOf(
                        QOrder.order.id.eq(id),
                        QOrder.order.deleted.ne(true),
                        QOrder.order.shopId.eq(shopId),
                        QOrder.order.brandAppId.eq(brandAppId)
                )
        );
        if (order == null) {
            throw new ErrStatusException(ErrStatus.FINDNULL, "没找到订单");
        }
        order.setSellerMemo(memo);
        UniResp<String> uniResp = new UniResp<>();
        //退款
        if (OrderStatusEnum.UNCONFIRMED.equals(order.getStatus())) {
            order.setStatus(OrderStatusEnum.REJECTED);
            Refund refund = new Refund();
            refund.setRefundStatus(RefundStatusEnum.REJECTING);
            refund.setOrderId(id);
            refund.setBrandAppId(brandAppId);
            refund.setShopId(shopId);
            refund.setReason(memo);
            refund.setSeq(commonService.getDateString());
            refund.setMemberId(order.getMemberId());
            QhPay qhPay = qhPayRepo.findOne(
                    Expressions.allOf(
                            QQhPay.qhPay.id.eq(order.getQhPayId()),
                            QQhPay.qhPay.deleted.ne(true),
                            QQhPay.qhPay.brandAppId.eq(brandAppId),
                            QQhPay.qhPay.orderId.eq(id)
                    )
            );
            if (qhPay == null) {
                throw new ErrStatusException(ErrStatus.FINDNULL, "找不到对用的支付信息");
            }
            refund.setRefundType(RefundTypeEnum.valueOf(qhPay.getPayType().getCode()));
            Integer payAmount = Optional.ofNullable(qhPay.getThirdPayAmount()).orElse(0);
            if ("WX".equals(qhPay.getPayType().getCode())) {
                refund.setWxAmount(payAmount);
            } else if ("ALIPAY".equals(qhPay.getPayType().getCode())) {
                refund.setAliAmount(payAmount);
            }
            refundRepo.save(refund);

            Map payMap = payService.refund(refund, order);
            //貌似支付宝退款没有回调
            Map refundResponse = Optional.ofNullable((Map) payMap.get("alipay_trade_refund_response")).orElse(new HashMap());
            String code = refundResponse == null ? "" : (String) refundResponse.get("code");
            String resultCode = (String) payMap.get("resultCode");
            String returnCode = (String) payMap.get("returnCode");
            if ("10000".equals(code) ||
                    ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode))) {
//                Payment payment = paymentRepo.findOne(
//                        Expressions.allOf(
//                                QPayment.payment.tradeNo.eq(order.getId()),
//                                QPayment.payment.deleted.ne(true),
//                                QPayment.payment.shopId.eq(order.getShopId()),
//                                QPayment.payment.brandAppId.eq(order.getBrandAppId())
//                        )
//                );
//                payment.setPaymentStatus(PaymentStatusEnum.PAYED);
//                payment.setPayType(qhPay.getPayType());

                //订单状态修改
                OrderLog orderLog = new OrderLog();
                orderLog.setOrderId(order.getId());
                orderLog.setShopId(order.getShopId());
                orderLog.setBrandAppId(order.getBrandAppId());

                order.setStatus(OrderStatusEnum.CLOSED);

                orderLog.setStatus(OrderStatusEnum.CLOSED);
                orderLog.setLastStatus(OrderStatusEnum.REJECTED);

                qhPay.setOrderId(order.getId());
                qhPay.setThirdPayAmount(order.getPaymentAmount());
                qhPay.setBrandAppId(order.getBrandAppId());
                qhPay.setRefundAmount(order.getPaymentAmount());
                qhPay.setPayType(qhPay.getPayType());
                qhPay.setPayTime(new Date());

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

                            SkuStore skuStore = skuStoreRepo.findOne(
                                    Expressions.allOf(
                                            QSkuStore.skuStore.brandAppId.eq(brandAppId),
                                            QSkuStore.skuStore.shopId.eq(shopId),
                                            QSkuStore.skuStore.skuId.eq(orderReq.getSkuId())
                                    )
                            );
                            skuStore.setNum(skuStore.getNum() + Integer.valueOf(orderReq.getNum()));
                            skuStore.setSalesVolume(skuStore.getSalesVolume() - Integer.valueOf(orderReq.getNum()));
                            skuStoreRepo.save(skuStore);
                        }
                );
                qhPayRepo.save(qhPay);
                refundRepo.save(refund);
//                paymentRepo.save(payment);
                orderRepo.save(order);

                // TODO 加库存
                // TODO 减销量
                List<Order.OrderItem> skus = order.getOrderItems();

                skus.forEach(
                        sku -> {

                        }
                );

                //账户日志表进行保存
                orderLog.setType(OperatorTypeEnum.PAYED);
                orderLog.setMemo(order.getBuyerMemo());
                orderLogRepo.save(orderLog);
                uniResp.setStatus(HttpStatus.SC_OK);
                uniResp.setData("订单修改完成");
                return uniResp;
            } else {
                refund.setRefundStatus(RefundStatusEnum.REFUNDFILED);
                uniResp.setStatus(Integer.parseInt(Optional.ofNullable((String) refundResponse.get("code")).orElse("40004")));
                uniResp.setData(Optional.ofNullable((String) refundResponse.get("sub_msg")).orElse("稍后再退"));
                return uniResp;
            }
        } else {
            OrderLog orderLog = new OrderLog();
            orderLog.setOrderId(order.getId());
            orderLog.setShopId(order.getShopId());
            orderLog.setBrandAppId(order.getBrandAppId());
            orderLog.setStatus(OrderStatusEnum.CANCELED);
            orderLog.setLastStatus(OrderStatusEnum.CANCELING);
            orderLog.setType(OperatorTypeEnum.PAYED);
            orderLog.setMemo(memo);
            orderLogRepo.save(orderLog);
        }
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData("订单修改完成");
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> confirmOrder(String brandAppId, String shopId, String id) {
        Order order = orderRepo.findOne(id);

        QhPay qhPay = qhPayRepo.findOne(
                Expressions.allOf(
                        QQhPay.qhPay.orderId.eq(id),
                        QQhPay.qhPay.brandAppId.eq(brandAppId)
                )
        );

        Shop shop = shopRepo.findOne(
                Expressions.allOf(
                        QShop.shop.id.eq(shopId),
                        QShop.shop.brandAppId.eq(brandAppId)
                )
        );

        //先把钱放到余额
        ShopAccount temAcount = new ShopAccount();
        temAcount.setBalance(0);
        temAcount.setBrandAppId(brandAppId);
        temAcount.setShopId(shopId);
        temAcount.setFreezeBalance(0);
        temAcount.setNoCashBalance(0);
        ShopAccount.AliAccount aliAccount = new ShopAccount.AliAccount();
        aliAccount.setDefault(false);
        temAcount.setAliAccount(aliAccount);
        temAcount.setTotalBalance(0);
        temAcount.setTotalWithdraw(0);
        ShopAccount shopAccount = Optional.ofNullable(
                shopAccountRepo.findOne(
                        Expressions.allOf(
                                QShopAccount.shopAccount.brandAppId.eq(brandAppId),
                                QShopAccount.shopAccount.shopId.eq(shopId),
                                QShopAccount.shopAccount.deleted.ne(true)
                        )
                ))
                .orElse(temAcount);
        if (shopAccount.getAliAccount() == null) {
            shopAccount.setAliAccount(aliAccount);
        }
        if (PayTypeEnum.ALIPAY.equals(qhPay.getPayType()) && !StringUtils.hasText(shopAccount.getAliAccount().getAccount())) {
            throw new ErrStatusException(ErrStatus.FINDNULL, "你还没有绑定支付宝，请到提现管理绑定");
        }
        shopAccount.setBalance(shopAccount.getBalance() + order.getPaymentAmount());
        shopAccount.setTotalBalance(shopAccount.getTotalBalance() + order.getPaymentAmount());
        ShopAccountLog shopAccountLog = conversionService.convert(shopAccount, ShopAccountLog.class);
        shopAccountLog.setType(AccountChangeTypeEnum.SELL);
        shopAccountLog.setChangeAmount(order.getPaymentAmount());
        shopAccountLog.setMoneyChangeEnum(MoneyChangeEnum.BALANCE);
        shopAccountRepo.save(shopAccount);
        shopAccountLogRepo.save(shopAccountLog);


        //判断是否是自提订单 改变订单状态
        if (order.getSourceType().getCode().equals(OrderSourceTypeEnum.SINCE.getCode())) {
            order.setStatus(OrderStatusEnum.SINCEING);
        } else {
            order.setStatus(OrderStatusEnum.UNSHIPPED);
            DeliverInvoice deliverInvoice = new DeliverInvoice();
            deliverInvoice.setShopId(shopId);
            deliverInvoice.setOrderId(order.getId());
            deliverInvoice.setBrandAppId(brandAppId);
            deliverInvoice.setSeq(commonService.getDateString());
            deliverInvoice.setDateCreated(new Date());
            deliverInvoice.setDeliverStatus(DeliverStatusEnum.UNSHIPPED);
            DeliverInvoice.OrderAddress orderAddress = deliverInvoice.new OrderAddress();
            orderAddress.setAdc(order.getAddr().getAdc());
            orderAddress.setPhone(order.getAddr().getPhone());
            orderAddress.setReceiver(order.getAddr().getReceiver());
            orderAddress.setStreet(order.getAddr().getStreet());
            deliverInvoice = deliverInvoiceRepo.save(deliverInvoice);

            //发货单日志
            DeliverInvoiceLog deliverInvoiceLog = new DeliverInvoiceLog();
            deliverInvoiceLog.setDeliverInvoiceId(deliverInvoice.getId());
            deliverInvoiceLog.setOrderId(order.getId());
            deliverInvoiceLog.setBrandAppId(brandAppId);
            deliverInvoiceLog.setShopId(shopId);
            deliverInvoiceLog.setDeliverStatusEnum(DeliverStatusEnum.UNSHIPPED);
            deliverInvoiceLog.setDateCreated(deliverInvoice.getDateCreated());
            deliverInvoiceLogRepo.save(deliverInvoiceLog);
        }

        orderRepo.save(order);

        //生成订单日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(order.getId());
        orderLog.setShopId(shopId);
        orderLog.setStatus(OrderStatusEnum.UNSHIPPED);
        orderLog.setLastStatus(OrderStatusEnum.UNCONFIRMED);
        orderLog.setType(OperatorTypeEnum.CONFIRM_PAYMENT);
        orderLog.setMemo(order.getBuyerMemo());
        orderLogRepo.save(orderLog);


        UniResp<String> uniResp = new UniResp<>();
        //统计今日已经提现总额度
        Calendar todayStartCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        todayStartCalendar.set(todayStartCalendar.get(Calendar.YEAR), todayStartCalendar.get(Calendar.MONTH), todayStartCalendar.get(Calendar.DATE) - 1, 24, 0, 0);
        ArrayList<Withdraw> withdraws = Lists.newArrayList(withdrawRepo.findAll(
                Expressions.allOf(
                        QWithdraw.withdraw.shopId.eq(shopId),
                        QWithdraw.withdraw.brandAppId.eq(brandAppId),
                        QWithdraw.withdraw.deleted.ne(true),
                        QWithdraw.withdraw.status.eq(WithdrawStatusEnum.FINISH),
                        QWithdraw.withdraw.applyTime.after(todayStartCalendar.getTime())
                )
        ));
        int sum = withdraws.stream().mapToInt(Withdraw::getRefundFee).sum();

        Withdraw withdraw = new Withdraw();
        withdraw.setBrandAppId(brandAppId);
        withdraw.setShopId(shopId);
        withdraw.setInitType(WithdrawInitTypeEnum.INIT_BY_SYS);
        withdraw.setRefundFee(order.getPaymentAmount());
        withdraw.setStatus(WithdrawStatusEnum.CASHING);
        withdraw.setSeq(commonService.getDateString());
        withdraw.setApplyTime(new Date());

        StringBuffer sb = new StringBuffer(shop.getName());
        String userId = memberRepo.findOne(order.getMemberId()).getUserId();
        String phone = (String) userService.getOauthUserInfoByUserId(userId).get("phone");

        withdraw.setMemo(sb.append("-").append("门店系统").append("-").append(phone).append("-").append(order.getSeq()).toString());

        //企业打款
        if (order.getPaymentAmount() < 100) {
            uniResp.setData("单次收入低于1元，收入已经转到您的账户，请明天再来提取");
            uniResp.setStatus(10011);
        } else {
            if (PayTypeEnum.WX.equals(qhPay.getPayType())) {
                //微信提现的情况
                if (sum > 2000000) {
                    uniResp.setData("今天的收入已经超过20000元了，收入已经转到您的账户，请明天再来提取");
                    uniResp.setStatus(10011);
                } else if (withdraws.stream()
                        .filter(it ->
                                PayTypeEnum.WX.equals(it.getPayType())
                        )
                        .collect(Collectors.toList()).size() >= 10) {
                    uniResp.setData("微信提现不能超过10次");
                    uniResp.setStatus(10011);
                } else {
                    withdraw.setPayType(PayTypeEnum.WX);
                    payService.withdrawCash(withdraw, shopAccount);
                }

            } else {
                //支付宝提现的情况
                if (sum > 1000000000) {
                    uniResp.setData("今天的收入已经超过100万元了，收入已经转到您的账户，请明天再来提取");
                    uniResp.setStatus(10011);
                } else if (order.getPaymentAmount() > 5000000) {
                    uniResp.setData("单笔收入最多5万元，请稍后再来提现");
                    uniResp.setStatus(10011);
                } else {
                    withdraw.setPayType(PayTypeEnum.ALIPAY);
                    payService.withdrawCash(withdraw, shopAccount);
                }
            }
        }


        //企业打款成功 店铺余额减少 （企业打款失败 余额不变）
        if (WithdrawStatusEnum.FINISH.equals(withdraw.getStatus())) {

            shopAccount.setBalance(shopAccount.getBalance() - order.getPaymentAmount());
            shopAccount.setTotalBalance(shopAccount.getTotalBalance() - order.getPaymentAmount());
            shopAccountRepo.save(shopAccount);

            ShopAccountLog accountLogWith = conversionService.convert(shopAccount, ShopAccountLog.class);
            accountLogWith.setType(PayTypeEnum.WX.equals(withdraw.getPayType()) ? AccountChangeTypeEnum.WITHDRAW_WX : AccountChangeTypeEnum.WITHDRAW_ALIPAY);
            accountLogWith.setChangeAmount(withdraw.getRefundFee());
            accountLogWith.setMoneyChangeEnum(MoneyChangeEnum.BALANCE);
            shopAccountLogRepo.save(accountLogWith);

            uniResp.setStatus(200);
            uniResp.setData("success");
        } else {
            if (!StringUtils.hasText(uniResp.getData())) {
                uniResp.setData("该次收入已经转到您的余额中，请及时提现");
                uniResp.setStatus(10013);
            }
        }
        return uniResp;
    }

    @Override
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<Map<String, String>> getLogisticsCompanyEnum(String brandAppId, String shopId) {
        Map<String, String> LogisticsCompanyEnumMap = LogisticsCompanyEnum.getMap();
        UniResp<Map<String, String>> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(LogisticsCompanyEnumMap);
        return uniResp;
    }
}
