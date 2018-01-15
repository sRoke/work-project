package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.refund;


import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.UserGetResp;
import net.kingsilk.qh.platform.api.brandApp.BrandAppApi;
import net.kingsilk.qh.platform.api.brandApp.dto.BrandAppResp;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.refund.RefundApi;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.refund.dto.*;
import net.kingsilk.qh.shop.api.common.dto.SkuInfoModel;
import net.kingsilk.qh.shop.core.*;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.*;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.refund.convert.RefundConvert;
import net.kingsilk.qh.shop.service.QhShopProperties;
import net.kingsilk.qh.shop.service.service.CommonService;
import net.kingsilk.qh.shop.service.service.MsgService;
import net.kingsilk.qh.shop.service.service.SecService;
import net.kingsilk.qh.shop.service.util.DbUtil;
import net.kingsilk.qh.shop.service.util.ParamUtils;
import net.kingsilk.wx4j.broker.api.wxCom.mp.at.WxComMpAtApi;
import net.kingsilk.wx4j.broker.api.wxCom.mp.user.GetResp;
import net.kingsilk.wx4j.broker.api.wxCom.mp.user.WxComMpUserApi;
import net.kingsilk.wx4j.client.mp.api.user.InfoResp;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.querydsl.core.types.dsl.Expressions.allOf;


@Component("mallRefundResource")
public class RefundResource implements RefundApi {

    @Autowired
    private RefundRepo refundRepo;

    @Autowired
    private RefundConvert refundConvert;

    @Autowired
    private QhShopProperties qhShopProperties;

    @Autowired
    private SkuRepo skuRepo;

    @Autowired
    private UserApi userApi;

    @Autowired
    private MsgService msgService;

    @Autowired
    private BrandAppApi brandAppApi;

    @Autowired
    private net.kingsilk.wx4j.client.mp.api.user.UserApi wxUserApi;

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private WxComMpAtApi wxComMpAtApi;

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private WxComMpUserApi wxComMpUserApi;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private SecService secService;

    @Autowired
    private LogisticsRepo logisticsRepo;

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private ItemPropValueRepo itemPropValueRepo;

    @Autowired
    private RefundLogRepo refundLogRepo;

    @Autowired
    private SkuStoreRepo skuStoreRepo;

    @Override
    public UniResp<RefundSkuInfo> skuInfo(
            String brandAppId,
            String shopId,
            String id,
            String orderId) {
        Assert.notNull(id, "skuId错误");
        Assert.notNull(orderId, "orderId错误");
        Sku sku = skuRepo.findOne(
                allOf(
                        QSku.sku.id.eq(id),
//                        QSku.sku.deleted.ne(true),
                        QSku.sku.brandAppId.eq(brandAppId)
                )
        );
        Order order = orderRepo.findOne(
                allOf(
                        QOrder.order.id.eq(orderId),
                        QOrder.order.brandAppId.eq(brandAppId)
//                        QOrder.order.deleted.ne(true)
                )
        );
        if (sku == null) {
            throw new ErrStatusException(ErrStatus.FINDNULL, "找不到SKU信息");
        }


        RefundTypeEnum[] refundTypeEnums = new RefundTypeEnum[]{RefundTypeEnum.ONLYMONEY, RefundTypeEnum.ITEM};
        Map<String, String> map = Arrays.stream(refundTypeEnums).collect(Collectors.toMap(RefundTypeEnum::getCode, RefundTypeEnum::getDesp));
        SkuInfoModel skuInfoModel = conversionService.convert(sku, SkuInfoModel.class);
        //item中没有数据，就从sku中取
        ArrayList<String> itmeImgs = skuInfoModel.getImgs();
        if (itmeImgs == null || itmeImgs.size() < 1) {
            ArrayList<String> skuImgs = new ArrayList<>();
            skuImgs.add(sku.getImgs().iterator().next());
            skuInfoModel.setImgs(skuImgs);
        }
        List specs = skuInfoModel.getSpecs();
        if (specs == null || specs.size() < 1) {
            ArrayList<String> skuPropvalues = new ArrayList<>();
            for (Sku.Spec spec : sku.getSpecs()) {
                ItemPropValue propValue = itemPropValueRepo.findOne(spec.getItemPropValueId());
                skuPropvalues.add(propValue.getName());
            }
            skuInfoModel.setSpecs(skuPropvalues);
        }

        UniResp<RefundSkuInfo> uniResp = new UniResp<>();
        RefundSkuInfo refundSkuInfo = new RefundSkuInfo();
        refundSkuInfo.setSkuInfo(skuInfoModel);
        order.getOrderItems().forEach(
                orderItem -> {
                    if (orderItem.getSkuId().equals(id)) {
                        refundSkuInfo.getSkuInfo().setNum(orderItem.getNum());
                    }
                }
        );
        refundSkuInfo.setRefundTypeEnums(map);
        Optional.ofNullable(shopRepo.findOne(
                allOf(
                        QShop.shop.deleted.ne(true),
                        QShop.shop.id.eq(order.getShopId())
                )
        )).ifPresent(it ->
                refundSkuInfo.setPhone(it.getPhone())
        );
        if (order != null || order.getOrderItems() != null) {
            for (Order.OrderItem item : order.getOrderItems()) {
                if (id.equalsIgnoreCase(item.getSkuId())) {
                    refundSkuInfo.getSkuInfo().setAllRealPayPrice(item.getAllRealPayPrice());
                }
            }
        }

        uniResp.setData(refundSkuInfo);
        uniResp.setStatus(HttpStatus.SC_OK);
        return uniResp;
    }

    @Override
    public UniResp<RefundInfoResp> info(
            String brandAppId,
            String shopId,
            String id) {

        Refund refund = refundRepo.findOne(id);
        Assert.notNull(refund, "退款订单错误");
        UniResp<RefundInfoResp> uniResp = new UniResp<>();

        RefundInfoResp infoResp = refundConvert.infoRespConvert(refund);
        uniResp.setData(infoResp);
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }


    @Override
    public UniResp<String> generateRefund(
            String brandAppId,
            String shopId,
            List<OrderCheckReq> req) {

        Assert.notNull(brandAppId, "brandAppId错误");
        Assert.notNull(shopId, "shopId错误");
        Assert.notNull(req, "请求参数错误");
        UniResp<String> uniResp = new UniResp<>();
        if (req.size() > 0) {
            for (OrderCheckReq orderReq : req) {
                Refund refund = new Refund();
                Sku sku = skuRepo.findOne(orderReq.getSkuId());
                Order order = orderRepo.findOne(orderReq.getOrderId());
                Assert.notNull(sku, "sku错误");
                Assert.notNull(order, "order错误");
                //判断退款金额
                for (Order.OrderItem item : order.getOrderItems()) {

                    if (item.getSkuId().equalsIgnoreCase(orderReq.getSkuId())) {
                        if (orderReq.getPrice() == null) {
                            uniResp.setStatus(ErrStatus.REFUNDMONEYEREEOR);
                            uniResp.setData("退款金额不能为空");
                            return uniResp;
                        }
                        if (Integer.parseInt(orderReq.getPrice()) > Integer.parseInt(item.getAllRealPayPrice())) {
                            uniResp.setStatus(ErrStatus.REFUNDMONEYEREEOR);
                            uniResp.setData("退款金额不能大于支付金额");
                            return uniResp;
                        }
                    }
                }

                refund.setBrandAppId(brandAppId);
                refund.setShopId(shopId);
                refund.setOrderId(orderReq.getOrderId());
                refund.setRefundType(RefundTypeEnum.valueOf(orderReq.getType()));
                refund.setMemberId(order.getMemberId());
                refund.setSeq(commonService.getDateString());
                refund.setRefundStatus(RefundStatusEnum.UNCHECKED);
                refund.setRefundMoney(Integer.valueOf(orderReq.getPrice()));
                //TODO 退款 销售价
                refund.setMemo(orderReq.getReason());
                refund.setReason(orderReq.getReason());
                Refund.Sku refundSku = new Refund.Sku();
                refundSku.setApplyedNum(orderReq.getNum());
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
                refundRepo.save(refund);
                order.getOrderItems().forEach(
                        orderItem -> {
                            req.forEach(
                                    orderCheckReq -> {
                                        if (orderItem.getSkuId().equals(orderCheckReq.getSkuId())) {
                                            orderItem.setRefundId(refund.getId());
                                        }
                                    }
                            );
                        }
                );
                orderRepo.save(order);

                //日志
                RefundLog refundLog = new RefundLog();
                refundLog.setOperatorType(OperatorTypeEnum.MONEY_ONLY);
                refundLog.setRefundMoney(refund.getRefundMoney());
                refundLog.setRefundId(refund.getId());
                refundLog.setStatus(RefundStatusEnum.UNCHECKED);
                refundLog.setBrandAppId(brandAppId);
                refundLog.setShopId(shopId);
                refundLogRepo.save(refundLog);

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
                String curUserId = secService.curUserId();
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
                String sharUrl = qhShopProperties.getQhShop().getHtml().getUrl() + "brandApp/" + brandAppId + "/store/" + shopId + "/refund/refundDetail?id=" + refund.getId();
//            谭号提交了一笔订单，等待TA支付，点击查看。
                if (StringUtils.hasText(nicName)) {
                    nicName = "\"" + nicName + "\"";
                } else {
                    nicName = "\"" + phone + "\"";
                }
                String content = nicName + "发起了一笔退款申请，等你确认\n" +
                        "<a href='" + sharUrl + "'>点击查看</a>";
                msgService.sendMsg(wxComAppId, wxMpAppId, openId, content);

//                //释放库存
//                SkuStore skuStore = skuStoreRepo.findOne(allOf(
//                        QSkuStore.skuStore.brandAppId.eq(brandAppId),
//                        QSkuStore.skuStore.skuId.eq(orderReq.getSkuId())
//                ));
//                skuStore.setNum(skuStore.getNum() + orderReq.getNum());
//                skuStore.setSalesVolume(skuStore.getSalesVolume() - orderReq.getNum());
//                skuStoreRepo.save(skuStore);

//                OrderLog orderLog = new OrderLog();
//                orderLog.setMemberId(order.getMemberId());
//                orderLog.setBrandAppId(order.getBrandAppId());
//                orderLog.setShopId(order.getShopId());
//                orderLog.setLastStatus(order.getStatus());
//                orderLog.setStatus(order.getStatus());
//                orderLog.setOrderId(order.getId());
//                orderLog.set
            }
            uniResp.setStatus(ErrStatus.OK);
            uniResp.setData("操作成功");
            return uniResp;
        }
        uniResp.setStatus(ErrStatus.PARAMNUll);
        uniResp.setData("操作失败");
        return uniResp;
    }


    @Override
    public UniResp<UniPageResp<RefundInfoResp>> page(
            String brandAppId,
            String shopId,
            RefundPageReq refundPageReq) {

        if (refundPageReq == null) {
            throw new ErrStatusException(ErrStatus.FINDNULL, "请求参数错误");
        }

        List sort = refundPageReq.getSort();
        if (sort == null || sort.size() == 0) {
            sort.add("dateCreated,desc");
        }
        Sort s = ParamUtils.toSort(sort);
        Pageable pageable = new PageRequest(refundPageReq.getPage(), refundPageReq.getSize(), s);
        String userId = secService.curUserId();

        Member member = memberRepo.findOne(allOf(
                QMember.member.brandAppId.eq(brandAppId),
                QMember.member.shopId.eq(shopId),
                QMember.member.userId.eq(userId),
                QMember.member.deleted.ne(true)    //TODO
        ));
        if (member == null) {
            throw new ErrStatusException(ErrStatus.FINDNULL, "查询会员信息错误");
        }

        List<RefundStatusEnum> list = new ArrayList<>();
        if ((refundPageReq.getStatus()).size() > 0) {
            refundPageReq.getStatus().forEach(string ->
                    list.add(RefundStatusEnum.valueOf(string)));
        }

        List<RefundStatusEnum> defaultList = new ArrayList<>();
        defaultList.add(RefundStatusEnum.REFUNDFILED);
        defaultList.add(RefundStatusEnum.REJECTING);
        Page<Refund> pageDate = refundRepo.findAll(
                allOf(
                        QRefund.refund.brandAppId.eq(brandAppId),
                        QRefund.refund.shopId.eq(shopId),
                        QRefund.refund.memberId.eq(member.getId()),
                        QRefund.refund.deleted.ne(true),
                        refundPageReq.getStatus().size() > 0 ? DbUtil.opIn(QRefund.refund.refundStatus, list) :
                                DbUtil.opNotIn(QRefund.refund.refundStatus, defaultList)

                ), pageable
        );

        UniResp<UniPageResp<RefundInfoResp>> uniResp = new UniResp<>();
        if (pageDate.getContent() == null || pageDate.getContent().size() < 1) {
            uniResp.setStatus(ErrStatus.FINDNULL);
            uniResp.setMessage("查询不到对应的信息");
            return uniResp;
        }

        Page<RefundInfoResp> respPage = pageDate.map(refund -> {

            RefundInfoResp refundInfoResp = refundConvert.infoRespConvert(refund);

            return refundInfoResp;
        });


        UniPageResp<RefundInfoResp> refundPageResp = new UniPageResp<>();
        refundPageResp = conversionService.convert(pageDate, UniPageResp.class);
        refundPageResp.setContent(respPage.getContent());
        uniResp.setData(refundPageResp);
        uniResp.setStatus(ErrStatus.OK);

        return uniResp;
    }

    @Override
    public UniResp<String> refundLogistics(
            String brandAppId,
            String shopId,
            String id,
            RefundLogisticsReq req) {
        Refund refund = refundRepo.findOne(
                allOf(
                        QRefund.refund.id.eq(id),
                        QRefund.refund.brandAppId.eq(brandAppId),
                        QRefund.refund.shopId.eq(shopId),
                        QRefund.refund.deleted.ne(true),
                        QRefund.refund.refundStatus.eq(RefundStatusEnum.WAIT_BUYER_SENDING)
                )
        );

        Logistics logistics = new Logistics();
        logistics.setCompany(LogisticsCompanyEnum.valueOf(req.getCompany()));
        logistics.setStatus(LogisticsStatusEnum.ON_WAY);
        logistics.setExpressNo(req.getExpressNo());
        logisticsRepo.save(logistics);
        refund.setLogisticsId(logistics.getId());

        refund.setBuyerDeliveredTime(new Date());
        refund.setRefundStatus(RefundStatusEnum.WAIT_SELLER_RECEIVED);
        refundRepo.save(refund);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData("提交成功");

        return uniResp;
    }
}
