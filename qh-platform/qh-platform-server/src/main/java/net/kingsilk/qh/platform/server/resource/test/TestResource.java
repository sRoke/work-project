package net.kingsilk.qh.platform.server.resource.test;


import io.swagger.annotations.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import javax.ws.rs.*;
import java.util.*;

@Component
@Singleton
@Path("/test")
@Api
public class TestResource {


    @GET
    public String get() {

        return "Hello " + new Date();
    }
}
