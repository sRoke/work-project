package net.kingsilk.qh.agency.admin.api.authorities;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.kingsilk.qh.agency.admin.api.UniResp;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@RestController
@RequestMapping("/authorities")
@Api(
        tags = "authorities",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "权限管理相关API"
)
public interface AuthoritiesApi  {
    @ApiOperation(
            value = "当前用户的权限信息",
            nickname = "当前用户的权限信息",
            notes = "当前用户的权限信息"
    )
    @Path("/getAuthorities")
    @GET
    @Produces( MediaType.APPLICATION_JSON)
    UniResp<UserDetails> getAuthorities();


}
