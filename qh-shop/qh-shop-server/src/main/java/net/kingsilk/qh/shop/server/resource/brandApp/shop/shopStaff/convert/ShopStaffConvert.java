package net.kingsilk.qh.shop.server.resource.brandApp.shop.shopStaff.convert;

import net.kingsilk.qh.shop.api.brandApp.shop.shopStaff.dto.ShopStaffInfoResp;
import net.kingsilk.qh.shop.domain.ShopStaff;
import org.springframework.stereotype.Component;

@Component
public class ShopStaffConvert {

    public ShopStaffInfoResp infoRespConvert(ShopStaff shopStaff) {
        ShopStaffInfoResp staffInfoResp = new ShopStaffInfoResp();
        staffInfoResp.setMemo(shopStaff.getMemo());
        staffInfoResp.setUserId(shopStaff.getUserId());
        staffInfoResp.setShopId(shopStaff.getShopId());
        staffInfoResp.setLastModifiedDate(shopStaff.getLastModifiedDate());
        staffInfoResp.setDisabled(shopStaff.getEnable());
        staffInfoResp.setDateCreated(shopStaff.getDateCreated());
        return staffInfoResp;

    }
}
