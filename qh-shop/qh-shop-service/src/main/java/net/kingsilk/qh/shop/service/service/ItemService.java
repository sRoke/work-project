package net.kingsilk.qh.shop.service.service;

import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.api.brandApp.shop.item.dto.ItemSaveReq;
import net.kingsilk.qh.shop.core.ItemStatusEnum;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.ItemPropValueRepo;
import net.kingsilk.qh.shop.repo.ItemRepo;
import net.kingsilk.qh.shop.repo.SkuRepo;
import net.kingsilk.qh.shop.repo.SkuStoreRepo;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

import static com.querydsl.core.types.dsl.Expressions.allOf;

/**
 *
 */
@Service
public class ItemService {

    @Autowired
    private ItemPropValueRepo itemPropValueRepo;

    @Autowired
    private SkuRepo skuRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private SkuStoreRepo skuStoreRepo;

    public String saveItem(Item item, ItemSaveReq itemSaveReq) {


        item.setLastModifiedDate(new Date());

        item.setCode(itemSaveReq.getCode());
        item.setTitle(itemSaveReq.getTitle());
        item.setDetail(itemSaveReq.getDetail());
        item.setDesp(itemSaveReq.getDesp());
        item.setCategorys(itemSaveReq.getCategorys());

        if (itemSaveReq.getStatus() != null) {
            item.setStatus(ItemStatusEnum.valueOf(itemSaveReq.getStatus()));
        } else {
            item.setStatus(ItemStatusEnum.EDITING);
        }
        item.setImgs(itemSaveReq.getImgs());
        item.setFreight(itemSaveReq.getFreight());
        item.getSpecs().clear();

        if (itemSaveReq.getSpecs() != null && itemSaveReq.getSpecs().size() > 0) {
            Set<Item.SpecDef> specDefs = new HashSet<>();
            itemSaveReq.getSpecs().forEach(
                    specDef -> {
                        Item.SpecDef spec = new Item.SpecDef();
                        spec.setItemPropId(specDef.getItemPropId());
                        spec.setItemPropValueIds(specDef.getItemPropValueIds());
                        specDefs.add(spec);
                    }

            );
            item.setSpecs(specDefs);
        }
        itemRepo.save(item);


        Iterable<Sku> deleteSkus = skuRepo.findAll(
                allOf(
                        QSku.sku.itemId.eq(item.getId())
                )
        );
        for (Sku deleteSku : deleteSkus) {
//            skuRepo.delete(deleteSku)
            deleteSku.setDeleted(true);
            skuRepo.save(deleteSku);
        }
        itemSaveReq.getSkuList().forEach(
                skuMiniInfo -> {
                    Sku sku = null;
                    if (skuMiniInfo.getId() != null) {
                        sku = skuRepo.findOne(skuMiniInfo.getId());
                    }
                    if (sku == null) {
                        sku = new Sku();
                        sku.setDateCreated(new Date());
                    }
                    sku.setDeleted(false);
                    sku.setTitle(item.getTitle());
                    sku.setLastModifiedDate(new Date());
                    sku.setItemId(item.getId());
                    sku.getSpecs().clear();


                    if (skuMiniInfo.getSpecList() != null && skuMiniInfo.getSpecList().size() > 0) {
                        Set<Sku.Spec> specs = new HashSet<>();
                        skuMiniInfo.getSpecList().forEach(
                                specId -> {
                                    ItemPropValue itemPropValue = itemPropValueRepo.findOne(specId.getItemPropValueId());
                                    Assert.notNull(itemPropValue, "商品规格错误！");
                                    Sku.Spec spec = new Sku.Spec();
                                    spec.setItemPropId(itemPropValue.getItemPropId());
                                    spec.setItemPropValueId(itemPropValue.getId());
                                    specs.add(spec);
                                }
                        );

                        sku.setSpecs(specs);
                    }

                    sku.setLabelPrice(skuMiniInfo.getLabelPrice());
                    sku.setBuyPrice(skuMiniInfo.getBuyPrice());
                    sku.setSalePrice(skuMiniInfo.getSalePrice());
                    sku.setCode(skuMiniInfo.getCode());
                    sku.getImgs().clear();
                    if (skuMiniInfo.getImgs() != null) {
                        sku.getImgs().add(skuMiniInfo.getImgs());
                    } else {
                        sku.setImgs(itemSaveReq.getImgs());
                    }
                    sku.setEnable(skuMiniInfo.getEnable());
                    sku.setBrandAppId(item.getBrandAppId());
                    sku.setShopId(item.getShopId());
                    skuRepo.save(sku);

                    //保存库存信息
                    SkuStore skuStore = new SkuStore();
                    skuStore.setBrandAppId(sku.getBrandAppId());
                    skuStore.setShopId(sku.getShopId());
                    skuStore.setNum(skuMiniInfo.getStorage());
                    skuStore.setSkuId(sku.getId());
                    skuStoreRepo.save(skuStore);
                }
        );
        return item.getId();
    }

    public void checkItem(Item item) {
//        if (item.getSpecs() != null || item.getSpecs().size() == 0) {
//            throw new ErrStatusException(ErrStatus.TIEMERROR, "请至少选择一种规格");
//        }
        if (item.getTitle() == null) {
            throw new ErrStatusException(ErrStatus.TIEMERROR, "请选择主标题");
        }
//        if (item.getDetail() == null) {
//            throw new ErrStatusException(ErrStatus.TIEMERROR, "请编写描述");
//        }
//        if (item.getCategorys() == null || item.getCategorys().size() == 0) {
//            throw new ErrStatusException(ErrStatus.TIEMERROR, "请至少选择一种商品分类");
//        }

//        item.getSpecs().forEach(
//                specDef -> {
//                    Integer ids = specDef.getItemPropValueIds().size();
//                    if (ids == 0) {
//                        throw new ErrStatusException(ErrStatus.TIEMERROR, "请至少选择一种属性值");
//                    }
//                }
//        );

        Iterator<Sku> skus = skuRepo.findAll(
                allOf(
                        QSku.sku.itemId.eq(item.getId()),
                        QSku.sku.deleted.eq(false)
                )
        ).iterator();
        List<Sku> skuList = IteratorUtils.toList(skus);
        if (skuList == null || skuList.size() == 0) {
            throw new ErrStatusException(ErrStatus.TIEMERROR, "请至少添加一种sku");
        }

        skuList.forEach(
                skuMiniInfo -> {
//                    Integer buyerPrice = skuMiniInfo.getBuyPrice();
//                    Integer labelPrice = skuMiniInfo.getLabelPrice();
                    Integer salePrice = skuMiniInfo.getSalePrice();
                    Set<Sku.Spec> specs = skuMiniInfo.getSpecs();
//                    if (specs == null || specs.size() <= 0) {
//                        throw new ErrStatusException(ErrStatus.TIEMERROR, "请至少选择一种规格");
//                    }
//                    Integer storage = skuMiniInfo.getStorage();
//                    if (buyerPrice == null) {
//                        throw new ErrStatusException(ErrStatus.TIEMERROR, "请输入采购价");
//                    }
//                    if (labelPrice == null) {
//                        throw new ErrStatusException(ErrStatus.TIEMERROR, "请输入吊牌价");
//                    }
                    if (salePrice == null) {
                        throw new ErrStatusException(ErrStatus.TIEMERROR, "请输入促销价");
                    }
//                    if (storage != null) {
//                        throw new ErrStatusException(ErrStatus.TIEMERROR, "请输入库存");
//                    }
                }
        );
        if (item.getImgs() == null || item.getImgs().size() == 0) {
            throw new ErrStatusException(ErrStatus.TIEMERROR, "请至少上传一张图片");
        }

        if (item.getStatus() == null) {
            throw new ErrStatusException(ErrStatus.TIEMERROR, "请选择状态");
        }
        Boolean itemSkuStatus = false;
        for (Sku sku : skuList) {
            if (Boolean.FALSE == sku.getEnable()) {
                itemSkuStatus = true;
            }
        }
        if (ItemStatusEnum.NORMAL.getCode().equals(item.getStatus()) && !itemSkuStatus) {
            throw new ErrStatusException(ErrStatus.TIEMERROR, "SKU全部禁用不能为正常状态");
        }
    }


}
