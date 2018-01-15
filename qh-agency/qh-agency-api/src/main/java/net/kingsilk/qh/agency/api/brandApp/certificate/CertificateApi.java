package net.kingsilk.qh.agency.api.brandApp.certificate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniPageReq;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.certificate.dto.CertificateInfoResp;
import net.kingsilk.qh.agency.api.brandApp.certificate.dto.CertificatePageResp;
import net.kingsilk.qh.agency.api.brandApp.certificate.dto.CertificateSaveReq;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by lit on 17/8/29.
 */
@Api(
        tags = "certificate",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "授权书相关API"
)
@Path("/brandApp/{brandAppId}/certificate")
@Component
public interface CertificateApi {
    @ApiOperation(
            value = "授权书分页列表",
            nickname = "授权书分页列表",
            notes = "授权书分页列表"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('PARTNERLICENSE_R')")
    UniResp<CertificatePageResp> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @BeanParam UniPageReq uniPageReq
    );

    @ApiOperation(
            value = "查看授权书信息",
            nickname = "查看授权书信息",
            notes = "查看授权书信息"
    )
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('PARTNERLICENSE_R')")
    UniResp<CertificateInfoResp> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "id") String id
    );


    @ApiOperation(
            value = "保存授权书",
            nickname = "保存授权书",
            notes = "保存授权书"
    )
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('PARTNERLICENSE_U')")
    UniResp<String> save(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            CertificateSaveReq certificateSaveReq);

    @ApiOperation(
            value = "更新授权书",
            nickname = "更新授权书",
            notes = "更新授权书"
    )
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('PARTNERLICENSE_U')")
    UniResp<String> update(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "id") String id,
            CertificateSaveReq certificateSaveReq);

    @ApiOperation(
            value = "更改状态",
            nickname = "更改状态",
            notes = "更改状态"
    )
    @PUT
    @Path("/{id}/changeStatus")
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('PARTNERLICENSE_D')")
    UniResp<String> changeStatus(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "状态")
            @QueryParam(value = "status") String status);

}
