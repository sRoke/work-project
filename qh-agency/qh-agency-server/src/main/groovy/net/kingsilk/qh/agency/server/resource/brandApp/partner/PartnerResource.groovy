package net.kingsilk.qh.agency.server.resource.brandApp.partner

import net.kingsilk.qh.agency.api.UniPageResp
import net.kingsilk.qh.agency.api.UniResp
import net.kingsilk.qh.agency.api.brandApp.partner.*
import net.kingsilk.qh.agency.core.PartnerApplyStatusEnum
import net.kingsilk.qh.agency.core.PartnerOperateEnum
import net.kingsilk.qh.agency.core.PartnerTypeEnum
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.repo.*
import net.kingsilk.qh.agency.security.BrandAppIdFilter
import net.kingsilk.qh.agency.service.*
import net.kingsilk.qh.oauth.api.user.org.OrgAddReq
import net.kingsilk.qh.oauth.api.user.org.OrgApi
import net.kingsilk.qh.oauth.core.OrgStatusEnum
import org.apache.http.HttpStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.convert.ConversionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component
import org.springframework.util.Assert

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.core.Context
import java.text.SimpleDateFormat

import static com.querydsl.core.types.dsl.Expressions.allOf
import static com.querydsl.core.types.dsl.Expressions.anyOf

/**
 *
 */


@Component
class PartnerResource implements PartnerApi {

    @Autowired
    PartnerRepo partnerRepo

    @Autowired
    PartnerConvert partnerConvert

    @Autowired
    PartnerStaffService memberService

    @Autowired
    AddrService addrService

    @Autowired
    CommonService commonService

    @Autowired
    PartnerAccountRepo partnerAccountRepo

    @Autowired
    SecService secService

    @Autowired
    AdcRepo adcRepo

    @Autowired
    UserService userService

    @Autowired
    SysConfRepo sysConfRepo;

    @Context
    HttpServletRequest request

    @Autowired
    @Qualifier(value = "mvcConversionService")
    ConversionService conversionService

    @Autowired
    PartnerApplyLogRepo partnerApplyLogRepo

    @Autowired
    PartnerChangeLogRepo partnerChangeLogRepo

    @Autowired
    StaffRepo staffRepo

    @Autowired
    MongoTemplate mongoTemplate

    @Autowired
    OrgApi orgApi
    //-------------------------查看渠道商信息---------------------------------//
    @Override
    UniResp<PartnerInfoResp> info(
            String brandAppId,
            String id
    ) {
        Partner partner = partnerRepo.findOne(id)
        PartnerInfoResp resp = partnerConvert.partnerInfoConvert(partner)
        return new UniResp<PartnerInfoResp>(status: 200, data: resp)
    }

    //-------------------------更新渠道商信息---------------------------------//
    @Override
    UniResp<String> update(
            String brandAppId,
            String id,
            PartnerSaveReq partnerSaveReq) {
        Partner partner = partnerRepo.findOne(id)
        PartnerTypeEnum lastPartnerTypeEnum = partner.partnerTypeEnum
        PartnerApplyStatusEnum lastPartnerStatusEnum = partner.partnerApplyStatus
        PartnerOperateEnum operate =
                partnerSaveReq.partnerType ? PartnerOperateEnum.UPGRADE : PartnerOperateEnum.BASIC
        String staffId = null
        String userId = secService.curUserId()
        Staff staff = staffRepo.findOneByUserIdAndDisabledAndDeletedAndBrandAppId(userId, false, false, brandAppId)
        if (staff) {
            staffId = staff.id
        }
        Assert.notNull(partner, "当前经销商不存在")
        partnerSaveReq.parentId ? partner.parent = partnerRepo.findOne(partnerSaveReq.parentId) : null
        partnerSaveReq.phone ? partner.phone = partnerSaveReq.phone : null
        partnerSaveReq.partnerType ? partner.partnerTypeEnum = PartnerTypeEnum.valueOf(partnerSaveReq.partnerType) : null
        partnerSaveReq.idNo ? partner.idNo = partnerSaveReq.idNo : null
        partnerSaveReq.realName ? partner.realName = partnerSaveReq.realName : null
        partnerSaveReq.shopName ? partner.shopName = partnerSaveReq.shopName : null
        partnerSaveReq.applyStatus ? partner.partnerApplyStatus = PartnerApplyStatusEnum.valueOf(partnerSaveReq.applyStatus) : null
        partnerSaveReq.invitationCode ? partner.invitationCode = partnerSaveReq.invitationCode : null
        partnerSaveReq.shopAddr ? partner.adc = adcRepo.findOneByNo(partnerSaveReq.shopAddr)?.no : null
        partnerRepo.save(partner)
        PartnerChangeLog partnerChangeLog = new PartnerChangeLog(partner.id, partner.brandAppId,
                partner.partnerTypeEnum, lastPartnerTypeEnum, partner.partnerApplyStatus,
                lastPartnerStatusEnum, operate, staffId)
        partnerChangeLogRepo.save(partnerChangeLog)
        return new UniResp<String>(status: 200, data: "保存成功")
    }

//-------------------------获取渠道商列表---------------------------------//
    @Override
    UniResp<UniPageResp<PartnerInfoResp>> page(
            String brandAppId,
            PartnerPageReq partnerPageReq) {

        PageRequest pageRequest = new PageRequest(
                partnerPageReq.page,
                partnerPageReq.size,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))
        String source = partnerPageReq.source
        Page<Partner> partners

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        Date startDate = null
        Date endDate = null


        if (partnerPageReq.startDate) {
            startDate = sdf.parse(partnerPageReq.startDate);
        }
        if (partnerPageReq.endDate) {
            endDate = sdf.parse(partnerPageReq.endDate);
        }
        if (source == "APPLY") {
            partners = partnerRepo.findAll(
                    allOf(
                            startDate ? QPartnerStaff.partnerStaff.dateCreated.gt(startDate) : null,
                            endDate ? QPartnerStaff.partnerStaff.lastModifiedDate.lt(endDate) : null,
                            QPartner.partner.deleted.in([false, null]),
                            QPartner.partner.brandAppId.eq(brandAppId),
                            QPartner.partner.partnerApplyStatus.ne(PartnerApplyStatusEnum.NORMAL),
                            partnerPageReq.applyType ? QPartner.partner.partnerTypeEnum.eq(PartnerTypeEnum.valueOf(partnerPageReq.applyType)) : null,
                            anyOf(
                                    partnerPageReq.keyWord ? QPartner.partner.phone.eq(partnerPageReq.keyWord) : null,
                                    partnerPageReq.keyWord ? QPartner.partner.realName.like("%" + partnerPageReq.keyWord + "%") : null,
                            )
                    ),
                    pageRequest
            )
        } else if (source == "PASS") {
            partners = partnerRepo.findAll(
                    allOf(
                            startDate ? QPartnerStaff.partnerStaff.dateCreated.gt(startDate) : null,
                            endDate ? QPartnerStaff.partnerStaff.lastModifiedDate.lt(endDate) : null,
                            QPartner.partner.deleted.in([false, null]),
                            QPartner.partner.brandAppId.eq(brandAppId),
                            QPartner.partner.partnerApplyStatus.eq(PartnerApplyStatusEnum.NORMAL),
                            partnerPageReq.applyType ? QPartner.partner.partnerTypeEnum.eq(PartnerTypeEnum.valueOf(partnerPageReq.applyType)) : null,
                            anyOf(
                                    partnerPageReq.keyWord ? QPartner.partner.phone.like("%" + partnerPageReq.keyWord + "%") : null,
                                    partnerPageReq.keyWord ? QPartner.partner.realName.like("%" + partnerPageReq.keyWord + "%") : null,
                            )

                    ),
                    pageRequest
            )
        } else {
            return new UniResp<UniPageResp<PartnerInfoResp>>(status: 200, data: "参数错误")
        }
        // 将钱皇股份放到最前面
        Partner partnerCom = partners.content.find {
            PartnerTypeEnum.BRAND_COM.equals(it.partnerTypeEnum) && !it.disabled && !it.deleted
        }
        //partners.content指向的是引用，所以不能直接增删，必须对partnerInfoResps增删
//        partners.content.removeAll(partnerCom)
//        partners.content.addAll(0,partnerCom)

        UniResp<UniPageResp<PartnerInfoResp>> infoPage = new UniResp<UniPageResp<PartnerInfoResp>>()
        infoPage.status = 200
        infoPage.data = conversionService.convert(partners, UniPageResp)
        List<PartnerInfoResp> partnerInfoResps = new LinkedList<PartnerInfoResp>()
        partners.content.each {
            partnerInfoResps.add(partnerConvert.partnerInfoRespConvert(it))
        }
        if (partners.content.contains(partnerCom)) {
            def find = partnerInfoResps.find {
                PartnerTypeEnum.BRAND_COM.desp.equals(it.partnerType)
            }
            partnerInfoResps.remove(find)
            partnerInfoResps.add(0, partnerConvert.partnerInfoRespConvert(partnerCom))
        }
        //unique没用?
        infoPage.data.content.addAll(partnerInfoResps.unique())
        return infoPage
    }
//-------------------------渠道商审核---------------------------------//

    @Override
    UniResp<String> review(
            String brandAppId,
            String id,
            Boolean status,
            String parentId,
            String shopBrandAppId,
            String shopId) {
        String userId = secService.curUserId()
        Staff staff = staffRepo.findOneByUserIdAndDisabledAndDeletedAndBrandAppId(userId, false, false, brandAppId)

        Partner partner = partnerRepo.findOne(id)
        Assert.notNull(partner, "参数错误")
        Partner parent = null
        if (status) {
            partner.partnerApplyStatus = PartnerApplyStatusEnum.NORMAL
            if (parentId) {
                parent = partnerRepo.findOne(parentId)
                Assert.notNull(!parent, "上级渠道商信息错误")
            }
            //根据渠道商类型找基础设置的
            String sysConfKey = partner.partnerTypeEnum.equals(PartnerTypeEnum.GENERAL_AGENCY) ? "generalAgencyMinPlaceNum"
                    : (partner.partnerTypeEnum.equals(PartnerTypeEnum.REGIONAL_AGENCY) ? "regionaLagencyMinPlaceNum"
                    : "leagueMinPlaceNum")
            //防止基础排名变高，而最高排名比基础排名低，需要从基础排名后开始算
            SysConf sysConf = sysConfRepo.findByKeyAndBrandAppId(sysConfKey, brandAppId)
            Integer number = 0
            if (sysConf) {
                number = sysConf.getValueInt()
            }
//            Partner lastPartner = partnerRepo.findByPartnerTypeEnumAndBrandAppIdAndPlaceNumAfter(partner.partnerTypeEnum,brandAppId,number,new Sort(Sort.Direction.DESC,"dateCreated"));
            Integer partnerCount = mongoTemplate.count(new Query().addCriteria(new Criteria().andOperator(
                    Criteria.where("brandAppId").is(brandAppId),
                    Criteria.where("partnerApplyStatus").is(PartnerApplyStatusEnum.NORMAL),
                    Criteria.where("disabled").is(false),
                    Criteria.where("deleted").is(false),
                    Criteria.where("partnerTypeEnum").is(partner.partnerTypeEnum))),
                    Partner.class)

            partner.placeNum = partnerCount + number + 1
            partner.parent = parent
            partner.shopBrandAppId = shopBrandAppId
            partner.shopId = shopId

            OrgAddReq orgAddReq = new OrgAddReq()
            orgAddReq.setStatus(OrgStatusEnum.ENABLED)
            orgAddReq.setName(partner.getShopName())
            net.kingsilk.qh.oauth.api.UniResp<String> oauthResp = orgApi.add(partner.getUserId(), orgAddReq)
            Assert.notNull(oauthResp.getData(), "新增组织失败")
            partner.setOrgId(oauthResp.getData())

            memberService.register(partner.userId, partner)
        } else {
            if (parentId) {
                parent = partnerRepo.findOne(parentId)
                Assert.notNull(!parent, "上级渠道商信息错误")
            }
            partner.partnerApplyStatus = PartnerApplyStatusEnum.REJECT
        }
        partnerRepo.save(partner)

        PartnerAccount ptAccount = partnerAccountRepo.findByPartner(partner)

        if (!ptAccount) {
            PartnerAccount partnerAccount = new PartnerAccount()
            partnerAccount.partner = partner
            partnerAccount.freezeBalance = 0
            partnerAccount.balance = 0
            partnerAccount.brandAppId = brandAppId
            partnerAccount.owedBalance = 0
            partnerAccount.noCashBalance = 0
            partnerAccountRepo.save(partnerAccount)
        }
        //保存相应的审核操作记录
        PartnerOperateEnum operate = PartnerOperateEnum.AUDITING
        String staffId = null

        if (staff) {
            staffId = staff.id
        }

        PartnerChangeLog partnerChangeLog = new PartnerChangeLog(partner.id, partner.brandAppId,
                partner.partnerTypeEnum, partner.partnerTypeEnum, partner.partnerApplyStatus,
                partner.partnerApplyStatus, operate, staffId)
        partnerChangeLogRepo.save(partnerChangeLog)
        return new UniResp<String>(status: 200, data: "审核成功")
    }

    //-------------------------渠道商禁用---------------------------------//
    @Override
    UniResp<String> changeStatus(String brandAppId,
                                 String id,
                                 Boolean status) {
        Partner partner = partnerRepo.findOne(id)
        PartnerOperateEnum operate = PartnerOperateEnum.UPGRADE
        String staffId = null
        String userId = secService.curUserId()
        Staff staff = staffRepo.findOneByUserIdAndDisabledAndDeletedAndBrandAppId(userId, false, false, brandAppId)
        if (staff) {
            staffId = staff.id
        }
        Assert.notNull(partner, "参数错误")
        partner.disabled = status
        partnerRepo.save(partner)

        //保存操作日志
        PartnerChangeLog partnerChangeLog = new PartnerChangeLog(partner.id, partner.brandAppId,
                partner.partnerTypeEnum, partner.partnerTypeEnum, partner.partnerApplyStatus,
                partner.partnerApplyStatus, operate, staffId)
        partnerChangeLogRepo.save(partnerChangeLog)
        return new UniResp<String>(status: 200, data: "操作成功")
    }

    @Override
    UniResp<String> apply(
            String brandAppId,
            PartnerApplyReq partnerApplyReq) {
        String userId = secService.curUserId()
        String brandId = BrandAppIdFilter.getBrandAppId()
        Partner partner = partnerRepo.findOne(
                allOf(
                        QPartner.partner.userId.eq(userId),
                        QPartner.partner.brandAppId.eq(brandId)
                )
        )
        if (!partner) {
            partner = new Partner()
        }
        partner.userId = userId
        partner.brandAppId = brandAppId
        partner.seq = commonService.getDateString()
        partner.realName = partnerApplyReq.realName
        partner.idNo = partnerApplyReq.idNo
//        if (partnerApplyReq.invitationCode) {
//            Partner parent = partnerRepo.findByInvitationCode(partnerApplyReq.invitationCode)
//            partner.parent.id = parent?.id
//        }
//        def code = commonService.genRandomNum()
//        def hasCodeUsed = partnerRepo.findByInvitationCode(code)
//        while (hasCodeUsed) {
//            hasCodeUsed = partnerRepo.findByInvitationCode(commonService.genRandomNum())
//        }
//        partner.invitationCode = commonService.genRandomNum()
        partner.invitationCode = partnerApplyReq.invitationCode
        partner.partnerTypeEnum = PartnerTypeEnum.valueOf(partnerApplyReq.partnerType)
        partner.phone = partnerApplyReq.phone
        partner.adc = adcRepo.findOneByNo(partnerApplyReq.shopAddr).no
        partner.partnerApplyStatus = PartnerApplyStatusEnum.APPLYING
        partnerRepo.save(partner)
        //保存相应的审核操作记录
        PartnerOperateEnum operate = PartnerOperateEnum.AUDITING
        String staffId = null
        Staff staff = staffRepo.findOneByUserIdAndDisabledAndDeletedAndBrandAppId(userId, false, false, brandAppId)
        if (staff) {
            staffId = staff.id
        }
        PartnerChangeLog partnerChangeLog = new PartnerChangeLog(partner.id, partner.brandAppId,
                partner.partnerTypeEnum, partner.partnerTypeEnum, partner.partnerApplyStatus,
                partner.partnerApplyStatus, operate, staffId)
        partnerChangeLogRepo.save(partnerChangeLog)
        return new UniResp<String>(status: 200, data: "保存成功!")
    }

    @Override
    UniResp<String> check(
            String brandAppId) {
        PartnerStaff partnerStaff = memberService.getCurPartnerStaff()
        def oauthUser = userService.getOauthUserInfo(request)
//        String userId=secService.curUserId()
//        Map oauthUser = userService.getOauthUserInfoByUserId(userId);
        if (!oauthUser.phone) {
            return new UniResp<String>(status: 10301, data: "当前用户未注册")
        }
        Partner partner = partnerRepo.findOneByUserIdAndBrandAppId(oauthUser.id as String, brandAppId)
        if (!partner) {
            return new UniResp<String>(status: 10302, data: "当前用户未申请渠道商")
        }
        if (partner.partnerApplyStatus == PartnerApplyStatusEnum.APPLYING) {
            return new UniResp<String>(status: 10303, data: "申请待审核")
        } else if (partner.partnerApplyStatus == PartnerApplyStatusEnum.REJECT) {
            return new UniResp<String>(status: 10304, data: partner.id)
        } else if (partnerStaff && partner.partnerApplyStatus == PartnerApplyStatusEnum.NORMAL) {
            return new UniResp<String>(status: 200, data: partner.id)
        } else {
            return new UniResp<String>(status: 10305, data: "账户信息错误")
        }
    }

    @Override
    UniResp<LinkedHashMap<String, String>> partnerTypes(String brandAppId) {
        SysConf sysConf = sysConfRepo.findByKeyAndBrandAppId("partnerTypes", brandAppId)
        LinkedHashMap<String, String> partnerTypes = new LinkedHashMap<String, String>()
        sysConf.valueList.each { it ->
            Optional.ofNullable(it).ifPresent { i ->
                partnerTypes.put(String.valueOf(i), PartnerTypeEnum.valueOf(String.valueOf(i)).desp)
            }
        }
        UniResp<LinkedHashMap<String, String>> uniResp = new UniResp<LinkedHashMap<String, String>>()
        uniResp.setData(partnerTypes)
        uniResp.setStatus(HttpStatus.SC_OK)
        return uniResp
    }
}
