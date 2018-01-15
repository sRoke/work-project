package net.kingsilk.qh.agency.wap.controller

import net.kingsilk.qh.agency.domain.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate

/**
 * Created by lit on 17/7/20.
 */
class TestLitController {
    @Autowired
    MongoTemplate mongoTemplate

    void initcategory(){
        Category category=new Category()
        category.name="111"
        category.icon="111"
        category.desp="111"
        mongoTemplate.save(category)
    }

}
