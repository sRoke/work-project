package net.kingsilk.qh.shop.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created by lit on 17/12/4.
 */
public class JacksonTest01 {

    @Test
    public void testJsonToMap01() throws IOException {

        ObjectMapper om = new ObjectMapper();
        String json = "{\"a\":{\"a1\":111,\"a2\":true},\"b\":{\"b1\":\"bbb1\",\"b2\":\"bbb2\"}}";

        Map<String, Map<String, String>> map = om.readValue(json, Map.class);

        System.out.println(map);

    }
}
