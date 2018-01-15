package net.kingsilk.qh.agency.es.repo;


import net.kingsilk.qh.agency.es.domain.EsItem;

/**
 *
 */
public interface EsItemRepository extends BaseRepository<EsItem, String> {

    EsItem findOneByItemId(String itemId);
}
