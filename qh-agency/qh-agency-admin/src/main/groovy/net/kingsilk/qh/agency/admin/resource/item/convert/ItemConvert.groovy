package net.kingsilk.qh.agency.admin.resource.item.convert

import net.kingsilk.qh.agency.admin.api.common.dto.ItemProp
import net.kingsilk.qh.agency.admin.api.common.dto.ItemPropValue
import org.springframework.stereotype.Component


/**
 * Created by lit on 17/7/28.
 */
@Component
class ItemConvert {
    ItemProp itemPropConvert(net.kingsilk.qh.agency.domain.ItemProp itemProp){
        ItemProp itemProp1=new ItemProp()
        itemProp1.id=itemProp.id
        itemProp1.name=itemProp.name
        itemProp1.brandId=itemProp.brandId
        itemProp1.memo=itemProp.memo
        itemProp1.code=itemProp.code
        itemProp1.itemPropType=itemProp1.itemPropType
        itemProp1.memName=itemProp.memName
        itemProp1.unit=itemProp.unit
        return itemProp1
    }

    Set<ItemPropValue> itemPropValueListConvert (Set<net.kingsilk.qh.agency.domain.ItemPropValue> itemPropValueSet){
        Set<ItemPropValue> itemPropValues=new HashSet<>()
        itemPropValueSet.each {
            ItemPropValue itemPropValue=new ItemPropValue()
            itemPropValue.id=it.id
            itemPropValue.brandId=it.brandId
            itemPropValue.itemProp=itemPropConvert(it.itemProp)
            itemPropValue.code=it.code
            itemPropValue.memo=it.memo
            itemPropValue.name=it.name
            itemPropValue.img=it.img
            itemPropValue.color=it.color
            itemPropValues.add(itemPropValue)
        }
        return itemPropValues
    }
    ItemPropValue itemPropValueConvert(net.kingsilk.qh.agency.domain.ItemPropValue itemPropValue1){
        ItemPropValue itemPropValue=new ItemPropValue()
        itemPropValue.id=itemPropValue1.id
        itemPropValue.brandId=itemPropValue1.brandId
        itemPropValue.itemProp=itemPropConvert(itemPropValue1.itemProp)
        itemPropValue.code=itemPropValue1.code
        itemPropValue.memo=itemPropValue1.memo
        itemPropValue.name=itemPropValue1.name
        itemPropValue.img=itemPropValue1.img
        itemPropValue.color=itemPropValue1.color
        return itemPropValue
    }

}
