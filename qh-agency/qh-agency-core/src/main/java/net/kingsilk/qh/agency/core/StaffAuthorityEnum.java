package net.kingsilk.qh.agency.core;

/**
 * 平台员工权限代码
 */
public enum StaffAuthorityEnum {


    /**
     * 在 Staff 表中的话，就默认都有。无需将该该值写到数据库中
     */
    STAFF("员工"),
    PARTNERSTAFF("渠道商员工"),

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
     * 商品发布
     */
    ITEM_C("添加商品"),
    ITEM_U("更新商品"),
    ITEM_R("查看商品"),
    ITEM_D("删除商品"),
    ITEM_A("管理商品", ITEM_C, ITEM_U, ITEM_R, ITEM_D),


    /**
     * 商品属性管理
     */
    ITEM_PROP_C("添加商品属性"),
    ITEM_PROP_U("编辑商品属性"),
    ITEM_PROP_R("查看商品属性"),
    ITEM_PROP_D("删除商品属性"),
    ITEM_PROP_A("属性管理", ITEM_PROP_C, ITEM_PROP_U, ITEM_PROP_R, ITEM_PROP_D),


    /**
     * 商品分类管理
     */
    CATEGORY_C("添加商品分类"),
    CATEGORY_U("编辑商品分类"),
    CATEGORY_R("查看商品分类"),
    CATEGORY_D("删除商品分类"),
    CATEGORY_A("管理商品分类", CATEGORY_C, CATEGORY_U, CATEGORY_R, CATEGORY_D),


    /**
     * 订单管理
     */
    ORDER_C("改价"),
    ORDER_R("查看"),
    ORDER_E("导出"),
    ORDER_Q("取消订单"),
    ORDER_J("拒绝订单"),
    ORDER_Y("确认接单"),
    ORDER_A("订单管理", ORDER_C, ORDER_R, ORDER_E, ORDER_Q, ORDER_J, ORDER_Y),

    /***
     * 发货管理
     */
    DELIVERINVOICE_C("修改地址"),
    DELIVERINVOICE_U("发货"),
    DELIVERINVOICE_R("查看发货单"),
    DELIVERINVOICE_A("发货管理", DELIVERINVOICE_C, DELIVERINVOICE_U, DELIVERINVOICE_R),

    /***
     * 退款管理(直接退款)
     */
    REFUNDMONEY_U("退款"),
    REFUNDMONEY_R("查看退款订单"),
    REFUNDMONEY_C("确认退款订单"),
    REFUNDMONEY_A("退款管理", REFUNDMONEY_U, REFUNDMONEY_R, REFUNDMONEY_C),

    /**
     * 退货管理
     */
    REFUND_C("同意退货"),
    REFUND_R("查看"),
    REFUND_J("拒绝买家退货"),
    REFUND_A("退货管理", REFUND_C, REFUND_R, REFUND_J),

    /***
     * 渠道商审核管理
     */
    PARTNERCHECK_U("渠道商审核"),
    PARTNERCHECK_R("渠道商查看"),
    PARTNERCHECK_A("渠道商管理", PARTNERCHECK_U, PARTNERCHECK_R),

    /***
     * 渠道商授权管理
     */
    PARTNERLICENSE_U("渠道商授权更新"),
    PARTNERLICENSE_R("渠道商授权查看"),
    PARTNERLICENSE_D("渠道商授权禁用"),
    PARTNERLICENSE_A("渠道商授权管理", PARTNERLICENSE_U, PARTNERLICENSE_R, PARTNERLICENSE_D),

    /**
     * 渠道商帐号管理
     */
    PARTNERINFO_U("渠道商帐号更新"),
    PARTNERINFO_R("渠道商帐号查看"),
    PARTNERINFO_D("渠道商帐号禁用"),
    PARTNERINFO_A("渠道商帐号管理", PARTNERINFO_U, PARTNERINFO_R, PARTNERINFO_D),

    /***
     * 提现管理
     */
    WITHDRAWCASH_R("查看提现记录"),
    WITHDRAWCASH_U("同意提现"),
    WITHDRAWCASH_J("拒绝提现"),
    WITHDRAWCASH_A("资产管理", WITHDRAWCASH_R, WITHDRAWCASH_U),

    /***
     * 账户流水管理
     */
    ACCOUNTLOG_R("查看账户流水"),
    ACCOUNTLOG_A("账户流水管理", ACCOUNTLOG_R),

    /***
     * 基础设置管理
     */
    PARAMETER_R("基础参数查看"),
    PARAMETER_U("基础参数修改"),
    REFUNDADDR_R("查看我的退货地址"),
    REFUNDADDR_U("更新我的退货地址"),
    STATISTICS_R("查看统计参数信息"),
    STATISTICS_U("更新统计参数信息"),
    BASESET_A("基础设置管理", PARAMETER_R, PARAMETER_U, REFUNDADDR_R, REFUNDADDR_U, STATISTICS_R, STATISTICS_U),


    /**
     * SUPER ADMIN
     */
    SA("超级管理员", REFUND_A, ORDER_A, CATEGORY_A, ITEM_PROP_A,
            ITEM_A, STAFF_GROUP_A, STAFF_A, DELIVERINVOICE_A, REFUNDMONEY_A, PARTNERCHECK_A,
            PARTNERLICENSE_A, PARTNERINFO_A, WITHDRAWCASH_A, ACCOUNTLOG_A, BASESET_A);


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