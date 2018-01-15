package net.kingsilk.qh.agency.service

import com.querydsl.core.types.dsl.Expressions
import groovy.transform.CompileStatic
import net.kingsilk.qh.agency.domain.QItemPropValue
import net.kingsilk.qh.agency.repo.ItemPropValueRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by yanfq on 17-4-6.
 */

@Service
@CompileStatic
class ItemPropValueService {

    @Autowired
    ItemPropValueRepo itemPropValueRepo

    def search(String itemPropId, String bandComId) {
        def list = itemPropValueRepo.findAll(
                Expressions.allOf(
                        QItemPropValue.itemPropValue.deleted.in([null, false]),
                        bandComId ? QItemPropValue.itemPropValue.brandAppId.eq(bandComId) : null,
                        itemPropId ? QItemPropValue.itemPropValue.itemProp.id.eq(itemPropId) : null
                ))
        return list
    }
}
