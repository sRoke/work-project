package net.kingsilk.qh.agency.es.repo;

import net.kingsilk.qh.agency.es.domain.EsSkuStore;

public interface EsSkuStoreRepository extends BaseRepository<EsSkuStore, String> {

    EsSkuStore findOneBySkuStoreId(String skuStoreId);
}
