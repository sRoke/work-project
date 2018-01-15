package net.kingsilk.qh.agency.admin.controller.itemProp

import net.kingsilk.qh.agency.admin.controller.BasePageResp
import net.kingsilk.qh.agency.domain.ItemProp
import net.kingsilk.qh.agency.domain.ItemPropValue
import org.springframework.data.domain.Page

/**
 * Created by lit on 17-4-6.
 */
class ItemPropValueListResp extends BasePageResp {
    void convert(Iterable<ItemPropValue> page, ItemPropListReq req) {
        pageSize = req.pageSize
        curPage = req.curPage + 1
        recList = new ArrayList()
        page.each { ItemPropValue it ->
            ItemPropValueListModel info = new ItemPropValueListModel()
            info.convert(it)
            recList.add(info)
        }
    }
}
