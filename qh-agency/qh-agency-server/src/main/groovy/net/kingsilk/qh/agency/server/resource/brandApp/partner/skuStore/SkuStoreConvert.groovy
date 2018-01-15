package net.kingsilk.qh.agency.server.resource.brandApp.partner.skuStore

import net.kingsilk.qh.agency.api.brandApp.item.dto.ItemMinInfo
import net.kingsilk.qh.agency.api.brandApp.partner.skuStore.dto.SkuStorePageResp
import net.kingsilk.qh.agency.domain.Item
import net.kingsilk.qh.agency.domain.PartnerStaff
import net.kingsilk.qh.agency.domain.Sku
import net.kingsilk.qh.agency.domain.SkuStore
import net.kingsilk.qh.agency.repo.PartnerStaffRepo
import net.kingsilk.qh.agency.repo.SkuRepo
import net.kingsilk.qh.agency.server.resource.brandApp.item.convert.ItemConvert
import net.kingsilk.qh.agency.service.PartnerStaffService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SkuStoreConvert {
    @Autowired
    ItemConvert itemConvert

    @Autowired
    PartnerStaffService partnerStaffService

    @Autowired
    SkuRepo skuRepo

    @Autowired
    PartnerStaffRepo partnerStaffRepo

    SkuStorePageResp skuPageRespConvert(SkuStore skuStore) {
        SkuStorePageResp skuStorePageResp = new SkuStorePageResp()
        skuStorePageResp.skuInfo = itemConvert.skuConvert(skuStore.sku, null)
        skuStorePageResp.num = skuStore.num
        skuStorePageResp.skuStoreId = skuStore.id
        skuStorePageResp.salesNum = skuStore.salesVolume
        return skuStorePageResp
    }

    ItemMinInfo convertItemSearchResp(Item item) {
        ItemMinInfo itemMinInfo = new ItemMinInfo()


        PartnerStaff curMember = partnerStaffService.getCurPartnerStaff()
//        PartnerStaff curMember=partnerStaffRepo.findOne("597c2d4bd5f19a59a34c68cd")//create by yuanke 20170810
        def partnerType = curMember.partner.partnerTypeEnum.code

        List<Sku> skus = skuRepo.findAllByItemAndDeleted(item, false)
        itemMinInfo.price = -1
        itemMinInfo.partnerType = "ERROR"
        itemMinInfo.tags = item.tags
        if (skus && partnerType) {
//            Sku sku = skus[0]
            List<Sku.TagPrice> minTags = new ArrayList<>()
            skus.each { Sku sku ->
                Sku.TagPrice minTag = sku.tagPrices.findAll { Sku.TagPrice it ->
                    return partnerType == it.tag.code
                }.min { Sku.TagPrice it ->
                    it.price
                }
                minTags.add(minTag)
            }
//            //暂时随便取一条
//            Sku.TagPrice minTag = sku.tagPrices.findAll { Sku.TagPrice it ->
//                return partnerType == it.tag.code
//            }.min { Sku.TagPrice it ->
//                it.price
//            }
            itemMinInfo.skuMinSalePrice = skus.min {
                Sku miniSku ->
                    miniSku.salePrice
            }.salePrice
            itemMinInfo.price = minTags*.price.min()
            itemMinInfo.partnerType = minTags[0].tag.code
        }

        itemMinInfo.id = item.id
        itemMinInfo.title = item.title
        itemMinInfo.desp = item.desp
        itemMinInfo.imgs = item.imgs[0]

        return itemMinInfo;
    }
}
