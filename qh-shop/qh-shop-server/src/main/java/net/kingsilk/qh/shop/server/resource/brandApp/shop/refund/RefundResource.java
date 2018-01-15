package net.kingsilk.qh.shop.server.resource.brandApp.shop.refund;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.refund.RefundApi;
import net.kingsilk.qh.shop.api.brandApp.shop.refund.dto.RefundInfoResp;
import net.kingsilk.qh.shop.core.OrderStatusEnum;
import net.kingsilk.qh.shop.core.RefundStatusEnum;
import net.kingsilk.qh.shop.core.RefundTypeEnum;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.OrderRepo;
import net.kingsilk.qh.shop.repo.RefundRepo;
import net.kingsilk.qh.shop.repo.SkuStoreRepo;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.refund.convert.RefundConvert;
import net.kingsilk.qh.shop.service.util.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Component
public class RefundResource implements RefundApi {

    @Autowired
    private RefundRepo refundRepo;

    @Autowired
    private SkuStoreRepo skuStoreRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private RefundConvert refundConvert;

    @Autowired
    @Qualifier(value = "mvcConversionService")
    private ConversionService conversionService;

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<RefundInfoResp> info(String brandAppId, String shopId, String id) {
        Refund refund = refundRepo.findOne(
                Expressions.allOf(
                        QRefund.refund.shopId.eq(shopId),
                        QRefund.refund.brandAppId.eq(brandAppId),
                        QRefund.refund.id.eq(id),
                        QRefund.refund.deleted.ne(true)
                ));
        if (refund == null) {
            throw new ErrStatusException(ErrStatus.FINDNULL, "该订单不存在");
        }
        UniResp<RefundInfoResp> uniResp = new UniResp<>();
        RefundInfoResp refundInfoResp = refundConvert.refundInfoRespConvert(refund);
        uniResp.setData(refundInfoResp);
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> agreeReturnGoods(
            String brandAppId,
            String shopId,
            String id,
            String memo) {
        Refund refund = refundRepo.findOne(
                Expressions.allOf(
                        QRefund.refund.shopId.eq(shopId),
                        QRefund.refund.brandAppId.eq(brandAppId),
                        QRefund.refund.id.eq(id),
                        QRefund.refund.deleted.ne(true)
                ));
        refund.setMemo(memo);

        if (refund.getRefundType().equals(RefundTypeEnum.ONLYMONEY)) {
            refund.setRefundStatus(RefundStatusEnum.FINISHED);
            // TODO 加库存
            // TODO 减销量
            Order order = orderRepo.findOne(refund.getOrderId());
            if (order.getStatus().equals(OrderStatusEnum.UNSHIPPED) || order.getStatus().equals(OrderStatusEnum.SINCEING) ||
                    order.getStatus().equals(OrderStatusEnum.UNCONFIRMED)) {
                refund.getSkus().forEach(
                        sku -> {
                            SkuStore skuStore = skuStoreRepo.findOne(
                                    Expressions.allOf(
                                            QSkuStore.skuStore.brandAppId.eq(brandAppId),
                                            QSkuStore.skuStore.shopId.eq(shopId),
                                            QSkuStore.skuStore.skuId.eq(sku.getSkuId())
                                    )
                            );
                            skuStore.setNum(skuStore.getNum() + sku.getApplyedNum());
                            skuStore.setSalesVolume(skuStore.getSalesVolume() - sku.getApplyedNum());
                            skuStoreRepo.save(skuStore);
                        }
                );
            }
        } else {
            refund.setRefundStatus(RefundStatusEnum.WAIT_BUYER_SENDING);
        }

        refundRepo.save(refund);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData("操作成功");
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> agreeRefund(
            String brandAppId,
            String shopId,
            String id) {

        Refund refund = refundRepo.findOne(
                Expressions.allOf(
                        QRefund.refund.shopId.eq(shopId),
                        QRefund.refund.brandAppId.eq(brandAppId),
                        QRefund.refund.id.eq(id),
                        QRefund.refund.deleted.ne(true)
                ));
        refund.setRefundStatus(RefundStatusEnum.FINISHED);

        // TODO 加库存
        // TODO 减销量
        Set<Refund.Sku> skus = refund.getSkus();

        skus.forEach(
                sku -> {
                    SkuStore skuStore = skuStoreRepo.findOne(
                            Expressions.allOf(
                                    QSkuStore.skuStore.brandAppId.eq(brandAppId),
                                    QSkuStore.skuStore.shopId.eq(shopId),
                                    QSkuStore.skuStore.skuId.eq(sku.getSkuId())
                            )
                    );
                    skuStore.setNum(skuStore.getNum() + sku.getApplyedNum());
                    skuStore.setSalesVolume(skuStore.getSalesVolume() - sku.getApplyedNum());
                    skuStoreRepo.save(skuStore);
                }
        );

        refundRepo.save(refund);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData("success");
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> reject(
            String brandAppId,
            String shopId,
            String id,
            String rejectReason) {
        Refund refund = refundRepo.findOne(
                Expressions.allOf(
                        QRefund.refund.shopId.eq(shopId),
                        QRefund.refund.brandAppId.eq(brandAppId),
                        QRefund.refund.id.eq(id),
                        QRefund.refund.deleted.ne(true)
                ));
        refund.setRefundStatus(RefundStatusEnum.REJECTED);
        refund.setRejectReason(rejectReason);
        refundRepo.save(refund);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData("success");
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<UniPageResp<RefundInfoResp>> page(
            String brandAppId,
            String shopId,
            int size,
            int page,
            List<String> sort,
            List<String> status,
            String keyWord) {

        PageRequest pageRequest = new PageRequest(page, size,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")));


        List<RefundStatusEnum> list = new ArrayList<>();
        if (status.size() > 0) {
            status.forEach(string ->
                    list.add(RefundStatusEnum.valueOf(string)));
        }

        List<RefundStatusEnum> defaultList = new ArrayList<>();
        defaultList.add(RefundStatusEnum.REFUNDFILED);
        defaultList.add(RefundStatusEnum.REJECTING);
        Page<Refund> refundPage = refundRepo.findAll(
                allOf(
                        QRefund.refund.brandAppId.eq(brandAppId),
                        QRefund.refund.shopId.eq(shopId),
                        QRefund.refund.deleted.ne(true),
                        status.size() > 0 ? DbUtil.opIn(QRefund.refund.refundStatus, list) :
                                DbUtil.opNotIn(QRefund.refund.refundStatus, defaultList),
                        !StringUtils.isEmpty(keyWord) ? QRefund.refund.seq.like("%" + keyWord + "%") : null),
                pageRequest
        );

        UniResp<UniPageResp<RefundInfoResp>> uniResp = new UniResp<>();

        Page<RefundInfoResp> resps = refundPage.map(
                refund ->
                        refundConvert.refundInfoRespConvert(refund)
        );
        UniPageResp<RefundInfoResp> uniPageResp = conversionService.convert(resps, UniPageResp.class);
        uniPageResp.setContent(resps.getContent());
        uniResp.setStatus(200);
        uniResp.setData(uniPageResp);

        return uniResp;
    }
}
