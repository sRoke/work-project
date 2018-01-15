package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.order.convert;

import net.kingsilk.qh.oauth.api.ErrStatusException;
import net.kingsilk.qh.oauth.api.user.addr.AddrGetResp;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.domain.Order;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component("mallAddrConvert")
public class AddrConvert {

    public void aouthToShopAddrConvert(Order order, AddrGetResp addr) {

        Order.ShippingAddress addrOfOrder = order.new ShippingAddress();
        addrOfOrder.setAdc(addr.getAdc());
        addrOfOrder.setReceiver(addr.getContact());
        addrOfOrder.setStreet(addr.getStreet());
        if (addr.getPhones() != null && !addr.getPhones().isEmpty()) {
            addrOfOrder.setPhone(addr.getPhones().iterator().next()); //第一个手机号
        }
        addrOfOrder.setMemo(addr.getMemo());
        order.setAddr(addrOfOrder);
    }
}
