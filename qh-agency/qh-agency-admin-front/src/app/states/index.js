import   conf                               from "../conf";
import   sMain                              from "./main";

import   sMainBindPhone                     from "./main/bindPhone";
import   sMainLogin                         from "./main/login";
import   sMainBrandApp                     from "./main/brandApp";
import   sMainLoginTime                     from "./main/loginTime";
import   sMainAuthorizationImg              from "./main/authorizationImg";

import   sMainBrandAppUser                 from "./main/brandApp/user";
import   sMainBrandAppGooAdd               from "./main/brandApp/gooAdd";
import   sMainBrandAppGoods                from "./main/brandApp/goods";

import   sMainBrandAppGoodEdit                from "./main/brandApp/goodEdit";
import   sMainBrandAppGoodAdd               from "./main/brandApp/goodAdd";

import   sMainBrandAppGooSee               from "./main/brandApp/gooSee";
import   sMainBrandAppComplain             from "./main/brandApp/complain";
import   sMainBrandAppHome                 from "./main/brandApp/home";
import   sMainBrandAppUsAdd                from "./main/brandApp/usAdd";
import   sMainBrandAppUsEdit               from "./main/brandApp/usEdit";
import   sMainBrandAppOrder                from "./main/brandApp/order";
import   sMainBrandAppOrdDetail            from "./main/brandApp/ordDetail";
import   sMainBrandAppRefund               from "./main/brandApp/refund";
import   sMainBrandAppRefDetail            from "./main/brandApp/refDetail";
import   sMainBrandAppRole                 from "./main/brandApp/role";
import   sMainBrandAppMember               from "./main/brandApp/member";
import   sMainBrandAppAttribute            from "./main/brandApp/attribute";
import   sMainBrandAppAttAdd               from "./main/brandApp/attAdd";
import   sMainBrandAppClassify             from "./main/brandApp/classify";
import   sMainBrandAppMbAdd                from "./main/brandApp/mbAdd";
import   sMainBrandAppRoAdd                from "./main/brandApp/roAdd";
import   sMainBrandAppQiNiu                from "./main/brandApp/qiniu";
import   sMainBrandAppOrg                  from "./main/brandApp/org";
import   sMainBrandAppOrgEdit              from "./main/brandApp/org/edit";
import   sMainBrandAppOrgList              from "./main/brandApp/org/list";
import   sMainBrandAppSysConf              from "./main/brandApp/sysConf";
import   sMainBrandAppManageRefund         from "./main/brandApp/manageRefund";
import   sMainBrandAppPartnerApply         from "./main/brandApp/partnerApply";
import   sMainBrandAppPartnerManage        from "./main/brandApp/partnerManage";
import   sMainBrandAppManageLook           from "./main/brandApp/manageLook";
import   sMainBrandAppReview               from "./main/brandApp/review";
import   sMainBrandAppPublish              from "./main/brandApp/publish";
import   sMainBrandAppSendOrder            from "./main/brandApp/sendOrder";

import   sMainBrandAppLookAuthorization    from "./main/brandApp/lookAuthorization";
import   sMainBrandAppPublicNumber         from "./main/brandApp/publicNumber";
//会员编辑  2017-08-01
import   sMainBrandAppMemEditr             from "./main/brandApp/memEdit";
//rmy 2017-07-17 发货详情
import   sMainBrandAppSendDetail           from "./main/brandApp/sendDetail";
//rmy 2017-08-03 退货管理
import   sMainBrandAppRfGoods              from  "./main/brandApp/rfGoods";
import  sMainBrandAppExample               from  './main/brandApp/example';
import  sMainBrandAppAssetManage           from  './main/brandApp/assetManage';
import  sMainBrandAppTransactionLog        from  './main/brandApp/transactionLog';
import  sMainBrandAppAuthorization         from  './main/brandApp/authorization';

import  sMainBrandAppBasicSetting          from  './main/brandApp/basicSetting';
import  sMainBrandAppPaySetting          from  './main/brandApp/paySetting';


export default angular.module(`${conf.app}.states`, [
    sMain.name,
    sMainBrandAppExample.name,
    sMainBindPhone.name,
    sMainLogin.name,
    sMainBrandApp.name,
    sMainLoginTime.name,
    sMainBrandAppUser.name,
    sMainBrandAppGooAdd.name,
    sMainBrandAppGoods.name,
    sMainBrandAppGooSee.name,

    sMainBrandAppGoodEdit.name,
    sMainBrandAppGoodAdd.name,

    sMainBrandAppComplain.name,
    sMainBrandAppHome.name,
    sMainBrandAppOrder.name,
    sMainBrandAppOrdDetail.name,
    sMainBrandAppRefund.name,
    sMainBrandAppRefDetail.name,
    sMainBrandAppRole.name,
    sMainBrandAppMember.name,
    sMainBrandAppMemEditr.name,
    sMainBrandAppAttribute.name,
    sMainBrandAppAttAdd.name,
    sMainBrandAppClassify.name,
    sMainBrandAppMbAdd.name,
    sMainBrandAppUsAdd.name,
    sMainBrandAppUsEdit.name,
    sMainBrandAppRoAdd.name,
    sMainBrandAppQiNiu.name,
    sMainBrandAppOrg.name,
    sMainBrandAppOrgEdit.name,
    sMainBrandAppOrgList.name,
    sMainBrandAppSysConf.name,
    sMainBrandAppManageRefund.name,
    sMainBrandAppPartnerApply.name,
    sMainBrandAppPartnerManage.name,
    sMainBrandAppManageLook.name,
    sMainBrandAppReview.name,
    sMainBrandAppPublish.name,
    sMainBrandAppSendOrder.name,
    sMainBrandAppLookAuthorization.name,
    sMainBrandAppSendDetail.name,
    sMainBrandAppRfGoods.name,
    sMainBrandAppAssetManage.name,
    sMainBrandAppTransactionLog.name,
    sMainBrandAppAuthorization.name,
    sMainAuthorizationImg.name,
    sMainBrandAppBasicSetting.name,
    sMainBrandAppPublicNumber.name,
    sMainBrandAppPaySetting.name
])

