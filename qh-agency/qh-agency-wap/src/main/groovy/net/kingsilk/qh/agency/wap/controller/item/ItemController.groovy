//package net.kingsilk.qh.agency.wap.controller.item
//
//import com.querydsl.core.types.dsl.BooleanExpression
//import groovy.transform.CompileStatic
//import io.swagger.annotations.Api
//import io.swagger.annotations.ApiOperation
//import io.swagger.annotations.ApiResponse
//import io.swagger.annotations.ApiResponses
//import net.kingsilk.qh.agency.wap.api.UniResp
//import net.kingsilk.qh.agency.core.ItemStatusEnum
//import net.kingsilk.qh.agency.domain.*
//import net.kingsilk.qh.agency.repo.CategoryRepo
//import net.kingsilk.qh.agency.repo.ItemRepo
//import net.kingsilk.qh.agency.repo.SkuRepo
//import net.kingsilk.qh.agency.service.CommonService
//import net.kingsilk.qh.agency.service.MemberService
//import net.kingsilk.qh.agency.service.UserService
//import net.kingsilk.qh.agency.util.DbUtil
//
//import net.kingsilk.qh.agency.wap.controller.item.model.ItemInfoModel
//import net.kingsilk.qh.agency.wap.controller.item.model.ItemMiniInfoModel
//import net.kingsilk.qh.agency.wap.controller.item.model.SkuInfoModel
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.data.domain.Page
//import org.springframework.data.domain.PageRequest
//import org.springframework.data.domain.Sort
//import org.springframework.data.mongodb.core.MongoTemplate
//import org.springframework.http.MediaType
//import org.springframework.security.access.prepost.PreAuthorize
//import org.springframework.util.Assert
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RequestMethod
//import org.springframework.web.bind.annotation.ResponseBody
//import org.springframework.web.bind.annotation.RestController
//
//import javax.servlet.http.HttpServletRequest
//
//import static com.querydsl.core.types.dsl.Expressions.allOf
//import static com.querydsl.core.types.dsl.Expressions.anyOf
//
//@RestController()
//@RequestMapping("/api/item")
//@Api(
//        tags = "item",
//        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
//        protocols = "http,https",
//        description = "商品相关API"
//)
//@CompileStatic
//class ItemController {
//
//    @Autowired
//    UserService userService
//
//    @Autowired
//    CommonService commonService
//
//    @Autowired
//    MongoTemplate mongoTemplate
//
//    @Autowired
//    MemberService memberService
//
//    @Autowired
//    SkuRepo skuRepo
//
//    @Autowired
//    CategoryRepo categoryRepo
//
//    @Autowired
//    ItemRepo itemRepo
//
//    @Autowired
//    DbUtil dbUtil
//
    @RequestMapping(path = "/search",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "搜索商品",
            nickname = "搜索商品",
            notes = "搜索商品"
    )
    @ApiResponses([
            @ApiResponse(
                    code = 200,
                    message = "正常结果")
    ])
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('MEMBER')")
    UniResp<ItemSearchResp> search(ItemSearchReq req, HttpServletRequest request) {
        println(request.getHeader("Authorization"))

        PartnerStaff curMember = memberService.getCurPartnerStaff()
//        String[] tags = curMember?.tags*.code
        String categoryId = req.categoryId
        Iterable<Category> categories = []
//        TODO 临时去掉，京九你自己来处理
        if (categoryId != "other") {
            /**
             * 找出该分类以及该分类的子分类列表
             */
            categories = categoryRepo.findAll(
                    allOf(
                            anyOf(
                                    categoryId ? QCategory.category.id.eq(categoryId) : null,
                                    categoryId ? QCategory.category.parent.id.eq(categoryId) : null,

                            ),
                            QCategory.category.deleted.in([false, null])
                    )
            )
        } else {
            /**
             * 找出不属于这四个分类及其子分类的所有其他分类
             */

            PageRequest pageRequest = new PageRequest(0, 4, new Sort(Sort.Direction.ASC, "order"))




            List<Category> categoryList = categoryRepo.findAll(
                    allOf(
                            QCategory.category.parent.isNull(),
                            QCategory.category.deleted.in([false, null])
                    ), pageRequest
            ).content

            /**
             *DbUtil 工具类示范代码区，可用list的addAll方法多次调用进行追加，用allOf或anyOf处理list中元素并/或的关系，
             * deleted参数只需要在最后一次调用传入即可
             */
            List<BooleanExpression> expressions=dbUtil.opNotIn(categoryList, QCategory.category.parent, null)
            expressions.addAll(dbUtil.opNotIn(categoryList*.id, QCategory.category.id, QCategory.category.deleted))
            categories = categoryRepo.findAll(
                    allOf(
                            (BooleanExpression[])expressions.toArray()
                    )
            )
        }

        PageRequest pageRequest = new PageRequest(req.number, req.pageSize,
                new Sort(new Sort.Order(
                        (req.sort == "DESC" ? Sort.Direction.DESC : Sort.Direction.ASC),
                        req.sortBy)
                )
        )
        /**
         * 根据分类情况筛选出属于这些分类的商品
         */
        Page<Item> domainPage = itemRepo.findAll(
                allOf(
                        QItem.item.deleted.in([false, null]),
                        QItem.item.status.eq(ItemStatusEnum.NORMAL),
                        categories ? QItem.item.tags.any().in(categories*.id) : null

                ),
                pageRequest
        )
        Page<ItemMiniInfoModel> respPage = domainPage.map({ Item item ->
            ItemMiniInfoModel info = new ItemMiniInfoModel()
            List<Sku> skus = skuRepo.findAllByItemAndDeleted(item, false)
//            info.convert(item, skus, tags)
            return info
        })

        ItemSearchResp resp = new ItemSearchResp(page: respPage)
        return new UniResp<ItemSearchResp>(status: 200, data: resp)
    }
//
//    @RequestMapping(path = "/detail",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "商品详情",
//            nickname = "商品详情",
//            notes = "商品详情"
//    )
//    @ApiResponses([
//            @ApiResponse(
//                    code = 200,
//                    message = "正常结果")
//    ])
//    @ResponseBody
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('MEMBER')")
//    UniResp<ItemInfoModel> detail(ItemDetailReq req) {
//        PartnerStaff curMember = memberService.getCurPartnerStaff()
////        String[] tags = curMember?.tags*.code
//
//        Item item = mongoTemplate.findById(req.itemId, Item)
//        Assert.notNull(item, "itemId错误")
//        Assert.isTrue(!item.deleted, "该商品已下架")
//
//        ItemInfoModel resp = new ItemInfoModel()
////        resp.convert(item, null, tags)
//        //List<Sku> skus = skuRepo.findAllByItemId(item.id)
//        Iterable<Sku> skus = skuRepo.findAll(allOf(
//                QSku.sku.item.eq(item),
//                QSku.sku.status.eq("false")
//        ))
//        skus.each { Sku it ->
//            SkuInfoModel sku = new SkuInfoModel()
////            sku.convert(it, tags)
//            resp.skus.add(sku)
//        }
//        return new UniResp<ItemInfoModel>(status: 200, data: resp)
//    }
//
//    @RequestMapping(path = "/getCategory",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "获取商品分类",
//            nickname = "获取商品分类",
//            notes = "获取商品分类"
//    )
//    @ApiResponses([
//            @ApiResponse(
//                    code = 200,
//                    message = "正常结果")
//    ])
//    @ResponseBody
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('MEMBER')")
//    UniResp<Iterable<Category>> getCategory() {
//
//        PageRequest pageRequest = new PageRequest(0, 4, new Sort(Sort.Direction.ASC, "order"))
//
//        Iterable<Category> categoryList = categoryRepo.findAll(
//                allOf(
//                        QCategory.category.parent.isNull(),
//                        QCategory.category.deleted.in([false, null])
//                ), pageRequest
//        )
//        return new UniResp<Iterable<Category>>(status: 200, data: categoryList)
//    }
//}
