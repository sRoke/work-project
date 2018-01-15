package net.kingsilk.qh.agency.admin.resource.itemProp.convert

import net.kingsilk.qh.agency.admin.api.itemProp.dto.*
import net.kingsilk.qh.agency.domain.ItemProp
import net.kingsilk.qh.agency.domain.ItemPropValue
import org.springframework.stereotype.Component

/**
 * Created by lit on 17/7/21.
 */
@Component
class ItemPropConvert {

    def itemPropSaveReqConvert(ItemPropSaveReq itemPropSaveReq, ItemProp itemProp) {
        itemProp.setName(itemPropSaveReq.name)
        itemProp.setMemName(itemPropSaveReq.memName)
        itemProp.setMemo(itemPropSaveReq.memo)
        return itemProp
    }

    ItemPropValue convertToItemPropValue(ItemPropValue itemPropValue, ItemProp itemProp, ItemPropSaveReq.ItemPropValues itemPropValues) {
        itemPropValue.setName(itemPropValues.name)
//        itemPropValue.setCompany(itemProp.getCompany())
        itemPropValue.setItemProp(itemProp)
        return itemPropValue
    }

    def itemPropInfoRespConvert(ItemProp itemProp, List<ItemPropValue> valuesList) {
        ItemPropInfoResp itemPropResp = new ItemPropInfoResp()

        itemPropResp.code = itemProp.code
        itemPropResp.id = itemProp.id
        itemPropResp.type = itemProp.itemPropType
        itemPropResp.name = itemProp.name
        itemPropResp.dateCreated = itemProp.dateCreated
//        itemPropResp.companyId = itemProp.company == null ? null : itemProp.company.id
//        itemPropResp.companyName = itemProp.company == null ? null : itemProp.company.name
        itemPropResp.unit = itemProp.unit
        itemPropResp.memName = itemProp.memName
        itemPropResp.memo = itemProp.memo
        itemPropResp.lastModifiedDate = itemProp.lastModifiedDate
        for (ItemPropValue itemPropValue : valuesList) {
            itemPropResp.itemPropValues.add(itemPropValue)
        }
        return itemPropResp
    }

    def itemPropListRespConvert(Iterable<ItemProp> page, ItemPropListReq req) {
        ItemPropListResp itemPropListResp = new ItemPropListResp()
        itemPropListResp.pageSize = req.pageSize
        itemPropListResp.curPage = req.curPage + 1
        itemPropListResp.recList = new ArrayList()
        page.each { ItemProp it ->
            ItemPropListModel info = new ItemPropListModel()
            if (!it) {
                return
            }
            info.name = it.name
            info.id = it.id
            info.type = it.itemPropType
            itemPropListResp.recList.add(info)
        }
        return itemPropListResp
    }

    def itemPropValueListRespConvert(Iterable<ItemPropValue> page, ItemPropListReq req) {
        ItemPropValueListResp itemPropValueListResp = new ItemPropValueListResp()
        itemPropValueListResp.pageSize = req.pageSize
        itemPropValueListResp.curPage = req.curPage + 1
        itemPropValueListResp.recList = new ArrayList()
        page.each { ItemPropValue it ->
            ItemPropValueListModel info = new ItemPropValueListModel()
            if (!it) {
                return
            }
            info.name = it.name
            info.id = it.id
            itemPropValueListResp.recList.add(info)
        }
        return itemPropValueListResp
    }
}
