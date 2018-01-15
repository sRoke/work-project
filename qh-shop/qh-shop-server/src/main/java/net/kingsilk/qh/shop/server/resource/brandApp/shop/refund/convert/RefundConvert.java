package net.kingsilk.qh.shop.server.resource.brandApp.shop.refund.convert;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.oauth.api.UniResp;
import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.UserGetResp;
import net.kingsilk.qh.platform.api.brandApp.BrandAppApi;
import net.kingsilk.qh.platform.api.brandApp.dto.BrandAppResp;
import net.kingsilk.qh.shop.api.brandApp.shop.refund.dto.AddressInfo;
import net.kingsilk.qh.shop.api.brandApp.shop.refund.dto.RefundInfoResp;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Component
public class RefundConvert {

    @Autowired
    private LogisticsRepo logisticsRepo;

    @Autowired
    private AdcRepo adcRepo;

    @Autowired
    private ItemPropRepo itemPropRepo;

    @Autowired
    private ItemPropValueRepo itemPropValueRepo;

    @Autowired
    private SkuRepo skuRepo;

    @Autowired
    private UserApi userApi;

    @Autowired
    private net.kingsilk.wx4j.client.mp.api.user.UserApi wxUserApi;

    @Autowired
    private WxComMpAtApi wxComMpAtApi;

    @Autowired
    private WxComMpUserApi wxComMpUserApi;

    @Autowired
    private BrandAppApi brandAppApi;

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private OrderRepo orderRepo;

    public RefundInfoResp refundInfoRespConvert(Refund refund) {
        RefundInfoResp infoResp = new RefundInfoResp();
        infoResp.setBrandAppId(refund.getBrandAppId());
        infoResp.setSeq(refund.getSeq());
        infoResp.setOrderId(refund.getOrderId());
        infoResp.setId(refund.getId());
        infoResp.setShopId(refund.getShopId());
        infoResp.setWxAmount(refund.getWxAmount());
        infoResp.setAliAmount(refund.getAliAmount());
        infoResp.setRefundStatus(refund.getRefundStatus());
        infoResp.setRefundStatusDesp(refund.getRefundStatus().getDesp());
        infoResp.setRefundType(refund.getRefundType());
        infoResp.setRefundTypeDesp(refund.getRefundType().getDesp());
        if (!StringUtils.isEmpty(refund.getLogisticsId())) {
            Logistics logistics = logisticsRepo.findOne(refund.getLogisticsId());
            infoResp.setCompany(logistics.getCompany());
            infoResp.setCompanyName(logistics.getCompany().getDesp());
            infoResp.setExpressNo(logistics.getExpressNo());
            infoResp.setExpressStatusDesp(logistics.getStatus().getDesp());
            infoResp.setStatus(logistics.getStatus());
        }
        infoResp.setMemo(refund.getMemo());
        infoResp.setReason(refund.getReason());
        infoResp.setRejectReason(refund.getRejectReason());
        if (refund.getRefundAddress() != null) {
            infoResp.setAddrInfo(toAddrModel(refund.getRefundAddress()));
        }
        Set<RefundInfoResp.Sku> skuSet = new LinkedHashSet<>();
        refund.getSkus().forEach(
                sku -> {
                    RefundInfoResp.Sku sku1 = new RefundInfoResp.Sku();
                    sku1.setApplyedNum(sku.getApplyedNum());
                    sku1.setSkuId(sku.getSkuId());
                    Sku sku2 = skuRepo.findOne(sku.getSkuId());
                    List<RefundInfoResp.Sku.SkuSpec> skuSpecs = new ArrayList<>();
                    sku2.getSpecs().forEach(
                            spec -> {
                                RefundInfoResp.Sku.SkuSpec skuSpec = new RefundInfoResp.Sku.SkuSpec();
                                if (!StringUtils.isEmpty(spec.getItemPropId())) {
                                    skuSpec.setPropName(itemPropRepo.findOne(spec.getItemPropId()).getName());
                                }
                                skuSpec.setPropValue(itemPropValueRepo.findOne(spec.getItemPropValueId()).getName());
                                skuSpecs.add(skuSpec);
                            }
                    );
                    sku1.setSkuSpecs(skuSpecs);
                    sku1.setSkuPrice(sku.getSkuPrice());
                    Order order = orderRepo.findOne(refund.getOrderId());
                    sku1.setOrderPrice(Integer.parseInt(order.getOrderItems().stream()
                            .filter(orderItem ->
                                    orderItem.getSkuId().equals(sku.getSkuId())).findFirst()
                            .orElse(new Order().new OrderItem()).getAllRealPayPrice()));
                    sku1.setTitle(sku2.getTitle());
                    sku1.setSkuImg(sku2.getImgs());
                    skuSet.add(sku1);
                }
        );

        infoResp.setSkus(skuSet);
        infoResp.setRefundMoney(refund.getRefundMoney());

        //todo 去掉注释
        Member member = memberRepo.findOne(
                Expressions.allOf(
                        QMember.member.brandAppId.eq(refund.getBrandAppId()),
                        QMember.member.shopId.eq(refund.getShopId()),
                        QMember.member.id.eq(refund.getMemberId())
                )
        );

        UniResp<UserGetResp> oauthResp = userApi.get(member.getUserId());

        if (StringUtils.hasText(oauthResp.getData().getRealName())) {
            infoResp.setRealName(oauthResp.getData().getRealName());
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
                InfoResp infoRes = wxUserApi.info(uniRes.getData().getAccessToken(), openId, "zh_CN");
                if (StringUtils.hasText(infoRes.getNickName())) {
                    infoResp.setRealName(infoRes.getNickName());
                } else {
                    net.kingsilk.wx4j.broker.api.UniResp<GetResp> wxResp
                            = wxComMpUserApi.get(wxComAppId, wxMpAppId, openId);
                    infoResp.setRealName (wxResp.getData().getNickName());
                }
                infoResp.setRealName(infoRes.getNickName());
            }
        }
        infoResp.setPhone(oauthResp.getData().getPhone());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        infoResp.setBuyerDeliveredTime(sdf.format(refund.getDateCreated()));
        return infoResp;
    }


    public AddressInfo toAddrModel(Refund.RefundAddress refundAddress) {
        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setPhone(refundAddress.getPhone());
        addressInfo.setReceiver(refundAddress.getReceiver());
        addressInfo.setStreet(refundAddress.getStreet());
        addressInfo.setAddress(refundAddress.getAdc());
        Adc adc = adcRepo.findOneByNo(refundAddress.getAdc());
        if (!StringUtils.isEmpty(refundAddress.getAdc())) {
            addressInfo.setCountyNo(adc.getNo());
            addressInfo.setCounty(adc.getName());
            if (adc.getParent() != null) {
                addressInfo.setCity(adc.getParent().getName());
                addressInfo.setCityNo(adc.getParent().getNo());
                if (adc.getParent().getParent() != null) {
                    addressInfo.setProvince(adc.getParent().getParent().getName());
                    addressInfo.setProvinceNo(adc.getParent().getParent().getNo());
                }
            }
        }
        return addressInfo;
    }
}
