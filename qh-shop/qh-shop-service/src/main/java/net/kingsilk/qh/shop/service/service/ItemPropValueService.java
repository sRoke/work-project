package net.kingsilk.qh.shop.service.service;

import com.querydsl.core.types.dsl.Expressions;
import groovy.transform.CompileStatic;
import net.kingsilk.qh.shop.domain.ItemPropValue;
import net.kingsilk.qh.shop.domain.QItemPropValue;
import net.kingsilk.qh.shop.repo.ItemPropValueRepo;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * Created by yanfq on 17-4-6.
 */

@Service
@CompileStatic
public class ItemPropValueService {

    @Autowired
    ItemPropValueRepo itemPropValueRepo;

    public List<ItemPropValue> search(String itemPropId, String bandComId) {
        Iterator<ItemPropValue> list = itemPropValueRepo.findAll(
                Expressions.allOf(
                        QItemPropValue.itemPropValue.deleted.eq(false),
                        bandComId!=null ? QItemPropValue.itemPropValue.brandAppId.eq(bandComId) : null,
                        itemPropId!=null ? QItemPropValue.itemPropValue.itemPropId.eq(itemPropId) : null
                )).iterator();

        return IteratorUtils.toList(list);
    }
}
