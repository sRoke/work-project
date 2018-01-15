import   conf                               from "../conf";
import   sMain                              from "./main";

import   sMainBindPhone                     from "./main/bindPhone";
import   sMainLogin                         from "./main/login";
import   sMainBrandApp                      from "./main/brandApp";
import   sMainLoginTime                     from "./main/loginTime";
import   sMainBrandAppHome                  from "./main/brandApp/home";
import   sMainBrandAppPublicNumber          from "./main/brandApp/publicNumber";
import  sMainBrandAppAuthorization          from  './main/brandApp/authorization';
import  sMainBrandAppPaySetting             from  './main/brandApp/paySetting';
import  sMainBrandAppSetBuyPrice             from  './main/brandApp/setBuyPrice';


//基础数据
import  sMainBrandAppBasicData             from  './main/brandApp/basicData';
import  sMainBrandAppBasicStore            from  './main/brandApp/store';
import  sMainBrandAppBasicAddStore         from  './main/brandApp/addStore';
import  sMainBrandAppBasicEditStore        from  './main/brandApp/editStore';
import  sMainBrandAppGooSee                from  './main/brandApp/gooSee';

export default angular.module(`${conf.app}.states`, [
    sMain.name,
    sMainBindPhone.name,
    sMainLogin.name,
    sMainBrandApp.name,
    sMainLoginTime.name,
    sMainBrandAppHome.name,
    sMainBrandAppAuthorization.name,
    sMainBrandAppPaySetting.name,
    sMainBrandAppPublicNumber.name,
    sMainBrandAppBasicData.name,
    sMainBrandAppGooSee.name,
    sMainBrandAppBasicStore.name,
    sMainBrandAppBasicAddStore.name,
    sMainBrandAppBasicEditStore.name,
    sMainBrandAppSetBuyPrice.name
])

