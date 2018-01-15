package net.kingsilk.qh.shop.service.service;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.core.CartTypeEnum;
import net.kingsilk.qh.shop.domain.Cart;
import net.kingsilk.qh.shop.domain.QCart;
import net.kingsilk.qh.shop.repo.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepo cartRepo;

    public Cart banding(String cartId, String userId, String type) {
        Cart cart = cartRepo.findOne(cartId);
        //绑定购物车
        if (!userId.equals(cart.getUserId())) {
            //如果登录就将未登录的购物车加如到已登录的购物车
            cart.setUserId(userId);
            Cart temCart = cartRepo.findOne(
                    Expressions.allOf(
                            QCart.cart.brandAppId.eq(cart.getBrandAppId()),
                            QCart.cart.shopId.eq(cart.getShopId()),
                            QCart.cart.userId.eq(userId),
                            QCart.cart.deleted.ne(true),
                            QCart.cart.cartTypeEnum.eq(CartTypeEnum.valueOf(type))
                    )
            );
            Optional.ofNullable(temCart).ifPresent(it ->
                    {
                        List<String> cartSkus = cart.getCartItems().stream().map(Cart.CartItem::getSkuId).collect(Collectors.toList());
                        //先将sku不同的直接加入到新的购物车中
                        cart.getCartItems().addAll(
                                it.getCartItems().stream()
                                        .filter(cartItem ->
                                                !cartSkus.contains(cartItem.getSkuId())
                                        ).collect(Collectors.toList())
                        );

                        cart.getCartItems().stream().peek(cartItem ->
                                //查找相同的sku
                                it.getCartItems().stream().filter(temCartItem ->
                                        temCartItem.getSkuId().equals(cartItem.getSkuId())
                                ).findFirst().ifPresent(cartItem1 ->
                                        //将已经登录的sku加入到新的购物车
                                        cartItem.setNum(cartItem1.getNum() + cartItem.getNum())
                                )
                        );

                        temCart.setDeleted(true);
                        cartRepo.save(temCart);
                    }
            );
            cartRepo.save(cart);
        }
        return cart;
    }

    public Cart getCart(String cartId, String curUserId, String brandAppId, String shopId,String type) {
        Cart cart;
        if (StringUtils.hasText(cartId) && StringUtils.hasText(curUserId)) {
            //绑定购物车
            cart = banding(cartId, curUserId, type);
        } else if (StringUtils.hasText(cartId) && !StringUtils.hasText(curUserId)) {
            //对未绑定购物车进行操作
            cart = cartRepo.findOne(cartId);
        } else if (StringUtils.hasText(curUserId)) {
            //已经登录
            Cart temCart = new Cart();
            temCart.setCartItems(new LinkedList<>());
            temCart.setBrandAppId(brandAppId);
            temCart.setCartTypeEnum(CartTypeEnum.valueOf(type));
            temCart.setUserId(curUserId);
            cart = Optional.ofNullable(
                    cartRepo.findOne(
                            Expressions.allOf(
                                    QCart.cart.userId.eq(curUserId),
                                    QCart.cart.brandAppId.eq(brandAppId),
                                    QCart.cart.deleted.ne(true),
                                    QCart.cart.shopId.eq(shopId),
                                    QCart.cart.cartTypeEnum.eq(CartTypeEnum.valueOf(type))
                            )
                    )
            ).orElse(temCart);
            cartRepo.save(cart);
        } else {
            //未登录且第一次添加购物车
            cart = new Cart();
            cart.setCartItems(new LinkedList<>());
            cart.setBrandAppId(brandAppId);
            cart.setCartTypeEnum(CartTypeEnum.valueOf(type));
            cartRepo.save(cart);
        }
        return cart;
    }

}
