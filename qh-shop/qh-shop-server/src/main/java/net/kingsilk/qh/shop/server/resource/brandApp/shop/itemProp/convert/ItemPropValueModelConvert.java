package net.kingsilk.qh.shop.server.resource.brandApp.shop.itemProp.convert;

import net.kingsilk.qh.shop.api.common.dto.ItemPropValueModel;
import net.kingsilk.qh.shop.domain.ItemPropValue;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by lit on 17/11/13.
 */
@Component
public class ItemPropValueModelConvert implements Converter<ItemPropValue, ItemPropValueModel> {
    @Override
    public ItemPropValueModel convert(ItemPropValue source) {
        ItemPropValueModel itemPropValueModel = new ItemPropValueModel();
        itemPropValueModel.setBrandAppId(source.getBrandAppId());
        itemPropValueModel.setCode(source.getCode());
        itemPropValueModel.setName(source.getName());
        itemPropValueModel.setMemo(source.getMemo());
        return itemPropValueModel;
    }
}
