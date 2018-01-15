import   conf                                       from "../conf";
import   sMain                                      from "./main";


import   sMainContentMain                           from "./main/contentMain";
import   sMainBindPhone                             from "./main/bindPhone";
import   sMainLogin                                 from "./main/login";
import   sMainBrandApp                              from "./main/brandApp";
import   sMainLoginTime                             from "./main/loginTime";
import   sMainBrandAppHome                          from "./main/brandApp/home";
import   sMainBrandAppPublicNumber                  from "./main/brandApp/publicNumber";


//营销管理
import  sMainBrandAppMarketing                  from  './main/brandApp/marketing';
import  sMainBrandAppMarketingVote              from  './main/brandApp/marketing/vote';
import  sMainBrandAppMarketingWorksList         from  './main/brandApp/marketing/worksList'
import  sMainBrandAppMarketingRecordVote        from  './main/brandApp/marketing/recordVote'
import  sMainBrandAppMarketingCreatVote         from  './main/brandApp/marketing/creatVote';


export default angular.module(`${conf.app}.states`, [
    sMain.name,
    sMainContentMain.name,

    sMainBindPhone.name,
    sMainLogin.name,
    sMainBrandApp.name,
    sMainLoginTime.name,
    sMainBrandAppHome.name,


    sMainBrandAppMarketing.name,
    sMainBrandAppMarketingVote.name,
    sMainBrandAppMarketingWorksList.name,
    sMainBrandAppMarketingRecordVote.name,
    sMainBrandAppMarketingCreatVote.name,
    sMainBrandAppPublicNumber.name
])

