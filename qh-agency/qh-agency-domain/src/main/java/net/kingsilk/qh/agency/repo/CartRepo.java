package net.kingsilk.qh.agency.repo;

import net.kingsilk.qh.agency.core.CartTypeEnum;
import net.kingsilk.qh.agency.domain.Cart;
import net.kingsilk.qh.agency.domain.PartnerStaff;

import java.util.List;

public interface CartRepo extends BaseRepo<Cart, String> {

    @Deprecated
    public abstract Cart findOneByPartnerStaff(PartnerStaff partnerStaff);

    List<Cart> findCartsByBrandAppIdIn(List cartItems);

    public abstract Cart findOneByPartnerStaffAndBrandAppIdAndCartTypeEnum(PartnerStaff partnerStaff, String brandAppId,CartTypeEnum cartTypeEnum);
}
