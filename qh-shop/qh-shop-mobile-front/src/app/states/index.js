import conf                                                                     from "../conf";
import sMain                                                                    from "./main";
import sMainOtherMain                                                           from "./main/otherMain";
import sMainBindPhone                                                           from "./main/bindPhone";
import sMainWxLogin                                                             from "./main/wxLogin";
import sMainBrandApp                                                            from "./main/brandApp";
import sMainLoginTime                                                           from "./main/loginTime";


import   sMainBrandStoreAppHome                                                 from "./main/brandApp/store/home";
import   sMainBrandStoreAppItem                                                 from "./main/brandApp/store/item";
import   sMainBrandStoreAppCart                                                 from "./main/brandApp/store/cart";
import   sMainBrandStoreAppSearch                                               from "./main/brandApp/store/search";
import   sMainBrandStoreAppSearchItem                                               from "./main/brandApp/store/searchItem";


//选择门店
import   sMainBrandAppSelectStore                                               from "./main/brandApp/selectStore";
import   sMainBrandAppAuthorities                                               from "./main/brandApp/authorities";
import   sMainBrandAppStore                                                     from "./main/brandApp/store";
import   sMainBrandAppStorePersonalCenter                                       from "./main/brandApp/store/personalCenter";
import   sMainBrandAppPersonalCenterCenterHome                                  from "./main/brandApp/store/personalCenter/centerHome";
import   sMainBrandAppPersonalCenterAddAddress                                  from "./main/brandApp/store/personalCenter/addAddress";
import   sMainBrandAppPersonalCenterAddress                                     from "./main/brandApp/store/personalCenter/address";
import   sMainBrandAppPersonalCenterOrderDetail                                     from "./main/brandApp/store/personalCenter/orderDetail";
import   sMainBrandAppPersonalCenterApplyRefund                                     from "./main/brandApp/store/personalCenter/applyRefund";
import   sMainBrandAppPersonalCenterWriteLogistics                                     from "./main/brandApp/store/personalCenter/writeLogistics";
import   sMainBrandAppPersonalCenterAllOrder                                     from "./main/brandApp/store/personalCenter/allOrder";
import   sMainBrandAppPersonalCenterRefundOrder                                     from "./main/brandApp/store/personalCenter/refundOrder";
import   sMainBrandAppPersonalCenterRefundDetail                                     from "./main/brandApp/store/personalCenter/refundDetail";
import   sMainBrandAppConfirmOrder                                                      from "./main/brandApp/store/confirmOrder";
import   sMainBrandAppPersonalCenterEditAddress                                 from "./main/brandApp/store/personalCenter/editAddress";

import   sMainBrandAppStoreCloseDoor                                                     from "./main/brandApp/store/closeDoor";

export default angular.module(`${conf.app}.states`, [
    sMain.name,
    sMainOtherMain.name,
    sMainBindPhone.name,
    sMainWxLogin.name,
    sMainBrandApp.name,
    sMainLoginTime.name,
    sMainBrandStoreAppHome.name,
    sMainBrandStoreAppItem.name,
    sMainBrandStoreAppCart.name,
    sMainBrandStoreAppSearch.name,
    sMainBrandStoreAppSearchItem.name,
    sMainBrandAppSelectStore.name,
    sMainBrandAppStoreCloseDoor.name,
    sMainBrandAppStore.name,
    sMainBrandAppAuthorities.name,
    sMainBrandAppStorePersonalCenter.name,
    sMainBrandAppPersonalCenterCenterHome.name,
    sMainBrandAppPersonalCenterAddress.name,
    sMainBrandAppPersonalCenterAddAddress.name,
    sMainBrandAppPersonalCenterAllOrder.name,
    sMainBrandAppConfirmOrder.name,
    sMainBrandAppPersonalCenterOrderDetail.name,
    sMainBrandAppPersonalCenterApplyRefund.name,
    sMainBrandAppPersonalCenterWriteLogistics.name,
    sMainBrandAppPersonalCenterEditAddress.name,
    sMainBrandAppPersonalCenterRefundOrder.name,
    sMainBrandAppPersonalCenterRefundDetail.name,
])