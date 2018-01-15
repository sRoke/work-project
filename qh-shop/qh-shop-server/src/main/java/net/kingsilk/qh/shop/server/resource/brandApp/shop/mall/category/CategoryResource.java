package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.category;

import net.kingsilk.qh.shop.api.UniPageReq;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.category.dto.CategoryInfoResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.category.CategoryApi;
import net.kingsilk.qh.shop.domain.QCategory;
import net.kingsilk.qh.shop.repo.CategoryRepo;
import net.kingsilk.qh.shop.service.util.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Component("mallCategoryResource")
public class CategoryResource implements CategoryApi {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    @Qualifier(value = "mvcConversionService")
    private ConversionService conversionService;

    @Override
    @PreAuthorize("permitAll()")
    public UniResp<List<CategoryInfoResp>> list(String brandAppId, String shopId) {

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

    @Override
    public UniResp<UniPageResp<CategoryInfoResp>> page(String brandAppId, String shopId, UniPageReq uniPageReq) {
        return null;
    }

    @Override
    public UniResp<String> enable(String brandAppId, String shopId, String id, boolean enable) {
        return null;
    }

    @Override
    public UniResp<CategoryInfoResp> info(String brandAppId, String shopId, String id) {
        return null;
    }
}
