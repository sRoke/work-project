package net.kingsilk.qh.agency.server.resource.brandApp.category

import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.api.UniPageReq
import net.kingsilk.qh.agency.api.UniPageResp
import net.kingsilk.qh.agency.api.UniResp
import net.kingsilk.qh.agency.api.brandApp.category.CategoryApi
import net.kingsilk.qh.agency.api.brandApp.category.dto.CategoryInfoResp
import net.kingsilk.qh.agency.api.brandApp.category.dto.CategorySaveReq
import net.kingsilk.qh.agency.domain.Category
import net.kingsilk.qh.agency.domain.Item
import net.kingsilk.qh.agency.domain.QCategory
import net.kingsilk.qh.agency.domain.QItem
import net.kingsilk.qh.agency.repo.CategoryRepo
import net.kingsilk.qh.agency.repo.ItemRepo
import net.kingsilk.qh.agency.service.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.convert.ConversionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component
import org.springframework.util.Assert

/**
 *
 */

@Component(value = "categoryResource")
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
    CategoryService categoryService

    @Override
    UniResp<List<CategoryInfoResp>> list(String brandAppId, UniPageReq uniPageReq) {

        List<Category> categories =  categoryService.getCategorys(brandAppId)
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
    UniResp<UniPageResp<CategoryInfoResp>> page(String brandAppId, UniPageReq uniPageReq) {

        Page<Category> categories = categoryRepo.findAll(
                Expressions.allOf(
                        QCategory.category.deleted.in([false, null]),
                        QCategory.category.brandAppId.eq(brandAppId)
                ), new PageRequest(uniPageReq.page, uniPageReq.size,
                new Sort(Sort.Direction.ASC,"order"))
        )
        UniResp<UniPageResp<CategoryInfoResp>> uniResp = new UniResp<>()
        uniResp.data = conversionService.convert(categories, UniPageResp)
        categories.content.each {
            Category category ->
                uniResp.data.content.add(categoryConvert.convertResp(category))
        }
        uniResp.setStatus(200)
        return uniResp
    }

    @Override
    UniResp<String> save(String bandComId, CategorySaveReq categorySaveReq) {

        Category category = new Category()
        Assert.notNull(categorySaveReq.name, "请输入名称")

        categorySaveReq.getItems().each {
            Item item = itemRepo.findOne(
                    Expressions.allOf(
                            QCategory.category.brandAppId.eq(bandComId),
                            QItem.item.deleted.in([false, null]),
                            QItem.item.id.eq(it.id)
                    )
            )
            Assert.notNull(item, "商品不存在")
        }
        if (categorySaveReq.parent != null) {
            category.parent = categoryRepo.findOne(
                    Expressions.allOf(
                            QCategory.category.brandAppId.eq(bandComId),
                            QCategory.category.deleted.in([false, null]),
                            QCategory.category.id.eq(categorySaveReq.parent))
            )
        }
        categoryService.saveCategory(categorySaveReq,bandComId)
        categorySaveReq.setBrandAppId(bandComId)
        category = categoryConvert.categorySave(category, categorySaveReq)
        categoryRepo.save(category)
        return new UniResp(status: 200, data: "保存成功")
    }

    @Override
    UniResp<String> update(String bandComId,
                           String id, CategorySaveReq categorySaveReq) {
        Category category = categoryRepo.findOne(
                Expressions.allOf(
                        QCategory.category.deleted.in([false, null]),
                        QCategory.category.id.eq(id),
                        QCategory.category.brandAppId.eq(bandComId)
                )
        )

        Assert.notNull(category, "分类信息不存在")
        categoryService.saveCategory(categorySaveReq,bandComId)
        categorySaveReq.setBrandAppId(bandComId)
        category = categoryConvert.categoryUpdate(category, categorySaveReq)
        categoryRepo.save(category)
        return new UniResp(status: 200, data: "更新成功")
    }

    @Override
    UniResp<String> enable(String bandComId, String id, boolean disabled) {
        Assert.notNull(bandComId, "品牌商id不能为空")
        Category category = categoryRepo.findOne(
                Expressions.allOf(
                        QCategory.category.deleted.in([false, null]),
                        QCategory.category.brandAppId.eq(bandComId),
                        QCategory.category.id.eq(id)
                ))
        if (!category) {
            return new UniResp(status: 10024, data: "指定的分类不存在")
        }

        category.disabled = disabled
        mongoTemplate.save(category)
        return new UniResp(status: 200, data: "操作成功")
    }

    @Override
    UniResp<String> delete(String bandComId, String id) {

        Category category = categoryRepo.findOne(
                Expressions.allOf(
                        QCategory.category.deleted.in([false, null]),
                        QCategory.category.brandAppId.eq(bandComId),
                        QCategory.category.id.eq(id)
                ))
        if (!category) {
            return new UniResp(status: 10024, data: "指定的分类不存在")
        }

        Iterable<Category> categories = categoryRepo.findAll(
                Expressions.allOf(
                        QCategory.category.parent.eq(category),
                        QCategory.category.deleted.in([false, null])
                )
        )
        if (categories.size() > 0) {
            return new UniResp(status: 500, data: "请先删除子分类！")
        }
        category.deleted = true
        categoryRepo.save(category)
        return new UniResp(status: 200, data: "操作成功")
    }
}
