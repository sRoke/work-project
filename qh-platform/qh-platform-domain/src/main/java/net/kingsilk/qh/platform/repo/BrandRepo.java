package net.kingsilk.qh.platform.repo;

import net.kingsilk.qh.platform.domain.*;

public interface BrandRepo extends BaseRepo<Brand, String> {
    public void deleteBrandByBrandComIdAndAndId(String brandComId, String brandId);

    public Brand findBrandByBrandComIdAndAndId(String brandComId, String brandId);
}
