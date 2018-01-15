package net.kingsilk.qh.agency.server.resource.brandApp.notify

import net.kingsilk.qh.agency.api.brandApp.notify.NotifyApi
import net.kingsilk.qh.agency.api.brandApp.notify.dto.NotifyQhPayReq
import net.kingsilk.qh.agency.api.brandApp.notify.dto.NotifyQhPayResp
import net.kingsilk.qh.agency.api.brandApp.notify.dto.NotifyShopInfoReq
import net.kingsilk.qh.agency.domain.Order
import net.kingsilk.qh.agency.repo.OrderRepo
import net.kingsilk.qh.agency.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


/**
 * Created by zcw on 3/30/17.
 * 调用外部系统回调相关接口
 */
@Component
public class NotifyResource implements NotifyApi {

    @Autowired
    OrderRepo orderRepo

    @Autowired
    OrderService orderService

    String qhPay(NotifyQhPayReq req) {
        Order order = orderRepo.findOne(req.bizOrderNo)

        if (!order) {
            return new NotifyQhPayResp(code: "FAILED")
        }
        if (req.type == "PAY") {
            orderService.paySuccessHandle(order, req.payType, req.payTime)
        } else if (req.type == "REFUND") {
            orderService.refundHandle()
        }
        return new NotifyQhPayResp(code: "SUCCESS")
    }

    @Override
    String qhShop(NotifyShopInfoReq req) {
        if (req.getShopInfo().get("type") == "PAY") {
            orderService.payShopSuccessHandle(req)
        } else if (req.getShopInfo().get("type") == "REFUND") {
            orderService.refundHandle()
        }
        return new NotifyQhPayResp(code: "SUCCESS")
    }
}