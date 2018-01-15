package net.kingsilk.qh.agency.admin

import org.junit.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

/**
 * Created by tpx on 17-3-20.
 */
//@RunWith(SpringRunner)
@SpringBootTest
class TestTpx {


    public final static HOST = "http://localhost:10070/api/"
    RestTemplate restTemplate = new RestTemplate()

    @Test
    void Item_page() {
        String url = HOST + "item/page";

        HttpHeaders headers = new HttpHeaders();
        //headers.setAccept([MediaType.APPLICATION_JSON_UTF8_VALUE])
        headers.set("Company-Id", "test");
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .toUri()
                .toString()

        HttpEntity<String> reqEntity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(uri,
                HttpMethod.GET, reqEntity, String.class);

        println(respEntity?.body)

    }


    @Test
    void Item_save() {
        String url = HOST + "item/save";

        HttpHeaders headers = new HttpHeaders();
        //headers.setAccept([MediaType.APPLICATION_JSON_UTF8_VALUE])
        headers.setContentType(MediaType.APPLICATION_JSON)
        headers.set("Company-Id", "test");
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .toUri()
                .toString()

        String str = '''

            {
              "code": "string",
              "desp": "string",
              "detail": "string",
              "id": "string",
              "imgs": [
                "string"
              ],
              "props": [
                {
                  "id": "string",
                  "itemProp": {
                    "code": "string",
                    "id": "string",
                    "memName": "string",
                    "memo": "string",
                    "name": "string",
                    "type": "LIST",
                    "unit": "string"
                  },
                  "propValue": {
                    "code": "string",
                    "color": "string",
                    "id": "string",
                    "img": "string",
                    "memo": "string",
                    "name": "string"
                  }
                }
              ],
              "specs": [
                {
                  "id": "string",
                  "itemProp": {
                    "code": "string",
                    "id": "string",
                    "memName": "string",
                    "memo": "string",
                    "name": "string",
                    "type": "LIST",
                    "unit": "string"
                  },
                  "itemPropValues": [
                    {
                      "code": "string",
                      "color": "string",
                      "id": "string",
                      "img": "string",
                      "memo": "string",
                      "name": "string"
                    }
                  ]
                }
              ],
              "status": "EDITING",
              "title": "string"
            }
        '''
        HttpEntity<String> reqEntity = new HttpEntity<String>(str, headers);
        ResponseEntity<String> respEntity = restTemplate.exchange(uri,
                HttpMethod.POST, reqEntity, String.class);

        println(respEntity)
    }
}
