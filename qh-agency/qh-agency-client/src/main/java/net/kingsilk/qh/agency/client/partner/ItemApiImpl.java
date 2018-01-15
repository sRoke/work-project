package net.kingsilk.qh.agency.client.partner;

import net.kingsilk.qh.agency.api.UniPageResp;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.item.dto.ItemInfoModel;
import net.kingsilk.qh.agency.api.brandApp.item.dto.ItemMinInfo;
import net.kingsilk.qh.agency.api.brandApp.item.dto.ItemSearchReq;
import net.kingsilk.qh.agency.api.brandApp.partner.item.ItemApi;
import net.kingsilk.qh.agency.client.AbstractApi;
import org.springframework.web.client.RestOperations;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ItemApiImpl extends AbstractApi implements ItemApi {

    final String API_URL_PAGE
            = "/brandApp/{brandAppId}/partner/{partnerId}/item";


    final String API_URL_INFO
            = "/brandApp/{brandAppId}/partner/{partnerId}/item/{id}";

    String agencyUrl;
    RestOperations restTemplate;

    public ItemApiImpl(RestOperations restTemplate, String agencyUrl) {
        this.restTemplate = restTemplate;
        this.agencyUrl = agencyUrl;
    }

    @Override
    public UniResp<UniPageResp<ItemMinInfo>> searchItem(String brandAppId, String partnerId, ItemSearchReq req) {
        return null;
    }

    @Override
    public UniResp<ItemInfoModel> detail(String brandAppId, String partnerId, String id, String type) {
        return null;
    }

    @Override
    public Map<String, String> getDefaultApiUrls() {
        final Map<String, String> m = new HashMap<>();
        m.put("page", API_URL_PAGE);
        m.put("info", API_URL_INFO);
        final Map<String, String> defaultApiUrls = Collections.unmodifiableMap(m);
        return defaultApiUrls;
    }
}
