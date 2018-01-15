package net.kingsilk.qh.agency.server.resource.brandApp.partner.skuStore

import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.api.UniPageResp
import net.kingsilk.qh.agency.api.UniResp
import net.kingsilk.qh.agency.api.brandApp.item.dto.ItemMinInfo
import net.kingsilk.qh.agency.api.brandApp.item.dto.ItemSearchReq
import net.kingsilk.qh.agency.api.brandApp.partner.skuStore.SkuStoreApi
import net.kingsilk.qh.agency.api.brandApp.partner.skuStore.dto.SkuStorePageReq
import net.kingsilk.qh.agency.api.brandApp.partner.skuStore.dto.SkuStorePageResp
import net.kingsilk.qh.agency.api.common.dto.SkuInfoModel
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.es.domain.EsSkuStore
import net.kingsilk.qh.agency.es.repo.EsSkuStoreRepository
import net.kingsilk.qh.agency.repo.*
import net.kingsilk.qh.agency.server.resource.brandApp.order.convert.SkuConvert
import net.kingsilk.qh.agency.service.PartnerService
import net.kingsilk.qh.agency.service.PartnerStaffService
import net.kingsilk.qh.agency.service.SecService
import net.kingsilk.qh.agency.util.DbUtil
import org.bson.types.ObjectId
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.MatchQueryBuilder
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
import org.springframework.util.Assert
import org.springframework.util.StringUtils

import static org.elasticsearch.index.query.QueryBuilders.*

class SkuStoreResource implements SkuStoreApi {
    @Autowired
    SecService secService
    @Autowired
    StaffRepo staffRepo
    @Autowired
    PartnerRepo partnerRepo
    @Autowired
    SkuStoreRepo skuStoreRepo
    @Autowired
    @Qualifier(value = "mvcConversionService")
    ConversionService conversionService

    @Autowired
    SkuStoreConvert skuStoreConvert

    @Autowired
    ItemRepo itemRepo

    @Autowired
    CategoryRepo categoryRepo

    @Autowired
    SkuRepo skuRepo

    @Autowired
    PartnerStaffService partnerStaffService

    @Autowired
    SkuConvert skuConvert

    @Autowired
    DbUtil dbUtil

    @Autowired
    EsSkuStoreRepository esSkuStoreRepository

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate

    @Autowired
    private PartnerService partnerService

    @Override
    UniResp<SkuStorePageResp> info(String brandAppId, String partnerId, String skuStoreId) {
        partnerService.check()
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()

        SkuStore skuStore = skuStoreRepo.findOne(
                Expressions.allOf(
                        QSkuStore.skuStore.deleted.in([false, null]),
                        QSkuStore.skuStore.id.eq(skuStoreId)
                )
        )

        Assert.notNull(skuStore, "没有该商品的库存信息")
        UniResp<SkuStorePageResp> uniResp = new UniResp<SkuStorePageResp>()
        SkuStorePageResp infoResp = new SkuStorePageResp()

        infoResp.skuInfo = skuConvert.skuInfoConvert(skuStore.sku, partnerStaff.partner.partnerTypeEnum.code)
        infoResp.setNum(skuStore.num)
        infoResp.setSalesNum(skuStore.salesVolume)
        infoResp.setDateCreated(skuStore.getDateCreated())
        uniResp.setData(infoResp)
        uniResp.setStatus(200)
        return uniResp
    }

    @Override
    UniResp<UniPageResp<SkuStorePageResp>> page(String brandAppId, String partnerId, SkuStorePageReq req) {
        partnerService.check()
        PageRequest pageRequest = new PageRequest(req.page, req.size, Sort.Direction.DESC, "dateCreated")
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()

        String categoryId = req.getCategoryId()
        List<String> categories = new ArrayList<>()
        List<String> items = new ArrayList<>()
        List<String> skuList = new ArrayList<>()

        if (categoryId) {
            Category categorie = categoryRepo.findOne(categoryId)
            if (categorie) {
                List<String> categorieList = categoryRepo.findAll(
                        QCategory.category.parent.id.eq(categorie.id)
                )*.id.toList()
                if (categorieList) {
                    categories.addAll(categorieList)
                }

            }
            categories.add(categorie.id)

            if (categories) {
                items = itemRepo.findAll(
                        Expressions.allOf(
                                QItem.item.deleted.in(false),
                                dbUtil.opIn(QItem.item.tags.any(), categories))
                )*.id
            }

            if (items) {
                skuList = skuRepo.findAll(
                        Expressions.allOf(
                                QSku.sku.deleted.in(false),
                                dbUtil.opIn(QSku.sku.item.id, items)
                        )

                )*.id
            }
            if (!skuList) {
                skuList.add(new ObjectId().toString())
            }
        }

        List<SkuStore> skuStores = new ArrayList<>()
        if (req.keyWord) {
            skuStores = skuStoreRepo.findAll(
                    Expressions.allOf(
                            QSkuStore.skuStore.deleted.in([false, null]),
                            QSkuStore.skuStore.partner.id.eq(partnerStaff.partner.id),
                            QSkuStore.skuStore.brandAppId.eq(brandAppId)
                    )
            ).asList()
        }
        List<SkuStore> skuStoreList = skuStores.findAll {
            it.sku.item.title.contains(req.keyWord)
        }
        Page<SkuStore> page = skuStoreRepo.findAll(
                Expressions.allOf(
                        QSkuStore.skuStore.deleted.in([false, null]),
                        QSkuStore.skuStore.partner.id.eq(partnerStaff.partner.id),
                        QSkuStore.skuStore.brandAppId.eq(brandAppId),
                        skuStores.size() > 0 ? QSkuStore.skuStore.id.in(skuStoreList*.id) : null,
                        dbUtil.opIn(QSkuStore.skuStore.sku.id, skuList)
//                        categories.size() > 0 ? QSkuStore.skuStore.sku.item.tags.any().in(categories*.id) : null
                ), pageRequest)
        UniResp<UniPageResp<SkuStorePageResp>> uniResp = new UniResp<>()
        uniResp.data = conversionService.convert(page, UniPageResp)

        page.content.each {
            SkuStore skuStore ->
                if (skuStore.num > 0) {
                    uniResp.data.content.add(skuStoreConvert.skuPageRespConvert(skuStore))
                }
        }
        uniResp.status = 200
        return uniResp
    }

    @Override
    UniResp<UniPageResp<ItemMinInfo>> searchSkuStore(
            String brandAppId,
            String partnerId,
            ItemSearchReq req) {
        partnerService.check()
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        println "-----------start------------" + new Date()
        String categoryId = req.getCategoryId();
        List<Category> categories = new ArrayList<>()

        if (categoryId) {
            Category categorie = categoryRepo.findOne(categoryId);
            if (categorie) {
                List<Category> categorieList = categoryRepo.findAll(
                        QCategory.category.parent.id.eq(categorie.id)
                ).toList()
                if (categorieList) {
                    categories.addAll(categorieList)
                }

            }
            categories.add(categorie)
        }
        MatchQueryBuilder matchQueryBuilder = null
        BoolQueryBuilder query = boolQuery()
        if (req.type == "purchase") {
            matchQueryBuilder = matchQuery("partnerId", partnerStaff.partner.parent.id)
        } else if (req.type == "refund") {
            matchQueryBuilder = matchQuery("partnerId", partnerStaff.partner.id)
        }

        List<QueryBuilder> mustClauses = new ArrayList<>();
        if (StringUtils.hasText(req.keyWord)) {
            mustClauses.add(multiMatchQuery(req.keyWord, "title", "seq"))
        }
        mustClauses.add(matchQuery("brandAppId", brandAppId))
        mustClauses.add(matchQuery("deleted", false))
        mustClauses.add(rangeQuery("num").gt(0))
        if (matchQueryBuilder) {
            mustClauses.add(matchQueryBuilder)
        }
        mustClauses.each {
            query.must(it)
        }

//        Arrays.asList(
//                matchQueryBuilder ? matchQueryBuilder : null,
//                StringUtils.hasText(brandAppId) ? : null,
//
//        )
//                .find { it != null }
//                .each { query.must(it) }

        BoolQueryBuilder queryBuilder = boolQuery()
        if (categories.size() > 0) {
            categories*.id.each {
                queryBuilder.should(termQuery("tags", it))
            }
        }

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(query)
                .withFilter(queryBuilder)
                .build()
                .setPageable(new PageRequest(req.page, req.size))
                .addSort(new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))
        Page<EsSkuStore> esSkuStores = elasticsearchTemplate.queryForPage(searchQuery, EsSkuStore.class)

        println "-----------end------------" + new Date()

        UniResp<UniPageResp<ItemMinInfo>> uniResp = new UniResp<>()
        uniResp.status = 200
        uniResp.data = conversionService.convert(esSkuStores, UniPageResp)

        esSkuStores.content.each {
            EsSkuStore esSkuStore ->
                ItemMinInfo itemMinInfo = new ItemMinInfo()
                itemMinInfo.id = esSkuStore.skuId
                itemMinInfo.brandAppId
                def partnerType = partnerStaff.partner.partnerTypeEnum.code
                itemMinInfo.skuMinSalePrice = esSkuStore.skuMinSalePrice
                itemMinInfo.price = esSkuStore.tagPrices.get(partnerType)
                itemMinInfo.partnerType = partnerType
                itemMinInfo.title = esSkuStore.title
                itemMinInfo.salePrice = esSkuStore.salePrice
                itemMinInfo.desp = esSkuStore.desp
                itemMinInfo.specs = esSkuStore.specs
                itemMinInfo.imgs = esSkuStore.imgs[0]
                itemMinInfo.num = esSkuStore.num
                uniResp.data.content.add(itemMinInfo)

        }

        return uniResp
    }

    @Override
    UniResp<SkuInfoModel> skuDetail(
            String brandAppId,
            String partnerId,
            String skuId,
            String code) {
        partnerService.check()
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()

        Boolean isAdmin = false
        if (partnerStaff == null) {
            String curUserId = secService.curUserId()
            if (!StringUtils.isEmpty(curUserId)) {
                Staff staff =
                        staffRepo.
                                findOneByUserIdAndDisabledAndDeletedAndBrandAppId(curUserId, false, false, brandAppId)
                Assert.notNull(staff, "请先登录")
                isAdmin = true
            }
        }


        Sku sku = skuRepo.findOne(
                Expressions.allOf(
                        QSku.sku.deleted.in([false]),
                        skuId != null ? QSku.sku.id.eq(skuId) : null,
                        code != null ? QSku.sku.code.eq(code) : null
                )
        )
        Assert.notNull(sku, "没有此sku的信息")
        if (skuId == null || skuId == "") {
            skuId = sku.getId()
        }
        SkuStore skuStore = null
        if (!isAdmin) {
            skuStore = skuStoreRepo.findOne(
                    Expressions.allOf(
                            QSkuStore.skuStore.partner.id.eq(partnerStaff.getPartner().getId()),
                            QSkuStore.skuStore.sku.id.eq(skuId),
                            QSkuStore.skuStore.deleted.in([false])
                    )
            )
            Assert.notNull(skuStore, "库存中没有此商品")
        }
        UniResp<SkuInfoModel> resp = new UniResp<>()
        String type = isAdmin ? "BRAND_COM" : partnerStaff.partner.partnerTypeEnum.code
        SkuInfoModel skuInfoModel = skuConvert.skuInfoConvert(sku, type)
        skuInfoModel.storage = skuStore ? skuStore.num : 0
        resp.status = 200
        resp.setData(skuInfoModel)
        return resp
    }
}
