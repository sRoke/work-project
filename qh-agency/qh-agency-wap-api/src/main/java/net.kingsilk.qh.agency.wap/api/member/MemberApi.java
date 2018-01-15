package net.kingsilk.qh.agency.wap.api.member;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.kingsilk.qh.agency.wap.api.UniResp;
import net.kingsilk.qh.agency.wap.api.item.dto.ItemMiniInfoModel;
import net.kingsilk.qh.agency.wap.api.item.dto.ItemSearchReq;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by lit on 17/7/22.
 */
@Api(
        tags = "member",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "会员相关API"
)
@Path("/member")
@Component
public interface MemberApi {

    @ApiOperation(
            value = "注册用户",
            nickname = "注册用户",
            notes = "注册用户"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/register")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> register( @Context HttpServletRequest request);

}
