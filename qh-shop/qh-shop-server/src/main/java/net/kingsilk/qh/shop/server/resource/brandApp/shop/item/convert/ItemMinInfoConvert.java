package net.kingsilk.qh.shop.server.resource.brandApp.shop.item.convert;

import com.google.common.collect.Lists;
import net.kingsilk.qh.shop.api.brandApp.shop.item.dto.ItemMinInfo;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.SkuRepo;
import net.kingsilk.qh.shop.repo.SkuStoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.allOf;

/**
 *
 */
@Component
public class ItemMinInfoConvert implements Converter<Item, ItemMinInfo> {

    @Autowired
    private SkuStoreRepo skuStoreRepo;

    @Autowired
    private SkuRepo skuRepo;

    @Override
    public ItemMinInfo convert(Item source) {
        ItemMinInfo info = new ItemMinInfo();
        info.setId(source.getId());
        LinkedHashSet<String> imgs = source.getImgs();
        info.setImgs(imgs);
        info.setTitle(source.getTitle());
        info.setDesp(source.getDesp());

//        SkuStore skuStore=skuStoreRepo.findOne(
//                allOf(
//                        QSkuStore.skuStore.brandAppId.eq(source.getBrandAppId()),
//                        QSkuStore.skuStore.shopId.eq(source.getShopId())
//                )
//        );
//        info.setStore()
//        item.props.each {
//            if (it.itemProp && it.itemProp.name && "品牌" == it.itemProp.name) {
//                info.brand = it.itemPropValue.name
//            }
//        }
//        if (item.tags) {
//            Iterable<Category> categoriesList = categoryRepo.findAll(
//                    Expressions.allOf(
//                            QCategory.category.id.in(item.tags),
//                            QCategory.category.deleted.in([null, false])
//                    )
//            )
//            for (Category category : categoriesList) {
//                info.categoryName.add(category.name)
//            }
//        }

//        info.categoryName = item.tags

        List<Sku> skus = Lists.newArrayList(skuRepo.findAll(
                allOf(
                        QSku.sku.itemId.eq(source.getId()),
                        QSku.sku.deleted.ne(true),
                        QSku.sku.shopId.eq(source.getShopId()),
                        QSku.sku.brandAppId.eq(source.getBrandAppId())
                )
        ));

        List<Integer> stores = new ArrayList<>();
        List<Integer> salesVolumes = new ArrayList<>();
        skus.forEach(
                sku -> {
                    SkuStore skuStore = skuStoreRepo.findOne(
                            allOf(
                                    QSkuStore.skuStore.brandAppId.eq(sku.getBrandAppId()),
                                    QSkuStore.skuStore.shopId.eq(sku.getShopId()),
                                    QSkuStore.skuStore.skuId.eq(sku.getId())
                            )
                    );
                    if (skuStore != null) {
                        stores.add(skuStore.getNum());
                        salesVolumes.add(skuStore.getSalesVolume());
                    }

                }
        );
        info.setStore(stores.stream().reduce(0, (a, b) -> a + b));
        info.setSalesVolume(salesVolumes.stream().reduce(0, (a, b) -> a + b));

        skus.sort(Comparator.comparing(Sku::getSalePrice));
        info.setSalePrice(skus.isEmpty() ? null : skus.get(0).getSalePrice());
        info.setBuyPrice(skus.isEmpty() ? null : skus.get(0).getBuyPrice());
        info.setStatusCode(source.getStatus().getCode());
        info.setStatusDesp(source.getStatus().getDesp());
        return info;
    }

//    SkuInfoModel skuConvert(Sku sku, String type) {
//        SkuInfoModel skuInfoModel = new SkuInfoModel()
//        PartnerStaff curMember = partnerStaffService.getCurPartnerStaff()
////        PartnerStaff curMember=partnerStaffRepo.findOne("597c2d4bd5f19a59a34c68cd")//create by yuanke 20170810
//        def partnerType = curMember.partner.partnerTypeEnum.code
//
//        Sku.TagPrice minTag = sku.tagPrices.findAll { Sku.TagPrice it ->
//            return partnerType == it.tag.code
//        }.min { Sku.TagPrice it ->
//            it.price
//        }
//        skuInfoModel.price = minTag.price
//        skuInfoModel.curTag = minTag.tag.code
//        skuInfoModel.leaguePrice = sku.tagPrices.find {Sku.TagPrice it ->
//            return PartnerTypeEnum.LEAGUE.code == it.tag.code
//        }.price
//        skuInfoModel.generalAgencyPrice = sku.tagPrices.find {Sku.TagPrice it ->
//            return PartnerTypeEnum.GENERAL_AGENCY.code == it.tag.code
//        }.price
//        skuInfoModel.regionalAgencyPrice = sku.tagPrices.find {Sku.TagPrice it ->
//            return PartnerTypeEnum.REGIONAL_AGENCY.code == it.tag.code
//        }.price
//        skuInfoModel.salePrice = sku.salePrice
//        skuInfoModel.skuId = sku.id
//        skuInfoModel.title = sku.item?.title
////        if(type=="PURCHASE"){
//        skuInfoModel.imgs = sku.imgs
////        }else {
////            skuInfoModel.imgs = sku.imgs?:sku.item.imgs
////        }
//        skuInfoModel.props = []
//        skuInfoModel.specs = []
//        sku.props.each {
//            def m = [
//            name : it.itemProp?.name,
//                    value: it.itemPropValue?.name
//            ]
//            skuInfoModel.props.add(m)
//        }
//        sku.specs.each {
//            def m = [
//            name   : it.itemProp?.name,
//                    nameId : it.itemProp?.id,
//                    value  : it.itemPropValue?.name,
//                    valueId: it.itemPropValue?.id
//            ]
//            skuInfoModel.specs.add(m)
//        }
//        skuInfoModel.detail = sku.detail
//        skuInfoModel.status = sku.status
//        SkuStore skuStore = null
//        if (type == "REFUND") {
//            skuStore = skuStoreRepo.findOne(
//                    Expressions.allOf(
//                            QSkuStore.skuStore.sku.eq(sku),
//                            QSkuStore.skuStore.partner.eq(curMember.partner),
//                            QSkuStore.skuStore.brandAppId.eq(curMember.brandAppId),
//                            )
//            )
//        } else {
//            skuStore = skuStoreRepo.findOne(
//                    Expressions.allOf(
//                            QSkuStore.skuStore.sku.eq(sku),
//                            QSkuStore.skuStore.partner.eq(curMember.partner.parent),
//                            QSkuStore.skuStore.brandAppId.eq(curMember.brandAppId),
//                            )
//            )
//        }
//
//
//        skuInfoModel.storage = skuStore ? skuStore.num : 0
//        return skuInfoModel
//    }
}
