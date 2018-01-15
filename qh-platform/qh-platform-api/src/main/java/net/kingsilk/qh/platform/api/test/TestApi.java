package net.kingsilk.qh.platform.api.test;

import io.swagger.annotations.*;
import net.kingsilk.qh.platform.api.*;
import org.springframework.security.access.prepost.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * 品牌商相关API。
 */
@Api
@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public interface TestApi {


    @ApiOperation(
            value = "测试 @PreAuthorize on interface"
    )
    @GET
    @Path("/p1")
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("permitAll")
    UniResp<String> p1();


    @ApiOperation(
            value = "测试 @PreAuthorize on interface"
    )
    @GET
    @Path("/p2")
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("denyAll")
    UniResp<String> p2();


    @ApiOperation(
            value = "测试 @PreAuthorize on 实现类上"
    )
    @GET
    @Path("/c1")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> c1();


    @ApiOperation(
            value = "测试 @PreAuthorize on 实现类上"
    )
    @GET
    @Path("/c2")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> c2();

    @ApiOperation(
            value = "测试 java.util.Optional"
    )
    @POST
    @Path("/opt")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<MyTestBean> opt(MyTestBean req);


}
