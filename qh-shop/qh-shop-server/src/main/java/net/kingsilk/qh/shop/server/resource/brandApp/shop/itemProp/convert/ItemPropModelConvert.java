package net.kingsilk.qh.shop.server.resource.brandApp.shop.itemProp.convert;

import net.kingsilk.qh.shop.api.common.dto.ItemPropModel;
import net.kingsilk.qh.shop.api.common.dto.ItemPropValueModel;
import net.kingsilk.qh.shop.domain.ItemProp;
import net.kingsilk.qh.shop.domain.QItemPropValue;
import net.kingsilk.qh.shop.repo.ItemPropValueRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.querydsl.core.types.dsl.Expressions.allOf;

/**
 * Created by lit on 17/11/13.
 */
@Component
public class ItemPropModelConvert implements Converter<ItemProp, ItemPropModel> {

    @Autowired
    private ItemPropValueRepo itemPropValueRepo;

    @Override
    public ItemPropModel convert(ItemProp source) {
        ItemPropModel itemPropModel = new ItemPropModel();
        itemPropModel.setId(source.getId());
        itemPropModel.setBrandAppId(source.getBrandAppId());
        itemPropModel.setMemName(source.getMemName());
        itemPropModel.setUnit(source.getUnit());
        itemPropModel.setName(source.getName());
        itemPropModel.setCode(source.getCode());
        itemPropModel.setMemo(source.getMemo());

        List<ItemPropValueModel> itemPropValues = new ArrayList<>();
        itemPropValueRepo.findAll(
                allOf(
                        QItemPropValue.itemPropValue.itemPropId.eq(source.getId()),
                        QItemPropValue.itemPropValue.brandAppId.eq(source.getBrandAppId()),
                        QItemPropValue.itemPropValue.shopId.eq(source.getShopId()),
                        QItemPropValue.itemPropValue.deleted.ne(true)
                )
        ).forEach(
                itemPropValue -> {
                    ItemPropValueModel itemPropVal = new ItemPropValueModel();
                    itemPropVal.setId(itemPropValue.getId());
                    itemPropVal.setName(itemPropValue.getName());
                    itemPropValues.add(itemPropVal);
                }
        );

        itemPropModel.setPropValues(itemPropValues);
        return itemPropModel;
    }
}
