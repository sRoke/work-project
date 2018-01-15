package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.order.convert;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.oauth.api.UniResp;
import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.UserGetResp;
import net.kingsilk.qh.oauth.api.user.addr.AddrGetResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.addr.dto.AddrModel;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.order.dto.AddressInfo;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.order.dto.OrderInfoResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.order.dto.OrderItemInfo;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.order.dto.OrderMiniInfo;
import net.kingsilk.qh.shop.api.brandApp.shop.order.dto.LogisticInfo;
import net.kingsilk.qh.shop.core.RefundStatusEnum;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.*;
import net.kingsilk.qh.shop.service.service.AddrService;
import net.kingsilk.qh.shop.service.service.SecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Component("mallOrderConvert")
public class OrderConvert {

    @Autowired
    private SkuRepo skuRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ItemPropRepo itemPropRepo;

    @Autowired
    private ItemPropValueRepo itemPropValueRepo;

    @Autowired
    private QhPayRepo qhPayRepo;

    @Autowired
    private AdcRepo adcRepo;

    @Autowired
    private SecService secService;

    @Autowired
    private DeliverInvoiceRepo deliverInvoiceRepo;

    @Autowired
    private LogisticsRepo logisticsRepo;

    @Autowired
    private UserApi userApi;

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

//    @Autowired
//    private MemberRepo memberRepo;

    @Autowired
    private RefundRepo refundRepo;


    public OrderMiniInfo orderMiniInfoConvert(Order order) {
        OrderMiniInfo info = new OrderMiniInfo();
        info.setId(order.getId());
        info.setSeq(order.getSeq());
//        info.setRealName(order.get);
        info.setDateCreated(order.getDateCreated());
        info.setOrderPrice(order.getTotalPrice());
        info.setStatus(order.getStatus());
        info.setStatusDesp(order.getStatus().getDesp());
        //todo  oauth中地址的姓名和手机
        if (order.getAddr() != null) {
            info.setPhone(order.getAddr().getPhone());
            info.setRealName(order.getAddr().getReceiver());    //TODO 下单者member
        }
        info.setPaymentPrice(order.getPaymentAmount());
        info.setTotal(0);

        order.getOrderItems().forEach(
                orderItem -> {
                    Sku sku = skuRepo.findOne(orderItem.getSkuId());
                    Item item = itemRepo.findOne(sku.getItemId());
                    OrderItemInfo orderItemInfo = new OrderItemInfo();
                    orderItemInfo.setSkuImg(sku.getImgs().iterator().next());
                    orderItemInfo.setSkuId(sku.getId());
                    orderItemInfo.setItemTitle(sku.getTitle()); //todo item的标题都放在sku中？
                    orderItemInfo.setSkuTitle(sku.getTitle());
                    orderItemInfo.setSkuPrice(Integer.parseInt(orderItem.getSkuPrice()));  //用下单时的价格
                    orderItemInfo.setLabelPrice(sku.getLabelPrice());
                    orderItemInfo.setCode(sku.getCode());
                    orderItemInfo.setNum(Integer.parseInt(orderItem.getNum()));
                    orderItemInfo.setAdjustPrice(Integer.valueOf(orderItem.getAdjustedAmount()));
                    orderItemInfo.setRealPayPrice(Integer.valueOf(orderItem.getRealPayPrice()));
                    if (orderItem.getAllRealPayPrice() != null) {
                        orderItemInfo.setAllRealPayPrice(Integer.parseInt(orderItem.getAllRealPayPrice()));
                    }
                    if (orderItem.getRefundId() != null) {
                        Refund refund = refundRepo.findOne(orderItem.getRefundId());
                        orderItemInfo.setRefundId(orderItem.getRefundId());
                        if (refund.getRefundStatus().equals(RefundStatusEnum.valueOf("FINISHED"))) {
                            orderItemInfo.setRefundStatus("FINISHED");
                            orderItemInfo.setRefundStatusDesp("退款已完成");
                        }else {
                            orderItemInfo.setRefundStatus("REFUNDING");
                            orderItemInfo.setRefundStatusDesp("退款中");
                        }
                    }
                    info.setTotal(info.getTotal() + Integer.parseInt(orderItem.getNum()));

                    sku.getSpecs().forEach(spec -> {
                        OrderItemInfo.SpecInfo specInfo = new OrderItemInfo.SpecInfo();
                        if (!StringUtils.isEmpty(spec.getItemPropId())) {
                            ItemProp itemProp = itemPropRepo.findOne(spec.getItemPropId());
                            specInfo.setPropName(itemProp.getName());
                        }
                        ItemPropValue propValue = itemPropValueRepo.findOne(spec.getItemPropValueId());
                        specInfo.setPropValue(propValue.getName());
                        orderItemInfo.getSpecInfos().add(specInfo);
                    });
                    info.getOrderItems().add(orderItemInfo);
                });
        return info;
    }


    public OrderInfoResp orderInfoConvert(Order order) {

        OrderInfoResp infoResp = new OrderInfoResp();
        infoResp.setId(order.getId());
        infoResp.setSeq(order.getSeq());

        if (!StringUtils.isEmpty(order.getQhPayId())) {
            QhPay qhPay = qhPayRepo.findOne(order.getQhPayId());
            infoResp.setPayType(qhPay.getPayType().getCode());
            infoResp.setPayTypeDesp(qhPay.getPayType().getDesp());
            SimpleDateFormat format =
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            infoResp.setPayTime(format.format(qhPay.getPayTime()));
        }
        infoResp.setOrderPrice(order.getTotalPrice());
        infoResp.setAdjustPrice(order.getAdjustedAmount());
        infoResp.setFreight(order.getFreight());
        infoResp.setPaymentPrice(order.getPaymentAmount());
        infoResp.setStatus(order.getStatus());
        infoResp.setStatusDesp(order.getStatus().getDesp());
        infoResp.setDateCreated(order.getDateCreated());
        infoResp.setBuyerMemo(order.getBuyerMemo());
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        try {
            infoResp.setSinceTakeTime(format.format(Optional.ofNullable(order.getSinceTakeTime()).orElse(new Date())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (order.getSourceType() != null) {
            infoResp.setOrderSourceType(order.getSourceType());
            infoResp.setOrderSourceTypeDesp(order.getSourceType().getDesp());
        }
        infoResp.setTotal(0);

        //todo 会员得信息是否要与 oauth 同步
//        if (!StringUtils.isEmpty(order.getMemberId())) {
//            Member member = memberRepo.findOne(order.getMemberId());
//            if (member != null || member.getRealName() != null) {
//                infoResp.setRealName(member.getRealName());
//            }
//        }
        //下单人
        String userId = secService.curUserId();
        UniResp<UserGetResp> userGetRespUniResp = userApi.get(userId);
        if (userGetRespUniResp != null || userGetRespUniResp.getData() != null) {
            UserGetResp userGetResp = userGetRespUniResp.getData();
            infoResp.setRealName(userGetResp.getRealName());
            infoResp.setPhone(userGetResp.getPhone());
        }

        //地址转换
        AddressInfo addressInfo = new AddressInfo();
        Optional.ofNullable(order.getAddr()).ifPresent(it ->
                infoResp.setAddress(conversionService.convert(it, AddrModel.class))
        );
//        if (!Optional.ofNullable(order.getAddr()).isPresent()) {
//            AddrGetResp addrGetResp = addrService.getDefault(userId, "USER_SHIPPING_ADDR").getData();
//            if (addrGetResp != null) {
//                infoResp.setAddress(conversionService.convert(addrGetResp, AddrModel.class));
//            }
//        }

        //sku
        order.getOrderItems().forEach(
                orderItem -> {
                    OrderItemInfo itemInfo = new OrderItemInfo();
                    Sku sku = skuRepo.findOne(orderItem.getSkuId());
                    Item item = itemRepo.findOne(sku.getItemId());
                    itemInfo.setSkuImg(sku.getImgs().iterator().next());
                    itemInfo.setItemTitle(item.getTitle());
                    itemInfo.setSkuTitle(item.getTitle());
                    itemInfo.setSkuPrice(sku.getSalePrice());
                    itemInfo.setLabelPrice(sku.getLabelPrice());
                    itemInfo.setCode(sku.getCode());
                    itemInfo.setSkuId(orderItem.getSkuId());
                    itemInfo.setNum(Integer.parseInt(orderItem.getNum()));
                    infoResp.setTotal(infoResp.getTotal() + Integer.parseInt(orderItem.getNum()));
                    itemInfo.setAdjustPrice(Integer.valueOf(orderItem.getAdjustedAmount()));
                    itemInfo.setRealPayPrice(Integer.valueOf(orderItem.getRealPayPrice()));
                    if (orderItem.getAllRealPayPrice() != null) {
                        itemInfo.setAllRealPayPrice(Integer.parseInt(orderItem.getAllRealPayPrice()));
                    }
                    if (!StringUtils.isEmpty(orderItem.getRefundId())) {

                        Refund refund = refundRepo.findOne(
                                Expressions.allOf(
                                        QRefund.refund.id.eq(orderItem.getRefundId()),
                                        QRefund.refund.brandAppId.eq(order.getBrandAppId())
//                                        QRefund.refund.shopId.eq(order.getShopId())
                                )
                        );
                        if (!refund.getRefundStatus().equals(RefundStatusEnum.REJECTED)) {
                            itemInfo.setRefundId(orderItem.getRefundId());
                            if (refund.getRefundStatus().equals(RefundStatusEnum.FINISHED)) {
                                itemInfo.setRefundStatus("FINISHED");
                                itemInfo.setRefundStatusDesp("退款已完成");
                            }else {
                                itemInfo.setRefundStatus("REFUNDING");
                                itemInfo.setRefundStatusDesp("退款中");
                            }

//                            if (refund.getRefundStatus().equals(RefundStatusEnum.FINISHED)) {
//                                itemInfo.setRefundStatus("退款已完成");
//                            } else {
//                                itemInfo.setRefundStatus("退款中");
//                            }
                        }
                    }

                    //sku属性名及对应值
                    sku.getSpecs().forEach(itemSpec -> {
                        OrderItemInfo.SpecInfo specInfo = new OrderItemInfo.SpecInfo();
                        ;
                        if (!StringUtils.isEmpty(itemSpec.getItemPropId())) {
                            ItemProp itemProp = itemPropRepo.findOne(itemSpec.getItemPropId());
                            specInfo.setPropName(itemProp.getName());
                        }
                        ItemPropValue propValue = itemPropValueRepo.findOne(itemSpec.getItemPropValueId());
                        specInfo.setPropValue(propValue.getName());
                        itemInfo.getSpecInfos().add(specInfo);
                    });

                    infoResp.getOrderItems().add(itemInfo);
                });

        //物流信息
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
//                logisticInfo.setCompanyNo(logistics.getCompany().getCode());
                infoResp.getLogisticses().add(logisticInfo);
            }
        }
        return infoResp;
    }

    //地址转换
    public void addressInfoConvert(AddressInfo addressInfo, Order order) {
        if (order.getAddr() != null) {
            Order.ShippingAddress address = order.getAddr();
            addressInfo.setReceiver(address.getReceiver());
            addressInfo.setStreet(address.getStreet());
            addressInfo.setPhone(address.getPhone());
            Adc adc = adcRepo.findOneByNo(address.getAdc());
            if (address.getAdc() != null) {
                addressInfo.setCity(adc.getName());
                addressInfo.setCountyNo(adc.getNo());
                if (adc.getParent() != null) {
                    addressInfo.setCity(adc.getParent().getName());
                    addressInfo.setCityNo(adc.getParent().getNo());
                    if (adc.getParent().getParent() != null) {
                        addressInfo.setProvince(adc.getParent().getParent().getName());
                        addressInfo.setProvinceNo(adc.getParent().getParent().getNo());
                    }
                }
            }
        }
    }


//    private AddressInfo address;
//    private List<OrderItemInfo> orderItems = new ArrayList<OrderItemInfo>();

//    private Boolean haveRefund = false;
    //    private String realName;
//    private String phone;
}
