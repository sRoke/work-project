package net.kingsilk.qh.agency.admin.controller.category

import com.querydsl.core.types.dsl.Expressions
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import net.kingsilk.qh.agency.admin.api.UniResp
import net.kingsilk.qh.agency.domain.Category
import net.kingsilk.qh.agency.domain.Item
import net.kingsilk.qh.agency.domain.QCategory
import net.kingsilk.qh.agency.repo.CategoryRepo
import net.kingsilk.qh.agency.service.CommonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by tpx on 17-3-16.
 */
@RestController()
@RequestMapping("/api/category")
@Api( // 用在类上，用于设置默认值
        tags = "category",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        protocols = "http,https",
        description = "商品分类相关API"
)
class CategoryController {

    @Autowired
    MongoTemplate mongoTemplate

    @Autowired
    CategoryRepo categoryRepo

    @Autowired
    CommonService commonService


    @RequestMapping(path = "/list",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "商品分类列表",
            nickname = "商品分类列表",
            notes = "商品分类列表"
    )
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('CATEGORY_R')")
    UniResp<String> list() {
        Iterable<Category> categories=categoryRepo.findAll(
                Expressions.allOf(
                        QCategory.category.deleted.in([false,null])
                )
        )
        return new UniResp(status: 200, data:categories);
    }


    @RequestMapping(path = "/save",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(
            value = "保存或更新商品分类",
            nickname = "保存或更新商品分类",
            notes = "保存或更新商品分类"
    )
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('CATEGORY_C','CATEGORY_U') && hasAnyAuthority('STAFF') ")
    UniResp<String> save(@RequestBody CategorySaveReq categorySaveReq) {
        Category category = null;
        if (categorySaveReq.getIsAdd()) {
            category = new Category()
        } else {
            category = mongoTemplate.findById(categorySaveReq.getId(), Category);
            if (!category) {
                return new UniResp(status: 10024, message: "分类信息不存在");
            }
        }
        if (!categorySaveReq.name) {
            return new UniResp(status: 10024, message: "请输入名称");
        }
        categorySaveReq.getItems().each {
            Item item = mongoTemplate.findById(it.getId(), Item);
            if (item) {
                category.items.add(item);
            }
        }
        category.parent = mongoTemplate.findById(categorySaveReq.parent, Category);
        categorySaveReq.convertToCategory(category);
        mongoTemplate.save(category);
        return new UniResp(status: 200, data: "保存成功");
    }

    @RequestMapping(path = "/enable",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "商品分类禁用启用",
            nickname = "商品分类禁用启用",
            notes = "商品分类禁用启用"
    )
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('CATEGORY_C','CATEGORY_U') && hasAnyAuthority('STAFF') ")
    UniResp<String> enable(@RequestBody CategoryEnableReq categoryEnableReq) {
        Category category = mongoTemplate.findById(categoryEnableReq.id, Category)
        if (!category) {
            return new UniResp(status: 10024, data: "指定的分类不存在");
        }
        //category.disabled = categoryEnableReq.disabled;
        category.disabled = categoryEnableReq.isDisabled();
        mongoTemplate.save(category);
        return new UniResp(status: 200, data: "操作成功"); 
    }

    @RequestMapping(path = "/delete",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "删除商品分类",
            nickname = "删除商品分类",
            notes = "删除商品分类"
    )
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF','CATEGORY_D') && hasAnyAuthority('STAFF')"  )
    UniResp<String> delete(@RequestBody CategoryEnableReq categoryEnableReq) {

        Category category = categoryRepo.findOne(categoryEnableReq.id)
        if (!category) {
            return new UniResp(status: 10024, data: "指定的分类不存在");
        }

        Iterable<Category> categories=categoryRepo.findAll(
                Expressions.allOf(
                        QCategory.category.parent.eq(category),
                        QCategory.category.deleted.in([false, null])
                )
        )
        if (categories.size()>0){
            return new UniResp(status: 500, data: "请先删除子分类！")
        }
        category.deleted=true
        categoryRepo.save(category)
        return new UniResp(status: 200, data: "操作成功")
    }

}
