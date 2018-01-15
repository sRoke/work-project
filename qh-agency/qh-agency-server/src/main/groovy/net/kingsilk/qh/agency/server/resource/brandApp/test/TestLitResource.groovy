package net.kingsilk.qh.agency.server.resource.brandApp.test

import net.kingsilk.qh.agency.api.UniResp
import net.kingsilk.qh.agency.api.common.dto.SkuInfoModel
import net.kingsilk.qh.agency.msg.impl.esSkuStore.sync.SyncTrigger
import net.kingsilk.qh.agency.repo.ItemRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.util.StringUtils
import org.springframework.web.client.RestOperations
import org.springframework.web.util.UriComponentsBuilder

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.GET
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType

/**
 * Created by lit on 17/7/26.
 */
@Path("/brandApp/{brandAppId}/partner/{testId}/aaa")
@Component
class TestLitResource {

    @Autowired
    ItemRepo itemRepo

    @Autowired
    MongoTemplate mongoTemplate

    @Context
    HttpServletRequest httpServletRequest
    @Autowired
    RestOperations restTemplate

    @Autowired
    SyncTrigger syncTrigger

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    String get() {

        String at = httpServletRequest.getHeader("Authorization");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        headers.set("Authorization", at);
        headers.set("BrandAppp-Id", "59782691f8fdbc1f9b2c4323")
        RequestEntity requestEntity = new RequestEntity(null, headers, null, null);
        String url = "https://agency.kingsilk.net/local/16000/rs/api/brandApp/{brandAppId}/partner/{partnerId}/skuStore/skuDetail";
        Map<String, String> map = new LinkedHashMap<>();
        map.put("brandAppId", "59782691f8fdbc1f9b2c4323");
        map.put("partnerId", "123");
        MultiValueMap<String, String> mup = new LinkedMultiValueMap<>();
        if (!StringUtils.isEmpty("599e3af20f083055e03d6701")) {
            mup.set("skuId", "599e3af20f083055e03d6701");
        }

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(mup)
                .buildAndExpand(map)
                .toUri();
        ResponseEntity<UniResp<SkuInfoModel>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<UniResp<SkuInfoModel>>() {
                });
        return responseEntity.getBody();



        SyncTrigger

//        @Autowired
//        ItemRepo itemRepo
//
//        @Autowired
//        MongoTemplate mongoTemplate

//
//        @Override
//        String get() {
//            List<Item> items = itemRepo.findAll(
//                    Expressions.allOf(
////                        QItem.item.brandAppId.eq(""),
//                            QItem.item.deleted.in([false, null])
//                    )
//            ).asList()
//
//            Item item = mongoTemplate.findById("111", Item)
//            Category category = mongoTemplate.findById("", Category);
//
//            return items.size()
//        }


    }

    @GET
    @Path("/syncTrigger")
    @Produces(MediaType.APPLICATION_JSON)
    String syncTrigger() {

        syncTrigger.run()
        return "SUCCESS"
    }
}