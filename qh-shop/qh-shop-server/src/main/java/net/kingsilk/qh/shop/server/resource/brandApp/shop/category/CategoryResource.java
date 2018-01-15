package net.kingsilk.qh.shop.server.resource.brandApp.shop.category;

import com.google.common.collect.Lists;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.UniPageReq;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.category.CategoryApi;
import net.kingsilk.qh.shop.api.brandApp.shop.category.dto.CategoryInfoResp;
import net.kingsilk.qh.shop.api.brandApp.shop.category.dto.CategorySaveReq;
import net.kingsilk.qh.shop.domain.Category;
import net.kingsilk.qh.shop.domain.Item;
import net.kingsilk.qh.shop.domain.QCategory;
import net.kingsilk.qh.shop.domain.QItem;
import net.kingsilk.qh.shop.repo.CategoryRepo;
import net.kingsilk.qh.shop.repo.ItemRepo;
import net.kingsilk.qh.shop.service.util.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.allOf;

/**
 *
 */
@Component
public class CategoryResource implements CategoryApi {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    @Qualifier(value = "mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private ItemRepo itemRepo;

    //--------------------------------商品分类列表,不带分页---------------------------------------//
    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<List<CategoryInfoResp>> list(
            String brandAppId,
            String shopId
    ) {

        ArrayList<String> sort = new ArrayList<>();
        sort.add("order,asc");
        sort.add("lastModifiedDate,desc");
        Sort s = ParamUtils.toSort(sort);
        UniResp<List<CategoryInfoResp>> uniResp = new UniResp<>();
        List<CategoryInfoResp> categoryInfoRespList = new ArrayList<>();
        categoryRepo.findAll(
                allOf(
                        QCategory.category.brandAppId.eq(brandAppId),
                        QCategory.category.shopId.eq(shopId),
                        QCategory.category.deleted.eq(false),
                        QCategory.category.enable.eq(true)
                ), s)
                .forEach(
                category -> {
                    CategoryInfoResp resp = conversionService.convert(category, CategoryInfoResp.class);
                    categoryInfoRespList.add(resp);
                }

        );
        uniResp.setData(categoryInfoRespList);
        uniResp.setStatus(200);
        return uniResp;
    }


    //--------------------------------商品分类列表,带分页---------------------------------------//
    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<UniPageResp<CategoryInfoResp>> page(
            String brandAppId,
            String shopId,
            UniPageReq uniPageReq
    ) {
        UniResp<UniPageResp<CategoryInfoResp>> uniResp = new UniResp<>();
        UniPageResp<CategoryInfoResp> pageResp = new UniPageResp<>();
        List<CategoryInfoResp> categoryInfoRespList = new ArrayList<>();
        PageRequest pageRequest = new PageRequest(uniPageReq.getPage(), uniPageReq.getSize(),
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")));
        Page<Category> categoryPage = categoryRepo.findAll(
                allOf(
                        QCategory.category.brandAppId.eq(brandAppId),
                        QCategory.category.shopId.eq(shopId),
                        QCategory.category.deleted.eq(false),
                        QCategory.category.enable.eq(true)
                ), pageRequest
        );
        categoryPage.getContent().forEach(
                category -> {
                    CategoryInfoResp resp = conversionService.convert(category, CategoryInfoResp.class);
                    categoryInfoRespList.add(resp);
                }

        );
        conversionService.convert(categoryPage, UniPageResp.class);
        pageResp.setContent(categoryInfoRespList);
        uniResp.setData(pageResp);
        uniResp.setStatus(200);
        return uniResp;
    }

    //--------------------------------保存商品分类---------------------------------------//
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> save(
            String brandAppId,
            String shopId,
            CategorySaveReq categorySaveReq) {
        Category category = conversionService.convert(categorySaveReq, Category.class);

        category.setBrandAppId(brandAppId);
        category.setShopId(shopId);


        categoryRepo.save(category);
        UniResp uniResp = new UniResp();
        uniResp.setData("保存成功");
        uniResp.setStatus(200);
        return uniResp;
    }

    //--------------------------------更新商品分类---------------------------------------//
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> update(
            String brandAppId,
            String shopId,
            String id,
            CategorySaveReq categorySaveReq) {

        Category category = categoryRepo.findOne(
                allOf(
                        QCategory.category.brandAppId.eq(brandAppId),
                        QCategory.category.shopId.eq(shopId),
                        QCategory.category.id.eq(id)
                )
        );
        Assert.notNull(category, "商品分类ID错误！");
        category = conversionService.convert(categorySaveReq, Category.class);
        category.setBrandAppId(brandAppId);
        category.setShopId(shopId);
        category.setId(id);
        categoryRepo.save(category);

        UniResp uniResp = new UniResp();
        uniResp.setData("更新成功");
        uniResp.setStatus(200);
        return uniResp;
    }

    //--------------------------------商品分类禁用启用---------------------------------------//
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> enable(
            String brandAppId,
            String shopId,
            String id,
            boolean enabled) {
        Category category = categoryRepo.findOne(
                allOf(
                        QCategory.category.brandAppId.eq(brandAppId),
                        QCategory.category.shopId.eq(shopId),
                        QCategory.category.id.eq(id)
                )
        );
        Assert.notNull(category, "商品分类ID错误！");
        category.setEnable(enabled);
        categoryRepo.save(category);

        UniResp uniResp = new UniResp();
        uniResp.setData("更新成功");
        uniResp.setStatus(200);
        return uniResp;
    }

    //--------------------------------删除商品分类---------------------------------------//
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> delete(                                              //todo
            String brandAppId,
            String shopId,
            String id) {
        Category delCategory = categoryRepo.findOne(
                allOf(
                        QCategory.category.brandAppId.eq(brandAppId),
                        QCategory.category.shopId.eq(shopId),
                        QCategory.category.id.eq(id)
                )
        );
        Assert.notNull(delCategory, "商品分类ID错误！");
        Iterable<Category> allChild = categoryRepo.findAll(
                allOf(
                        QCategory.category.brandAppId.eq(brandAppId),
                        QCategory.category.shopId.eq(brandAppId),
                        QCategory.category.parentId.eq(id)
                )
        );
        if (allChild.iterator().hasNext()){
            UniResp uniResp = new UniResp();
            uniResp.setData("请先删除子分类");
            uniResp.setStatus(ErrStatus.DELERROR);
            return uniResp;
        }

        //遍历所以商品
        ArrayList<Item> items = Lists.newArrayList(itemRepo.findAll(
                allOf(
                        QItem.item.brandAppId.eq(brandAppId),
                        QItem.item.shopId.eq(shopId),
                        QItem.item.deleted.ne(true)
                )
        ));

        //todo
        for (Item item :items){
            for (String categoryId :item.getCategorys()){
                Category curCategory = categoryRepo.findOne(categoryId);
                if (delCategory.getName().equalsIgnoreCase(curCategory.getName())){
                    UniResp<String> uniResp = new UniResp<>();
                    uniResp.setData("该分类已经被其他商品使用，不能删除。");
                    uniResp.setStatus(ErrStatus.DELERROR);
                    return uniResp;
                }
            }
        }
        delCategory.setDeleted(true);
        categoryRepo.save(delCategory);

        UniResp uniResp = new UniResp();
        uniResp.setData("删除成功");
        uniResp.setStatus(200);
        return uniResp;
    }


    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<CategoryInfoResp> info(
            String brandAppId,
            String shopId,
            String id) {

        Category category = categoryRepo.findOne(
                allOf(
                        QCategory.category.brandAppId.eq(brandAppId),
                        QCategory.category.shopId.eq(shopId),
                        QCategory.category.id.eq(id),
                        QCategory.category.deleted.ne(true),
                        QCategory.category.enable.eq(true)
                )
        );
        Assert.notNull(category, "商品分类ID错误，或可能已经被删除！");
        CategoryInfoResp infoResp = conversionService.convert(category, CategoryInfoResp.class);
        UniResp<CategoryInfoResp> uniResp = new UniResp<>();
        uniResp.setData(infoResp);
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }
}
