package net.kingsilk.qh.platform.server.resource;

import io.swagger.annotations.*;
import io.swagger.jaxrs.*;
import net.kingsilk.qh.platform.api.*;
import net.kingsilk.qh.platform.api.test.*;
import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
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
@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class TestResource implements TestApi, ApplicationContextAware {

    @Context
    private HttpServletRequest req;

    @Autowired
    private Map<String, String> mm;


    private ApplicationContext appCtx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appCtx = applicationContext;

    }


    @Override
    public UniResp<String> p1() {
        UniResp<String> resp = new UniResp<>();
        resp.setData("p1");
        return resp;
    }

    @Override
    public UniResp<String> p2() {
        UniResp<String> resp = new UniResp<>();
        resp.setData("p1");
        return resp;
    }

    @Override
    @PreAuthorize("permitAll")
    public UniResp<String> c1() {
        UniResp<String> resp = new UniResp<>();
        resp.setData("c1 : " +
                "\n req=" + req +
                "\n mm = " + mm +
                "\n appCtx = " + appCtx +
                "\n this = " + this
        );
        return resp;
    }

    @Override
    @PreAuthorize("denyAll")
    public UniResp<String> c2() {
        UniResp<String> resp = new UniResp<>();
        resp.setData("c2");
        return resp;
    }

    /*
    curl -v -X PATCH \
        -H "Content-Type: application/json" \
        -d '{"name":"zhang3","age":11,"street":"111","hobbies":["aaa", "bbb"],"birthday":1501511001513}' \
        http://localhost:10200/api/test/opt

    {"data":{"name":"zhang3","age":11,"street":"111","hobbies":["aaa","bbb"],"birthday":1501511001513},"timestamp":1501511223783}


    curl -v -X PATCH \
        -H "Content-Type: application/json" \
        -d '{"name":"zhang3","age":null,"street":null,"hobbies":null,"birthday":null}' \
        http://localhost:10200/api/test/opt

    {"data":{"name":"zhang3"},"timestamp":1501513592701}

    curl -v -X PATCH \
        -H "Content-Type: application/json" \
        -d '{"name":"zhang3"}' \
        http://localhost:10200/api/test/opt

    {"data":{"name":"zhang3"},"timestamp":1501511189788}
     */
    @Override
    @PATCH
    @Path("/opt")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UniResp<MyTestBean> opt(MyTestBean req) {
        UniResp<MyTestBean> resp = new UniResp<>();
        resp.setData(req);
        resp.setTimestamp(new Date());

        System.out.println("age         : "
                + req.getAge().isPresent() + " : "
                + (req.getAge().isPresent() ? req.getAge().get() : "N/A"));

        System.out.println("street      : "
                + req.getStreet().isPresent() + " : "
                + (req.getStreet().isPresent() ? req.getStreet().get() : "N/A"));

        System.out.println("hobbies     : "
                + req.getHobbies().isPresent() + " : "
                + (req.getHobbies().isPresent() ? req.getHobbies().get() : "N/A"));

        System.out.println("birthday    : "
                + req.getBirthday().isPresent() + " : "
                + (req.getBirthday().isPresent() ? req.getBirthday().get() : "N/A"));

        return resp;
    }


}
