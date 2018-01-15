package net.kingsilk.qh.platform.service.service;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.platform.core.StaffAuthorityEnum;
import net.kingsilk.qh.platform.domain.QStaff;
import net.kingsilk.qh.platform.domain.QStaffGroup;
import net.kingsilk.qh.platform.domain.Staff;
import net.kingsilk.qh.platform.domain.StaffGroup;
import net.kingsilk.qh.platform.repo.StaffGroupRepo;
import net.kingsilk.qh.platform.repo.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class StaffGroupService {

    @Autowired
    private StaffGroupRepo staffGroupRepo;

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private SecService secService;

    public List<StaffGroup> search(String keywords, String staffId) {
        Iterable<StaffGroup> list;
        String userId = secService.curUserId();

        Staff staff = staffRepo.findOne(
                Expressions.allOf(
                        QStaff.staff.userId.eq(userId),
                        QStaff.staff.deleted.in(false)
                )
        );
        Assert.notNull(staff, "员工 ${userId} 不存在");
        if (!StringUtils.isEmpty(staffId)) {
            Staff staff1 = staffRepo.findOne(staffId);
            list = staffGroupRepo.findAll(
                    Expressions.allOf(
                            QStaffGroup.staffGroup.staffs.contains(staff1),
                            QStaffGroup.staffGroup.deleted.in(false)
                    ));
        } else {
            if (staff.getAuthorities().contains(StaffAuthorityEnum.SA.name())) {
                list = staffGroupRepo.findAll(
                        Expressions.allOf(
                                !StringUtils.isEmpty(keywords) ? QStaffGroup.staffGroup.name.contains(keywords) : null,
                                QStaffGroup.staffGroup.deleted.in(false)
                        ));
            } else {
                list = staffGroupRepo.findAll(
                        Expressions.allOf(
                                QStaffGroup.staffGroup.staffs.contains(staff),
                                !StringUtils.isEmpty(keywords) ? QStaffGroup.staffGroup.name.contains(keywords) : null,
                                QStaffGroup.staffGroup.deleted.in(false)
                        ));
            }
        }
        List<StaffGroup> staffGroupList = new ArrayList<>();
        if (list.iterator().hasNext()) {
            staffGroupList.add(list.iterator().next());
        }
        return staffGroupList;
    }

    /**
     * 权限三层结构模板,根据权限枚举值填充三层结构
     * <p>
     * [
     * {*         group:"人员管理",
     * children: [
     * {*                  group:"员工管理",
     * authorities:[
     * {*                          title:"禁用"
     * authority:"STAFF_C"
     * }*
     * ]
     * }*         ]
     * }*
     * <p>
     * ]
     *
     * @return 三层结构列表
     */
    public Map<String, Map<String, Map<String, String>>> getAuthorMap() {
        Map<String, Map<String, Map<String, String>>> authorMap = new HashMap<>();
        Map<String, Map<String, String>> classifyMap;
        Boolean isAdmin = false;

        classifyMap = new HashMap<>();
        classifyMap.put("应用信息", fillAuth(new HashMap<>(), StaffAuthorityEnum.APPLICATION_A));
        classifyMap.put("角色管理", fillAuth(new HashMap<>(), StaffAuthorityEnum.STAFF_GROUP_A));
        authorMap.put("应用管理", classifyMap);


        classifyMap = new HashMap<>();
        classifyMap.put("商家信息", fillAuth(new HashMap<>(), StaffAuthorityEnum.BRANDCOM_A));
        classifyMap.put("商家订单", fillAuth(new HashMap<>(), StaffAuthorityEnum.ORDER_A));
        authorMap.put("商家管理", classifyMap);

        classifyMap = new HashMap<>();
        classifyMap.put("账户信息", fillAuth(new HashMap<>(), StaffAuthorityEnum.ACCOUNT_A));
        classifyMap.put("账户流水", fillAuth(new HashMap<>(), StaffAuthorityEnum.ACCOUNTLOG_A));
//        classifyMap.put("交易记录",fillAuth(new HashMap<String, String>(), StaffAuthorityEnum.ORDER_A));
//        classifyMap.put("对账单",fillAuth(new HashMap<String, String>(), StaffAuthorityEnum.ORDER_A));
        authorMap.put("资金管理", classifyMap);

        classifyMap = new HashMap<>();
        classifyMap.put("员工管理", fillAuth(new HashMap<>(), StaffAuthorityEnum.STAFF_A));
        classifyMap.put("店铺参数", fillAuth(new HashMap<>(), StaffAuthorityEnum.SHOPPARM_A));
        authorMap.put("人员管理", classifyMap);

        return authorMap;


    }

    /**
     * 根据权限枚举列表的父节点进行其子节点的添加
     *
     * @param auths Map类型，key为权限枚举类型值，valus为是否有该权限，默认为FALSE
     * @param e     权限枚举值的父枚举节点
     * @return
     */
    public static Map<String, String> fillAuth(Map<String, String> auths, StaffAuthorityEnum e) {
        if (e.getChildren() != null) {
            StaffAuthorityEnum[] items = e.getChildren();
            for (StaffAuthorityEnum item : items) {
                auths.put(item.name(), item.getDesp());
            }

        }

        return auths;
    }

}
