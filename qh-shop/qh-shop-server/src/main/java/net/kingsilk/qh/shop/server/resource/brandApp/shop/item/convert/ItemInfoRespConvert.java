package net.kingsilk.qh.shop.server.resource.brandApp.shop.item.convert;

import com.google.common.collect.Lists;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.api.brandApp.shop.item.dto.ItemInfoResp;
import net.kingsilk.qh.shop.api.brandApp.shop.item.dto.SkuMiniInfo;
import net.kingsilk.qh.shop.api.common.dto.ItemPropModel;
import net.kingsilk.qh.shop.api.common.dto.ItemPropValueModel;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.querydsl.core.types.dsl.Expressions.allOf;

/**
 * Created by lit on 17/12/1.
 */
@Component
public class ItemInfoRespConvert implements Converter<Item, ItemInfoResp> {

    @Autowired
    private SkuStoreRepo skuStoreRepo;

    @Autowired
    private ItemPropRepo itemPropRepo;

    @Autowired
    private ItemPropValueRepo itemPropValueRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private SkuRepo skuRepo;

    @Override
    public ItemInfoResp convert(Item item) {
        if (item == null) {
            throw new ErrStatusException(ErrStatus.TIEMERROR, "商品不存在");
        }


        ItemInfoResp itemInfoResp = new ItemInfoResp();
        itemInfoResp.setId(item.getId());
        itemInfoResp.setDetail(item.getDetail());
        itemInfoResp.setImgs(item.getImgs());
        itemInfoResp.setFreight(item.getFreight());

        //商品分类包装
        Set<String> categoryNames = new HashSet<>();
        item.getCategorys().forEach(
                categoryId ->
                        categoryNames.add(categoryRepo.findOne(categoryId).getName())
        );
        itemInfoResp.getCategory().put("id", item.getCategorys());
        itemInfoResp.getCategory().put("name", categoryNames);
        itemInfoResp.setCategorys(item.getCategorys());

        itemInfoResp.setCode(item.getCode());
        itemInfoResp.setTitle(item.getTitle());
        itemInfoResp.setDesp(item.getDesp());
        itemInfoResp.setStatus(item.getStatus());

        //商品规格包装

        if (item.getSpecs() != null && item.getSpecs().size() > 0) {
            Set<ItemInfoResp.SpecDef> specDefs = new HashSet<>();
            item.getSpecs().forEach(
                    specDef -> {

                        ItemInfoResp.SpecDef specDefResp = new ItemInfoResp.SpecDef();

                        //规格名包装
                        ItemProp itemProp = itemPropRepo.findOne(specDef.getItemPropId());
                        ItemPropModel itemPropModel = new ItemPropModel();
                        itemPropModel.setId(itemProp.getId());
                        itemPropModel.setName(itemProp.getName());
                        specDefResp.setItemProp(itemPropModel);

                        //规格值包装
                        Set<ItemPropValueModel> itemPropValueModels = new LinkedHashSet<>();
                        specDef.getItemPropValueIds().forEach(
                                itemPropValueId -> {

                                    ItemPropValueModel itemPropValueModel = new ItemPropValueModel();
                                    ItemPropValue itemPropValue = itemPropValueRepo.findOne(itemPropValueId);

                                    itemPropValueModel.setId(itemPropValue.getId());
                                    itemPropValueModel.setName(itemPropValue.getName());
                                    itemPropValueModels.add(itemPropValueModel);
                                });
                        List<ItemPropValueModel> orderItemPropValue = Lists.newArrayList(itemPropValueModels.stream().sorted(Comparator.comparing(itemPropValueModel -> itemPropValueModel.getId().hashCode())).collect(Collectors.toList()));
                        specDefResp.setItemPropValueList(orderItemPropValue);
                        specDefs.add(specDefResp);
//                    Set<ItemInfoResp.SpecDef> specDefs = new TreeSet<>();
//                    specDefs.sort((str1, str2) -> str1.getItemProp().getId()-str2.getItemProp().getId());
                    });
            List<ItemInfoResp.SpecDef> orderSpecDefs = Lists.newArrayList(specDefs.stream().sorted(Comparator.comparing(s2 -> s2.getItemProp().getId().hashCode())).collect(Collectors.toList()));
            itemInfoResp.setSpecs(orderSpecDefs);
        }


        //SKu相关信息包装返回
        List<Sku> skus = Lists.newArrayList(skuRepo.findAll(
                allOf(
                        QSku.sku.itemId.eq(item.getId()),
                        QSku.sku.deleted.in(false)
                )
        ));

        List<SkuMiniInfo> skuList = new ArrayList<>();
        for (Sku sku : skus) {
            SkuMiniInfo itemSku = new SkuMiniInfo();
            itemSku.setId(sku.getId());
            Iterator<String> iterator = sku.getImgs().iterator();
            itemSku.setImgs(iterator.hasNext() ? iterator.next() : "");
            itemSku.setCode(sku.getCode());
            itemSku.setBuyPrice(sku.getBuyPrice());
            itemSku.setLabelPrice(sku.getLabelPrice());
            itemSku.setSalePrice(sku.getSalePrice());

            if (sku.getSpecs() != null && sku.getSpecs().size() > 0) {
                Set<SkuMiniInfo.SkuSpec> skuSpecSet = new LinkedHashSet<>();
                sku.getSpecs().forEach(
                        spec -> {
                            SkuMiniInfo.SkuSpec skuSpec = new SkuMiniInfo.SkuSpec();
                            ItemProp itemProp = itemPropRepo.findOne(spec.getItemPropId());
                            skuSpec.setItemPropId(itemProp.getId());
                            skuSpec.setItemPropName(itemProp.getName());
                            ItemPropValue itemPropValue = itemPropValueRepo.findOne(spec.getItemPropValueId());
                            skuSpec.setItemPropValueId(itemPropValue.getId());
                            skuSpec.setItemPropValueName(itemPropValue.getName());
                            skuSpecSet.add(skuSpec);
                        }
                );

                List<SkuMiniInfo.SkuSpec> orderSpec = Lists.newArrayList(skuSpecSet.stream().sorted(Comparator.comparing(s1 -> s1.getItemPropId().hashCode())).collect(Collectors.toList()));
                itemSku.setSpecList(orderSpec);
            }
//            for (Sku.Spec spec : sku.getSpecs()) {
//                SkuMiniInfo.SkuSpec skuSpec = new SkuMiniInfo.SkuSpec();
////                skuSpec.setId(spec.getId());
//                if (spec.getItemPropId() != null) {
//                    ItemProp itemProp = itemPropRepo.findOne(spec.getItemPropId());
//                    skuSpec.setItemPropId(itemProp.getId());
//                    skuSpec.setItemProp(itemProp.getName());
//                }
//                ItemPropValue itemPropValue = itemPropValueRepo.findOne(spec.getItemPropValueId());
//                skuSpec.setItemPropValue(itemPropValue.getName());
//                skuSpec.setItemPropValueId(itemPropValue.getId());
//                itemSku.getSpecList().add(skuSpec);
//            }
            SkuStore skuStore = skuStoreRepo.findOne(
                    allOf(
                            QSkuStore.skuStore.brandAppId.eq(sku.getBrandAppId()),
                            QSkuStore.skuStore.shopId.eq(sku.getShopId()),
                            QSkuStore.skuStore.skuId.eq(sku.getId())
                    )
            );
            if (skuStore != null) {
                itemSku.setStorage(skuStore.getNum());
            }

            skuList.add(itemSku);
//            itemInfoResp.getSkuList().add(itemSku);
        }
        List<SkuMiniInfo> orderSpec = Lists.newArrayList(skuList.stream().sorted(Comparator.comparing(s1 -> s1.getId().hashCode())).collect(Collectors.toList()));
        itemInfoResp.setSkuList(orderSpec);
        return itemInfoResp;
    }
}
