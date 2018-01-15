import conf                                                     from "../conf";
import sMain                                                    from "./main";
import sMainOtherMain                                           from "./main/otherMain";
import sMainBindPhone                                           from "./main/bindPhone";
import sMainWxLogin                                             from "./main/wxLogin";
import sMainRaffleApp                                            from "./main/raffleApp";
import sMainLoginTime                                           from "./main/loginTime";
// import   sMainJoinUs                                         from "./main/joinUS";
import sMainRaffleAppHome                                        from "./main/raffleApp/raffle/home";
import sMainRaffleAppConfirmOrder                                from "./main/raffleApp/raffle/confirmOrder";
import sMainRaffleAppEditAddress                                 from "./main/raffleApp/raffle/editAddress";
import sMainRaffleAppAddress                                     from "./main/raffleApp/raffle/address";
import sMainRaffleAppAddAddress                                  from "./main/raffleApp/raffle/addAddress";
//--------------------------------------------------------------no.1   投票活动
import sMainRaffleAppRaffle                                        from "./main/raffleApp/raffle";

import sMainRaffleAppDemo                                      from "./main/raffleApp/raffle/demo";

export default angular.module(`${conf.app}.states`, [
    sMain.name,
    sMainOtherMain.name,
    sMainBindPhone.name,
    sMainWxLogin.name,
    sMainRaffleApp.name,
    sMainLoginTime.name,
    sMainRaffleAppHome.name,
    sMainRaffleAppRaffle.name,
    sMainRaffleAppConfirmOrder.name,
    sMainRaffleAppEditAddress.name,
    sMainRaffleAppAddress.name,
    sMainRaffleAppAddAddress.name,
    sMainRaffleAppDemo.name,
])