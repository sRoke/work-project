package net.kingsilk.qh.shop.server.resource.brandApp.shop.deliverInvoice;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.deliverInvoice.DeliverInvoiceApi;
import net.kingsilk.qh.shop.api.brandApp.shop.deliverInvoice.dto.DeliverInvoiceShipReq;
import net.kingsilk.qh.shop.core.DeliverStatusEnum;
import net.kingsilk.qh.shop.core.LogisticsCompanyEnum;
import net.kingsilk.qh.shop.core.OrderStatusEnum;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.*;
import net.kingsilk.qh.shop.service.service.SecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DeliverInvoiceResource implements DeliverInvoiceApi {
    @Autowired
    private DeliverInvoiceRepo deliverInvoiceRepo;

    @Autowired
    private DeliverInvoiceLogRepo deliverInvoiceLogRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private LogisticsRepo logisticsRepo;

    @Autowired
    private SecService secService;

    @Autowired
    private ShopStaffRepo shopStaffRepo;

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> logistics(
            String brandAppId,
            String shopId,
            String id,
            DeliverInvoiceShipReq deliverInvoiceShipReq) {

        String userId = secService.curUserId();

        ShopStaff shopStaff = shopStaffRepo.findOne(
                Expressions.allOf(
                        QShopStaff.shopStaff.userId.eq(userId),
                        QShopStaff.shopStaff.shopId.eq(shopId),
                        QShopStaff.shopStaff.brandAppId.eq(brandAppId),
                        QShopStaff.shopStaff.deleted.ne(true)
                )
        );
        UniResp<String> uniResp = new UniResp<>();
        if (shopStaff == null) {
            uniResp.setStatus(ErrStatus.FINDNULL);
            uniResp.setData("你不是商家员工");
            return uniResp;
        }

        DeliverInvoice deliverInvoice = deliverInvoiceRepo.findOne(
                Expressions.allOf(
                        QDeliverInvoice.deliverInvoice.brandAppId.eq(brandAppId),
                        QDeliverInvoice.deliverInvoice.shopId.eq(shopId),
                        QDeliverInvoice.deliverInvoice.deleted.ne(true),
                        QDeliverInvoice.deliverInvoice.orderId.eq(id)
                )
        );

        if (deliverInvoice == null) {
            uniResp.setStatus(ErrStatus.FINDNULL);
            uniResp.setData("未找到该发货单");
            return uniResp;
        }
        Order order = orderRepo.findOne(id);

        if (order != null) {
            Logistics logistics = null;
            if (!StringUtils.isEmpty(deliverInvoice.getLogisticsesId())) {
                logistics = logisticsRepo.findOne(deliverInvoice.getLogisticsesId());
            }
            if (logistics == null) {
                logistics = new Logistics();
            }
            logistics.setCompany(LogisticsCompanyEnum.valueOf(deliverInvoiceShipReq.getCompany()));
            logistics.setExpressNo(deliverInvoiceShipReq.getExpressNo());
            logistics = logisticsRepo.save(logistics);

            deliverInvoice.setLogisticsesId(logistics.getId());
        }
        deliverInvoice.setDeliverStaff(shopStaff.getId());
        deliverInvoiceRepo.save(deliverInvoice);
        uniResp.setStatus(200);
        uniResp.setData("保存成功！");
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> ship(String brandAppId, String shopId, String id) {

        String userId = secService.curUserId();

        ShopStaff shopStaff = shopStaffRepo.findOne(
                Expressions.allOf(
                        QShopStaff.shopStaff.userId.eq(userId),
                        QShopStaff.shopStaff.shopId.eq(shopId),
                        QShopStaff.shopStaff.brandAppId.eq(brandAppId),
                        QShopStaff.shopStaff.deleted.ne(true)
                )
        );
        UniResp<String> uniResp = new UniResp<>();
        if (shopStaff == null) {
            uniResp.setStatus(ErrStatus.FINDNULL);
            uniResp.setData("你不是商家员工");
            return uniResp;
        }

        DeliverInvoice deliverInvoice = deliverInvoiceRepo.findOne(
                Expressions.allOf(
                        QDeliverInvoice.deliverInvoice.brandAppId.eq(brandAppId),
                        QDeliverInvoice.deliverInvoice.shopId.eq(shopId),
                        QDeliverInvoice.deliverInvoice.deleted.ne(true),
                        QDeliverInvoice.deliverInvoice.orderId.eq(id)
                )
        );

        deliverInvoice.setDeliverStatus(DeliverStatusEnum.UNRECEIVED);
        deliverInvoiceRepo.save(deliverInvoice);

        Order order = orderRepo.findOne(id);
        order.setStatus(OrderStatusEnum.UNRECEIVED);
        orderRepo.save(order);

        Logistics logistics = logisticsRepo.findOne(deliverInvoice.getLogisticsesId());
        DeliverInvoiceLog deliverInvoiceLog = new DeliverInvoiceLog();
        deliverInvoiceLog.setShopId(shopId);
        deliverInvoiceLog.setDeliverInvoiceId(deliverInvoice.getId());
        deliverInvoiceLog.setBrandAppId(brandAppId);
        deliverInvoiceLog.setOrderId(order.getId());
        deliverInvoiceLog.setDeliverStaffId(shopStaff.getId());
        deliverInvoiceLog.setDeliverStatusEnum(DeliverStatusEnum.UNRECEIVED);
        deliverInvoiceLog.setDateCreated(deliverInvoice.getDateCreated());
        deliverInvoiceLog.setLogisticsId(logistics.getId());

        deliverInvoiceLogRepo.save(deliverInvoiceLog);


        uniResp.setStatus(200);
        uniResp.setData("success");
        return uniResp;
    }
}
