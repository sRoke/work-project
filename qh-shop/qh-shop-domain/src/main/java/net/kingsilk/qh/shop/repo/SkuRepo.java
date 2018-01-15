package net.kingsilk.qh.shop.repo;

import net.kingsilk.qh.shop.domain.Sku;

import java.util.List;

public interface SkuRepo extends BaseRepo<Sku,String>{
    List<Sku> findAllByItemIdAndDeleted(String itemId,Boolean deleted);
}
