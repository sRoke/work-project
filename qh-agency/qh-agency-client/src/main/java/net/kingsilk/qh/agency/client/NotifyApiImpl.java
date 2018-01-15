package net.kingsilk.qh.agency.client;

import net.kingsilk.qh.agency.api.brandApp.notify.NotifyApi;
import net.kingsilk.qh.agency.api.brandApp.notify.dto.NotifyQhPayReq;
import net.kingsilk.qh.agency.api.brandApp.notify.dto.NotifyShopInfoReq;
import org.springframework.http.*;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NotifyApiImpl extends AbstractApi implements NotifyApi {

    final String API_URL_SHOP = "/notify/shop";

    RestOperations restTemplate;
    String agencyUrl;

    public NotifyApiImpl(RestOperations restTemplate, String agencyUrl) {
        this.restTemplate = restTemplate;
        this.agencyUrl = agencyUrl;
    }

    @Override
    public String qhPay(NotifyQhPayReq req) {
        //TODO 暂时用不到
        return null;
    }


    @Override
    public String qhShop(NotifyShopInfoReq req) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<NotifyShopInfoReq> requestEntity = new HttpEntity<>(req, headers);

        String url = agencyUrl + getApiUrl("shop");

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("price", req.getPrice())
                .queryParam("skuIds", req.getSkuIds())
                .queryParam("shopInfo", req.getShopInfo())
                .build()
                .toUri();

        ResponseEntity<String> infoResp = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                requestEntity,
                String.class);
        return infoResp.getBody();
    }

    @Override
    public Map<String, String> getDefaultApiUrls() {
        final Map<String, String> m = new HashMap<>();
        m.put("shop", API_URL_SHOP);
        final Map<String, String> defaultApiUrls = Collections.unmodifiableMap(m);
        return defaultApiUrls;
    }
}
