package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.order;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.UserGetResp;
import net.kingsilk.qh.oauth.api.user.addr.AddrGetResp;
import net.kingsilk.qh.platform.api.brandApp.BrandAppApi;
import net.kingsilk.qh.platform.api.brandApp.dto.BrandAppResp;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.order.OrderApi;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.order.dto.*;
import net.kingsilk.qh.shop.core.*;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.*;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.member.MemberResource;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.order.convert.AddrConvert;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.order.convert.OrderConvert;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.order.convert.OrderCreateConvert;
import net.kingsilk.qh.shop.service.QhShopProperties;
import net.kingsilk.qh.shop.service.service.*;
import net.kingsilk.qh.shop.service.util.DbUtil;
import net.kingsilk.qh.shop.service.util.ParamUtils;
import net.kingsilk.wx4j.broker.api.wxCom.mp.at.WxComMpAtApi;
import net.kingsilk.wx4j.broker.api.wxCom.mp.user.GetResp;
import net.kingsilk.wx4j.broker.api.wxCom.mp.user.WxComMpUserApi;
import net.kingsilk.wx4j.client.mp.api.user.InfoResp;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Component("mallOrderResource")
public class OrderResource implements OrderApi {

    @Autowired
    private SkuRepo skuRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private OrderCreateConvert orderCreateConvert;

    @Autowired
    private SecService secService;

    @Autowired
    private net.kingsilk.wx4j.client.mp.api.user.UserApi wxUserApi;

    @Autowired
    private CartService cartService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private WxComMpAtApi wxComMpAtApi;

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private WxComMpUserApi wxComMpUserApi;

    @Autowired
    @Qualifier("mallAddrConvert")
    private AddrConvert addrConvert;

    @Autowired
    private UserApi userApi;

    @Autowired
    private MsgService msgService;

    @Autowired
    private BrandAppApi brandAppApi;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderLogRepo orderLogRepo;

    @Autowired
    @Qualifier("mallOrderConvert")
    private OrderConvert orderConvert;

    @Autowired
    private QhPayRepo qhPayRepo;

    @Autowired
    private RefundRepo refundRepo;

    @Autowired
    private RefundLogRepo refundLogRepo;

    @Autowired
    private PayService payService;

    @Autowired
    private QhShopProperties qhShopProperties;

    @Autowired
    private AddrService addrService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private DeliverInvoiceRepo deliverInvoiceRepo;

    @Autowired
    private DeliverInvoiceLogRepo deliverInvoiceLogRepo;

    @Autowired
    private SkuStoreService skuStoreService;

    @Autowired
    private SkuStoreRepo skuStoreRepo;

    @Autowired
    private SkuStoreLogRepo skuStoreLogRepo;

    @Autowired
    private MemberResource memberResource;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    @Qualifier("initThreadPool")
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Override
    public UniResp<Map<String, String>> create(
            String brandAppId,
            String shopId,
            List<OrderCheckReq> orderCheckRaq) {

        Assert.notNull(shopId, "获取不到shopId");
        Assert.notNull(orderCheckRaq, "请选择商品");

        UniResp<Map<String, String>> uniResp = new UniResp<>();


        //检查购买数量 检查库存量 及　商品是否下架
        for (OrderCheckReq req : orderCheckRaq) {
            //检查sku购买数量
            if (req.getNum() == null || req.getNum() < 1) {
                HashMap<String, String> map = new HashMap<>();
                map.put("skuId", req.getSkuId());
                uniResp.setData(map);
                uniResp.setException("数量要为正整数");
                return uniResp;
            }
            Sku sku = skuRepo.findOne(req.getSkuId());
            HashMap<String, String> map = new HashMap<>();
            //检查库存量
            if (!skuStoreService.checkSkuStoreNum(sku, req.getNum())) {
                map.put("商品名称：", sku.getTitle());
                uniResp.setData(map);
                uniResp.setException("该商品库存不够了");
                uniResp.setStatus(ErrStatus.TIEMERROR);
                return uniResp;
            }
            //检查是否下架
            if (sku.isDeleted()) {
                map.put("商品名称：", sku.getTitle());
                uniResp.setData(map);
                uniResp.setException("该商品已经下架了");
                uniResp.setStatus(ErrStatus.TIEMERROR);
                return uniResp;

            }
        }

        Order order = new Order();
        //一般的信息
        order.setBrandAppId(brandAppId);
        order.setShopId(shopId);
        String userId = secService.curUserId();
        if (userId != null) {
            String memberId = memberResource.getMemberIdByUserId(brandAppId, shopId, userId);
            order.setMemberId(memberId);
        }


        order.setSeq(commonService.getDateString());
        order.setStatus(OrderStatusEnum.UNCOMMITED);

        //地址信息
        net.kingsilk.qh.oauth.api.UniResp<AddrGetResp> userShippingAddr = addrService.getDefault(userId, "USER_SHIPPING_ADDR");
        AddrGetResp addrData = userShippingAddr.getData();
        if (StringUtils.hasText(addrData.getId())) { //getId() 表示有地址
            addrConvert.aouthToShopAddrConvert(order, addrData);
        }

        //计算运费
        for (OrderCheckReq req : orderCheckRaq) {
            Sku sku = skuRepo.findOne(req.getSkuId());
            Item item = itemRepo.findOne(sku.getItemId());
            if (order.getFreight() == null) {
                order.setFreight(0);
            }
            if (item.getFreight() == null || item.getFreight() < 0) {
                item.setFreight(0);
            }
            if (order.getFreight() < item.getFreight()) {
                order.setFreight(item.getFreight());
            }
        }

        //转换并计算价格
        orderCreateConvert.checkReqConvert(order, orderCheckRaq);
        order.setPaymentAmount(order.getTotalPrice());
//        order.setPaymentAmount(order.getTotalPrice() + Optional.ofNullable(order.getFreight()).orElse(0));
        order.setSeq(commonService.getDateString());
        orderRepo.save(order);

        //日志相关
        OrderLog orderLog = new OrderLog();
        orderLog.setBrandAppId(brandAppId);
        orderLog.setShopId(shopId);
//        orderLog.setUserId(userId);
        orderLog.setOrderId(order.getId());
        orderLog.setLastStatus(order.getStatus());
        orderLog.setType(OperatorTypeEnum.CREATE);
        orderLog.setStatus(OrderStatusEnum.UNCOMMITED);
        orderLogRepo.save(orderLog);

        uniResp.setStatus(ErrStatus.OK);
        HashMap<String, String> map = new HashMap<>();
        map.put("orderId", order.getId());
        uniResp.setData(map);
        uniResp.setMessage("操作成功");
        return uniResp;
    }


    @Override
    public UniResp<String> chooseAddr(String brandAppId, String shopId, String id, String addrId) {

        Order order = orderRepo.findOne(
                allOf(
                        QOrder.order.brandAppId.eq(brandAppId),
                        QOrder.order.id.eq(id),
                        QOrder.order.deleted.ne(true)
                )
        );
        String userId = secService.curUserId();
        net.kingsilk.qh.oauth.api.UniResp<AddrGetResp> addrGetRespUniResp = addrService.get(userId, addrId);
        Order.ShippingAddress shippingAddress = order.new ShippingAddress();
        shippingAddress.setAdc(addrGetRespUniResp.getData().getAdc());
        shippingAddress.setMemo(addrGetRespUniResp.getData().getMemo());
        shippingAddress.setPhone(addrGetRespUniResp.getData().getPhones().isEmpty() ? null : addrGetRespUniResp.getData().getPhones().iterator().next());
        shippingAddress.setReceiver(addrGetRespUniResp.getData().getContact());
        shippingAddress.setStreet(addrGetRespUniResp.getData().getStreet());
        order.setAddr(shippingAddress);
        orderRepo.save(order);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData("修改成功");
        return uniResp;
    }

    //------------------------------------------------------------------------------//
    @Override
    public UniResp<String> commit(
            String brandAppId,
            String shopId,
            String id,
            OrderCommitReq ordercommitReq) {

        Assert.notNull(shopId, "获取不到shopId信息");
        Assert.notNull(id, "获取不到orderId信息");
        Assert.notNull(ordercommitReq, "获取不到订单参数信息");
        UniResp<String> uniResp = new UniResp<>();

        //todo 可以先 check?

        Order order = orderRepo.findOne(id);
//                allOf(
//                        QOrder.order.brandAppId.eq(brandAppId),
//                        QOrder.order.userId.eq(id),
//                        QOrder.order.deleted.ne(true)
//                ));
        if (order == null || order.isDeleted() == true) {
            uniResp.setData("商品错误：查找不到对应的商品");
            uniResp.setStatus(ErrStatus.FINDNULL);
            return uniResp;
        }

        String userId = secService.curUserId();
        if (userId != null) {
            String memberId = memberResource.getMemberIdByUserId(brandAppId, shopId, userId);
            if (memberId != null) {
                order.setMemberId(memberId);
            } else {
                throw new ErrStatusException(ErrStatus.NO_MEMBER, "找不到该会员信息");
            }
        } else {
            throw new ErrStatusException(ErrStatus.UNLOGIN, "用户未登陆");
        }


        if (!OrderStatusEnum.UNCOMMITED.equals(order.getStatus()) &&
                !OrderStatusEnum.UNCONFIRMED.equals(order.getStatus())) {
            uniResp.setStatus(ErrStatus.UNKNOWN);
            uniResp.setData("请勿需重复提交订单！");
            return uniResp;
        }

        if (OrderStatusEnum.UNCOMMITED.equals(order.getStatus())) {

            //检查地址信息
            if (OrderSourceTypeEnum.SINCE.getCode().equalsIgnoreCase(ordercommitReq.getSourceType())) { //自取,置空地址
                order.setAddr(null);
                SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
                try {
                    if (ordercommitReq.getSinceTakeTime() != null) {
                        order.setSinceTakeTime(format.parse(ordercommitReq.getSinceTakeTime()));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            order.setSourceType(OrderSourceTypeEnum.valueOf(ordercommitReq.getSourceType()));

            //检查库存
            ArrayList<SkuStore> skuStores = new ArrayList<>();
            List<Order.OrderItem> orderItems = order.getOrderItems();
//            orderItems.forEach(orderItem -> {
            for (Order.OrderItem orderItem : orderItems) {
                SkuStore skuStore = skuStoreService.reduceSkuStoreNum(
                        orderItem.getSkuId(), Integer.parseInt(orderItem.getNum()));
                if (skuStore == null) {
                    uniResp.setData(orderItem.getSkuId());
                    uniResp.setException("skuId:" + orderItem.getSkuId() + " 该商品库存不够了");
                    uniResp.setStatus(ErrStatus.TIEMERROR);
                    return uniResp;
                }
                if (skuStore.getSalesVolume() == null) {
                    skuStore.setSalesVolume(0);
                } else {
                    skuStore.setSalesVolume(skuStore.getSalesVolume() + Integer.parseInt(orderItem.getNum()));
                }
                skuStores.add(skuStore);
            }
            //更新库存
            skuStoreRepo.save(skuStores);

            //情况购物车
            String curUserId = secService.curUserId();
            Cart cart = cartService.getCart(null, curUserId, brandAppId, shopId, "MALL");
            List<Cart.CartItem> collect = cart.getCartItems().stream().filter(it ->
                    order.getOrderItems().stream().map(Order.OrderItem::getSkuId).collect(Collectors.toList()).contains(it.getSkuId())
            ).collect(Collectors.toList());
            cart.getCartItems().removeAll(collect);
            cartRepo.save(cart);


            //库存日志
            for (int i = 0; i < orderItems.size(); i++) {
                SkuStoreLog skuStoreLog = new SkuStoreLog();
                skuStoreLog.setNum(-Integer.parseInt(orderItems.get(i).getNum()));
                skuStoreLog.setSkuStoreId(skuStores.get(i).getId());
                skuStoreLog.setBrandAppId(brandAppId);
                skuStoreLog.setShopId(shopId);
                skuStoreLog.setStoreChangeEnum(SkuStoreChangeEnum.SELL);
                skuStoreLogRepo.save(skuStoreLog);
            }

            order.setBuyerMemo(ordercommitReq.getBuyerMemo());
            //转换并计算价格
            orderCreateConvert.commitReqConvert(order, ordercommitReq);
            order.setStatus(OrderStatusEnum.UNPAYED);
            order.setSeq(commonService.getDateString());
            orderRepo.save(order);

            net.kingsilk.qh.platform.api.UniResp<BrandAppResp> info = brandAppApi.info(brandAppId);
            String wxComAppId = info.getData().getWxComAppId();
            String wxMpAppId = info.getData().getWxMpId();

            Shop shop = shopRepo.findOne(
                    Expressions.allOf(
                            QShop.shop.brandAppId.eq(brandAppId),
                            QShop.shop.id.eq(shopId),
                            QShop.shop.deleted.ne(true)
                    )
            );

            net.kingsilk.qh.oauth.api.UniResp<UserGetResp> infoByPhone = userApi.getInfoByPhone(shop.getPhone());

            List<UserGetResp.WxUser> openIdList = infoByPhone.getData().getWxUsers().stream().filter(wxUser ->
                    wxMpAppId.equals(wxUser.getAppId())
            ).collect(Collectors.toList());

            String openId = openIdList.get(0).getOpenId();

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

//            https://kingsilk.net/shop/local/?_ddnsPort=16700#/brandApp/5a2a827352faff00084b289f/store/5a2e2e6b5f15030008b03862/home
            String sharUrl = qhShopProperties.getQhShop().getHtml().getUrl() + "brandApp/" + brandAppId + "/store/" + shopId + "/order/orderDetail?id=" + id;
//            谭号提交了一笔订单，等待TA支付，请查看。
            if (StringUtils.hasText(nicName)) {
                nicName = "\"" + nicName + "\"";
            } else {
                nicName = "\"" + phone + "\"";
            }
            String content = nicName + "提交了一笔订单，等待TA支付\n" +
                    "<a href='" + sharUrl + "'>点击查看</a>";
            msgService.sendMsg(wxComAppId, wxMpAppId, openId, content);

            //执行定时任务
            Runnable run = () -> {
                taskService.run(order.getId());
            };
            Date runTime = new Date(order.getDateCreated().getTime() + 60 * 60 * 1000);
            threadPoolTaskScheduler.schedule(run, runTime);

            //日志记录
            OrderLog orderLog = new OrderLog();
            orderLog.setBrandAppId(brandAppId);
            orderLog.setShopId(shopId);
            orderLog.setOrderId(order.getId());
            orderLog.setLastStatus(order.getStatus());
            orderLog.setOrderSourceType(order.getSourceType());
            orderLog.setMemo(order.getSellerMemo());
            orderLog.setType(OperatorTypeEnum.CREATE);
            orderLog.setStatus(OrderStatusEnum.UNPAYED);
            orderLogRepo.save(orderLog);
        }
        uniResp.setStatus(ErrStatus.OK);
        uniResp.setData("操作成功");

        return uniResp;
    }

    @Override
    public UniResp<OrderPageResp<OrderMiniInfo>> page(
            String brandAppId,
            String shopId,
            OrderPageReq orderPageReq) {

        String userId = secService.curUserId();
        String memberId = memberResource.getMemberIdByUserId(brandAppId, shopId, userId);
        Assert.notNull(userId, "获取不到当前用户信息");
        if (orderPageReq.getSort().size() == 0) {
            orderPageReq.getSort().add("dateCreated,desc");
        }
        Sort s = ParamUtils.toSort(orderPageReq.getSort());
        Pageable pageable = new PageRequest(orderPageReq.getPage(), orderPageReq.getSize(), s);

        List<OrderStatusEnum> list = new ArrayList<>();
        if (orderPageReq.getStatus().size() > 0) {
            orderPageReq.getStatus().forEach(status ->
                    list.add(OrderStatusEnum.valueOf(status)));
        }

        Page<Order> orders = orderRepo.findAll(
                allOf(
                        QOrder.order.brandAppId.eq(brandAppId),
                        QOrder.order.memberId.eq(memberId),
                        QOrder.order.deleted.ne(true),
                        list.size() > 0 ? DbUtil.opIn(QOrder.order.status, list) :
                                QOrder.order.status.ne(OrderStatusEnum.valueOf("UNCOMMITED")),

                        !StringUtils.isEmpty(orderPageReq.getKeyWord())
                                ? QOrder.order.seq.like("%" + orderPageReq.getKeyWord() + "%") : null
                ), pageable
        );

        UniResp<OrderPageResp<OrderMiniInfo>> uniResp = new UniResp<>();
        if (orders == null || orders.getContent() == null || orders.getContent().size() == 0) {
            uniResp.setStatus(ErrStatus.FINDNULL);
            uniResp.setMessage("没有相关订单");
            return uniResp;
        }
        OrderPageResp<OrderMiniInfo> orderMiniInfoPage = new OrderPageResp<>();
        ArrayList<OrderMiniInfo> orderMiniInfoList = new ArrayList<>();
        orderMiniInfoPage.setSize(orders.getSize());
        orderMiniInfoPage.setNumber(orders.getNumber());
        orderMiniInfoPage.setTotalPages(orders.getTotalPages());
        orderMiniInfoPage.setTotalElements(orders.getTotalElements());
        orders.getContent().forEach(it ->
                orderMiniInfoList.add(orderConvert.orderMiniInfoConvert(it))
        );
        orderMiniInfoPage.setContent(orderMiniInfoList);
        uniResp.setData(orderMiniInfoPage);
        uniResp.getData().setOrderStatusEnumMap(OrderStatusEnum.getMap());
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    @Override
    public UniResp<OrderInfoResp> info(
            String brandAppId,
            String shopId,
            String id) {

        Assert.notNull(id, "获取不到orderId");
        Assert.notNull(shopId, "获取不到门店Id");

        Order order = orderRepo.findOne(
                allOf(
                        QOrder.order.brandAppId.eq(brandAppId),
                        QOrder.order.id.eq(id),
                        QOrder.order.deleted.ne(true)
                )
        );
        UniResp<OrderInfoResp> uniResp = new UniResp<>();
        if (order == null) {
            uniResp.setMessage("查找不到对应的order");
            uniResp.setStatus(ErrStatus.FINDNULL);
            return uniResp;
        }
        OrderInfoResp orderInfoResp = orderConvert.orderInfoConvert(order);
        ArrayList<Refund> refunds = Lists.newArrayList(refundRepo.findOne(
                allOf(
                        QRefund.refund.id.eq(id),
                        QRefund.refund.deleted.ne(true)
                )
        ));
        if (refunds != null && refunds.size() > 0) {
            orderInfoResp.setHaveRefund(true);
        }
//        orderInfoResp.
        uniResp.setData(orderInfoResp);
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    @Override
    public UniResp<String> rejectOrder(
            String brandAppId,
            String shopId,
            String id,
            String memo) {


        Order order = orderRepo.findOne(allOf(
                QOrder.order.brandAppId.eq(brandAppId),
                QOrder.order.id.eq(id),
                QOrder.order.deleted.ne(true)));
        UniResp<String> uniResp = new UniResp<>();
        if (order == null) {
            uniResp.setMessage("查找不到对应的order");
            uniResp.setStatus(ErrStatus.FINDNULL);
            return uniResp;
        }
        order.setStatus(OrderStatusEnum.REJECTED);
//        order.setLastStatus(OrderStatusEnum.UNCONFIRMED)
        order.setSellerMemo(memo);
        Refund refund = new Refund();
//        def qhWXPay = qhPayRepo.findByOrderAndPayType(order,"WX")
//        Integer wxPay = qhWXPay?qhWXPay.thirdPayAmount:0
//        def qhAliPay = qhPayRepo.findByOrderAndPayType(order,"ALI")
//        Integer aliPay = qhAliPay?qhAliPay.thirdPayAmount:0
//        refund.aliAmount = aliPay
//        refund.wxAmount = wxPay
        refund.setBrandAppId(brandAppId);
        refund.setShopId(shopId);
        refund.setOrderId(id);
        refund.setSeq(commonService.getDateString());
        refund.setRefundType(RefundTypeEnum.ALIPAY);       //TODO
        refund.setRefundMoney(order.getTotalPrice());
        refund.setReason(memo);


        RefundLog refundLog = new RefundLog();
        refundLog.setRefundId(refund.getId());
        refundLog.setBrandAppId(brandAppId);
        refundLog.setShopId(shopId);
        refundLog.setOperatorType(OperatorTypeEnum.REJECT);
        refundLog.setLastStatus(RefundStatusEnum.REJECTED);
        refundLog.setRefundMoney(order.getTotalPrice());
//        refundLog.setStaffId(order.get);                   //TODO
        orderRepo.save(order);
        refundRepo.save(refund);
        refundLogRepo.save(refundLog);

        uniResp.setStatus(ErrStatus.OK);
        uniResp.setMessage("操作成功");
        return uniResp;
    }

    @Override
    public UniResp<Map<String, String>> getLogisticsCompanyEnum(
            String brandAppId) {

        Map<String, String> map = LogisticsCompanyEnum.getMap();
        UniResp<Map<String, String>> uniResp = new UniResp<>();
        uniResp.setStatus(ErrStatus.OK);
        uniResp.setData(map);
        return uniResp;
    }

    //    @Override
//    public UniResp<String> confirmOrder(
//            String brandAppId,
//            String shopId,
//            String id) {
//
//        Assert.notNull(shopId,"没有获取到shopId");
//        Assert.notNull(id,"没有获取到orderId");
//
//        Order order = orderRepo.findOne(
//                allOf(
//                        QOrder.order.id.eq(id),
//                        QOrder.order.status.eq(OrderStatusEnum)
//                )
//        );
//        Assert.notNull(order,"找不到对应的订单");
//        order.
//
//        return null;
//    }

    @Override
    public UniResp<String> cancelOrder(String brandAppId, String shopId, String id, String memo) {
        Order order = orderRepo.findOne(
                Expressions.allOf(
                        QOrder.order.id.eq(id),
                        QOrder.order.deleted.ne(true),
                        QOrder.order.brandAppId.eq(brandAppId)
                )
        );
        if (order == null) {
            throw new ErrStatusException(ErrStatus.FINDNULL, "没找到订单");
        }
        UniResp<String> uniResp = new UniResp<>();
        //退款
        if (OrderStatusEnum.UNCONFIRMED.equals(order.getStatus())) {
            order.setStatus(OrderStatusEnum.CANCELED);
            Refund refund = new Refund();
            refund.setRefundStatus(RefundStatusEnum.REJECTING);
            refund.setOrderId(id);
            refund.setBrandAppId(brandAppId);
            refund.setShopId(shopId);
            refund.setReason("取消订单");
            refund.setMemberId(order.getMemberId());
            refund.setSeq(commonService.getDateString());
            QhPay qhPay = qhPayRepo.findOne(
                    Expressions.allOf(
                            QQhPay.qhPay.id.eq(order.getQhPayId()),
                            QQhPay.qhPay.deleted.ne(true),
                            QQhPay.qhPay.brandAppId.eq(brandAppId),
                            QQhPay.qhPay.orderId.eq(id)
                    )
            );
            if (qhPay == null) {
                throw new ErrStatusException(ErrStatus.FINDNULL, "找不到对应的支付信息");
            }
            refund.setRefundType(RefundTypeEnum.valueOf(qhPay.getPayType().getCode()));
            Integer payAmount = Optional.ofNullable(qhPay.getThirdPayAmount()).orElse(0);
            if ("WX".equals(qhPay.getPayType().getCode())) {
                refund.setWxAmount(payAmount);
            } else if ("ALIPAY".equals(qhPay.getPayType().getCode())) {
                refund.setAliAmount(payAmount);
            }
            refundRepo.save(refund);
//            if (order.getOrderItems() != null || order.getOrderItems().size() > 0) {
//                for (Order.OrderItem orderItem : order.getOrderItems()) {
//                    orderItem.setRefundId(refund.getId());
//                }
//            }

            Map payMap = payService.refund(refund, order);
            //貌似支付宝退款没有回调
            Map refundResponse = Optional.ofNullable((Map) payMap.get("alipay_trade_refund_response")).orElse(new HashMap());
            String code = refundResponse == null ? "" : (String) refundResponse.get("code");
            String resultCode = (String) payMap.get("resultCode");
            String returnCode = (String) payMap.get("returnCode");
            if ("10000".equals(code) ||
                    ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode))) {
//                    Payment payment = paymentRepo.findOne(
//                            Expressions.allOf(
//                                    QPayment.payment.tradeNo.eq(order.getId()),
//                                    QPayment.payment.deleted.ne(true),
//                                    QPayment.payment.brandAppId.eq(order.getBrandAppId())
//                            )
//                    );
//                    payment.setPaymentStatus(PaymentStatusEnum.PAYED);
//                    payment.setPayType(qhPay.getPayType());

                //订单状态修改
                OrderLog orderLog = new OrderLog();
                orderLog.setOrderId(order.getId());
                orderLog.setShopId(order.getShopId());
                orderLog.setBrandAppId(order.getBrandAppId());
                orderLog.setMemberId(order.getMemberId());
                orderLog.setStatus(OrderStatusEnum.CANCELED);
                orderLog.setLastStatus(OrderStatusEnum.CANCELING);
                qhPay.setOrderId(order.getId());
                qhPay.setThirdPayAmount(order.getPaymentAmount());
                qhPay.setBrandAppId(order.getBrandAppId());
                qhPay.setRefundAmount(order.getPaymentAmount());
                qhPay.setPayType(qhPay.getPayType());
                qhPay.setPayTime(new Date());

                //释放库存
                skuStoreService.refreshSkuStore(order, brandAppId);

//                order.setStatus(OrderStatusEnum.CANCELED);
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
                qhPayRepo.save(qhPay);
                refundRepo.save(refund);
//                    paymentRepo.save(payment);
                orderRepo.save(order);


                //账户日志表进行保存
                orderLog.setType(OperatorTypeEnum.PAYED);
                orderLog.setMemo(order.getBuyerMemo());
                orderLogRepo.save(orderLog);

                RefundLog refundLog = new RefundLog();
                refundLog.setBrandAppId(brandAppId);
                refundLog.setShopId(shopId);
                refundLog.setRefundId(refund.getId());
//                refundLog.setLastStatus();
                Integer aliAmount = refund.getAliAmount();
                refundLog.setRefundMoney(aliAmount != null ? aliAmount : refund.getWxAmount());
                refundLog.setStatus(RefundStatusEnum.FINISHED);
                refundLog.setOperatorType(OperatorTypeEnum.MONEY_ONLY);
                refundLogRepo.save(refundLog);

                uniResp.setStatus(HttpStatus.SC_OK);
                uniResp.setData("订单修改完成");
            } else {
                refund.setRefundStatus(RefundStatusEnum.REFUNDFILED);
                uniResp.setStatus(Integer.parseInt(Optional.ofNullable((String) refundResponse.get("code")).orElse("40004")));
                uniResp.setData(Optional.ofNullable((String) refundResponse.get("sub_msg")).orElse("稍后再退"));
            }
        } else if (order.getStatus() == OrderStatusEnum.UNPAYED) {
            order.setStatus(OrderStatusEnum.CANCELED);
            //释放库存
            skuStoreService.refreshSkuStore(order, brandAppId);

            OrderLog orderLog = new OrderLog();
            orderLog.setOrderId(order.getId());
            orderLog.setShopId(order.getShopId());
            orderLog.setBrandAppId(order.getBrandAppId());
            orderLog.setStatus(OrderStatusEnum.CANCELED);
            orderLog.setLastStatus(OrderStatusEnum.UNPAYED);
            orderLog.setType(OperatorTypeEnum.CANCEL);
            orderLog.setMemo(order.getBuyerMemo());
            orderLogRepo.save(orderLog);
            orderRepo.save(order);
            uniResp.setStatus(HttpStatus.SC_OK);
            uniResp.setData("订单修改完成");
        } else {
            throw new ErrStatusException(ErrStatus.ORDER_STATUS_ERROR, "订单状态错误！");
        }

        return uniResp;
    }


    @Override
    public UniResp<String> confirmReceive(
            String brandAppId,
            String shopId,
            String id) {

        Order order = orderRepo.findOne(id);
        Assert.notNull(order, "订单错误");
        Assert.isTrue(order.getStatus() == OrderStatusEnum.UNRECEIVED, "订单状态错误");
        order.setStatus(OrderStatusEnum.FINISHED);
        orderRepo.save(order);

        //改变发货单状态
        DeliverInvoice deliverInvoice = deliverInvoiceRepo.findOne(
                allOf(
                        QDeliverInvoice.deliverInvoice.deleted.ne(true),
                        QDeliverInvoice.deliverInvoice.orderId.eq(order.getId())
                )
        );
        deliverInvoice.setDeliverStatus(DeliverStatusEnum.RECEIVED);
        deliverInvoiceRepo.save(deliverInvoice);

        //确认收货后 卖家的账户处理 //TODO
        //确认收货时的账户明细变更

        //订单日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(order.getId());
        orderLog.setMemberId(order.getMemberId());
        orderLog.setType(OperatorTypeEnum.CONFIRMED);
        orderLog.setBrandAppId(brandAppId);
        orderLog.setShopId(shopId);
        orderLog.setStatus(order.getStatus());
        orderLog.setOrderSourceType(order.getSourceType());
//        orderLog.setMemo();
        orderLog.setLastStatus(OrderStatusEnum.UNRECEIVED);
        orderLogRepo.save(orderLog);

        //运单日志
        DeliverInvoiceLog deliverInvoiceLog = new DeliverInvoiceLog();
        deliverInvoiceLog.setBrandAppId(brandAppId);
        deliverInvoiceLog.setShopId(shopId);
        deliverInvoiceLog.setOrderId(order.getId());
        deliverInvoiceLog.setDeliverStatusEnum(DeliverStatusEnum.RECEIVED);
        deliverInvoiceLog.setDeliverInvoiceId(deliverInvoice.getId());
        deliverInvoiceLog.setLogisticsId(deliverInvoice.getLogisticsesId());
        deliverInvoiceLogRepo.save(deliverInvoiceLog);

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(ErrStatus.OK);
        uniResp.setData("操作成功");
        return uniResp;
    }

    @Override
    public UniResp<String> confirmSince(String brandAppId, String shopId, String id) {
        Order order = orderRepo.findOne(id);
        Assert.notNull(order, "订单错误");
        Assert.isTrue(order.getStatus() == OrderStatusEnum.SINCEING, "订单状态错误");
        order.setStatus(OrderStatusEnum.FINISHED);
        orderRepo.save(order);

        //订单日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(order.getId());
        orderLog.setMemberId(order.getMemberId());
        orderLog.setType(OperatorTypeEnum.CONFIRMED);
        orderLog.setBrandAppId(brandAppId);
        orderLog.setShopId(shopId);
        orderLog.setStatus(order.getStatus());
        orderLog.setOrderSourceType(order.getSourceType());
//        orderLog.setMemo();
        orderLog.setLastStatus(OrderStatusEnum.UNRECEIVED);
        orderLogRepo.save(orderLog);

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(ErrStatus.OK);
        uniResp.setData("操作成功");
        return uniResp;
    }

    @Override
    public UniResp<String> deliverType(
            String brandAppId,
            String shopId,
            String id,
            String orderSourceType) {

        UniResp<String> uniResp = new UniResp<>();
        if (orderSourceType != null) {

            Order order = orderRepo.findOne(allOf(
                    QOrder.order.brandAppId.eq(brandAppId),
                    QOrder.order.id.eq(id),
                    QOrder.order.deleted.ne(true)
            ));
            if (order == null) {
                uniResp.setMessage("找不的对应的订单");
                uniResp.setStatus(ErrStatus.FINDNULL);
                return uniResp;
            }
            if (OrderSourceTypeEnum.MALL.equals(OrderSourceTypeEnum.valueOf(orderSourceType))) {
                //把运费　加到支付价中
                order.setPaymentAmount(order.getTotalPrice() + Optional.ofNullable(order.getFreight()).orElse(0));
            }
            if (OrderSourceTypeEnum.SINCE.equals(OrderSourceTypeEnum.valueOf(orderSourceType))) {
                order.setPaymentAmount(order.getTotalPrice());
            }
            order.setSourceType(OrderSourceTypeEnum.valueOf(orderSourceType));
            orderRepo.save(order);
            uniResp.setStatus(ErrStatus.OK);
            uniResp.setMessage("成功了");
            uniResp.setData(order.getSourceType().getDesp());
            return uniResp;
        }
        uniResp.setMessage("失败了");
        uniResp.setStatus(ErrStatus.UNKNOWN);
        return uniResp;
    }
}
