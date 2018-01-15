package net.kingsilk.qh.shop.es.repo;


import net.kingsilk.qh.shop.es.domain.EsItem;

/**
 *
 */

public interface EsItemRepository extends BaseRepository<EsItem, String> {

    EsItem findOneByItemId(String itemId);
}
