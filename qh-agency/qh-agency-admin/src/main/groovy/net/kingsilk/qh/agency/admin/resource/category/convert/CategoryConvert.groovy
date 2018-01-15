package net.kingsilk.qh.agency.admin.resource.category.convert

import net.kingsilk.qh.agency.admin.api.category.dto.CategorySaveReq
import net.kingsilk.qh.agency.domain.Category
import org.springframework.stereotype.Component

/**
 * Created by lit on 17/7/24.
 */
@Component
class CategoryConvert {

    def categorySave(Category category, CategorySaveReq categorySaveReq) {
        category.setName(categorySaveReq.name);
        category.setDesp(categorySaveReq.desp);
        category.setOrder(categorySaveReq.order);
        category.setIcon(categorySaveReq.icon);
        category.setDisabled(categorySaveReq.disabled);
        category.setImg(categorySaveReq.img);
        return category
    }

}
