package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.Item.convert;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.common.dto.SkuInfoModel;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.ItemPropValueRepo;
import net.kingsilk.qh.shop.repo.ItemRepo;
import net.kingsilk.qh.shop.repo.SkuStoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SkuInfoModelConverter implements Converter<Sku, SkuInfoModel> {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ItemPropValueRepo itemPropValueRepo;

    @Autowired
    private SkuStoreRepo skuStoreRepo;

    @Override
    public SkuInfoModel convert(Sku sku) {
        SkuInfoModel skuInfoModel = new SkuInfoModel();
        //设置图片等信息



        Optional.ofNullable(sku).ifPresent(it ->
                {
                    skuInfoModel.setPrice(it.getSalePrice());
                    skuInfoModel.setDetail(it.getDetail());

                    skuInfoModel.setSkuId(it.getId());

                    Optional.ofNullable(
                            itemRepo.findOne(
                                    Expressions.allOf(
//                                            QItem.item.deleted.ne(true),
                                            QItem.item.brandAppId.eq(sku.getBrandAppId()),
                                            QItem.item.id.eq(sku.getItemId())
                                    )
                            )
                    ).ifPresent(item ->
                            {
                                skuInfoModel.setImgs(
                                        Lists.newArrayList(sku.getImgs())
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
//                                                                    QItemPropValue.itemPropValue.deleted.ne(true)
                                                            )
                                                    )).orElse(new ItemPropValue()).getName())
                                            .collect(Collectors.toList())
                            )
                    );

                    SkuStore skuStore = skuStoreRepo.findOne(
                            Expressions.allOf(
                                    QSkuStore.skuStore.skuId.eq(sku.getId()),
                                    QSkuStore.skuStore.brandAppId.eq(sku.getBrandAppId())
//                                    QSkuStore.skuStore.deleted.ne(true)
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
