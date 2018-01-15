package net.kingsilk.qh.agency.admin.controller.itemProp

import net.kingsilk.qh.agency.admin.controller.BasePageResp
import net.kingsilk.qh.agency.admin.controller.Search.StaffModel
import net.kingsilk.qh.agency.domain.ItemProp
import net.kingsilk.qh.agency.domain.Staff
import org.springframework.data.domain.Page

/**
 * Created by lit on 17-4-6.
 */
class ItemPropListResp extends BasePageResp {




    void convert(Iterable<ItemProp> page, ItemPropListReq req) {
        pageSize = req.pageSize
        curPage = req.curPage + 1
        recList = new ArrayList()
        page.each { ItemProp it ->
            ItemPropListModel info = new ItemPropListModel()
            info.convert(it)
            recList.add(info)
        }
    }
}
