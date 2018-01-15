import conf                                                                     from "../conf";
import sMain                                                                    from "./main";
import sMainOtherMain                                                           from "./main/otherMain";
import sMainBindPhone                                                           from "./main/bindPhone";
import sMainWxLogin                                                             from "./main/wxLogin";
import sMainBrandApp                                                            from "./main/brandApp";
import sMainLoginTime                                                           from "./main/loginTime";


import sMainTest1                                                           from "./main/test1";
import sMainTest2                                                           from "./main/test2";
import sMainTest3                                                           from "./main/test3";



//选择门店
import   sMainBrandAppSelectStore                                               from "./main/brandApp/selectStore";
import   sMainBrandAppAuthorities                                               from "./main/brandApp/authorities";
import   sMainBrandAppStore                                                     from "./main/brandApp/store";

import   sMainBrandAppHome                                                        from "./main/brandApp/store/home";
import   sMainBrandAppShareShop                                                   from "./main/brandApp/store/shareShop";
import   sMainBrandAppMemberList                                                        from "./main/brandApp/store/memberList";
import   sMainBrandAppMemberInfo                                                        from "./main/brandApp/store/memberInfo";
import   sMainBrandAppMemberDetail                                                        from "./main/brandApp/store/memberDetail";




import   sMainBrandAppTextImg                                                     from "./main/brandApp/store/textImg";
import   sMainBrandAppViewImg                                                     from "./main/brandApp/store/viewImg";

import   sMainBrandAppCashier                                                   from "./main/brandApp/store/cashier";
import   sMainBrandAppSettlement                                                from "./main/brandApp/store/settlement";
import   sMainBrandAppReceivables                                               from "./main/brandApp/store/receivables";
import   sMainBrandAppQrCodePay                                                 from "./main/brandApp/store/qrCodePay";

import   sMainBrandAppCashFlow                                                  from "./main/brandApp/store/cashFlow";
import   sMainBrandAppCashFlowMain                                              from "./main/brandApp/store/cashFlow/cashflowMain";
import   sMainBrandAppCashFlowDetail                                            from "./main/brandApp/store/cashFlow/cashFlowDetail";

import   sMainBrandAppItemManage                                                from "./main/brandApp/store/itemManage";
import   sMainBrandAppItemManageItemMan                                         from "./main/brandApp/store/itemManage/itemMan";
import   sMainBrandAppItemManageItemAdd                                         from "./main/brandApp/store/itemManage/itemAdd";
import   sMainBrandAppItemManageItemStock                                       from "./main/brandApp/store/itemManage/itemStock";
import   sMainBrandAppItemManageStockDetail                                     from "./main/brandApp/store/itemManage/stockDetail";
import   sMainBrandAppItemManageItemEdit                                        from "./main/brandApp/store/itemManage/itemEdit";
import   sMainBrandAppItemManageItemSetPrice                                    from "./main/brandApp/store/itemManage/itemSetPrice";
import   sMainBrandAppItemManageSpecEdit                                        from "./main/brandApp/store/itemManage/specEdit";

//门店管理
import   sMainBrandAppShop                                                      from "./main/brandApp/store/shop";
import   sMainBrandAppShopShopManage                                            from "./main/brandApp/store/shop/shopManage";
import   sMainBrandAppShopChangeShop                                            from "./main/brandApp/store/shop/changeShop";
import   sMainBrandAppShopSalesMan                                              from "./main/brandApp/store/shop/salesMan";
import   sMainBrandAppShopAddSales                                              from "./main/brandApp/store/shop/addSales";
import   sMainBrandAppShopEditSales                                             from "./main/brandApp/store/shop/editSales";
import   sMainBrandAppShopStoreHouse                                            from "./main/brandApp/store/shop/storeHouse";
import   sMainBrandAppShopAddStore                                              from "./main/brandApp/store/shop/addStore";
import   sMainBrandAppShopEditStore                                             from "./main/brandApp/store/shop/editStore";
import   sMainBrandAppShopRoles                                                 from "./main/brandApp/store/shop/shopRoles";
import   sMainBrandAppShopRoleAdd                                               from "./main/brandApp/store/shop/shopRoleAdd";
import   sMainBrandAppShopRoleView                                              from "./main/brandApp/store/shop/shopRoleView";
import   sMainBrandAppShopSupplier                                              from "./main/brandApp/store/shop/supplier";
import   sMainBrandAppShopAddSupplier                                           from "./main/brandApp/store/shop/addSupplier";
import   sMainBrandAppShopEditSupplier                                          from "./main/brandApp/store/shop/editSupplier";
import   sMainBrandAppShopMember                                                from "./main/brandApp/store/shop/member";
import   sMainBrandAppShopAddMember                                             from "./main/brandApp/store/shop/addMember";
import   sMainBrandAppShopEditMember                                            from "./main/brandApp/store/shop/editMember";
import   sMainBrandAppShopAddGoods                                              from "./main/brandApp/store/shop/addGoods";
import   sMainBrandAppShopEditShop                                              from "./main/brandApp/store/shop/editShop";
import   sMainBrandAppShopMyIncome                                              from "./main/brandApp/store/shop/myIncome";
import   sMainBrandAppShopDiscountDetail                                              from "./main/brandApp/store/shop/discountDetail";
import   sMainBrandAppShopSelectClassify                                        from "./main/brandApp/store/shop/selectClassify";
import   sMainBrandAppShopWithdraw                                              from "./main/brandApp/store/shop/withdraw";
import   sMainBrandAppShopDrawType                                              from "./main/brandApp/store/shop/drawType";
import   sMainBrandAppShopSetPay                                              from "./main/brandApp/store/shop/setPay";
import   sMainBrandAppShopGoodSpec                                              from "./main/brandApp/store/shop/goodSpec";
import   sMainBrandAppShopItemEditSelectSpec                                              from "./main/brandApp/store/shop/itemEditSelectSpec";
import   sMainBrandAppShopBuyStore                                              from "./main/brandApp/store/shop/buyStore";
import   sMainBrandAppShopPayDetail                                              from "./main/brandApp/store/shop/payDetail";


//库存记录
import   sMainBrandAppInventoryRecord                                           from "./main/brandApp/store/InventoryRecord";
import   sMainBrandAppInventoryRecordInInventory                                from "./main/brandApp/store/InventoryRecord/inInventory";
import   sMainBrandAppInventoryRecordInInventoryRegister                        from "./main/brandApp/store/InventoryRecord/inInventoryRegister";
import   sMainBrandAppInventoryRecordOutInventory                               from "./main/brandApp/store/InventoryRecord/outInventory";
import   sMainBrandAppInventoryRecordOutInventoryRegister                       from "./main/brandApp/store/InventoryRecord/outInventoryRegister";
import   sMainBrandAppInventoryRecordInventoryDetail                            from "./main/brandApp/store/InventoryRecord/inventoryDetail";
import   sMainBrandAppInventoryRecordItemChoice                                 from "./main/brandApp/store/InventoryRecord/itemChoice";
import   sMainBrandAppShopSelectSpec                                       from "./main/brandApp/store/shop/selectSpec";
import   sMainBrandAppShopViewSpec                                       from "./main/brandApp/store/shop/viewSpec";


//订单相关
import   sMainBrandAppStoreOrder                                                from "./main/brandApp/store/order";
import   sMainBrandAppStoreOrderCenter                                          from "./main/brandApp/store/order/orderCenter";
import   sMainBrandAppStoreOrderDetail                                          from "./main/brandApp/store/order/orderDetail";

import   sMainBrandAppStoreChooseExpress                                        from "./main/brandApp/store/order/chooseExpress";

//退款相关

import   sMainBrandAppStoreRefund                                               from "./main/brandApp/store/refund";
import   sMainBrandAppStoreRefundList                                           from "./main/brandApp/store/refund/refundList";
import   sMainBrandAppStoreRefundDetail                                         from "./main/brandApp/store/refund/refundDetail";

//卖货相关
import   sMainBrandAppStoreSellers                                              from "./main/brandApp/store/sellers";
import   sMainBrandAppStoreSellersItem                                          from "./main/brandApp/store/sellers/sellersItem";
import   sMainBrandAppStoreSellersList                                          from "./main/brandApp/store/sellers/sellersList";

//营销管理
import   sMainBrandAppStoreMarketing                                          from "./main/brandApp/store/marketing";


export default angular.module(`${conf.app}.states`, [
    sMain.name,
    sMainOtherMain.name,
    sMainBindPhone.name,
    sMainWxLogin.name,
    sMainBrandApp.name,
    sMainLoginTime.name,
    sMainTest1.name,
    sMainTest2.name,
    sMainTest3.name,
    sMainBrandAppStore.name,
    sMainBrandAppAuthorities.name,
    sMainBrandAppMemberList.name,
    sMainBrandAppMemberDetail.name,
    sMainBrandAppMemberInfo.name,
    sMainBrandAppTextImg.name,
    sMainBrandAppHome.name,
    sMainBrandAppCashier.name,
    sMainBrandAppItemManage.name,
    sMainBrandAppSettlement.name,
    sMainBrandAppReceivables.name,
    sMainBrandAppQrCodePay.name,
    sMainBrandAppShareShop.name,

    sMainBrandAppSelectStore.name,


    sMainBrandAppCashFlow.name,
    sMainBrandAppCashFlowMain.name,
    sMainBrandAppCashFlowDetail.name,

    sMainBrandAppItemManageItemMan.name,
    sMainBrandAppItemManageItemEdit.name,
    sMainBrandAppInventoryRecord.name,
    sMainBrandAppInventoryRecordInInventory.name,
    sMainBrandAppInventoryRecordOutInventory.name,
    sMainBrandAppInventoryRecordOutInventoryRegister.name,
    sMainBrandAppInventoryRecordInInventoryRegister.name,
    sMainBrandAppInventoryRecordInventoryDetail.name,
    sMainBrandAppInventoryRecordItemChoice.name,

    sMainBrandAppShop.name,
    sMainBrandAppShopShopManage.name,
    sMainBrandAppShopChangeShop.name,
    sMainBrandAppShopSalesMan.name,
    sMainBrandAppShopAddSales.name,
    sMainBrandAppShopMyIncome.name,

    sMainBrandAppShopEditSales.name,
    sMainBrandAppShopStoreHouse.name,
    sMainBrandAppShopAddStore.name,

    sMainBrandAppShopRoles.name,
    sMainBrandAppShopRoleAdd.name,
    sMainBrandAppShopRoleView.name,
    sMainBrandAppShopEditStore.name,
    sMainBrandAppShopSupplier.name,
    sMainBrandAppShopAddSupplier.name,
    sMainBrandAppShopEditSupplier.name,
    sMainBrandAppShopMember.name,
    sMainBrandAppShopAddMember.name,
    sMainBrandAppShopEditMember.name,
    sMainBrandAppItemManageItemStock.name,
    sMainBrandAppItemManageStockDetail.name,
    sMainBrandAppItemManageItemAdd.name,
    sMainBrandAppShopAddGoods.name,
    sMainBrandAppShopEditShop.name,
    sMainBrandAppShopSelectClassify.name,
    sMainBrandAppItemManageSpecEdit.name,
    sMainBrandAppItemManageItemSetPrice.name,
    sMainBrandAppStoreOrder.name,
    sMainBrandAppStoreOrderCenter.name,
    sMainBrandAppStoreOrderDetail.name,
    sMainBrandAppStoreChooseExpress.name,

    sMainBrandAppStoreRefund.name,
    sMainBrandAppStoreRefundList.name,
    sMainBrandAppStoreRefundDetail.name,


    sMainBrandAppShopDiscountDetail.name,
    sMainBrandAppShopWithdraw.name,
    sMainBrandAppShopViewSpec.name,
    sMainBrandAppStoreSellers.name,
    sMainBrandAppStoreSellersList.name,
    sMainBrandAppStoreSellersItem.name,

    sMainBrandAppShopDrawType.name,
    sMainBrandAppShopSetPay.name,
    sMainBrandAppShopGoodSpec.name,
    sMainBrandAppShopSelectSpec.name,
    sMainBrandAppShopItemEditSelectSpec.name,
    sMainBrandAppShopBuyStore.name,
    sMainBrandAppShopPayDetail.name,
    sMainBrandAppViewImg.name,

    sMainBrandAppStoreMarketing.name,
])