package net.kingsilk.qh.agency.server.resource.brandApp.itemProp

import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.api.UniPageReq
import net.kingsilk.qh.agency.api.UniPageResp
import net.kingsilk.qh.agency.api.UniResp
import net.kingsilk.qh.agency.api.brandApp.itemProp.ItemPropApi
import net.kingsilk.qh.agency.api.brandApp.itemProp.dto.ItemPropModel
import net.kingsilk.qh.agency.api.brandApp.itemProp.dto.ItemPropPageReq
import net.kingsilk.qh.agency.api.brandApp.itemProp.dto.ItemPropSaveReq
import net.kingsilk.qh.agency.api.brandApp.itemProp.dto.ItemPropValueModel
import net.kingsilk.qh.agency.domain.ItemProp
import net.kingsilk.qh.agency.domain.ItemPropValue
import net.kingsilk.qh.agency.domain.QItemProp
import net.kingsilk.qh.agency.domain.QItemPropValue
import net.kingsilk.qh.agency.repo.ItemPropRepo
import net.kingsilk.qh.agency.repo.ItemPropValueRepo
import net.kingsilk.qh.agency.server.resource.brandApp.itemProp.convert.ItemPropConvert
import net.kingsilk.qh.agency.service.ItemPropValueService
import net.kingsilk.qh.agency.service.PartnerStaffService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.convert.ConversionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import org.springframework.util.Assert

@Component
class ItemPropResource implements ItemPropApi {

    @Autowired
    ItemPropRepo itemPropRepo

    @Autowired
    ItemPropValueService itemPropValueService

    @Autowired
    ItemPropValueRepo itemPropValueRepo

    @Autowired
    ItemPropConvert itemPropConvert

    @Autowired
    PartnerStaffService partnerStaffService

    @Autowired
    @Qualifier(value = "mvcConversionService")
    ConversionService conversionService

    @Override
    UniResp<UniPageResp<ItemPropModel>> page(String bandComId, ItemPropPageReq itemPropPageReq) {

        Assert.notNull(bandComId, "品牌商id不能为空")

        Page<ItemProp> itemPropPage = itemPropRepo.findAll(
                Expressions.allOf(
                        QItemProp.itemProp.deleted.in([null, false]),
                        QItemProp.itemProp.brandAppId.eq(bandComId),
                        itemPropPageReq.name ? QItemProp.itemProp.name.like("%" + itemPropPageReq.name + "%") : null
                ), new PageRequest(itemPropPageReq.page, itemPropPageReq.size, new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")
        )))

        UniResp<UniPageResp<ItemPropModel>> uniResp = new UniResp<>()

        //类型转换
        //先把返回的自定义page类型和spring的page类型转换
        //再在参数中处理api中dto的类型和damain的类型
        uniResp.data = conversionService.convert(itemPropPage, UniPageResp)
        itemPropPage.content.each {
            ItemProp itemProp ->
                uniResp.data.content.add(
                        itemPropConvert.convertToItemPropModel(
                                itemProp,
                                itemPropConvert.listConvertToItemPropValueModel(
                                        itemPropValueService.search(itemProp.id, bandComId).asList()))
                )
        }

        uniResp.status = 200
        return uniResp
    }


    @Override
    UniResp<String> save(String bandComId, ItemPropSaveReq itemPropSaveReq) {

        Assert.notNull(bandComId, "品牌商id不能为空")

        ItemProp it = new ItemProp();

        ItemProp itemProp = itemPropConvert.itemPropSaveReqConvert(itemPropSaveReq, it)
        itemProp.setBrandAppId(bandComId)
        ItemProp itProp = itemPropRepo.save(itemProp)

        //遍历属性值，并保存
        itemPropSaveReq.itemPropValues.each {
            ItemPropValueModel itemPropValueModel ->
                itemPropValueModel.setBrandAppId(bandComId)
                ItemPropValue itemPropValue = itemPropConvert.itemPropValueConvert(itemPropValueModel)
                itemPropValue.setItemProp(itProp)
                itemPropValueRepo.save(itemPropValue)
        }



        Assert.notNull(itProp, "属性保存失败")

        return new UniResp<String>(status: 200, data: "保存成功")
    }


    @Override
    UniResp<String> update(
            String bandComId, String id, ItemPropSaveReq itemPropSaveReq) {
        Assert.notNull(bandComId, "品牌商id不能为空")
        ItemProp itemProp = itemPropRepo.findOne(
                Expressions.allOf(
                        QItemProp.itemProp.deleted.in([false, null]),
                        QItemProp.itemProp.brandAppId.eq(bandComId),
                        QItemProp.itemProp.id.eq(id)
                ))

        Assert.notNull(itemProp, "未找到该属性")

        //获取新的属性值
        Set<ItemPropValueModel> newItemPropValue = itemPropSaveReq.itemPropValues

        // 根据属性查找属性值列表
        def list = itemPropValueService.search(itemProp.id, bandComId)
        List<String> oldIdList = new ArrayList()
        List<String> haveIdList = new ArrayList()

        //找到修改的时候属性值中不需要删除的
        for (ItemPropValue oldPropValue : list) {
            oldIdList.add(oldPropValue.id)
            for (ItemPropValueModel newPropValue : newItemPropValue) {
                if (oldPropValue.id == newPropValue.id) {
                    haveIdList.add(oldPropValue.id)
                }
            }
        }
        //移除不需要删除的属性值
        oldIdList.removeAll(haveIdList)

        //删除修改时删除的属性值
        if (oldIdList.size() > 0) {
            for (String itemPropValueId : oldIdList) {
                ItemPropValue itemPropValue = itemPropValueRepo.findOne(
                        Expressions.allOf(
                                QItemPropValue.itemPropValue.id.eq(itemPropValueId),
                                QItemPropValue.itemPropValue.brandAppId.eq(bandComId)
                        )
                )
                itemPropValue.setDeleted(true)
                itemPropValueRepo.save(itemPropValue)
            }
        }

//        List<ItemPropValue> list2 = new ArrayList<ItemPropValue>()
        // 把修改时修改的属性新增至属性表中
        newItemPropValue.each {
            //判断属性是否已经拥有，没有则新增一条属性
            if (it.getId() == null) {
                ItemPropValue itemPropValue =
                        itemPropConvert.itemPropValueConvert(it)
                itemPropValue.itemProp = itemProp
                itemPropValue.brandAppId = bandComId
                itemPropValueRepo.save(itemPropValue)
//                list2.add(itemPropValue)
            }
        }


        itemProp = itemPropConvert.itemPropSaveReqConvert(itemPropSaveReq, itemProp)
//        itemProp.setId(id)
        itemProp.brandAppId = bandComId
        itemPropRepo.save(itemProp)

        return new UniResp<String>(status: 200, data: "更新成功")
    }


    @Override
    UniResp<String> delete(String bandComId, String itemPropId) {
        Assert.notNull(bandComId, "品牌商id不能为空")

        ItemProp itemProp = itemPropRepo.findOne(
                Expressions.allOf(
                        QItemProp.itemProp.brandAppId.eq(bandComId),
                        QItemProp.itemProp.id.eq(itemPropId)
                ))

        Assert.notNull(itemProp, "所删除的数据不存在")

        itemProp.deleted = true

        itemPropRepo.save(itemProp)

        return new UniResp(status: 200, data: "保存成功")
    }


    @Override
    UniResp<ItemPropModel> info(String bandComId, String itemPropid) {

        Assert.notNull(bandComId, "品牌商id不能为空")

        ItemProp itemProp = itemPropRepo.findOne(
                Expressions.allOf(
                        QItemProp.itemProp.deleted.in([false, null]),
                        QItemProp.itemProp.brandAppId.eq(bandComId),
                        QItemProp.itemProp.id.eq(itemPropid)
                )
        )
        Assert.notNull(itemProp, "所查找的数据不存在")

        def list = itemPropValueService.search(itemProp.id, bandComId).asList()


        return new UniResp<ItemPropModel>(status: 200,
                data: itemPropConvert.convertToItemPropModel(itemProp,
                        itemPropConvert.listConvertToItemPropValueModel(list)))
    }

    @Override
    UniResp<List<ItemPropModel>> list(String bandComId, String itemPropKeyword,
                                      UniPageReq uniPageReq) {

        Assert.notNull(bandComId, "品牌商id不能为空")

        List<ItemProp> itemProp = itemPropRepo.findAll(
                Expressions.allOf(
                        QItemProp.itemProp.deleted.in([false, null]),
                        QItemProp.itemProp.brandAppId.eq(bandComId),
                        itemPropKeyword ? QItemProp.itemProp.name.like("%" + itemPropKeyword + "%") : null,
                )
        )

        UniResp<List<ItemPropModel>> uniResp = new UniResp<>()

        if (itemProp.size() > 0) {
            List<ItemPropModel> itemPropModelList = new LinkedList<>()
            itemProp.each { ItemProp it ->
                itemPropModelList.add(itemPropConvert.convertToItemPropModel(it, null))
            }
            uniResp.data = itemPropModelList
            uniResp.status = 200
        } else {
            uniResp.setStatus(200)
        }
        return uniResp
    }

    @Override
    UniResp<ItemPropValueModel> addPropValue(String brandAppId, String name, String id) {
        UniResp<ItemPropValue> umoRespString = new UniResp<>()
        ItemProp itemProp = itemPropRepo.findOne(id)
        ItemPropValue itemPropValue = new ItemPropValue()
        itemPropValue.itemProp = itemProp
        itemPropValue.brandAppId = brandAppId
        itemPropValue.name = name
        itemPropValueRepo.save(itemPropValue)
        umoRespString.status = 200
        umoRespString.data = itemPropConvert.convertToItemPropValueModel(itemPropValue)
        return umoRespString
    }

    @Override
    UniResp<List<ItemPropValueModel>> itemPropListItem(String bandComId,
                                                       String itemPropKeyword,
                                                       UniPageReq uniPageReq) {

        Assert.notNull(bandComId, "品牌商id不能为空")

        List<ItemPropValue> itemPropValue = itemPropValueRepo.findAll(
                Expressions.allOf(
                        QItemPropValue.itemPropValue.itemProp.id.eq(itemPropKeyword),
                        QItemPropValue.itemPropValue.deleted.in([false, null]))
        )
        UniResp<List<ItemPropValueModel>> uniResp = new UniResp<>()

        //类型转换
        //先把返回的自定义page类型和spring的page类型转换
        //再在参数中处理api中dto的类型和damain的类型
        List<ItemPropValueModel> itemPropValueModelList = new LinkedList<>()
        itemPropValue.each { ItemPropValue it ->
            itemPropValueModelList.add(itemPropConvert.convertToItemPropValueModel(it))
        }
        uniResp.data = itemPropValueModelList
        uniResp.status = 200
        return uniResp
    }


}
