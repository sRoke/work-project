package net.kingsilk.qh.agency.wap.resource.partner

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import net.kingsilk.qh.agency.core.PartnerApplyStatusEnum
import net.kingsilk.qh.agency.core.PartnerTypeEnum
import net.kingsilk.qh.agency.domain.Partner
import net.kingsilk.qh.agency.domain.PartnerStaff
import net.kingsilk.qh.agency.domain.QPartner
import net.kingsilk.qh.agency.repo.AdcRepo
import net.kingsilk.qh.agency.repo.PartnerRepo
import net.kingsilk.qh.agency.repo.PartnerStaffRepo
import net.kingsilk.qh.agency.security.BrandAppIdFilter
import net.kingsilk.qh.agency.service.CommonService
import net.kingsilk.qh.agency.service.MemberService
import net.kingsilk.qh.agency.service.SecService
import net.kingsilk.qh.agency.service.UserService
import net.kingsilk.qh.agency.wap.api.UniResp
import net.kingsilk.qh.agency.wap.api.partner.PartnerApi
import net.kingsilk.qh.agency.wap.api.partner.dto.PartnerApplyReq
import net.kingsilk.qh.agency.wap.api.partner.dto.PartnerInfoResp
import net.kingsilk.qh.agency.wap.api.partner.dto.PartnerUpdateReq
import net.kingsilk.qh.agency.wap.resource.partner.convert.PartnerConvert
import org.springframework.beans.factory.annotation.Autowired

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType

import static com.querydsl.core.types.dsl.Expressions.allOf

/**
 *
 */
@Api(
        tags = "partner",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "渠道商管理API"
)
@Path("/partner")
public class PartnerResource implements PartnerApi {

    @Autowired
    MemberService memberService
    @Autowired
    PartnerRepo partnerRepo

    @Autowired
    PartnerConvert partnerConvert

    @Autowired
    UserService userService
    @Autowired
    PartnerStaffRepo partnerStaffRepo

    @Autowired
    CommonService commonService

    @Autowired
    SecService secService

    @Autowired
    AdcRepo adcRepo

    //-------------------------渠道商申请---------------------------------//
    @ApiOperation(
            value = "渠道商申请",
            nickname = "渠道商申请",
            notes = "渠道商申请"
    )
    @Path("/apply")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> apply(
            PartnerApplyReq partnerApplyReq) {
//        PartnerStaff member = memberService.getCurPartnerStaff()
        String userId=secService.curUserId()
        String brandId = BrandAppIdFilter.getBrandAppId()
        Partner partner = partnerRepo.findOne(
                allOf(
                        QPartner.partner.userId.eq(userId),
                        QPartner.partner.brandId.eq(brandId)
                )
        )
        if (!partner) {
            partner = new Partner()
        }
        partner.userId = userId
        partner.brandId = brandId
        partner.seq = commonService.getDateString()
        partner.realName=partnerApplyReq.realName
        partner.idNo=partnerApplyReq.idNo
        if (partnerApplyReq.invitationCode) {
            Partner parent = partnerRepo.findByInvitationCode(partnerApplyReq.invitationCode)
            partner.parentId = parent?.id
        }
        def code=commonService.genRandomNum()
        def hasCodeUsed=partnerRepo.findByInvitationCode(code)
        while(hasCodeUsed){
            hasCodeUsed=partnerRepo.findByInvitationCode(commonService.genRandomNum())
        }
        partner.invitationCode=commonService.genRandomNum()
        partner.partnerTypeEnum = PartnerTypeEnum.valueOf(partnerApplyReq.partnerType)
        partner.phone = partnerApplyReq.phone
        partner.adc = adcRepo.findOneByNo(partnerApplyReq.shopAddr)
        partner.partnerApplyStatus=PartnerApplyStatusEnum.APPLYING
        partnerRepo.save(partner)
//        member.partner=partner
//        partnerStaffRepo.save(member)
        return new UniResp<String>(status: 200, data: "保存成功!")
    }

    //-------------------------检查渠道商信息---------------------------------//
    @ApiOperation(
            value = "检查渠道商信息",
            nickname = "检查渠道商信息",
            notes = "检查渠道商信息"
    )
    @Path("/check")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> check(@Context HttpServletRequest request) {

    }

    //-------------------------渠道商信息---------------------------------//
    @ApiOperation(
            value = "渠道商信息",
            nickname = "渠道商信息",
            notes = "渠道商信息"
    )
    @Path("/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<PartnerInfoResp> info() {
//        PartnerStaff member = memberService.getCurPartnerStaff()
        String brandId = BrandAppIdFilter.getBrandAppId()
        String userId=secService.curUserId()
        Partner partner = partnerRepo.findOneByUserIdAndBrandId(userId, brandId)
        PartnerInfoResp resp = partnerConvert.partnerInfoRespConvert(partner)
        return new UniResp<PartnerInfoResp>(status: 200, data: resp)
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
    UniResp<String> update(PartnerUpdateReq partnerUpdateReq) {
        Partner partner=partnerRepo.findOne(partnerUpdateReq.id)
        partner.shopName=partnerUpdateReq.shopName
        partner.phone=partnerUpdateReq.phone
        partnerRepo.save(partner)
        return new UniResp<String>(status: 200, data: "操作成功")
    }


}
