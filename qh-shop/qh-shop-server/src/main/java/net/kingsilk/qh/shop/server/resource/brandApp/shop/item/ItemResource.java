package net.kingsilk.qh.shop.server.resource.brandApp.shop.item;

import com.mongodb.gridfs.GridFSFile;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.item.ItemApi;
import net.kingsilk.qh.shop.api.brandApp.shop.item.dto.ItemInfoResp;
import net.kingsilk.qh.shop.api.brandApp.shop.item.dto.ItemMinInfo;
import net.kingsilk.qh.shop.api.brandApp.shop.item.dto.ItemPageReq;
import net.kingsilk.qh.shop.api.brandApp.shop.item.dto.ItemSaveReq;
import net.kingsilk.qh.shop.core.ItemStatusEnum;
import net.kingsilk.qh.shop.domain.Category;
import net.kingsilk.qh.shop.domain.Item;
import net.kingsilk.qh.shop.domain.QCategory;
import net.kingsilk.qh.shop.domain.QItem;
import net.kingsilk.qh.shop.es.domain.EsItem;
import net.kingsilk.qh.shop.msg.EventPublisher;
import net.kingsilk.qh.shop.msg.api.search.ItemsImport.sync.ImportSyncEvent;
import net.kingsilk.qh.shop.msg.api.search.esItem.sync.SyncEvent;
import net.kingsilk.qh.shop.repo.CategoryRepo;
import net.kingsilk.qh.shop.repo.ItemRepo;
import net.kingsilk.qh.shop.service.service.DownloadModelService;
import net.kingsilk.qh.shop.service.service.ExcelGridFDService;
import net.kingsilk.qh.shop.service.service.ItemService;
import net.kingsilk.qh.shop.service.util.ParamUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
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
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import java.io.InputStream;
import java.util.*;

import static com.querydsl.core.types.dsl.Expressions.allOf;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 *
 */

@Component
public class ItemResource implements ItemApi {

    @Autowired
    private ItemService itemService;

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    @Qualifier(value = "mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private ItemRepo itemRepo;

    @Context
    private HttpServletResponse response;

    @Autowired
    private DownloadModelService downloadModelService;

    @Autowired
    private ExcelGridFDService excelGridFDService;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    //--------------------------------保存商品信息---------------------------------------//
    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> save(
            String brandAppId,
            String shopId,
            ItemSaveReq itemSaveReq) {

        Item item = new Item();
        item.setDateCreated(new Date());
        item.setBrandAppId(brandAppId);
        item.setShopId(shopId);
        String itemId = itemService.saveItem(item, itemSaveReq);

        SyncEvent syncEvent = new SyncEvent();
        syncEvent.setItemId(itemId);
        eventPublisher.publish(syncEvent);
        UniResp<String> resp = new UniResp<>();
        resp.setStatus(200);
        resp.setData(item.getId());
        return resp;
    }

    //--------------------------------商品信息详情---------------------------------------//
    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<ItemInfoResp> info(
            String brandAppId,
            String shopId,
            String id) {
        Item item = itemRepo.findOne(
                allOf(
                        QItem.item.brandAppId.eq(brandAppId),
                        QItem.item.shopId.eq(shopId),
                        QItem.item.id.eq(id)
                )
        );

        ItemInfoResp itemInfoResp = conversionService.convert(item, ItemInfoResp.class);
        UniResp<ItemInfoResp> resp = new UniResp<>();
        resp.setStatus(200);
        resp.setData(itemInfoResp);
        return resp;
    }

    //--------------------------------商品分页信息详情,普通搜索---------------------------------------//

    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<UniPageResp<ItemMinInfo>> page(
            String brandAppId,
            String shopId,
            ItemPageReq itemPageReq) {
        PageRequest pageRequest = new PageRequest(itemPageReq.getPage(), itemPageReq.getSize(),
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")));
        Page<Item> itemList = itemRepo.findAll(
                allOf(
                        itemPageReq.getKeyWords() != null ? QItem.item.title.contains(itemPageReq.getKeyWords()) : null,
                        itemPageReq.getStatus() != null ? QItem.item.status.eq(ItemStatusEnum.valueOf(itemPageReq.getStatus())) : null,
                        QItem.item.shopId.eq(shopId),
                        QItem.item.deleted.in(false),
                        QItem.item.brandAppId.eq(brandAppId)
                ), pageRequest
        );


        UniResp<UniPageResp<ItemMinInfo>> infoPage = new UniResp<>();

        infoPage.setStatus(200);
        infoPage.setData(conversionService.convert(itemList, UniPageResp.class));
        itemList.getContent().forEach(
                item -> {
                    ItemMinInfo itemMinInfo = conversionService.convert(item, ItemMinInfo.class);
                    itemMinInfo.setDateCreated(item.getDateCreated());
                    itemMinInfo.setCategorys(item.getCategorys());
                    infoPage.getData().getContent().add(itemMinInfo);
                }
        );
        return infoPage;
    }

    //--------------------------------商品分页信息详情,普通搜索---------------------------------------//
    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<UniPageResp<ItemMinInfo>> search(
            String brandAppId,
            String shopId,
            ItemPageReq itemPageReq) {

//        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        String categoryId = itemPageReq.getCategory();
        List<String> categoryList = new ArrayList<>();
//        Sort sort = ParamUtils.toSort(itemPageReq.getSort());
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
        }
        mustClauses.add(matchQuery("brandAppId", brandAppId));
        mustClauses.add(matchQuery("shopId", shopId));
        mustClauses.add(matchQuery("deleted", false));

        if (itemPageReq.getStatus() != null) {
            if (!"ALL".equals(itemPageReq.getStatus())) {
                mustClauses.add(matchQuery("statusCode", ItemStatusEnum.valueOf(itemPageReq.getStatus())));
            }
        } else {
            mustClauses.add(matchQuery("statusCode", ItemStatusEnum.NORMAL.getCode()));
        }
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
                .addSort(new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")));
        Page<EsItem> esItems = elasticsearchTemplate.queryForPage(searchQuery, EsItem.class);

        UniResp<UniPageResp<ItemMinInfo>> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(conversionService.convert(esItems, UniPageResp.class));

        esItems.getContent().forEach(
                esItem -> {

                    Item item = itemRepo.findOne(esItem.getItemId());
                    ItemMinInfo itemMinInfo = conversionService.convert(item, ItemMinInfo.class);
                    itemMinInfo.setDateCreated(item.getDateCreated());
                    itemMinInfo.setCategorys(item.getCategorys());
                    Set<String> categoryNames = new HashSet<>();

                    item.getCategorys().forEach(
                            category -> {
                                Category category1 = categoryRepo.findOne(category);
                                if (category1 != null) {
                                    categoryNames.add(category1.getName());
                                }

                            }
                    );
                    itemMinInfo.setCategoryNames(categoryNames);
                    uniResp.getData().getContent().add(itemMinInfo);

//                    ItemMinInfo itemMinInfo = new ItemMinInfo();
//                    itemMinInfo.setId(esItem.getItemId());
//                    itemMinInfo.setBrandAppId(brandAppId);
//
//                    itemMinInfo.setSkuMinSalePrice(esItem.getSkuMinSalePrice());
//                    itemMinInfo.setTitle(esItem.getTitle());
//                    itemMinInfo.setDesp(esItem.getDesp());
//                    itemMinInfo.setImgs(esItem.getImgs());
//                    uniResp.getData().getContent().add(itemMinInfo);
                }
        );
        return uniResp;
    }

    //--------------------------------删除商品---------------------------------------//
    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> delete(
            String brandAppId,
            String shopId,
            String id) {
        Item item = itemRepo.findOne(id);
        item.setDeleted(true);
        itemRepo.save(item);

        SyncEvent syncEvent = new SyncEvent();
        syncEvent.setItemId(item.getId());
        eventPublisher.publish(syncEvent);
        UniResp<String> resp = new UniResp<>();
        resp.setStatus(200);
        resp.setData("删除成功");
        return resp;
    }

    //--------------------------------商品状态更改---------------------------------------//
    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> changeStatus(
            String brandAppId,
            String shopId,
            String id,
            String status
    ) {
        Item item = itemRepo.findOne(id);
        itemService.checkItem(item);
        item.setStatus(ItemStatusEnum.valueOf(status));
        itemRepo.save(item);

        SyncEvent syncEvent = new SyncEvent();
        syncEvent.setItemId(item.getId());
        eventPublisher.publish(syncEvent);
        UniResp<String> resp = new UniResp<>();
        resp.setStatus(200);
        resp.setData("状态更改成功");
        return resp;
    }

    //--------------------------------更新商品信息---------------------------------------//
    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> update(
            String brandAppId,
            String shopId,
            String id,
            ItemSaveReq itemSaveReq) {
        Item item = itemRepo.findOne(id);
        Assert.notNull(item, "商品信息错误！");
        item.setDateCreated(new Date());
        item.setBrandAppId(brandAppId);
        item.setShopId(shopId);
        String itemId = itemService.saveItem(item, itemSaveReq);

        SyncEvent syncEvent = new SyncEvent();
        syncEvent.setItemId(itemId);
        eventPublisher.publish(syncEvent);
        UniResp<String> resp = new UniResp<>();
        resp.setStatus(200);
        resp.setData(item.getId());
        return resp;
    }


    @Override
    public UniResp<String> downloadModel(
            String brandAppId,
            String shopId) {
        downloadModelService.downloadModel(response);
        return null;
//        return uniResp;
    }

//    @Override
//    public UniResp<String> uploadModel(
//            String brandAppId,
//            String shopId,
//            InputStream uploadedInputStream,
//            FormDataContentDisposition fileDetail) {
//        Assert.notNull(uploadedInputStream,"获取不到输入流信息");
//        Assert.notNull(fileDetail,"获取不到上传的信息");
//        Assert.notNull(brandAppId,"获取不到brandAppId信息");
//
//        UniResp<String> uniResp = excelModelService.uploadExcelModel(uploadedInputStream, fileDetail);
//        return uniResp;
//    }


    //todo
    @Override
    public UniResp<String> importItems(
            String brandAppId,
            String shopId,
            InputStream uploadedInputStream,
            FormDataContentDisposition fileDetail) {
//
        UniResp<String> uniResp = new UniResp<String>();

        GridFSFile fsFile = excelGridFDService.saveWorkbook(uploadedInputStream, fileDetail);
        if (fsFile != null) {
            ImportSyncEvent event = new ImportSyncEvent();
            event.setBrandAppId(brandAppId);
            event.setShopId(shopId);
            event.setDbFileId(fsFile.getId().toString());
            event.setDaFileName(fsFile.getFilename());
            eventPublisher.publish(event);

            uniResp.setStatus(ErrStatus.OK);
            uniResp.setData("成功，正在导入中...");
            return uniResp;
        } else {
            uniResp.setStatus(ErrStatus.UNKNOWN);
            uniResp.setData("导入失败！");
            return uniResp;
        }
    }


//
//        List<String> excelHeadPropsList;
//
//        try {
//            List<ArrayList<String>> arrayLists = excelReadService.readExcel(uploadedInputStream, fileDetail);
//            if (arrayLists == null || arrayLists.size() == 0) {
//                uniResp.setStatus(ErrStatus.PARAMNUll);
//                uniResp.setData("读取不到excel数据");
//                return uniResp;
//            }
//
//            excelHeadPropsList = arrayLists.get(0);
//            if (excelHeadPropsList == null) {
//                uniResp.setData("表格第二行的数据为空");
//                uniResp.setStatus(ErrStatus.UNKNOWN);
//                return uniResp;
//            }
//            if (excelHeadPropsList.size() < 9 && excelHeadPropsList.size() > 10) {
//                uniResp.setData("不支持该格式的excel的导入");
//                uniResp.setStatus(ErrStatus.UNKNOWN);
//                return uniResp;
//            }
//
//            Assert.notNull(arrayLists.get(0).get(1), "第一个产品读取不到");
////            String sameItem = arrayLists.get(0).get(1);
//
//            ArrayList<String> itemIdList = new ArrayList<>();
//            //前一个商品
//            String prevItemTitle = null;
//            Item prevItem = null;
//
//            for (int i =1 ; i<arrayLists.size(); i++ ) {
//
//                long prevstore = System.currentTimeMillis();
//
//                ArrayList<String> row = arrayLists.get(i);
//                Item item = null;
//                if (prevItemTitle != row.get(1) || prevItemTitle == null) {
//                    ArrayList<Item> titleItems = Lists.newArrayList(itemRepo.findAll(
//                            allOf(
//                                    QItem.item.brandAppId.eq(brandAppId),
//                                    QItem.item.shopId.eq(shopId),
//                                    QItem.item.title.eq(row.get(1)),
//                                    QItem.item.deleted.ne(true)
//                            )
//                    ));
//                    if (titleItems == null || titleItems.size() < 1) {
//                        item = new Item();
//                        item.setDateCreated(new Date());
//                    } else if (titleItems.size() == 1) {
//                        item = titleItems.get(0);
//                    } else {
//                        item = titleItems.get(0);
//                        uniResp.setException(titleItems.get(0).getTitle() + " : 该商品已经存在(重名了)");
//                    }
//                    item.setBrandAppId(brandAppId);
//                    item.setShopId(shopId);
//                    item.setStatus(ItemStatusEnum.EDITING);
//
//                    Set<String> categorys = new HashSet<>();
//                    categorys.add(row.get(0));
//                    item.setCategorys(categorys);
//
//                    item.setTitle(row.get(1));
//                    itemRepo.save(item);
//                    prevItem = item;
//                    //新的商品，先清理sku
//                    Iterable<Sku> deleteSkus = skuRepo.findAll(
//                            allOf(
//                                    QSku.sku.itemId.eq(item.getId())
//                            )
//                    );
//                    for (Sku deleteSku : deleteSkus) { //todo
//                        deleteSku.setDeleted(true);
//                        skuRepo.save(deleteSku);
//                    }
//                } else if (prevItemTitle == row.get(1)) {
//                    item = prevItem;
//                }
//                //存入sku
//                Sku sku = new Sku();
//                if (excelHeadPropsList.size() == 9) {
//
//                    Sku.Spec skuSpec1 = itemPropService.skuSpecPackage(item, row.get(2), excelHeadPropsList.get(3)
//                            , row.get(3));
//                    Sku.Spec skuSpec2 = itemPropService.skuSpecPackage(item, row.get(2), excelHeadPropsList.get(4)
//                            , row.get(4));
//                    sku.getSpecs().add(skuSpec1);
//                    sku.getSpecs().add(skuSpec2);
//
//                } else if (excelHeadPropsList.size() == 10) {
//                    Sku.Spec skuSpec1 = itemPropService.skuSpecPackage(item, row.get(2), excelHeadPropsList.get(3)
//                            , row.get(3));
//                    Sku.Spec skuSpec2 = itemPropService.skuSpecPackage(item, row.get(2), excelHeadPropsList.get(4)
//                            , row.get(4));
//                    Sku.Spec skuSpec3 = itemPropService.skuSpecPackage(item, row.get(2), excelHeadPropsList.get(5)
//                            , row.get(5));
//                    sku.getSpecs().add(skuSpec1);
//                    sku.getSpecs().add(skuSpec2);
//                    sku.getSpecs().add(skuSpec3);
//                }
//
//                sku.setEnable(true);
//                sku.setCode(row.get(2));
//                sku.setItemId(item.getId());
//                sku.setTitle(item.getTitle());
//                if (!StringUtils.isEmpty(row.get(row.size() - 3))){
//                    sku.setLabelPrice(Integer.parseInt(row.get(row.size() - 3)) * 100);
//                }
//                if (!StringUtils.isEmpty(row.get(row.size() - 2))){
//                    sku.setLabelPrice(Integer.parseInt(row.get(row.size() - 2)) * 100);
//                }
//                if (!StringUtils.isEmpty(row.get(row.size() - 1))){
//                    sku.setLabelPrice(Integer.parseInt(row.get(row.size() - 1)) * 100);
//                }
//                skuRepo.save(sku);
//
//
////                skuStore.setBrandAppId(brandAppId);    //todo 库存
////                skuStore.setShopId(shopId);
////                skuStore.setNum(Integer.parseInt(row.get(5)));
////                skuStore.setSkuId(sku.getId());
//
//                itemIdList.add(item.getId());
//
//                long atferstore = System.currentTimeMillis();
//                long l = atferstore - prevstore;
//                System.out.println("store one item times:"+l);
//            }
//            itemIdList.forEach(
//                    id -> {
//                        SyncEvent syncEvent = new SyncEvent();
//                        syncEvent.setItemId(id);
//                        eventPublisher.publish(syncEvent);
//                    }
//            );

//            uniResp.setStatus(200);
//            uniResp.setData("success");
//            return uniResp;

//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        uniResp.setStatus(ErrStatus.UNKNOWN);
//        uniResp.setData("导入失败");
//        return uniResp;
//    }
}
