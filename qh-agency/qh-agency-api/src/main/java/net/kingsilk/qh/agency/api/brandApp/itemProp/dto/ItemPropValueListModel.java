package net.kingsilk.qh.agency.api.brandApp.itemProp.dto;

/**
 * Created by lit on 17-4-6.
 */

@Deprecated
public class ItemPropValueListModel {
//    public void convert(ItemPropValueModel itemPropValue) {
//        if (!itemPropValue.asBoolean()) {
//            return;
//
//        }
//
//        name = ((String) (itemPropValue.name));
//        id = ((String) (itemPropValue.id));
//    }


    private String id;
    private String name;

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
}
