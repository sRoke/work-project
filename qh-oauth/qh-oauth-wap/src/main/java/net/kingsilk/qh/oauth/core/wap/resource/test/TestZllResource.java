package net.kingsilk.qh.oauth.core.wap.resource.test;

import net.kingsilk.qh.oauth.api.ErrStatusException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;

/**
 *
 */
@Path("/test/zll")
public class TestZllResource {

    @GET
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Object home(

    ) {
        return " /test/zll : " + new Date();
    }


    @GET
    @Path("/ex")
    @Consumes(MediaType.APPLICATION_JSON)
    public Object ex() {
        throw new ErrStatusException(9988, "hhhhh");
    }

}
