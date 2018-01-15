package net.kingsilk.qh.shop.server.resource.brandApp.shop.itemProp.convert;

import net.kingsilk.qh.shop.api.brandApp.shop.itemProp.dto.ItemPropSaveReq;
import net.kingsilk.qh.shop.domain.ItemProp;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by lit on 17/11/13.
 */
public class ItemPropSaveReqConvert implements Converter<ItemPropSaveReq, ItemProp> {
    @Override
    public ItemProp convert(ItemPropSaveReq source) {
        ItemProp itemProp = new ItemProp();
        itemProp.setName(source.getName());
        itemProp.setMemName(source.getMemName());
        itemProp.setMemo(source.getMemo());
        itemProp.setBrandAppId(source.getBrandAppId());
        itemProp.setCode(source.getCode());
        itemProp.setUnit(source.getUnit());
        itemProp.setItemPropType(source.getType());
        return itemProp;
    }
}
