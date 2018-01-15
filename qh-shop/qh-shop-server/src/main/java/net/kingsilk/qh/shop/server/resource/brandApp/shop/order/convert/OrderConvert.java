package net.kingsilk.qh.shop.server.resource.brandApp.shop.order.convert;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.oauth.api.UniResp;
import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.UserGetResp;
import net.kingsilk.qh.platform.api.brandApp.BrandAppApi;
import net.kingsilk.qh.platform.api.brandApp.dto.BrandAppResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.addr.dto.AddrModel;
import net.kingsilk.qh.shop.api.brandApp.shop.order.dto.LogisticInfo;
import net.kingsilk.qh.shop.api.brandApp.shop.order.dto.OrderInfoResp;
import net.kingsilk.qh.shop.api.brandApp.shop.order.dto.OrderItemInfo;
import net.kingsilk.qh.shop.api.brandApp.shop.order.dto.OrderResp;
import net.kingsilk.qh.shop.core.OrderSourceTypeEnum;
import net.kingsilk.qh.shop.core.RefundStatusEnum;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.*;
import net.kingsilk.wx4j.broker.api.wxCom.mp.at.WxComMpAtApi;
import net.kingsilk.wx4j.broker.api.wxCom.mp.user.GetResp;
import net.kingsilk.wx4j.broker.api.wxCom.mp.user.WxComMpUserApi;
import net.kingsilk.wx4j.client.mp.api.user.InfoResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderConvert {

    @Autowired
    private SkuRepo skuRepo;

    @Autowired
    private ItemPropRepo itemPropRepo;

    @Autowired
    private ItemPropValueRepo itemPropValueRepo;

    @Autowired
    private AdcRepo adcRepo;

    @Autowired
    private DeliverInvoiceRepo deliverInvoiceRepo;

    @Autowired
    private LogisticsRepo logisticsRepo;

    @Autowired
    private RefundRepo refundRepo;

    @Autowired
    private WxComMpAtApi wxComMpAtApi;

    @Autowired
    private BrandAppApi brandAppApi;

    @Autowired
    private WxComMpUserApi wxComMpUserApi;

    @Autowired
    private QhPayRepo qhPayRepo;

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private UserApi userApi;

    @Autowired
    private net.kingsilk.wx4j.client.mp.api.user.UserApi wxUserApi;

    public OrderResp toOrderResp(Order order) {
        OrderResp orderResp = new OrderResp();
        orderResp.setId(order.getId());
        orderResp.setSeq(order.getSeq());
        orderResp.setStatus(order.getStatus());
        orderResp.setStatusDesp(order.getStatus().getDesp());
        orderResp.setOrderPrice(order.getTotalPrice());
        orderResp.setShopId(order.getShopId());
        List<OrderItemInfo> orderItemInfos = new ArrayList<>();

        //订单商品信息
        orderResp.setTotal(0);
        order.getOrderItems().forEach(
                (Order.OrderItem orderItem) -> {
                    orderResp.setTotal(Integer.valueOf(orderItem.getNum()) + orderResp.getTotal());
                    OrderItemInfo orderItemInfo = new OrderItemInfo();
                    orderItemInfo.setNum(Integer.valueOf(orderItem.getNum()));
                    Sku sku = skuRepo.findOne(orderItem.getSkuId());
                    orderItemInfo.setItemTitle(sku.getTitle());
//                    orderItemInfo.setSkuTitle(sku.getTitle());
                    orderItemInfo.setSkuImg(sku.getImgs());
                    orderItemInfo.setSkuPrice(sku.getSalePrice());
                    orderItemInfo.setLabelPrice(sku.getLabelPrice());
                    orderItemInfo.setRealPayPrice(Integer.valueOf(orderItem.getRealPayPrice()));
                    List<OrderItemInfo.SpecInfo> specInfos = new ArrayList<>();
                    sku.getSpecs().forEach(
                            spec -> {
                                OrderItemInfo.SpecInfo specInfo = new OrderItemInfo.SpecInfo();
                                if (!StringUtils.isEmpty(spec.getItemPropId())) {
                                    specInfo.setPropName(itemPropRepo.findOne(spec.getItemPropId()).getName());
                                }
                                specInfo.setPropValue(itemPropValueRepo.findOne(spec.getItemPropValueId()).getName());
                                specInfos.add(specInfo);
                            });
                    orderItemInfo.setSpecInfos(specInfos);
                    orderItemInfos.add(orderItemInfo);
                }
        );
        orderResp.setOrderItems(orderItemInfos);
        orderResp.setPaymentPrice(order.getPaymentAmount());
        return orderResp;
    }


    public OrderInfoResp toOrderInfoResp(Order order) {
        OrderInfoResp orderInfoResp = new OrderInfoResp();
        List<OrderItemInfo> orderItemInfos = new ArrayList<>();
        orderInfoResp.setId(order.getId());
        orderInfoResp.setShopId(order.getShopId());
        orderInfoResp.setBuyerMemo(order.getBuyerMemo());
        orderInfoResp.setFreight(order.getFreight());
        order.getOrderItems().forEach(
                orderItem -> {
                    OrderItemInfo orderItemInfo = new OrderItemInfo();
                    orderItemInfo.setNum(Integer.valueOf(orderItem.getNum()));
                    orderItemInfo.setRealPayPrice(Integer.valueOf(orderItem.getRealPayPrice()));
                    orderItemInfo.setSkuId(orderItem.getSkuId());
                    orderItemInfo.setSkuPrice(Integer.valueOf(orderItem.getSkuPrice()));
                    Sku sku = skuRepo.findOne(orderItem.getSkuId());
                    orderItemInfo.setItemTitle(sku.getTitle());
                    orderItemInfo.setSkuImg(sku.getImgs());
                    orderItemInfo.setLabelPrice(sku.getLabelPrice());
                    List<OrderItemInfo.SpecInfo> specInfos = new ArrayList<>();

                    if (!StringUtils.isEmpty(orderItem.getRefundId())) {

                        Refund refund = refundRepo.findOne(
                                Expressions.allOf(
                                        QRefund.refund.id.eq(orderItem.getRefundId()),
                                        QRefund.refund.shopId.eq(order.getShopId())
                                )
                        );
                        if (!refund.getRefundStatus().equals(RefundStatusEnum.REJECTED)) {

                            if (refund.getRefundStatus().equals(RefundStatusEnum.FINISHED)) {
                                orderItemInfo.setRefundStatus("退款已完成");
                            } else {
                                orderItemInfo.setRefundStatus("退款中");
                            }
                        }
                    }


                    sku.getSpecs().forEach(
                            spec -> {
                                OrderItemInfo.SpecInfo specInfo = new OrderItemInfo.SpecInfo();
                                if (!StringUtils.isEmpty(spec.getItemPropId())) {
                                    specInfo.setPropName(itemPropRepo.findOne(spec.getItemPropId()).getName());
                                }
                                specInfo.setPropValue(itemPropValueRepo.findOne(spec.getItemPropValueId()).getName());
                                specInfos.add(specInfo);
                            });
                    orderItemInfo.setSpecInfos(specInfos);
                    orderItemInfo.setAdjustPrice(Integer.valueOf(orderItem.getAdjustedAmount()));
                    orderItemInfos.add(orderItemInfo);
                }
        );

        orderInfoResp.setOrderItems(orderItemInfos);
        orderInfoResp.setAdjustPrice(order.getAdjustedAmount());
        orderInfoResp.setOrderPrice(order.getTotalPrice());
        orderInfoResp.setPaymentPrice(order.getPaymentAmount());
        orderInfoResp.setStatus(order.getStatus());
        orderInfoResp.setStatusDesp(order.getStatus().getDesp());
        orderInfoResp.setSeq(order.getSeq());


        DeliverInvoice deliverInvoice = deliverInvoiceRepo.findOne(
                Expressions.allOf(
                        QDeliverInvoice.deliverInvoice.orderId.eq(order.getId()),
                        QDeliverInvoice.deliverInvoice.deleted.ne(true)
                )
        );
        if (deliverInvoice != null) {
            if (!StringUtils.isEmpty(deliverInvoice.getLogisticsesId())) {
                Logistics logistics = logisticsRepo.findOne(deliverInvoice.getLogisticsesId());
                LogisticInfo logisticInfo = new LogisticInfo();
                logisticInfo.setCompany(logistics.getCompany().getDesp());
                logisticInfo.setExpressNo(logistics.getExpressNo());
                logisticInfo.setCompanyNo(logistics.getCompany().getCode());
                orderInfoResp.getLogisticses().add(logisticInfo);
            }
        }
        if (order.getAddr() != null) {
            orderInfoResp.setAddress(toAddrModel(order.getAddr()));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        if (!StringUtils.isEmpty(order.getQhPayId())) {
            QhPay qhpay = qhPayRepo.findOne(order.getQhPayId());
            orderInfoResp.setPayType(qhpay.getPayType().getDesp());
            orderInfoResp.setPayTime(simpleDateFormat.format(qhpay.getPayTime()));
        }
        orderInfoResp.setDateCreated(simpleDateFormat.format(order.getDateCreated()));
        orderInfoResp.setOrderType(order.getSourceType().getCode());
        if (order.getSourceType().equals(OrderSourceTypeEnum.SINCE)) {
            orderInfoResp.setSinceTakeTime(order.getSinceTakeTime());
        }
        Member member = memberRepo.findOne(
                Expressions.allOf(
                        QMember.member.id.eq(order.getMemberId()),
                        QMember.member.shopId.eq(order.getShopId())
                )
        );
        UniResp<UserGetResp> oauthResp = userApi.get(member.getUserId());
        if (StringUtils.hasText(oauthResp.getData().getRealName())) {
            orderInfoResp.setRealName(oauthResp.getData().getRealName());
        } else {
            net.kingsilk.qh.platform.api.UniResp<BrandAppResp> respUniResp = brandAppApi.info(member.getBrandAppId());

            String wxComAppId = respUniResp.getData().getWxComAppId();
            String wxMpAppId = respUniResp.getData().getWxMpId();

            //获取本地accesstoken
            net.kingsilk.wx4j.broker.api.UniResp<net.kingsilk.wx4j.broker.api.wxCom.mp.at.GetResp> uniRes =
                    wxComMpAtApi.get(wxComAppId, wxMpAppId);

            //获取微信头像
            String openId = oauthResp.getData().getWxUsers().stream().filter(wxUser ->
                    wxMpAppId.equals(wxUser.getAppId())
            ).findFirst().orElse(new UserGetResp.WxUser()).getOpenId();
            if (StringUtils.hasText(openId)) {
                //查看用户是否关注微信公众号
                InfoResp infoResp = wxUserApi.info(uniRes.getData().getAccessToken(), openId, "zh_CN");
                if (StringUtils.hasText(infoResp.getNickName())) {
                    orderInfoResp.setRealName(infoResp.getNickName());
                } else {
                    net.kingsilk.wx4j.broker.api.UniResp<GetResp> wxResp
                            = wxComMpUserApi.get(wxComAppId, wxMpAppId, openId);
                    orderInfoResp.setRealName(wxResp.getData().getNickName());
                }

            }
        }
        orderInfoResp.setPhone(oauthResp.getData().getPhone());

        return orderInfoResp;
    }

    public AddrModel toAddrModel(Order.ShippingAddress address) {
        AddrModel addrModel = new AddrModel();

        addrModel.setPhone(address.getPhone());
        addrModel.setReceiver(address.getReceiver());
        addrModel.setStreet(address.getStreet());
        addrModel.setAdcNo(address.getAdc());
        Adc adc = adcRepo.findOneByNo(address.getAdc());
        if (!StringUtils.isEmpty(address.getAdc())) {
            addrModel.setCountyNo(adc.getNo());
            addrModel.setArea(adc.getName());
            if (adc.getParent() != null) {
                addrModel.setCity(adc.getParent().getName());
                addrModel.setCityNo(adc.getParent().getNo());
                if (adc.getParent().getParent() != null) {
                    addrModel.setProvince(adc.getParent().getParent().getName());
                    addrModel.setProvinceNo(adc.getParent().getParent().getNo());
                }
            }
        }
        return addrModel;
    }


}
