package net.kingsilk.qh.shop.server.resource.brandApp.shop.category.convert;

import net.kingsilk.qh.shop.api.brandApp.shop.category.dto.CategoryInfoResp;
import net.kingsilk.qh.shop.domain.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class CategoryInfoRespConvert implements Converter<Category, CategoryInfoResp> {

    @Override
    public CategoryInfoResp convert(Category category) {
        CategoryInfoResp resp = new CategoryInfoResp();
        resp.setBrandAppId(category.getBrandAppId());
        resp.setParentId(category.getParentId());
        resp.setShopId(category.getShopId());
        resp.setDesp(category.getDesp());
        resp.setId(category.getId());
        resp.setName(category.getName());
        resp.setOrder(category.getOrder());
        return resp;
    }
}
