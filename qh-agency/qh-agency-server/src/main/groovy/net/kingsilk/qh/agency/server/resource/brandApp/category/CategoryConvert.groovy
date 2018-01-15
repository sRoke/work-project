package net.kingsilk.qh.agency.server.resource.brandApp.category

import net.kingsilk.qh.agency.api.brandApp.category.dto.CategoryInfoResp
import net.kingsilk.qh.agency.api.brandApp.category.dto.CategorySaveReq
import net.kingsilk.qh.agency.domain.Category
import net.kingsilk.qh.agency.repo.CategoryRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by lit on 17/7/24.
 */
@Component
class CategoryConvert {
    @Autowired
    CategoryRepo categoryRepo


    Category categorySave(Category category, CategorySaveReq categorySaveReq) {
        category.setName(categorySaveReq.name);
        category.setDesp(categorySaveReq.desp);
        category.setOrder(categorySaveReq.order);
        category.setParent(category.parent);
        category.setDisabled(categorySaveReq.disabled);
        category.setBrandAppId(categorySaveReq.getBrandAppId())
        return category
    }

    Category categoryUpdate(Category category, CategorySaveReq categorySaveReq) {

        if (categorySaveReq.getName() != null) {
            category.setName(categorySaveReq.getName())
        }
        if (categorySaveReq.getDisabled() != null) {
            category.setDisabled(categorySaveReq.getDisabled())
        }
        if (categorySaveReq.getDesp() != null) {
            category.setDesp(categorySaveReq.getDesp())
        }
        if (categorySaveReq.getBrandAppId() != null) {
            category.setBrandAppId(categorySaveReq.getBrandAppId())
        }
        if (categorySaveReq.getOrder() != null) {
            category.setOrder(categorySaveReq.getOrder())
        }
        if (categorySaveReq.getParent() != null) {
            Category parent = categoryRepo.findOne(categorySaveReq.parent)
            category.setParent(parent)
        }
        return category
    }

    CategoryInfoResp convertResp(Category category) {
        CategoryInfoResp infoResp = new CategoryInfoResp()
        infoResp.setId(category.getId())
        infoResp.setDesp(category.getDesp())
        infoResp.setOrder(category.getOrder())
        infoResp.setBrandAppId(category.getBrandAppId())
        infoResp.setName(category.getName())
        if (category.parent != null) {
            infoResp.setParentId(category.getParent().getId())
        }
        return infoResp
    }

}
