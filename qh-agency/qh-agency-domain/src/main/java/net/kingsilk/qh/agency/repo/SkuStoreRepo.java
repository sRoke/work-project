package net.kingsilk.qh.agency.repo;

import net.kingsilk.qh.agency.domain.Sku;
import net.kingsilk.qh.agency.domain.SkuStore;

/**
 * Created by lit on 17/7/15.
 */
public interface SkuStoreRepo extends BaseRepo<SkuStore, String> {
    SkuStore findBySku(Sku sku);
}
