package net.kingsilk.qh.agency.admin.controller.item

import com.querydsl.core.types.dsl.Expressions
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import net.kingsilk.qh.agency.admin.api.UniResp
import net.kingsilk.qh.agency.core.ItemStatusEnum
import net.kingsilk.qh.agency.core.PartnerTypeEnum
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.repo.*
import net.kingsilk.qh.agency.service.CommonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

/**
 * Created by tpx on 17-3-14.
 *
 *
 *
 * 新增：
 * 1. get /api/category/list  获取 “分类标签列表”
 * 1. TODO  上传图片
 * 1. 查询商品熟悉个 /api/itemProp/search
 * 1. POST /api/sku/add  注意：批量
 */
@RestController()
@RequestMapping("/api/item")
@Api( // 用在类上，用于设置默认值
        tags = "item",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        protocols = "http,https",
        description = "商品相关API"
)
class ItemController {

    @Autowired
    CommonService commonService

    @Autowired
    ItemRepo itemRepo
    @Autowired
    ItemPropRepo itemPropRepo

    @Autowired
    SkuRepo skuRepo

    @Autowired
    ItemPropValueRepo itemPropValueRepo

    @Autowired
    CategoryRepo categoryRepo

    @RequestMapping(path = "/save",
            method = RequestMethod.POST,
            //consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "保存商品信息",
            nickname = "保存商品信息",
            notes = "保存商品信息"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('ITEM_C','ITEM_U')")
    UniResp<String> save(@RequestBody ItemSaveReq itemSaveReq) {

        Item item = null;
        if (itemSaveReq.id) {
            item = itemRepo.findOne(itemSaveReq.id)
            if (!item) {
                return new UniResp<String>(status: 10024, data: "商品不存在")
            }
        } else {
            item = new Item()
            item.dateCreated = new Date()
        }
        item.lastModifiedDate = new Date()
        if (!itemSaveReq.props || itemSaveReq.props.size() == 0) {
            return new UniResp<String>(status: 10024, data: "请至少选择一种属性")
        }

        if (!itemSaveReq.specs || itemSaveReq.specs.size() == 0) {
            return new UniResp<String>(status: 10024, data: "请至少选择一种规格")
        }

        if (!itemSaveReq.imgs || itemSaveReq.imgs.size() == 0) {
            return new UniResp<String>(status: 10024, data: "请至少上传一张图片")
        }

        if (!itemSaveReq.status) {
            return new UniResp<String>(status: 10024, data: "请选择状态")
        }
        Boolean itemSkuStatus = false
        for (ItemSaveReq.ItemSku sku : itemSaveReq.skuList) {
            if (sku.status == "false") {
                itemSkuStatus = true
            }
        }
        if (itemSaveReq.status == ItemStatusEnum.NORMAL && !itemSkuStatus) {
            return new UniResp<String>(status: 10024, data: "SKU全部禁用不能为正常状态")
        }

        item.props.clear()
        item.specs.clear()
        itemSaveReq.props.each { ItemSaveReq.ItemProps itemProp ->

            ItemProp itemProp1 = itemPropRepo.findOne(itemProp.getItemPropId())
            ItemPropValue propValue = itemPropValueRepo.findOne(itemProp.getItemPropValueId())
            Item.UsedItemProp itemUsedItemProp = new Item.UsedItemProp();
            itemUsedItemProp.itemProp = itemProp1
            itemUsedItemProp.itemPropValue = propValue
            itemUsedItemProp.id = itemProp.id
            item.props.add(itemUsedItemProp)
        }

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
        item.code = itemSaveReq.code
        item.title = itemSaveReq.title
        item.detail = itemSaveReq.detail
        item.desp = itemSaveReq.desp
        item.tags = itemSaveReq.tags
        item.status = itemSaveReq.status
        item.itemUnit = itemSaveReq.itemUnit
        item.imgs = itemSaveReq.imgs
        itemRepo.save(item)

//        Iterable<Sku> deleteSkus = skuRepo.findAll(
//                Expressions.allOf(
//                        QSku.sku.item.eq(item)
//                )
//        )
//        for (Sku deleteSku : deleteSkus) {
//            skuRepo.delete(deleteSku)
//        }

        itemSaveReq.skuList.each {

            Sku sku = skuRepo.findOne(it.id)
            if (!sku) {
                sku = new Sku();
                sku.dateCreated = new Date();
            }

            sku.lastModifiedDate = new Date()
            sku.item = item
            sku.specs.clear()
            it.specList.each { ItemSaveReq.ItemSku.SkuSpec itemSpecDef ->
                ItemProp itemProp = itemPropRepo.findOne(itemSpecDef.itemPropId)
                ItemPropValue itemPropValue = itemPropValueRepo.findOne(itemSpecDef.itemPropValueId)
                Sku.Spec spec = new Sku.Spec();
                spec.itemProp = itemProp;
                spec.itemPropValue = itemPropValue
                spec.id = itemSpecDef.id;
                sku.specs.add(spec);
            }
            sku.price = it.price * 100
            sku.storage = it.storage
            sku.status = it.status
            sku.tagPrices.clear()
            if (it.leaguePrice) {
                Sku.TagPrice tagPrice = new Sku.TagPrice()
                tagPrice.tag = PartnerTypeEnum.LEAGUE
                tagPrice.price = it.leaguePrice * 100
                sku.tagPrices.add(tagPrice)
            }
            if (it.agencyPrice) {
                Sku.TagPrice tagPrice = new Sku.TagPrice()
                tagPrice.tag = PartnerTypeEnum.AGENCY
                tagPrice.price = it.agencyPrice * 100
                sku.tagPrices.add(tagPrice)
            }
            skuRepo.save(sku)
        }
        return new UniResp<String>(status: 200, data: item.getId())
    }

    @RequestMapping(path = "/info",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "商品信息详情",
            nickname = "商品信息详情",
            notes = "商品信息详情"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('ITEM_R')")
    UniResp<ItemInfoResp> info(String id) {
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
            prop.itemProp = itemProp.itemProp
            prop.itemPropValueList = itemPropValueRepo.findAll(
                    Expressions.allOf(
                            QItemPropValue.itemPropValue.itemProp.eq(prop.itemProp)
                    )
            ).toSet()
            prop.itemPropValue = itemProp.itemPropValue
            itemInfoResp.props.add(prop)
        }
        for (Item.SpecDef spec : item.specs) {
            ItemInfoResp.SpecDef sdef = new ItemInfoResp.SpecDef()
            sdef.id = spec.id
            sdef.itemProp = spec.itemProp
            sdef.itemPropValueList = itemPropValueRepo.findAll(
                    Expressions.allOf(
                            QItemPropValue.itemPropValue.itemProp.eq(spec.itemProp)
                    )
            ).toSet()
            sdef.itemPropValue = spec.itemPropValues
            itemInfoResp.specs.add(sdef)
        }
        for (Sku sku : skus) {
            ItemInfoResp.ItemSku itemSku = new ItemInfoResp.ItemSku()
            itemSku.id = sku.id
            if (sku.tagPrices[0].tag == PartnerTypeEnum.AGENCY) {
                itemSku.agencyPrice = (sku.tagPrices[0].price / 100).intValue()
                itemSku.leaguePrice = (sku.tagPrices[1].price / 100).intValue()
            } else {
                itemSku.agencyPrice = (sku.tagPrices[1].price / 100).intValue()
                itemSku.leaguePrice = (sku.tagPrices[0].price / 100).intValue()
            }
            itemSku.price = (sku.price / 100).intValue()
            itemSku.storage = sku.storage
            itemSku.status = sku.status
            for (Sku.Spec spec : sku.specs) {
                ItemInfoResp.ItemSku.SkuSpec skuSpec = new ItemInfoResp.ItemSku.SkuSpec()
                skuSpec.id = spec.id
                skuSpec.itemProp = spec.itemProp
//                skuSpec.itemPropValueList=itemPropValueRepo.findAll(
//                        Expressions.allOf(
//                                QItemPropValue.itemPropValue.itemProp.eq(spec.itemProp)
//                        )
//                ).toSet()
                skuSpec.itemPropValue = spec.itemPropValue
                itemSku.specList.add(skuSpec)
            }
            itemInfoResp.skuList.add(itemSku)
        }
        return new UniResp<ItemInfoResp>(status: 200, data: itemInfoResp)
    }

    @RequestMapping(path = "/page",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "商品分页信息详情",
            nickname = "商品分页信息详情",
            notes = "商品分页信息详情"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('ITEM_R')")
    UniResp<ItemPageResp> page(ItemPageReq itemPageReq) {

        PageRequest pageRequest = new PageRequest(itemPageReq.curPage, itemPageReq.pageSize,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))
        Page<Item> itemList = itemRepo.findAll(
                Expressions.allOf(
                        itemPageReq.getTitle() ? QItem.item.title.contains(itemPageReq.getTitle()) : null,
                        itemPageReq.getStatus() ? QItem.item.status.eq(ItemStatusEnum.valueOf(itemPageReq.getStatus())) : null,
                        QItem.item.deleted.in([null, false])
                ), pageRequest
        )
        Page<ItemPageResp.ItemMinInfo> infoPage = itemList.map({ Item item ->
            ItemPageResp.ItemMinInfo info = new ItemPageResp.ItemMinInfo();
            info.id = item.id
            info.imgs = item.imgs[0]
            info.title = item.title

            item.props.each {
                if (it.itemProp && it.itemProp.name && "品牌" == it.itemProp.name) {
                    info.brand = it.itemPropValue.name
                }
            }
            if (item.tags) {
                Iterable<Category> categoriesList = categoryRepo.findAll(
                        Expressions.allOf(
                                QCategory.category.id.in(item.tags),
//                            QCategory.category.deleted.in(null,false)
                        )
                )
                for (Category category : categoriesList) {
                    info.categoryName.add(category.name)
                }
            }

//            info.categoryName = item.tags
            info.statusCode = item.status.code
            info.statusDesp = item.status.desp
            return info
        });
        ItemPageResp resp = new ItemPageResp()
        resp.recList = infoPage
        return new UniResp(status: 200, data: resp);
    }


    @RequestMapping(path = "/delete",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "删除商品",
            nickname = "删除商品",
            notes = "删除商品"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('ITEM_D')")
    UniResp<ItemPageResp> delete(String id) {


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

    @RequestMapping(path = "/changeStatus",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "商品状态更改",
            nickname = "商品状态更改",
            notes = "商品状态更改"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('ITEM_C','ITEM_U')")
    UniResp<ItemPageResp> changeStatus(String id, String status) {


        Item item = itemRepo.findOne(
                Expressions.allOf(
                        QItem.item.id.eq(id),
                        QItem.item.deleted.in([null, false])
                )
        )
        item.status = ItemStatusEnum.valueOf(status)
        itemRepo.save(item)
        return new UniResp(status: 200, data: "状态改变成功");
    }

//    @RequestMapping(path = "/download",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "下载商品",
//            nickname = "下载商品",
//            notes = "下载商品"
//    )
//    @ResponseBody
//    UniResp<ItemPageResp> download(String id, String status) {
//
//
//        Item item = itemRepo.findOne(
//                Expressions.allOf(
//                        QItem.item.id.eq(id),
//                        QItem.item.deleted.in([null, false])
//                )
//        )
//        item.status = ItemStatusEnum.valueOf(status)
//        itemRepo.save(item)
//        return new UniResp(status: 200, data: "状态改变成功");
//    }

//    @RequestMapping(path = "/saveSku",
//            method = RequestMethod.POST,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "保存Sku商品信息详情",
//            nickname = "保存Sku商品信息详情",
//            notes = "保存Sku商品信息详情"
//    )
//    @ResponseBody
//    UniResp<ItemSaveSku> saveSku(@RequestBody List<ItemSaveSku> itemSaveSkus) {
//        itemSaveSkus.each {
//            Item item = itemRepo.findOne("58e8dad38f3b9145e0f033fc") //TODO
//            if (!item) {
//                return new UniResp<String>(status: 10024, message: "商品不存在", data: null)
//            }
//            Sku sku = skuRepo.findOne(it.getId())
//            if (!sku) {
//                sku = new Sku();
//                sku.dateCreated = new Date();
//            }
//
//            it.specList.each { ItemSaveSku.SkuSpec itemSpecDef ->
//                ItemProp itemProp = itemPropRepo.findOne(itemSpecDef.itemPropId)
//                ItemPropValue itemPropValue = itemPropValueRepo.findOne(itemSpecDef.itemPropValueId)
//                Sku.Spec spec = new Sku.Spec();
//                spec.itemProp = itemProp;
//                spec.itemPropValue = itemPropValue
//                spec.id = itemSpecDef.id;
//                sku.specs.add(spec);
//
//            }
//            sku.price=it.price
//            sku.storage=it.storage
//            sku.tagPrices.price.add(it.leaguePrice)
//            sku.tagPrices.price.add(it.agencyPrice)
//            skuRepo.save(sku)
//        }
//        return new UniResp<String>(status: 200, data: "保存成功")
//    }

//    @RequestMapping(path = "/skuInfo",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "Sku商品信息详情",
//            nickname = "Sku商品信息详情",
//            notes = "Sku商品信息详情"
//    )
//    @ResponseBody
//    UniResp<List<ItemSaveSku>> skuInfo(String itemId) {
//        Item item = itemRepo.findOne(itemId)
//        if (!item) {
//            return new UniResp<String>(status: 10024, message: "商品不存在", data: null)
//        }
//        List<ItemSaveReq> itemSaveReqList= new ArrayList<ItemSaveSku>()
//        Criteria criteria = new Criteria()
//        criteria.andOperator(
//                Criteria.where("delete").in([null, false]),
//                Criteria.where("item").is(item.getId())
//        )
//        Query query = new Query(criteria)
//        List<Sku> skus = mongoTemplate.find(query, Sku)
//        skus.each {
//            itemSaveSkusList.add(ItemSaveSku.convertSkuToResp(item, it));
//        }
//
//
//        return new UniResp<String>(status: 200, data: itemSaveSkusList)
//    }


}
