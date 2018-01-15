package net.kingsilk.qh.agency.server.resource.brandApp.partner.cart

import net.kingsilk.qh.agency.api.UniResp
import net.kingsilk.qh.agency.api.brandApp.partner.cart.CartApi
import net.kingsilk.qh.agency.api.brandApp.partner.cart.dto.CartItemInfo
import net.kingsilk.qh.agency.api.brandApp.partner.cart.dto.CartNumResp
import net.kingsilk.qh.agency.core.CartTypeEnum
import net.kingsilk.qh.agency.domain.Cart
import net.kingsilk.qh.agency.domain.PartnerStaff
import net.kingsilk.qh.agency.domain.Sku
import net.kingsilk.qh.agency.repo.CartRepo
import net.kingsilk.qh.agency.repo.SkuRepo
import net.kingsilk.qh.agency.service.CommonService
import net.kingsilk.qh.agency.service.CompanyService
import net.kingsilk.qh.agency.service.PartnerService
import net.kingsilk.qh.agency.service.PartnerStaffService
import net.kingsilk.qh.agency.service.SecService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component
import org.springframework.util.Assert

@Component
public class CartResource implements CartApi {

    @Autowired
    SecService secService

    @Autowired
    CommonService commonService

    @Autowired
    PartnerStaffService partnerStaffService

    @Autowired
    CompanyService companyService

    @Autowired
    MongoTemplate mongoTemplate

    @Autowired
    CartRepo cartRepo

    @Autowired
    SkuRepo skuRepo

    @Autowired
    CartConvert cartConvert

    @Autowired
    private PartnerService partnerService
    @Override
    UniResp<String> add(
            String brandAppId,
            String partnerId,
            String skuId,
            int num,
            String type
    ) {
        partnerService.check()
        Assert.notNull(type, "购物车类型不能为空！")
        String curUserId = secService.curUserId()
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        Cart cart = cartRepo.findOneByPartnerStaffAndBrandAppIdAndCartTypeEnum(partnerStaff, brandAppId, CartTypeEnum.valueOf(type))

        if (!cart) {
            cart = new Cart()
            cart.setCartItems(new HashSet<>())
            cart.partnerStaff = partnerStaff
            cart.brandAppId = partnerStaff.brandAppId
            cart.cartTypeEnum = CartTypeEnum.valueOf(type)
        }
        Sku sku = skuRepo.findOne(skuId)
        Assert.notNull(sku, "商品错误")
        boolean isNew = true
        for (Cart.CartItem cartItem : cart.cartItems) {
            if (cartItem.sku?.id == skuId) {
                isNew = false
                if (num < 0) {
                    ////小于等于0，移除购物车
                    //
                    Assert.notNull(null, "数量错误")
                    break;
                }

                cartItem.num = num
                if (num == 0) {
                    cart.cartItems.remove(cartItem)
                }
                break
            }
        }
        if (isNew && num > 0) {
            Cart.CartItem ci = new Cart.CartItem()
            ci.num = num
            ci.sku = sku
            cart.cartItems.add(ci)
        }
        cartRepo.save(cart)
        return new UniResp<String>(status: 200, data: "SUCCESS")
    }

    @Override
    UniResp<String> setNum(
            String brandAppId,
            String partnerId,
            String skuId,
            int num,
            String type
    ) {
        partnerService.check()
        Assert.notNull(type, "购物车类型不能为空！")
        String curUserId = secService.curUserId()
//        Company company = companyService.getCurCompany()
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        Cart cart = cartRepo.findOneByPartnerStaffAndBrandAppIdAndCartTypeEnum(partnerStaff, brandAppId, CartTypeEnum.valueOf(type))
        if (!cart) {
            cart = new Cart()
            cart.setCartItems(new HashSet<>())
            cart.cartTypeEnum = CartTypeEnum.valueOf(type)
            cart.partnerStaff = partnerStaff
            cart.brandAppId = partnerStaff.brandAppId
        }
        Sku sku = skuRepo.findOne(skuId)
        Assert.notNull(sku, "商品错误")
        if (!sku) {
            //商品不存在，跳过
            return;
        }
        boolean isNew = true
        for (Cart.CartItem cartItem : cart.cartItems) {
            if (cartItem.sku.id == skuId) {
                isNew = false
                if (num <= 0) {
                    ////小于等于0，移除购物车
                    cart.cartItems.remove(cartItem)
                    break;
                }
                cartItem.num = num
                break
            }
        }
        if (isNew && num > 0) {
            Cart.CartItem ci = new Cart.CartItem()
            ci.num = num
            ci.sku = sku
            cart.cartItems.add(ci)
        }

        cartRepo.save(cart)
        return new UniResp<String>(status: 200, data: "SUCCESS")
    }

    @Override
    UniResp<List<CartItemInfo>> list(
            String brandAppId,
            String partnerId,
            String type
    ) {
        partnerService.check()
        Assert.notNull(type, "购物车类型不能为空！")
        String curUserId = secService.curUserId()
//        Company company = companyService.getCurCompany()
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        Cart cart = cartRepo.findOneByPartnerStaffAndBrandAppIdAndCartTypeEnum(partnerStaff, brandAppId, CartTypeEnum.valueOf(type))

        String partnerType = partnerStaff.partner.partnerTypeEnum.code
//        String partnerType = null
        if (!cart) {
            cart = new Cart()
            cart.setCartItems(new HashSet<>())
            cart.brandAppId = partnerStaff.brandAppId
            cart.cartTypeEnum = CartTypeEnum.valueOf(type)
            cart.partnerStaff = partnerStaff
            cartRepo.save(cart)
        }
        UniResp<List<CartItemInfo>> resp = new UniResp<List<CartItemInfo>>()
        resp.status = 200
        resp.data = cartConvert.cartItemInfoConvert(cart.cartItems, partnerType,type)
        return resp
    }


    @Override
    UniResp<CartNumResp> num(
            String brandAppId,
            String partnerId,
            String type
    ) {
        partnerService.check()
        Assert.notNull(type, "购物车类型不能为空！")
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        Cart cart = cartRepo.findOneByPartnerStaffAndBrandAppIdAndCartTypeEnum(partnerStaff, brandAppId, CartTypeEnum.valueOf(type))

        if (!cart) {
            cart = new Cart()
            cart.setCartItems(new HashSet<>())
            cart.brandAppId = partnerStaff.brandAppId
            cart.cartTypeEnum = CartTypeEnum.valueOf(type)
            cart.partnerStaff = partnerStaff
            cartRepo.save(cart)
        }
        CartNumResp resp = new CartNumResp()
        resp.typeNum = cart.cartItems.size()
        resp.totalNum = cart.cartItems.sum { Cart.CartItem it ->
            return it.num
        }
        return new UniResp<CartNumResp>(status: 200, data: resp)
    }


    @Override
    UniResp<CartNumResp> clearCart(
            String brandAppId,
            String partnerId,
            String type
    ) {
        partnerService.check()
        Assert.notNull(type, "购物车类型不能为空！")
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        Cart cart = cartRepo.findOneByPartnerStaffAndBrandAppIdAndCartTypeEnum(partnerStaff, brandAppId, CartTypeEnum.valueOf(type))

        if (!cart) {
            cart = new Cart()
            cart.setCartItems(new HashSet<>())
            cart.brandAppId = partnerStaff.brandAppId
            cart.cartTypeEnum = CartTypeEnum.valueOf(type)
            cart.partnerStaff = partnerStaff
            cartRepo.save(cart)
        }
        cart.cartItems.clear()
        cartRepo.save(cart)
        return new UniResp<CartNumResp>(status: 200, data: "操作成功")
    }

    @Override
    UniResp<CartNumResp> removeCart(
            String brandAppId,
            String partnerId,
            String type,
            List cartItems
    ) {
        partnerService.check()
        Assert.notNull(type, "购物车类型不能为空！")
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        Cart cart = cartRepo.findOneByPartnerStaffAndBrandAppIdAndCartTypeEnum(partnerStaff, brandAppId, CartTypeEnum.valueOf(type))

        if (!cart) {
            cart = new Cart()
            cart.setCartItems(new HashSet<>())
            cart.brandAppId = partnerStaff.brandAppId
            cart.cartTypeEnum = CartTypeEnum.valueOf(type)
            cart.partnerStaff = partnerStaff
            cartRepo.save(cart)
        }
        Set set = new HashSet()
        cart.cartItems.each { Cart.CartItem cartItem ->
            if(cartItems.contains(cartItem.sku.id)){
                set.add(cartItem)
            }
        }
        cart.cartItems.removeAll(set)
        cartRepo.save(cart)
        return new UniResp<CartNumResp>(status: 200, data: "操作成功")
    }


}
