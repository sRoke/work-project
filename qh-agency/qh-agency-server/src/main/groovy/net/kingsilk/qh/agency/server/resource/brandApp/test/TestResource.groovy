package net.kingsilk.qh.agency.server.resource.brandApp.test

import net.kingsilk.qh.agency.api.brandApp.test.TestApi
import org.springframework.stereotype.Component

import javax.ws.rs.Path

/**
 *
 */
@Path("/test/{brandAppId}/test")
@Component
class TestResource implements TestApi {


    @Override
    String get() {
        return "testApi : get : " + new Date();
    }

    @Override
    String getTest() {
        return "testApi : getTest : " + new Date();
    }

    @Override
    String getTest1() {
        return "testApi : getTest1 : " + new Date();
    }
}
