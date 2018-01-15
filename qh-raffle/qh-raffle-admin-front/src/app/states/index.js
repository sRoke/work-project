import conf                                                     from "../conf";
import sMain                                                    from "./main";
import sMainOtherMain                                           from "./main/otherMain";
import sMainBindPhone                                           from "./main/bindPhone";
import sMainWxLogin                                             from "./main/wxLogin";
import sMainRaffleApp                                            from "./main/raffleApp";
import sMainLoginTime                                           from "./main/loginTime";
// import   sMainJoinUs                                         from "./main/joinUS";
import sMainRaffleAppHome                                        from "./main/raffleApp/home";




//抽奖活动后台
import sMainRaffleAppRaffle                                     from "./main/raffleApp/raffle";

import sMainRaffleAppRaffleHome                                 from "./main/raffleApp/raffle/raffleHome";
import sMainRaffleAppRaffleAdd                                  from "./main/raffleApp/raffle/raffleAdd";
import sMainRaffleAppRaffleEdit                                  from "./main/raffleApp/raffle/raffleEdit";
import sMainRaffleAppRaffleView                                 from "./main/raffleApp/raffle/raffleView";
import sMainRaffleAppRaffleJoinNum                              from "./main/raffleApp/raffle/joinNum";
import sMainRaffleAppRaffleTextImg                              from "./main/raffleApp/raffle/textImg";
import sMainRaffleAppRafflePriceList                            from "./main/raffleApp/raffle/priceList";
import sMainRaffleAppRaffleSetLottery                          from "./main/raffleApp/raffle/setLottery";





export default angular.module(`${conf.app}.states`, [
    sMain.name,
    sMainOtherMain.name,
    sMainBindPhone.name,
    sMainWxLogin.name,
    sMainRaffleApp.name,
    sMainLoginTime.name,
    sMainRaffleAppHome.name,


    sMainRaffleAppRaffle.name,
    sMainRaffleAppRaffleHome.name,
    sMainRaffleAppRaffleAdd.name,
    sMainRaffleAppRaffleEdit.name,
    sMainRaffleAppRaffleView.name,
    sMainRaffleAppRaffleJoinNum.name,
    sMainRaffleAppRaffleTextImg.name,
    sMainRaffleAppRafflePriceList.name,
    sMainRaffleAppRaffleSetLottery.name
])