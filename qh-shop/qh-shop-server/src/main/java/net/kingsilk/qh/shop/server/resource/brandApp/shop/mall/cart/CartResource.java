package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.cart;


import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.cart.CartApi;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.cart.dto.CartItemInfo;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.cart.dto.CartItemInfos;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.cart.dto.CartNumResp;
import net.kingsilk.qh.shop.domain.Cart;
import net.kingsilk.qh.shop.domain.Sku;
import net.kingsilk.qh.shop.repo.CartRepo;
import net.kingsilk.qh.shop.repo.SkuRepo;
import net.kingsilk.qh.shop.service.service.CartService;
import net.kingsilk.qh.shop.service.service.SecService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CartResource implements CartApi {

    @Autowired
    private SecService secService;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private SkuRepo skuRepo;

    @Autowired
    private CartService cartService;

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

    @Override
    public UniResp<String> add(String brandAppId, String shopId, String skuId, String cartId, int num, String type) {

        Assert.notNull(type, "购物车类型不能为空！");
        String curUserId = secService.curUserId();
        //判断是否初次绑定到用户ID
        Cart cart = cartService.getCart(cartId, curUserId, brandAppId, shopId, type);
        cart.setShopId(shopId);
        Sku sku = skuRepo.findOne(skuId);
        Assert.notNull(sku, "商品错误");
        List<Cart.CartItem> cartItems = cart.getCartItems();
        Optional<Cart.CartItem> item = cartItems.stream()
                .filter(it ->
                        skuId.equals(it.getSkuId())
                )
                .findFirst();
        item.ifPresent(cartItem ->
                cartItem.setNum(num + cartItem.getNum())

        );
        if (!item.isPresent()) {
            Cart.CartItem ci = cart.new CartItem();
            ci.setNum(num);
            ci.setSkuId(skuId);
            cartItems.add(ci);
        }

        cartRepo.save(cart);

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setData(cart.getId());
        uniResp.setStatus(HttpStatus.SC_OK);
        return uniResp;
    }

    @Override
    public UniResp<String> update(String brandAppId, String shopId, String skuId, String cartId, int num, String type) {

        Assert.notNull(type, "购物车类型不能为空！");
        String curUserId = secService.curUserId();
        //判断是否初次绑定到用户ID
        Cart cart = cartService.getCart(cartId, curUserId, brandAppId, shopId, type);

        Sku sku = skuRepo.findOne(skuId);
        Assert.notNull(sku, "商品错误");
        List<Cart.CartItem> cartItems = cart.getCartItems();
        Optional<Cart.CartItem> item = cartItems.stream()
                .filter(it ->
                        skuId.equals(it.getSkuId())
                )
                .findFirst();
        item.ifPresent(cartItem ->
                {
                    if (num != 0) {
                        cartItem.setNum(num);
                    } else {
                        cartItems.remove(cartItem);
                    }
                }
        );
        cart.setShopId(shopId);
        if (!item.isPresent()) {
            Cart.CartItem ci = cart.new CartItem();
            ci.setNum(num);
            ci.setSkuId(skuId);
            cartItems.add(ci);
        }

        cartRepo.save(cart);

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setData(cart.getId());
        uniResp.setStatus(HttpStatus.SC_OK);
        return uniResp;
    }

    @Override
    public UniResp<List<CartItemInfo>> list(String brandAppId, String shopId, String cartId, String type) {
        Assert.notNull(type, "购物车类型不能为空！");
        UniResp<List<CartItemInfo>> resp = new UniResp<>();
        String curUserId = secService.curUserId();
        Cart cart = cartService.getCart(cartId, curUserId, brandAppId, shopId, type);
        cartRepo.save(cart);
        CartItemInfos cartItemInfos = conversionService.convert(cart, CartItemInfos.class);
        resp.setData(cartItemInfos.getCartItemInfos());

        resp.setStatus(HttpStatus.SC_OK);
        return resp;
    }

    @Override
    public UniResp<CartNumResp> num(String brandAppId, String shopId, String cartId, String type) {

        Assert.notNull(type, "购物车类型不能为空！");
        UniResp<CartNumResp> uniResp = new UniResp<>();
        String curUserId = secService.curUserId();
        Cart cart = cartService.getCart(cartId, curUserId, brandAppId, shopId, type);
        CartNumResp resp = new CartNumResp();
        resp.setTypeNum(Optional.ofNullable(cart.getCartItems()).orElse(new ArrayList<>()).size());

        resp.setTotalNum(
                Optional.ofNullable(cart.getCartItems())
                        .orElse(new ArrayList<>())
                        .stream()
                        .mapToInt(Cart.CartItem::getNum)
                        .sum()
        );
        uniResp.setData(resp);

        uniResp.setStatus(HttpStatus.SC_OK);
        return uniResp;
    }

    @Override
    public UniResp<String> removeCart(
            String brandAppId,
            String shopId,
            String cartId,
            String type,
            List cartItems
    ) {
        Assert.notNull(type, "购物车类型不能为空！");
        UniResp<String> uniResp = new UniResp<>();
        String curUserId = secService.curUserId();
        Cart cart = cartService.getCart(cartId, curUserId, brandAppId, shopId, type);
        cart.getCartItems().removeAll(
                cart.getCartItems().stream()
                        .filter(it ->
                                cartItems.contains(it.getSkuId())
                        )
                        .collect(Collectors.toList())
        );
        cartRepo.save(cart);
        uniResp.setData("删除成功");
        uniResp.setStatus(HttpStatus.SC_OK);
        return uniResp;
    }

    @Override
    public UniResp<String> clearCart(
            String brandAppId,
            String shopId,
            String cartId,
            String type
    ) {
        Assert.notNull(type, "购物车类型不能为空！");
        UniResp<String> uniResp = new UniResp<>();
        String curUserId = secService.curUserId();
        Cart cart = cartService.getCart(cartId, curUserId, brandAppId, shopId, type);
        cart.getCartItems().clear();
        cartRepo.save(cart);
        cartRepo.save(cart);
        uniResp.setData("删除成功");
        uniResp.setStatus(HttpStatus.SC_OK);
        return uniResp;
    }

}
