package net.kingsilk.qh.agency.wap.resource.item

import com.querydsl.core.types.dsl.Expressions
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import net.kingsilk.qh.agency.core.ItemStatusEnum
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.repo.CategoryRepo
import net.kingsilk.qh.agency.repo.ItemRepo
import net.kingsilk.qh.agency.repo.SkuRepo
import net.kingsilk.qh.agency.service.ItemService
import net.kingsilk.qh.agency.wap.api.UniResp
import net.kingsilk.qh.agency.wap.api.item.ItemApi
import net.kingsilk.qh.agency.wap.api.item.dto.*
import net.kingsilk.qh.agency.wap.resource.item.convert.CategoryConvert
import net.kingsilk.qh.agency.wap.resource.item.convert.ItemConvert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType

@Api(tags = "item", produces = MediaType.APPLICATION_JSON, protocols = "http,https", description = "商品相关API")
@Path("/item")
@Component
class ItemResource implements ItemApi {

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemConvert itemConvert
    @Autowired
    CategoryConvert categoryConvert

    @Autowired
    SkuRepo skuRepo

    @ApiOperation(value = "搜索商品", nickname = "搜索商品", notes = "搜索商品")
    @Path("/search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<List<ItemMiniInfoModel>> search(@BeanParam ItemSearchReq req, @Context HttpServletRequest request) {

        String keyWord = req.keyWord

        String categoryId = req.getCategoryId();
        List<Category> categories = new ArrayList<>()

        if (categoryId) {
            Category categorie = categoryRepo.findOne(categoryId);
            if (categorie) {
                List<Category> categorieList = categoryRepo.findAll(
                        QCategory.category.parent.id.eq(categorie.id)
                ).toList()
                if (categorieList) {
                    categories.addAll(categorieList)
                }

            }
            categories.add(categorie)
        }

        /**
         * 根据分类情况筛选出属于这些分类的商品
         */
        Iterator<Item> items = itemRepo.findAll(
                Expressions.allOf(QItem.item.deleted.in(Arrays.asList(false, null)),
                        keyWord ? QItem.item.title.like("%"+keyWord+"%") : null,
                        QItem.item.status.eq(ItemStatusEnum.NORMAL),
                        categories.size() > 0 ? QItem.item.tags.any().in(categories*.id) : null)
        ).iterator()

        def resp = itemConvert.convertItemSearchResp(items);
        return new UniResp<List<ItemMiniInfoModel>>(status: 200, data: resp);
    }

    @ApiOperation(value = "商品详情", nickname = "商品详情", notes = "商品详情")
    @Path("/detail")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<ItemInfoModel> detail(@QueryParam(value = "itemId") String itemId) {
        Item item = itemRepo.findOne(itemId);

        def resp = itemConvert.convertItemInfo(item)
        Iterable<Sku> skus = skuRepo.findAll(
                Expressions.allOf(
                        QSku.sku.item.eq(item),
                        QSku.sku.status.eq("false")
                ))
        skus.each { Sku it ->
            SkuInfoModel sku = itemConvert.skuConvert(it)
            resp.skus.add(sku)
        }
        return new UniResp<ItemInfoModel>(status: 200, data: resp);
    }

    @ApiOperation(value = "获取商品分类", nickname = "获取商品分类", notes = "获取商品分类")
    @Path("/getCategory")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<List<CategoryResp>> getCategory() {
        PageRequest pageRequest = new PageRequest(0, 4, new Sort(Sort.Direction.ASC, "order"));

        Iterator<Category> categoryList = categoryRepo.findAll(Expressions.allOf(QCategory.category.parent.isNull(), QCategory.category.deleted.in(Arrays.asList(null, false))), pageRequest).iterator();
        def resp = categoryConvert.convertCategoryResp(categoryList)
        return new UniResp<List<CategoryResp>>(status: 200, data: resp)
    }


}
