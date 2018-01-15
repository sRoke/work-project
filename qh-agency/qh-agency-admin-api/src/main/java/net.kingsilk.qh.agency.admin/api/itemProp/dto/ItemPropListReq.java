package net.kingsilk.qh.agency.admin.api.itemProp.dto;

import net.kingsilk.qh.agency.admin.api.common.dto.BasePageReq;

import javax.ws.rs.QueryParam;

/**
 * Created by lit on 17-4-6.
 */
public class ItemPropListReq extends BasePageReq{

    @QueryParam(value = "itemPropKeyword")
    private String itemPropKeyword;

    public String getItemPropKeyword() {
        return itemPropKeyword;
    }

    public void setItemPropKeyword(String itemPropKeyword) {
        this.itemPropKeyword = itemPropKeyword;
    }
}
