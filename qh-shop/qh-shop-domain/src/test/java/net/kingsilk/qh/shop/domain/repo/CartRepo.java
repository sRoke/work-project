package net.kingsilk.qh.shop.domain.bak;


import net.kingsilk.qh.shop.domain.bak.Cart;
import net.kingsilk.qh.shop.repo.BaseRepo;

public interface CartRepo extends BaseRepo<Cart, String> {
    Cart findOneByPartnerIdAndCartNum(String partnerId,int cartNum);
}
