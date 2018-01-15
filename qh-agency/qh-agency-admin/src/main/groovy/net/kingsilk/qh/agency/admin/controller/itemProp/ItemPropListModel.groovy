package net.kingsilk.qh.agency.admin.controller.itemProp

import net.kingsilk.qh.agency.core.ItemPropTypeEnum
import net.kingsilk.qh.agency.domain.ItemProp

/**
 * Created by lit on 17-4-6.
 */
class ItemPropListModel {
     String id
     String name;
     ItemPropTypeEnum type;


    
    
    void convert(ItemProp itemProp) {
        if (!itemProp) {
            return;
        }
        name = itemProp.name
        id = itemProp.id
        type = itemProp.itemPropType
    }
}
