package net.kingsilk.qh.agency.admin.api.itemProp.dto;

import net.kingsilk.qh.agency.core.ItemPropTypeEnum;

/**
 * Created by lit on 17-4-6.
 */
public class ItemPropListModel {
//    public void convert(ItemProp itemProp) {
//        if (!itemProp.asBoolean()) {
//            return;
//
//        }
//
//        name = ((String) (itemProp.name));
//        id = ((String) (itemProp.id));
//        type = ((ItemPropTypeEnum) (itemProp.itemPropType));
//    }


    private String id;
    private String name;
    private ItemPropTypeEnum type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemPropTypeEnum getType() {
        return type;
    }

    public void setType(ItemPropTypeEnum type) {
        this.type = type;
    }
}
