package net.kingsilk.qh.shop.server.controller.test;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Component
public class TestResource {


    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String get() {
        return "hello " + new Date();
    }
}
