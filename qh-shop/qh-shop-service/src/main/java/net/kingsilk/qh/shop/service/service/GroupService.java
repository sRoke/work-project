package net.kingsilk.qh.shop.service.service;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.core.AuthorityEnum;
import net.kingsilk.qh.shop.domain.QShopStaff;
import net.kingsilk.qh.shop.domain.QShopStaffGroup;
import net.kingsilk.qh.shop.domain.ShopStaff;
import net.kingsilk.qh.shop.domain.ShopStaffGroup;
import net.kingsilk.qh.shop.repo.ShopStaffGroupRepo;
import net.kingsilk.qh.shop.repo.ShopStaffRepo;
import net.kingsilk.qh.shop.service.security.BrandAppIdFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupService {

    @Autowired
    private SecService secService;

    @Autowired
    private ShopStaffRepo shopStaffRepo;

    @Autowired
    private ShopStaffGroupRepo shopStaffGroupRepo;

    public List<ShopStaffGroup> search(String keywords, String staffId) {
        Iterable<ShopStaffGroup> list;
        String userId = secService.curUserId();
        BrandAppIdFilter brandAppIdFilter = new BrandAppIdFilter();
        String brandId = brandAppIdFilter.getBrandAppId();
        String shopId = brandAppIdFilter.getShopId();
        ShopStaff shopStaff = shopStaffRepo.findOne(
                Expressions.allOf(
                        QShopStaff.shopStaff.userId.eq(userId),
                        QShopStaff.shopStaff.brandAppId.eq(brandId),
                        QShopStaff.shopStaff.shopId.eq(shopId),
                        QShopStaff.shopStaff.deleted.ne(true))
        );
        Assert.notNull(shopStaff, "员工 ${userId} 不存在");
        if (staffId != null) {
            ShopStaff staff1 = shopStaffRepo.findOne(staffId);
            list = shopStaffGroupRepo.findAll(
                    Expressions.allOf(
                            QShopStaffGroup.shopStaffGroup.shopId.eq(staff1.getShopId()),
                            QShopStaffGroup.shopStaffGroup.staffS.contains(staff1),
                            QShopStaffGroup.shopStaffGroup.deleted.ne(true)
                    ));
        } else {
            list = shopStaffGroupRepo.findAll(
                    Expressions.allOf(
                            QShopStaffGroup.shopStaffGroup.shopId.eq(shopStaff.getShopId()),
                            QShopStaffGroup.shopStaffGroup.staffS.contains(shopStaff),
                            !StringUtils.isEmpty(keywords) ? QShopStaffGroup.shopStaffGroup.name.contains(keywords) : null,
                            QShopStaffGroup.shopStaffGroup.deleted.ne(true)
                    ));
        }

        List<ShopStaffGroup> resp = new ArrayList<>();

        list.forEach(
                shopStaffGroup -> {
                    resp.add(shopStaffGroup);
                }
        );

        return resp;
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

//        List<Map<String, Object>>

        /****前端权限*****/
        classifyMap = new HashMap<>();
        classifyMap.put("商品发布", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_ITEM_A));
        classifyMap.put("商品分类管理", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_CATEGORY_A));
        authorMap.put("商品", classifyMap);

        classifyMap = new HashMap<>();
        classifyMap.put("入库登记", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_INSTOCK_A));
        classifyMap.put("入库记录", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_INSTOCKLOG_A));
        classifyMap.put("出库登记", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_OUTSTOCK_A));
        classifyMap.put("出库记录", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_OUTSTOCKLOG_A));
        classifyMap.put("库存盘点", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_STOCKCHECK_A));
        classifyMap.put("收银", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_CASHIER_A));
        authorMap.put("经销存", classifyMap);

        classifyMap = new HashMap<>();
        classifyMap.put("门店信息", fillAuth(new HashMap<>(), AuthorityEnum.SHOPINFO_A));
        classifyMap.put("角色管理", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_STAFF_GROUP_A));
        classifyMap.put("员工管理", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_STAFF_A));
        classifyMap.put("仓库管理", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_STORE_A));
        classifyMap.put("供应商管理", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_SUPPLIER_A));
        classifyMap.put("会员管理", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_MEMBER_A));
        authorMap.put("基础信息", classifyMap);

        return authorMap;
    }



//    Map<String, Map<String, Map<String, String>>> authorMap = new HashMap<>();
//    Map<String, Map<String, String>> classifyMap;
//
//    /****前端权限*****/
//    classifyMap = new HashMap<>();
//        classifyMap.put("商品发布", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_ITEM_A));
//        classifyMap.put("商品分类管理", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_CATEGORY_A));
//        authorMap.put("商品", classifyMap);
//
//    classifyMap = new HashMap<>();
//        classifyMap.put("入库登记", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_INSTOCK_A));
//        classifyMap.put("入库记录", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_INSTOCKLOG_A));
//        classifyMap.put("出库登记", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_OUTSTOCK_A));
//        classifyMap.put("出库记录", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_OUTSTOCKLOG_A));
//        classifyMap.put("库存盘点", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_STOCKCHECK_A));
//        classifyMap.put("收银", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_CASHIER_A));
//        authorMap.put("经销存", classifyMap);
//
//    classifyMap = new HashMap<>();
//        classifyMap.put("门店信息", fillAuth(new HashMap<>(), AuthorityEnum.SHOPINFO_A));
//        classifyMap.put("角色管理", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_STAFF_GROUP_A));
//        classifyMap.put("员工管理", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_STAFF_A));
//        classifyMap.put("仓库管理", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_STORE_A));
//        classifyMap.put("供应商管理", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_SUPPLIER_A));
//        classifyMap.put("会员管理", fillAuth(new HashMap<>(), AuthorityEnum.SHOP_MEMBER_A));
//        authorMap.put("基础信息", classifyMap);
//
//        return authorMap;


    /**
     * 根据权限枚举列表的父节点进行其子节点的添加
     *
     * @param auths Map类型，key为权限枚举类型值，valus为是否有该权限，默认为FALSE
     * @param e     权限枚举值的父枚举节点
     * @return
     */
    public static Map<String, String> fillAuth(Map<String, String> auths, AuthorityEnum e) {
        if (e.getChildren() != null) {
            AuthorityEnum[] items = e.getChildren();
            for (AuthorityEnum item : items) {
                auths.put(item.name(), item.getDesp());
            }

        }

        return auths;
    }
}
