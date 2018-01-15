package net.kingsilk.qh.shop.server.resource.brandApp.shop.category.convert;

import net.kingsilk.qh.shop.api.brandApp.shop.category.dto.CategorySaveReq;
import net.kingsilk.qh.shop.domain.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by lit on 17/11/8.
 */
@Component
public class CategorySaveReqConvert implements Converter<CategorySaveReq, Category> {
    @Override
    public Category convert(CategorySaveReq source) {

        Category category = new Category();
        category.setParentId(source.getParentId());
        category.setDesp(source.getDesp());
        category.setName(source.getName());
        category.setOrder(source.getOrder());
        category.setEnable(source.isEnable());
        return category;
    }
}
