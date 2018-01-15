package net.kingsilk.qh.shop.core;


/**
 * 佣金提现状态
 */
public enum SeqTypeEnum {


    TEST("TEST","测试用编号"),
    Brand("Brand","品牌编号"),
    Item("Item","商品编号"),
    CleanItem("CleanItem","洗护商品编号"),
    Sku("Sku","Sku编号"),
    Staff("Staff","员工编号"),
    User("User","用户编号"),
    StaffGroup("StaffGroup","员工组编号"),
    UserGroup("UserGroup","用户组编号"),
    Order("Order","订单"),
    HOTELORDER("HOTELORDER","酒店订单"),
    RENTORDER("RENTORDER","租赁订单"),
    CLEANORDER("CLEANORDER","清洗订单"),
    ORG_ORDER("ORG_ORDER","酒店柜台订单"),
    RENTITEM("RENTITEM","租赁商品"),
    ServiceStation("ServiceStation","服务网点"),
    PaymentNo("PaymentNo","支付编号"),
    RefundNo("RefundNo","退货退款编号"),
    ActivityRecharge("ActivityRecharge","充值活动赠送编号"),
    TRANSPORT("TRANSPORT","租赁任务"),
    USER_PAY("USER_PAY","用户被动支付"),
    ORGORDER("ORGORDER","店铺订单"),
    USER_ORG("USER_ORG","商铺编号"),
    GROUP_ORDER("GROUP_ORDER","拼团订单");
    // Store("","店铺编号"),
    SeqTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    SeqTypeEnum(String code) {
        this(code, null);
    }

    public String getCode() {
        return code;
    }

    public String getDesp() {
        return desp;
    }

    private final String code;
    private final String desp;
}
