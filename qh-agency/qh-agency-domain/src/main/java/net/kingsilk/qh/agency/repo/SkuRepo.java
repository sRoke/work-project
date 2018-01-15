package net.kingsilk.qh.agency.repo;

import net.kingsilk.qh.agency.domain.Item;
import net.kingsilk.qh.agency.domain.Sku;

import java.util.List;

/**
 *
 */
public interface SkuRepo extends BaseRepo<Sku, String> {


    List<Sku> findAllByItemAndDeleted(Item item, boolean deleted);
}