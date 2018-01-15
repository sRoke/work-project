package net.kingsilk.qh.agency.admin.controller.itemProp

import net.kingsilk.qh.agency.admin.controller.BasePageReq

/**
 * Created by lit on 17-4-6.
 */
class ItemPropListReq extends BasePageReq {
    private String itemPropKeyword

    String getItemPropKeyword() {
        return itemPropKeyword
    }

    void setItemPropKeyword(String itemPropKeyword) {
        this.itemPropKeyword = itemPropKeyword
    }
}
