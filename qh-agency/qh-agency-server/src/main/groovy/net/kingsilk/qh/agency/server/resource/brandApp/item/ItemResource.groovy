package net.kingsilk.qh.agency.server.resource.brandApp.item

import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.api.UniPageResp
import net.kingsilk.qh.agency.api.UniResp
import net.kingsilk.qh.agency.api.brandApp.item.ItemApi
import net.kingsilk.qh.agency.api.brandApp.item.dto.*
import net.kingsilk.qh.agency.api.common.dto.SkuInfoModel
import net.kingsilk.qh.agency.core.ItemStatusEnum
import net.kingsilk.qh.agency.core.PartnerTypeEnum
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.es.domain.EsItem
import net.kingsilk.qh.agency.msg.api.search.esItem.sync.SyncEvent
import net.kingsilk.qh.agency.msg.impl.EventPublisherImpl
import net.kingsilk.qh.agency.repo.*
import net.kingsilk.qh.agency.server.resource.brandApp.item.convert.ItemConvert
import net.kingsilk.qh.agency.service.ItemService
import net.kingsilk.qh.agency.service.PartnerStaffService
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.convert.ConversionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.data.elasticsearch.core.query.SearchQuery
import org.springframework.stereotype.Component
import org.springframework.util.Assert
import org.springframework.util.StringUtils

import static org.elasticsearch.index.query.QueryBuilders.*

/**
 * Created by tpx on 17-3-14.
 * <p>
 * <p>
 * <p>
 * 新增：
 * 1. get /api/category/list  获取 “分类标签列表”
 * 1. 查询商品熟悉个 /api/itemProp/search
 * 1. POST /api/sku/add  注意：批量
 */
@Component(value = "itemResource")
class ItemResource implements ItemApi {

    @Autowired
    ItemRepo itemRepo

    @Autowired
    ItemPropRepo itemPropRepo

    @Autowired
    ItemPropValueRepo itemPropValueRepo

    @Autowired
    SkuRepo skuRepo

    @Autowired
    CategoryRepo categoryRepo

    @Autowired
    ItemConvert itemConvert

    @Autowired
    PartnerRepo partnerRepo

    @Autowired
    SkuStoreRepo skuStoreRepo

    @Autowired
    @Qualifier(value = "mvcConversionService")
    ConversionService conversionService

    @Autowired
    ItemService itemService

    @Autowired
    private PartnerStaffService partnerStaffService

    @Autowired
    private EventPublisherImpl eventPublisher;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate

    @Override
    UniResp<String> save(
            String brandAppId,
            ItemSaveReq itemSaveReq
    ) {

        if (!itemSaveReq.specs || itemSaveReq.specs.size() == 0) {
            return new UniResp<String>(status: 10024, data: "请至少选择一种规格")
        }
        if (!itemSaveReq.title) {
            return new UniResp<String>(status: 10024, data: "请选择主标题")
        }
        if (!itemSaveReq.detail) {
            return new UniResp<String>(status: 10024, data: "请编写描述")
        }
        if (!itemSaveReq.tags || itemSaveReq.tags.size() == 0) {
            return new UniResp<String>(status: 10024, data: "请至少选择一种标签")
        }
        if (!itemSaveReq.skuList || itemSaveReq.skuList.size() == 0) {
            return new UniResp<String>(status: 10024, data: "请至少选择一种sku")
        }
        Iterator specsIterator = itemSaveReq.specs.iterator()
        while (specsIterator.hasNext()) {
            Integer ids = specsIterator.next().itemPropValueIds.size()
            if (ids == 0) {
                return new UniResp<String>(status: 10024, data: "请至少选择一种属性值")
            }
        }

        Iterator skuIterator = itemSaveReq.skuList.iterator()
        while (skuIterator.hasNext()) {
            def skuNext = skuIterator.next()
            Integer generalAgencyPrice = skuNext.generalAgencyPrice
            Integer labelPrice = skuNext.labelPrice
            Integer leaguePrice = skuNext.leaguePrice
            Integer regionalAgencyPrice = skuNext.regionalAgencyPrice
            Integer salePrice = skuNext.salePrice
            Integer storage = skuNext.storage
            if (!generalAgencyPrice) {
                return new UniResp<String>(status: 10024, data: "请输入总代价")
            }
            if (!labelPrice) {
                return new UniResp<String>(status: 10024, data: "请输入吊牌价")
            }
            if (!leaguePrice) {
                return new UniResp<String>(status: 10024, data: "请输入加盟价")
            }
            if (!regionalAgencyPrice) {
                return new UniResp<String>(status: 10024, data: "请输入市代价")
            }
            if (!salePrice) {
                return new UniResp<String>(status: 10024, data: "请输入销售价")
            }
            if (!storage) {
                return new UniResp<String>(status: 10024, data: "请输入库存")
            }
        }

        if (!itemSaveReq.imgs || itemSaveReq.imgs.size() == 0) {
            return new UniResp<String>(status: 10024, data: "请至少上传一张图片")
        }

        if (!itemSaveReq.status) {
            return new UniResp<String>(status: 10024, data: "请选择状态")
        }
        Boolean itemSkuStatus = false
        for (SkuMiniInfo sku : itemSaveReq.skuList) {
            if (sku.status == "false") {
                itemSkuStatus = true
            }
        }
        if (itemSaveReq.status == ItemStatusEnum.NORMAL && !itemSkuStatus) {
            return new UniResp<String>(status: 10024, data: "SKU全部禁用不能为正常状态")
        }
//        Item item = null;
//        if (itemSaveReq.id) {
//            item = itemRepo.findOne(itemSaveReq.id)
//            if (!item) {
//                return new UniResp<String>(status: 10024, data: "商品不存在")
//            }
//        } else {
//
//        }
        Item item = new Item()
        item.dateCreated = new Date()
        Map<String, List<String>> ids = itemService.saveItem(item, itemSaveReq)
        if (ids.size() > 0) {
            ids.get("itemId").each {
                SyncEvent syncEvent = new SyncEvent()
                syncEvent.itemId = it
                eventPublisher.publish(syncEvent);
            }

            ids.get("skuStoreIds").each {
                net.kingsilk.qh.agency.msg.api.search.esSkuStore.sync.SyncEvent syncEvent = new net.kingsilk.qh.agency.msg.api.search.esSkuStore.sync.SyncEvent()
                syncEvent.skuStoreId = it
                eventPublisher.publish(syncEvent);
            }
        }
        return new UniResp<String>(status: 200, data: item.id)
    }


    @Override
    UniResp<String> update(
            String brandAppId,
            String id,
            ItemSaveReq itemSaveReq) {

        if (!itemSaveReq.specs || itemSaveReq.specs.size() == 0) {
            return new UniResp<String>(status: 10024, data: "请至少选择一种规格")
        }
        if (!itemSaveReq.title) {
            return new UniResp<String>(status: 10024, data: "请选择主标题")
        }
        if (!itemSaveReq.detail) {
            return new UniResp<String>(status: 10024, data: "请编写描述")
        }
        if (!itemSaveReq.tags || itemSaveReq.tags.size() == 0) {
            return new UniResp<String>(status: 10024, data: "请至少选择一种标签")
        }
        if (!itemSaveReq.skuList || itemSaveReq.skuList.size() == 0) {
            return new UniResp<String>(status: 10024, data: "请至少选择一种sku")
        }
        Iterator specsIterator = itemSaveReq.specs.iterator()
        while (specsIterator.hasNext()) {
            Integer ids = specsIterator.next().itemPropValueIds.size()
            if (ids == 0) {
                return new UniResp<String>(status: 10024, data: "请至少选择一种属性值")
            }
        }

        Iterator skuIterator = itemSaveReq.skuList.iterator()
        while (skuIterator.hasNext()) {
            def skuNext = skuIterator.next()
            Integer generalAgencyPrice = skuNext.generalAgencyPrice
            Integer labelPrice = skuNext.labelPrice
            Integer leaguePrice = skuNext.leaguePrice
            Integer regionalAgencyPrice = skuNext.regionalAgencyPrice
            Integer salePrice = skuNext.salePrice
            Integer storage = skuNext.storage
            if (!generalAgencyPrice) {
                return new UniResp<String>(status: 10024, data: "请输入总代价")
            }
            if (!labelPrice) {
                return new UniResp<String>(status: 10024, data: "请输入吊牌价")
            }
            if (!leaguePrice) {
                return new UniResp<String>(status: 10024, data: "请输入加盟价")
            }
            if (!regionalAgencyPrice) {
                return new UniResp<String>(status: 10024, data: "请输入市代价")
            }
            if (!salePrice) {
                return new UniResp<String>(status: 10024, data: "请输入销售价")
            }
            if (!storage) {
                return new UniResp<String>(status: 10024, data: "请输入库存")
            }
        }

        if (!itemSaveReq.imgs || itemSaveReq.imgs.size() == 0) {
            return new UniResp<String>(status: 10024, data: "请至少上传一张图片")
        }

        if (!itemSaveReq.status) {
            return new UniResp<String>(status: 10024, data: "请选择状态")
        }
        Boolean itemSkuStatus = false
        for (SkuMiniInfo sku : itemSaveReq.skuList) {
            if (sku.status == "false") {
                itemSkuStatus = true
            }
        }
        if (itemSaveReq.status == ItemStatusEnum.NORMAL && !itemSkuStatus) {
            return new UniResp<String>(status: 10024, data: "SKU全部禁用不能为正常状态")
        }


        Item item = null;
        Map<String, List<String>> ids = null
        if (id) {
            item = itemRepo.findOne(id)
            if (!item) {
                return new UniResp<String>(status: 10024, data: "商品不存在")
            } else {
                ids = itemService.saveItem(item, itemSaveReq)
            }
        }
        if (ids.size() > 0) {
            ids.get("itemId").each {
                SyncEvent syncEvent = new SyncEvent()
                syncEvent.itemId = it
                eventPublisher.publish(syncEvent);
            }

            ids.get("skuStoreIds").each {
                net.kingsilk.qh.agency.msg.api.search.esSkuStore.sync.SyncEvent syncEvent = new net.kingsilk.qh.agency.msg.api.search.esSkuStore.sync.SyncEvent()
                syncEvent.skuStoreId = it
                eventPublisher.publish(syncEvent);
            }
        }
        return new UniResp<String>(status: 200, data: item.id)
    }

    @Override
    UniResp<ItemInfoModel> detail(
            String brandAppId,
            String id,
            String type) {
        Item item = itemRepo.findOne(id);
        Assert.notNull(type, "类型不能为空！")
        def resp = itemConvert.convertItemInfo(item)
        Iterable<Sku> skus = skuRepo.findAll(
                Expressions.allOf(
                        QSku.sku.item.eq(item),
                        QSku.sku.status.eq("false")
                ))

        skus.each { Sku it ->
            SkuInfoModel sku = itemConvert.skuConvert(it, type)
            resp.skus.add(sku)
        }
        return new UniResp<ItemInfoModel>(status: 200, data: resp);
    }

    @Override
    UniResp<ItemInfoResp> info(
            String brandAppId,
            String id
    ) {
        Item item = itemRepo.findOne(id)
        if (!item) {
            return new UniResp<ItemInfoResp>(status: 10024, message: "商品不存在", data: null)
        }

        Iterable<Sku> skus = skuRepo.findAll(
                Expressions.allOf(
                        QSku.sku.item.eq(item),
                        QSku.sku.deleted.in([null, false])
                )

        )
        ItemInfoResp itemInfoResp = new ItemInfoResp()
        itemInfoResp.id = item.id
        itemInfoResp.detail = item.detail
        itemInfoResp.imgs = item.imgs
        itemInfoResp.tags = item.tags
        itemInfoResp.code = item.code
        itemInfoResp.title = item.title
        itemInfoResp.desp = item.desp
        itemInfoResp.status = item.status
        for (Item.UsedItemProp itemProp : item.props) {
            ItemInfoResp.ItemProps prop = new ItemInfoResp.ItemProps()
            prop.id = itemProp.id
            prop.itemProp = itemConvert.itemPropConvert(itemProp.itemProp)
            prop.itemPropValueList = itemConvert.itemPropValueListConvert(
                    itemPropValueRepo.findAll(
                            Expressions.allOf(
                                    QItemPropValue.itemPropValue.itemProp.eq(itemProp.itemProp)
                            )
                    ).toSet()
            )
            prop.itemPropValue = itemConvert.itemPropValueConvert(itemProp.itemPropValue)
            itemInfoResp.props.add(prop)
        }
        for (Item.SpecDef spec : item.specs) {
            ItemInfoResp.SpecDef sdef = new ItemInfoResp.SpecDef()
            sdef.id = spec.id
            sdef.itemProp = itemConvert.itemPropConvert(spec.itemProp)
            sdef.itemPropValueList = itemConvert.itemPropValueListConvert(
                    itemPropValueRepo.findAll(
                            Expressions.allOf(
                                    QItemPropValue.itemPropValue.itemProp.eq(spec.itemProp)
                            )
                    ).toSet()
            )

            sdef.itemPropValue = itemConvert.itemPropValueListConvert(spec.itemPropValues)
            itemInfoResp.specs.add(sdef)
        }
        for (Sku sku : skus) {
            SkuMiniInfo itemSku = new SkuMiniInfo()
            itemSku.id = sku.id
            itemSku.generalAgencyPrice = (sku.tagPrices.find {
                it.tag == PartnerTypeEnum.GENERAL_AGENCY
            }.price).intValue()
            itemSku.regionalAgencyPrice = (sku.tagPrices.find {
                it.tag == PartnerTypeEnum.REGIONAL_AGENCY
            }.price).intValue()
            itemSku.leaguePrice = (sku.tagPrices.find {
                it.tag == PartnerTypeEnum.LEAGUE
            }.price).intValue()
            Iterator iterator = sku.imgs.iterator()
            itemSku.imgs = iterator.hasNext() ? iterator.next() : ""
            itemSku.code = sku.code
            itemSku.labelPrice = (sku.labelPrice).intValue()
            itemSku.salePrice = (sku.salePrice).intValue()
            SkuStore skuStore = skuStoreRepo.findBySku(sku)
            itemSku.storage = skuStore ? skuStore.num : 0
            itemSku.status = sku.status
            for (Sku.Spec spec : sku.specs) {
                SkuMiniInfo.SkuSpec skuSpec = new SkuMiniInfo.SkuSpec()
                skuSpec.id = spec.id
                skuSpec.itemProp = itemConvert.itemPropConvert(spec.itemProp)
//                skuSpec.itemPropValueList=itemPropValueRepo.findAll(
//                        Expressions.allOf(
//                                QItemPropValue.itemPropValue.itemProp.eq(spec.itemProp)
//                        )
//                ).toSet()
                skuSpec.itemPropValue = itemConvert.itemPropValueConvert(spec.itemPropValue)
                itemSku.specList.add(skuSpec)
            }
            itemInfoResp.skuList.add(itemSku)
        }
        return new UniResp<ItemInfoResp>(status: 200, data: itemInfoResp)
    }

    @Override
    UniResp<UniPageResp<ItemMinInfo>> page(
            String brandAppId,
            ItemPageReq req) {
        PageRequest pageRequest = new PageRequest(req.page, req.size,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))
        Page<Item> itemList = itemRepo.findAll(
                Expressions.allOf(
                        req.getTitle() ? QItem.item.title.contains(req.getTitle()) : null,
                        req.getStatus() ? QItem.item.status.eq(ItemStatusEnum.valueOf(req.getStatus())) : null,
                        QItem.item.deleted.in([null, false]),
                        QItem.item.brandAppId.eq(brandAppId)
                ), pageRequest
        )


        UniResp<UniPageResp<ItemMinInfo>> infoPage = new UniResp<UniPageResp<ItemMinInfo>>()
        infoPage.status = 200
        infoPage.data = conversionService.convert(itemList, UniPageResp)
        itemList.content.each {
            infoPage.data.content.add(itemConvert.itemMinInfoConvert(it))
        }
        return infoPage
    }

    @Override
    UniResp<String> delete(
            String brandAppId,
            String id
    ) {
        Item item = itemRepo.findOne(
                Expressions.allOf(
                        QItem.item.id.eq(id),
                        QItem.item.deleted.in([null, false])
                )
        )
        Iterable<Sku> skuList = skuRepo.findAll(
                Expressions.allOf(
                        QSku.sku.item.eq(item),
                        QSku.sku.deleted.in([null, false])
                )
        )
        for (Sku sku : skuList) {
            sku.deleted = true
            skuRepo.save(sku)
        }
        item.deleted = true
        itemRepo.save(item)
        return new UniResp(status: 200, data: "删除成功");
    }

    @Override
    UniResp<String> changeStatus(
            String brandAppId,
            String id,
            String status) {
        Item item = itemRepo.findOne(
                Expressions.allOf(
                        QItem.item.id.eq(id),
                        QItem.item.deleted.in([null, false])
                )
        )
        item.status = ItemStatusEnum.valueOf(status)
        itemRepo.save(item)

        SyncEvent syncEvent = new SyncEvent()
        syncEvent.itemId = item.id
        eventPublisher.publish(syncEvent);


        return new UniResp(status: 200, data: "状态改变成功");
    }

}
