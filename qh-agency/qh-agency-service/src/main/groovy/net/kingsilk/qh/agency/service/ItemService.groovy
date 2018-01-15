package net.kingsilk.qh.agency.service

import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.api.brandApp.item.dto.ItemSaveReq
import net.kingsilk.qh.agency.api.brandApp.item.dto.SkuMiniInfo
import net.kingsilk.qh.agency.core.ItemStatusEnum
import net.kingsilk.qh.agency.core.PartnerTypeEnum
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.repo.*
import net.kingsilk.qh.agency.security.BrandAppIdFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by lit on 17/7/20.
 */
@Service
class ItemService {

    @Autowired
    ItemPropRepo itemPropRepo

    @Autowired
    ItemPropValueRepo itemPropValueRepo

    @Autowired
    SkuRepo skuRepo

    @Autowired
    ItemRepo itemRepo

    @Autowired
    PartnerRepo partnerRepo

    @Autowired
    SkuStoreRepo skuStoreRepo

    @Autowired
    SecService secService

    def saveItem(Item item, ItemSaveReq itemSaveReq) {

        Partner partner = partnerRepo.findOneByPartnerTypeEnumAndBrandAppId(PartnerTypeEnum.BRAND_COM, BrandAppIdFilter.brandAppId)

        Map<String,List> map=new HashMap<>()
        List<String> skuStoreList =new ArrayList<>()

        item.lastModifiedDate = new Date()

        item.specs.clear()
        itemSaveReq.specs.each { ItemSaveReq.SpecDef itemSpecDef ->

            ItemProp itemProp = itemPropRepo.findOne(itemSpecDef.getItemPropId())
            Set<ItemPropValue> itemPropValues = new HashSet<>()
            itemSpecDef.getItemPropValueIds().each { String id ->
                ItemPropValue propValue = itemPropValueRepo.findOne(id)
                itemPropValues.add(propValue)
            }
            Item.SpecDef itemSpecDef1 = new Item.SpecDef()
            itemSpecDef1.itemProp = itemProp
            itemSpecDef1.itemPropValues = itemPropValues
            itemSpecDef1.id = itemSpecDef.id
            item.specs.add(itemSpecDef1)
        }
        item.brandAppId=BrandAppIdFilter.getBrandAppId()
        item.code = itemSaveReq.code
        item.title = itemSaveReq.title
        item.detail = itemSaveReq.detail
        item.desp = itemSaveReq.desp
        item.tags = itemSaveReq.tags
        item.status = ItemStatusEnum.valueOf(itemSaveReq.status)
        item.imgs = itemSaveReq.imgs
        itemRepo.save(item)
        Iterable<Sku> deleteSkus = skuRepo.findAll(
                Expressions.allOf(
                        QSku.sku.item.eq(item)
                )
        )
        for (Sku deleteSku : deleteSkus) {
//            skuRepo.delete(deleteSku)
            deleteSku.deleted = true
        }

        itemSaveReq.skuList.each {

            Sku sku = skuRepo.findOne(it.id)
            if (!sku) {
                sku = new Sku();
                sku.dateCreated = new Date();
            }
            sku.deleted = false
            sku.lastModifiedDate = new Date()
            sku.item = item
            sku.specs.clear()
            it.specList.each { SkuMiniInfo.SkuSpec itemSpecDef ->
                ItemProp itemProp = itemPropRepo.findOne(itemSpecDef.itemPropId)
                ItemPropValue itemPropValue = itemPropValueRepo.findOne(itemSpecDef.itemPropValueId)
                Sku.Spec spec = new Sku.Spec();
                spec.itemProp = itemProp;
                spec.itemPropValue = itemPropValue
                spec.id = itemSpecDef.id;
                sku.specs.add(spec);
            }
            sku.labelPrice = it.labelPrice
            sku.salePrice = it.salePrice
            sku.code=it.code
            sku.imgs.clear()
            if(it.imgs){
                sku.imgs.add(it.imgs)
            }else {
                sku.imgs.add(itemSaveReq.imgs[0])
            }
            sku.status = it.status
            sku.tagPrices.clear()
            if (it.leaguePrice) {
                Sku.TagPrice tagPrice = new Sku.TagPrice()
                tagPrice.tag = PartnerTypeEnum.LEAGUE
                tagPrice.price = it.leaguePrice
                sku.tagPrices.add(tagPrice)
            }
            if (it.generalAgencyPrice) {
                Sku.TagPrice tagPrice = new Sku.TagPrice()
                tagPrice.tag = PartnerTypeEnum.GENERAL_AGENCY
                tagPrice.price = it.generalAgencyPrice
                sku.tagPrices.add(tagPrice)
            }
            if (it.regionalAgencyPrice) {
                Sku.TagPrice tagPrice = new Sku.TagPrice()
                tagPrice.tag = PartnerTypeEnum.REGIONAL_AGENCY
                tagPrice.price = it.regionalAgencyPrice
                sku.tagPrices.add(tagPrice)
            }
            skuRepo.save(sku)

            /**
             * 新建商品完成后保存库存信息
             */
            SkuStore skuStore = skuStoreRepo.findOne(
                    Expressions.allOf(
                            QSkuStore.skuStore.sku.eq(sku),
                            QSkuStore.skuStore.partner.eq(partner)
                    )
            )
            if (!skuStore) {
                skuStore = new SkuStore()
                skuStore.dateCreated=new Date()
            }
            skuStore.brandAppId = BrandAppIdFilter.getBrandAppId()
            skuStore.lastModifiedDate=new Date()
            skuStore.partner = partner
            skuStore.sku = sku
            skuStore.num = it.storage
            skuStoreRepo.save(skuStore)
            skuStoreList.add(skuStore.id)
        }
        List<String> itemIdList=new ArrayList<>()
        itemIdList.add(item.id)
        map.put("itemId",itemIdList)
        map.put("skuStoreIds",skuStoreList)
        return map
    }

}
