package net.kingsilk.qh.agency.service

import com.querydsl.core.types.dsl.Expressions
import groovy.transform.CompileStatic
import net.kingsilk.qh.agency.core.StaffAuthorityEnum
import net.kingsilk.qh.agency.domain.QStaff
import net.kingsilk.qh.agency.domain.QStaffGroup
import net.kingsilk.qh.agency.domain.Staff
import net.kingsilk.qh.agency.domain.StaffGroup
import net.kingsilk.qh.agency.repo.CompanyRepo
import net.kingsilk.qh.agency.repo.StaffGroupRepo
import net.kingsilk.qh.agency.repo.StaffRepo
import net.kingsilk.qh.agency.security.BrandAppIdFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.Assert

/**
 * Created by yanfq on 17-4-6.
 */

@Service
@CompileStatic
class StaffGroupService {

    @Autowired
    StaffGroupRepo staffGroupRepo

    @Autowired
    StaffRepo staffRepo

    @Autowired
    SecService secService

    @Autowired
    CompanyRepo companyRepo

    List<StaffGroup> search(String keywords, String staffId) {
        List<StaffGroup> list
        String userId = secService.curUserId()
        String brandId = BrandAppIdFilter.getBrandAppId()
//        Assert.notNull(brandId, "公司 ${brandId} 不存在")
//        Company brandId = companyRepo.findOne(companyId)
//        Assert.notNull(brandId, "公司 ${brandId} 不存在")

        Staff staff = staffRepo.findOne(
                Expressions.allOf(
                        QStaff.staff.userId.eq(userId),
                        QStaff.staff.brandAppId.eq(brandId),
                        QStaff.staff.deleted.in([false, null])
                )
        )
        Assert.notNull(staff, "员工 ${userId} 不存在")
        if (staffId) {
            Staff staff1 = staffRepo.findOne(staffId)
            list = staffGroupRepo.findAll(
                    Expressions.allOf(
                            QStaffGroup.staffGroup.staffs.contains(staff1),
                            QStaffGroup.staffGroup.deleted.in([null, false])
                    )).toList()
        } else {
            if (staff.authorities.contains(StaffAuthorityEnum.SA.name())) {
                list = staffGroupRepo.findAll(
                        Expressions.allOf(
                                keywords ? QStaffGroup.staffGroup.name.contains(keywords) : null,
                                QStaffGroup.staffGroup.deleted.in([null, false])
                        )).toList()
            } else {
                list = staffGroupRepo.findAll(
                        Expressions.allOf(
                                QStaffGroup.staffGroup.staffs.contains(staff),
                                keywords ? QStaffGroup.staffGroup.name.contains(keywords) : null,
                                QStaffGroup.staffGroup.deleted.in([null, false])
                        )).toList()
            }
        }
        return list
    }

    /**
     * 权限三层结构模板,根据权限枚举值填充三层结构
     *
     * [
     *{*         group:"人员管理",
     *         children: [
     *{*                  group:"员工管理",
     *                  authorities:[
     *{*                          title:"禁用"
     *                          authority:"STAFF_C"
     *}*
     *                  ]
     *}*         ]
     *}*
     *
     * ]
     *
     * @return 三层结构列表
     */
    Map<String, Map<String, Map<String, String>>> getAuthorMap() {
        Map<String, Map<String, Map<String, String>>> authorMap = new HashMap<>()
        Map<String, Map<String, String>> classifyMap
        Boolean isAdmin = false

        classifyMap = new HashMap<>()
        classifyMap.put("渠道审核", fillAuth(new HashMap<String, String>(), StaffAuthorityEnum.PARTNERCHECK_A))
        classifyMap.put("渠道信息", fillAuth(new HashMap<String, String>(), StaffAuthorityEnum.PARTNERLICENSE_A))
        classifyMap.put("渠道帐号", fillAuth(new HashMap<String, String>(), StaffAuthorityEnum.PARTNERINFO_A))
        authorMap.put("渠道管理", classifyMap)


        classifyMap = new HashMap<>()
        classifyMap.put("属性管理", fillAuth(new HashMap<String, String>(), StaffAuthorityEnum.ITEM_PROP_A))
        classifyMap.put("分类管理", fillAuth(new HashMap<String, String>(), StaffAuthorityEnum.CATEGORY_A))
        classifyMap.put("商品发布", fillAuth(new HashMap<String, String>(), StaffAuthorityEnum.ITEM_A))
        authorMap.put("商品管理", classifyMap)

        classifyMap = new HashMap<>()
        classifyMap.put("订单中心", fillAuth(new HashMap<String, String>(), StaffAuthorityEnum.ORDER_A))
        classifyMap.put("退货管理", fillAuth(new HashMap<String, String>(), StaffAuthorityEnum.REFUND_A))
        classifyMap.put("发货管理", fillAuth(new HashMap<String, String>(), StaffAuthorityEnum.DELIVERINVOICE_A))
        classifyMap.put("退款管理", fillAuth(new HashMap<String, String>(), StaffAuthorityEnum.REFUNDMONEY_A))
        authorMap.put("订单管理", classifyMap)

        classifyMap = new HashMap<>()
        classifyMap.put("提现管理", fillAuth(new HashMap<String, String>(), StaffAuthorityEnum.WITHDRAWCASH_A))
        classifyMap.put("帐号流水", fillAuth(new HashMap<String, String>(), StaffAuthorityEnum.ACCOUNTLOG_A))
        authorMap.put("资产管理", classifyMap)

        classifyMap = new HashMap<>()
        classifyMap.put("员工管理", fillAuth(new HashMap<String, String>(), StaffAuthorityEnum.STAFF_A))
        classifyMap.put("角色管理", fillAuth(new HashMap<String, String>(), StaffAuthorityEnum.STAFF_GROUP_A))
        authorMap.put("人员管理", classifyMap)

        classifyMap = new HashMap<>()
        classifyMap.put("基础设置", fillAuth(new HashMap<String, String>(), StaffAuthorityEnum.BASESET_A))
//        classifyMap.put("登录日志", fillAuth(new HashMap<String, String>(), StaffAuthorityEnum.LOGIN_LOG_A))
//        classifyMap.put("物流管理", fillAuth(new HashMap<String, String>(), StaffAuthorityEnum.LOGISTICS_A))
//        classifyMap.put("统计报表", fillAuth(new HashMap<String, String>(), StaffAuthorityEnum.REPORT_A))
        authorMap.put("系统管理", classifyMap)

        return authorMap


    }

    /**
     * 根据权限枚举列表的父节点进行其子节点的添加
     * @param auths Map类型，key为权限枚举类型值，valus为是否有该权限，默认为FALSE
     * @param e 权限枚举值的父枚举节点
     * @return
     */
    static Map<String, String> fillAuth(Map<String, String> auths, StaffAuthorityEnum e) {
        if (e.children) {
            StaffAuthorityEnum[] items = e.getChildren()
            for (StaffAuthorityEnum item : items) {
                auths.put(item.name(), item.desp)
            }

        }

        return auths
    }

}
