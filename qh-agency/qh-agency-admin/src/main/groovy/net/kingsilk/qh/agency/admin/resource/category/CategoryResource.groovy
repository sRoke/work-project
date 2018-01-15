package net.kingsilk.qh.agency.admin.api.category

import com.querydsl.core.types.dsl.Expressions
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.admin.api.UniResp
import net.kingsilk.qh.agency.admin.api.category.dto.CategorySaveReq
import net.kingsilk.qh.agency.admin.resource.category.convert.CategoryConvert
import net.kingsilk.qh.agency.domain.Category
import net.kingsilk.qh.agency.domain.Item
import net.kingsilk.qh.agency.domain.QCategory
import net.kingsilk.qh.agency.repo.CategoryRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

/**
 * Created by lit on 17/7/24.
 */

@Api(
        tags = "category",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "会员相关API"
)
@Path("/category")
@Component
public class CategoryResource {
    @Autowired
    CategoryRepo categoryRepo

    @Autowired
    MongoTemplate mongoTemplate

    @Autowired
    CategoryConvert categoryConvert

    @ApiOperation(
            value = "商品分类列表",
            nickname = "商品分类列表",
            notes = "商品分类列表"
    )
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<List<Category>> list() {
        Iterable<Category> categories = categoryRepo.findAll(
                Expressions.allOf(
                        QCategory.category.deleted.in([false, null])
                )
        ).asList()
        return new UniResp(status: 200, data: categories);
    }


    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "保存或更新商品分类",
            nickname = "保存或更新商品分类",
            notes = "保存或更新商品分类"
    )
    UniResp<String> save(CategorySaveReq categorySaveReq) {
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
        category = categoryConvert.categorySave(category, categorySaveReq);
        mongoTemplate.save(category);
        return new UniResp(status: 200, data: "保存成功");
    }

    @ApiOperation(
            value = "商品分类禁用启用",
            nickname = "商品分类禁用启用",
            notes = "商品分类禁用启用"
    )
    @Path("/enable")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> enable(
            @ApiParam(required = true, value = "商品分类的ID")
            @QueryParam(value = "id") String id,
            @ApiParam(required = true, value = "false:正常;true:禁用")
            @QueryParam(value = "disabled") boolean disabled) {
        Category category = mongoTemplate.findById(id, Category)
        if (!category) {
            return new UniResp(status: 10024, data: "指定的分类不存在");
        }
        //category.disabled = categoryEnableReq.disabled;
        category.disabled = disabled;
        mongoTemplate.save(category);
        return new UniResp(status: 200, data: "操作成功"); ;
    }

    @ApiOperation(
            value = "删除商品分类",
            nickname = "删除商品分类",
            notes = "删除商品分类"
    )
    @Path("/delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> delete(@ApiParam(required = true, value = "商品分类的ID")
                           @QueryParam(value = "id") String id,
                           @ApiParam(required = true, value = "false:正常;true:禁用")
                           @QueryParam(value = "deleted") boolean deleted) {
        Category category = categoryRepo.findOne(id)
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
