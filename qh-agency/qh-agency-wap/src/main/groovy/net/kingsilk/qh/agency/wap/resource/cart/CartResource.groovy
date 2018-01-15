package net.kingsilk.qh.agency.wap.resource.cart

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import net.kingsilk.qh.agency.domain.Cart
import net.kingsilk.qh.agency.domain.PartnerStaff
import net.kingsilk.qh.agency.domain.Sku
import net.kingsilk.qh.agency.repo.CartRepo
import net.kingsilk.qh.agency.repo.SkuRepo
import net.kingsilk.qh.agency.service.CommonService
import net.kingsilk.qh.agency.service.CompanyService
import net.kingsilk.qh.agency.service.MemberService
import net.kingsilk.qh.agency.service.SecService
import net.kingsilk.qh.agency.wap.api.UniResp
import net.kingsilk.qh.agency.wap.api.cart.CartApi
import net.kingsilk.qh.agency.wap.api.cart.dto.CartAddReq
import net.kingsilk.qh.agency.wap.api.cart.dto.CartListResp
import net.kingsilk.qh.agency.wap.api.cart.dto.CartNumResp
import net.kingsilk.qh.agency.wap.api.cart.dto.CartSetNumReq
import net.kingsilk.qh.agency.wap.resource.cart.convert.CartConvert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component
import org.springframework.util.Assert

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Api(
        tags = "cart",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "购物车相关api")
@Path("/cart")
@Component
public class CartResource implements CartApi {

    @Autowired
    SecService secService

    @Autowired
    CommonService commonService

    @Autowired
    MemberService memberService

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

    @ApiOperation(
            value = "加入购物车",
            nickname = "加入购物车",
            notes = "加入购物车"
    )
    @Path("add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> add(CartAddReq req) {
        String curUserId = secService.curUserId()
//        Company company = companyService.getCurCompany()
        PartnerStaff member=memberService.getCurPartnerStaff()
        Cart cart = cartRepo.findOneByPartnerStaffAndBrandId(member,member.brandId)

        if (!cart) {
            cart = new Cart()
            cart.setCartItems(new HashSet<>())
            cart.partnerStaff = member
            cart.brandId = member.brandId
        }
        Sku sku = skuRepo.findOne(req.skuId)
        Assert.notNull(sku, "商品错误")
        boolean isNew = true
        for (Cart.CartItem cartItem : cart.cartItems) {
            if (cartItem.sku?.id == req.skuId) {
                isNew = false
                if (req.num < 0) {
                    ////小于等于0，移除购物车
                    //
                    Assert.notNull(null, "数量错误")
                    break;
                }

                cartItem.num = req.num
                if (req.num == 0) {
                    cart.cartItems.remove(cartItem)
                }
                break
            }
        }
        if (isNew && req.num > 0) {
            Cart.CartItem ci = new Cart.CartItem()
            ci.num = req.num
            ci.sku = sku
            cart.cartItems.add(ci)
        }
        cartRepo.save(cart)
        return new UniResp<String>(status: 200, data: "SUCCESS")
    }

    @ApiOperation(
            value = "修改数量",
            nickname = "修改数量",
            notes = "修改数量"
    )
    @Path("setNum")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> setNum(CartSetNumReq req) {
        String curUserId = secService.curUserId()
//        Company company = companyService.getCurCompany()
        PartnerStaff member=memberService.getCurPartnerStaff()
        Cart cart = cartRepo.findOneByPartnerStaffAndBrandId(member,member.brandId)
        if (!cart) {
            cart = new Cart()
            cart.setCartItems(new HashSet<>())
            cart.partnerStaff = member
            cart.brandId = member.brandId
        }
        req.each {
            Sku sku = skuRepo.findOne(it.skuId)
            //Assert.notNull(sku, "商品错误")
            if (!sku) {
                //商品不存在，跳过
                return;
            }
            boolean isNew = true
            for (Cart.CartItem cartItem : cart.cartItems) {
                if (cartItem.sku.id == it.skuId) {
                    isNew = false
                    if (it.num <= 0) {
                        ////小于等于0，移除购物车
                        cart.cartItems.remove(cartItem)
                        break;
                    }
                    cartItem.num = it.num
                    break
                }
            }
            if (isNew && it.num > 0) {
                Cart.CartItem ci = new Cart.CartItem()
                ci.num = it.num
                ci.sku = sku
                cart.cartItems.add(ci)
            }
        }
        cartRepo.save(cart)
        return new UniResp<String>(status: 200, data: "SUCCESS")
    }

    @ApiOperation(
            value = "购物车商品列表",
            nickname = "购物车商品列表",
            notes = "购物车商品列表"
    )
    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<CartListResp> list() {
        String curUserId = secService.curUserId()
//        Company company = companyService.getCurCompany()
        PartnerStaff member=memberService.getCurPartnerStaff()
        Cart cart = cartRepo.findOneByPartnerStaffAndBrandId(member,member.brandId)

        String partnerType = member.partner.partnerTypeEnum.code
//        String partnerType = null
        if (!cart) {
            cart = new Cart()
            cart.setCartItems(new HashSet<>())
            cart.brandId = member.brandId
            cart.partnerStaff = member
            cartRepo.save(cart)
        }
        CartListResp resp = cartConvert.cartItemInfoConvert(cart.cartItems, partnerType)
        return new UniResp<CartListResp>(status: 200, data: resp)
    }


    @ApiOperation(
            value = "购物车商品数量",
            nickname = "购物车商品数量",
            notes = "购物车商品数量"
    )
    @Path("num")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<CartNumResp> num() {
        String curUserId = secService.curUserId()
//        Company company = companyService.getCurCompany()
        PartnerStaff member=memberService.getCurPartnerStaff()
        Cart cart = cartRepo.findOneByPartnerStaffAndBrandId(member,member.brandId)

        if (!cart) {
            cart = new Cart()
            cart.setCartItems(new HashSet<>())
            cart.brandId = member.brandId
            cart.partnerStaff = member
            cartRepo.save(cart)
        }
        CartNumResp resp = new CartNumResp()
        resp.typeNum = cart.cartItems.size()
        resp.totalNum = cart.cartItems.sum { Cart.CartItem it ->
            return it.num
        }
        return new UniResp<CartNumResp>(status: 200, data: resp)
    }


    @ApiOperation(
            value = "清空购物车",
            nickname = "清空购物车",
            notes = "清空购物车"
    )
    @Path("clearCart")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<CartNumResp> clearCart() {
        String curUserId = secService.curUserId()
//        Company company = companyService.getCurCompany()
        PartnerStaff member=memberService.getCurPartnerStaff()
        Cart cart = cartRepo.findOneByPartnerStaffAndBrandId(member,member.brandId)

        if (!cart) {
            cart = new Cart()
            cart.setCartItems(new HashSet<>())
            cart.brandId = member.brandId
            cart.partnerStaff = member
            cartRepo.save(cart)
        }
        cart.cartItems.clear()
        cartRepo.save(cart)
        return new UniResp<CartNumResp>(status: 200, data: "操作成功")
    }


}
