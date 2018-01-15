package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.cart.converter;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.cart.dto.CartItemInfo;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.cart.dto.CartItemInfos;
import net.kingsilk.qh.shop.api.common.dto.SkuInfoModel;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.ItemPropValueRepo;
import net.kingsilk.qh.shop.repo.ItemRepo;
import net.kingsilk.qh.shop.repo.SkuRepo;
import net.kingsilk.qh.shop.repo.SkuStoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CartItemInfoConverter implements Converter<Cart, CartItemInfos> {

    @Autowired
    private SkuRepo skuRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ItemPropValueRepo itemPropValueRepo;

    @Autowired
    private SkuStoreRepo skuStoreRepo;

    @Override
    public CartItemInfos convert(Cart cart) {

        CartItemInfos cartItemInfos = new CartItemInfos();
        List<CartItemInfo> items = new ArrayList<>();
        List<Cart.CartItem> cartItems = cart.getCartItems();
        Optional.ofNullable(cartItems).ifPresent(it ->
                it.forEach(item -> {
                            SkuInfoModel skuInfoModel = skuInfoModelConvert(item.getSkuId(), cart);

                            CartItemInfo cartItemInfo = new CartItemInfo();
                            cartItemInfo.setSku(skuInfoModel);
                            cartItemInfo.setNum(item.getNum());
                            items.add(cartItemInfo);
                        }
                )
        );
        cartItemInfos.setCartItemInfos(items);
        return cartItemInfos;
    }

    private SkuInfoModel skuInfoModelConvert(String skuId, Cart cart) {

        SkuInfoModel skuInfoModel = new SkuInfoModel();
        Sku sku = skuRepo.findOne(
                Expressions.allOf(
                        QSku.sku.brandAppId.eq(cart.getBrandAppId()),
                        QSku.sku.id.eq(skuId)
                )
        );
        //设置图片等信息
        Optional.ofNullable(sku).ifPresent(it ->
                {
                    skuInfoModel.setPrice(it.getSalePrice());
                    skuInfoModel.setSkuId(skuId);

                    Optional.ofNullable(
                            itemRepo.findOne(
                                    Expressions.allOf(
                                            QItem.item.id.eq(sku.getItemId())
                                    )
                            )
                    ).ifPresent(item ->
                            {
                                skuInfoModel.setImgs(
                                        Lists.newArrayList(item.getImgs())
                                );
                                skuInfoModel.setTitle(
                                        Optional.ofNullable(sku.getTitle())
                                                .orElse(item.getTitle())
                                );
                            }
                    );

                    Optional.ofNullable(sku.getSpecs()).ifPresent(specs ->

                            skuInfoModel.setSpecs(
                                    specs.stream()
                                            .map(spec ->
                                                    Optional.ofNullable(itemPropValueRepo.findOne(
                                                            Expressions.allOf(
                                                                    QItemPropValue.itemPropValue.id.eq(spec.getItemPropValueId())
                                                            )
                                                    )).orElse(new ItemPropValue()).getName())
                                            .collect(Collectors.toList())
                            )
                    );

                    SkuStore skuStore = skuStoreRepo.findOne(
                            Expressions.allOf(
                                    QSkuStore.skuStore.skuId.eq(skuId)
                            )
                    );
                    Optional.ofNullable(skuStore).ifPresent(store ->
                            skuInfoModel.setStorage(Optional.ofNullable(skuStore.getNum()).orElse(0))
                    );
                }
        );
        return skuInfoModel;
    }
}
