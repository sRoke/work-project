package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.Item.convert;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.item.dto.ItemInfoModel;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.ItemPropValueRepo;
import net.kingsilk.qh.shop.repo.SkuRepo;
import net.kingsilk.qh.shop.service.util.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ItemInfoModelConverter implements Converter<Item, ItemInfoModel> {

    @Autowired
    private SkuRepo skuRepo;

    @Autowired
    private ItemPropValueRepo itemPropValueRepo;

    @Override
    public ItemInfoModel convert(Item item) {

        ItemInfoModel itemInfoModel = new ItemInfoModel();

        List<Sku> skus = Lists.newArrayList(skuRepo.findAll(
                Expressions.allOf(
                        QSku.sku.itemId.eq(item.getId()),
                        QSku.sku.deleted.ne(true),
                        QSku.sku.brandAppId.eq(item.getBrandAppId())
                )
        ));
        itemInfoModel.setPrice(
                skus.stream()
                        .mapToInt(Sku::getSalePrice)
                        .min()
                        .orElse(0)
        );
        itemInfoModel.setItemId(item.getId());
        itemInfoModel.setTagPrice(skus.stream()
                .mapToInt(Sku::getLabelPrice)
                .min()
                .orElse(0));
        itemInfoModel.setTitle(item.getTitle());
        itemInfoModel.setItemUnit(item.getItemUnit());
        itemInfoModel.setDesp(item.getDesp());
        itemInfoModel.setImgs(Lists.newArrayList(item.getImgs()));

        Optional.ofNullable(item.getSpecs()).ifPresent(specs ->
                {
//                    specs.forEach(specDef -> {
//                                Map<String, List<ItemPropValue>> listMap = Lists.newArrayList(itemPropValueRepo.findAll(
//                                        Expressions.allOf(
//                                                DbUtil.opIn(QItemPropValue.itemPropValue.id, Lists.newArrayList(specDef.getItemPropValueIds())),
//                                                QItemPropValue.itemPropValue.deleted.ne(true),
//                                        )
//                                )).stream().collect(Collectors.groupingBy(ItemPropValue::getName));
//                            }
//                    );

                    //只将propValues的name传给前端
                    List<String> propValues = new ArrayList<>();
                    specs.forEach(spec ->
                            propValues.addAll(
                                    Lists.newArrayList(
                                            itemPropValueRepo.findAll(
                                                    Expressions.allOf(
                                                            DbUtil.opIn(QItemPropValue.itemPropValue.id, Lists.newArrayList(spec.getItemPropValueIds())),
                                                            QItemPropValue.itemPropValue.deleted.ne(true)
                                                    )
                                            )
                                    ).stream()
                                            .map(ItemPropValue::getName)
                                            .collect(Collectors.toList())
                            )
                    );
                    itemInfoModel.setSpecs(propValues);
                }
        );
        itemInfoModel.setDetail(item.getDetail());
        return itemInfoModel;
    }
}
