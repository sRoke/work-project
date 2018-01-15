import conf                                                     from "../conf";
import sMain                                                    from "./main";
import sMainOtherMain                                           from "./main/otherMain";
import sMainBindPhone                                           from "./main/bindPhone";
import sMainWxLogin                                             from "./main/wxLogin";
import sMainVoteApp                                            from "./main/voteApp";
import sMainLoginTime                                           from "./main/loginTime";
// import   sMainJoinUs                                         from "./main/joinUS";
import sMainVoteAppHome                                        from "./main/voteApp/home";




//砍价活动后台
import sMainVoteAppVote                                     from "./main/voteApp/vote";

import sMainVoteAppVoteHome                                 from "./main/voteApp/vote/voteHome";
import sMainVoteAppVoteAdd                                  from "./main/voteApp/vote/voteAdd";
import sMainVoteAppVoteEdit                                  from "./main/voteApp/vote/voteEdit";
import sMainVoteAppVoteView                                 from "./main/voteApp/vote/voteView";
import sMainVoteAppVoteJoinNum                              from "./main/voteApp/vote/joinNum";
import sMainVoteAppVoteTextImg                              from "./main/voteApp/vote/textImg";
import sMainVoteAppVoteChooseTime                           from "./main/voteApp/vote/chooseTime";

import sMainVoteAppVoteSuccecssPeo                          from "./main/voteApp/vote/succecssPeo";

import sMainVoteAppVotePriceList                            from "./main/voteApp/vote/priceList";

import sMainVoteAppVoteSampleReels                          from "./main/voteApp/vote/sampleReels";
import sMainVoteAppVoteAuditing                          from "./main/voteApp/vote/auditing";
import sMainVoteAppVoteWorkInfo                          from "./main/voteApp/vote/workInfo";





export default angular.module(`${conf.app}.states`, [
    sMain.name,
    sMainOtherMain.name,
    sMainBindPhone.name,
    sMainWxLogin.name,
    sMainVoteApp.name,
    sMainLoginTime.name,
    sMainVoteAppHome.name,


    sMainVoteAppVote.name,
    sMainVoteAppVoteHome.name,
    sMainVoteAppVoteAdd.name,
    sMainVoteAppVoteEdit.name,
    sMainVoteAppVoteView.name,
    sMainVoteAppVoteJoinNum.name,
    sMainVoteAppVoteTextImg.name,
    sMainVoteAppVoteChooseTime.name,
    sMainVoteAppVoteSuccecssPeo.name,
    sMainVoteAppVotePriceList.name,
    sMainVoteAppVoteSampleReels.name,
    sMainVoteAppVoteAuditing.name,
    sMainVoteAppVoteWorkInfo.name,
])