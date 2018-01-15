package net.kingsilk.qh.shop.server.resource.brandApp.shop.skuStoreLog;

import net.kingsilk.qh.shop.api.brandApp.shop.skuStoreLog.SkuStoreLogApi;
import net.kingsilk.qh.shop.api.brandApp.shop.skuStoreLog.dto.SkuStoreLogResp;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class SkuStoreLogResource implements SkuStoreLogApi {


    @Override
    public UniResp<SkuStoreLogResp> info(
            String brandAppId,
            String shopId,
            String skuStoreLogId) {

        return null;
    }

    @Override
    public UniResp<UniPageResp<SkuStoreLogResp>> page(String brandAppId, String shopId, int size, int page, List<String> sort, String keyWord) {
        return null;
    }
}
