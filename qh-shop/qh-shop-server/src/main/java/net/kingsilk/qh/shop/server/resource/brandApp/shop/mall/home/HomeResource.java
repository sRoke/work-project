package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.home;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.home.HomeApi;
import net.kingsilk.qh.shop.core.OrderStatusEnum;
import net.kingsilk.qh.shop.core.RefundStatusEnum;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.MemberRepo;
import net.kingsilk.qh.shop.repo.OrderRepo;
import net.kingsilk.qh.shop.repo.RefundRepo;
import net.kingsilk.qh.shop.repo.ShopRepo;
import net.kingsilk.qh.shop.service.service.SecService;
import net.kingsilk.qh.shop.service.util.DbUtil;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Component("mallHomeResource")
public class HomeResource implements HomeApi {


    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private RefundRepo refundRepo;

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private SecService secService;

    @Override
    public UniResp<String> get(
            String brandAppId,
            String shopId) {

        UniResp<String> uniResp = new UniResp<>();
        if (shopId == null) {
            uniResp.setStatus(ErrStatus.PARAMNUll);
            uniResp.setMessage("shopId错误");
            return uniResp;
        }

        Shop shop = shopRepo.findOne(
                allOf(
                        QShop.shop.brandAppId.eq(brandAppId),
                        QShop.shop.id.eq(shopId),
                        QShop.shop.deleted.ne(true)
                )
        );

        if (shop == null) {
            uniResp.setStatus(ErrStatus.FINDNULL);
            uniResp.setMessage("无有效门店");
            return uniResp;
        }

        uniResp.setData(shop.getName());
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    @Override
    public UniResp<Map<String, Long>> getShopOrderNum(String brandAppId, String shopId) {

        String curUserId = secService.curUserId();

        Member member = memberRepo.findOne(
                Expressions.allOf(
                        QMember.member.shopId.eq(shopId),
                        QMember.member.brandAppId.eq(brandAppId),
                        QMember.member.userId.eq(curUserId),
                        QMember.member.deleted.ne(true),
                        QMember.member.enable.eq(true)
                )
        );

        List<Order> orders = Lists.newArrayList(orderRepo.findAll(
                Expressions.allOf(
                        QOrder.order.shopId.eq(shopId),
                        QOrder.order.memberId.eq(member.getId()),
                        QOrder.order.deleted.ne(true),
                        QOrder.order.brandAppId.eq(brandAppId)
                )
        ));

        Long size = (long) Lists.newArrayList(refundRepo.findAll(
                Expressions.allOf(
                        QRefund.refund.deleted.ne(true),
                        QRefund.refund.shopId.eq(shopId),
                        QRefund.refund.memberId.eq(member.getId()),
                        QRefund.refund.brandAppId.eq(brandAppId),
                        DbUtil.opIn(QRefund.refund.refundStatus, Lists.newArrayList(RefundStatusEnum.REFUNDING, RefundStatusEnum.UNCHECKED, RefundStatusEnum.WAIT_BUYER_SENDING, RefundStatusEnum.WAIT_SELLER_RECEIVED))
                )
        )).size();

        Map<String, Long> map = new HashMap<>();

        map.put(OrderStatusEnum.UNPAYED.getCode(), orders.stream().filter(it ->
                OrderStatusEnum.UNPAYED.equals(it.getStatus())
        ).count());

        map.put(OrderStatusEnum.UNSHIPPED.getCode(), orders.stream().filter(it ->
                OrderStatusEnum.UNSHIPPED.equals(it.getStatus())||OrderStatusEnum.UNCONFIRMED.equals(it.getStatus())
        ).count());

        map.put(OrderStatusEnum.UNRECEIVED.getCode(), orders.stream().filter(it ->
                OrderStatusEnum.UNRECEIVED.equals(it.getStatus())|| OrderStatusEnum.SINCEING.equals(it.getStatus())
        ).count());

        map.put(RefundStatusEnum.REFUNDING.getCode(), size);

        UniResp<Map<String, Long>> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData(map);
        return uniResp;
    }
}
