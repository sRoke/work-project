package net.kingsilk.qh.agency.server.resource.brandApp.item.convert

import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.api.common.dto.SkuInfoModel
import net.kingsilk.qh.agency.api.brandApp.item.dto.ItemInfoModel
import net.kingsilk.qh.agency.api.brandApp.item.dto.ItemMinInfo
import net.kingsilk.qh.agency.api.brandApp.itemProp.dto.ItemPropModel
import net.kingsilk.qh.agency.api.brandApp.itemProp.dto.ItemPropValueModel
import net.kingsilk.qh.agency.core.PartnerTypeEnum
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.repo.CategoryRepo
import net.kingsilk.qh.agency.repo.PartnerStaffRepo
import net.kingsilk.qh.agency.repo.SkuRepo
import net.kingsilk.qh.agency.repo.SkuStoreRepo
import net.kingsilk.qh.agency.service.PartnerStaffService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by lit on 17/7/28.
 */
@Component
class ItemConvert {
    @Autowired
    PartnerStaffService partnerStaffService

    @Autowired
    SkuRepo skuRepo

    @Autowired
    PartnerStaffRepo partnerStaffRepo;

    @Autowired
    SkuStoreRepo skuStoreRepo

//    ItemPropModel itemPropConvert(net.kingsilk.qh.agency.domain.ItemPropModel itemProp){
//        ItemPropModel itemProp1=new ItemPropModel()
//        itemProp1.id=itemProp.id
//        itemProp1.name=itemProp.name
//        itemProp1.brandId=itemProp.brandId
//        itemProp1.memo=itemProp.memo
//        itemProp1.code=itemProp.code
//        itemProp1.itemPropType=itemProp1.itemPropType
//        itemProp1.memName=itemProp.memName
//        itemProp1.unit=itemProp.unit
//        return itemProp1
//    }
//
//    Set<ItemPropValueModel> itemPropValueListConvert (Set<net.kingsilk.qh.agency.domain.ItemPropValueModel> itemPropValueSet){
//        Set<ItemPropValueModel> itemPropValues=new HashSet<>()
//        itemPropValueSet.each {
//            ItemPropValueModel itemPropValue=new ItemPropValueModel()
//            itemPropValue.id=it.id
//            itemPropValue.brandId=it.brandId
//            itemPropValue.itemProp=itemPropConvert(it.itemProp)
//            itemPropValue.code=it.code
//            itemPropValue.memo=it.memo
//            itemPropValue.name=it.name
//            itemPropValue.img=it.img
//            itemPropValue.color=it.color
//            itemPropValues.add(itemPropValue)
//        }
//        return itemPropValues
//    }
//    ItemPropValueModel itemPropValueConvert(net.kingsilk.qh.agency.domain.ItemPropValueModel itemPropValue1){
//        ItemPropValueModel itemPropValue=new ItemPropValueModel()
//        itemPropValue.id=itemPropValue1.id
//        itemPropValue.brandId=itemPropValue1.brandId
//        itemPropValue.itemProp=itemPropConvert(itemPropValue1.itemProp)
//        itemPropValue.code=itemPropValue1.code
//        itemPropValue.memo=itemPropValue1.memo
//        itemPropValue.name=itemPropValue1.name
//        itemPropValue.img=itemPropValue1.img
//        itemPropValue.color=itemPropValue1.color
//        return itemPropValue
//    }

    @Autowired
    CategoryRepo categoryRepo

    ItemPropModel itemPropConvert(net.kingsilk.qh.agency.domain.ItemProp itemProp) {
        ItemPropModel itemProp1 = new ItemPropModel()
        itemProp1.id = itemProp.id
        itemProp1.name = itemProp.name
        itemProp1.brandAppId = itemProp.brandAppId
        itemProp1.memo = itemProp.memo
        itemProp1.code = itemProp.code
        itemProp1.memName = itemProp.memName
        itemProp1.unit = itemProp.unit
        return itemProp1
    }

    Set<ItemPropValueModel> itemPropValueListConvert(Set<net.kingsilk.qh.agency.domain.ItemPropValue> itemPropValueSet) {
        Set<ItemPropValueModel> itemPropValues = new HashSet<>()
        itemPropValueSet.each {
            ItemPropValueModel itemPropValue = new ItemPropValueModel()
            itemPropValue.id = it.id
            itemPropValue.brandAppId = it.brandAppId
//            itemPropValue.itemProp = itemPropConvert(it.itemProp)
            itemPropValue.code = it.code
            itemPropValue.memo = it.memo
            itemPropValue.name = it.name
            itemPropValue.img = it.img
            itemPropValue.color = it.color
            itemPropValues.add(itemPropValue)
        }
        return itemPropValues
    }

    ItemPropValueModel itemPropValueConvert(net.kingsilk.qh.agency.domain.ItemPropValue itemPropValue1) {
        ItemPropValueModel itemPropValue = new ItemPropValueModel()
        itemPropValue.id = itemPropValue1.id
        itemPropValue.brandAppId = itemPropValue1.brandAppId
//        itemPropValue.itemProp = itemPropConvert(itemPropValue1.itemProp)
        itemPropValue.code = itemPropValue1.code
        itemPropValue.memo = itemPropValue1.memo
        itemPropValue.name = itemPropValue1.name
        itemPropValue.img = itemPropValue1.img
        itemPropValue.color = itemPropValue1.color
        return itemPropValue
    }

    ItemMinInfo itemMinInfoConvert(Item item) {
        ItemMinInfo info = new ItemMinInfo();
        info.id = item.id
        info.imgs = item.imgs[0]
        info.title = item.title

//        item.props.each {
//            if (it.itemProp && it.itemProp.name && "品牌" == it.itemProp.name) {
//                info.brand = it.itemPropValue.name
//            }
//        }
        if (item.tags) {
            Iterable<Category> categoriesList = categoryRepo.findAll(
                    Expressions.allOf(
                            QCategory.category.id.in(item.tags),
                            QCategory.category.deleted.in([null, false])
                    )
            )
            for (Category category : categoriesList) {
                info.categoryName.add(category.name)
            }
        }

//        info.categoryName = item.tags
        info.statusCode = item.status.code
        info.statusDesp = item.status.desp
        return info
    }

    SkuInfoModel skuConvert(Sku sku, String type) {
        SkuInfoModel skuInfoModel = new SkuInfoModel()
        PartnerStaff curMember = partnerStaffService.getCurPartnerStaff()
//        PartnerStaff curMember=partnerStaffRepo.findOne("597c2d4bd5f19a59a34c68cd")//create by yuanke 20170810
        def partnerType = curMember.partner.partnerTypeEnum.code

        Sku.TagPrice minTag = sku.tagPrices.findAll { Sku.TagPrice it ->
            return partnerType == it.tag.code
        }.min { Sku.TagPrice it ->
            it.price
        }
        skuInfoModel.price = minTag.price
        skuInfoModel.curTag = minTag.tag.code
        skuInfoModel.leaguePrice = sku.tagPrices.find {Sku.TagPrice it ->
            return PartnerTypeEnum.LEAGUE.code == it.tag.code
        }.price
        skuInfoModel.generalAgencyPrice = sku.tagPrices.find {Sku.TagPrice it ->
            return PartnerTypeEnum.GENERAL_AGENCY.code == it.tag.code
        }.price
        skuInfoModel.regionalAgencyPrice = sku.tagPrices.find {Sku.TagPrice it ->
            return PartnerTypeEnum.REGIONAL_AGENCY.code == it.tag.code
        }.price
        skuInfoModel.salePrice = sku.salePrice
        skuInfoModel.skuId = sku.id
        skuInfoModel.title = sku.item?.title
//        if(type=="PURCHASE"){
        skuInfoModel.imgs = sku.imgs
//        }else {
//            skuInfoModel.imgs = sku.imgs?:sku.item.imgs
//        }
        skuInfoModel.props = []
        skuInfoModel.specs = []
        sku.props.each {
            def m = [
                    name : it.itemProp?.name,
                    value: it.itemPropValue?.name
            ]
            skuInfoModel.props.add(m)
        }
        sku.specs.each {
            def m = [
                    name   : it.itemProp?.name,
                    nameId : it.itemProp?.id,
                    value  : it.itemPropValue?.name,
                    valueId: it.itemPropValue?.id
            ]
            skuInfoModel.specs.add(m)
        }
        skuInfoModel.detail = sku.detail
        skuInfoModel.status = sku.status
        SkuStore skuStore = null
        if (type == "REFUND") {
            skuStore = skuStoreRepo.findOne(
                    Expressions.allOf(
                            QSkuStore.skuStore.sku.eq(sku),
                            QSkuStore.skuStore.partner.eq(curMember.partner),
                            QSkuStore.skuStore.brandAppId.eq(curMember.brandAppId),
                    )
            )
        } else {
            skuStore = skuStoreRepo.findOne(
                    Expressions.allOf(
                            QSkuStore.skuStore.sku.eq(sku),
                            QSkuStore.skuStore.partner.eq(curMember.partner.parent),
                            QSkuStore.skuStore.brandAppId.eq(curMember.brandAppId),
                    )
            )
        }


        skuInfoModel.storage = skuStore ? skuStore.num : 0
        return skuInfoModel
    }

    ItemInfoModel convertItemInfo(Item item) {
        ItemInfoModel itemInfoModel = new ItemInfoModel();
        PartnerStaff curMember = partnerStaffService.getCurPartnerStaff()
        def partnerType = curMember.partner.partnerTypeEnum.code

        List<Sku> skus = skuRepo.findAllByItemAndDeleted(item, false)
        itemInfoModel.price = -1
        itemInfoModel.partnerType = "ERROR"
        itemInfoModel.tags = item.tags
        if (skus && partnerType) {
            Sku sku = skus[0]           //暂时随便取一条
            Sku.TagPrice minTag = sku.tagPrices.findAll { Sku.TagPrice it ->
                return partnerType == it.tag.code
            }.min { Sku.TagPrice it ->
                it.price
            }
            itemInfoModel.price = minTag.price
            itemInfoModel.partnerType = minTag.tag.code
        }
        itemInfoModel.skuMinSalePrice = skus.min {
            Sku miniSku ->
                miniSku.salePrice
        }.salePrice
        itemInfoModel.itemId = item.id
        itemInfoModel.title = item.title
        itemInfoModel.itemUnit = item.itemUnit
        itemInfoModel.desp = item.desp
        itemInfoModel.imgs = item.imgs

        itemInfoModel.props = []
        itemInfoModel.specs = []
        item.props.each {
            def m = [
                    name : it.itemProp?.name,
                    value: it.itemPropValue?.name
            ]
            itemInfoModel.props.add(m)
        }
        item.specs.each {
            def values = []
            it.itemPropValues.each {
                values.add([
                        valueId: it.id,
                        value  : it.name
                ])
            }
            def m = [
                    name  : it.itemProp?.name,
                    nameId: it.itemProp?.id,
                    values: values
            ]
            itemInfoModel.specs.add(m)
        }
        itemInfoModel.detail = item.detail
        itemInfoModel.tags = item.tags
        itemInfoModel.skus = []
        return itemInfoModel;
    }
}
