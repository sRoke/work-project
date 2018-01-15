package net.kingsilk.qh.agency.wap.resource.item.convert

import net.kingsilk.qh.agency.domain.Category
import net.kingsilk.qh.agency.wap.api.item.dto.CategoryResp
import org.springframework.stereotype.Component

/**
 * Created by lit on 17/7/20.
 */
@Component
class CategoryConvert {
    List<CategoryResp> convertCategoryResp(Iterator<Category> categorys) {
        List<CategoryResp> categoryResps = new ArrayList<CategoryResp>();

        while (categorys.hasNext()) {
            CategoryResp categoryResp = new CategoryResp();
            Category category = categorys.next();
            categoryResp.id = category.id
            categoryResp.name = category.name
            categoryResp.desp = category.desp
            categoryResp.icon = category.icon
            categoryResp.img = category.img
            categoryResps.add(categoryResp);
        }

        return categoryResps;
    }
}
