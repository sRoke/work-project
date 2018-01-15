package net.kingsilk.qh.agency.server.resource.brandApp.itemProp.convert

import net.kingsilk.qh.agency.api.brandApp.itemProp.dto.ItemPropModel
import net.kingsilk.qh.agency.api.brandApp.itemProp.dto.ItemPropSaveReq
import net.kingsilk.qh.agency.api.brandApp.itemProp.dto.ItemPropValueModel
import net.kingsilk.qh.agency.domain.ItemProp
import net.kingsilk.qh.agency.domain.ItemPropValue
import org.springframework.stereotype.Component

/**
 * Created by yuanke on 17/8/9.
 */
@Component
class ItemPropConvert {

    ItemProp itemPropSaveReqConvert(ItemPropSaveReq itemPropSaveReq, ItemProp itemProp) {
//        ItemProp itemProp=new ItemProp()
        itemProp.setName(itemPropSaveReq.getName())
        itemProp.setMemName(itemPropSaveReq.getMemName())
        itemProp.setMemo(itemPropSaveReq.getMemo())
//        itemProp.setBrandAppId(itemPropSaveReq.getBrandAppId())
        itemProp.setCode(itemPropSaveReq.getCode())
        itemProp.setUnit(itemPropSaveReq.getUnit())
        itemProp.setItemPropType(itemPropSaveReq.getType())
        return itemProp
    }

    ItemPropValue itemPropValueConvert(ItemPropValueModel itemPropValueModel) {
        ItemPropValue itemPropValue = new ItemPropValue()
        itemPropValue.setBrandAppId(itemPropValueModel.getBrandAppId())
        itemPropValue.setCode(itemPropValueModel.getCode())
        itemPropValue.setName(itemPropValueModel.getName())
        itemPropValue.setColor(itemPropValueModel.getColor())
        itemPropValue.setImg(itemPropValueModel.getImg())
        itemPropValue.setMemo(itemPropValueModel.getMemo())
        return itemPropValue
    }

    ItemProp itemPropModelConvert(ItemPropModel itemPropModel) {
        ItemProp itemProp = new ItemProp()
        itemProp.setMemo(itemPropModel.getMemo())
        itemProp.setName(itemPropModel.getName())
        itemProp.setCode(itemPropModel.getCode())
        itemProp.setBrandAppId(itemPropModel.getBrandAppId())
        itemProp.setUnit(itemPropModel.getUnit())
        itemProp.setMemName(itemPropModel.getMemName())
        itemProp.setItemPropType(itemPropModel.getType())
        return itemProp
    }

    ItemPropModel convertToItemPropModel(ItemProp itemProp, List<ItemPropValueModel> list) {
        ItemPropModel itemPropModel = new ItemPropModel()
        itemPropModel.setId(itemProp.getId())
        itemPropModel.setBrandAppId(itemProp.getBrandAppId())
        itemPropModel.setType(itemProp.getItemPropType())
        itemPropModel.setMemName(itemProp.getMemName())
        itemPropModel.setUnit(itemProp.getUnit())
        itemPropModel.setName(itemProp.getName())
        itemPropModel.setCode(itemProp.getCode())
        itemPropModel.setMemo(itemProp.getMemo())
        itemPropModel.setPropValues(list)
        return itemPropModel
    }

    ItemPropValueModel convertToItemPropValueModel(ItemPropValue propVal) {
        ItemPropValueModel propValModel = new ItemPropValueModel()
        propValModel.setMemo(propVal.getMemo())
        propValModel.setCode(propVal.getName())
        propValModel.setName(propVal.getName())
        propValModel.setBrandAppId(propVal.getBrandAppId())
        propValModel.setId(propVal.getId())
        propValModel.setImg(propVal.getImg())
        propValModel.setColor(propVal.getColor())
        return propValModel
    }


    List<ItemPropValueModel> listConvertToItemPropValueModel(List<ItemPropValue> itemPropValues) {
        List<ItemPropValueModel> list = new ArrayList<>()

        itemPropValues.each {
            list.add(convertToItemPropValueModel(it))
        }

        return list
    }

//    def itemPropValueListRespConvert(Iterable<ItemPropValueModel> page, ItemPropListReq req) {
//        ItemPropValueListResp itemPropValueListResp = new ItemPropValueListResp()
//        itemPropValueListResp.pageSize = req.pageSize
//        itemPropValueListResp.curPage = req.curPage + 1
//        itemPropValueListResp.recList = new ArrayList()
//        page.each { ItemPropValueModel it ->
//            ItemPropValueListModel info = new ItemPropValueListModel()
//            if (!it) {
//                return
//            }
//            info.name = it.name
//            info.id = it.id
//            itemPropValueListResp.recList.add(info)
//        }
//        return itemPropValueListResp
//    }
}
