package net.kingsilk.qh.agency.admin.resource.partner

import com.querydsl.core.types.dsl.Expressions
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import net.kingsilk.qh.agency.admin.api.UniResp
import net.kingsilk.qh.agency.admin.api.partner.PartnerApi
import net.kingsilk.qh.agency.admin.api.partner.dto.PartnerInfoResp
import net.kingsilk.qh.agency.admin.api.partner.dto.PartnerPageReq
import net.kingsilk.qh.agency.admin.api.partner.dto.PartnerPageResp
import net.kingsilk.qh.agency.admin.api.partner.dto.PartnerSaveReq
import net.kingsilk.qh.agency.admin.resource.partner.convert.PartnerConvert
import net.kingsilk.qh.agency.core.PartnerApplyStatusEnum
import net.kingsilk.qh.agency.domain.Partner
import net.kingsilk.qh.agency.domain.QPartner
import net.kingsilk.qh.agency.repo.AdcRepo
import net.kingsilk.qh.agency.repo.PartnerRepo
import net.kingsilk.qh.agency.service.AddrService
import net.kingsilk.qh.agency.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import org.springframework.util.Assert

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

/**
 *
 */

@Api(
        tags = "partner",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "员工管理相关API"
)
@Path("/partner")
@Component
class PartnerResource implements PartnerApi {
    @Autowired
    PartnerRepo partnerRepo

    @Autowired
    PartnerConvert partnerConvert

    @Autowired
    MemberService memberService

    @Autowired
    AddrService addrService

    @Autowired
    AdcRepo adcRepo
    //-------------------------查看渠道商信息---------------------------------//
    @ApiOperation(
            value = "渠道商信息",
            nickname = "渠道商信息",
            notes = "渠道商信息"
    )
    @Path("/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<PartnerInfoResp> info(@QueryParam(value = "id") String id) {
        Partner partner = partnerRepo.findOne(id)
        PartnerInfoResp resp = partnerConvert.partnerInfoConvert(partner)
        return new UniResp<PartnerInfoResp>(status: 200, data: resp)

    }

    //-------------------------保存渠道商信息---------------------------------//
    @ApiOperation(
            value = "保存渠道商信息",
            nickname = "保存渠道商信息",
            notes = "保存渠道商信息"
    )
    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> save(PartnerSaveReq partnerSaveReq) {

    }

    //-------------------------更新渠道商信息---------------------------------//
    @ApiOperation(
            value = "更新渠道商信息",
            nickname = "更新渠道商信息",
            notes = "更新渠道商信息"
    )
    @Path("/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> update( PartnerSaveReq partnerSaveReq) {
        Partner partner = partnerRepo.findOne(partnerSaveReq.id)
        Assert.notNull(partner, "当前经销商不存在")
//        partner.parent = partnerRepo.findOne(partnerSaveReq.parentId)
        partner.phone = partnerSaveReq.phone
        partner.realName = partnerSaveReq.realName
        partner.adc = adcRepo.findOneByNo(partnerSaveReq.shopAddr)
        partnerRepo.save(partner)
        //TODO
        return new UniResp<String>(status: 200, data: "保存成功")
    }

    //-------------------------获取渠道商列表---------------------------------//
    @ApiOperation(
            value = "获取渠道商列表",
            nickname = "获取渠道商列表",
            notes = "获取渠道商列表"
    )
    @Path("/page")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<PartnerPageResp> page(
            @BeanParam PartnerPageReq partnerPageReq,
            @QueryParam(value = "source") String source) {

        PageRequest pageRequest = new PageRequest(
                partnerPageReq.curPage,
                partnerPageReq.pageSize,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))
        Page<Partner> partners
        if (source == "APPLY") {
            partners = partnerRepo.findAll(
                    Expressions.allOf(
                            QPartner.partner.deleted.in([false, null]),
                            QPartner.partner.partnerApplyStatus.ne(PartnerApplyStatusEnum.NORMAL)
                    ),
                    pageRequest
            )
        } else if (source == "PASS") {
            partners = partnerRepo.findAll(
                    Expressions.allOf(
                            QPartner.partner.deleted.in([false, null]),
                            QPartner.partner.partnerApplyStatus.eq(PartnerApplyStatusEnum.NORMAL)
                    ),
                    pageRequest
            )
        } else {
            return new UniResp<PartnerPageResp>(status: 200, data: "参数错误")
        }
        Page<PartnerPageResp> infoPage = partners.map({ Partner partner ->
            PartnerPageResp resp = new PartnerPageResp()
            resp.id = partner.id
            resp.realName = partner.realName
            resp.partnerApplyStatus = partner.partnerApplyStatus?.desp
            resp.seq = partner.seq
            resp.phone = partner.phone
            resp.adc = partner.adc?.no
            resp.shopAddr = addrService.getAdcInfo(partner.adc?.no)
            resp.shopAddr = partner.adc?.no
            resp.disabled = partner.disabled
            resp.createDate = partner.dateCreated.toLocaleString()
            resp.partnerType = partner.partnerTypeEnum?.desp
            return resp
        })
        return new UniResp<PartnerPageResp>(status: 200, data: infoPage)
    }

    //-------------------------渠道商审核---------------------------------//
    @ApiOperation(
            value = "渠道商审核",
            nickname = "渠道商审核",
            notes = "渠道商审核"
    )
    @Path("/review")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> review(@QueryParam(value = "id") String id,
                           @QueryParam(value = "status") Boolean status) {
        Partner partner = partnerRepo.findOne(id)
        Assert.notNull(partner, "参数错误")
        if (status) {
            partner.partnerApplyStatus = PartnerApplyStatusEnum.NORMAL
            memberService.register(partner.userId, partner)
        } else {
            partner.partnerApplyStatus = PartnerApplyStatusEnum.REJECT
        }
        partnerRepo.save(partner)
        return new UniResp<String>(status: 200, data: "审核成功")
    }

    //-------------------------渠道商禁用---------------------------------//
    @ApiOperation(
            value = "渠道商禁用",
            nickname = "渠道商禁用",
            notes = "渠道商禁用"
    )
    @Path("/changeStatus")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> changeStatus(@QueryParam(value = "id") String id,
                                 @QueryParam(value = "status") Boolean status) {
        Partner partner = partnerRepo.findOne(id)
        Assert.notNull(partner, "参数错误")
        partner.disabled = status
        partnerRepo.save(partner)
        return new UniResp<String>(status: 200, data: "操作成功")
    }

}
