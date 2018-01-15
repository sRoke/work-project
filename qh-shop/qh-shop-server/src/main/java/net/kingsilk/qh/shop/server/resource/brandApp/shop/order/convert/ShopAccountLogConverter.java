package net.kingsilk.qh.shop.server.resource.brandApp.shop.order.convert;

import net.kingsilk.qh.shop.domain.ShopAccount;
import net.kingsilk.qh.shop.domain.ShopAccountLog;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ShopAccountLogConverter implements Converter<ShopAccount, ShopAccountLog> {
    @Override
    public ShopAccountLog convert(ShopAccount shopAccount) {

        ShopAccountLog shopAccountLog = new ShopAccountLog();
        shopAccountLog.setShopAccountId(shopAccount.getId());
        shopAccountLog.setBalance(shopAccount.getBalance());
        shopAccountLog.setFreezeBalance(shopAccount.getFreezeBalance());
        shopAccountLog.setBrandAppId(shopAccount.getBrandAppId());
        shopAccountLog.setNoCashBalance(shopAccount.getNoCashBalance());
        shopAccountLog.setShopId(shopAccount.getShopId());

        return shopAccountLog;
    }
}
