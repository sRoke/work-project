package net.kingsilk.qh.shop.api.controller.mall.member;


import io.swagger.annotations.*;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.member.dto.MemberMinPageResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.member.dto.MemberModel;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.member.dto.MemberPageReq;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Api(
        tags = "member",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "会员相关api")
@Path("/brandApp/{brandAppId}/shop/{shopId}/member")
@Component
public interface MemberApi {


    @ApiOperation(
            value = "新增会员",
            nickname = "新增会员",
            notes = "新增会员"
    )
    @Path("/add")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    UniResp<Map<String, String>> add(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId")
                    String shopId,
            @ApiParam(value = "微信第三方平台的 APP ID")
            @QueryParam(value = "wxComAppId")
                    String wxComAppId,
            @ApiParam(value = "微信公众号 APP ID")
            @QueryParam(value = "wxMpAppId")
                    String wxMpAppId
    );

    //只能给店主停用member
    @ApiOperation(
            value = "删除会员",
            nickname = "删除会员",
            notes = "删除会员"
    )
    @Path("/{memberId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    UniResp<String> disable(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId")
                    String shopId,
            @ApiParam(value = "会员ID")
            @PathParam(value = "memberId")
                    String memberId
    );

    @ApiOperation(
            value = "更新会员",
            nickname = "更新会员",
            notes = "更新会员"
    )
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    UniResp<String> update(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId")
                    String shopId,
            @ApiParam(value = "会员id")
            @PathParam(value = "id")
                    String id,
            @ApiParam(value = "会员信息")
            @BeanParam
                    MemberModel memberReq
    );

    @ApiOperation(
            value = "会员信息",
            nickname = "会员信息",
            notes = "会员信息"
    )
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    UniResp<MemberModel> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId")
                    String shopId,
            @ApiParam(value = "商品ID")
            @PathParam(value = "id")
                    String id
    );


    @ApiOperation(
            value = "会员信息分页",
            nickname = "会员信息分页",
            notes = "会员信息分页"
    )
    @Path("/page")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    UniResp<UniPageResp<MemberMinPageResp>> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId")
                    String shopId,
            @BeanParam
                    MemberPageReq pageReq);
//            @ApiParam(value = "每页多少个记录,最大100")
//            @QueryParam("size")
//            @DefaultValue("10")
//                    int size,
//            @ApiParam(value = "默认，从0开始")
//            @QueryParam("page")
//            @DefaultValue("0")
//                    int page,
//            @ApiParam("是否启用，如：'true'")
//            @QueryParam("enable")
//            @DefaultValue("true")
//                    Boolean enable,
//            @ApiParam("排序条件，如：'dateCreated,desc'")
//            @QueryParam("sort")
//            @DefaultValue("dateCreated,desc")
//                    List<String> sort,
//            @ApiParam("搜索关键字，包括手机号和名字")
//            @QueryParam("sort")
//            @DefaultValue("dateCreated,desc")
//                    String keyword
//    );


    @ApiOperation(
            value = "会员Id",
            nickname = "会员Id",
            notes = "会员Id"
    )
    @Path("/user/{userId}")
    @GET
//    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    String getMemberIdByUserId(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId")
                    String shopId,
            @ApiParam(value = "商品ID")
            @PathParam(value = "userId")
                    String userId
    );

    @ApiOperation(
            value = "是否是会员",
            nickname = "是否是会员",
            notes = "是否是会员"
    )
    @Path("/isMember")
    @GET
//    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    UniResp<String> isMember(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId")
                    String shopId
    );

}
