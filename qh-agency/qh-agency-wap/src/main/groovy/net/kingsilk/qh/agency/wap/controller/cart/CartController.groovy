//package net.kingsilk.qh.agency.wap.controller.cart
//
//import io.swagger.annotations.Api
//import io.swagger.annotations.ApiOperation
//import io.swagger.annotations.ApiResponse
//import io.swagger.annotations.ApiResponses
//import net.kingsilk.qh.agency.wap.api.UniResp
//import net.kingsilk.qh.agency.domain.Cart
//import net.kingsilk.qh.agency.domain.Company
//import net.kingsilk.qh.agency.domain.PartnerStaff
//import net.kingsilk.qh.agency.domain.Sku
//import net.kingsilk.qh.agency.repo.CartRepo
//import net.kingsilk.qh.agency.repo.SkuRepo
//import net.kingsilk.qh.agency.service.CommonService
//import net.kingsilk.qh.agency.service.CompanyService
//import net.kingsilk.qh.agency.service.MemberService
//import net.kingsilk.qh.agency.service.SecService
//
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.data.mongodb.core.MongoTemplate
//import org.springframework.http.MediaType
//import org.springframework.security.access.prepost.PreAuthorize
//import org.springframework.util.Assert
//import org.springframework.web.bind.annotation.*
//
//@RestController()
//@RequestMapping("/api/cart")
//@Api(
//        tags = "cart",
//        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
//        protocols = "http,https",
//        description = "购物车相关api"
//)
//class CartController {
//
//    @Autowired
//    SecService secService
//
//    @Autowired
//    CommonService commonService
//
//    @Autowired
//    MemberService memberService
//
//    @Autowired
//    CompanyService companyService
//
//    @Autowired
//    MongoTemplate mongoTemplate
//
//    @Autowired
//    CartRepo cartRepo
//
//    @Autowired
//    SkuRepo skuRepo
//
//    @RequestMapping(path = "/add",
//            method = RequestMethod.POST,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "加入购物车",
//            nickname = "加入购物车",
//            notes = "加入购物车"
//    )
//    @ApiResponses([
//            @ApiResponse(
//                    code = 200,
//                    message = "正常结果")
//    ])
//    @ResponseBody
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('MEMBER')")
//    UniResp<String> add(@RequestBody CartAddReq req,
//                        @RequestHeader("Company-Id") String companyId) {
//
//        String curUserId = secService.curUserId()
//        Company company = companyService.getCurCompany()
//        Cart cart = cartRepo.findOneByUserIdAndCompany(curUserId, company)
//
//        if (!cart) {
//            cart = new Cart()
//            cart.setCartItems(new HashSet<>())
//            cart.userId = curUserId
//            cart.company = company
//        }
//        Sku sku = skuRepo.findOne(req.skuId)
//        Assert.notNull(sku, "商品错误")
//        boolean isNew = true
//        for (Cart.CartItem cartItem : cart.cartItems) {
//            if (cartItem.sku?.id == req.skuId) {
//                isNew = false
//                if (req.num <= 0) {
//                    ////小于等于0，移除购物车
//                    //cart.cartItems.remove(cartItem)
//                    Assert.notNull(null, "数量错误")
//                    break;
//                }
//                cartItem.num += req.num
//                break
//            }
//        }
//        if (isNew && req.num > 0) {
//            Cart.CartItem ci = new Cart.CartItem()
//            ci.num = req.num
//            ci.sku = sku
//            cart.cartItems.add(ci)
//        }
//        cartRepo.save(cart)
//        return new UniResp<String>(status: 200, data: "SUCCESS")
//    }
//
//
//    @RequestMapping(path = "/setNum",
//            method = RequestMethod.POST,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "修改数量",
//            nickname = "修改数量",
//            notes = "修改数量"
//    )
//    @ApiResponses([
//            @ApiResponse(
//                    code = 200,
//                    message = "正常结果")
//    ])
//    @ResponseBody
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('MEMBER')")
//    UniResp<String> setNum(@RequestBody CartSetNumReq[] req,
//                           @RequestHeader("Company-Id") String companyId) {
//
//        String curUserId = secService.curUserId()
//        Company company = companyService.getCurCompany()
//        Cart cart = cartRepo.findOneByUserIdAndCompany(curUserId, company)
//
//        if (!cart) {
//            cart = new Cart()
//            cart.setCartItems(new HashSet<>())
//            cart.userId = curUserId
//            cart.company = company
//        }
//        req.each {
//            Sku sku = skuRepo.findOne(it.skuId)
//            //Assert.notNull(sku, "商品错误")
//            if (!sku) {
//                //商品不存在，跳过
//                return;
//            }
//            boolean isNew = true
//            for (Cart.CartItem cartItem : cart.cartItems) {
//                if (cartItem.sku.id == it.skuId) {
//                    isNew = false
//                    if (it.num <= 0) {
//                        ////小于等于0，移除购物车
//                        cart.cartItems.remove(cartItem)
//                        break;
//                    }
//                    cartItem.num = it.num
//                    break
//                }
//            }
//            if (isNew && it.num > 0) {
//                Cart.CartItem ci = new Cart.CartItem()
//                ci.num = it.num
//                ci.sku = sku
//                cart.cartItems.add(ci)
//            }
//        }
//        cartRepo.save(cart)
//        return new UniResp<String>(status: 200, data: "SUCCESS")
//    }
//
//    @RequestMapping(path = "/list",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "购物车商品列表",
//            nickname = "购物车商品列表",
//            notes = "购物车商品列表"
//    )
//    @ApiResponses([
//            @ApiResponse(
//                    code = 200,
//                    message = "正常结果")
//    ])
//    @ResponseBody
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('MEMBER')")
//    UniResp<CartListResp> list(@RequestHeader("Company-Id") String companyId) {
//
//        String curUserId = secService.curUserId()
//        Company company = companyService.getCurCompany()
//        Cart cart = cartRepo.findOneByUserIdAndCompany(curUserId, company)
//
//        PartnerStaff curMember = memberService.getCurPartnerStaff()
//        String[] tags = curMember?.tags*.code
//
//        if (!cart) {
//            cart = new Cart()
//            cart.setCartItems(new HashSet<>())
//            cart.company = company
//            cart.userId = curUserId
//            cartRepo.save(cart)
//        }
//        CartListResp resp = new CartListResp()
//        resp.convert(cart.cartItems, tags)
//        return new UniResp<CartListResp>(status: 200, data: resp)
//    }
//
//    @RequestMapping(path = "/num",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "购物车商品数量",
//            nickname = "购物车商品数量",
//            notes = "购物车商品数量"
//    )
//    @ApiResponses([
//            @ApiResponse(
//                    code = 200,
//                    message = "正常结果")
//    ])
//    @ResponseBody
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('MEMBER')")
//    UniResp<CartNumResp> num(@RequestHeader("Company-Id") String companyId) {
//
//        String curUserId = secService.curUserId()
//        Company company = companyService.getCurCompany()
//        Cart cart = cartRepo.findOneByUserIdAndCompany(curUserId, company)
//
//        if (!cart) {
//            cart = new Cart()
//            cart.setCartItems(new HashSet<>())
//            cart.company = company
//            cart.userId = curUserId
//            cartRepo.save(cart)
//        }
//        CartNumResp resp = new CartNumResp()
//        resp.typeNum = cart.cartItems.size()
//        resp.totalNum = cart.cartItems.sum { Cart.CartItem it ->
//            return it.num
//        }
//        return new UniResp<CartNumResp>(status: 200, data: resp)
//    }
//}
//
