package net.kingsilk.qh.agency.api.brandApp.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 */
@Path("/brandApp/{brandAppId}/partner/{testId}/aaa")
public interface TestLitApi {


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    String get();

}
