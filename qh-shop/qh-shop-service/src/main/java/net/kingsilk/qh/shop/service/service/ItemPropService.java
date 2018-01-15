package net.kingsilk.qh.shop.service.service;

import net.kingsilk.qh.shop.core.ItemPropTypeEnum;
import net.kingsilk.qh.shop.domain.Item;
import net.kingsilk.qh.shop.domain.ItemProp;
import net.kingsilk.qh.shop.domain.ItemPropValue;
import net.kingsilk.qh.shop.domain.Sku;
import net.kingsilk.qh.shop.repo.ItemPropRepo;
import net.kingsilk.qh.shop.repo.ItemPropValueRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ItemPropService {

    @Autowired
    private ItemPropRepo itemPropRepo;

    @Autowired
    private ItemPropValueRepo itemPropValueRepo;




    public Sku.Spec skuSpecPackage(Item item,String code,String propName,String valueName){
        ItemProp itemProp = new ItemProp();
        itemProp.setBrandAppId(item.getBrandAppId());
        itemProp.setShopId(item.getShopId());
        itemProp.setCode(code);
        itemProp.setName(propName);
        itemProp.setItemPropType(ItemPropTypeEnum.TEXT);
        itemPropRepo.save(itemProp);

        ItemPropValue itemPropValue = new ItemPropValue();
        itemPropValue.setBrandAppId(item.getBrandAppId());
        itemPropValue.setShopId(item.getShopId());
        itemPropValue.setCode(code);
        itemPropValue.setName(valueName);
        itemPropValueRepo.save(itemPropValue);

        Sku.Spec skuSpec = new Sku.Spec();          //todo 是否先查该商品有该商品属性名。
        skuSpec.setItemPropId(itemProp.getId());
        skuSpec.setItemPropValueId(itemPropValue.getId());

        Item.SpecDef specDef = new Item.SpecDef();
        specDef.setItemPropId(itemProp.getId());
        Set<String> itemPropValueIds = specDef.getItemPropValueIds();
        itemPropValueIds.add(itemPropValue.getId());
        item.getSpecs().add(specDef);

        return skuSpec;
    }


//    ItemProp itemProp2 = new ItemProp();
//                    itemProp2.setBrandAppId(brandAppId);
//                    itemProp2.setShopId(shopId);
//                    itemProp2.setCode(row.get(2));
//                    itemProp2.setName(excelHeadPropsList.get(4));
//                    itemProp2.setItemPropType(ItemPropTypeEnum.TEXT);
//                    itemPropRepo.save(itemProp2);
//
//    ItemPropValue itemPropValue2 = new ItemPropValue();
//                    itemPropValue2.setBrandAppId(brandAppId);
//                    itemPropValue2.setShopId(shopId);
//                    itemPropValue2.setCode(row.get(2));
//                    itemPropValue2.setName(row.get(4));
//                    itemPropValueRepo.save(itemPropValue2);
}
