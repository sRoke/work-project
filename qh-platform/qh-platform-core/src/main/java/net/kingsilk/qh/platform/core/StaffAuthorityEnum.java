package net.kingsilk.qh.platform.core;

/**
 * 平台员工权限代码
 */
public enum StaffAuthorityEnum {


    /**
     * 在 Staff 表中的话，就默认都有。无需将该该值写到数据库中
     */
    STAFF("员工"),


    /***
     * 应用信息
     */
    APPLICATION_R("查看应用信息"),
    APPLICATION_U("编辑应用信息"),
    APPLICATION_A("应用管理", APPLICATION_R, APPLICATION_U),

    /**
     * 员工
     */
    STAFF_C("添加员工"),
    STAFF_U("编辑员工"),
    STAFF_R("查看员工"),
    //    STAFF_D("删除员工"),
    STAFF_A("管理员工", STAFF_C, STAFF_U, STAFF_R),


    /**
     * 角色
     */
    STAFF_GROUP_C("添加角色"),
    STAFF_GROUP_U("编辑角色"),
    STAFF_GROUP_R("查看角色"),
    STAFF_GROUP_D("删除角色"),
    STAFF_GROUP_A("管理角色", STAFF_GROUP_C, STAFF_GROUP_U, STAFF_GROUP_R, STAFF_GROUP_D),

    /**
     * 商家订单
     */
    ORDER_R("查看商家订单"),
    ORDER_U("编辑商家订单"),
    ORDER_D("删除商家订单"),
    ORDER_A("订单管理", ORDER_R, ORDER_U, ORDER_D),

    /***
     * 商家信息
     */
    BRANDCOM_C("新增商家"),
    BRANDCOM_U("编辑商家"),
    BRANDCOM_R("查看商家"),
    BRANDCOM_D("删除商家"),
    BRANDAPP_C("新增商家应用"),
    BRANDAPP_L("商家应用付费记录"),
    BRANDAPP_R("查看商家应用"),
    BRANDAPP_D("删除商家应用"),
    BRANDCOM_Y("商家应用"),
    BRANDAPP_A("商家应用管理",BRANDAPP_C,BRANDAPP_L,BRANDAPP_R,BRANDAPP_D,BRANDCOM_Y),
    BRANDCOM_A("商家信息", BRANDCOM_C, BRANDCOM_U, BRANDCOM_R, BRANDCOM_D, BRANDAPP_A),


    /***
     * 账户流水管理
     */
    ACCOUNTLOG_R("查看账户流水"),
    ACCOUNTLOG_A("账户流水管理", ACCOUNTLOG_R),


    /**
     * 账户信息
     */
    ACCOUNT_U("账户充值"),
    ACCOUNT_R("查看账户信息"),
    ACCOUNT_L("查看账户记录"),
    ACCOUNT_B("对账"),
    ACCOUNT_A("账户管理", ACCOUNT_U, ACCOUNT_R, ACCOUNT_L, ACCOUNT_B),


    /**
     * 店铺参数
     */
    SHOPPARM_C("新增店铺参数"),
    SHOPPARM_U("编辑店铺参数"),
    SHOPPARM_R("查看店铺参数"),
    SHOPPARM_A("店铺参数", SHOPPARM_C, SHOPPARM_U, SHOPPARM_R),


    /**
     * SUPER ADMIN
     */
    SA("超级管理员", APPLICATION_A, ORDER_A, STAFF_GROUP_A, STAFF_A, BRANDCOM_A, ACCOUNTLOG_A, ACCOUNT_A, SHOPPARM_A);


    private final String desp;
    private final StaffAuthorityEnum[] children;

    StaffAuthorityEnum(String desp, StaffAuthorityEnum... children) {

        this.desp = desp;
        this.children = children;
    }

    public String getDesp() {
        return desp;
    }

    public StaffAuthorityEnum[] getChildren() {
        return children;
    }
}