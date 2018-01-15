package net.kingsilk.qh.shop.server.resource.brandApp.sync;

import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.item.dto.SkuMiniInfo;
import net.kingsilk.qh.shop.api.brandApp.sync.SyncItemApi;
import net.kingsilk.qh.shop.api.brandApp.sync.dto.ItemSyncReq;
import net.kingsilk.qh.shop.core.ItemStatusEnum;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.msg.EventPublisher;
import net.kingsilk.qh.shop.msg.api.search.esItem.sync.SyncEvent;
import net.kingsilk.qh.shop.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.querydsl.core.types.dsl.Expressions.allOf;

/**
 *
 */

@Component
public class SyncItemResource implements SyncItemApi {


    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private SkuRepo skuRepo;

    @Autowired
    private ItemPropRepo itemPropRepo;

    @Autowired
    private ItemPropValueRepo itemPropValueRepo;

    @Autowired
    private SkuStoreRepo skuStoreRepo;

    @Autowired
    private EventPublisher eventPublisher;

    @PreAuthorize("isAuthenticated()")
    public UniResp<String> syncItem(
            String brandAppId,
            ItemSyncReq itemSyncReq
    ) {

        //先新建一个item不保存，后面根据不同的shopId去判断该商品是保存，还是编辑，还是不进行任何操作
        Item item = new Item();

        //先进行基本属性的赋值
        syncItemInfo(item, itemSyncReq);

        //针对不同的门店进行商品的同步
        itemSyncReq.getShopIds().forEach(

                shopId -> {
                    Item oldItem = itemRepo.findOne(
                            allOf(
                                    QItem.item.brandAppId.eq(brandAppId),
                                    QItem.item.shopId.eq(shopId),
                                    QItem.item.syncId.eq(itemSyncReq.getId())
                            )
                    );
                    if (oldItem == null) {
                        item.setId(null);
                        item.setDateCreated(new Date());
                        item.setBrandAppId(brandAppId);
                    } else if (oldItem.getVersion() == itemSyncReq.getVersion()) {
                        return;
                    } else {
                        item.setId(oldItem.getId());
                    }

                    item.getSpecs().clear();

                    Set<Item.SpecDef> specDefs = new HashSet<>();
                    itemSyncReq.getSpecs().forEach(
                            specDef -> {
                                Item.SpecDef spec = new Item.SpecDef();

                                //先去该门店下看能不能找到这个规格
                                ItemProp itemProp = itemPropRepo.findOne(
                                        allOf(
                                                QItemProp.itemProp.brandAppId.eq(brandAppId),
                                                QItemProp.itemProp.shopId.eq(shopId),
                                                QItemProp.itemProp.name.eq(specDef.getItemPropName())
                                        )
                                );
                                //如果找不到这个规格则自动新建一个该规格
                                if (itemProp == null) {
                                    itemProp = new ItemProp();
                                    itemProp.setBrandAppId(brandAppId);
                                    itemProp.setShopId(shopId);
                                    itemProp.setName(specDef.getItemPropName());
                                    itemPropRepo.save(itemProp);
                                }
                                String itemPropId = itemProp.getId();
                                spec.setItemPropId(itemPropId);

                                //查询该规格下有没有该规格值
                                Set<String> itemPropValueIds = new HashSet<>();
                                specDef.getItemPropValueNames().forEach(
                                        itemPropValueName -> {
                                            ItemPropValue itemPropValue = itemPropValueRepo.findOne(
                                                    allOf(
                                                            QItemPropValue.itemPropValue.brandAppId.eq(brandAppId),
                                                            QItemPropValue.itemPropValue.shopId.eq(shopId),
                                                            QItemPropValue.itemPropValue.itemPropId.eq(itemPropId),
                                                            QItemPropValue.itemPropValue.name.eq(itemPropValueName)
                                                    )
                                            );

                                            //如果找不到这个规格值，则自动新建一个该规格值
                                            if (itemPropValue == null) {
                                                itemPropValue = new ItemPropValue();
                                                itemPropValue.setBrandAppId(brandAppId);
                                                itemPropValue.setShopId(shopId);
                                                itemPropValue.setName(itemPropValueName);
                                                itemPropValueRepo.save(itemPropValue);
                                                itemPropValueIds.add(itemPropValue.getId());
                                            }
                                        }
                                );

                                spec.setItemPropValueIds(itemPropValueIds);
                                specDefs.add(spec);
                            }

                    );
                    item.setSpecs(specDefs);
                    itemRepo.save(item);
                }
        );


        Iterable<Sku> deleteSkus = skuRepo.findAll(
                allOf(
                        QSku.sku.itemId.eq(item.getId())
                )
        );
        for (Sku deleteSku : deleteSkus) {
            deleteSku.setDeleted(true);
            skuRepo.save(deleteSku);
        }
        itemSyncReq.getSkuList().forEach(
                skuMiniInfo -> {

                    Sku sku = new Sku();

                    syncSkuInfo(sku, skuMiniInfo, itemSyncReq, item);

                    itemSyncReq.getShopIds().forEach(
                            shopId -> {
                                Item oldItem = itemRepo.findOne(
                                        allOf(
                                                QItem.item.brandAppId.eq(brandAppId),
                                                QItem.item.shopId.eq(shopId),
                                                QItem.item.syncId.eq(itemSyncReq.getId())
                                        )
                                );

                                Sku oldSku = skuRepo.findOne(
                                        allOf(
                                                QSku.sku.itemId.eq(oldItem.getId()),
                                                QSku.sku.brandAppId.eq(brandAppId),
                                                QSku.sku.shopId.eq(shopId),
                                                QSku.sku.syncId.eq(skuMiniInfo.getId())
                                        )
                                );
                                if (oldSku == null) {
                                    sku.setId(null);
                                    sku.setDateCreated(new Date());
                                } else if (oldSku.getVersion() == skuMiniInfo.getVersion()) {
                                    return;
                                } else {
                                    sku.setId(oldSku.getId());
                                }

                                sku.setItemId(oldItem.getId());

                                Set<Sku.Spec> specs = new HashSet<>();
                                skuMiniInfo.getSpecList().forEach(
                                        specDef -> {

                                            Sku.Spec spec = new Sku.Spec();

                                            //先去该门店下看能不能找到这个规格
                                            ItemProp itemProp = itemPropRepo.findOne(
                                                    allOf(
                                                            QItemProp.itemProp.brandAppId.eq(brandAppId),
                                                            QItemProp.itemProp.shopId.eq(shopId),
                                                            QItemProp.itemProp.name.eq(specDef.getItemPropName())
                                                    )
                                            );
                                            //如果找不到这个规格则自动新建一个该规格
                                            if (itemProp == null) {
                                                itemProp = new ItemProp();
                                                itemProp.setBrandAppId(brandAppId);
                                                itemProp.setShopId(shopId);
                                                itemProp.setName(specDef.getItemPropName());
                                                itemPropRepo.save(itemProp);
                                            }
                                            spec.setItemPropId(itemProp.getId());

                                            ItemPropValue itemPropValue = itemPropValueRepo.findOne(
                                                    allOf(
                                                            QItemPropValue.itemPropValue.brandAppId.eq(brandAppId),
                                                            QItemPropValue.itemPropValue.shopId.eq(shopId),
                                                            QItemPropValue.itemPropValue.itemPropId.eq(itemProp.getId()),
                                                            QItemPropValue.itemPropValue.name.eq(specDef.getItemPropValueName())
                                                    )
                                            );

                                            //如果找不到这个规格值，则自动新建一个该规格值
                                            if (itemPropValue == null) {
                                                itemPropValue = new ItemPropValue();
                                                itemPropValue.setBrandAppId(brandAppId);
                                                itemPropValue.setShopId(shopId);
                                                itemPropValue.setName(specDef.getItemPropValueName());
                                                itemPropValueRepo.save(itemPropValue);
                                            }

                                            spec.setItemPropValueId(itemPropValue.getId());
                                            specs.add(spec);
                                        }
                                );

                                sku.setSpecs(specs);
                                sku.setShopId(shopId);

                                skuRepo.save(sku);

                                //保存库存信息
                                SkuStore skuStore = skuStoreRepo.findOne(
                                        allOf(
                                                QSkuStore.skuStore.brandAppId.eq(brandAppId),
                                                QSkuStore.skuStore.shopId.eq(sku.getShopId()),
                                                QSkuStore.skuStore.id.eq(sku.getId())
                                        )
                                );

                                if (skuStore == null) {
                                    skuStore = new SkuStore();
                                    skuStore.setBrandAppId(sku.getBrandAppId());
                                    skuStore.setShopId(sku.getShopId());
                                }
                                skuStore.setNum(skuMiniInfo.getStorage());
                                skuStore.setSkuId(sku.getId());
                                skuStoreRepo.save(skuStore);
                            }
                    );
                }
        );

        SyncEvent syncEvent = new SyncEvent();
        syncEvent.setItemId(item.getId());
        eventPublisher.publish(syncEvent);
        UniResp<String> resp = new UniResp<>();
        resp.setStatus(200);
        resp.setData(item.getId());
        return resp;
    }


    //同步商品的基本公用属性
    public Item syncItemInfo(Item item, ItemSyncReq itemSyncReq) {
        item.setLastModifiedDate(new Date());

        item.setSyncId(itemSyncReq.getId());
        item.setVersion(itemSyncReq.getVersion());
        item.setCode(itemSyncReq.getCode());
        item.setTitle(itemSyncReq.getTitle());
        item.setDetail(itemSyncReq.getDetail());
        item.setDesp(itemSyncReq.getDesp());
        item.setCategorys(itemSyncReq.getCategorys());

        if (itemSyncReq.getStatus() != null) {
            item.setStatus(ItemStatusEnum.valueOf(itemSyncReq.getStatus()));
        } else {
            item.setStatus(ItemStatusEnum.EDITING);
        }
        item.setImgs(itemSyncReq.getImgs());

        return item;
    }

    public Sku syncSkuInfo(Sku sku, SkuMiniInfo skuMiniInfo, ItemSyncReq itemSyncReq, Item item) {

//        if (skuMiniInfo.getId() != null) {
//            sku = skuRepo.findOne(skuMiniInfo.getId());
//        }
//        if (sku == null) {
//            sku = new Sku();
//            sku.setDateCreated(new Date());
//        }
        sku.setDeleted(false);
        sku.setTitle(item.getTitle());
        sku.setLastModifiedDate(new Date());
        sku.getSpecs().clear();


        sku.setLabelPrice(skuMiniInfo.getLabelPrice());
        sku.setBuyPrice(skuMiniInfo.getBuyPrice());
        sku.setSalePrice(skuMiniInfo.getSalePrice());
        sku.setCode(skuMiniInfo.getCode());
        sku.getImgs().clear();

        if (skuMiniInfo.getImgs() != null) {
            sku.getImgs().add(skuMiniInfo.getImgs());
        } else {
            sku.setImgs(itemSyncReq.getImgs());
        }
        sku.setEnable(skuMiniInfo.getEnable());
        sku.setBrandAppId(item.getBrandAppId());

        return sku;
    }


}
