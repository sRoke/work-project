package net.kingsilk.qh.agency.server.resource.brandApp.partner.item

import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.api.UniPageResp
import net.kingsilk.qh.agency.api.UniResp
import net.kingsilk.qh.agency.api.brandApp.item.dto.ItemInfoModel
import net.kingsilk.qh.agency.api.brandApp.item.dto.ItemMinInfo
import net.kingsilk.qh.agency.api.brandApp.item.dto.ItemSearchReq
import net.kingsilk.qh.agency.api.brandApp.partner.item.ItemApi
import net.kingsilk.qh.agency.api.common.dto.SkuInfoModel
import net.kingsilk.qh.agency.core.ItemStatusEnum
import net.kingsilk.qh.agency.domain.Category
import net.kingsilk.qh.agency.domain.Item
import net.kingsilk.qh.agency.domain.PartnerStaff
import net.kingsilk.qh.agency.domain.QCategory
import net.kingsilk.qh.agency.domain.QSku
import net.kingsilk.qh.agency.domain.Sku
import net.kingsilk.qh.agency.es.domain.EsItem
import net.kingsilk.qh.agency.msg.impl.EventPublisherImpl
import net.kingsilk.qh.agency.repo.*
import net.kingsilk.qh.agency.server.resource.brandApp.item.convert.ItemConvert
import net.kingsilk.qh.agency.service.ItemService
import net.kingsilk.qh.agency.service.PartnerService
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

@Component(value = "item")
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

    @Autowired
    private PartnerService partnerService

    @Override
    UniResp<UniPageResp<ItemMinInfo>> searchItem(
            String brandAppId,
            String partnerId,
            ItemSearchReq req) {

//        List<String> itemIdList = new ArrayList<>()
//        if (req.type == "purchase") {
//            itemIdList = skuStoreRepo.findAll(
//                    Expressions.allOf(
//                            QSkuStore.skuStore.partner.id.eq(partnerStaff.partner.parent.id),
//                            QSkuStore.skuStore.brandAppId.eq(brandAppId)
//                    )
//            )*.sku.item.id.unique()
//        } else if (req.type == "refund") {
//            itemIdList = skuStoreRepo.findAll(
//                    Expressions.allOf(
//                            QSkuStore.skuStore.partner.id.eq(partnerStaff.partner.id),
//                            QSkuStore.skuStore.brandAppId.eq(brandAppId),
//                            QSkuStore.skuStore.num.gt(0)
//                    )
//            )*.sku.item.id.unique()
//        }
//
//        String keyWord = req.keyWord
//
//        String categoryId = req.getCategoryId();
//        List<Category> categories = new ArrayList<>()
//
//        if (categoryId) {
//            Category categorie = categoryRepo.findOne(categoryId);
//            if (categorie) {
//                List<Category> categorieList = categoryRepo.findAll(
//                        QCategory.category.parent.id.eq(categorie.id)
//                ).toList()
//                if (categorieList) {
//                    categories.addAll(categorieList)
//                }
//
//            }
//            categories.add(categorie)
//        }
//        PageRequest pageRequest = new PageRequest(req.page, req.size, Sort.Direction.DESC, "dateCreated")
//        /**
//         * 根据分类情况筛选出属于这些分类的商品
//         */
//        Page<Item> items = new PageImpl<>(new ArrayList<Item>(), pageRequest, 0)
//        if (itemIdList.size() > 0) {
//            items = itemRepo.findAll(
//                    Expressions.allOf(QItem.item.deleted.in([false, null]),
//                            keyWord ? QItem.item.title.like("%" + keyWord + "%") : null,
//                            QItem.item.status.eq(ItemStatusEnum.NORMAL),
//                            itemIdList != null ? dbUtil.opIn(QItem.item.id, itemIdList) : null,
//                            categories.size() > 0 ? QItem.item.tags.any().in(categories*.id) : null
//                    ), pageRequest
//            )
//        }

        /*

            ''

        *
        * */

        // 添加 must
//        RangeQueryBuilder storeRange = null;
//        if (minValue != null && maxValue != null) {
//            storeRange = rangeQuery("store");
//
//            if (minValue != null) {
//                storeRange.gt(minValue);
//            }
//            if (maxValue != null) {
//                storeRange.lt(maxValue);
//            }
//        }
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
        BoolQueryBuilder query = boolQuery()
        List<QueryBuilder> mustClauses = new ArrayList<>();
        if (StringUtils.hasText(req.keyWord)) {
            mustClauses.add(multiMatchQuery(req.keyWord, "title", "seq"))
        }
        mustClauses.add(matchQuery("brandAppId", brandAppId))
        mustClauses.add(matchQuery("deleted", false))
        mustClauses.add(matchQuery("statusCode", ItemStatusEnum.NORMAL.code))
        mustClauses.each {
            query.must(it)
        }
        BoolQueryBuilder queryBuilder = boolQuery()
        if (categories.size() > 0) {
            categories*.id.each {
                queryBuilder.should(termQuery("tags", it))
            }
        }

//        AbstractAggregationBuilder subAgg=topHits("top").addSort("dateCreated",SortOrder.DESC).setSize(1)

//        AbstractAggregationBuilder agg=cardinality("itemId")
//                .field("itemId")

        // 添加  should

//
//        AbstractAggregationBuilder aggs=terms("itemId")
//                .field("itemId")
//                .subAggregation(agg)
//        AbstractAggregationBuilder cardinality=terms("itemId")
//        .field("itemId")


        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(query)
                .withFilter(queryBuilder)
                .build()
                .setPageable(new PageRequest(req.page, req.size))
                .addSort(new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))
        Page<EsItem> esItems = elasticsearchTemplate.queryForPage(searchQuery, EsItem.class)

        println "-----------end------------" + new Date()
        // @formatter:off

        // @formatter:on

//                .withFilter(boolFilter().must(termFilter("id", documentId)))
//                .build();

        ////
//        List<Criteria> criteriaList = new ArrayList<>()
//        criteriaList.add(new Criteria("brandAppId").is(brandAppId))
//        criteriaList.add(new Criteria("deleted").is(false))
//        criteriaList.add(new Criteria("status").is(ItemStatusEnum.NORMAL.code))
//        if (req.type == "purchase") {
//            criteriaList.add(new Criteria("partnerId").is(partnerStaff.partner.parent.id))
//        } else if (req.type == "refund") {
//            criteriaList.add(new Criteria("partnerId").is(partnerStaff.partner.id))
//        }
//        criteriaList.add(new Criteria("num").greaterThan(0))
//        if (categories.size() > 0) {
//            criteriaList.add(new Criteria("tags").in(categories*.id))
//        }
//
//        List<Criteria> criterias = new ArrayList<>()
//        if (req.keyWord) {
//            criterias.add(new Criteria("title").contains(req.keyWord))
//            criterias.add(new Criteria("seq").contains(req.keyWord))
//        }
//        CriteriaQuery criteriaQuery = new CriteriaQuery(new Criteria().
//                and(new Criteria().criteriaChain.addAll(criteriaList) as Criteria).
//                or(new Criteria().criteriaChain.addAll(criterias) as Criteria))
//                .setPageable(new PageRequest(req.page, req.size))
//                .addSort(new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")));
//
//        Page<EsSkuStore> esSkuStores = elasticsearchTemplate.queryForPage(criteriaQuery,
//                EsSkuStore.class)


        UniResp<UniPageResp<ItemMinInfo>> uniResp = new UniResp<>()
        uniResp.status = 200
        uniResp.data = conversionService.convert(esItems, UniPageResp)

        esItems.content.each {
            EsItem esItem ->
                ItemMinInfo itemMinInfo = new ItemMinInfo()
                itemMinInfo.id = esItem.itemId
                itemMinInfo.brandAppId
                def partnerType = partnerStaff.partner.partnerTypeEnum.code
                itemMinInfo.skuMinSalePrice = esItem.skuMinSalePrice
                itemMinInfo.price = esItem.tagPrice.get(partnerType)
                itemMinInfo.partnerType = partnerType
                itemMinInfo.title = esItem.title
                itemMinInfo.desp = esItem.desp
                itemMinInfo.imgs = esItem.imgs[0]
                uniResp.data.content.add(itemMinInfo)

        }

        return uniResp
    }

    @Override
    UniResp<ItemInfoModel> detail(
            String brandAppId,
            String partnerId,
            String id,
            String type) {
        partnerService.check()
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
}
