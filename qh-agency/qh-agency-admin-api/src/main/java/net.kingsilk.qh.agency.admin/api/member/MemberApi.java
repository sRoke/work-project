package net.kingsilk.qh.agency.admin.api.member;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.kingsilk.qh.agency.admin.api.UniResp;
import net.kingsilk.qh.agency.admin.api.member.dto.MemberInfoResp;
import net.kingsilk.qh.agency.admin.api.member.dto.MemberPageReq;
import net.kingsilk.qh.agency.admin.api.member.dto.MemberPageResp;
import net.kingsilk.qh.agency.admin.api.member.dto.MemberSaveReq;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 *
 */
@Api(
        tags = "member",
        description = "会员管理相关API"
)
@Path("/api/member")
public interface MemberApi {

    @ApiOperation(
            value = "会员信息",
            notes = "会员信息")
    @Path("/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<MemberInfoResp> info(
            @QueryParam(value = "id")
                    String id
    );

    @ApiOperation(
            value = "保存或更新会员信息",
            nickname = "保存或更新会员信息",
            notes = "保存或更新会员信息"
    )
    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> save(@BeanParam MemberSaveReq req);

    @ApiOperation(
            value = "会员分页信息",
            nickname = "会员分页信息",
            notes = "会员分页信息"
    )

    @Path("/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> update(@BeanParam MemberSaveReq req);

    @ApiOperation(
            value = "会员分页信息",
            nickname = "会员分页信息",
            notes = "会员分页信息"
    )


    @Path("/page")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<MemberPageResp> page(@BeanParam MemberPageReq memberPageReq);

    @ApiOperation(
            value = "禁用或启用会员",
            notes = "禁用或启用会员"
    )
    @Path("/enable")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> enable(@QueryParam(value = "id") String id,
                           @QueryParam(value = "disabled") Boolean disabled);

    @ApiOperation(
            value = "禁用或启用会员",
            nickname = "禁用或启用会员",
            notes = "禁用或启用会员"
    )
    @Path("/export")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> exportExcel(@Context HttpServletResponse response) throws Exception;

    @ApiOperation(
            value = "上传会员信息",
            nickname = "上传会员信息",
            notes = "上传会员信息"
    )
    @Path("/upload")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> readExcel(@BeanParam MultipartFile file) throws IOException;

    @ApiOperation(
            value = "查询手机号是否重复",
            nickname = "查询手机号是否重复",
            notes = "查询手机号是否重复"
    )
    @Path("/queryPhone")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Boolean> queryPhone(@PathParam(value = "phone") String phone, @PathParam(value = "id") String id);

}
