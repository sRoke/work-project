package net.kingsilk.qh.agency.service

import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.api.brandApp.category.dto.CategorySaveReq
import net.kingsilk.qh.agency.domain.Category
import net.kingsilk.qh.agency.domain.QCategory
import net.kingsilk.qh.agency.repo.CategoryRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    List<Category> getCategorys(String brandAppId) {

        List<Category> categories = categoryRepo.findAll(
                Expressions.allOf(
                        QCategory.category.deleted.in([false, null]),
                        QCategory.category.brandAppId.eq(brandAppId),
                        QCategory.category.parent.isNull()
                ), new Sort(Sort.Direction.ASC, "order")
        ).toList()

        List<Category> categorieSons = getCategorySon(brandAppId,categories)
        categories.addAll(categorieSons)

        while(categorieSons!=null && categorieSons.size()!=0){

            categorieSons = getCategorySon(brandAppId,categorieSons)
            categories.addAll(categorieSons)
        }
        return categories
    }

    /**
     * 获得所有子级菜单,并安order排序
     * @param categories
     * @return
     */
    List<Category> getCategorySon(String brandAppId,List<Category> categories) {
        List<Category> categorieSons = new LinkedList<>()
        categories.each { Category category ->
            List<Category> categorieSon = categoryRepo.findAll(
                    Expressions.allOf(
                            QCategory.category.deleted.in([false, null]),
                            QCategory.category.brandAppId.eq(brandAppId),
                            QCategory.category.parent.eq(category)
                    ), new Sort(Sort.Direction.ASC, "order")
            ).toList()
            if (categorieSon) {
                categorieSons.addAll(categorieSon)
            }
        }
        return categorieSons
    }

    /**
     *
     * @param categorySaveReq
     * @param bandComId
     */
    void saveCategory(CategorySaveReq categorySaveReq , String bandComId){
        List<Category> categorySame = new LinkedList<>()
        if (categorySaveReq.parent != null) {
            categorySame = categoryRepo.findAll(
                    Expressions.allOf(
                            QCategory.category.brandAppId.eq(bandComId),
                            QCategory.category.deleted.in([false, null]),
                            QCategory.category.parent.id.eq(categorySaveReq.parent),
                            QCategory.category.order.eq(categorySaveReq.order)
                    )
            ).toList()
        }else {
            categorySame = categoryRepo.findAll(
                    Expressions.allOf(
                            QCategory.category.brandAppId.eq(bandComId),
                            QCategory.category.deleted.in([false, null]),
                            QCategory.category.parent.isNull(),
                            QCategory.category.order.eq(categorySaveReq.order)
                    )
            ).toList()

        }
        if(categorySame && categorySame.size()!=0){
            List<Category> categorys = categoryRepo.findAll(
                    Expressions.allOf(
                            QCategory.category.brandAppId.eq(bandComId),
                            QCategory.category.deleted.in([false, null]),
                            QCategory.category.order.gt((categorySaveReq.order-1))
                    )
            ).toList()
            categorys.each { Category categoryGreater ->
                categoryGreater.order = categoryGreater.order + 1
            }
            categoryRepo.save(categorys)
        }
    }

}
