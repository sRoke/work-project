package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.Item;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.item.dto.ItemInfoResp;
import net.kingsilk.qh.shop.api.brandApp.shop.item.dto.ItemMinInfo;
import net.kingsilk.qh.shop.api.brandApp.shop.item.dto.ItemPageReq;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.item.ItemApi;
import net.kingsilk.qh.shop.core.ItemStatusEnum;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.es.domain.EsItem;
import net.kingsilk.qh.shop.repo.*;
import net.kingsilk.qh.shop.service.service.SecService;
import net.kingsilk.qh.shop.service.util.ParamUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Component(value = "mallItemResource")
public class ItemResource implements ItemApi {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    @Qualifier(value = "mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private SkuRepo skuRepo;

    @Autowired
    private RecentSearchRepo recentSearchRepo;

    @Autowired
    private SecService secService;

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public UniResp<ItemInfoResp> info(
            String brandAppId,
            String shopId,
            String id) {
        Item item = itemRepo.findOne(id);

        ItemInfoResp itemInfoResp = conversionService.convert(item, ItemInfoResp.class);
        UniResp<ItemInfoResp> resp = new UniResp<>();
        resp.setStatus(200);
        resp.setData(itemInfoResp);
        return resp;
    }

    @Override
    public UniResp<UniPageResp<ItemMinInfo>> page(
            String brandAppId,
            String shopId,
            ItemPageReq itemPageReq) {


        Sort s = ParamUtils.toSort(itemPageReq.getSort());
        PageRequest pageRequest = new PageRequest(itemPageReq.getPage(), itemPageReq.getSize(), s);

        String userId = secService.curUserId();

        if (userId != null) {
            Member member = memberRepo.findOne(
                    Expressions.allOf(
                            QMember.member.brandAppId.eq(brandAppId),
                            QMember.member.deleted.ne(true),
                            QMember.member.userId.eq(userId)
                    )
            );
            Page<RecentSearch> recentSearchPage = recentSearchRepo.findAll(
                    Expressions.allOf(
                            QRecentSearch.recentSearch.deleted.ne(true),
                            QRecentSearch.recentSearch.brandAppId.eq(brandAppId),
                            QRecentSearch.recentSearch.memberId.eq(member.getId())
                    ), new PageRequest(0, 20, new Sort(new Sort.Order(Sort.Direction.ASC, "dateCreated")))
            );
            if (recentSearchPage.getContent().size() >= 20) {
                recentSearchRepo.delete(recentSearchPage.getContent().get(0));
            }
            if (!StringUtils.isEmpty(itemPageReq.getKeyWords())) {
                RecentSearch recentSearch = new RecentSearch();
                recentSearch.setBrandAppId(brandAppId);
                recentSearch.setMemberId(member.getId());
                recentSearch.setShopId(shopId);
                recentSearch.setKeyWord(itemPageReq.getKeyWords());
                recentSearchRepo.save(recentSearch);
            }
        }
        Page<Item> itemPage = itemRepo.findAll(
                Expressions.allOf(
                        QItem.item.brandAppId.eq(brandAppId),
                        QItem.item.status.eq(ItemStatusEnum.NORMAL),
                        QItem.item.deleted.ne(true),
                        !StringUtils.isEmpty(itemPageReq.getKeyWords()) ?
                                QItem.item.title.like("%" + itemPageReq.getKeyWords() + "%") : null,
                        !StringUtils.isEmpty(itemPageReq.getCategory()) ?
                                QItem.item.categorys.contains(itemPageReq.getCategory()) : null
                ), pageRequest
        );

        UniResp<UniPageResp<ItemMinInfo>> infoPage = new UniResp<>();
        infoPage.setData(conversionService.convert(itemPage, UniPageResp.class));
        itemPage.getContent().forEach(
                item -> {
                    ItemMinInfo itemMinInfo = conversionService.convert(item, ItemMinInfo.class);
                    itemMinInfo.setCategorys(item.getCategorys());
                    infoPage.getData().getContent().add(itemMinInfo);
                }
        );

        infoPage.setStatus(200);
        return infoPage;
    }

    @Override
    @PreAuthorize("permitAll()")
    public UniResp<UniPageResp<ItemMinInfo>> search(
            String brandAppId,
            String shopId,
            ItemPageReq itemPageReq) {


        String categoryId = itemPageReq.getCategory();
        List<String> categoryList = new ArrayList<>();
        Sort sort = ParamUtils.toSort(itemPageReq.getSort());
        if (categoryId != null) {
            Category categorie = categoryRepo.findOne(categoryId);
            if (categorie != null) {
                Iterator<Category> categories = categoryRepo.findAll(
                        QCategory.category.parentId.eq(categorie.getId())
                ).iterator();

                List<Category> categorieList = IteratorUtils.toList(categories);
                if (categorieList != null) {
                    categorieList.forEach(
                            category -> categoryList.add(category.getId())
                    );
                }
                categoryList.add(categorie.getId());
            }

        }
        BoolQueryBuilder query = boolQuery();
        List<QueryBuilder> mustClauses = new ArrayList<>();
        if (StringUtils.hasText(itemPageReq.getKeyWords())) {
            mustClauses.add(multiMatchQuery(itemPageReq.getKeyWords(), "title", "seq"));

            String userId = secService.curUserId();
            if (userId != null) {
                Member member = memberRepo.findOne(
                        Expressions.allOf(
                                QMember.member.brandAppId.eq(brandAppId),
                                QMember.member.shopId.eq(shopId),
                                QMember.member.deleted.ne(true),
                                QMember.member.userId.eq(userId)
                        )
                );
                RecentSearch recentSearch = new RecentSearch();
                recentSearch.setBrandAppId(brandAppId);
                recentSearch.setMemberId(member.getId());
                recentSearch.setShopId(shopId);
                recentSearch.setKeyWord(itemPageReq.getKeyWords());
                recentSearchRepo.save(recentSearch);
            }
        }
        mustClauses.add(matchQuery("brandAppId", brandAppId));
        mustClauses.add(matchQuery("shopId", shopId));
        mustClauses.add(matchQuery("deleted", false));
        mustClauses.add(matchQuery("statusCode", ItemStatusEnum.NORMAL.getCode()));
        mustClauses.forEach(
                query::must
        );
        BoolQueryBuilder queryBuilder = boolQuery();
        if (categoryList.size() > 0) {
            categoryList.forEach(
                    categoryIds -> queryBuilder.should(termQuery("categorys", categoryIds))
            );
        }

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(query)
                .withFilter(queryBuilder)
                .build()
                .setPageable(new PageRequest(itemPageReq.getPage(), itemPageReq.getSize()))
                .addSort(sort);
        Page<EsItem> esItems = elasticsearchTemplate.queryForPage(searchQuery, EsItem.class);


        UniResp<UniPageResp<ItemMinInfo>> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(conversionService.convert(esItems, UniPageResp.class));

        esItems.getContent().forEach(
                esItem -> {
                    ItemMinInfo itemMinInfo = new ItemMinInfo();
                    itemMinInfo.setId(esItem.getItemId());
                    itemMinInfo.setBrandAppId(brandAppId);
                    List<Sku> skus = Lists.newArrayList(skuRepo.findAll(
                            Expressions.allOf(
                                    QSku.sku.itemId.eq(esItem.getId()),
                                    QSku.sku.deleted.ne(true),
                                    QSku.sku.brandAppId.eq(esItem.getBrandAppId())
                            )
                    ));
                    Sku minSku = skus.stream().min(Comparator.comparing(Sku::getSalePrice)).orElse(new Sku());
                    itemMinInfo.setSalePrice(minSku.getLabelPrice());
                    itemMinInfo.setSkuMinSalePrice(esItem.getSkuMinSalePrice());
                    itemMinInfo.setTitle(esItem.getTitle());
                    itemMinInfo.setDesp(esItem.getDesp());
                    itemMinInfo.setImgs(esItem.getImgs());
                    uniResp.getData().getContent().add(itemMinInfo);
                }
        );
        return uniResp;
    }
}
