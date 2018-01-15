package net.kingsilk.qh.shop.server.resource.brandApp.shop.convert;

import net.kingsilk.qh.shop.api.brandApp.shop.dto.ShopResp;
import net.kingsilk.qh.shop.core.OrderStatusEnum;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.AdcRepo;
import net.kingsilk.qh.shop.repo.OrderRepo;
import net.kingsilk.qh.shop.repo.ShopAccountRepo;
import net.kingsilk.qh.shop.service.service.AddrService;
import net.kingsilk.qh.shop.service.util.DbUtil;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Component
public class ShopRespConvert {

    @Autowired
    private AdcRepo adcRepo;

    @Autowired
    private AddrService addrService;

    @Autowired
    private ShopAccountRepo shopAccountRepo;

    @Autowired
    private OrderRepo orderRepo;

    public ShopResp convert(Shop shop) {
        ShopResp shopResp = new ShopResp();
        shopResp.setAddress(shop.getAddress());
        shopResp.setBrandAppId(shop.getBrandAppId());
        shopResp.setImg(shop.getImg());

        shopResp.setExpireDate(shop.getExpireDate());
        shopResp.setName(shop.getName());

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        shopResp.setCurDate(calendar.getTime());

        shopResp.setPhone(shop.getPhone());
        shopResp.setTel(shop.getTel());
        shopResp.setAdcNo(shop.getAdcNo());

        ShopAccount shopAccount = shopAccountRepo.findOne(
                allOf(
                        QShopAccount.shopAccount.shopId.eq(shop.getId()),
                        QShopAccount.shopAccount.brandAppId.eq(shop.getBrandAppId())
                )
        );
        Assert.notNull(shopAccount, "店铺账号错误");
        shopResp.setTotalIncome(shopAccount.getTotalBalance());

        List<OrderStatusEnum> statusList = new ArrayList<>();
        statusList.add(OrderStatusEnum.UNCOMMITED);
        statusList.add(OrderStatusEnum.UNPAYED);
        statusList.add(OrderStatusEnum.CANCELED);
        statusList.add(OrderStatusEnum.CLOSED);

        Calendar todayStartCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        todayStartCalendar.set(todayStartCalendar.get(Calendar.YEAR), todayStartCalendar.get(Calendar.MONTH), todayStartCalendar.get(Calendar.DATE) - 1, 24, 0, 0);
        Date todayStartTime = todayStartCalendar.getTime();

        Long todayOrder = orderRepo.count(
                allOf(
                        DbUtil.opNotIn(QOrder.order.status, statusList),
                        QOrder.order.brandAppId.eq(shop.getBrandAppId()),
                        QOrder.order.shopId.eq(shop.getId()),
                        QOrder.order.dateCreated.after(todayStartTime)
                )
        );

        shopResp.setTodayOrder(Math.toIntExact(todayOrder));


        Calendar monthStartCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        monthStartCalendar.set(Calendar.DAY_OF_MONTH, 1);
        monthStartCalendar.set(monthStartCalendar.get(Calendar.YEAR), monthStartCalendar.get(Calendar.MONTH), monthStartCalendar.get(Calendar.DATE) - 1, 24, 0, 0);
        Date monthStartTime = monthStartCalendar.getTime();


        Iterator<Order> orders = orderRepo.findAll(
                allOf(
                        DbUtil.opNotIn(QOrder.order.status, statusList),
                        QOrder.order.brandAppId.eq(shop.getBrandAppId()),
                        QOrder.order.shopId.eq(shop.getId()),
                        QOrder.order.dateCreated.after(monthStartTime)
                )
        ).iterator();

        List<Order> orderList = IteratorUtils.toList(orders);
        Integer monthlyIncome = orderList.stream().mapToInt(Order::getPaymentAmount).sum();
        shopResp.setMonthlyIncome(monthlyIncome);


        Adc adc = adcRepo.findOneByNo(shop.getAdcNo());
        if (adc != null) {
            shopResp.setArea(adc.getName());
            if (adc.getParent() != null) {
                shopResp.setCityNo(adc.getParent().getNo());
                shopResp.setCity(adc.getParent().getName());
                if (adc.getParent().getParent() != null) {
                    shopResp.setProvinceNo(adc.getParent().getParent().getNo());
                    shopResp.setProvince(adc.getParent().getParent().getName());
                }
            }
            shopResp.setAddress(addrService.getAdcInfo(adc.getNo()));
        }

        shopResp.setDateCreated(shop.getDateCreated());
        shopResp.setShopType(shop.getShopType().getDesp());
        shopResp.setStatus(shop.getStatus().getCode());
        shopResp.setId(shop.getId());
        shopResp.setShopTypeCode(shop.getShopType().getCode());
        shopResp.setDetailAddr(shop.getAddress());
        return shopResp;
    }
}
