import conf                                                     from "../conf";
import sMain                                                    from "./main";
import sMainOtherMain                                           from "./main/otherMain";
import sMainBindPhone                                           from "./main/bindPhone";
import sMainWxLogin                                             from "./main/wxLogin";
import sMainBrandApp                                            from "./main/brandApp";
import sMainLoginTime                                           from "./main/loginTime";
// import   sMainJoinUs                                         from "./main/joinUS";
import sMainBrandAppHome                                        from "./main/brandApp/home";



//--------------------------------------------------------------no.1   投票活动
import sMainBrandAppVote                                        from "./main/brandApp/vote";
import sMainBrandAppVotHome                                     from "./main/brandApp/vote/votHome";
import sMainBrandAppVotSignUp                                   from "./main/brandApp/vote/votSignUp";
import sMainBrandAppMyVote                                      from "./main/brandApp/vote/myVote";

export default angular.module(`${conf.app}.states`, [
    sMain.name,
    sMainOtherMain.name,
    sMainBindPhone.name,
    sMainWxLogin.name,
    sMainBrandApp.name,
    sMainLoginTime.name,
    sMainBrandAppHome.name,
    sMainBrandAppVote.name,
    sMainBrandAppVotHome.name,
    sMainBrandAppVotSignUp.name,
    sMainBrandAppMyVote.name,


])