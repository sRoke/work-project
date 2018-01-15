import conf                                                     from "../conf";
import sMain                                                    from "./main";
import sMainOtherMain                                           from "./main/otherMain";
import sMainBindPhone                                           from "./main/bindPhone";
import sMainWxLogin                                             from "./main/wxLogin";
import sMainBrandApp                                           from "./main/brandApp";
import sMainLoginTime                                           from "./main/loginTime";
// import   sMainJoinUs                                         from "./main/joinUS";
import sMainBrandAppHome                                       from "./main/brandApp/home";
import sMainBrandAppShelves                                    from "./main/brandApp/shelves";
import sMainBrandAppCenter                                     from "./main/brandApp/center";
import sMainBrandAppCart                                       from "./main/brandApp/cart";
import sMainBrandAppAddress                                    from "./main/brandApp/address";
import sMainBrandAppAddAddress                                 from "./main/brandApp/addAddress";
//个人信息   地址
import sMainBrandAppPersonalAddress                            from "./main/brandApp/personalAddress";
import sMainBrandAppAddPerson                                  from "./main/brandApp/addPerson";
import sMainBrandAppItem                                       from "./main/brandApp/item";
import sMainBrandAppOrder                                      from "./main/brandApp/order";
import sMainBrandAppOrderDeliver                               from "./main/brandApp/order/deliver";
import sMainBrandAppOrderCheckOrder                            from "./main/brandApp/order/checkOrder";
import sMainBrandAppOrderOrderDetail                           from "./main/brandApp/order/orderDetail";
import sMainBrandAppOrderRefund                                from "./main/brandApp/order/refund";
import sMainBrandAppUser                                       from "./main/brandApp/user";
import sMainBrandAppUnionOrder                                 from "./main/brandApp/unionOrder";
import sMainBrandAppJoinUs                                     from "./main/brandApp/joinUS";
import sMainBrandAppCenterUserInfo                             from "./main/brandApp/center/userInfo";
import sMainBrandAppCenterMain                                 from "./main/brandApp/center/main";
import sMainBrandAppCenterSetting                              from "./main/brandApp/center/setting";
import sMainBrandAppCenterFeedback                             from "./main/brandApp/center/feedback";
import sMainBrandAppWallet                                     from "./main/brandApp/wallet";
import sMainBrandAppWalletAssets                               from "./main/brandApp/wallet/assets";
import sMainBrandAppWalletDetail                               from "./main/brandApp/wallet/walletDetail";
import sMainBrandAppUserShops                                  from "./main/brandApp/user/shops";
import sMainBrandAppUserAddress                                from "./main/brandApp/user/address";
import sMainBrandAppUserSetPassword                            from "./main/brandApp/user/setPassword";
import sMainBrandAppSales                                      from "./main/brandApp/sales";
import sMainBrandAppSalesSalesMain                             from "./main/brandApp/sales/salesMain";
import sMainBrandAppReport                                     from "./main/brandApp/report";
import sMainBrandAppReportReportMain                           from "./main/brandApp/report/reportMain";
import sMainBrandAppChannel                                    from "./main/brandApp/channel";
import sMainBrandAppChannelCustomer                            from "./main/brandApp/channel/customer";
import sMainBrandAppChannelDetail                              from "./main/brandApp/channel/detail";
//新创建
import sMainBrandAppPurchase                                   from "./main/brandApp/purchase";
// import   sMainBrandAppNewItem                               from "./main/brandApp/item";
import sMainBrandAppStock                                      from "./main/brandApp/stock";
import sMainBrandAppStockControl                               from "./main/brandApp/stockControl";
import sMainBrandAppStockItem                                  from "./main/brandApp/stockItem";
import sMainBrandAppStockDetail                                from "./main/brandApp/stockDetail";
import sMainBrandAppWeuiTest                                   from "./main/brandApp/weuiTest";
//rmy 2017-08-23    设置支付密码相关流程
import sMainBrandAppCenterPhone                                from "./main/brandApp/center/phone";
import sMainBrandAppCenterSetPsword                          from "./main/brandApp/center/setPsword";
import sMainBrandAppCenterModifyPassword                       from "./main/brandApp/center/modifyPassword";
//入账记录
import sMainBrandAppWalletEntryRecord                          from "./main/brandApp/wallet/entryRecord";
import sMainBrandAppWalletWithdraw                             from "./main/brandApp/wallet/withdraw";
import sMainBrandAppAuthorization                              from "./main/brandApp/authorization";



import sMainBrandAppWalletPayPassword                          from "./main/brandApp/wallet/payPassword";
import sMainBrandAppWalletTxPassword                           from "./main/brandApp/wallet/txPassword";
import sMainBrandAppWalletWdSuccess                            from "./main/brandApp/wallet/wdSuccess";

//2017-09-07 退货地址
import sMainBrandAppCenterRefundAddress                        from "./main/brandApp/center/refundAddress";
import sMainBrandAppCenterRfdAddress                           from "./main/brandApp/center/rfAddress";



//退货订单相关
import   sMainBrandAppRefund                                   from "./main/brandApp/refund";
import   sMainBrandAppRefundManage                             from "./main/brandApp/refund/refundManage";
import   sMainBrandAppOrderRefundOrder                         from "./main/brandApp/refund/refundOrder";
import   sMainBrandAppRefundOrderDetail                        from "./main/brandApp/refund/refundOrderDetail";
import   sMainBrandAppMyRefundManage                           from "./main/brandApp/refund/myRefundManage";
import   sMainBrandAppMyRefundDeliver                          from "./main/brandApp/refund/refundDeliver";
import   sMainBrandAppRefundMyOrderDetail                          from "./main/brandApp/refund/refundMyOrderDetail";

import   sMainBrandAppOrderManage                              from "./main/brandApp/orderManage";

import   sMainBrandAppManageList                               from "./main/brandApp/manageList";
import   sMainBrandAppManageOrderDetail                        from "./main/brandApp/manageOrderDetail";

import   sMainBrandAppMyPurchase                               from "./main/brandApp/myPurchase";
import   sMainBrandAppMyRefund                                 from "./main/brandApp/myRefund";
import   sMainBrandAppReturnGoods                              from "./main/brandApp/returnGoods";
import   sMainBrandAppManageInvoice                            from "./main/brandApp/manageInvoice";
import   sMainBrandAppManageInvoiceDetail                      from "./main/brandApp/manageInvoiceDetail";
import   sMainBrandAppItemRefund                               from "./main/brandApp/itemRefund";





//参谋

import sMainBrandAppRecord                            from "./main/brandApp/record";


export default angular.module(`${conf.app}.states`, [
    sMain.name,
    sMainOtherMain.name,
    sMainBindPhone.name,
    sMainWxLogin.name,
    sMainBrandApp.name,
    sMainLoginTime.name,
    sMainBrandAppHome.name,
    sMainBrandAppShelves.name,
    sMainBrandAppCenter.name,
    sMainBrandAppCart.name,
    sMainBrandAppAddress.name,
    sMainBrandAppAddAddress.name,
    sMainBrandAppItem.name,
    sMainBrandAppOrder.name,
    sMainBrandAppOrderDeliver.name,
    sMainBrandAppOrderCheckOrder.name,
    sMainBrandAppOrderOrderDetail.name,
    sMainBrandAppOrderRefund.name,
    sMainBrandAppUser.name,
    sMainBrandAppUnionOrder.name,
    sMainBrandAppJoinUs.name,
    sMainBrandAppCenterUserInfo.name,
    sMainBrandAppCenterMain.name,
    sMainBrandAppCenterSetting.name,
    sMainBrandAppCenterFeedback.name,
    sMainBrandAppWallet.name,
    sMainBrandAppWalletAssets.name,
    sMainBrandAppWalletDetail.name,
    sMainBrandAppUserShops.name,
    sMainBrandAppUserAddress.name,
    sMainBrandAppUserSetPassword.name,
    sMainBrandAppSales.name,
    sMainBrandAppSalesSalesMain.name,
    sMainBrandAppReport.name,
    sMainBrandAppReportReportMain.name,
    sMainBrandAppChannel.name,
    sMainBrandAppChannelCustomer.name,
    sMainBrandAppChannelDetail.name,
    sMainBrandAppPurchase.name,
    // sMainBrandAppNewItem.name,
    sMainBrandAppStock.name,
    sMainBrandAppStockControl.name,
    sMainBrandAppStockItem.name,
    sMainBrandAppStockDetail.name,
    sMainBrandAppWeuiTest.name,
    sMainBrandAppWeuiTest.name,
    //
    sMainBrandAppRefund.name,
    sMainBrandAppRefundManage.name,
    sMainBrandAppOrderRefundOrder.name,
    sMainBrandAppRefundOrderDetail.name,
    sMainBrandAppManageList.name,
    sMainBrandAppManageOrderDetail.name,
    sMainBrandAppMyPurchase.name,
    sMainBrandAppMyRefund.name,
    sMainBrandAppReturnGoods.name,
    sMainBrandAppOrderManage.name,
    sMainBrandAppManageInvoice.name,
    sMainBrandAppManageInvoiceDetail.name,
    sMainBrandAppItemRefund.name,
    sMainBrandAppMyRefundManage.name,
    sMainBrandAppMyRefundDeliver.name,
    sMainBrandAppRefundMyOrderDetail.name,
    //rmy 2017-08-23    设置支付密码相关流程
    sMainBrandAppCenterPhone.name,
    sMainBrandAppCenterSetPsword.name,
    sMainBrandAppCenterModifyPassword.name,
    sMainBrandAppWalletEntryRecord.name,
    sMainBrandAppWalletWithdraw.name,

    sMainBrandAppAuthorization.name,
    sMainBrandAppWalletPayPassword.name,
    sMainBrandAppWalletWdSuccess.name,

    sMainBrandAppWalletDetail.name,
    sMainBrandAppWalletTxPassword.name,
    sMainBrandAppRecord.name,
    sMainBrandAppPersonalAddress.name,
    sMainBrandAppAddPerson.name,
    sMainBrandAppCenterRefundAddress.name,
    sMainBrandAppCenterRfdAddress.name
])