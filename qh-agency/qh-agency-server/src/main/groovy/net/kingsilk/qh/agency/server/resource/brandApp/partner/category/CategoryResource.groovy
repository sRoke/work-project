package net.kingsilk.qh.agency.server.resource.brandApp.partner.category

import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.api.UniPageReq
import net.kingsilk.qh.agency.api.UniResp
import net.kingsilk.qh.agency.api.brandApp.category.dto.CategoryInfoResp
import net.kingsilk.qh.agency.api.brandApp.partner.category.CategoryApi
import net.kingsilk.qh.agency.domain.Category
import net.kingsilk.qh.agency.domain.QCategory
import net.kingsilk.qh.agency.repo.CategoryRepo
import net.kingsilk.qh.agency.repo.ItemRepo
import net.kingsilk.qh.agency.server.resource.brandApp.category.CategoryConvert
import net.kingsilk.qh.agency.service.PartnerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.convert.ConversionService
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

/**
 *
 */
@Component(value = "category")
class CategoryResource implements CategoryApi {
    @Autowired
    CategoryRepo categoryRepo

    @Autowired
    MongoTemplate mongoTemplate

    @Autowired
    CategoryConvert categoryConvert

    @Autowired
    ItemRepo itemRepo

    @Autowired
    @Qualifier(value = "mvcConversionService")
    ConversionService conversionService

    @Autowired
    private PartnerService partnerService

    @Override
    UniResp<List<CategoryInfoResp>> getCategoryList(String brandAppId, String partnerId, UniPageReq uniPageReq) {
        partnerService.check()
        List<Category> categories = categoryRepo.findAll(
                Expressions.allOf(
                        QCategory.category.deleted.in([false, null]),
                        QCategory.category.brandAppId.eq(brandAppId),
                        QCategory.category.parent.isNull()
                ),new Sort(Sort.Direction.ASC,"order")).toList()
        UniResp<List<CategoryInfoResp>> uniResp = new UniResp<>()
//        uniResp.data=conversionService.convert(categories,UniPageResp)
        uniResp.data = new ArrayList<>()
        categories.each {
            Category category ->
                uniResp.data.add(categoryConvert.convertResp(category))
        }
        uniResp.setStatus(200)
        return uniResp
    }

    @Override
    UniResp<List<CategoryInfoResp>> list(String brandAppId, String partnerId, UniPageReq uniPageReq) {
        partnerService.check()
        List<Category> categories = categoryRepo.findAll(
                Expressions.allOf(
                        QCategory.category.deleted.in([false, null]),
                        QCategory.category.brandAppId.eq(brandAppId),
                )
        ).toList()
        UniResp<List<CategoryInfoResp>> uniResp = new UniResp<>()
//        uniResp.data=conversionService.convert(categories,UniPageResp)
        uniResp.data = new ArrayList<>()
        categories.each {
            Category category ->
                uniResp.data.add(categoryConvert.convertResp(category))
        }
        uniResp.setStatus(200)
        return uniResp
    }

}
