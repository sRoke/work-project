import   conf                               from "../conf";
import   sMain                              from "./main";


import   sMainContentMain                   from "./main/contentMain";
import   sMainBindPhone                     from "./main/bindPhone";
import   sMainLogin                         from "./main/login";
import   sMainBrandApp                     from "./main/brandApp";
import   sMainLoginTime                     from "./main/loginTime";
import   sMainAuthorizationImg              from "./main/authorizationImg";
import   sMainBrandAppHome                 from "./main/brandApp/home";


//应用
import  sMainBrandAppApplication           from  './main/brandApp/application';
import  sMainBrandAppApplicationList       from  './main/brandApp/application/applicationList';
import  sMainBrandAppInformationEdit       from  './main/brandApp/application/informationEdit';

//应用角色
import  sMainBrandAppAppUserList           from  './main/brandApp/application/appUserList';
import  sMainBrandAppAppUserAdd            from  './main/brandApp/application/appUserAdd';
import  sMainBrandAppAppUserView            from  './main/brandApp/application/appUserView';

//商家
import  sMainBrandAppBusiness              from  './main/brandApp/business';
import  sMainBrandAppBusinessList          from  './main/brandApp/business/businessList';
import  sMainBrandAppBusinessAdd           from  './main/brandApp/business/businessAdd';
import  sMainBrandAppBusinessEdit          from  './main/brandApp/business/businessEdit';

//商家应用
import  sMainBrandAppBusApplication        from  './main/brandApp/business/busApplication';
import  sMainBrandAppBusAppAdd             from  './main/brandApp/business/busAppAdd';
import  sMainBrandAppBusAppView            from  './main/brandApp/business/busAppView';



export default angular.module(`${conf.app}.states`, [
    sMain.name,
    sMainContentMain.name,


    sMainBindPhone.name,
    sMainLogin.name,
    sMainBrandApp.name,
    sMainLoginTime.name,
    sMainBrandAppHome.name,
    sMainAuthorizationImg.name,


    sMainBrandAppApplication.name,
    sMainBrandAppApplicationList.name,
    sMainBrandAppInformationEdit.name,
    sMainBrandAppAppUserList.name,
    sMainBrandAppAppUserAdd.name,
    sMainBrandAppAppUserView.name,

    sMainBrandAppBusiness.name,
    sMainBrandAppBusinessList.name,
    sMainBrandAppBusinessAdd.name,
    sMainBrandAppBusinessEdit.name,

    sMainBrandAppBusApplication.name,
    sMainBrandAppBusAppAdd.name,
    sMainBrandAppBusAppView.name,
])

