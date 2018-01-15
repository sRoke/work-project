package net.kingsilk.qh.agency.admin.controller.itemProp

import net.kingsilk.qh.agency.core.ItemPropTypeEnum
import net.kingsilk.qh.agency.domain.ItemProp
import net.kingsilk.qh.agency.domain.ItemPropValue

/**
 * Created by lit on 17-4-6.
 */
class ItemPropValueListModel {
     String id
     String name;


    
    
    void convert(ItemPropValue itemPropValue) {
        if (!itemPropValue) {
            return;
        }
        name = itemPropValue.name
        id = itemPropValue.id
    }
}
