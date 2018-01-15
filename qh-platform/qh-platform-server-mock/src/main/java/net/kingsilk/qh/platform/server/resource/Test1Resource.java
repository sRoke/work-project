package net.kingsilk.qh.platform.server.resource;

import io.swagger.annotations.*;
import net.kingsilk.qh.platform.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.stereotype.*;

import javax.servlet.http.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

/**
 *
 */
@Component
//@Singleton
@Api
@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class Test1Resource {

    @Context
    private HttpServletRequest req;

    @Autowired
    private Map<String, String> mm;

    @ApiOperation("b")
    @GET
    @Path("/b")
    public String b() {
        return "b @ resource " + new Date();
    }

    @ApiOperation("c")
    @GET
    @Path("/c")
    public String c() {
        return "c @ resource " + new Date();
    }

    @Path("/t2")
    @GET
    @PreAuthorize("denyAll")
    public UniResp<String> t2() {
        UniResp<String> resp = new UniResp<>();
        resp.setData("t2");
        return resp;
    }

    @Path("/t3")
    @GET
    public UniResp<String> t3() {
        throw new IllegalArgumentException("===========whosyourdaddy");
    }


    @Path("/t4")
    @GET
    public UniResp<String> t4() {
        UniResp<String> resp = new UniResp<>();
        resp.setData("t4"
                + "\n req = " + req
                + "\n mm = " + mm
                + "\n this = " + this
        );
        return resp;
    }
}
