package net.kingsilk.qh.shop.server.resource.brandApp.shop.itemProp;

import net.kingsilk.qh.shop.api.UniPageReq;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.itemProp.ItemPropApi;
import net.kingsilk.qh.shop.api.brandApp.shop.itemProp.dto.ItemPropPageReq;
import net.kingsilk.qh.shop.api.common.dto.ItemPropModel;
import net.kingsilk.qh.shop.api.common.dto.ItemPropValueModel;
import net.kingsilk.qh.shop.domain.ItemProp;
import net.kingsilk.qh.shop.domain.ItemPropValue;
import net.kingsilk.qh.shop.domain.QItemProp;
import net.kingsilk.qh.shop.domain.QItemPropValue;
import net.kingsilk.qh.shop.repo.ItemPropRepo;
import net.kingsilk.qh.shop.repo.ItemPropValueRepo;
import net.kingsilk.qh.shop.service.service.ItemPropValueService;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Component
public class ItemPropResource implements ItemPropApi {

    @Autowired
    ItemPropRepo itemPropRepo;

    @Autowired
    ItemPropValueRepo itemPropValueRepo;

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;


    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<UniPageResp<ItemPropModel>> page(
            String brandAppId,
            String shopId,
            ItemPropPageReq itemPropPageReq) {

        Assert.notNull(brandAppId, "品牌商id不能为空");

        Page<ItemProp> itemPropPage = itemPropRepo.findAll(
                allOf(
                        QItemProp.itemProp.deleted.eq(false),
                        QItemProp.itemProp.brandAppId.eq(brandAppId),
                        QItemProp.itemProp.shopId.eq(shopId),
                        itemPropPageReq.getName() != null ? QItemProp.itemProp.name.like("%" + itemPropPageReq.getName() + "%") : null
                ),
                new PageRequest(
                        itemPropPageReq.getPage(), itemPropPageReq.getSize(), new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")
                )));

        UniResp<UniPageResp<ItemPropModel>> uniResp = new UniResp<>();

        //类型转换
        //先把返回的自定义page类型和spring的page类型转换
        //再在参数中处理api中dto的类型和damain的类型
        uniResp.setData(conversionService.convert(itemPropPage, UniPageResp.class));

        uniResp.setStatus(200);
        return uniResp;
    }


    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> save(
            String brandAppId,
            String shopId,
            String name
    ) {

        Assert.notNull(brandAppId, "品牌商id不能为空");
//        ItemProp itemProp = conversionService.convert(itemPropSaveReq, ItemProp.class);
        ItemProp itemProp = new ItemProp();
        itemProp.setBrandAppId(brandAppId);
        itemProp.setName(name);
        itemProp.setShopId(shopId);
        ItemProp itProp = itemPropRepo.save(itemProp);

        Assert.notNull(itProp, "属性保存失败");
        UniResp uniResp = new UniResp();
        uniResp.setData("保存成功");
        uniResp.setStatus(200);
        return uniResp;
    }


    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> update(
            String brandAppId,
            String shopId,
            String id,
            String name) {
        Assert.notNull(brandAppId, "品牌商id不能为空");
        ItemProp itemProp = itemPropRepo.findOne(
                allOf(
                        QItemProp.itemProp.deleted.eq(false),
                        QItemProp.itemProp.brandAppId.eq(brandAppId),
                        QItemProp.itemProp.shopId.eq(shopId),
                        QItemProp.itemProp.id.eq(id)
                ));

        Assert.notNull(itemProp, "未找到该属性");

        itemProp.setName(name);

        itemPropRepo.save(itemProp);
        UniResp uniResp = new UniResp();
        uniResp.setData("保存成功");
        uniResp.setStatus(200);
        return uniResp;
    }


    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> delete(
            String brandAppId,
            String shopId,
            String itemPropId) {
        Assert.notNull(brandAppId, "品牌商id不能为空");

        ItemProp itemProp = itemPropRepo.findOne(
                allOf(
                        QItemProp.itemProp.brandAppId.eq(brandAppId),
                        QItemProp.itemProp.shopId.eq(shopId),
                        QItemProp.itemProp.id.eq(itemPropId)
                ));

        Assert.notNull(itemProp, "所删除的数据不存在");

        itemProp.setDeleted(true);

        itemPropRepo.save(itemProp);
        UniResp uniResp = new UniResp();
        uniResp.setData("保存成功");
        uniResp.setStatus(200);
        return uniResp;
    }


//    @Override
////    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SA','SHOP_SA')")
//    public UniResp<ItemPropModel> info(
//            String brandAppId,
//            String shopId,
//            String itemPropId) {
//
//        Assert.notNull(brandAppId, "品牌商id不能为空");
//
//        ItemProp itemProp = itemPropRepo.findOne(
//                allOf(
//                        QItemProp.itemProp.deleted.eq(false),
//                        QItemProp.itemProp.brandAppId.eq(brandAppId),
//                        QItemProp.itemProp.shopId.eq(shopId),
//                        QItemProp.itemProp.id.eq(itemPropId)
//                )
//        );
//        Assert.notNull(itemProp, "所查找的数据不存在");
//
//
////        List<ItemPropValue> list = itemPropValueService.search(itemProp.getId(), brandAppId);
////        List<ItemPropValueModel> itemPropValueModels = new ArrayList<>();
////        list.forEach(
////                itemPropValue -> itemPropValueModels.add(conversionService.convert(itemPropValue, ItemPropValueModel.class))
////        );
//        ItemPropModel itemPropModel = conversionService.convert(itemProp, ItemPropModel.class);
////        itemPropModel.setPropValues(itemPropValueModels);
//        UniResp uniResp = new UniResp();
//        uniResp.setData(itemPropModel);
//        uniResp.setStatus(200);
//        return uniResp;
//    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<List<ItemPropModel>> list(
            String brandAppId,
            String shopId,
            String itemPropKeyword,
            UniPageReq uniPageReq) {

        Assert.notNull(brandAppId, "品牌商id不能为空");

        Iterator<ItemProp> itemProps = itemPropRepo.findAll(
                allOf(
                        QItemProp.itemProp.deleted.eq(false),
                        QItemProp.itemProp.brandAppId.eq(brandAppId),
                        QItemProp.itemProp.shopId.eq(shopId),
                        itemPropKeyword != null ? QItemProp.itemProp.name.like("%" + itemPropKeyword + "%") : null
                )
        ).iterator();
        List<ItemProp> itemPropList = IteratorUtils.toList(itemProps);
        UniResp<List<ItemPropModel>> uniResp = new UniResp<>();
        if (itemPropList.size() > 0) {
            List<ItemPropModel> itemPropModelList = new LinkedList<>();
            itemPropList.forEach(
                    itemProp -> itemPropModelList.add(conversionService.convert(itemProp, ItemPropModel.class))

            );
            uniResp.setData(itemPropModelList);
        }
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> addPropValue(
            String brandAppId,
            String shopId,
            String id,
            String name
    ) {
        ItemProp itemProp = itemPropRepo.findOne(id);
        Assert.notNull(itemProp, "找不到该规格");

        ItemPropValue itemPropValue = new ItemPropValue();

        itemPropValue.setItemPropId(itemProp.getId());
        itemPropValue.setShopId(shopId);
        itemPropValue.setBrandAppId(brandAppId);
        itemPropValue.setName(name);

        itemPropValueRepo.save(itemPropValue);

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setData("保存成功");
        uniResp.setStatus(200);
        return uniResp;
    }

    public UniResp<String> updatePropValue(
            String brandAppId,
            String shopId,
            String id,
            String name
    ) {
        UniResp<String> uniResp = new UniResp<>();
        ItemPropValue itemPropValue = itemPropValueRepo.findOne(
                allOf(
                        QItemPropValue.itemPropValue.shopId.eq(shopId),
                        QItemPropValue.itemPropValue.brandAppId.eq(brandAppId),
                        QItemPropValue.itemPropValue.id.eq(id)
                )
        );
        itemPropValue.setName(name);
        itemPropValueRepo.save(itemPropValue);
        uniResp.setData("保存成功");
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> deletePropValue(
            String brandAppId,
            String shopId,
            String itemPropId) {
        Assert.notNull(brandAppId, "品牌商id不能为空");

        ItemPropValue itemPropValue = itemPropValueRepo.findOne(
                allOf(
                        QItemPropValue.itemPropValue.brandAppId.eq(brandAppId),
                        QItemPropValue.itemPropValue.shopId.eq(shopId),
                        QItemPropValue.itemPropValue.id.eq(itemPropId)
                ));

        Assert.notNull(itemPropValue, "所删除的数据不存在");

        itemPropValue.setDeleted(true);

        itemPropValueRepo.save(itemPropValue);
        UniResp uniResp = new UniResp();
        uniResp.setData("保存成功");
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<List<ItemPropValueModel>> itemPropListItem(
            String brandAppId,
            String shopId,
            String itemPropKeyword,
            UniPageReq uniPageReq) {

        Assert.notNull(brandAppId, "品牌商id不能为空");

        Iterator<ItemPropValue> itemPropValues = itemPropValueRepo.findAll(
                allOf(
                        QItemPropValue.itemPropValue.itemPropId.eq(itemPropKeyword),
                        QItemPropValue.itemPropValue.shopId.eq(shopId),
                        QItemPropValue.itemPropValue.brandAppId.eq(brandAppId),
                        QItemPropValue.itemPropValue.deleted.in(false))
        ).iterator();

        List<ItemPropValue> itemPropValueList = IteratorUtils.toList(itemPropValues);
        UniResp<List<ItemPropValueModel>> uniResp = new UniResp<>();

        //类型转换
        //先把返回的自定义page类型和spring的page类型转换
        //再在参数中处理api中dto的类型和damain的类型
        List<ItemPropValueModel> itemPropValueModelList = new LinkedList<>();
        itemPropValueList.forEach(
                itemPropValue -> itemPropValueModelList.add(conversionService.convert(itemPropValue, ItemPropValueModel.class))
        );
        uniResp.setData(itemPropValueModelList);
        uniResp.setStatus(200);
        return uniResp;
    }


}
