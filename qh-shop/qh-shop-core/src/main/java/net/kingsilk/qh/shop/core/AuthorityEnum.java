package net.kingsilk.qh.shop.core;

public enum AuthorityEnum {

    STAFF("员工"),
    SHOPSTAFF("门店员工"),

    /***************************************  后台相关   ***************************************************/


    SA("超级管理员"),


    /*************************************  前端相关 ********************************************************/

    /**
     * 商品发布
     */
    SHOP_ITEM_C("添加商品"),
    SHOP_ITEM_U("更新商品"),
    SHOP_ITEM_R("查看商品"),
    SHOP_ITEM_D("删除商品"),
    SHOP_ITEM_T("下架商品"),
    SHOP_ITEM_A("管理商品", SHOP_ITEM_C, SHOP_ITEM_U, SHOP_ITEM_R, SHOP_ITEM_D, SHOP_ITEM_T),


    /**
     * 商品分类管理
     */
    SHOP_CATEGORY_C("添加商品分类"),
    SHOP_CATEGORY_U("编辑商品分类"),
    SHOP_CATEGORY_R("查看商品分类"),
    SHOP_CATEGORY_D("删除商品分类"),
    SHOP_CATEGORY_A("管理商品分类", SHOP_CATEGORY_C, SHOP_CATEGORY_U, SHOP_CATEGORY_R, SHOP_CATEGORY_D),

    /**
     * 入库登记
     */
    SHOP_INSTOCK_C("入库登记"),
    SHOP_INSTOCK_A("入库管理", SHOP_INSTOCK_C),

    /**
     * 入库记录
     */
    SHOP_INSTOCKLOG_R("查看记录"),
    SHOP_INSTOCKLOG_D("撤销记录"),
    SHOP_INSTOCKLOG_A("入库记录", SHOP_INSTOCKLOG_R, SHOP_INSTOCKLOG_D),

    /**
     * 出库登记
     */
    SHOP_OUTSTOCK_C("出库登记"),
    SHOP_OUTSTOCK_A("出库操作", SHOP_OUTSTOCK_C),

    /**
     * 出库记录
     */
    SHOP_OUTSTOCKLOG_R("查看记录"),
    SHOP_OUTSTOCKLOG_D("撤销记录"),
    SHOP_OUTSTOCKLOG_A("入库记录", SHOP_OUTSTOCKLOG_R, SHOP_OUTSTOCKLOG_D),


    /**
     * 库存盘点
     */
    SHOP_STOCKCHECK_C("库存盘点"),
    SHOP_STOCKCHECK_A("库存盘点", SHOP_STOCKCHECK_C),


    /**
     * 开单收银
     */
    SHOP_CASHIER_C("收银"),
    SHOP_CASHIER_A("收银", SHOP_CASHIER_C),

    /**
     * 门店信息
     */
    SHOPINFO_R("查看门店"),
    SHOPINFO_U("修改门店"),
    SHOPINFO_A("门店信息", SHOPINFO_R, SHOPINFO_U),

    /**
     * 角色信息
     */
    SHOP_STAFF_GROUP_C("添加角色"),
    SHOP_STAFF_GROUP_U("编辑角色"),
    SHOP_STAFF_GROUP_R("查看角色"),
    SHOP_STAFF_GROUP_D("删除角色"),
    SHOP_STAFF_GROUP_A("管理角色", SHOP_STAFF_GROUP_C, SHOP_STAFF_GROUP_U, SHOP_STAFF_GROUP_R, SHOP_STAFF_GROUP_D),

    /**
     * 店员信息
     */
    SHOP_STAFF_C("添加员工"),
    SHOP_STAFF_U("编辑员工"),
    SHOP_STAFF_R("查看员工"),
    //    SHOP_STAFF_D("删除员工"),
    SHOP_STAFF_A("管理员工", SHOP_STAFF_C, SHOP_STAFF_U, SHOP_STAFF_R),


    /**
     * 仓库信息
     */
    SHOP_STORE_R("查看仓库信息"),
    SHOP_STORE_C("创建仓库信息"),
    SHOP_STORE_U("更新从仓库信息"),
    SHOP_STORE_D("删除仓库信息"),
    SHOP_STORE_A("仓库信息", SHOP_STORE_R, SHOP_STORE_C, SHOP_STORE_U, SHOP_STORE_D),

    /**
     * 供应商信息
     */
    SHOP_SUPPLIER_C("创建供应商"),
    SHOP_SUPPLIER_U("更新供应商"),
    SHOP_SUPPLIER_R("查看供应商"),
    SHOP_SUPPLIER_D("删除供应商"),
    SHOP_SUPPLIER_A("供应商信息", SHOP_SUPPLIER_C, SHOP_SUPPLIER_U, SHOP_SUPPLIER_R, SHOP_SUPPLIER_D),

    /**
     * 会员信息
     */
    SHOP_MEMBER_C("创建会员"),
    SHOP_MEMBER_U("更新会员"),
    SHOP_MEMBER_R("查看会员"),
    SHOP_MEMBER_D("删除会员"),
    SHOP_MEMBER_A("会员信息", SHOP_MEMBER_C, SHOP_MEMBER_U, SHOP_MEMBER_R, SHOP_MEMBER_D),

    SHOP_SA("门店超级管理员", SHOP_ITEM_A, SHOP_CATEGORY_A, SHOP_INSTOCK_A, SHOP_INSTOCKLOG_A, SHOP_OUTSTOCK_A,
            SHOP_OUTSTOCKLOG_A, SHOP_STOCKCHECK_A, SHOP_CASHIER_A, SHOPINFO_A, SHOP_STAFF_GROUP_A, SHOP_STAFF_A,
            SHOP_STORE_A, SHOP_SUPPLIER_A, SHOP_MEMBER_A);


    private final String desp;
    private final AuthorityEnum[] children;


    AuthorityEnum(String desp, AuthorityEnum... children) {

        this.desp = desp;
        this.children = children;
    }

    public String getDesp() {
        return desp;
    }

    public AuthorityEnum[] getChildren() {
        return children;
    }
}
