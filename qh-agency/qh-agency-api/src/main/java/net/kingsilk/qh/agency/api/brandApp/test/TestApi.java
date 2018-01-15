package net.kingsilk.qh.agency.api.brandApp.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 */
@Path("/test/{brandAppId}/test")
public interface TestApi {


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    String get();

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    String getTest();

    @Path("/test1")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    String getTest1();
}
