package net.kingsilk.qh.agency.wap.resource.item.convert

import net.kingsilk.qh.agency.domain.Item
import net.kingsilk.qh.agency.domain.PartnerStaff
import net.kingsilk.qh.agency.domain.Sku
import net.kingsilk.qh.agency.repo.SkuRepo
import net.kingsilk.qh.agency.service.MemberService
import net.kingsilk.qh.agency.wap.api.item.dto.ItemInfoModel
import net.kingsilk.qh.agency.wap.api.item.dto.ItemMiniInfoModel
import net.kingsilk.qh.agency.wap.api.item.dto.SkuInfoModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by lit on 17/7/20.
 */
@Component
class ItemConvert {

    @Autowired
    SkuRepo skuRepo;


    @Autowired
    MemberService memberService

    List<ItemMiniInfoModel> convertItemSearchResp(Iterator<Item> items) {
        List<ItemMiniInfoModel> resp = new ArrayList<>();
        while (items.hasNext()) {
            Item item = items.next();
            ItemMiniInfoModel itemMiniInfoModel = new ItemMiniInfoModel();

            PartnerStaff curMember = memberService.getCurPartnerStaff()
            def partnerType = curMember.partner.partnerTypeEnum.code

            List<Sku> skus = skuRepo.findAllByItemAndDeleted(item, false)
            itemMiniInfoModel.price = -1
            itemMiniInfoModel.partnerType = "ERROR"
            itemMiniInfoModel.tags = item.tags
            if (skus && partnerType) {
                Sku sku = skus[0]           //暂时随便取一条
                Sku.TagPrice minTag = sku.tagPrices.findAll { Sku.TagPrice it ->
                    return partnerType == it.tag.code
                }.min { Sku.TagPrice it ->
                    it.price
                }
                itemMiniInfoModel.price = minTag.price/100
                itemMiniInfoModel.partnerType = minTag.tag.code
            }

            itemMiniInfoModel.itemId = item.id
            itemMiniInfoModel.title = item.title
            itemMiniInfoModel.itemUnit = item.itemUnit
            itemMiniInfoModel.desp = item.desp
            itemMiniInfoModel.imgs = item.imgs
            resp.add(itemMiniInfoModel)
        }

        return resp;
    }


    ItemInfoModel convertItemInfo(Item item) {
        ItemInfoModel itemInfoModel = new ItemInfoModel();
        PartnerStaff curMember = memberService.getCurPartnerStaff()
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
            itemInfoModel.price = minTag.price/100
            itemInfoModel.partnerType = minTag.tag.code
        }

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

    SkuInfoModel skuConvert(Sku sku) {
        SkuInfoModel skuInfoModel = new SkuInfoModel()
        PartnerStaff curMember = memberService.getCurPartnerStaff()
        def partnerType = curMember.partner.partnerTypeEnum.code
        Sku.TagPrice minTag = sku.tagPrices.findAll { Sku.TagPrice it ->

            return partnerType == it.tag.code
        }.min { Sku.TagPrice it ->
            it.price
        }
        skuInfoModel.price = minTag.price/100
        skuInfoModel.curTag = minTag.tag.code
        skuInfoModel.skuId = sku.id
        skuInfoModel.title = sku.item?.title
        skuInfoModel.imgs = sku.item?.imgs
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
        return skuInfoModel
    }

}
