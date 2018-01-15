package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.refund.convert;

import net.kingsilk.qh.oauth.api.UniResp;
import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.UserGetResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.refund.dto.RefundInfoResp;
import net.kingsilk.qh.shop.api.brandApp.shop.refund.dto.AddressInfo;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.*;
import net.kingsilk.qh.shop.service.service.AddrService;
import net.kingsilk.qh.shop.service.service.SecService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Component("mallRefundConvert")
public class RefundConvert {

    @Autowired
    private LogisticsRepo logisticsRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private QhPayRepo qhPayRepo;

    @Autowired
    private AddressConvert addressConvert;

    @Autowired
    private AddrService addrService;

    @Autowired
    private SkuRepo skuRepo;

    @Autowired
    private SecService secService;

    @Autowired
    private ItemPropRepo itemPropRepo;

    @Autowired
    private ItemPropValueRepo itemPropValueRepo;

    @Autowired
    private UserApi userApi;

    public RefundInfoResp infoRespConvert(Refund refund) {
        //基本信息
        RefundInfoResp infoResp = new RefundInfoResp();
        infoResp.setAliAmount(refund.getAliAmount());
        infoResp.setBrandAppId(refund.getBrandAppId());
        infoResp.setSeq(refund.getSeq());
        infoResp.setShopId(refund.getShopId());
        infoResp.setId(refund.getId());
        infoResp.setOrderId(refund.getOrderId());
        infoResp.setRefundStatus(refund.getRefundStatus());
        infoResp.setRefundStatusDesp(refund.getRefundStatus().getDesp());
        infoResp.setRefundType(refund.getRefundType());
        infoResp.setRefundTypeDesp(refund.getRefundType().getDesp());
//        infoResp.setPayTime(refund.);
        infoResp.setRefundMoney(refund.getRefundMoney());


        infoResp.setAdjustMoney(refund.getAdjustMoney());
        infoResp.setWxAmount(refund.getWxAmount());
        infoResp.setReason(refund.getReason());
        infoResp.setMemo(refund.getMemo());
        Set<RefundInfoResp.Sku> skus = new LinkedHashSet<>();
        refund.getSkus().forEach(  //一个订单只会对应一个sku
                sku -> {
                    RefundInfoResp.Sku sku1 = new RefundInfoResp.Sku();
                    infoResp.setRefundPrice((int)Math.rint(refund.getRefundMoney()/sku.getApplyedNum()));
                    Sku sku2 = skuRepo.findOne(sku.getSkuId());
                    sku1.setTitle(sku2.getTitle());
                    sku1.setApplyedNum(sku.getApplyedNum());
                    Order order = orderRepo.findOne(refund.getOrderId());
                    sku1.setOrderPrice(Integer.parseInt(order.getOrderItems().stream()
                            .filter(orderItem ->
                                    orderItem.getSkuId().equals(sku.getSkuId())).findFirst()
                            .orElse(new Order().new OrderItem()).getAllRealPayPrice()));
                    sku1.setSkuPrice(sku.getSkuPrice());
                    sku1.setSkuImg(sku2.getImgs());
                    sku1.setSkuId(sku.getSkuId());
                    List<RefundInfoResp.Sku.SkuSpec> specList = new ArrayList<>();
                    sku2.getSpecs().forEach(
                            spec -> {
                                RefundInfoResp.Sku.SkuSpec skuSpec = new RefundInfoResp.Sku.SkuSpec();
                                if (!StringUtils.isEmpty(spec.getItemPropId())) {
                                    skuSpec.setPropName(itemPropRepo.findOne(spec.getItemPropId()).getName());
                                }
                                skuSpec.setPropValue(itemPropValueRepo.findOne(spec.getItemPropValueId()).getName());
                                specList.add(skuSpec);
                            }
                    );
                    sku1.setSkuSpecs(specList);
                    skus.add(sku1);
                }
        );
        infoResp.setSkus(skus);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        infoResp.setRejectReason(refund.getRejectReason());
        if (refund.getExpressStatus() != null) {
            infoResp.setExpressStatus(refund.getExpressStatus());
            infoResp.setExpressStatusDesp(refund.getExpressStatus().getDesp());
            infoResp.setBuyerDeliveredTime(sdf.format(refund.getBuyerDeliveredTime()));     //TODO
            infoResp.setReceivedTime(sdf.format(refund.getReceivedTime()));
        }
        infoResp.setApplyTime(sdf.format(refund.getDateCreated()));
        //物流信息
        if (!StringUtils.isEmpty(refund.getLogisticsId())) {
            Logistics logistics = logisticsRepo.findOne(refund.getLogisticsId());
            if (logistics != null) {
                infoResp.setCompany(logistics.getCompany());
                infoResp.setCompanyName(logistics.getCompany().getDesp());
                infoResp.setExpressNo(logistics.getExpressNo());
            }
        }

        String userId = secService.curUserId();
        //退款人（下单人）
        UniResp<UserGetResp> userGetResp = userApi.get(userId);
        if (userGetResp != null || userGetResp.getData() != null) {
            infoResp.setRealName(userGetResp.getData().getRealName());
            infoResp.setPhone(userGetResp.getData().getPhone());
        }
        //地址信息
        if (refund.getRefundAddress() != null) {
            infoResp.setAddrInfo(addressConvert.toAddrModel(refund.getRefundAddress()));
        }

//        if (order != null) {
////            UniResp<AddrGetResp> user_shipping_addr = addrService.getDefault(userId, "USER_SHIPPING_ADDR");
////            addressConvert.aouthToShopAddrConvert(infoResp, user_shipping_addr.getData());
//            AddressInfo addressInfo = new AddressInfo();
//            Order.ShippingAddress shippingAddress = order.getAddr();
//            addressInfo.setReceiver(shippingAddress.getReceiver());
//            addressInfo.setPhone(shippingAddress.getPhone());
//            addressInfo.setStreet(shippingAddress.getStreet());
//            addressInfo.setCountyNo(shippingAddress.getAdc());
//            infoResp.setAddrInfo(addressInfo);
//        }
        Order order = orderRepo.findOne(refund.getOrderId());
        //支付信息
        if (order != null) {
            QhPay qhPay = qhPayRepo.findOne(order.getQhPayId());
            if (qhPay != null) {
                infoResp.setPayTime(sdf.format(qhPay.getPayTime()));
            }
        }
        return infoResp;
    }
}

